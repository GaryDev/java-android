package org.snow.wcfapp.soap.object;

import java.util.Hashtable;
import java.util.List;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class KvmListCustomize<T extends KvmSerializable> extends KvmListBase<T> {

	public KvmListCustomize(List<T> valueList, List<String> nameList) {
		super(valueList, nameList);
	}

	@Override
	public void getPropertyInfo(int index, Hashtable ht, PropertyInfo info) {
		super.getPropertyInfo(index, ht, info);
		info.type = PropertyInfo.OBJECT_CLASS;
	}

}
