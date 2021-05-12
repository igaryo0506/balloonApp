package app.igarashi.igaryo.countgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_ranking.*
import org.w3c.dom.Text

class RankingActivity : AppCompatActivity() {
    var realm:Realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val score = intent.getDoubleExtra("score",0.0)
        resultText.text = score.toString() + "秒"

        var x: Int = 0;
        save(score)

        val scores:Array<Score?>? = read()?.toTypedArray() ?: null
        scores?.sortBy{
            it?.second ?: 0.0
        }

        if(scores != null){
            for(s in scores) {
                Log.d("text",x.toString())
                x++
                if (s != null) {
                    Log.d("debag", x.toString()+ " " + s.second.toString())
                    add(x,s.second.toString())
                } else {
                    break
                }
                if(x==5){
                    break
                }
            }
        }
        resetButton.setOnClickListener {
            val resetIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(resetIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    private fun read(): RealmResults<Score?>?{
        if(realm.where(Score::class.java).findAll() != null){
            return realm.where(Score::class.java).findAll()
        }else{
            return null
        }
    }
    private fun save(second:Double){
        realm.executeTransaction{
            val score:Score = it.createObject(Score::class.java)
            score.second = second
        }
    }
    fun add(rank:Int,score:String){
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val rankTextView = TextView(this)
        rankTextView.text = rank.toString() + "位"
        rankTextView.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1F
        )
        rankTextView.gravity = Gravity.CENTER
        rankTextView.textSize = 24F

        val scoreTextView = TextView(this)
        scoreTextView.text = score
        scoreTextView.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1F
        )
        scoreTextView.gravity = Gravity.CENTER
        scoreTextView.textSize = 24F

        layout.addView(rankTextView)
        layout.addView(scoreTextView)
        container.addView(layout)
    }
}