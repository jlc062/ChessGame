/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jake Schaeffer
 * Section: 11am
 * Date: 12/8/19
 * Time: 6:29 PM
 *
 * Project: csci205finalproject
 * Package: Controller
 * Class: MenuBarController
 *
 * Description:
 *
 * ****************************************
 */
package Controller;

import Model.GameManager;
import View.BoardView;
import View.GameView;
import View.NetworkingPopUps.HostGamePopUp;
import View.NetworkingPopUps.JoinGamePopUp;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MenuBarController {

    /** The view */
    protected GameView theView;
    /** The controller */
    protected Controller controller;
    /** The model */
    protected GameManager theModel;

    /** Whether or not this controller is the host or client for a networking game **/
    protected boolean isHost;

    /**Ip address of host of game*/
    private String ipAddressToJoin;

    public MenuBarController(GameView theView, Controller controller, GameManager theModel) {
        this.theView = theView;
        this.controller = controller;
        this.theModel = theModel;
        RadioMenuItem enable2DBtn = (RadioMenuItem)theView.getGameMenuBar().getViewGroup().getToggles().get(0);
        //bind is3D to !2Dselected (or to 3DSelected, either one works)
        theView.is3DProperty().bind(enable2DBtn.selectedProperty().not());
        //set default values for menus
        enable2DBtn.setSelected(true);
        theView.getGameMenuBar().getPlayer1Colors().getToggles().get(6).setSelected(true);
        theView.getGameMenuBar().getPlayer2Colors().getToggles().get(7).setSelected(true);

        reloadGameViewAndResetBindings();

    }

    /**
     * Set up menu bar bindings and event handlers
     */
    protected void setUpMenuBar() {
        setUp2Dvs3DMenuClickHandlers();
        setupMultiplayerMenuClicksHandler();
        colorClickHandlers();
        setupSaveQuitRestartHandler();
    }


    private void setupMultiplayerMenuClicksHandler() {

        MenuItem hostGameBtn = theView.getGameMenuBar().getMenus().get(1).getItems().get(0);
        hostGameBtn.setOnAction( event -> {
            new HostGamePopUp();
            controller.isMultiplayer = true;
            isHost = true;
            try {
                controller.makeConnection();
                controller.makeSquaresClickable();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem joinGameBtn = theView.getGameMenuBar().getMenus().get(1).getItems().get(1);
        joinGameBtn.setOnAction( event -> {
            JoinGamePopUp joinGamePopUp = new JoinGamePopUp();
            controller.isMultiplayer = true;
            ipAddressToJoin = joinGamePopUp.getAddressToJoin();
            isHost = false;
            System.out.println("The user wants to join the game hosted at: " + ipAddressToJoin);
            try {
                controller.makeConnection();
                controller.makeSquaresClickable();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void setupSaveQuitRestartHandler() {

        MenuItem restartButton = theView.getGameMenuBar().getMenus().get(2).getItems().get(0);
        restartButton.setOnAction( event -> {
            controller.restartGame();
        });

        MenuItem quitButton = theView.getGameMenuBar().getMenus().get(2).getItems().get(1);
        quitButton.setOnAction( event -> {
            System.exit(0);
        });

    }

    /**
     * Sets up event handlers for when the 2D and 3D menu buttons are clicked
     */
    private void setUp2Dvs3DMenuClickHandlers() {
        //handle 2D button press
        //must be an event handler because the view must be reloaded
        RadioMenuItem enable2DBtn = (RadioMenuItem)theView.getGameMenuBar().getViewGroup().getToggles().get(0);
        enable2DBtn.setOnAction(event -> {
            reloadGameViewAndResetBindings();
        });

        //handle 3D button press
        RadioMenuItem enable3DBtn = (RadioMenuItem)theView.getGameMenuBar().getViewGroup().getToggles().get(1);
        enable3DBtn.setOnAction(event -> {
            reloadGameViewAndResetBindings();

        });

    }

    private void colorClickHandlers() {

        //set up player 1 colors
        for (Toggle colorOption : theView.getGameMenuBar().getPlayer1Colors().getToggles()) {
            MenuItem colorButton = (MenuItem)colorOption;

            colorButton.setOnAction(event -> {
                Color newColor = Color.web(colorButton.getText());
                BoardView.setPlayer1Color(newColor);
                theView.refreshPieceColors();
            });
        }

        //set up player to colors
        for (Toggle colorOption : theView.getGameMenuBar().getPlayer2Colors().getToggles()) {
            MenuItem colorButton = (MenuItem)colorOption;

            colorButton.setOnAction(event -> {
                Color newColor = Color.web(colorButton.getText());
                BoardView.setPlayer2Color(newColor);
                theView.refreshPieceColors();
            });
        }

    }

    /**
     * Reloads the view of the game as well as re-binds all the event handlers and bindings using the new board game loaded
     */
    protected void reloadGameViewAndResetBindings() {
        theView.makeGameView();
        setUpMenuBar();
        controller.makeSquaresClickable();
    }

    public String getIpAddressToJoin() {
        return ipAddressToJoin;
    }

}
