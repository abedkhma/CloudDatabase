package de.tum.i13.client;
import de.tum.i13.shared.Constants;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import static de.tum.i13.client.ClientCommandsLibrary.*;


public class TestClient {
    private static boolean isConnected = false;
    //clouddatabases.msrg.in.tum.de 5551

    public static void setConnected(boolean connected) {
        isConnected = connected;
    }

    public static void main(String[] args) throws IOException {
        ClientCommandsLibrary client = new ClientCommandsLibrary();
        Scanner scanner = new Scanner(System.in);
        String input;
        byte[] messageSend;

        while (true) { /* !input.equals("quit")*/
            System.out.print("EchoClient> ");
            input = scanner.nextLine();

    //--------------------------------------------------SEND--------------------------------------------------------------------
            if (input.startsWith("send ")) {
                if (!isConnected){
                    System.out.println("EchoClient> Please connect to the server first");
                    continue;
                }
                if (input.length() < 6)
                    System.out.println("EchoClient> To use the command \"send\", please enter your message after the command. Type \"help\" for more INFO.");
                else {
                    messageSend = input.substring(5).getBytes(Constants.TELNET_ENCODING);
                    if (messageSend.length> 128000)
                        System.out.println("EchoClient> The message is too big. It should not bigger than 128000 byte");
                    send(messageSend);
                }
    //--------------------------------------------------SEND--------------------------------------------------------------------

    //------------------------------------------------CONNECT--------------------------------------------------------------------
            }else if (input.startsWith("connect ")) {
                if (isConnected){
                    System.out.println("EchoClient> The connection already established");
                    continue;
                }
                if (input.length() < 9)
                    System.out.println("EchoClient> ERROR: To use the command \"connect\", please enter the address ADDRESS and the PORT. Type \"help\" for more INFO.");
                else{
                    String[] portAddressInput = input.substring(8).split(" ");
                    /*check after split if we get at least 2 splits to guarantee the correction of an input*/
                    if (portAddressInput.length < 2)
                        System.out.println("EchoClient> ERROR: To use the command \"connect\", please enter the address ADDRESS and the PORT. Type \"help\" for more INFO.");
                    else {
                        int port;
                        /*check if the input port numeric*/
                        try {
                            port = Integer.parseInt(portAddressInput[1]);
                        }catch (NumberFormatException e){
                            System.out.println("EchoClient> ERROR: the port must be numeric only");
                            continue;
                        }
                        if (port >= 1024 && port <= 65535) { /*check for valid port*/
                            connect(portAddressInput[0], port);
                        }else System.out.println("EchoClient> ERROR: invalid port range");
                    }
                }
    //------------------------------------------------CONNECT--------------------------------------------------------------------

    //------------------------------------------------LOGGING--------------------------------------------------------------------
            }else if (input.startsWith("logLevel ")) {
                if (!isConnected){
                    System.out.println("EchoClient> Please connect to the server first");
                    continue;
                }
                if (input.length() >= 12){
                    client.log( input.substring(9).split(" ",2)[0]);
                }else System.out.println("EchoClient> ERROR: please enter a valid value for the LOGGER. For more INFO please type \"help\"");
    //------------------------------------------------LOGGING--------------------------------------------------------------------

    //------------------------------------------HELP_DISCONNECT_QUIT-------------------------------------------------------------
            }else if (input.startsWith("help")) {
                help();

            }else if (input.startsWith("disconnect")) {
                if (!isConnected){
                    System.out.println("EchoClient> Please connect to the server first");
                    continue;
                }
                disconnect();

            } else if (input.startsWith("quit")) {
                quit();
                break;
            }
    //------------------------------------------HELP_DISCONNECT_QUIT-------------------------------------------------------------
            else help();
        }
    }
}
