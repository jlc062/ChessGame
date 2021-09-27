/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ryan Bailis
 * Section: MWF 11am
 * Instructor: Professor Brian King
 * Date: 11/26/19
 * Time: 11:43 PM
 *
 * Project: csci205FinalProject
 * Package: View
 * Class: View.GameMenu
 *
 * Description:
 *
 * ****************************************
 */
package View;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class GameMenuBar extends MenuBar {

    private final ToggleGroup viewGroup;
    private ToggleGroup player1Colors;
    private ToggleGroup player2Colors;


    //TODO - set selected RadioMenuButtons on game load.
    public GameMenuBar() {

        super();

        //create the game settings menu
        Menu view = new Menu("View (2D/3D)");
        viewGroup = new ToggleGroup();
        RadioMenuItem view2D = new RadioMenuItem("2D");
        RadioMenuItem view3D = new RadioMenuItem("3D");
        view.getItems().addAll(view2D,view3D);
        viewGroup.getToggles().addAll(view2D,view3D);

        Menu player1PieceColorMenu = new Menu("Player 1 Piece Color");
        player1Colors = createColorOptions(player1PieceColorMenu);

        Menu player2PieceColorMenu = new Menu("Player 2 Piece Color");
        player2Colors = createColorOptions(player2PieceColorMenu);

        Menu gameSettingsMenu = new Menu("Settings");
        ImageView settingsImage = new ImageView(new Image(getClass().getResourceAsStream("menuIcons/settings.png")));
        settingsImage.setFitWidth(25);
        settingsImage.setFitHeight(25);
        gameSettingsMenu.setGraphic(settingsImage);
        gameSettingsMenu.getItems().addAll(view, player1PieceColorMenu, player2PieceColorMenu);

        //create the quit menu
        Menu quitMenu = new Menu("Quit/Restart Game");
        ImageView quitMenuImage = new ImageView(new Image(getClass().getResourceAsStream("menuIcons/exit.png")));
        quitMenuImage.setFitWidth(25);
        quitMenuImage.setFitHeight(25);
        quitMenu.setGraphic(quitMenuImage);

        MenuItem restartGame = new MenuItem("Restart Game");
        MenuItem quitGame = new MenuItem("Quit");
        quitMenu.getItems().addAll(restartGame,quitGame);

        //create the multiplayer menu
        Menu multiplayer = new Menu("Multiplayer");

        MenuItem hostGame = new MenuItem("Host a Game");
        MenuItem joinGame = new MenuItem("Join a Game");
        multiplayer.getItems().addAll(hostGame,joinGame);
        ImageView multiplayerImage = new ImageView(new Image(getClass().getResourceAsStream("menuIcons/multiplayer.png")));
        multiplayerImage.setFitWidth(25);
        multiplayerImage.setFitHeight(25);
        multiplayer.setGraphic(multiplayerImage);

        this.getMenus().addAll(gameSettingsMenu,multiplayer,quitMenu);

    }

    private ToggleGroup createColorOptions(Menu menu) {
        int iconSize = 10;
        List<Color> colorList = new ArrayList<>();

        colorList.add(Color.RED);
        colorList.add(Color.ORANGE);
        colorList.add(Color.YELLOW);
        colorList.add(Color.GREEN);
        colorList.add(Color.BLUE);
        colorList.add(Color.PURPLE);
        colorList.add(Color.WHITE);
        colorList.add(Color.BLACK);
        colorList.add(Color.DARKRED);

        ToggleGroup colorsGroup = new ToggleGroup();

        for (Color color : colorList) {
            RadioMenuItem menuItem = new RadioMenuItem(String.valueOf(color));

            Shape icon = new Circle(iconSize,color);
            menuItem.setGraphic(icon);
            icon.setStroke(Color.BLACK);

            colorsGroup.getToggles().add(menuItem);
            menu.getItems().add(menuItem);
        }

        return colorsGroup;
    }

    public ToggleGroup getViewGroup() {
        return viewGroup;
    }


    public ToggleGroup getPlayer1Colors() {
        return player1Colors;
    }

    public ToggleGroup getPlayer2Colors() {
        return player2Colors;
    }
}
