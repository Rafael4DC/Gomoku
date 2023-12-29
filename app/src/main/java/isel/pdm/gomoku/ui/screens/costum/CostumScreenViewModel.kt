package isel.game.gomoku.ui.screens.costum

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.pdm.gomoku.domain.game.play.GameRules
import isel.pdm.gomoku.domain.game.play.OpenRule
import isel.pdm.gomoku.domain.game.play.Variant
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CostumViewModel : ViewModel() {

    var gameRules: GameRules by mutableStateOf(GameRules())

    fun changeVariant(variant: Variant) {
        viewModelScope.launch {
            gameRules = gameRules.copy(variant = variant)
        }
    }
    fun changeBoardSize(size: Int) {
        viewModelScope.launch {
            gameRules = gameRules.copy(boardSize = size)
        }
    }
    fun changeOpenRules(openRule: OpenRule) {
        viewModelScope.launch {
            gameRules = gameRules.copy(openRule = openRule)
        }
    }
    fun changeRoundDuration(duration: Int) {
        viewModelScope.launch {
            gameRules = gameRules.copy(secondsPerTurn = duration.toDuration(DurationUnit.SECONDS))
        }
    }
}