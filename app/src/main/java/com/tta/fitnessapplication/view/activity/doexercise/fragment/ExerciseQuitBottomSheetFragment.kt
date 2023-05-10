package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.DialogBottomSheetPauseExerciseBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.ExerciseBottomSheetFragment
import com.tta.fitnessapplication.view.activity.doexercise.fragment.PrepareFragment.Companion.pause

class ExerciseQuitBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomSheetPauseExerciseBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBottomSheetPauseExerciseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        var layout: CoordinatorLayout? = dialog.findViewById(R.id.bottomQuitSheetLayout)
        assert(layout != null)
        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        binding.imgBack.setOnClickListener {
            dialog.dismiss()
            pause = false
        }

        binding.tvQuit.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            requireActivity().finish()
        }

        binding.tvShowInfo.setOnClickListener {
            var bottomSheetFragment = ExerciseBottomSheetFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager,bottomSheetFragment.tag)
        }
    }
}