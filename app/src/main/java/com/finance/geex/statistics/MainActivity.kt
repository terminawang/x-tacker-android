package com.finance.geex.statistics

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.finance.geex.statisticslibrary.mananger.GeexDataApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_click1.setOnClickListener(View.OnClickListener {

            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)

        })

        text_click2.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }

        text_click3.setOnClickListener {
            val intent = Intent(this,TestFragmentActivity::class.java)
            startActivity(intent)
        }


    }



}
