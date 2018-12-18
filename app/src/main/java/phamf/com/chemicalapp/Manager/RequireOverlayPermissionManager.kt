package phamf.com.chemicalapp.Manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

class RequireOverlayPermissionManager(internal var activity: Activity) {

    internal var context: Context

    init {
        this.context = activity.baseContext
    }

    fun requirePermission(request_code: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.packageName))
            activity.startActivityForResult(intent, request_code)
        }
    }

}
