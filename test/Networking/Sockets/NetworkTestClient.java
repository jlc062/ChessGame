/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 12/3/19
 * Time: 1:25 PM
 *
 * Project: CSCI205FinalProject
 * Package: Network
 * Class: NetworkTest2
 *
 * Description:
 *
 * ****************************************
 */
package Networking.Sockets;

import Model.Movement;
import Model.Square;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetworkTestClient {
    private Client client;
    @BeforeEach
    void setUp() throws IOException {
        client = new Client("localhost");
        client.connect();
    }
    @AfterEach
    void tearDown(){

    }

    @Test
    void testClientMovementReceiving() throws IOException, ClassNotFoundException {
        //Create the movement object that we know should be received from the Server
        Square initialSquare = new Square(1,1);
        Square finalSquare = new Square(2,2);
        Movement correctMove = new Movement(initialSquare, finalSquare);

        //Receives the movement from the server
        Movement receivedMovement = client.readMovementFromServer();
        assertEquals(receivedMovement, correctMove);
    }

    @Test
    void testClientMovementSending() throws IOException {
        //Create the Squares for the movement object we are sending
        Square initialSquare = new Square(2,2);
        Square finalSquare = new Square(3,3);
        Movement move = new Movement(initialSquare, finalSquare);

        //Send the movement object to the client
        client.sendMovementToServer(move);
    }
//    @Test
//    void testMessaging() throws IOException {
//        System.out.println("Net2: Waiting for message");
//        String message = client.readStringFromServer();
//        System.out.println("Net2: Message - " + message);
//        assertTrue(message.equals("Hello Client!"));
//    }
//

}