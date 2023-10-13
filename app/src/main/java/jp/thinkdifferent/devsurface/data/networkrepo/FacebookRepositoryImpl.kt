package jp.thinkdifferent.devsurface.data.networkrepo

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.facebook.applinks.AppLinkData
import com.facebook.internal.AttributionIdentifiers
import jp.thinkdifferent.devsurface.App
import jp.thinkdifferent.devsurface.domain.repository.FacebookRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FacebookRepositoryImpl(private val context: Context) : FacebookRepository {
    override suspend fun getFaceData() = suspendCoroutine{ continuation ->
        AppLinkData.fetchDeferredAppLinkData(context){
            continuation.resume(App.facebook.postValue(it?.targetUri.toString()))
        }
    }
    override suspend fun getGaid() = suspendCoroutine { continuation ->
        AppLinkData.fetchDeferredAppLinkData(context){
            val response = AttributionIdentifiers.getAttributionIdentifiers(context)?.androidAdvertiserId ?: "no_data"
            continuation.resume(App.gaid.postValue(response))
        }
    }
    override fun getBridge() : String {
        return Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
    }
}