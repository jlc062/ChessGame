/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ryan Bailis
 * Section: MWF 11am
 * Instructor: Professor Brian King
 * Date: 12/8/19
 * Time: 1:43 PM
 *
 * Project: csci205finalproject
 * Package: View.networkingPopUps
 * Class: joinGamePopUp
 *
 * Description:
 *
 * ****************************************
 */
package View.NetworkingPopUps;

//Resource: https://alvinalexander.com/java/joptionpane-showinputdialog-examples


import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class JoinGamePopUp {

    String addressToJoin;

    public JoinGamePopUp() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Join a Game");
        dialog.setContentText("Please enter the IP address of the game you want to join");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(IP -> addressToJoin = IP);

    }

    public String getAddressToJoin() {
        return addressToJoin;
    }

}