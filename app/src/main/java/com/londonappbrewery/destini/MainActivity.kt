package com.londonappbrewery.destini

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // TODO: Steps 4 & 8 - Declare member variables here:
    private var isEnd = false  // true -> to display endDialogue, false -> display Trio
    private var isAns1 = true // true: buttonTop clicked, false: buttonBottom clicked
    private var whichTrio:Int = 1  // which Trio being displayed on the screen
    private var whichEnd:Int = 4 // which End being displayed on the screen

    // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
    private class Trio(val story : Int, val ans1 : Int, val ans2 : Int)
    private val mT1 = Trio(R.string.T1_Story, R.string.T1_Ans1, R.string.T1_Ans2)
    private val mT2 = Trio(R.string.T2_Story, R.string.T2_Ans1, R.string.T2_Ans2)
    private val mT3 = Trio(R.string.T3_Story, R.string.T3_Ans1, R.string.T3_Ans2)
    private val trioList = listOf(mT1, mT2, mT3)

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenTrio(whichTrio)

        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:
        buttonTop.setOnClickListener {
            isAns1 = true
            nextScreen()
            if (isEnd) screenEnd(whichEnd) else screenTrio(whichTrio)
        }

        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:
        buttonBottom.setOnClickListener {
            isAns1 = false
            nextScreen()
            if (isEnd) screenEnd(whichEnd) else screenTrio(whichTrio)
        }


    }

    // flow control
    private fun nextScreen(){  // update whichEnd and whichTrio
        if (isEnd) {
            screenEnd(whichEnd)

            
        }else {
           when {
               (whichTrio==1 && isAns1) -> {whichTrio=3; isEnd=false}
               (whichTrio==1 && !isAns1) -> {whichTrio=2; isEnd=false}
               (whichTrio==2 && isAns1) -> {whichTrio=3; isEnd=false}
               (whichTrio==2 && !isAns1) -> {whichEnd=4; isEnd=true}
               (whichTrio==3 && isAns1) -> {whichEnd=6; isEnd=true}
               (whichTrio==3 && !isAns1) -> {whichEnd=5; isEnd=true}
               else -> Toast.makeText(this, "ERROR in flow control", Toast.LENGTH_LONG).show()
               }
           }

    }

    // display Trio
    private fun screenTrio(whichT:Int){
        storyTextView.text = getString(trioList[whichT-1].story)
        buttonTop.text = getString(trioList[whichT-1].ans1)
        buttonBottom.text = getString(trioList[whichT-1].ans2)
    }

    // display End
    private fun screenEnd(whichE:Int){
        var endStory = ""
        when (whichE) {
            4 -> endStory = getString(R.string.T4_End)
            5 -> endStory = getString(R.string.T5_End)
            6 -> endStory = getString(R.string.T6_End)
            else -> Toast.makeText(this, "ERROR in screenEnd", Toast.LENGTH_LONG).show()
        }
        storyTextView.text = endStory
        buttonTop.visibility = android.view.View.GONE
        buttonBottom.visibility = android.view.View.GONE
        // set up alertDialogue
        val alert = android.app.AlertDialog.Builder(this)
        alert.setTitle("This Is The End")
        alert.setCancelable(false)
        alert.setMessage("To replay, rotate your phone and back")
        alert.setPositiveButton("Close Application") {
            dialogue, which -> finish()
        }
        alert.show()
    }

    // Save Instance State, if needed
    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("TrioKey", whichTrio)
        outState.putInt("EndKey", whichEnd)
        outState.putBoolean("isEndKey", isEnd)
        outState.putBoolean("isAns1Key", isAns1)
    }


}
