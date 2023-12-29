package isel.game.gomoku.ui.screens.about.components

import android.net.Uri
import androidx.annotation.DrawableRes
import isel.pdm.gomoku.R

data class AuthorInfo(
    val name:String,
    @DrawableRes val imageId: Int,
    val email: String,
    val git: Uri
){
    companion object{
        val authorInfo = listOf(
            AuthorInfo(
                name = "Rafael Costa",
                email = "A48315@alunos.isel.pt",
                imageId = R.drawable.ic_author,
                git = Uri.parse("https://github.com/Rafael4DC")
            ),
            AuthorInfo(
                name = "Bernardo Serra",
                email = "A48315@alunos.isel.pt",
                imageId = R.drawable.ic_armario,
                git = Uri.parse("https://github.com/bfmserra")
            )
        )
    }
}