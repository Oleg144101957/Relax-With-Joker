package jp.thinkdifferent.devsurface.data.networkrepo

import android.content.Context
import com.appsflyer.AppsFlyerLib
import jp.thinkdifferent.devsurface.App
import jp.thinkdifferent.devsurface.domain.repository.AppsRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsRepositoryImpl() : AppsRepository {

    private val devkey = "Nnhs9fU3Thh3geocAqGm3Z"
    override fun getAppsMap(context: Context){
        val listenner = DataListennerImpl()
        AppsFlyerLib.getInstance().init(devkey, listenner, context).start(context)
    }

    override suspend fun getAFUID(context: Context) = suspendCoroutine{ cont ->
        val result = AppsFlyerLib.getInstance().getAppsFlyerUID(context).toString()
        cont.resume(App.auid.postValue(result))
    }
}