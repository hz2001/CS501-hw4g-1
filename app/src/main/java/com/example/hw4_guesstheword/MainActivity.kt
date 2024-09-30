package com.example.hw4_guesstheword
import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.* // for layout
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.* // for Material design components
import androidx.compose.runtime.* // for managing the composable state
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.hw4_guesstheword.ui.theme.Hw4_guessTheWordTheme
import kotlinx.android.parcel.Parcelize
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hw4_guessTheWordTheme {
                Home()
            }
        }
    }
}

class Word(
    val word: String,
    val hint: String,
){
    fun contains(c: Char): Boolean {
        return word.contains(c,ignoreCase = true)
    }
}

val wordList = listOf(
    Word("Apple", "A fruit that keeps the doctor away."),
    Word("Piano", "A musical instrument with black and white keys."),
//    Word("Elephant", "The largest land animal with a trunk."),
//    Word("Paris", "The city of love and home to the Eiffel Tower."),
//    Word("Library", "A place where you can borrow books."),
//    Word("Rainbow", "Appears in the sky after rain and has seven colors."),
//    Word("Oxygen", "An essential gas we breathe in."),
//    Word("Shark", "A large predator found in the ocean."),
//    Word("Mountain", "A large natural elevation of the Earth's surface."),
//    Word("Chocolate", "A sweet treat made from cocoa."),
//    Word("Guitar", "A string instrument often used in rock music."),
//    Word("Sunflower", "A tall plant with a large, bright yellow flower."),
//    Word("Penguin", "A flightless bird that lives in cold, icy environments."),
//    Word("Laptop", "A portable computer that can be used on your lap."),
//    Word("Football", "A sport played with a round ball and two goals.")
)





// The layout to store the buttons
@Composable
fun CharButton(t: Char, onClick: (Char) -> Unit, buttonStates: MutableMap<Char, Boolean>) {
    Button(
        onClick = {
            onClick(t)
            buttonStates[t] = false
        },
        enabled = buttonStates[t] == true,
        modifier = Modifier
            .padding(2.dp)
            .width(40.dp)
            .height(40.dp)
    ) {
        Text(t.toString(), fontSize = 16.sp)
    }
}


@Composable
fun LetterGrid(buttonStates: MutableMap<Char, Boolean>, onClick: (Char) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
        items(('a'..'z').toList()) { item ->
            CharButton(
                t = item,
                onClick = { onClick(it)
                    Log.d("LetterGrid Print",item.toString())},
                buttonStates = buttonStates
            )
        }
    }
}





// The class to store the words

@Composable
fun HangManDisplay(phase: Int) {
    val imageList = listOf(
        R.drawable.base,
        R.drawable.phase1,
        R.drawable.phase2,
        R.drawable.phase3,
        R.drawable.phase4,
        R.drawable.phase5,
        R.drawable.phase6,
    )
    var currentPhase = phase
    if (phase > 6){
        currentPhase = 6
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageList[currentPhase]),
            contentDescription = "Image $currentPhase",
            modifier = Modifier
                .size(100.dp)
        )
    }
}


//@Composable
//fun Hint(word: Word){
//    var hintCounter by remember { mutableStateOf(0)}
//    if (hintCounter == 0){
//        // do not display anything
//    }else if(hintCounter == 1){
//        // display only the hint message
//        // find in word.hint, where word is the input with type Word
//
//    }else if(hintCounter == 2){
//        // eliminate half of the unused buttons
//        // phase + 1
//    }else {
//        // display all chars in the word if they are one of [a,e,i,o,u]
//        // phase + 1
//        val vowels = listOf('a','e','i','o','u')
//
//    }
//    // if phase == 6 end the game
//
//    Column{
//
//    }
//
//}
@Composable
fun WordDisplay(word: Word, wordvis: MutableMap<Char, Boolean>) {
    Row(horizontalArrangement = Arrangement.Center) {
        for (char in word.word) {
            Text(
                text = if (wordvis[char] == true) char.toString() else "_",
                fontSize = 30.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun Hint(word: Word, phase: Int, onHintUsed: () -> Unit) {

    val context = LocalContext.current


    Button(onClick = {

        if (phase>=0&&phase < 3) {


            onHintUsed()

            when (phase+1) { // TODO: Charles. Implement the second and third cases for "Hint"
                1 -> Toast.makeText(context, "Hint: ${word.hint}", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(context, "Hint: Half keyboard disable", Toast.LENGTH_SHORT).show()
                3 -> {
                    val vowels = listOf('a', 'e', 'i', 'o', 'u')
                    Toast.makeText(context, "Hint: Volwel reveald", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Hint not available", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Hint")
    }

    Spacer(modifier = Modifier.height(10.dp))



}




@Composable
fun Home(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var word by remember { mutableStateOf(wordList.random()) }
    var buttonStates by remember { mutableStateOf(('a'..'z').associateWith { true }.toMutableMap()) }
    var phase by remember { mutableStateOf(0) }
    var wordvis = remember { mutableStateMapOf<Char, Boolean>() }
    val context = LocalContext.current

    for (e in word.word) {
        wordvis[e] = false
    }

    fun onHintUsed() {
        if (phase < 6) phase++
    }

    fun startNewGame() {
        word = wordList.random()
        buttonStates.keys.forEach { char ->
            buttonStates[char] = true
        }
        wordvis.clear()
        word.word.forEach { wordvis[it] = false }
        phase = 0
    }

    val isGameWon = wordvis.values.all { it }
    val isGameOver = phase >= 6

    if (isGameOver) {
        Toast.makeText(context, "You lost! The word was ${word.word}.", Toast.LENGTH_LONG).show()
    } else if (isGameWon) {
        Toast.makeText(context, "Congratulations, You won!", Toast.LENGTH_LONG).show()
    }

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                WordDisplay(word = word, wordvis = wordvis)
                LetterGrid(buttonStates = buttonStates, onClick = { char ->
                    if (word.contains(char)) {
                        word.word.forEach { if (it.equals(char, ignoreCase = true)) wordvis[it] = true }
                    } else {
                        phase++
                    }
                    Log.d("Home Print",wordvis.toString())
                })

                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Hint(word = word, phase = phase, onHintUsed = { onHintUsed() })
                    NewGameButton(onNewGame = { startNewGame() })
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                HangManDisplay(phase = phase)
            }
        }
    } else {
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                HangManDisplay(phase = phase)
            }
            Spacer(Modifier.padding(30.dp))
            WordDisplay(word = word, wordvis = wordvis)
            Spacer(Modifier.padding(10.dp))
            LetterGrid(buttonStates = buttonStates, onClick = { char ->
                if (word.contains(char)) {
                    word.word.forEach { if (it.equals(char, ignoreCase = true)) wordvis[it] = true }
                } else {
                    phase++
                }
            })
            NewGameButton(onNewGame = { startNewGame() })
        }
    }
}

@Composable
fun NewGameButton(onNewGame: () -> Unit) {
    Button(onClick = { onNewGame() }) {
        Text(text = "New Game")
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hw4_guessTheWordTheme {
        Home()
    }
}
