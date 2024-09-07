package de.tum.i13.client;


import de.tum.i13.shared.Constants;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.tum.i13.shared.LogSetups.setupLogging;


public class ClientCommandsLibrary {
    private static final String help =
            """
                    connect <address> <port>:  Tries to establish a TCP- connection to the echo server based on the given server address and the port number of the echo service.
                    disconnect:                Tries to disconnect from the connected server.
                    send <message>:            Sends a text message to the echo server according to the communication protocol.
                    logLevel <level>:          Sets the logger to the specified log level
                                                * SEVERE (highest value)
                                                * WARNING
                                                * INFO
                                                * CONFIG
                                                * FINE
                                                * FINER
                                                * FINEST (lowest value)
                    quit:                      Tears down the active connection to the server and exits the program execution.""";
    private  final static Logger logger = Logger.getLogger(ClientCommandsLibrary.class.getName());
    private static String receiveConnection="";
    private static Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    public ClientCommandsLibrary(){setupLogging("clientFileReport.log");logger.setLevel(Level.INFO);}

    public static boolean checkConnection(){
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.write("connected\r\n");
            out.flush();
            String str = String.valueOf(byteToChar(receive()));
            if (str.equals("connected"))
                return true;
        }catch(Exception e) {
            System.out.println("not Connected");
            return false;
        }
        return false;
    }
    public static char[] byteToChar(byte[] byteArr){
        char[] charArr = new char[byteArr.length];
        for (int i = 0; i < byteArr.length; i++)
            charArr[i] = (char) byteArr[i];
        return charArr;
    }

    public static byte[] receive() throws IOException {
        byte[] receive = new byte[128000];
        int i = 0, temp1 = -1, temp2;
        inputStream = socket.getInputStream();

        while (true) {
            temp2 = inputStream.read();
            if (temp2 != '\n' && temp2 != '\r')
                receive[i] = (byte) temp2;
            if (temp1 == '\n' && temp2 == '\r' || temp2 == '\n' && temp1 == '\r')
                break;
            temp1 = temp2;
            if (i == 128000 - 1) {
                System.out.println("THERE IS PROBLEM WITH THE CONNECTION TO THE SERVER OR THE RECEIVED MESSAGE BIGGER THAN ALLOWED");
                docLog("RECEIVE METHOD, THE RECEIVED CHARACTER BIGGER THAN ALLOWED. LAST CHARACTER WAS: " + temp1);
                return (new byte[]{-1});
            }
            i++;
        }
        byte[] temp = new byte[i-1];
        System.arraycopy(receive, 0, temp, 0, i-1);
        return temp;
    }

    public static void help(){
        System.out.println("EchoClient>\n" + help);
    }
    public static void disconnect() {
        try {
            docLog("DISCONNECTING FROM THE SERVER ");
            if (inputStream != null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
            socket.close();
        } catch (IOException e) {
            logger.throwing(ClientCommandsLibrary.class.getName(), "disconnect", e);
            throw new RuntimeException(e + " ERROR BY DISCONNECT METHOD");
        }
        String str = receiveConnection.substring(receiveConnection.lastIndexOf(": ") + 2);
        System.out.println("EchoClient> Connection terminated: " + str);
        docLog("DISCONNECTING FROM THE SERVER AND THE BOOLEAN VARIABLE SET TO NOT ALIVE. THE IP AND THE PORT OF THE DEVICE IS: " + str);
        TestClient.setConnected(false);
    }
    public static void quit() {
        docLog("QUITTING THE APPLICATION");
        disconnect();
        System.out.println("EchoClient> Application exit!");
    }
    public void log (String str) {
        switch (str) {
            case "SEVERE" ->    logger.setLevel(Level.SEVERE);
            case "WARNING" ->   logger.setLevel(Level.WARNING);
            case "INFO" ->      logger.setLevel(Level.INFO);
            case "CONFIG" ->    logger.setLevel(Level.CONFIG);
            case "FINE" ->      logger.setLevel(Level.FINE);
            case "FINER" ->     logger.setLevel(Level.FINER);
            case "FINEST" ->    logger.setLevel(Level.FINEST);
            //case "ALL" ->       logger.setLevel(Level.ALL);
            //case "OFF" ->       logger.setLevel(Level.OFF);
            default -> System.out.println("EchoClient> ERROR: please enter a valid value for the LOGGER. For more INFO please type \"help\"");
        }
    }
    public static void docLog (String str){
        switch (logger.getLevel().getName()) {
            case "SEVERE" ->    logger.severe(str);
            case "WARNING" ->   logger.warning(str);
            case "INFO" ->      logger.info(str);
            case "CONFIG" ->    logger.config(str);
            case "FINE" ->      logger.fine(str);
            case "FINER" ->     logger.finer(str);
            case "FINEST" ->    logger.finest(str);
        }
    }
    public static void connect(String address, int port){
            try {
                docLog("CREATING NEW SOCKET");
                socket = new Socket(address, port);
                char[] message = byteToChar(receive());
                receiveConnection = String.valueOf(message);
                docLog("RECEIVED MESSAGE FROM THE SERVER WILE CONNECTING: " + receiveConnection);
                System.out.println("EchoClient> " + receiveConnection);
            } catch (IOException e) {
                logger.throwing(ClientCommandsLibrary.class.getName(), "connect", e);
                docLog("The connection failed");
                System.out.println(e + " - Please check the IP address or the port number - (connect method)");
                return;
            }
            //check if the server connected
            if (checkConnection()) {
                docLog("THE CONNECTION HAS BEEN SUCCESSFULLY ESTABLISHED");
                TestClient.setConnected(true);
            }
            else System.out.println("EchoClient> The connection failed");
    }
    
    public static void send(byte[] message) throws IOException {
        int temp2;
        docLog("THE MESSAGE TO THE SERVER: " + new String(message));
        outputStream = socket.getOutputStream();
        for (byte b : message) {
            temp2 = b;
            outputStream.write(temp2);
        }
        outputStream.write('\r');
        outputStream.write('\n');
        outputStream.flush();
        String receiveSend = String.valueOf(byteToChar(receive()));
        docLog("THE MESSAGE FROM THE SERVER: " + receiveSend);
        System.out.println("EchoClient> " + receiveSend);
    }
}
