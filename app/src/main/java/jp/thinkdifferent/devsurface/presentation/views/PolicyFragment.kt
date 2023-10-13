package jp.thinkdifferent.devsurface.presentation.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import jp.thinkdifferent.devsurface.App
import jp.thinkdifferent.devsurface.R
import jp.thinkdifferent.devsurface.RelaxConst
import jp.thinkdifferent.devsurface.databinding.FragmentPoliBinding
import jp.thinkdifferent.devsurface.domain.models.PickerDocuments
import jp.thinkdifferent.devsurface.presentation.customUI.PolicyCustomUI
import jp.thinkdifferent.devsurface.presentation.viewmodels.ViewModelRelax
import javax.inject.Inject


@AndroidEntryPoint
class PolicyFragment : Fragment() {

    @Inject
    lateinit var vm: ViewModelRelax

    private lateinit var binding: FragmentPoliBinding
    var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    private lateinit var policyView: PolicyCustomUI

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        binding = FragmentPoliBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        policyView = PolicyCustomUI(requireContext(), object : PickerDocuments {
            override fun onPickDoc(docs: ValueCallback<Array<Uri>>?) {
                chooseCallback = docs
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (policyView.canGoBack()) {
                        policyView.goBack()
                    }
                }
            })



        //Add listenners
        App.navToMenu.observe(viewLifecycleOwner) {
            if (it == RelaxConst.storage2[1]) {
                goToTheMenuScreen()
            }
        }

        //make Policy full screen

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        policyView.layoutParams = layoutParams


        binding.mainLayout.addView(policyView)
        policyView.startPolicyUI(getContent, vm)

        val base = RelaxConst.storage1[2]+RelaxConst.storage1[3]+RelaxConst.storage2[2]+RelaxConst.storage2[3]
        policyView.loadUrl(base)
    }


    private fun goToTheMenuScreen() {
        vm.saveDestinationInToTheStorage(RelaxConst.storage2[1])
        findNavController().navigate(PolicyFragmentDirections.actionPolicyFragmentToControlFragment())
    }
}