import boto
import os
import sys
import tempfile
import time
import subprocess
import shutil

from optparse import OptionParser


def parse_args():
  parser = OptionParser(usage="sparrow-exp <action> [options]" +
    "\n\n<action> can be: launch, deploy, start, stop, start-proto, stop-proto, command, collect-logs, destroy")
  parser.add_option("-z", "--zone", default="us-east-1b",
      help="Availability zone to launch instances in")
  parser.add_option("-a", "--ami", default="ami-a74393ce",
      help="Amazon Machine Image ID to use")
  parser.add_option("-t", "--instance-type", default="m1.large",
      help="Type of instance to launch (default: m1.large). " +
           "WARNING: must be 64 bit, thus small instances won't work")
  parser.add_option("-l", "--arrival-rate", type="int", default=100,
      help = "Arrival rate of jobs in proto frontends (jobs/s)")
  parser.add_option("-k", "--key-pair",
      help="Key pair to use on instances")
  parser.add_option("-i", "--identity-file",
      help="SSH private key file to use for logging into instances")
  parser.add_option("-f", "--frontends", type="int", default=1,
      help="Number of frontends to launch (default: 1)")
  parser.add_option("-b", "--backends", type="int", default=1,
      help="Number of backends to launch (default: 1)")
  parser.add_option("-w", "--wait", type="int", default=0,
      help="Number of seconds to wait for cluster nodes to boot (default: 0)")
  parser.add_option("-g", "--branch", default="master",
      help="Which git branch to checkout")
  parser.add_option("-d", "--log-dir", default="/tmp/",
      help="Local directory into which log files are copied")
  (opts, args) = parser.parse_args()
  if len(args) < 1:
    parser.print_help()
    sys.exit(1)
  if os.getenv('AWS_ACCESS_KEY_ID') == None:
    print >> sys.stderr, ("ERROR: The environment variable AWS_ACCESS_KEY_ID " +
                          "must be set")
    sys.exit(1)
  if os.getenv('AWS_SECRET_ACCESS_KEY') == None:
    print >> sys.stderr ("ERROR: The environment variable " +
                         "AWS_SECRET_ACCESS_KEY must be set")
    sys.exit(1)

  return (opts, args)

# Get the EC2 security group of the given name, creating it if it doesn't exist
def get_or_make_group(conn, name):
  groups = conn.get_all_security_groups()
  group = [g for g in groups if g.name == name]
  if len(group) > 0:
    return group[0]
  else:
    print "Creating security group " + name
    return conn.create_security_group(name, "Sparrow EC2 group")

# Copy a file to a given host through scp, throwing an exception if scp fails
def scp(host, opts, local_file, dest_file):
  subprocess.check_call(
      "scp -q -o StrictHostKeyChecking=no -i %s '%s' 'root@%s:%s'" %
      (opts.identity_file, local_file, host, dest_file), shell=True)

# Copy a file from a given host through scp, throwing an exception if scp fails
def scp_from(host, opts, dest_file, local_file):
  subprocess.check_call(
      "scp -q -o StrictHostKeyChecking=no -i %s 'root@%s:%s' '%s'" %
      (opts.identity_file, host, dest_file, local_file), shell=True)

# Parallel version of scp_from()
def scp_from_all(hosts, opts, dest_file, local_file):
  commands = []
  for host in hosts:
    commands.append(
      "scp -q -o StrictHostKeyChecking=no -i %s 'root@%s:%s' '%s'" %
      (opts.identity_file, host, dest_file, local_file))
  parallel_commands(commands, 0)
    

# Execute a sequence of commands in parallel, raising an exception if 
# more than tolerable_failures of them fail
def parallel_commands(commands, tolerable_failures):
  processes = {} # popen object --> command string
  failures = []
  for c in commands:
    p = subprocess.Popen(c, shell=True, stdout = subprocess.PIPE, 
                         stderr = subprocess.PIPE, stdin=subprocess.PIPE)
    processes[p] = c
  for p in processes.keys():
    (stdout, stderr) = p.communicate()
    if p.poll() != 0:
      failures.append((stdout, stderr, processes[p]))

  if len(failures) > tolerable_failures:
    out = "Parallel commands failed:\n"
    for (stdout, stderr, cmd) in failures:
      out = out + "command:\n%s\nstdout\n%sstderr\n%s\n" %  \
        (cmd, stdout, stderr)
    raise Exception(out)

# Run a command on a host through ssh, throwing an exception if ssh fails
def ssh(host, opts, command):
  subprocess.check_call(
      "ssh -t -o StrictHostKeyChecking=no -i %s root@%s '%s'" %
      (opts.identity_file, host, command), shell=True)

# Run a command on multiple hosts through ssh, throwing an exception on failure
def ssh_all(hosts, opts, command):
  commands = []
  for host in hosts:
    cmd = "ssh -t -o StrictHostKeyChecking=no -i %s root@%s '%s'" % \
      (opts.identity_file, host, command)
    commands.append(cmd)
  parallel_commands(commands, 0)

