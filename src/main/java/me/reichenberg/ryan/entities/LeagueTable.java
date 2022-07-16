package me.reichenberg.ryan.entities;

import me.reichenberg.ryan.comparators.LeagueTableEntryComparator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

// Mutable entity
// Local cache should be fine for this object
// Shouldn't be too expensive to create
public class LeagueTable {

    private final Map<String, LeagueTableEntry> table;
    private final List<Match> matches;
    public static final int WIN_POINTS = 3;
    public static final int LOSE_POINTS = 0;
    public static final int DRAW_POINTS = 1;

    public LeagueTable(final List<Match> matches) {
        this.matches = matches;
        this.table = new HashMap<>();
    }

    public List<LeagueTableEntry> getTableEntries() {
        return convert(matches);
    }


    private List<LeagueTableEntry> convert(List<Match> matches) {
        // League entries for both teams involved in the match
        matches.forEach(match -> {
            Pair<LeagueTableEntry, LeagueTableEntry> leagueEntries = getLeagueEntriesForMatch(match);
            // left = home, right = away
            updateLeagueTableEntries(match, leagueEntries.getLeft(), leagueEntries.getRight());
        });
        return table
                .values()
                .stream()
                .sorted(new LeagueTableEntryComparator())
                .collect(Collectors.toList());

    }

    private Pair<LeagueTableEntry, LeagueTableEntry> updateLeagueTableEntries(Match match, LeagueTableEntry home,
                                                                              LeagueTableEntry away) {
        // These objects are immutable so we need to create new ones every time
        home  = createNewTableEntry(home, match.getHomeScore(), match.getAwayScore(), determinePoints(match, home.getTeamName()));
        away = createNewTableEntry(away, match.getAwayScore(), match.getHomeScore(), determinePoints(match, away.getTeamName()));
        table.put(home.getTeamName(), home);
        table.put(away.getTeamName(), away);
        return Pair.of(home, away);
    }

    private LeagueTableEntry createNewTableEntry(LeagueTableEntry team, int goalsFor, int goalsAgainst, int points) {
        return  new LeagueTableEntry(team.getTeamName(),
                team.getPlayed() + 1,
                team.getWon() + points == WIN_POINTS ? 1: 0,
                team.getDrawn() + points == DRAW_POINTS ? 1: 0,
                team.getLost() + points ==  LOSE_POINTS ? 1: 0,
                team.getGoalsFor() + goalsFor,
                team.getGoalsAgainst() + goalsAgainst,
                team.getGoalsFor() - team.getGoalsAgainst(),
                team.getPoints() + points);
    }


    private int determinePoints(Match match, String team) {
        if (match.getHomeScore() == match.getAwayScore()) {
            return DRAW_POINTS;
        } else if (isTeamWinner(match, team)) {
            return WIN_POINTS;
        } else {
            return LOSE_POINTS;
        }
    }

    private boolean isTeamWinner(Match match, String team) {
        return match.getHomeScore() > match.getAwayScore() && match.getHomeTeam().equals(team)
                || match.getAwayScore() > match.getHomeScore() && match.getAwayTeam().equals(team);
    }


    /**
     * Left  = home team
     * Right = away team
     * @param match
     * @return
     */
    private Pair<LeagueTableEntry, LeagueTableEntry> getLeagueEntriesForMatch(Match match) {
        LeagueTableEntry home = new LeagueTableEntry(match.getHomeTeam());
        LeagueTableEntry away = new LeagueTableEntry(match.getAwayTeam());
        home = Optional.ofNullable(table.get(match.getHomeTeam()))
                .orElse(home);
        away = Optional.ofNullable(table.get(match.getAwayTeam()))
                .orElse(away);

        return Pair.of(home, away);
    }
}
