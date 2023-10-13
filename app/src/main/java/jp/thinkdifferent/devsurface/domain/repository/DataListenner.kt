package jp.thinkdifferent.devsurface.domain.repository

import com.appsflyer.AppsFlyerConversionListener

interface DataListenner : AppsFlyerConversionListener {

    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?)

    override fun onConversionDataFail(p0: String?)

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?)

    override fun onAttributionFailure(p0: String?)

    fun onSomethingGoesWrong(p0: String?)

}