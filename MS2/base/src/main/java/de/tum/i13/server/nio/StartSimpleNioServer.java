package de.tum.i13.server.nio;

import de.tum.i13.server.Database.CacheFileHandler;
import de.tum.i13.server.kv.KVCommandProcessor;
import de.tum.i13.shared.CommandProcessor;
import de.tum.i13.shared.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.tum.i13.shared.Config.parseCommandlineArgs;
import static de.tum.i13.shared.LogSetup.setupLogging;

public class StartSimpleNioServer {

    public String className() {
        return this.getClass().getName();
    }

    public static Logger logger = Logger.getLogger(StartSimpleNioServer.class.getName());

    public static void main(String[] args) throws IOException {
        StartSimpleNioServer ssns = new StartSimpleNioServer();
        Config cfg = parseCommandlineArgs(args);  //Do not change this
        setupLogging(cfg.logfile, Level.parse(cfg.loglevel));
        logger.info("Config: " + cfg);

        logger.info("starting server");


        //Replace with your Key Value command processor
        //Create a new directory if it's not created
        if (!Files.exists(cfg.dataDir)) {
            try {
                File dir = new File(cfg.dataDir.toString());
                if (dir.mkdir())
                    System.out.println("Directory \"" + cfg.dataDir.toString() + "\" created");
            } catch (Exception e) {
                System.out.println("Error by creating the data directory in \"" + ssns.className() + "\"");
                return;
            }
        }
        //Give a default size for the cache if there is no logical size acutely given
        if (cfg.cachesize <= 0) {
            cfg.cachesize = 1000;


            CacheFileHandler cacheFileHandler = new CacheFileHandler(cfg.cachesize, cfg.cachedisplacement,  cfg.dataDir);
            CommandProcessor echoLogic = new KVCommandProcessor(cacheFileHandler);

            SimpleNioServer sn = new SimpleNioServer(echoLogic);
            sn.bindSockets(cfg.listenaddr, cfg.port);
            sn.start();
        }
    }
}
