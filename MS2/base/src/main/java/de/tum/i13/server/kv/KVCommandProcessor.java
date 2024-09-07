package de.tum.i13.server.kv;

import de.tum.i13.server.Database.CacheFileHandler;
import de.tum.i13.server.nio.StartSimpleNioServer;
import de.tum.i13.shared.CommandProcessor;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class KVCommandProcessor implements CommandProcessor {
    private KVStore kvStore;

    public KVCommandProcessor(KVStore kvStore) {
        this.kvStore = kvStore;
    }

    @Override
    public String process(String command) {
        String[] splittedCommand = command.split(" ",3);
        splittedCommand[splittedCommand.length-1] = splittedCommand[splittedCommand.length-1].replace("\r\n", "");
        try {
            switch (splittedCommand[0]){
                case "put" -> {
                    if (splittedCommand.length < 3)
                        return "Error: put command should consist of key and value pair as \"put <key> <value>\"\r\n";
                    return kvStore.put(splittedCommand[1],splittedCommand[2].replace("\r\n", "")).toString() + "\r\n";}
                case "get" -> {
                    if (splittedCommand.length < 2)
                        return "Error: get command should consist of key as \"get <key>\"\r\n ";
                    return kvStore.get(splittedCommand[1]) + "\r\n";}
                case "delete" -> {
                    if (splittedCommand.length < 2)
                        return "Error: delete command should consist of key as \"delete <key>\"\r\n";
                    return kvStore.delete(splittedCommand[1]).toString() + "\r\n";}
                default -> {return "Error: unknown command\r\n";}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error: unknown command\r\n";
    }

    @Override
    public String connectionAccepted(InetSocketAddress address, InetSocketAddress remoteAddress) {
        //done: Logging
        StartSimpleNioServer.logger.info("The connection established for remote address: "  + remoteAddress + "and local addres: " + address);
        return "The connection is successfully established: LocalAddr: " + address.toString() + "| RemoteAddr: " + remoteAddress.toString() + "\r\n";
    }

    @Override
    public void connectionClosed(InetAddress address) {
        //done: Logging
        StartSimpleNioServer.logger.info("The connection closed with: "  + address);

    }
}
