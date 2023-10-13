package jp.thinkdifferent.devsurface

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper
import jp.thinkdifferent.devsurface.domain.usecase.LaunchTimesIncreaserUseCase
import jp.thinkdifferent.devsurface.presentation.viewmodels.ViewModelRelax
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var launchTimesIncreaser: LaunchTimesIncreaserUseCase

    @Inject
    lateinit var vm: ViewModelRelax

    override fun onCreate() {
        super.onCreate()
        initStorage()
        countOpens()
        setSignals()

        val dataFromStorage = vm.readDestinationFromTheStorage()
        if (dataFromStorage == RelaxConst.storage1[1]){
            //First time case -- here, in App Class
            //addDataCollectors()
            //requestData()
        }
    }

    private fun addDataCollectors() {
        apps.observeForever {
            if (it != null){
                vm.putApps(it)
            } else {
                vm.putApps(null)
            }
        }

        auid.observeForever {
            if (it != null){
                vm.putAUID(it)
            } else {
                vm.putAUID("null")
            }
        }

        facebook.observeForever {
            if (it != null){
                vm.putFb(it)
            } else {
                vm.putFb("null")
            }
        }

        gaid.observeForever {
            if (it != null){
                vm.putGaid(it)
            } else {
                vm.putGaid("null")
            }
        }
    }


    private fun setSignals() {
        val oKey = RelaxConst.storage1[0] + RelaxConst.storage2[0]
        OneSignal.initWithContext(this)
        OneSignal.setAppId(oKey)
    }

    private fun initStorage() {
        Paper.init(this)
    }

    private fun countOpens() {
        launchTimesIncreaser()
    }

    private fun requestData(){
        vm.printDataFromNetwork(this)
    }

    companion object{
        val apps = MutableLiveData<MutableMap<String, Any>?>()
        val facebook = MutableLiveData<String>()
        val gaid = MutableLiveData<String>()
        val auid = MutableLiveData<String>()
        val navToMenu = MutableLiveData<String>()
        val buttonAccept = MutableLiveData<String>()

    }
}