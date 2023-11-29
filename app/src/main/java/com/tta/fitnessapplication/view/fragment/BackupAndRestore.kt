package com.tta.fitnessapplication.view.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.position
import com.example.awesomedialog.title
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.databinding.FragmentBackupAndRestoreBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment

class BackupAndRestore : BaseFragment<FragmentBackupAndRestoreBinding>() {
    private lateinit var viewModel: HistoryViewModel
    private var emailUser = ""
    override fun getDataBinding(): FragmentBackupAndRestoreBinding {
        return FragmentBackupAndRestoreBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        mainViewModel.getBackUpFile(emailUser)
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.backUpFile.observe(viewLifecycleOwner){
            if (it.isSuccessful){
                if (it.body()?.message=="1"){
                    setView(true)
                    binding.tvBackUpTime.text = it.body()?.data
                    binding.viewCreateBackUp.visibility = View.GONE
                    binding.textView38.visibility = View.GONE

                    binding.viewDeleteBackUp.visibility = View.VISIBLE
                    binding.textView39.visibility = View.VISIBLE

                    binding.viewBackUp.visibility = View.VISIBLE
                    binding.textView49.visibility = View.VISIBLE
                } else {
                    setView(false)
                    binding.viewCreateBackUp.visibility = View.VISIBLE
                    binding.textView38.visibility = View.VISIBLE

                    binding.viewDeleteBackUp.visibility = View.GONE
                    binding.textView39.visibility = View.GONE

                    binding.viewBackUp.visibility = View.GONE
                    binding.textView49.visibility = View.GONE
                }
            }
        }
        mainViewModel.deleteHistory.observe(viewLifecycleOwner){
            hideLoading()
            if (it.isSuccessful){
                mainViewModel.getBackUpFile(emailUser)
                mainViewModel.updateUserBackUp(emailUser,"")
            }
        }
        mainViewModel.downloadHistory.observe(viewLifecycleOwner) { list ->
            hideLoading()
            var sz = 0
            if (list.isSuccessful && !list.body()?.data.isNullOrEmpty()) {
                for (item in list.body()?.data!!) {
                    viewModel.addHistory(item)
                    sz ++
                }
            }
            if (sz >= list.body()?.data!!.size){
                Toast.makeText(requireContext(), "Download Success", Toast.LENGTH_SHORT).show()
            }
        }
        mainViewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            Log.e("tteeee",it.toString())
        }
        mainViewModel.userBackUpFile.observe(viewLifecycleOwner){
            if (it.isSuccessful){
                if (it.body()?.response==1){

                }
            }
        }
    }

    private fun setView(b: Boolean) {
        if (!b){
            // không có dữ liệu sao lưu
            binding.viewDeleteBackUp.setOnClickListener {
                Toast.makeText(requireContext(),"You don't have any backed up data at all",Toast.LENGTH_SHORT).show()
            }
            binding.viewCreateBackUp.setOnClickListener {
                val deviceName = android.os.Build.MODEL
                var date = Constant.DATE.fullDateFormatter.format(Constant.DATE.today)
                AwesomeDialog.build(requireActivity())
                    .title("Notification")
                    .body("Do you want to make a backup? (You can back up data on another device)")
                    .onPositive("Yes") {
                        showLoading()
                        viewModel.readAllData.observe(viewLifecycleOwner) {
                            if (it.size>0){
                                var i = 0
                                for (item in it) {
                                    mainViewModel.uploadHistoryUser(
                                        item.id_user.toString(),
                                        item.date.toString(),
                                        item.time.toString(),
                                        item.activity.toString(),
                                        item.type.toString(),
                                        item.value.toString()
                                    )
                                    i++
                                }
                                mainViewModel.updateUserBackUp(emailUser,"$deviceName - $date")
                                if (i>=it.size){
                                    hideLoading()
                                    Toast.makeText(requireContext(),"Upload Success",Toast.LENGTH_SHORT).show()
                                    mainViewModel.getBackUpFile(emailUser)
                                }
                            }
                            else {
                                hideLoading()
                                Toast.makeText(requireContext(),"No History",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .onNegative("No")
                    .position(AwesomeDialog.POSITIONS.CENTER)
            }
        } else {
            binding.viewCreateBackUp.setOnClickListener {
                Toast.makeText(requireContext(),"You already have backup data, if you want to create a new one, delete the old one",Toast.LENGTH_SHORT).show()
            }
            binding.viewDeleteBackUp.setOnClickListener {
                AwesomeDialog.build(requireActivity())
                    .title("Notification")
                    .body("Do you want delete")
                    .onPositive("Yes") {
                        showLoading()
                        mainViewModel.deleteHistory(idUser.toInt())
                    }
                    .onNegative("No")
                    .position(AwesomeDialog.POSITIONS.CENTER)
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        var autoBackUp = loginPreferences.getBoolean(Constant.PREF.AUTO_BACKUP, false)
        binding.apply {
            viewBack.setOnClickListener {
                findNavController().popBackStack()
            }
            switch1.isChecked = autoBackUp
            switch1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // Xử lý khi Switch được bật
                    loginPrefsEditor.putBoolean(Constant.PREF.AUTO_BACKUP, isChecked)
                    loginPrefsEditor.commit()
                } else {
                    // Xử lý khi Switch được tắt
                    loginPrefsEditor.putBoolean(Constant.PREF.AUTO_BACKUP, isChecked)
                    loginPrefsEditor.commit()
                }
            }

            viewBackUp.setOnClickListener {
                showLoading()
                mainViewModel.getHistoryById(idUser)
            }
        }
    }
}