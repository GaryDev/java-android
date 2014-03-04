package org.snow.wcfapp.soap.object;

import java.util.Hashtable;
import java.util.List;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public abstract class KvmListBase<T> implements KvmSerializable {

	public static final String NAMESPACE = "http://snow.org/retailservice/2014/02/27/";
	
	protected List<T> list;
	protected List<String> nameList;
	protected String nameSpace;
	
	public KvmListBase(List<T> list, List<String> nameList) {
		this(NAMESPACE, list, nameList);
	}
	
	public KvmListBase(String nameSpace, List<T> list, List<String> nameList) {
		this.nameSpace = nameSpace;
		this.list = list;
		this.nameList = nameList;
	}
	
	@Override
	public Object getProperty(int index) {
		return list.get(index);
	}

	@Override
	public int getPropertyCount() {
		return list.size();
	}

	@Override
	public void getPropertyInfo(int index, Hashtable ht, PropertyInfo info) {
		info.namespace = nameSpace;
		info.name = nameList.get(index);
	}

	@Override
	public void setProperty(int index, Object value) {
		list.add(index, (T)value);
	}

}
