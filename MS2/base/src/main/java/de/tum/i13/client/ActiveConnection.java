package de.tum.i13.client;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.logging.Level;

import static de.tum.i13.shared.LogSetup.setupLogging;

/**
 * Created by chris on 19.10.15.
 */
public class ActiveConnection implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(ActiveConnection.class.getName());
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;

    public ActiveConnection(Socket socket, PrintWriter output, BufferedReader input) {

        setupLogging(Paths.get( "ClientLogReport.log"), Level.ALL);
        logger.setLevel(Level.ALL);

        docLog("Connect to the SERVER ");
        this.socket = socket;

        this.output = output;
        this.input = input;
    }

    public void write(String command) {
        docLog("Write to the SERVER: " + command);
        output.write(command + "\r\n");
        output.flush();
    }

    public String readline() throws IOException {
        String str = input.readLine();
        str = str.replace("\r\n" , "");
        docLog("Write to the SERVER: " + str);
        return str;
    }

    public void close() throws Exception {
        docLog("Close the connection : ");
        if (output != null)
            output.close();
        if (input != null)
            input.close();
        socket.close();
    }

    public static void docLog (String str){
        switch (logger.getLevel().getName()) {
            case "SEVERE" ->                logger.severe(str);
            case "WARNING" ->               logger.warning(str);
            case "INFO", "ALL", "OFF" ->    logger.info(str);
            case "CONFIG" ->                logger.config(str);
            case "FINE" ->                  logger.fine(str);
            case "FINER" ->                 logger.finer(str);
            case "FINEST" ->                logger.finest(str);
        }
    }

    public void setLogger (Level newLEvel){
        System.out.println(logger.getLevel().getName());
        logger.setLevel(newLEvel);
    }

    public String getInfo() {
        return "/" + this.socket.getRemoteSocketAddress().toString();
    }
}
