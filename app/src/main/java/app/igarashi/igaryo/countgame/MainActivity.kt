package app.igarashi.igaryo.countgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    var cnt = 0
    var second = 0.0
    var start = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = Timer().schedule(0, 10) {
            if (start) {
                second += 0.01
            }
        }


        pumpImage.setOnClickListener {
            if (!start) {
                second = 0.0
                start = true
            }
            cnt++
            if (cnt > 20) {
                balloonImage.setImageResource(R.drawable.balloon2)
                hintText.text = "空気が入ってるね"
            }
            if (cnt > 40) {
                balloonImage.setImageResource(R.drawable.balloon3)
                hintText.text = "空気がいっぱいだ！あとちょっと"
            }
            if (cnt > 60) {
                balloonImage.setImageResource(R.drawable.balloon4)
                hintText.text = "よくやった！" + (Math.round(second * 1000.0) / 1000.0).toString() + "秒かかったよ"
                pumpImage.isEnabled = false
                val rankingIntent: Intent = Intent(this, RankingActivity::class.java)
                timer.cancel()
                rankingIntent.putExtra("score", ((second * 1000.0).roundToInt() / 1000.0))
                startActivity(rankingIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        start = false
        second = 0.0
        cnt = 0
        balloonImage.setImageResource(R.drawable.balloon1)
        hintText.text = "空気入れをひたすらクリックしてね"
    }
}