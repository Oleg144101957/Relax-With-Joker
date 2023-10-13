package jp.thinkdifferent.devsurface.presentation.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.thinkdifferent.devsurface.R
import jp.thinkdifferent.devsurface.databinding.FragmentEntertainmentBinding
import jp.thinkdifferent.devsurface.domain.usecase.SaveBestScoresUseCase
import javax.inject.Inject


@AndroidEntryPoint
class EntertainmentFragment : Fragment() {


    @Inject
    lateinit var saveBestScoresUseCase: SaveBestScoresUseCase

    private lateinit var binding: FragmentEntertainmentBinding
    private var heightA: Int = 0
    private var widthA: Int = 0
    private var score = MutableLiveData(0)

    private var secondsElapsed = 0
    private val timerHandler = Handler(Looper.getMainLooper())


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            secondsElapsed++
            binding.timer.text = "Timer: $secondsElapsed"

            if (secondsElapsed > 10) {
                gameOver()
            }

            timerHandler.postDelayed(this, 1000) // Schedule the next update after 1 second
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntertainmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        timerHandler.post(timerRunnable)

        widthA = displayMetrics.widthPixels
        heightA = displayMetrics.heightPixels
        binding.btnClose.setOnClickListener {
            navigateToTheMenu()
        }

        for (i in 0..5) {
            makeElement()
        }
        score.observe(viewLifecycleOwner) {
            binding.scoresCounter.text = "${getString(R.string.scores_text)}$it"
        }
    }

    private fun makeElement() {
        val item = ImageView(requireContext())
        binding.root.addView(item)
        item.apply {
            layoutParams.width = (100..300).random()
            layoutParams.height = (100..300).random()
            setImageResource(
                when ((1..4).random()) {
                    1 -> R.drawable.y
                    2 -> R.drawable.w
                    3 -> R.drawable.s
                    else -> R.drawable.k
                }
            )
            x = ((-95)..(widthA + 95)).random().toFloat()
            y = (heightA + 100).toFloat()
            animate().apply {
                duration = (900..2500).random().toLong()
                x(((150)..(widthA - 250)).random().toFloat())
                y(((150)..(heightA - 250)).random().toFloat())
            }
            setOnClickListener {
                onBonusClicked(this)
            }
        }
    }

    private fun onBonusClicked(imageView: ImageView) {
        score.value = score.value?.plus(1)
        binding.root.removeView(imageView)
        makeElement()
        score.value?.let { saveBestScoresUseCase(it) }
    }

    private fun navigateToTheMenu() {
        findNavController().navigate(EntertainmentFragmentDirections.actionEntertainmentFragmentToControlFragment())
    }

    private fun gameOver() {

        val frameLayout = FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
        }

        binding.root.addView(frameLayout)

        val gameOverTextView = TextView(requireContext()).apply {
            text = context.getString(R.string.game_over_code)
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        frameLayout.addView(gameOverTextView)
        val btnCloseSize = dpToPx(48)

        val imageViewBtnClose = ImageView(context).apply {
            setImageResource(R.drawable.baseline_arrow_back_24)

            layoutParams = ViewGroup.LayoutParams(btnCloseSize, btnCloseSize)

            setOnClickListener {
                navigateToTheMenu()
            }
        }

        frameLayout.addView(imageViewBtnClose)


    }

    private fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }
}