package com.tta.fitnessapplication.view.noti

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.databinding.FragmentManagerNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ManagerNotification : BaseFragment<FragmentManagerNotificationBinding>() {
    private val PERMISSION_REQUEST_CODE = 200
    private lateinit var viewModel: NewNotificationViewModel
    private val args: ManagerNotificationArgs by navArgs()
    var type = 0
    private val adapter = ManagerNotificationAdapter()
    override fun getDataBinding(): FragmentManagerNotificationBinding {
        return FragmentManagerNotificationBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        requestNotificationPermission()
        with(binding) {
            recManagerNoti.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            recManagerNoti.layoutManager = linearLayoutManager
        }
        val notificationManager = NotificationManagerCompat.from(requireContext())
        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()

        if (!areNotificationsEnabled){
            val settingsIntent = Intent()
            settingsIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            settingsIntent.putExtra("app_package", requireContext().packageName)
            settingsIntent.putExtra("app_uid", requireContext().applicationInfo.uid)
            requireContext().startActivity(settingsIntent)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        type = args.notiType
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val list = ArrayList<Notification>()
                if (type == 0) {
                    list.addAll(it)
                } else {
                    for (item in it) {
                        if (item.type == type) {
                            list.add(item)
                        }
                    }
                }
                binding.tvNodata.visibility = View.GONE
                adapter.setListNotification(list, requireContext())
            } else {
                binding.tvNodata.visibility = View.VISIBLE
                binding.recManagerNoti.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val list = ArrayList<Notification>()
                if (type == 0) {
                    list.addAll(it)
                } else {
                    for (item in it) {
                        if (item.type == type) {
                            list.add(item)
                        }
                    }
                }
                binding.tvNodata.visibility = View.GONE
                adapter.setListNotification(list, requireContext())
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        with(binding) {
            viewBack.setOnClickListener {
                findNavController().popBackStack()
            }
            addButton.setOnClickListener {
                val action =
                    ManagerNotificationDirections.actionManagerNotificationToNewNotificationFragment()
                findNavController().navigate(action)
            }
            adapter.deleteNotification {
//                AlertDialog.Builder(requireContext())
//                    .setTitle("Notification")
//                    .setMessage("Do you want delete this notification ?")
//                    .setPositiveButton("Yes",
//                        DialogInterface.OnClickListener { _, _ ->
//                            viewModel.deleteNotification(it)
//                        })
//                    .setNegativeButton("No", null)
//                    .show()
                val action = ManagerNotificationDirections.actionManagerNotificationToUpdateNotification(it)
                findNavController().navigate(action)
            }
            adapter.editNotification { notification, isChecked ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Notification")
                    .setMessage("Do you want change status this notification ?")
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { _, _ ->
                            notification.enable = isChecked
                            viewModel.updateNotification(notification)
                        })
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền notification được cấp phép, thực hiện các hành động cần thiết ở đây
            } else {
                // Quyền notification không được cấp phép, xử lý theo logic của ứng dụng
                showPermissionDeniedAlert()
            }
        }
    }

    private fun showPermissionDeniedAlert() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Permissions Required")
            setMessage("Some features of the app require location and notification access. Please grant the necessary permissions to continue.")
            setPositiveButton("Go to Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                settingsIntent.data = uri
                startActivity(settingsIntent)
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                // Handle app behavior when permission is denied
            }
            setCancelable(false)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}