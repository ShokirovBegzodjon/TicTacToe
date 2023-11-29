package com.apphub.tictactoe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apphub.tictactoe.databinding.ActivitySelectGameBinding

private lateinit var binding: ActivitySelectGameBinding

class SelectGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.singlePlayer.setOnClickListener{   intentGame(0)  }
        binding.twoPlayer.setOnClickListener{   intentGame(1)  }
        binding.marketPlace.setOnClickListener{   market()  }
        binding.challengesPlace.setOnClickListener{  viewChannel()   }
    }
    private fun intentGame(tag: Int){
        startActivity(
            Intent(this,GameActivity::class.java).apply {
                putExtra("tag",tag)
            }
        )
    }
    private fun viewChannel(){
        startActivity(
            Intent(Intent.ACTION_VIEW,Uri.parse("https://t.me/my_app_project"))
        )
    }
    private fun market(){

    }
}