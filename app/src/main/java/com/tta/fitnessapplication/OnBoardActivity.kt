package com.tta.fitnessapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.tta.fitnessapplication.databinding.ActivityOnBoardBinding
import com.tta.fitnessapplication.view.activity.signup.SignUpActivity

class OnBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addEvent()
    }

    private fun addEvent() {
        binding.progessChangeFrame.setOnClickListener {
            var process = binding.progessChangeFrame.progress
            process += 25
            binding.progessChangeFrame.progress = process
            changeFrame(process)
        }
    }

    private fun changeFrame(i: Int) {
        val animation_fadein: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val animation_slidein: Animation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        when (i) {
            50 -> {
                binding.imgFrame.setImageResource(R.drawable.img_frame_two)
                binding.tvTitleFrame.text = "Get Burn"
                binding.tvDesFrame.text =
                    "Let’s keep burning, to achive yours goals, it hurts only temporarily, if you give up now you will be in pain forever"
                binding.imgFrame.startAnimation(animation_fadein)
                binding.tvTitleFrame.startAnimation(animation_slidein)
                binding.tvDesFrame.startAnimation(animation_slidein)

            }
            75 -> {
                binding.imgFrame.setImageResource(R.drawable.img_frame_three)
                binding.tvTitleFrame.text = "Eat Well"
                binding.tvDesFrame.text =
                    "Let's start a healthy lifestyle with us, we can determine your diet every day. healthy eating is fun"
                binding.imgFrame.startAnimation(animation_fadein)
                binding.tvTitleFrame.startAnimation(animation_slidein)
                binding.tvDesFrame.startAnimation(animation_slidein)
            }
            100 -> {
                binding.imgFrame.setImageResource(R.drawable.img_frame_four)
                binding.tvTitleFrame.text = "Improve Sleep  Quality"
                binding.tvDesFrame.text =
                    "Improve the quality of your sleep with us, good quality sleep can bring a good mood in the morning"
                binding.imgFrame.startAnimation(animation_fadein)
                binding.tvTitleFrame.startAnimation(animation_slidein)
                binding.tvDesFrame.startAnimation(animation_slidein)
            }
            125 -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}