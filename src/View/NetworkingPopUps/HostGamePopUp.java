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


import javafx.scene.control.Alert;

import java.net.InetAddress;
import java.net.UnknownHostException;


//Resource: https://alvinalexander.com/java/joptionpane-showmessagedialog-examples-1

public class HostGamePopUp {

    InetAddress ip;

    {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host. Better check that out!");
        }
    }

    public HostGamePopUp() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Host a Game");
        alert.setContentText("This is your IP address: " + ip.getHostAddress());

        alert.showAndWait();

    }
}