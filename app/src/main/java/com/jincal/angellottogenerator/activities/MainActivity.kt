package com.jincal.angellottogenerator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jincal.angellottogenerator.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainSelectGeneratorTextView.setOnClickListener {
            startActivity<GeneratorActivity>()
        }

        mainSelectInstructionTextView.setOnClickListener {
            startActivity<InstructionActivity>()
        }

        mainSelectHistoryTextView.setOnClickListener {
            startActivity<HistoryActivity>()
        }
    }
}