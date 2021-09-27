/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/22/19
 * Time: 4:47 PM
 *
 * Project: CSCI205FinalProject
 * Package: Network
 * Class: Client
 *
 * Description:
 *  A Client class took object reading and writing from stack overflow, will connect to a server
 * socket and then create an Object Input Stream and Object Output Stream to read and write from the client socket.
 * This is how we will handle sending back and forth information from the client to the server.
 * ****************************************
 */
package Networking.Sockets;

import Model.Movement;

import java.io.*;
import java.net.Socket;

public class Client {
    //IPAddress of host server
    private String serverIPAddress;
    //Socket client that is connection to the server socket
    static Socket clientSocket;
    static ObjectInputStream in;
    static ObjectOutputStream out;
    //Port number for the serverSocket, arbitrary
    private static final int portNum = 4444;
    //So we can see if it is connected
    public boolean isConnected = false;

    /**
     * Constructor, passes in the server IP Address so that it can connect to the
     * serverSocket
     * @param serverIPAddress, IPAddress of host
     * @throws IOException
     */
    public Client(String serverIPAddress) throws IOException {
        this.serverIPAddress = serverIPAddress;
    }

    /**
     * Sets up the connection between the client and the server
     * @throws IOException
     */
    public void connect() throws IOException {
        setUpClientSeverConnection();
    }

    /**
     * Connects the socket of the client to the server through the server socket,
     * creates and input and output object streams so that we can write information back and forth
     * @throws IOException
     */
    private void setUpClientSeverConnection() throws IOException {
        //Displays host IP and port info
        System.out.println("C: Waiting for Connection... ");
        System.out.println("C: Server IP Address: "+ serverIPAddress);
        System.out.println("C: Server Port Number: "+ portNum);

        //Connects clientSocket to serverSocket
        clientSocket = new Socket(serverIPAddress, portNum);
        System.out.println("C: Connected");
        isConnected = true;

        //Create a way to communicate via the clientSocket
        createInputOutputObjectStreams();
    }

    /**
     * Creates the two streams needed, 'out' an output stream that can write to the client socket,
     * and 'in' an input stream that can read from the socket.
     * @throws IOException
     */
    private void createInputOutputObjectStreams() throws IOException {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Reads an object from the server, checks to make sure that it is a,
     * movement object, than casts what it reads to a movement object if the former
     * is true, returns this.
     * @return move, the movement of the person who sends it
     * @throws IOException
     */
    public Movement readMovementFromServer() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (obj instanceof Movement){
            Movement move = (Movement) obj;
            return move;
        }
        return null;
    }

    /**
     * Sends a movement object to the server through the socket
     * @param move, a Movement object that holds the move to be made
     * @throws IOException
     */
    public void sendMovementToServer(Movement move) throws IOException {
        out.writeObject(move);
        out.flush();
    }


    /** Below this line is remnants of testing methods **/


    /**
     * Reads a message from the server, through the socket
     */
    public String readStringFromServer() throws IOException {
        String message = in.readUTF();
        return message;
    }
}