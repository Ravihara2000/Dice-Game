package com.example.greekdice

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class Game : AppCompatActivity() {
    // ImageViews for the user's dice and the computer's dice
    var u1img:ImageView?=null
    var u2img:ImageView?=null
    var u3img:ImageView?=null
    var u4img:ImageView?=null
    var u5img:ImageView?=null
    var c1img:ImageView?=null
    var c2img:ImageView?=null
    var c3img:ImageView?=null
    var c4img:ImageView?=null
    var c5img:ImageView?=null
    // Variables to store the values of the user's dice and the computer's dice
    var uran1:Int=1
    var uran2:Int=1
    var uran3:Int=1
    var uran4:Int=1
    var uran5:Int=1
    var cran1:Int=1
    var cran2:Int=1
    var cran3:Int=1
    var cran4:Int=1
    var cran5:Int=1
    // Buttons to get the user's selected dice
    var btnget1:Button?=null
    var btnget2:Button?=null
    var btnget3:Button?=null
    var btnget4:Button?=null
    var btnget5:Button?=null
    // Variables to keep track of whether the user has selected each dice or not
    var btn1Tapped:Boolean=false
    var btn2Tapped:Boolean=false
    var btn3Tapped:Boolean=false
    var btn4Tapped:Boolean=false
    var btn5Tapped:Boolean=false
    // Random number generator
    var ran: java.util.Random = java.util.Random()
    // Array of strings to display the values of the dice
    val sides= arrayOf<String>("one","one","two","three","four","five","six")
    // Arrays to store the values of the user's dice and the computer's dice
    val usernums= arrayOf<Int>(uran1,uran2,uran3,uran4,uran5)
    val computernums= arrayOf<Int>(cran1,cran2,cran3,cran4,cran5)

    //--scores--
    var usersum:Int=0
    var comsum:Int=0
    var target:Int=109

    // Buttons to shuffle the dice and to score the round
    var btnshuffle:Button?=null
    var btnscore:Button?=null
    // TextViews to display the current score and number of attempts
    var tvscore:TextView?=null
    var tvattempt:TextView?=null

    // Counters for the number of throws and attempts
    var throws:Int=0
    var attempt:Int=1

    // Dialogs to display when the user wins or loses
    var dialogLost:Dialog?=null
    var dialogLostBinding:View?=null
    var dialogWin:Dialog?=null
    var dialogWinBinding:View?=null

    //--for computer random rolls--
    var check:Boolean=true

    //--for tie state--
    var isTie:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

// Get the target score from the previous activity
        target=intent.getIntExtra("EXTRA_DATA",109)
        println(target)

        // Set up the dialog boxes for win and loss scenarios
        dialogLost= Dialog(this)
        dialogLostBinding=layoutInflater.inflate(R.layout.popup_lost,null)

        dialogWin= Dialog(this)
        dialogWinBinding=layoutInflater.inflate(R.layout.popup_win,null)

        // Get references to the user and computer dice images
        u1img=findViewById(R.id.u1)
        u2img=findViewById(R.id.u2)
        u3img=findViewById(R.id.u3)
        u4img=findViewById(R.id.u4)
        u5img=findViewById(R.id.u5)
        c1img=findViewById(R.id.c1)
        c2img=findViewById(R.id.c2)
        c3img=findViewById(R.id.c3)
        c4img=findViewById(R.id.c4)
        c5img=findViewById(R.id.c5)

        // Get references to the "get" buttons for each user dice
        btnget1=findViewById<Button>(R.id.btnget1)
        btnget2=findViewById<Button>(R.id.btnget2)
        btnget3=findViewById<Button>(R.id.btnget3)
        btnget4=findViewById<Button>(R.id.btngett4)
        btnget5=findViewById<Button>(R.id.btnget5)

        // Get references to the "shuffle" and "score" buttons, as well as the score and attempt text views
        btnshuffle=findViewById<Button>(R.id.shuffle)
        btnscore=findViewById<Button>(R.id.btnscore)
        tvscore=findViewById<TextView>(R.id.score_view)
        tvattempt=findViewById<TextView>(R.id.tvattempt)

        savedInstanceState?.let {
            btn1Tapped = it.getBoolean("btn1Tapped")
            btn2Tapped = it.getBoolean("btn2Tapped")
            btn3Tapped = it.getBoolean("btn3Tapped")
            btn4Tapped = it.getBoolean("btn4Tapped")
            btn5Tapped = it.getBoolean("btn5Tapped")
            usernums[0] = it.getInt("usernum1")
            usernums[1] = it.getInt("usernum2")
            usernums[2] = it.getInt("usernum3")
            usernums[3] = it.getInt("usernum4")
            usernums[4] = it.getInt("usernum5")
            computernums[0] = it.getInt("computernum1")
            computernums[1] = it.getInt("computernum2")
            computernums[2] = it.getInt("computernum3")
            computernums[3] = it.getInt("computernum4")
            computernums[4] = it.getInt("computernum5")
            usersum = it.getInt("usersum")
            comsum = it.getInt("comsum")
            throws = it.getInt("throws")
            attempt = it.getInt("attempt")
        }

        initialize()

        btnget1?.setOnClickListener {
            btn1Tapped=!btn1Tapped
            if(btn1Tapped){btnget1?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget1?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget2?.setOnClickListener {
            btn2Tapped=!btn2Tapped
            if(btn2Tapped){btnget2?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget2?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget3?.setOnClickListener {
            btn3Tapped=!btn3Tapped
            if(btn3Tapped){btnget3?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget3?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget4?.setOnClickListener {
            btn4Tapped=!btn4Tapped
            if(btn4Tapped){btnget4?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget4?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }
        btnget5?.setOnClickListener {
            btn5Tapped=!btn5Tapped
            if(btn5Tapped){btnget5?.setBackgroundColor(getResources().getColor(R.color.teal_700));}
            else btnget5?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        }

        hideControllers()

        btnshuffle?.setOnClickListener {
            if(isTie){
                //--if the game tied--
                println("tied")
                hideControllers()
                generateUserValues()
                generateComputerValues()
                setImages()
                setScore()
            }
            //--if the game not tied
            if(throws<2){
                generateUserValues()

                if (throws==0){
                    println(throws)
                    generateComputerValues()
                    btnshuffle?.text="Re-throw"}
                else if(throws==1){
                    btnshuffle?.text="Re-throw again"}

                setImages()
                throws+=1

                resetButtons()

            }else if(throws==2){
                println(throws)
                generateUserValues()
                generateComputerValuesRandom()
                generateComputerValuesRandom()
                setImages()
                setScore()
                throws=0
            }

            if (throws==1){
                showControllers()
            }
        }
        btnscore?.setOnClickListener {
            if(throws==2){
                generateComputerValuesRandom()
                generateComputerValuesRandom()
                setImages()
            }
            setScore()
        }
        // Update the score and attempt text views
        tvscore?.text = getString(R.string.score_text)
        tvattempt?.text = getString(R.string.attempt_text, attempt)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("btn1Tapped", btn1Tapped)
        outState.putBoolean("btn2Tapped", btn2Tapped)
        outState.putBoolean("btn3Tapped", btn3Tapped)
        outState.putBoolean("btn4Tapped", btn4Tapped)
        outState.putBoolean("btn5Tapped", btn5Tapped)
        outState.putInt("usernum1", usernums[0])
        outState.putInt("usernum2", usernums[1])
        outState.putInt("usernum3", usernums[2])
        outState.putInt("usernum4", usernums[3])
        outState.putInt("usernum5", usernums[4])
        outState.putInt("computernum1", computernums[0])
        outState.putInt("computernum2", computernums[1])
        outState.putInt("computernum3", computernums[2])
        outState.putInt("computernum4", computernums[3])
        outState.putInt("computernum5", computernums[4])
        outState.putInt("usersum", usersum)
        outState.putInt("comsum", comsum)
        outState.putInt("throws", throws)
        outState.putInt("attempt", attempt)
    }
    private fun setScore(){
        attempt+=1
        tvattempt?.text="Round "+attempt
        usersum=genSum(true)
        comsum=genSum(false)

        tvscore?.text=comsum.toString()+"/"+usersum.toString()

        if (usersum>=target || comsum>=target){
            checkWins()
        }
        hideControllers()
        resetButtons()
        btnshuffle?.text="Throw"
        throws=0
    }

    private fun generateUserValues(){
        if (btn1Tapped==false){
            usernums[0]=ran.nextInt(5)+1
        }
        if (btn2Tapped==false){
            usernums[1]=ran.nextInt(5)+1
        }
        if (btn3Tapped==false){
            usernums[2]=ran.nextInt(5)+1
        }
        if (btn4Tapped==false){
            usernums[3]=ran.nextInt(5)+1
        }
        if (btn5Tapped==false){
            usernums[4]=ran.nextInt(5)+1
        }
    }

    private fun generateComputerValues(){
        for (i in 0..computernums.size-1){
            computernums[i]=ran.nextInt(6)+1
        }
    }

    private fun generateComputerValuesRandom(){
        check=ran.nextBoolean()
        println("genrate random numbers for computer: "+check)
        if(check){
            println("gen ran true")
            //--keep dices randomly
            btn1Tapped=ran.nextBoolean()
            btn2Tapped=ran.nextBoolean()
            btn3Tapped=ran.nextBoolean()
            btn4Tapped=ran.nextBoolean()
            btn5Tapped=ran.nextBoolean()

            //--reroll
            if (btn1Tapped==false){
                computernums[0]=ran.nextInt(5)+1
            }
            if (btn2Tapped==false){
                computernums[1]=ran.nextInt(5)+1
            }
            if (btn3Tapped==false){
                computernums[2]=ran.nextInt(5)+1
            }
            if (btn4Tapped==false){
                computernums[3]=ran.nextInt(5)+1
            }
            if (btn5Tapped==false){
                computernums[4]=ran.nextInt(5)+1
            }
        }else{
            println("gen ran false")
            //--no rolls
        }
    }

    private fun checkWins(){
        print("check wins ran")
        if (usersum>=target || comsum>=target){
            if(usersum==comsum){
                //--tie state functions--
                println("tied")
                isTie=true
            }
            else if (usersum>comsum){
                isTie=false
                //--user wins
                println("user won")
                dialogWinBinding?.let { dialogWin?.setContentView(it) }
                dialogWin?.setCancelable(true)
                dialogWin?.setCanceledOnTouchOutside(false)
                dialogWin?.setOnCancelListener{
                    finish()
                }
                dialogWin?.show()
            } else if(comsum>usersum){
                isTie=false
                //--user lost
                println("use lost")
                dialogLostBinding?.let { dialogLost?.setContentView(it) }
                dialogLost?.setCancelable(true)
                dialogLost?.setCanceledOnTouchOutside(false)
                dialogLost?.setOnCancelListener{
                    finish()
                }
                dialogLost?.show()
            }
        }
    }

    private fun setImages(){
        u1img?.setImageResource(resources.getIdentifier(sides[usernums[0]],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(sides[usernums[1]],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(sides[usernums[2]],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(sides[usernums[3]],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(sides[usernums[4]],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(sides[computernums[0]],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(sides[computernums[1]],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(sides[computernums[2]],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(sides[computernums[3]],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(sides[computernums[4]],"drawable","com.example.dicegame"))
    }

    private fun resetButtons(){
        btnget1?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget2?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget3?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget4?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))
        btnget5?.setBackgroundColor(getResources().getColor(R.color.backgroundcolor))

        btn1Tapped=false
        btn2Tapped=false
        btn3Tapped=false
        btn4Tapped=false
        btn5Tapped=false
    }

    private fun hideControllers(){

        println("hide")
        btnscore?.isVisible=false
        btnget1?.isVisible=false
        btnget2?.isVisible=false
        btnget3?.isVisible=false
        btnget4?.isVisible=false
        btnget5?.isVisible=false
    }

    private fun showControllers(){
        btnscore?.isVisible=true
        btnget1?.isVisible=true
        btnget2?.isVisible=true
        btnget3?.isVisible=true
        btnget4?.isVisible=true
        btnget5?.isVisible=true
    }

    private fun initialize(){
        u1img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u2img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u3img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u4img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        u5img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c1img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c2img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c3img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c4img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))
        c5img?.setImageResource(resources.getIdentifier(sides[0],"drawable","com.example.dicegame"))

        for(i in 0..usernums.size-1){
            usernums[i]=1
            computernums[i]=1
        }

    }

    private fun genSum(user:Boolean):Int{
        if (user){
            for(i in 0..usernums.size-1){
                print(usernums[i])
                usersum=usersum+usernums[i]
            }
            return usersum
        }else{
            for(k in 0..computernums.size-1){
                print(computernums[k])
                comsum=comsum+computernums[k]
            }
            return comsum
        }
    }
}