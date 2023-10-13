package jp.thinkdifferent.devsurface.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import jp.thinkdifferent.devsurface.data.storage.GameStorage
import jp.thinkdifferent.devsurface.domain.models.NetworkData
import jp.thinkdifferent.devsurface.domain.repository.AppsRepository
import jp.thinkdifferent.devsurface.domain.repository.FacebookRepository
import javax.inject.Inject

class ViewModelRelax @Inject constructor(
    private val gameStorage: GameStorage,
    private val appsRepo: AppsRepository,
    private val facebookRepo: FacebookRepository
) : ViewModel() {

    val liveDataFromNetwork: MutableLiveData<NetworkData?> = MutableLiveData(NetworkData(apps = null, auid = null, fb = null, gaid = null, bridge = null))

    fun printDataFromNetwork(context: Context){

        val bridge = facebookRepo.getBridge()

        putBridge(bridge)

        appsRepo.getAppsMap(context)

        viewModelScope.launch {
            facebookRepo.getFaceData()
            facebookRepo.getGaid()
            appsRepo.getAFUID(context)
        }
    }

    fun saveDestinationInToTheStorage(dest: String){
        gameStorage.writeDestination(dest)

    }

    fun readDestinationFromTheStorage(): String {
        return gameStorage.readDestination()

    }

    fun putApps(appsMap: MutableMap<String, Any>?){
        val newLiveData = liveDataFromNetwork.value?.copy(apps = appsMap)
        if (newLiveData != null){
            liveDataFromNetwork.value = newLiveData
        }
    }


    fun putAUID(auid: String){
        val newLiveData = liveDataFromNetwork.value?.copy(auid = auid)
        if (newLiveData != null){
            liveDataFromNetwork.value = newLiveData
        }
    }

    fun putFb(fB: String){
        val newLiveData = liveDataFromNetwork.value?.copy(fb = fB)
        if (newLiveData != null){
            liveDataFromNetwork.value = newLiveData
        }
    }

    fun putGaid(gaid: String){
        val newLiveData = liveDataFromNetwork.value?.copy(gaid = gaid)
        if (newLiveData != null){
            liveDataFromNetwork.value = newLiveData
        }
    }

    private fun putBridge(bridge: String){
        val newLiveData = liveDataFromNetwork.value?.copy(bridge = bridge)
        if (newLiveData != null){
            liveDataFromNetwork.value = newLiveData
        }
    }
}