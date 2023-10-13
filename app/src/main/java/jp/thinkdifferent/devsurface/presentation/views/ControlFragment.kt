package jp.thinkdifferent.devsurface.presentation.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.thinkdifferent.devsurface.databinding.FragmentMenuBinding
import jp.thinkdifferent.devsurface.domain.usecase.GetScoresUseCase
import javax.inject.Inject


@AndroidEntryPoint
class ControlFragment : Fragment() {

    @Inject
    lateinit var getScoresUseCase: GetScoresUseCase

    private lateinit var binding: FragmentMenuBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scores = getScoresUseCase()

        //turn off back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //Do nothing
                }
            }
        )


        binding.btnLeave.setOnClickListener {
            requireActivity().finishAffinity()
        }

        binding.btnEntertainment.setOnClickListener {
            findNavController().navigate(ControlFragmentDirections.actionControlFragmentToEntertainmentFragment())
        }

        binding.bestScores.text = "Best scores is $scores"

    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

}