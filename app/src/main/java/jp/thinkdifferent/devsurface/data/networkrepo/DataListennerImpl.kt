package jp.thinkdifferent.devsurface.data.networkrepo

import android.util.Log
import jp.thinkdifferent.devsurface.App
import jp.thinkdifferent.devsurface.domain.repository.DataListenner

class DataListennerImpl() : DataListenner {
    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        App.apps.postValue(p0)
    }

    override fun onConversionDataFail(p0: String?) {
        val emptyMutableMap: MutableMap<String, Any> = mutableMapOf()
        App.apps.postValue(emptyMutableMap)
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
        val emptyMutableMap: MutableMap<String, Any> = mutableMapOf()
        App.apps.postValue(emptyMutableMap)
    }

    override fun onAttributionFailure(p0: String?) {
        val emptyMutableMap: MutableMap<String, Any> = mutableMapOf()
        App.apps.postValue(emptyMutableMap)
    }

    override fun onSomethingGoesWrong(p0: String?) {
        val emptyMutableMap: MutableMap<String, Any> = mutableMapOf()
        App.apps.postValue(emptyMutableMap)
    }
}