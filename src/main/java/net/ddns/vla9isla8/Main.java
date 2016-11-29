package net.ddns.vla9isla8;
import net.ddns.vla9isla8.ircc.entity.Command;
import net.ddns.vla9isla8.ircc.service.IRCCService;
import net.ddns.vla9isla8.ircc.service.bravia.RemoteService;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static IRCCService service = new RemoteService("192.168.88.253");

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        reader.lines().forEach(Main::sendRequest);
    }

    private static void sendRequest(String s) {
        List<Command> commands = service.getCommandsList();
        if(s.equals("-h")){
            printListCommands(commands.stream().map(Command::getName).collect(Collectors.toSet()));
            return;
        }
        try{
            Command cmd = commands.stream().filter(command -> command.getName().equals(s)).findFirst().get();
            service.executeCommand(cmd,true);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printListCommands(Set<String> commands) {
        final int[] i = {1};
        commands.stream().sorted().forEachOrdered(s -> {
            if(i[0]++%5==0){
                System.out.printf("%20s\n",s);
            } else {
            System.out.printf("%20s\t",s);
            }
        });
        System.out.println();
    }
}
