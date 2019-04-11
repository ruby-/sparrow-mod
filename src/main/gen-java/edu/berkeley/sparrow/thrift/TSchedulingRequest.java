/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package edu.berkeley.sparrow.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2019-04-09")
public class TSchedulingRequest implements org.apache.thrift.TBase<TSchedulingRequest, TSchedulingRequest._Fields>, java.io.Serializable, Cloneable, Comparable<TSchedulingRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSchedulingRequest");

  private static final org.apache.thrift.protocol.TField APP_FIELD_DESC = new org.apache.thrift.protocol.TField("app", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TASKS_FIELD_DESC = new org.apache.thrift.protocol.TField("tasks", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField USER_FIELD_DESC = new org.apache.thrift.protocol.TField("user", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PROBE_RATIO_FIELD_DESC = new org.apache.thrift.protocol.TField("probeRatio", org.apache.thrift.protocol.TType.DOUBLE, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSchedulingRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSchedulingRequestTupleSchemeFactory());
  }

  public String app; // required
  public List<TTaskSpec> tasks; // required
  public TUserGroupInfo user; // required
  public String description; // optional
  public double probeRatio; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    APP((short)1, "app"),
    TASKS((short)2, "tasks"),
    USER((short)3, "user"),
    DESCRIPTION((short)4, "description"),
    PROBE_RATIO((short)5, "probeRatio");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // APP
          return APP;
        case 2: // TASKS
          return TASKS;
        case 3: // USER
          return USER;
        case 4: // DESCRIPTION
          return DESCRIPTION;
        case 5: // PROBE_RATIO
          return PROBE_RATIO;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PROBERATIO_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DESCRIPTION,_Fields.PROBE_RATIO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.APP, new org.apache.thrift.meta_data.FieldMetaData("app", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TASKS, new org.apache.thrift.meta_data.FieldMetaData("tasks", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TTaskSpec.class))));
    tmpMap.put(_Fields.USER, new org.apache.thrift.meta_data.FieldMetaData("user", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TUserGroupInfo.class)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROBE_RATIO, new org.apache.thrift.meta_data.FieldMetaData("probeRatio", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSchedulingRequest.class, metaDataMap);
  }

  public TSchedulingRequest() {
  }

  public TSchedulingRequest(
    String app,
    List<TTaskSpec> tasks,
    TUserGroupInfo user)
  {
    this();
    this.app = app;
    this.tasks = tasks;
    this.user = user;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSchedulingRequest(TSchedulingRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetApp()) {
      this.app = other.app;
    }
    if (other.isSetTasks()) {
      List<TTaskSpec> __this__tasks = new ArrayList<TTaskSpec>(other.tasks.size());
      for (TTaskSpec other_element : other.tasks) {
        __this__tasks.add(new TTaskSpec(other_element));
      }
      this.tasks = __this__tasks;
    }
    if (other.isSetUser()) {
      this.user = new TUserGroupInfo(other.user);
    }
    if (other.isSetDescription()) {
      this.description = other.description;
    }
    this.probeRatio = other.probeRatio;
  }

  public TSchedulingRequest deepCopy() {
    return new TSchedulingRequest(this);
  }

  @Override
  public void clear() {
    this.app = null;
    this.tasks = null;
    this.user = null;
    this.description = null;
    setProbeRatioIsSet(false);
    this.probeRatio = 0.0;
  }

  public String getApp() {
    return this.app;
  }

  public TSchedulingRequest setApp(String app) {
    this.app = app;
    return this;
  }

  public void unsetApp() {
    this.app = null;
  }

  /** Returns true if field app is set (has been assigned a value) and false otherwise */
  public boolean isSetApp() {
    return this.app != null;
  }

  public void setAppIsSet(boolean value) {
    if (!value) {
      this.app = null;
    }
  }

  public int getTasksSize() {
    return (this.tasks == null) ? 0 : this.tasks.size();
  }

  public java.util.Iterator<TTaskSpec> getTasksIterator() {
    return (this.tasks == null) ? null : this.tasks.iterator();
  }

  public void addToTasks(TTaskSpec elem) {
    if (this.tasks == null) {
      this.tasks = new ArrayList<TTaskSpec>();
    }
    this.tasks.add(elem);
  }

  public List<TTaskSpec> getTasks() {
    return this.tasks;
  }

  public TSchedulingRequest setTasks(List<TTaskSpec> tasks) {
    this.tasks = tasks;
    return this;
  }

  public void unsetTasks() {
    this.tasks = null;
  }

  /** Returns true if field tasks is set (has been assigned a value) and false otherwise */
  public boolean isSetTasks() {
    return this.tasks != null;
  }

  public void setTasksIsSet(boolean value) {
    if (!value) {
      this.tasks = null;
    }
  }

  public TUserGroupInfo getUser() {
    return this.user;
  }

  public TSchedulingRequest setUser(TUserGroupInfo user) {
    this.user = user;
    return this;
  }

  public void unsetUser() {
    this.user = null;
  }

  /** Returns true if field user is set (has been assigned a value) and false otherwise */
  public boolean isSetUser() {
    return this.user != null;
  }

  public void setUserIsSet(boolean value) {
    if (!value) {
      this.user = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public TSchedulingRequest setDescription(String description) {
    this.description = description;
    return this;
  }

  public void unsetDescription() {
    this.description = null;
  }

  /** Returns true if field description is set (has been assigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.description = null;
    }
  }

  public double getProbeRatio() {
    return this.probeRatio;
  }

  public TSchedulingRequest setProbeRatio(double probeRatio) {
    this.probeRatio = probeRatio;
    setProbeRatioIsSet(true);
    return this;
  }

  public void unsetProbeRatio() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROBERATIO_ISSET_ID);
  }

  /** Returns true if field probeRatio is set (has been assigned a value) and false otherwise */
  public boolean isSetProbeRatio() {
    return EncodingUtils.testBit(__isset_bitfield, __PROBERATIO_ISSET_ID);
  }

  public void setProbeRatioIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROBERATIO_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case APP:
      if (value == null) {
        unsetApp();
      } else {
        setApp((String)value);
      }
      break;

    case TASKS:
      if (value == null) {
        unsetTasks();
      } else {
        setTasks((List<TTaskSpec>)value);
      }
      break;

    case USER:
      if (value == null) {
        unsetUser();
      } else {
        setUser((TUserGroupInfo)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
      }
      break;

    case PROBE_RATIO:
      if (value == null) {
        unsetProbeRatio();
      } else {
        setProbeRatio((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case APP:
      return getApp();

    case TASKS:
      return getTasks();

    case USER:
      return getUser();

    case DESCRIPTION:
      return getDescription();

    case PROBE_RATIO:
      return getProbeRatio();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case APP:
      return isSetApp();
    case TASKS:
      return isSetTasks();
    case USER:
      return isSetUser();
    case DESCRIPTION:
      return isSetDescription();
    case PROBE_RATIO:
      return isSetProbeRatio();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TSchedulingRequest)
      return this.equals((TSchedulingRequest)that);
    return false;
  }

  public boolean equals(TSchedulingRequest that) {
    if (that == null)
      return false;

    boolean this_present_app = true && this.isSetApp();
    boolean that_present_app = true && that.isSetApp();
    if (this_present_app || that_present_app) {
      if (!(this_present_app && that_present_app))
        return false;
      if (!this.app.equals(that.app))
        return false;
    }

    boolean this_present_tasks = true && this.isSetTasks();
    boolean that_present_tasks = true && that.isSetTasks();
    if (this_present_tasks || that_present_tasks) {
      if (!(this_present_tasks && that_present_tasks))
        return false;
      if (!this.tasks.equals(that.tasks))
        return false;
    }

    boolean this_present_user = true && this.isSetUser();
    boolean that_present_user = true && that.isSetUser();
    if (this_present_user || that_present_user) {
      if (!(this_present_user && that_present_user))
        return false;
      if (!this.user.equals(that.user))
        return false;
    }

    boolean this_present_description = true && this.isSetDescription();
    boolean that_present_description = true && that.isSetDescription();
    if (this_present_description || that_present_description) {
      if (!(this_present_description && that_present_description))
        return false;
      if (!this.description.equals(that.description))
        return false;
    }

    boolean this_present_probeRatio = true && this.isSetProbeRatio();
    boolean that_present_probeRatio = true && that.isSetProbeRatio();
    if (this_present_probeRatio || that_present_probeRatio) {
      if (!(this_present_probeRatio && that_present_probeRatio))
        return false;
      if (this.probeRatio != that.probeRatio)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_app = true && (isSetApp());
    list.add(present_app);
    if (present_app)
      list.add(app);

    boolean present_tasks = true && (isSetTasks());
    list.add(present_tasks);
    if (present_tasks)
      list.add(tasks);

    boolean present_user = true && (isSetUser());
    list.add(present_user);
    if (present_user)
      list.add(user);

    boolean present_description = true && (isSetDescription());
    list.add(present_description);
    if (present_description)
      list.add(description);

    boolean present_probeRatio = true && (isSetProbeRatio());
    list.add(present_probeRatio);
    if (present_probeRatio)
      list.add(probeRatio);

    return list.hashCode();
  }

  @Override
  public int compareTo(TSchedulingRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetApp()).compareTo(other.isSetApp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.app, other.app);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTasks()).compareTo(other.isSetTasks());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTasks()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tasks, other.tasks);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUser()).compareTo(other.isSetUser());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user, other.user);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(other.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.description, other.description);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProbeRatio()).compareTo(other.isSetProbeRatio());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProbeRatio()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.probeRatio, other.probeRatio);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TSchedulingRequest(");
    boolean first = true;

    sb.append("app:");
    if (this.app == null) {
      sb.append("null");
    } else {
      sb.append(this.app);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tasks:");
    if (this.tasks == null) {
      sb.append("null");
    } else {
      sb.append(this.tasks);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("user:");
    if (this.user == null) {
      sb.append("null");
    } else {
      sb.append(this.user);
    }
    first = false;
    if (isSetDescription()) {
      if (!first) sb.append(", ");
      sb.append("description:");
      if (this.description == null) {
        sb.append("null");
      } else {
        sb.append(this.description);
      }
      first = false;
    }
    if (isSetProbeRatio()) {
      if (!first) sb.append(", ");
      sb.append("probeRatio:");
      sb.append(this.probeRatio);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (user != null) {
      user.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TSchedulingRequestStandardSchemeFactory implements SchemeFactory {
    public TSchedulingRequestStandardScheme getScheme() {
      return new TSchedulingRequestStandardScheme();
    }
  }

  private static class TSchedulingRequestStandardScheme extends StandardScheme<TSchedulingRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSchedulingRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // APP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.app = iprot.readString();
              struct.setAppIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TASKS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.tasks = new ArrayList<TTaskSpec>(_list16.size);
                TTaskSpec _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new TTaskSpec();
                  _elem17.read(iprot);
                  struct.tasks.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setTasksIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.user = new TUserGroupInfo();
              struct.user.read(iprot);
              struct.setUserIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.description = iprot.readString();
              struct.setDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PROBE_RATIO
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.probeRatio = iprot.readDouble();
              struct.setProbeRatioIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSchedulingRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.app != null) {
        oprot.writeFieldBegin(APP_FIELD_DESC);
        oprot.writeString(struct.app);
        oprot.writeFieldEnd();
      }
      if (struct.tasks != null) {
        oprot.writeFieldBegin(TASKS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.tasks.size()));
          for (TTaskSpec _iter19 : struct.tasks)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.user != null) {
        oprot.writeFieldBegin(USER_FIELD_DESC);
        struct.user.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.description != null) {
        if (struct.isSetDescription()) {
          oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
          oprot.writeString(struct.description);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetProbeRatio()) {
        oprot.writeFieldBegin(PROBE_RATIO_FIELD_DESC);
        oprot.writeDouble(struct.probeRatio);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TSchedulingRequestTupleSchemeFactory implements SchemeFactory {
    public TSchedulingRequestTupleScheme getScheme() {
      return new TSchedulingRequestTupleScheme();
    }
  }

  private static class TSchedulingRequestTupleScheme extends TupleScheme<TSchedulingRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSchedulingRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetApp()) {
        optionals.set(0);
      }
      if (struct.isSetTasks()) {
        optionals.set(1);
      }
      if (struct.isSetUser()) {
        optionals.set(2);
      }
      if (struct.isSetDescription()) {
        optionals.set(3);
      }
      if (struct.isSetProbeRatio()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetApp()) {
        oprot.writeString(struct.app);
      }
      if (struct.isSetTasks()) {
        {
          oprot.writeI32(struct.tasks.size());
          for (TTaskSpec _iter20 : struct.tasks)
          {
            _iter20.write(oprot);
          }
        }
      }
      if (struct.isSetUser()) {
        struct.user.write(oprot);
      }
      if (struct.isSetDescription()) {
        oprot.writeString(struct.description);
      }
      if (struct.isSetProbeRatio()) {
        oprot.writeDouble(struct.probeRatio);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSchedulingRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.app = iprot.readString();
        struct.setAppIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.tasks = new ArrayList<TTaskSpec>(_list21.size);
          TTaskSpec _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = new TTaskSpec();
            _elem22.read(iprot);
            struct.tasks.add(_elem22);
          }
        }
        struct.setTasksIsSet(true);
      }
      if (incoming.get(2)) {
        struct.user = new TUserGroupInfo();
        struct.user.read(iprot);
        struct.setUserIsSet(true);
      }
      if (incoming.get(3)) {
        struct.description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
      if (incoming.get(4)) {
        struct.probeRatio = iprot.readDouble();
        struct.setProbeRatioIsSet(true);
      }
    }
  }

}

