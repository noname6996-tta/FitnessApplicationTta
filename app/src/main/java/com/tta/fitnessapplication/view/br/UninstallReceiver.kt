package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tta.fitnessapplication.api.ApiService
import com.tta.fitnessapplication.data.model.BaseResponse
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import com.tta.fitnessapplication.view.activity.history.db.HistoryDatabase
import com.tta.fitnessapplication.view.activity.history.db.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UninstallReceiver : BroadcastReceiver() {
    var uninstallListener: UninstallListener? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_FULLY_REMOVED) {
            val packageName = intent.data?.encodedSchemeSpecificPart
            // Xử lý sự kiện gỡ ứng dụng ở đây
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận gỡ bỏ ứng dụng")
            builder.setMessage("Bạn có chắc chắn muốn gỡ bỏ ứng dụng không?")
            builder.setPositiveButton("Có") { dialog, which ->
                uninstallListener?.onUninstallConfirmed()
            }
            builder.setNegativeButton("Không", null)
            val dialog = builder.create()
            dialog.show()

        }
    }
}

interface UninstallListener {
    fun onUninstallConfirmed()
}