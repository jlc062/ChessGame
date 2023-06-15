# Running the Application

Check the images in docs/setup
1. Verify that the javafx-sdk/lib folder is added as a java library to the project stucture. Make sure the ChessGame.imi has a reference to this library.
2. Set a PATH_TO_FX variable in the intellij preferences.
This will be different for every user. I have it stored the javafx libraries as part of the project.
Mine is /User/jimcampbell/Coding.../ChessGame/lib/javafx-sdk-20.0.1/lib
YOURS should have C:/ or /{wherever you downloaded this }/ChessGame/lib/javafx-sdk-20.0.1/lib

Then in Run Configurations > edit configurations... add this as a vm option:
--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
It is recommended to type that out and not copy paste.

#### A customizable, 3D, multi-player chess game for anyone aged 3 to 103.

## Chess Rules
In the game of chess, two players attempt to strategically capture each other's king. This can be done by a player who places the threat of capture on their opponent using one or more of their game pieces. If a king is in check, then the player must make a move that eliminates the threat of capture and cannot leave the king in check. Checkmate happens when a king is placed in check and there is no legal move to escape. Checkmate ends the game and the side whose delivered the checkmate wins the game.

Each game piece has specific set of allowable moves. They are as follows:
* The King can move exactly one square horizontally, vertically, or diagonally.
* The Queen can move any number of vacant squares diagonally, horizontally, or vertically.
* The Rook can move any number of vacant squares vertically or horizontally.
* The Bishop can move any number of vacant squares in any diagonal direction.
* The Knight can move two squares along any row or column and then one in any non-diagonal direction. (The resulting move is in the shape of an "L").
* Pawns can move forward one square if that square is unoccupied. If the pawn has not yet moved, the pawn can be moved two squares forward. Pawns may capture an enemy piece on either of the two spaces diagonally adjacent to their current position.

## Chess Implementation
This project is a GUI implementation of the chess game described above. In its most basic form, this game can be payed by two players at the same computer. The first player (white by default) starts by simply left clicking a piece to move. The valid moves become highlighted green to indicate availability. Gameplay continues with alternating turns between the bottom and the top team. 

For users wishing to play the game with advanced graphics, the "Settings" menu will allow 3D graphics to be enabled. In addition, a player may customize the board by selecting one of the 9 different colors for his/her game pieces.

This implementation of chess also supports network play. If a player desires to engage in competition with an opponent on another computer, the player can use the "Multiplayer" menu to chose to host or join a game. In network play, each user has the opportunity to customize their view independent of their opponent's view preferences. If a user wishes to quit or restart the game, these options are presented in the "Quit/Restart Game" menu.

## Installation and Run
To run the source code, please use your package manager of choice. If you would like a prebuilt version of the game, download and run the .jar file. 

## Project Authors
This project was completed by Ryan Bailis, James Campbell, Ethan Dunne, and Jake Schaeffer. All work is their own. Citations and references are provided in file headers as appropriate. 

## Libraries and Resources
* [JavaFX](https://openjfx.io) 
* [Interactive Mesh](http://www.interactivemesh.org/models/jfx3dimporter.html)
* [Official Chess Rules](http://www.uschess.org/content/view/7324/)

All other resources are documented in file headers as appropriate.

## Contributing
The developers are not accepting outside contributors at this time.

## Project Details
This project was developed as part of a class assignment. CSCI205, Software Engineering & Design at [Bucknell University](https://bucknell.edu) is a course focused on exposing students to real-world Java development, class design, and the principles of software engineering.

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Gameplay Screenshots
![Chess Game 2D](/design/screenshot2D.png)
Screenshot of 2D gameplay.

![Chess Game 3D](/design/screenshot3D.png)
Screenshot of 3D gameplay. Player 2 changed their piece color to green via the "Settings Menu".

##Citations:

http://net-informations.com/java/net/multithreaded.htm

2D pieces are from [Wikimedia Commons](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces)

