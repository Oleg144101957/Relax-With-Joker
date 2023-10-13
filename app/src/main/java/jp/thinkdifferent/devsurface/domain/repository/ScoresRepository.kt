package jp.thinkdifferent.devsurface.domain.repository

interface ScoresRepository {
    fun readScores() : Int
    fun saveScores(scores: Int)
}