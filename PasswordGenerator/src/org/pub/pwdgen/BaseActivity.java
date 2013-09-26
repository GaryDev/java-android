package org.pub.pwdgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pub.pwdgen.adapter.AdapterData;
import org.pub.pwdgen.adapter.SeparatedListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public abstract class BaseActivity extends Activity implements OnItemClickListener {
	
	private ListView itemListView;
	private SeparatedListAdapter adapter;

	protected Map<String, AdapterData> mAdapter = new HashMap<String, AdapterData>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String[] sections = this.createSections();
		Map<String, List<Map<String, Object>>> sectionItemMap = this.createSectionItemsMap();
		int resouceId = this.getAdapterResourceId();
		String[] from = this.getAdapterFromKey();
		int[] to = this.getAdapterToId();
		
		adapter = new SeparatedListAdapter(this);
		for (int i = 0; i < sections.length; i++) {
			String section = sections[i];
			List<Map<String, Object>> sectionItem = sectionItemMap.get(section);
			SimpleAdapter itemAdapter = new SimpleAdapter(this, sectionItem, resouceId, from, to);
			adapter.addSection(section, itemAdapter);
		}
		
		itemListView = (ListView) findViewById(R.id.item_list);
		itemListView.setAdapter(adapter);
		itemListView.setOnItemClickListener(this);
	}
	
	private Map<String, List<Map<String, Object>>> createSectionItemsMap() {
		Map<String, List<Map<String, Object>>> sectionItemMap = new HashMap<String, List<Map<String,Object>>>();
		String[] sections = this.createSections();
		for (int i = 0; i < sections.length; i++) {
			String section = sections[i];
			sectionItemMap.put(section, mAdapter.get(section).createSectionItems());
		}
		return sectionItemMap;
	}
	
	protected abstract String[] createSections();
	
	protected abstract int getAdapterResourceId();
	protected abstract String[] getAdapterFromKey();
	protected abstract int[] getAdapterToId();
}
