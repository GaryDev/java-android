package org.pub.pwdgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pub.pwdgen.adapter.SeparatedListAdapter;
import org.pub.pwdgen.util.IntentsUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ListView optionListView;	
	private SeparatedListAdapter adapter;
	
	private static final String[] sections = new String[] { "Option", "Others" };
	
	private static final String MENU_OPTION_PWIN = "Password for Windows";
	private static final String MENU_OPTION_PWEB = "Password for Website";
	private static final String MENU_OTHER_GM = "Open Google Market";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Map<String, List<Map<String, Object>>> menu = this.getMenu();
		
		adapter = new SeparatedListAdapter(this);
		for (int i = 0; i < sections.length; i++) {
			SimpleAdapter itemAdapter = new SimpleAdapter(this, menu.get(sections[i]),
					R.layout.menu_list_item, new String[] { "title", "info" },
					new int[] { R.id.item_title, R.id.item_info });
			adapter.addSection(sections[i], itemAdapter);
		}
		
		optionListView = (ListView) findViewById(R.id.list_option);
		optionListView.setAdapter(adapter);
		optionListView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
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
		});
		
	}
	
	private Map<String, List<Map<String, Object>>> getMenu() {
		Map<String, List<Map<String, Object>>> menu = new HashMap<String, List<Map<String,Object>>>();
		for (int i = 0; i < sections.length; i++) {
			List<Map<String, Object>> menuItem = null;
			switch (i) {
			case 0:
				menuItem = getMenuOption();
				break;
			case 1:
				menuItem = getMenuOther();
				break;
			default:
				break;
			}
			menu.put(sections[i], menuItem);
		}
		return menu;
	}
	
	private List<Map<String, Object>> getMenuOption() {
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
	
	private List<Map<String, Object>> getMenuOther() {
		List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", MENU_OTHER_GM);
		map.put("info", "Find apps from Google Market");
		map.put("action", "https://www.google.com/enterprise/marketplace/?pli=1");
		items.add(map);
		
		return items;
	}

}
