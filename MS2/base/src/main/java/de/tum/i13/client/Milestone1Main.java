package de.tum.i13.client;

import de.tum.i13.shared.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PortUnreachableException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.tum.i13.shared.Config.parseCommandlineArgs;

//connect clouddatabases.msrg.in.tum.de 5551
public class Milestone1Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ActiveConnection activeConnection = null;
        for(;;) {
            Config cfg = parseCommandlineArgs(args);  //Do not change this

            System.out.print("EchoClient> ");
            String line = reader.readLine();

            //System.out.print("command:");
            //System.out.println(line);
            if (!line.isEmpty()) {
                String[] command = line.split(" ");
                switch (command[0]) {
                    case "connect" -> activeConnection = buildconnection(command);
                    //case "send" -> sendMessage(activeConnection, command, line);
                    case "disconnect" -> closeConnection(activeConnection);
                    case "logLevel" -> setLogLevel(activeConnection, command);
                    case "put" -> put(activeConnection, command, line);
                    case "get" -> get(activeConnection, command, line);
                    case "delete" -> delete(activeConnection, command, line);
                    case "help" -> printHelp();
                    case "quit" -> {
                        printEchoLine("Application exit!");
                        return;
                    }
                    default -> {
                        printEchoLine("Unknown command:");
                        printHelp();
                    }
                }
            }
        }
    }

    private static void setLogLevel(ActiveConnection activeConnection, String[] command){
        if(activeConnection == null) {
            printEchoLine("Error! Not connected!");
            return;
        }
        if (command.length < 2) {
            printEchoLine("Error! Nothing to get!");
            return;
        }
        switch (command[1]) {
            case "SEVERE" ->    activeConnection.setLogger((Level.SEVERE));
            case "WARNING" ->   activeConnection.setLogger(Level.WARNING);
            case "INFO" ->      activeConnection.setLogger(Level.INFO);
            case "CONFIG" ->    activeConnection.setLogger(Level.CONFIG);
            case "FINE" ->      activeConnection.setLogger(Level.FINE);
            case "FINER" ->     activeConnection.setLogger(Level.FINER);
            case "FINEST" ->    activeConnection.setLogger(Level.FINEST);
            case "ALL" ->      activeConnection.setLogger(Level.ALL);
            case "OFF" ->     activeConnection.setLogger(Level.OFF);
            default -> System.out.println("EchoClient> ERROR: please enter a valid value for the LOGGER. For more INFO please type \"help\"");
        }
    }

    private static void put (ActiveConnection activeConnection, String[] command,  String line ){
        if(activeConnection == null) {
            printEchoLine("Error! Not connected!");
            return;
        }
        if (command.length < 3) {
            printEchoLine("Error! Nothing to store!");
            return;
        }
        activeConnection.write(line);

        try {
            printEchoLine(activeConnection.readline()); //read the server answer
        } catch (IOException e) {
            printEchoLine("Error! Not connected!");
        }
    }

    private static void get (ActiveConnection activeConnection, String[] command, String line ){
        if(activeConnection == null) {
            printEchoLine("Error! Not connected!");
            return;
        }
        if (command.length < 2) {
            printEchoLine("Error! Nothing to get!");
            return;
        }
        activeConnection.write(line);

        try {
            printEchoLine(activeConnection.readline()); //read the server answer
        } catch (IOException e) {
            printEchoLine("Error! Not connected!");
        }
    }

    private static void delete (ActiveConnection activeConnection, String[] command, String line ){
        if(activeConnection == null) {
            printEchoLine("Error! Not connected!");
            return;
        }
        if (command.length < 2) {
            printEchoLine("Error! Nothing to delete!");
            return;
        }
        activeConnection.write(line);

        try {
            printEchoLine(activeConnection.readline()); //read the server answer
        } catch (IOException e) {
            printEchoLine("Error! Not connected!");
        }
    }


    private static void printHelp() {
        System.out.println(
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
                    quit:                      Tears down the active connection to the server and exits the program execution."""
        );
    }

    private static void printEchoLine(String msg) {
        System.out.println("EchoClient> " + msg);
    }

    private static void closeConnection(ActiveConnection activeConnection) {
        if(activeConnection != null) {
            try {
                activeConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("A problem occurred by closing the connection");
                //Done: handle gracefully
                activeConnection = null;
            }
        }
    }

    private static void sendMessage(ActiveConnection activeConnection, String[] command, String line) {
        if(activeConnection == null) {
            printEchoLine("Error! Not connected!");
            return;
        }
        int firstSpace = line.indexOf(" ");
        if(firstSpace == -1 || firstSpace + 1 >= line.length()) {
            printEchoLine("Error! Nothing to send!");
            return;
        }

        String cmd = line.substring(firstSpace + 1);
        activeConnection.write(cmd); //sent to the server message

        try {
            printEchoLine(activeConnection.readline()); //read the server answer
        } catch (IOException e) {
            printEchoLine("Error! Not connected!");
        }
    }

    private static ActiveConnection buildconnection(String[] command) {
        if(command.length == 3){
            try {
                EchoConnectionBuilder kvcb = new EchoConnectionBuilder(command[1], Integer.parseInt(command[2]));
                ActiveConnection ac = kvcb.connect();
                String confirmation = ac.readline();
                printEchoLine(confirmation);
                return ac;
                //DONE: separate between could not connect, unknown host and invalid port
            } catch (PortUnreachableException e) {
                printEchoLine("The port number is illegal or not right");
            } catch (UnknownHostException nr){
                printEchoLine("Please check the IP address");
            } catch (IOException e){
                printEchoLine("It is IOException: there is a problem occurred with the connection");
            }
        }
        return null;
    }
}
