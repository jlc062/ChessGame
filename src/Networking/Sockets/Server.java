/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/22/19
 * Time: 11:45 AM
 *
 * Project: CSCI205FinalProject
 * Package: Network
 * Class: Server
 *
 * Description:
 *  A Server class took object reading and writing from stack overflow, will connect to a server
 * socket and then create an Object Input Stream and Object Output Stream to read and write from the client socket.
 * This is how we will handle sending back and forth information from the client to the server.
 * ****************************************
 */
package Networking.Sockets;

import Model.Movement;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server{
    //serverSocket for the Server
    private ServerSocket serverSocket;
    //Client socket
    private Socket clientSocket;
    //Establishes an output stream so we can send info to client
    private ObjectOutputStream out;
    //Establishes an input stream to get info from the client
    private ObjectInputStream in;
    //Port number for Server
    private static final int portNum = 4444;
    //To see if the server is connected to client
    public boolean isConnected = false;
    //IP Address
    private static String IPAddress;



    /**
     * Connects to a client socket.
     * @throws IOException
     */
    public void connect() throws IOException {
        setUpServerClientConnection();
    }

    /**
     * Finds the ip address of the server, shows this to the player,
     * shows the portNumber, makes a serverSocket with this portNumber,
     * and then accepts a client socket.
     * @throws IOException
     */
    private void setUpServerClientConnection() throws IOException {
        //Shows information to the player, and stores them
        findIPAddress();
        System.out.println("S: Your Address " + IPAddress);
        System.out.println("S: Your port: " + portNum);

        serverSocket = new ServerSocket(portNum);
        clientSocket = serverSocket.accept();
        isConnected = true;

        //Create a way to communicate via the clientSocket
        createInputOutputObjectStreams();
    }

    /**
     * Finds the IPAddress of this server
     * @throws UnknownHostException
     */
    private void findIPAddress() throws UnknownHostException {
        //Get IP address of the Server
        InetAddress inetAddress = InetAddress.getLocalHost();
        //Get String representation of the internet address
        IPAddress = inetAddress.getHostAddress();
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
     * Writes a movement object to the client
     * @param move, the movement of the player
     * @throws IOException
     */
    public void sendMovementToClient(Movement move) throws IOException {
        while (isConnected == false){
            continue;
        }
        out.writeObject(move);
        out.flush();
    }

    /**
     * Reads an object from the client, checks to make sure that it is a,
     * movement object, than casts what it reads to a movement object if the former
     * is true, returns this.
     * @return move, the movement received from the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Movement readMovementFromClient() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (obj instanceof Movement){
            Movement move = (Movement) obj;
            return move;
        }
        return null;
    }

    /**
     * Closes the server socket so that it is not longer accessible
     * @throws IOException
     */
    public void closeServerSocket() throws IOException {
        //Close the serverSocket so that only one client can connect at a time
        serverSocket.close();
    }

    public static String getIPAddress() {
        return IPAddress;
    }

    /**Below this line are remnants of testing methods**/

    /**
     * Using for testing the connection between server and client
     * Writes a message to the client
     */
    public void writeToClient(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }
}