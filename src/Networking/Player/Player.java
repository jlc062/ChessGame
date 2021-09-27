package Networking.Player;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/14/19
 * Time: 9:00 PM
 *
 * Project: CSCI205FinalProject
 * Package: Model.ChessParts
 * Class: Networking.Player.Player
 *
 * Description:
 * A player implementation that can make moves, stores their ipaddress, and
 * can get an send moves to and from the other player, two players in a chess game,
 * one is white one is black team. Implements runnable, so that a Thread can be started and we can
 * tell this thread what to do when it is run.
 * ****************************************
 */

import Controller.Controller;
import Model.Movement;
import Model.Team;

import java.io.IOException;

public abstract class Player implements Runnable{
    //Whether it is the players turn or not
    public boolean isTurn = false;
    private Team team;
    protected Controller controller;

    public Player(Team team, Controller controller) {

        this.team = team;
        this.controller = controller;
    }

    public Team getTeam() {
        return this.team;
    }



    /**
     * Sends a move to the other player using a Movement object
     * @param moveMade, the movement object that will be sent
     * @throws IOException
     */
    public abstract void sendMove(Movement moveMade) throws IOException;

    /**
     * Connect the client to the server
     * @throws IOException
     */
    public abstract void connect() throws IOException;


//    public abstract Movement waitForMove() throws IOException, ClassNotFoundException;
}