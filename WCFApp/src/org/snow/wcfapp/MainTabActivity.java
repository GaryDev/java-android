package org.snow.wcfapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements
		OnCheckedChangeListener {

	private RadioGroup mainTab;
	private TabHost mTabHost;
	private TextView mUserName;

	private Intent mHomeIntent;
	private Intent mNewsIntent;
	private Intent mInfoIntent;
	private Intent mSearchIntent;
	private Intent mMoreIntent;

	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_NEWS = "tab_tag_news";
	private final static String TAB_TAG_INFO = "tab_tag_info";
	private final static String TAB_TAG_SEARCH = "tab_tag_search";
	private final static String TAB_TAG_MORE = "tab_tag_more";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.menu_bottom);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_custom);
		
		mUserName = (TextView) this.findViewById(R.id.header_text);
		String userName = getIntent().getExtras().getString("StaffName");
		mUserName.setText(userName);
		
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);

		prepareIntent();
		setupIntent();
		
		((RadioButton)findViewById(R.id.radio_button0)).setChecked(true);
	}

	private void prepareIntent() {
		mHomeIntent = new Intent(this, HomeActivity.class);
		mNewsIntent = new Intent(this, NewsActivity.class);
		mInfoIntent = new Intent(this, InfoActivity.class);
		mSearchIntent = new Intent(this, SearchActivity.class);
		mMoreIntent = new Intent(this, MoreActivity.class);
	}

	/**
	 * 
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.menu_home,
				R.drawable.icon_1_n, mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_NEWS, R.string.menu_news,
				R.drawable.icon_2_n, mNewsIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_INFO, R.string.menu_info,
				R.drawable.icon_3_n, mInfoIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_SEARCH, R.string.menu_search,
				R.drawable.icon_4_n, mSearchIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_MORE, R.string.menu_more,
				R.drawable.icon_5_n, mMoreIntent));
	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button0:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_NEWS);
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_INFO);
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_SEARCH);
			break;
		case R.id.radio_button4:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_MORE);
			break;
		}
	}

}
