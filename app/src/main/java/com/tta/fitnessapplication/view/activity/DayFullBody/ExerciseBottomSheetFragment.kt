package com.tta.fitnessapplication.view.activity.DayFullBody

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.DialogBottomSheetInfoExerciseBinding

class ExerciseBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBottomSheetInfoExerciseBinding
    private lateinit var dialog : BottomSheetDialog
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
        binding = DialogBottomSheetInfoExerciseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        var layout : CoordinatorLayout? = dialog.findViewById(R.id.bottomSheetLayout)
        assert(layout!=null)
        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

    }
}