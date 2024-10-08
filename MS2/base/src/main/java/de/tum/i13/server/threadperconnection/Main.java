//package de.tum.i13.server.threadperconnection;
//
//import de.tum.i13.server.Database.FileProcessor;
//import de.tum.i13.server.echo.EchoLogic;
//import de.tum.i13.shared.CommandProcessor;
//import de.tum.i13.shared.Config;
//
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import static de.tum.i13.shared.Config.parseCommandlineArgs;
//import static de.tum.i13.shared.LogSetup.setupLogging;
//
///**
// * Created by chris on 09.01.15.
// */
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//        Config cfg = parseCommandlineArgs(args);  //Do not change this
//       // setupLogging(cfg.logfile);
//
//        final ServerSocket serverSocket = new ServerSocket();
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                System.out.println("Closing thread per connection kv server");
//                try {
//                    serverSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        //bind to localhost only
//        serverSocket.bind(new InetSocketAddress(cfg.listenaddr, cfg.port));
//
//        /** what to do here? synchronize what **/
//        //Replace with your Key value server logic
//        CommandProcessor logic = new EchoLogic();
//
//        while (true) {
//            Socket clientSocket = serverSocket.accept();
//
//            //When we accept a connection, we start a new Thread for this connection
//            Thread th = new ConnectionHandleThread(logic, clientSocket);
//            th.start();
//        }
//    }
//}
