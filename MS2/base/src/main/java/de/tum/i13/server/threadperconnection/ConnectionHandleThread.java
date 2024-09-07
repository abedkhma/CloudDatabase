//package de.tum.i13.server.threadperconnection;
//
//import de.tum.i13.shared.CommandProcessor;
//import de.tum.i13.shared.Constants;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class ConnectionHandleThread extends Thread {
//    private CommandProcessor cp;
//    private Socket clientSocket;
//
//    public ConnectionHandleThread(CommandProcessor commandProcessor, Socket clientSocket) {
//        this.cp = commandProcessor;
//        this.clientSocket = clientSocket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Constants.TELNET_ENCODING));
//            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Constants.TELNET_ENCODING));
//
//            String firstLine;
//            while ((firstLine = in.readLine()) != null) {
//                //receive the command from the client and send it to the ecoLogic to get process depends on the command, put or get key, value..
//                String res = cp.process(firstLine);
//                //res is the return message from the cp. For example if the data successfully stored or read and then the server send message to the client
//                out.write(res);
//                out.flush();
//            }
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
