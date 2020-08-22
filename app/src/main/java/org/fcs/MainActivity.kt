package org.fcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import org.fcs.controller.SubjectsProcessor
import org.fcs.util.Constants

class MainActivity : AppCompatActivity() {

    @BindView(R.id.lvColleges)
    lateinit var lvColleges: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setWidgets()
    }

    private fun setWidgets() {
        val list = SubjectsProcessor().Process(this)
        lvColleges.adapter = ArrayAdapter(this, R.layout.college_view, list)
        lvColleges.setOnItemClickListener { _, _, position, _ -> startGame(position) }
    }

    private fun startGame(col: Int) {
        val i = Intent(this, Game::class.java)
        i.putExtra(Constants().collegePosition, col)
        startActivity(i)
    }
}