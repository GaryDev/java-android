package org.pub.pwdgen.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtils {

	public static void invokeWebBrowser(Activity activity, String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		activity.startActivity(intent);
	}
	
	public static void invokeActivity(Activity activity, Class<?> clazz) {
		Intent intent = new Intent(activity, clazz);
		activity.startActivity(intent);
	}
	
}
