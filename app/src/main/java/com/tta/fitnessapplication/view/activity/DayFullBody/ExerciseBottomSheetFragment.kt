package com.tta.fitnessapplication.view.activity.DayFullBody

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.databinding.DialogBottomSheetInfoExerciseBinding
import com.tta.fitnessapplication.view.fragment.dayfullbodyfragement.ImageFragment.Companion.image
import com.tta.fitnessapplication.view.fragment.dayfullbodyfragement.VideoFragment.Companion.urlExercise

class ExerciseBottomSheetFragment(exercise: Exercise) : BottomSheetDialogFragment() {
    private var _binding: DialogBottomSheetInfoExerciseBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapterPager: FragmentPageAdapter
    private var exercise = exercise
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomSheetInfoExerciseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        var layout: CoordinatorLayout? = dialog.findViewById(R.id.bottomSheetLayout)
        assert(layout != null)
        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        initUiPager()

        binding.view13.setOnClickListener {
            dialog.dismiss()
        }

        binding.textView23.text = exercise.name
        binding.tvDesc.text = exercise.title
        binding.tvFocusArea.text = exercise.area
        image = exercise.image
        urlExercise = exercise.name
        if (exercise.type == "0") {
            binding.tvTimeTodo.text = exercise.number + "s"
        } else {
            binding.tvTimeTodo.text = "x" + exercise.number
        }
    }

    fun initUiPager() {
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        adapterPager = FragmentPageAdapter(requireActivity().supportFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Image"))
        tabLayout.addTab(tabLayout.newTab().setText("Video"))

        viewPager2.adapter = adapterPager

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    interface OnDismissListener {
        fun onDialogDismissed()
    }

    private var dismissListener: OnDismissListener? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        dismissListener?.onDialogDismissed()
    }

    // Method to set the dismiss listener
    fun setDismissListener(listener: OnDismissListener) {
        dismissListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}