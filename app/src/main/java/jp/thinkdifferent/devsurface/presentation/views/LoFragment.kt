package jp.thinkdifferent.devsurface.presentation.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.thinkdifferent.devsurface.RelaxConst
import jp.thinkdifferent.devsurface.databinding.FragmentLoaBinding
import jp.thinkdifferent.devsurface.presentation.viewmodels.ViewModelRelax
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoFragment : Fragment() {

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            //requestData()
        }

    private lateinit var binding: FragmentLoaBinding

    @Inject
    lateinit var viewModelRelax: ViewModelRelax


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoaBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //turn off back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //Do nothing
                }
            }
        )

        //check internet

        if (isInternetAvailable(requireContext())) {
            //requestPermission()
            lifecycleScope.launch {
                delay(2000)
                navigateToControlFragment()
            }

        } else {
            goToTheNoNetworkFragment()
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }


    private fun goToTheNoNetworkFragment() {
        findNavController().navigate(LoFragmentDirections.actionPrepFragmentToNoNetFragment())
    }

    private fun requestData() {
        val dataFromStorage = viewModelRelax.readDestinationFromTheStorage()
        if (dataFromStorage == RelaxConst.storage1[1]) {
            //First time -- loading Add listenner
            viewModelRelax.liveDataFromNetwork.observe(viewLifecycleOwner) {
                //observe First time data
                if (it?.apps != null && it.auid != null && it.fb != null && it.gaid != null && it.bridge != null) {
                    navigateToPolicyFragment()
                }
            }
        } else if (dataFromStorage == RelaxConst.storage2[1]) {
            //Have Attention case
            //Go to the menu -- loading screen
            navigateToControlFragment()
        } else if (dataFromStorage.startsWith(RelaxConst.storage1[2])) {
            //Have link
            //Go directly to the Policy -- paste to the Loading Screen
            navigateToPolicyFragment()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestPermission() {
        val permission = android.Manifest.permission.POST_NOTIFICATIONS
        requestPermission.launch(permission)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        )
    }

    private fun navigateToControlFragment() {
        lifecycleScope.launch {
            delay(1998)
            findNavController().navigate(LoFragmentDirections.actionPrepFragmentToControlFragment())
        }
    }

    private fun navigateToPolicyFragment() {
        lifecycleScope.launch {
            findNavController().navigate(LoFragmentDirections.actionPrepFragmentToPolicyFragment())
        }
    }
}