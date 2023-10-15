package com.tta.fitnessapplication.view.noti

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentManagerNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ManagerNotification : BaseFragment<FragmentManagerNotificationBinding>() {
    private lateinit var viewModel: NewNotificationViewModel
    private val adapter = ManagerNotificationAdapter()
    override fun getDataBinding(): FragmentManagerNotificationBinding {
        return FragmentManagerNotificationBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        with(binding) {
            recManagerNoti.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            recManagerNoti.layoutManager = linearLayoutManager
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvNodata.visibility = View.GONE
                adapter.setListNotification(it, requireContext())
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
                findNavController().navigate(R.id.action_managerNotification_to_newNotificationFragment)
            }
        }
    }
}