package me.reichenberg.ryan.tests;

import me.reichenberg.ryan.components.CsvParser;
import me.reichenberg.ryan.entities.LeagueTable;
import me.reichenberg.ryan.entities.LeagueTableEntry;
import me.reichenberg.ryan.entities.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeagueTableEntryTests {

    private final CsvParser parser = new CsvParser();
    private final String TEAM_NAME_COLUMN = "teamName";
    private final String WON_COLUMN = "won";
    private final String DRAW_COLUMN = "drawn";
    private final String LOST_COLUMN = "lost";
    private final String GOALS_FOR_COLUMN = "goalsFor";
    private final String GOALS_AGAINST_COLUMN = "goalsAgainst";
    private final String GOAL_DIFFERENCE_COLUMN = "goalDifference";
    private final String POINTS_COLUMN = "points";
    private final String PLAYED_COLUMN = "played";


    @Test
    public void shouldSuccessfullyValidateInvinciblesSeason() throws Exception {
        validateStandings("/data/epl");
    }

    @Test
    public void shouldSuccessfullyValidateLaLigaSeason() throws Exception {
       validateStandings("/data/laliga");
    }

    @Test
    /**
     * getResource will return null, causing an NPE instead of FileNotFoundException.
     * This is okay because the intended use is to pass in a file path (string) like below
     */
    public void shouldFailOnFileNotFound() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            parser.parse("./path/does/not/exist/match_data.csv", Match.class);
        });
    }

    private void validateStandings(String path) throws Exception {
        List<String[]> standings = parser.parse(this.getClass().getResource(path + "/standings.csv").getPath());
        List<Match> matches = parser.parse(this.getClass().getResource(path + "/match_data.csv").getPath(), Match.class);
        List<String> headers = Arrays.asList(standings.get(0));
        List<LeagueTableEntry> tableEntries = new LeagueTable(matches).getTableEntries();
        for(int i = 1; i < standings.size(); i++) {
            String[] standing = standings.get(i);
            LeagueTableEntry entry = tableEntries.get(i - 1);
            assertEquals(entry.getTeamName(), standing[headers.indexOf(TEAM_NAME_COLUMN)]);
            assertEquals(entry.getPlayed(), Integer.valueOf(standing[headers.indexOf(PLAYED_COLUMN)]));
            assertEquals(entry.getPoints(), Integer.valueOf(standing[headers.indexOf(POINTS_COLUMN)]));
            assertEquals(entry.getWon(), Integer.valueOf(standing[headers.indexOf(WON_COLUMN)]));
            assertEquals(entry.getDrawn(), Integer.valueOf(standing[headers.indexOf(DRAW_COLUMN)]));
            assertEquals(entry.getLost(), Integer.valueOf(standing[headers.indexOf(LOST_COLUMN)]));
            assertEquals(entry.getGoalsFor(), Integer.valueOf(standing[headers.indexOf(GOALS_FOR_COLUMN)]));
            assertEquals(entry.getGoalsAgainst(), Integer.valueOf(standing[headers.indexOf(GOALS_AGAINST_COLUMN)]));
            assertEquals(entry.getGoalDifference(), Integer.valueOf(standing[headers.indexOf(GOAL_DIFFERENCE_COLUMN)]));


        }
    }
}
