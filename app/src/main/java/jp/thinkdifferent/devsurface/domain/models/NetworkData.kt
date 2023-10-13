package jp.thinkdifferent.devsurface.domain.models

data class NetworkData(
    val apps: MutableMap<String, Any>?,
    val auid: String?,
    val fb: String?,
    val gaid: String?,
    val bridge: String?
)