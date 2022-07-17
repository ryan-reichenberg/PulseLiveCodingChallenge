package me.reichenberg.ryan.services;

import me.reichenberg.ryan.components.CsvParser;
import me.reichenberg.ryan.entities.LeagueTable;
import me.reichenberg.ryan.entities.LeagueTableEntry;
import me.reichenberg.ryan.entities.Match;

import java.io.IOException;
import java.util.List;

public class LeagueTableService {
    private final CsvParser parser = new CsvParser();
    private final String tableFormat = "%10s %30s %10s %10s %10s %10s %10s %10s %10s %10s";

    /**
     * Process csv and transform to LeagueTable object
     * @param filePath - the path to the match data csv
     * @return a new League Table instance
     * @throws IOException
     */
    public LeagueTable processMatchData(String filePath) throws IOException {
        List<Match> matches = parser.parse(filePath, Match.class);
        return new LeagueTable(matches);
    }

    /**
     * Print formatted league table to standard output
     * @param standings - list of league  table entries
     */
    public void printLeageTable(List<LeagueTableEntry> standings) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(tableFormat,
                "Position", "Team", "Played",  "Won", "Drawn", "Lost", "GF", "GA", "GD", "Points"));
        builder.append("\n");
        for (int i = 0; i< standings.size(); i++) {
            LeagueTableEntry standing = standings.get(i);
            builder.append(String.format(tableFormat,
                    i+1, standing.getTeamName(), standing.getPlayed(),
                    standing.getWon(), standing.getDrawn(), standing.getLost(), standing.getGoalsFor(),
                    standing.getGoalsAgainst(), standing.getGoalDifference(), standing.getPoints()));
            builder.append("\n");
        }

        System.out.println(builder);

    }
}
