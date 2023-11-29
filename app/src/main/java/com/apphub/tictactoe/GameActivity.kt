package com.apphub.tictactoe

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.apphub.tictactoe.databinding.ActivityGameBinding


private lateinit var binding: ActivityGameBinding
private var tag = -1
private var isgaming = true
private var person = 1
private var person1_win = 0
private var person2_win = 0
private var firstClickAl = true

private var list:MutableList<Int> = arrayListOf()
private var person1_icon = R.drawable.x1
private var person2_icon = R.drawable.o1
private lateinit var imgList:List<ImageView>
private var winList = arrayOf(
    arrayOf(0,1,2),
    arrayOf(3,4,5),
    arrayOf(6,7,8),

    arrayOf(0,3,6),
    arrayOf(1,4,7),
    arrayOf(2,5,8),

    arrayOf(0,4,8),
    arrayOf(2,4,6),

)
private lateinit var my_animation:Animation
class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        my_animation = AnimationUtils.loadAnimation(this, R.anim.my_animation)
        binding.restart.setOnClickListener { restartGame() }
        binding.exit.setOnClickListener { finish() }
        imageLaod()
        restartGame()
        setName()
    }

    private fun imageLaod(){
        imgList = listOf(
            binding.img0,
            binding.img1,
            binding.img2,
            binding.img3,
            binding.img4,
            binding.img5,
            binding.img6,
            binding.img7,
            binding.img8
        )
    }
    private fun resetGame() {
        person = (1..2).random()
         if ((0..1).random() == 1){
            person1_icon = R.drawable.o1
            person2_icon = R.drawable.x1
        }else{
             person1_icon = R.drawable.x1
             person2_icon = R.drawable.o1
        }
        binding.person1Icon.setImageResource(person1_icon)
        binding.person2Icon.setImageResource(person2_icon)
        alpha(person)
        list.clear()
        for (i in 0..8) {
            list.add(-1)
        }

        isgaming = true
        for (i in 0 until binding.gridlayout.childCount) {
            imgList[i].setImageResource(0)
            (binding.gridlayout.getChildAt(i) as CardView).setCardBackgroundColor(getColor(R.color.dark_white))
        }
        firstClickAl = true
        alClick()
    }

    private fun restartGame() {
        person1_win = 0
        person2_icon = 0
        binding.person1Win.text = "0"
        binding.person2Win.text = "0"
        resetGame()
    }

    private fun setName(){
        tag = intent.getIntExtra("tag", -1)
        when (tag) {
            0 -> {
                binding.person1Name.text = "Person"
                binding.person2Name.text = "Robot"
            }
            1 -> {
                binding.person1Name.text = "Person One"
                binding.person2Name.text = "Person Two"
        }

        }
    }

    fun onClickImage(view: View) {
        val cardView = view as CardView
        val clickTag = cardView.tag.toString().toInt()
        if (isgaming && list[clickTag] == -1 && (tag == 1 || (tag == 0 && person == 1))) {
            list[clickTag] = person
            isgaming = false
            person = if (person == 1) {
                imgList[clickTag].setImageResource(person1_icon)
                imgList[clickTag].animation = my_animation
                2
            } else {
                imgList[clickTag].setImageResource(person2_icon)
                imgList[clickTag].animation = my_animation
                1
            }
            Handler(Looper.getMainLooper()).postDelayed({
                isgaming = true
                winGame()
            },500)
        }
    }

    private fun winGame(){
        for (lists in winList) {
            if (list[lists[0]] == list[lists[1]] &&
                list[lists[1]] == list[lists[2]] &&
                list[lists[0]] != -1
            ) {
                if (list[lists[0]] == 1) {
                    finishGame(1, lists)
                } else {
                    finishGame(2, lists)
                }
                isgaming = false
            }
        }
        if (isgaming) {
            var thereA1 = false
            for (i in 0 until list.size) {
                if (list[i] == -1)
                    thereA1 = true
            }
            if (!thereA1) {
                finishGame(0, arrayOf(-1, -1))
                isgaming = false
            }
        }
        alpha(person)
        alClick()
    }

    private fun alClick() {
        if (person == 2 && tag == 0 && isgaming) {
            var i: Int = -1
            if (firstClickAl) {
                i = random08()
                firstClickAl = false
            } else {
                for (j in 1..2) {
                    i = searchSpace(j)
                    if (i != -1){
                        break
                    }
                }
                if (i == -1) {
                    i = random08()
                }

            }
            Log.d("i = ", i.toString())
            Handler(Looper.getMainLooper()).postDelayed({
                list[i] = 2
                person = 1
                    imgList[i].setImageResource(person2_icon)
                    imgList[i].animation = my_animation

                winGame()
            }, 1500)
        }
    }
    private fun random08(): Int {
        var i: Int
        while (true) {
            i = (0..8).random()
            Log.d("random i = ", i.toString())
            if (list[i] == -1) {
                break
            }
        }
        return i
    }
    private fun searchSpace( j: Int): Int{
        var i = -1
        for (oneList in winList) {
            Log.d("list ", oneList.toIntArray().toString())
            if (
                (list[oneList[0]] == j && list[oneList[1]] == j && list[oneList[2]] == -1)
            ) {
                i = oneList[2]
                break
            } else if (
                (list[oneList[0]] == j && list[oneList[1]] == -1 && list[oneList[2]] == j)
            ) {
                i = oneList[1]
                break
            } else if (
                (list[oneList[0]] == -1 && list[oneList[1]] == j && list[oneList[2]] == j)
            ) {
                i = oneList[0]
                break
            }
        }
        if (j == 1 && i == -1) {
            for (oneList in winList) {
                if (
                    (list[oneList[0]] == 2 && list[oneList[1]] == -1 && list[oneList[2]] == -1)
                ) {
                    i = (oneList[1]..oneList[2]).random()
                    break
                } else if (
                    (list[oneList[0]] == -1 && list[oneList[1]] == 2 && list[oneList[2]] == -1)
                ) {
                    i = (oneList[0]..oneList[2]).random()
                    break
                } else if (
                    (list[oneList[0]] == -1 && list[oneList[1]] == -1 && list[oneList[2]] == 2)
                ) {
                    i = (oneList[0]..oneList[1]).random()
                    break
                }
            }
        }
        return i
    }

    private fun finishGame(tag: Int,list:Array<Int>){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.finish_game_activity,null)
        builder.setView(view)
        val win: TextView = view.findViewById(R.id.win)
        val winIcon: ImageView = view.findViewById(R.id.winicon)
        val playAgain:CardView = view.findViewById(R.id.play_again)
        val blackButton:CardView = view.findViewById(R.id.back)
        blackButton.setOnClickListener {
            finish()
        }
        val dialog: Dialog = builder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.show()
        },700)

        playAgain.setOnClickListener {
            resetGame()
            dialog.dismiss()
        }

        val tagIntent = intent.getIntExtra("tag", -1)

        when(tag) {
            1 -> {
                if (tagIntent == 0){
                    win.text = "You Win!"
                    win.setTextColor(getColor(R.color.orange))
                    winIcon.setImageResource(R.drawable.win_icon)

                }else{
                    win.text = "Person One Win!"
                    win.setTextColor(getColor(R.color.orange))
                    winIcon.setImageResource(R.drawable.win_icon)
                }
                ++person1_win
                binding.person1Win.text = "$person1_win"
                playAgain.setCardBackgroundColor(getColor(R.color.orange))
                blackButton.setCardBackgroundColor(getColor(R.color.orange))
            }
            2 -> {
                if (tagIntent == 0){
                    win.text = "You Lose!"
                    win.setTextColor(getColor(R.color.light_red))
                    winIcon.setImageResource(R.drawable.lost_icon)
                    playAgain.setCardBackgroundColor(getColor(R.color.light_red))
                    blackButton.setCardBackgroundColor(getColor(R.color.light_red))
                }else{
                    win.text = "Person Two Win!"
                    win.setTextColor(getColor(R.color.orange))
                    winIcon.setImageResource(R.drawable.win_icon)
                    playAgain.setCardBackgroundColor(getColor(R.color.orange))
                    blackButton.setCardBackgroundColor(getColor(R.color.orange))
                }
                ++person2_win
                binding.person2Win.text = "$person2_win"
            }
            0 ->{
                win.text = "Draw"
                winIcon.setImageResource(R.drawable.draw_icon)
                win.setTextColor(getColor(R.color.white))
                playAgain.setCardBackgroundColor(getColor(R.color.dark_green))
                blackButton.setCardBackgroundColor(getColor(R.color.dark_green))
            }
        }
        for (i in 0 until binding.gridlayout.childCount) {
            val cardView = binding.gridlayout.getChildAt(i) as CardView
            val index = (cardView).tag.toString().toInt()
            for (element in list)
                if (element == index) {
                    cardView.setCardBackgroundColor(getColor(R.color.light_green))
                }
        }

    }

    private fun alpha(person: Int){
        if (person == 1){
            binding.person1.alpha = 1f
            binding.person2.alpha = 0.5f
        }else{
            binding.person1.alpha = 0.5f
            binding.person2.alpha = 1f
        }
    }
}