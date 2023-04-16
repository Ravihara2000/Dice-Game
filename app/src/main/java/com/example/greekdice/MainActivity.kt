package com.example.greekdice

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnnewgame=findViewById<Button>(R.id.btnnewgame)
        val btnabout=findViewById<Button>(R.id.btnabout)
        var target=109

        //var dialogsettargetBinding= LayoutInflater.from(this).inflate(R.layout.popup_set_target,null)

        var dialog= Dialog(this)

        btnnewgame.setOnClickListener{
            dialog.setContentView(R.layout.popup_set_target)
            dialog.setCancelable(true)

            var tfsettarget=dialog.findViewById(R.id.tftarget) as EditText
            var btnnext=dialog.findViewById(R.id.btnplay) as Button

            btnnext.setOnClickListener {
                target=Integer.parseInt(tfsettarget.text.toString())
                val newgameintent=Intent(this,Game::class.java)
                newgameintent.putExtra("EXTRA_DATA", target)
                startActivity(newgameintent)
                dialog.dismiss()
            }
            dialog.show()
        }

        btnabout.setOnClickListener{
            dialog.setContentView(R.layout.popup_about)
            dialog.setCancelable(true)
            dialog.show()
        }

    }
}