# Launch a cluster and return instances launched
def launch_cluster(conn, opts):
  backend_group = get_or_make_group(conn, "sparrow-backends")
  frontend_group = get_or_make_group(conn, "sparrow-frontends")
  groups = [backend_group, frontend_group]

  for group in groups:
    if group.rules == []: # Group was now just created
      # Allow all access from all other sparrow machines
      for group2 in groups:
        group.authorize(src_group=group2) 
      # Allow some access from all machines
      group.authorize('tcp', 22, 22, '0.0.0.0/0')

  print "Launching instances..."
  try:
    image = conn.get_all_images(image_ids=[opts.ami])[0]
  except:
    print >> stderr, "Could not find AMI " + opts.ami
    sys.exit(1)

  frontend_res = image.run(key_name = opts.key_pair,
                          security_groups = [frontend_group],
                          instance_type = opts.instance_type,
                          placement = opts.zone,
                          min_count = opts.frontends,
                          max_count = opts.frontends)
  backend_res = image.run(key_name = opts.key_pair,
                          security_groups = [backend_group],
                          instance_type = opts.instance_type,
                          placement = opts.zone,
                          min_count = opts.backends,
                          max_count = opts.backends)

  print "Launched cluster with %s frontends and %s backends" % (
         opts.frontends, opts.backends)

  return(frontend_res.instances, backend_res.instances)

# Wait for a set of launched instances to exit the "pending" state
# (i.e. either to start running or to fail and be terminated)
def wait_for_instances(conn, instances):
  while True:
    for i in instances:
      i.update()
    if len([i for i in instances if i.state == 'pending']) > 0:
      time.sleep(5)
    else:
      return

# Check whether a given EC2 instance object is in a state we consider active,
# i.e. not terminating or terminated. We count both stopping and stopped as
# active since we can restart stopped clusters.
def is_active(instance):
  return (instance.state in ['pending', 'running', 'stopping', 'stopped'])


def find_existing_cluster(conn, opts):
  print "Searching for existing Sparrow cluster..."
  reservations = conn.get_all_instances()
  frontend_nodes = []
  backend_nodes = []
  for res in reservations:
    active = [i for i in res.instances if is_active(i)]
    if len(active) > 0:
      group_names = [g.name for g in res.groups]
      if group_names == ["sparrow-frontends"]:
        frontend_nodes += res.instances
      elif group_names == ["sparrow-backends"]:
        backend_nodes += res.instances
  if frontend_nodes != [] and backend_nodes != []:
    print ("Found %d frontend and %s backend nodes" %
           (len(frontend_nodes), len(backend_nodes)))

    print "Frontends:"
    for fe in frontend_nodes:
      print fe.public_dns_name
    print "Backends:"
    for be in backend_nodes:
      print be.public_dns_name

    return (frontend_nodes, backend_nodes)
  else:
    print "ERROR: Could not find full cluster: fe=%s be=%s" % (
      frontend_nodes, backend_nodes)
    sys.exit(1)


# Deploy Sparrow binaries and configuration on a launched cluster
def deploy_cluster(frontends, backends, opts):
  # Replace template vars
  tmp_dir = tempfile.mkdtemp()

  template_vars = {
    "static_frontends": ",".join(["%s:12345" % i.public_dns_name \
                                 for i in frontends]),
    "frontend_list": "\n".join(["%s" % i.public_dns_name \
                                 for i in frontends]),
    "static_backends": ",".join(["%s:20502" % i.public_dns_name \
                                 for i in backends]),
    "backend_list": "\n".join(["%s" % i.public_dns_name \
                                 for i in backends]),
    "arrival_lambda": "%s" % opts.arrival_rate,
    "git_branch": "%s" % opts.branch
  }
  for filename in os.listdir("template"):
    if filename[0] not in '#.~' and filename[-1] != '~':
      local_file = os.path.join(tmp_dir, filename)
      with open(os.path.join("template", filename)) as src:
        with open(local_file, "w") as dest:
          text = src.read()
          for key in template_vars:
            text = text.replace("{{" + key + "}}", template_vars[key])
          dest.write(text)
          dest.close()

  driver_machine = frontends[0].public_dns_name
  print "Chose driver machine: %s ..." % driver_machine
 
  # Rsync this to one machine
  command = (("rsync -rv -e 'ssh -o StrictHostKeyChecking=no -i %s' " +
      "'%s/' 'root@%s:~/'") % (opts.identity_file, tmp_dir, driver_machine))
  subprocess.check_call(command, shell=True)
  # Remove the temp directory we created above
  shutil.rmtree(tmp_dir)

  print "Copying SSH key %s to driver..." % opts.identity_file
  ssh(driver_machine, opts, 'mkdir -p /root/.ssh')
  scp(driver_machine, opts, opts.identity_file, '/root/.ssh/id_rsa')

  print "Building sparrow on driver machine..."
  ssh(driver_machine, opts, "chmod 775 /root/build_sparrow.sh;" + 
                            "/root/build_sparrow.sh;")

  print "Deploying sparrow to other machines..."
  ssh(driver_machine, opts, "chmod 775 /root/deploy_sparrow.sh;" +
                            "/root/deploy_sparrow.sh")  

