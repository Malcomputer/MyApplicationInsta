package com.firechat.myapplicationtest.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

	public static final String[] PERMISSIONS = {
			Manifest.permission.WRITE_EXTERNAL_STORAGE, /* 0 */
			Manifest.permission.READ_EXTERNAL_STORAGE, /* 1 */
			Manifest.permission.CAMERA                /* 2 */
	};

	public static void checkPermissions(Context context, String permission) {

		if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
			grantPermission(context, permission);
		}
	}

	private static void grantPermission(Context context, String permission) {
		ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 1);
	}
}
