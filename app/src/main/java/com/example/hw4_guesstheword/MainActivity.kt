package com.example.hw4_guesstheword
import android.content.res.Configuration
import android.os.Bundle
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
import androidx.compose.material3.* // for Material design components
import androidx.compose.runtime.* // for managing the composable state
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.example.hw4_guesstheword.ui.theme.Hw4_guessTheWordTheme

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
    Word("Elephant", "The largest land animal with a trunk."),
    Word("Paris", "The city of love and home to the Eiffel Tower."),
    Word("Library", "A place where you can borrow books."),
    Word("Rainbow", "Appears in the sky after rain and has seven colors."),
    Word("Oxygen", "An essential gas we breathe in."),
    Word("Shark", "A large predator found in the ocean."),
    Word("Mountain", "A large natural elevation of the Earth's surface."),
    Word("Chocolate", "A sweet treat made from cocoa."),
    Word("Guitar", "A string instrument often used in rock music."),
    Word("Sunflower", "A tall plant with a large, bright yellow flower."),
    Word("Penguin", "A flightless bird that lives in cold, icy environments."),
    Word("Laptop", "A portable computer that can be used on your lap."),
    Word("Football", "A sport played with a round ball and two goals.")
)


// The layout to store the buttons
@Composable
fun CharButton(t: String, taggleToFalse: Boolean = false, onclick: () -> Unit){
    var enabled by remember { mutableStateOf(true) }
    if (taggleToFalse) {
        enabled = false
    }
    Button(
        onClick = {
            onclick()
            enabled = false
        },
        enabled = enabled
    ){
        Text(t)
    }
}


// 1/3 This is the Button composable that contains all Buttons to be pressed.
@Composable
fun AllButtons(mapping: HashMap<String, Boolean>, onclick: () -> Unit){
    Column {
        LazyRow {
            items(listOf("a".."e")) { letter ->
                mapping[letter.toString()]?.let {
                    CharButton(t= letter.toString(),
                    taggleToFalse = it,
                    onclick = onclick) }
            }
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
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageList[phase]),
            contentDescription = "Image $phase",
            modifier = Modifier
                .size(100.dp)
        )
    }
}


@Composable
fun Hint(word: Word){
    var hintCounter by remember { mutableStateOf(0)}
    if (hintCounter == 0){
        // do not display anything
    }else if(hintCounter == 1){
        // display only the hint message
        // find in word.hint, where word is the input with type Word

    }else if(hintCounter == 2){
        // eliminate half of the unused buttons
        // phase + 1
    }else {
        // display all chars in the word if they are one of [a,e,i,o,u]
        // phase + 1
        val vowels = listOf('a','e','i','o','u')

    }
    // if phase == 6 end the game

    Column{

    }

}





@Composable
fun Home(modifier: Modifier = Modifier) {
    // init configuration for Landscape / Portrait mode
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    // TODO need to save the current state, not rebuilding everything
    var word by remember { mutableStateOf(wordList[0])}
    var buttonToChar by remember { mutableStateOf(hashMapOf<String, Boolean>()) }

    var phase by remember { mutableStateOf(0) } //Take track of how many chances the user has

    fun nextPhase(){
        if (phase < 6){
            phase ++
        }
    }


    for (char in 'a'..'z'){
        val currentChar = char.toString()
        if (word.contains(char)){
            buttonToChar[currentChar] = true
        }
    }

    if (isLandscape){
        Row(modifier = Modifier.fillMaxSize()) {
            // buttons and hint on the left
            Column {
                AllButtons(buttonToChar,{nextPhase()}) {target ->
                    buttonToChar[target] = false
                }
                Spacer(Modifier.padding(30.dp))
                Hint(word)
            }

            // the handing man on the right
            HangManDisplay(phase = phase)
        }
    } else{
        // Portrait: Handing man on the top,
        // button pad on the bottom
        // no hint displayed
        Column {
            HangManDisplay(phase = phase)
            Spacer(Modifier.padding(30.dp))
            AllButtons(buttonToChar, onclick = { nextPhase() }){
                target ->
                buttonToChar[target] = false
            }
        }

    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hw4_guessTheWordTheme {
        Home()
    }
}