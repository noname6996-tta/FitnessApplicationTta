package com.tta.fitnessapplication.view.noti

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.databinding.FragmentManagerNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ManagerNotification : BaseFragment<FragmentManagerNotificationBinding>() {
    private lateinit var viewModel: NewNotificationViewModel
    private val args: ManagerNotificationArgs by navArgs()
    var type = 0
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
        type = args.notiType
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
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
        }
    }
}