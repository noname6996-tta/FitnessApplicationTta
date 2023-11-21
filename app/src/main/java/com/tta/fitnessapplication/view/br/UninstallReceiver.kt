package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UninstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_REMOVED) {
            val packageName = intent.data?.encodedSchemeSpecificPart
            // Xử lý sự kiện gỡ ứng dụng ở đây
        }
    }
}
