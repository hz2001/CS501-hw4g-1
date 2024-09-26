# CS501-hw4g-1
Contains CS501 HW4 

Task Break Down

* components (Composables)*
Main page:
The "man" composable
1. Assets prep: body parts assets 
2. Position Info and functions to trigger them
3. A counter associated with the progress --- the man shows different states as the counter changes.
4. When the counter == 6, return to the lost result page;

The guess composable 
1. The button collections from a-z, such that when clicked, trigger the following things: a) check if the character associated with the button has a place in the word. And change the UI accordingly. b) each button can be clicked once each game: managed by a Bool value for each button. Turn grey when clicked (modifier change). Reset when the game ends. 
2. A conjuncture of "_" placed in the UI block represents the hidden word.
3. An class "word" represents the words to be guessed.
     Attri: (char: position) map, Hint message.
4. A list of "word" object that stores our words that can be played.
5. When all blanks are filled, return to the win result page.

The Hint composable 
1. a "Hint" button with a text field.
2. a counter that take track of the state of the Hint Button.
3. The hint should be able to manipulate the "guess" composible.
4. 1st time clicked: show a hint in the text field.
5. 2nd time clicked: show half of the ineffective unused buttons switch state. The "man" counter + 1.
6. 3rd time clicked: all vowels showing. The "man" counter + 1.
7. If clicking the hint button would cause the user to be completely hung, ie, lose the game, then show a toast, “Hint not available”.

The main layout
1. Contains the "man", the "guess block", and the "hint" component.
2. The layout of the page should be handled differently when the configuration of the page changes.
3. do not display the hint component when we are at the portrait layout. 


The Result Page: 
1. shows the correspondent page based on the result of the game.
2. The Start button, when clicked, randomizes a word for the user to play with.
