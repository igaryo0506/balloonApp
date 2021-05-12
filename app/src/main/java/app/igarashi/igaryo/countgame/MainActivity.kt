package app.igarashi.igaryo.countgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cnt = 0
        setContentView(R.layout.activity_main)
        var second = 0.0
        var start = false
        resetButton.isVisible = false

        val timer = Timer().schedule(0, 100, {
            if(start){
                second += 0.1
            }
            Log.v("nullpo",second.toString())
        })


        pumpImage.setOnClickListener{
            if(!start){
                second = 0.0
                start = true
            }
            cnt++
            if(cnt > 15){
                balloonImage.setImageResource(R.drawable.balloon2)
                hintText.text = "空気が入ってるね"
            }
            if(cnt > 30){
                balloonImage.setImageResource(R.drawable.balloon3)
                hintText.text = "空気がいっぱいだ！あとちょっと"
            }
            if(cnt > 45){
                balloonImage.setImageResource(R.drawable.balloon4)
                hintText.text = "よくやった！"+ (Math.round(second*10.0)/10.0).toString() + "秒かかったよ"
                pumpImage.isEnabled = false
                resetButton.isVisible = true
                val rankingIntent: Intent = Intent(this,RankingActivity::class.java)
                rankingIntent.putExtra("score", (Math.round(second*10.0)/10.0).toString())
                startActivity(rankingIntent)
            }
        }

        resetButton.setOnClickListener {
            start = false
            second = 0.0
            cnt = 0
            resetButton.isVisible = false
            balloonImage.setImageResource(R.drawable.balloon1)
            hintText.text = "空気入れをひたすらクリックしてね"
        }
    }
}