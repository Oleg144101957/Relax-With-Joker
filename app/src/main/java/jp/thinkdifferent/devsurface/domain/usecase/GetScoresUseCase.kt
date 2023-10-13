package jp.thinkdifferent.devsurface.domain.usecase

import jp.thinkdifferent.devsurface.domain.repository.ScoresRepository
import javax.inject.Inject

class GetScoresUseCase @Inject constructor(private val scoresRepository: ScoresRepository) {
    operator fun invoke() : Int{
        return scoresRepository.readScores()
    }
}