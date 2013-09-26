package org.pub.pwdgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pub.pwdgen.adapter.AdapterData;
import org.pub.pwdgen.util.IntentsUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private static final String MENU_OPTION_PWIN = "Password for Windows";
	private static final String MENU_OPTION_PWEB = "Password for Website";
	private static final String MENU_OTHER_GM = "Open Google Market";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
		Object action = item.get("action");
		if(action == null) {
			Toast.makeText(view.getContext(), "Coming soon..", Toast.LENGTH_SHORT).show();
		} else {
			if(action instanceof Class<?>) {
				Class<?> clazz = (Class<?>) action;
				IntentsUtils.invokeActivity(MainActivity.this, clazz);
			} else if(action instanceof String) {
				String url = (String) action;
				IntentsUtils.invokeWebBrowser(MainActivity.this, url);
			}
		}
	}

	@Override
	protected String[] createSections() {
		String[] sections = new String[] { "Operation", "Others" };
		
		mAdapter.put(sections[0], passwordAD);
		mAdapter.put(sections[1], othersAD);
		
		return sections;
	}

	@Override
	protected int getAdapterResourceId() {
		return R.layout.menu_list_item;
	}

	@Override
	protected String[] getAdapterFromKey() {
		return new String[] { "title", "info" };
	}

	@Override
	protected int[] getAdapterToId() {
		return new int[] { R.id.item_title, R.id.item_info };
	}

	private AdapterData passwordAD = new AdapterData() {
		@Override
		public List<Map<String, Object>> createSectionItems() {
			List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", MENU_OPTION_PWIN);
			map.put("info", "Generate password for Windows account");
			map.put("action", PasswordWinActivity.class);
			items.add(map);
			
			map = new HashMap<String, Object>();
			map.put("title", MENU_OPTION_PWEB);
			map.put("info", "Generate password for Website account");
			map.put("action", null);
			items.add(map);
			
			return items;
		}
	};
	
	private AdapterData othersAD = new AdapterData() {
		@Override
		public List<Map<String, Object>> createSectionItems() {
			List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", MENU_OTHER_GM);
			map.put("info", "Find apps from Google Market");
			map.put("action", "https://www.google.com/enterprise/marketplace/?pli=1");
			items.add(map);
			
			return items;
		}
	};
	
}
