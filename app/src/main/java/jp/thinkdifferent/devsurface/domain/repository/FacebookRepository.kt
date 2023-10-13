package jp.thinkdifferent.devsurface.domain.repository

interface FacebookRepository {

    suspend fun getFaceData()
    suspend fun getGaid()
    fun getBridge() : String

}