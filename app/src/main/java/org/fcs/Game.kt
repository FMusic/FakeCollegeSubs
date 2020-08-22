package org.fcs

import android.content.Intent
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import org.fcs.controller.SubjectsProcessor
import org.fcs.model.College
import org.fcs.util.Constants
import org.w3c.dom.Text
import java.util.function.Consumer
import kotlin.random.Random

class Game : AppCompatActivity() {
    @BindView(R.id.tvSubject)
    lateinit var tvSubject: TextView

    lateinit var root: ViewGroup
    lateinit var sceneQuestion: Scene
    lateinit var sceneFake: Scene
    lateinit var sceneReal: Scene
    lateinit var sceneBye: Scene

    lateinit var realQ: List<String>
    lateinit var fakeQ: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        setScenes(null, null)
        val pickedCollege = intent.getIntExtra(Constants().collegePosition, 0)
        val col: College = SubjectsProcessor().getCollege(pickedCollege, this)!!
        startGame(col)
    }

    private fun setScenes(question: String?, type: TypeOfQuestion?) {
        var root: ViewGroup = findViewById(R.id.llRoot)
        if (question != null && type != null){
            sceneQuestion = Scene.getSceneForLayout(root, R.layout.game, this)
        } else{
            sceneQuestion = Scene.getSceneForLayout(root, R.layout.game, this)
        }
        sceneFake = Scene.getSceneForLayout(root, R.layout.fake, this)
        sceneReal = Scene.getSceneForLayout(root, R.layout.real, this)
        sceneBye = Scene.getSceneForLayout(root, R.layout.bye, this)
    }

    private fun startGame(col: College) {
        realQ = col.real
        fakeQ = col.fake
        newQuestion(realQ, fakeQ)
    }

    private fun newQuestion(real: List<String>, fake: List<String>) {
        var questionsNum : Int = real.size + fake.size
        if (questionsNum == 0){
            sayGoodBye()
        }else {
            val nextInt = Random(System.currentTimeMillis()).nextInt(0, questionsNum)
            var (question, type) = getQuestion(real, fake, nextInt)
            setScenes(question, type)
            TransitionManager.go(sceneQuestion, Fade())
            sceneQuestion.enter()
            root = findViewById(R.id.llRoot)
            val tv: TextView = findViewById(R.id.tvSubject)
            tv.text = question
            root.setOnClickListener { changeScene(type) }
        }
    }

    private fun sayGoodBye() {
        TransitionManager.go(sceneBye, Fade())
        root.setOnClickListener{returnToMainActivity()}
    }

    private fun returnToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun changeScene(type: TypeOfQuestion) {
        when(type){
            TypeOfQuestion.REAL -> TransitionManager.go(sceneReal, Explode())
            TypeOfQuestion.FAKE -> TransitionManager.go(sceneFake, Explode())
        }
        root.setOnClickListener{ newQuestion(realQ, fakeQ)}
    }

    private fun getQuestion(real: List<String>, fake: List<String>, nextInt: Int): Pair<String, TypeOfQuestion> {
        var question: String
        var type: TypeOfQuestion
        if (nextInt < realQ.size){
            question = realQ[nextInt]
            type = TypeOfQuestion.REAL
            realQ = removeFromList(real, nextInt)
        } else{
            question = fakeQ[nextInt - realQ.size]
            type = TypeOfQuestion.FAKE
            fakeQ = removeFromList(fake, nextInt-realQ.size)
        }
        return Pair(question, type)
    }

    private fun removeFromList(list: List<String>, element: Int): List<String> {
        var newlist :ArrayList<String> = ArrayList()
        for (x in list.indices){
            if (x != element){
                newlist.add(list[x])
            }
        }
        return newlist
    }

    enum class TypeOfQuestion{
        REAL,
        FAKE
    }
}
