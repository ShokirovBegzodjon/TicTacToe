package com.apphub.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apphub.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.letsPlay.setOnClickListener {
            startActivity(Intent(this,SelectGameActivity::class.java))
        }
    }
    //https://www.figma.com/file/YLTSIcGrB3c1deeczmkVS2/Tic-Tac-Toe-(Community)-(Community)?type=design&node-id=2-3&mode=design&t=KxiOsQBk0fvk8DkU-0
}