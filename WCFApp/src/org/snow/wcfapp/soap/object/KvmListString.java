package org.snow.wcfapp.soap.object;

import java.util.Hashtable;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

public class KvmListString extends KvmListBase<String> {

	public KvmListString(List<String> valueList, List<String> nameList) {
		super(valueList, nameList);
	}

	@Override
	public void getPropertyInfo(int index, Hashtable ht, PropertyInfo info) {
		super.getPropertyInfo(index, ht, info);
		info.type = PropertyInfo.STRING_CLASS;
	}

}
