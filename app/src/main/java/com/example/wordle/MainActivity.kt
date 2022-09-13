package com.example.wordle


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast




// author: calren
object FourLetterWordList {
    // List of most common 4 letter words from: https://7esl.com/4-letter-words/
    val fourLetterWords =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

    // Returns a list of four letter words as a list
    fun getAllFourLetterWords(): List<String> {
        return fourLetterWords.split(",")
    }

    // Returns a random four letter word from the list in all caps
    fun getRandomFourLetterWord(): String {
        val allWords = getAllFourLetterWords()
        val randomNumber = (0..allWords.size).shuffled().last()
        return allWords[randomNumber].uppercase()
    }
}

/**
 * Parameters / Fields:
 *   wordToGuess : String - the target word the user is trying to guess
 *   guess : String - what the user entered as their guess
 *
 * Returns a String of 'O', '+', and 'X', where:
 *   'O' represents the right letter in the right place
 *   '+' represents the right letter in the wrong place
 *   'X' represents a letter not in the target word
 */

var streak = 0
class MainActivity : AppCompatActivity() {

    fun checkGuess(guess: String,target :String) : String {

        var result = ""
        for (i in 0..3) {
            if (guess[i] == target[i]) {
                result += "O"
            }
            else if (guess[i] in target) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guess1 = findViewById<TextView>(R.id.textView3)
        val guess_1_check = findViewById<TextView>(R.id.textView4)
        val guess2 = findViewById<TextView>(R.id.right2)
        val guess2_check = findViewById<TextView>(R.id.right2check)
        val guess3 = findViewById<TextView>(R.id.right3)
        val guess3_check = findViewById<TextView>(R.id.right3check)
        val target = findViewById<TextView>(R.id.target)
        val guess1_edit = findViewById<TextView>(R.id.guess_1_real)
        val guess1_check_left = findViewById<TextView>(R.id.guess_1_check_left)
        val guess2_edit  = findViewById<TextView>(R.id.left2)
        val guess2_edit_check_left  = findViewById<TextView>(R.id.left2check)
        val guess3_edit  = findViewById<TextView>(R.id.left3)
        val guess3_edit_check_left  = findViewById<TextView>(R.id.left3check)
        val guess = findViewById<Button>(R.id.button)
        val reset = findViewById<Button>(R.id.reset)
        val input = findViewById<EditText>(R.id.input_text)
        val streak_view = findViewById<TextView>(R.id.streak)
        var attempt = 0

        fun init() : String{
            attempt = 0
            val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            reset.visibility = View.INVISIBLE
            guess1.visibility = View.INVISIBLE
            guess_1_check.visibility = View.INVISIBLE
            target.visibility = View.INVISIBLE
            guess1_edit.visibility = View.INVISIBLE
            guess1_check_left.visibility = View.INVISIBLE
            guess2_edit.visibility = View.INVISIBLE
            guess2_edit_check_left.visibility = View.INVISIBLE
            guess2.visibility = View.INVISIBLE
            guess2_check.visibility = View.INVISIBLE
            guess3_edit.visibility = View.INVISIBLE
            guess3_edit_check_left.visibility = View.INVISIBLE
            guess3.visibility = View.INVISIBLE
            guess3_check.visibility = View.INVISIBLE
            input.text.clear()
            input.hint = "Enter 4 letter here"
            return wordToGuess
        }

        var wordToGuess = init()
        var strVal = String()
        var guessed = String()
        guess.setOnClickListener {
            strVal= input.text.toString().uppercase()
            if (strVal.length != 4 ){

                if (attempt == 0){
                Toast.makeText(it.context,"Enter 4 letter word",Toast.LENGTH_SHORT).show()
                wordToGuess = init()
                }
                else if (attempt > 0 ){
                    Toast.makeText(it.context,"Enter 4 letter word",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                guessed = checkGuess(strVal, wordToGuess)
                attempt = attempt + 1

                if (strVal == wordToGuess ){
                    streak += 1
                    streak_view.text = streak.toString()
                }
                if (attempt == 1){
                    guess1_edit.text = "Guess #1"
                    guess1_edit.visibility = View.VISIBLE
                    guess1_check_left.text = "Guess #1 Check"
                    guess1_check_left.visibility = View.VISIBLE

                    guess1.text = strVal
                    guess1.visibility = View.VISIBLE
                    guess_1_check.text = guessed
                    guess_1_check.visibility = View.VISIBLE
                    input.text.clear()
                    input.hint = "Enter 4 letter here"

                }
                else if (attempt == 2) {
                    guess2_edit.text = "Guess 2"
                    guess2_edit.visibility = View.VISIBLE
                    guess2_edit_check_left.text = "Guess #2 Check"
                    guess2_edit_check_left.visibility = View.VISIBLE

                    guess2.text = strVal
                    guess2.visibility = View.VISIBLE
                    guess2_check.text = guessed
                    guess2_check.visibility = View.VISIBLE
                    input.text.clear()
                    input.hint = "Enter 4 letter here"
                }
                else if (attempt == 3){
                    guess3_edit.text = "Guess 3"
                    guess3_edit.visibility = View.VISIBLE
                    guess3_edit_check_left.text = "Guess #3 Check"
                    guess3_edit_check_left.visibility = View.VISIBLE

                    guess3.text = strVal
                    guess3.visibility = View.VISIBLE
                    guess3_check.text = guessed
                    guess3_check.visibility = View.VISIBLE

                    target.text = wordToGuess
                    target.visibility = View.VISIBLE
                    guess.visibility = View.INVISIBLE
                    reset.visibility = View.VISIBLE
                    reset.setOnClickListener {
                        reset.visibility = View.INVISIBLE
                        guess.visibility = View.VISIBLE
                        wordToGuess = init()
                    }
                }
            }

        }

    }
}