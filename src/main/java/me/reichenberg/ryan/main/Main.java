package me.reichenberg.ryan.main;

import me.reichenberg.ryan.entities.LeagueTable;
import me.reichenberg.ryan.services.LeagueTableService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Match data is required for the program run. Please see the readme on how to run the application.");
            System.exit(1);
        }
        LeagueTableService service = new LeagueTableService();
        LeagueTable table = service.processMatchData(args[0]);
        service.printLeageTable(table.getTableEntries());
    }
}
