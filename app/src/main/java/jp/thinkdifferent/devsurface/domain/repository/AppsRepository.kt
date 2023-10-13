package jp.thinkdifferent.devsurface.domain.repository

import android.content.Context

interface AppsRepository {
    fun getAppsMap(context: Context)
    suspend fun getAFUID(context: Context)

}