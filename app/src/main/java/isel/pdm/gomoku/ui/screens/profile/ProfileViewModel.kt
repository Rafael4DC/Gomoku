package isel.pdm.gomoku.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.pdm.gomoku.domain.app.RankingService
import isel.pdm.gomoku.domain.game.app.Ranker
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var rankers: List<Ranker>? by mutableStateOf(null)
        private set

    fun fetchRankers(service: RankingService) {
        viewModelScope.launch {
            rankers = service.getRankers(3)
        }
    }
}