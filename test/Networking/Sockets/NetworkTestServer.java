package Networking.Sockets;

import Model.Movement;
import Model.Square;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkTestServer {

    private Server server;

    @BeforeEach
    void setUp() throws IOException {
        this.server = new Server();
        server.connect();
    }
    @AfterEach
    void tearDown() throws IOException {
        server.closeServerSocket();
    }

    @Test
    void testServerMovementSending() throws IOException {
        //Create Squares to give to the Movement object
        Square initialSquare = new Square(1,1);
        Square finalSquare = new Square(2,2);
        Movement move = new Movement(initialSquare, finalSquare);

        //Send the movement object to the Client
        server.sendMovementToClient(move);

    }

    @Test
    void testServerMovementReceiving() throws IOException, ClassNotFoundException {
        //Create the movement we know that the client is sending
        Square initialSquare = new Square(2,2);
        Square finalSquare = new Square(3,3);
        Movement correctMove = new Movement(initialSquare, finalSquare);

        //Receive the movement from the client
        Movement receivedMovement = server.readMovementFromClient();
        assertEquals(receivedMovement, correctMove);
    }

//    @Test
//    void testMessaging() throws IOException {
//        Server server = new Server();
//        System.out.println("Net1: Message is 'Hello Client!'");
//        server.writeToClient("Hello Client!");
//        System.out.println("Net1: Sent to Client...");
//    }
}