# Start a deployed Sparrow cluster
def start_cluster(frontends, backends, opts):
  all_machines = []
  for fe in frontends: 
    all_machines.append(fe.public_dns_name) 
  for be in backends: 
    all_machines.append(be.public_dns_name)

  print "Starting sparrow on all machines..."
  ssh_all(all_machines, opts, "chmod 755 /root/start_sparrow.sh;" +
                              "/root/start_sparrow.sh;")

# Stop a deployed Sparrow cluster
def stop_cluster(frontends, backends, opts):
  all_machines = []
  for fe in frontends:
    all_machines.append(fe.public_dns_name)
  for be in backends:
    all_machines.append(be.public_dns_name)
  print "Stopping sparrow on all machines..."
  ssh_all(all_machines, opts, "chmod 755 /root/stop_sparrow.sh;" +
                              "/root/stop_sparrow.sh;")

# Start the prototype backends/frontends
def start_proto(frontends, backends, opts):
  print "Starting Proto backends..."
  ssh_all([be.public_dns_name for be in backends], opts,
         "chmod 755 /root/start_proto_backend.sh;" + 
         "/root/start_proto_backend.sh")
  print "Starting Proto frontends..."
  ssh_all([fe.public_dns_name for fe in frontends], opts,
          "chmod 755 /root/start_proto_frontend.sh;" + 
          "/root/start_proto_frontend.sh")

# Start the prototype backends/frontends
def stop_proto(frontends, backends, opts):
  print "Stopping Proto frontends..."
  ssh_all([fe.public_dns_name for fe in frontends], opts,
          "chmod 755 /root/stop_proto_frontend.sh;" +
          "/root/stop_proto_frontend.sh")
  print "Stopping Proto backends..."
  ssh_all([be.public_dns_name for be in backends], opts,
         "chmod 755 /root/stop_proto_backend.sh;" +
         "/root/stop_proto_backend.sh")

# Collect logs from all machines
def collect_logs(frontends, backends, opts):
  scp_from_all([fe.public_dns_name for fe in frontends], opts,
    "/root/*.log", opts.log_dir)
  scp_from_all([be.public_dns_name for be in backends], opts,
    "/root/*.log", opts.log_dir)
  ssh_all([fe.public_dns_name for fe in frontends], opts,
          "rm -f /root/*.log")
  ssh_all([be.public_dns_name for be in backends], opts,
          "rm -f /root/*.log")

# Tear down a cluster
def destroy_cluster(frontends, backends, opts):
  response = raw_input("Are you sure you want to destroy the cluster " +
    "?\nALL DATA ON ALL NODES WILL BE LOST!!\n" +
    "Destroy cluster (y/N): ")

  if response == "y":
    print "Terminating frontends"
    for fe in frontends:
      fe.terminate()
    print "Terminating backends"
    for be in backends:
      be.terminate()   

# Execute a shell command on all machines
def execute_command(frontends, backends, opts, cmd):
  ssh_all([fe.public_dns_name for fe in frontends], opts, cmd)
  ssh_all([be.public_dns_name for be in backends], opts, cmd)

def main():
  (opts, args) = parse_args()
  conn = boto.connect_ec2()
  action = args[0]

  if action == "launch":
    (frontends, backends) = launch_cluster(conn, opts)
    return

  if action == "command" and len(args) < 2:
    print "Command action requires command string"

  # Wait until ec2 says the cluster is started, then possibly wait more time
  # to make sure all nodes have booted.
  (frontends, backends) = find_existing_cluster(conn, opts)
  print "Waiting for instances to start up"
  wait_for_instances(conn, frontends)
  wait_for_instances(conn, backends)

  print "Waiting %d more seconds..." % opts.wait
  time.sleep(opts.wait)

  if action == "command":
    cmd = " ".join(args[1:])
    print "Executing command: %s" % cmd
    execute_command(frontends, backends, opts, cmd)

  if action == "deploy":
    print "Deploying files..."
    deploy_cluster(frontends, backends, opts)

  if action == "start":
    print "Starting cluster..."
    start_cluster(frontends, backends, opts)

  if action == "stop":
    print "Stopping cluster..."
    stop_cluster(frontends, backends, opts)

  if action == "start-proto":
    print "Starting proto application..."
    start_proto(frontends, backends, opts)

  if action == "stop-proto":
    print "Stopping proto application..."
    stop_proto(frontends, backends, opts)

  if action == "collect-logs":
    print "Collecting logs..."
    collect_logs(frontends, backends, opts)

  if action == "destroy":
    destroy_cluster(frontends, backends, opts)

if __name__ == "__main__":
  main()