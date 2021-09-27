/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 12/3/19
 * Time: 8:45 PM
 *
 * Project: CSCI205FinalProject
 * Package: Networking.NController
 * Class: HostPlayer
 *
 * Description:
 * A Host player class, is automatically the server and has the black team,
 * will listen for objects from the client using a thread. Platform.runLater found at this site.
 * https://stackoverflow.com/questions/17850191/why-am-i-getting-java-lang-illegalstateexception-not-on-fx-application-thread
 * ****************************************
 */
package Networking.Player;

import Model.Movement;
import Model.Team;
import Controller.Controller;
import Networking.Sockets.Server;
import javafx.application.Platform;

import java.io.IOException;

public class HostPlayer extends Player{
    private Server server;
    private boolean gameOver = false;

    public HostPlayer(Controller controller) throws IOException {
        super(Team.BLACK, controller);
        this.server = new Server();
    }


    @Override
    public void sendMove(Movement moveMade) throws IOException {
        server.sendMovementToClient(moveMade);
    }

    @Override
    public void connect() throws IOException {
        server.connect();
    }

    /**
     * A new thread can be made so that our program can run what it needs to on the javaFX
     * side of things, meanwhile the server will always be listening to the socket output stream
     * for a movement object. At which point it will simulate a click to the controller using the simulateClick method,
     * Which means the other player made a click and we have that data and now need the controller to simulate it.
     * The lambda function is there because originally we got an error from there being two threads, this one and the javaFx
     * one trying to send information to each other. I found this lambda function solution online using the Platform.runLater
     * which is used to update a GUI thread from a non GUI thread, it puts it in a queue and will update it as soon as possible
     */
    @Override
    public void run() {
        while(!gameOver){
            try {
                Movement move = server.readMovementFromClient();
                Platform.runLater(
                        () -> {
                            controller.simulateClick(move);

                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public Movement waitForMove() throws IOException, ClassNotFoundException {
//        Movement move = server.readMovementFromClient();
//        System.out.println(move);
//        return move;
//    }

}