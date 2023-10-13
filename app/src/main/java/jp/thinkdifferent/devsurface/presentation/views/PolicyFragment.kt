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


        App.buttonAccept.observe(viewLifecycleOwner) {
            if (it == RelaxConst.storage1[4]) {
                addAcceptButton()
            }
        }


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


        val dataFromStorage = vm.readDestinationFromTheStorage()
        if (dataFromStorage == RelaxConst.storage1[1]) {
            //First time
            //Raw data from NETWORK - First time
            val dataFromNetwork = vm.liveDataFromNetwork.value
            val mapOfHeaders = mutableMapOf<String, String>()


            //secure_get_parametr + "secure_key"
            mapOfHeaders.put("yg6efp86br", "45a7bpl9zu")


            //set 1 BEFORE RELEASE
            if (dataFromNetwork?.bridge == "1") {
                //adb key
                mapOfHeaders.put("j21e18l0ug", "bfsjhbcsdjhbhjdfsjdfjhghj")
                addAcceptButton()
            }


            //gadid_key
            if (dataFromNetwork != null) {
                dataFromNetwork.gaid?.let {
                    mapOfHeaders.put("o3qli6irrb", it)
                    OneSignal.setExternalUserId(it)
                }
            }

            //deeplink_key
            if (dataFromNetwork != null) {
                dataFromNetwork.fb?.let { mapOfHeaders.put("e4i8km2jkt", it) }
            }


            //apps mutable map
            val apps = dataFromNetwork?.apps

            //<string name="source_key">rd6nxmx7jl</string>
            if (apps != null && apps["media_source"] != null) {
                mapOfHeaders.put("rd6nxmx7jl", apps["media_source"].toString())
            }

            //<string name="af_id_key">qitqxy09sq</string>
            if (dataFromNetwork?.auid != null) {
                mapOfHeaders.put("qitqxy09sq", dataFromNetwork.auid)
            }

            //<string name="adgroup_key">whjhjwt00l</string>
            if (apps != null && apps["adgroup"] != null) {
                mapOfHeaders.put("whjhjwt00l", apps["adgroup"].toString())
            }

            //<string name="orig_cost_key">0klsfhe3xx</string>
            if (apps != null && apps["orig_cost"] != null) {
                mapOfHeaders.put("0klsfhe3xx", apps["orig_cost"].toString())
            }

            //<string name="af_siteid_key">9m3ri52ha7</string>
            if (apps != null && apps["af_siteid"] != null) {
                mapOfHeaders.put("9m3ri52ha7", apps["af_siteid"].toString())
            }

            //<string name="app_campaign_key">0g1xymvbg9</string>
            if (apps != null && apps["campaign"] != null) {
                mapOfHeaders.put("0g1xymvbg9", apps["campaign"].toString())
            }

            //<string name="adset_key">ln80va8z88</string>
            if (apps != null && apps["adset"] != null) {
                mapOfHeaders.put("ln80va8z88", apps["adset"].toString())
            }

            //<string name="adset_id_key">wlvitqzb3e</string>
            if (apps != null && apps["adset_id"] != null) {
                mapOfHeaders.put("wlvitqzb3e", apps["adset_id"].toString())
            }

            //<string name="campaign_id_key">rtpxcszjvm</string>
            if (apps != null && apps["campaign_id"] != null) {
                mapOfHeaders.put("rtpxcszjvm", apps["campaign_id"].toString())
            }

            val base = RelaxConst.storage1[2]+RelaxConst.storage1[3]+RelaxConst.storage2[2]+RelaxConst.storage2[3]

            policyView.loadUrl(base, mapOfHeaders)

        } else if (dataFromStorage.startsWith(RelaxConst.storage1[2])) {
            policyView.loadUrl(dataFromStorage)
        } else {
            goToTheMenuScreen()
        }

    }

    private fun addAcceptButton() {
        //add button to navigate to menu
        val buttonAccept = Button(requireContext())
        buttonAccept.text = getString(R.string.accept)

        val buttonParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,   // Width
            FrameLayout.LayoutParams.WRAP_CONTENT    // Height
        )

        // Center the button in the FrameLayout
        buttonParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM

        buttonAccept.layoutParams = buttonParams

        buttonAccept.setOnClickListener {
            goToTheMenuScreen()
        }

        binding.mainLayout.addView(buttonAccept)
    }

    private fun goToTheMenuScreen() {
        //put WARNING to the Room
        vm.saveDestinationInToTheStorage(RelaxConst.storage2[1])
        findNavController().navigate(PolicyFragmentDirections.actionPolicyFragmentToControlFragment())
    }
}