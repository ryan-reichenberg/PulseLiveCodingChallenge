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
        Pair<MatchOutcome, MatchOutcome> outcomes = determineMatchOutcomes(match, home.getTeamName());

        home = createNewTableEntry(home, match.getHomeScore(), match.getAwayScore(), outcomes.getLeft());
        away = createNewTableEntry(away, match.getAwayScore(), match.getHomeScore(), outcomes.getRight());
        table.put(home.getTeamName(), home);
        table.put(away.getTeamName(), away);
        return Pair.of(home, away);
    }

    private LeagueTableEntry createNewTableEntry(LeagueTableEntry team, int goalsFor, int goalsAgainst, MatchOutcome outcome) {
        LeagueTableEntry.LeagueTableEntryBuilder builder  = LeagueTableEntry.LeagueTableEntryBuilder.newBuilder();
        builder.withTeamName(team.getTeamName());
        builder.withPlayed(team.getPlayed() + 1);
        updateWinDrawLoss(builder, team, outcome);
        builder.withPoints(team.getPoints() + determinePoints(outcome));
        builder.withGoalsFor(team.getGoalsFor() + goalsFor);
        builder.withGoalsAgainst(team.getGoalsAgainst() +goalsAgainst);
        return builder.build();
    }

    private void updateWinDrawLoss(LeagueTableEntry.LeagueTableEntryBuilder builder, LeagueTableEntry team, MatchOutcome outcome) {
        builder.withWon(team.getWon());
        builder.withLost(team.getLost());
        builder.withDrawn(team.getDrawn());
        if (outcome == MatchOutcome.DRAW) {
            builder.incrementDraw();
        } else if (outcome == MatchOutcome.WIN) {
            builder.incrementWin();
        } else {
            builder.incrementLoss();
        }
    }


    private Pair<MatchOutcome, MatchOutcome> determineMatchOutcomes(Match match, String home) {
        if (match.getHomeScore() == match.getAwayScore()) {
            return Pair.of(MatchOutcome.DRAW, MatchOutcome.DRAW);
        } else if (isTeamWinner(match, home)) {
            return Pair.of(MatchOutcome.WIN, MatchOutcome.LOSE);
        } else {
            return Pair.of(MatchOutcome.LOSE, MatchOutcome.WIN);
        }

    }
    private int determinePoints(MatchOutcome outcome) {
       switch(outcome) {
           case WIN:
               return WIN_POINTS;
           case DRAW:
               return DRAW_POINTS;
           default:
               return LOSE_POINTS;
       }
    }

    private boolean isTeamWinner(Match match, String team) {
        return (match.getHomeScore() > match.getAwayScore() && match.getHomeTeam().equals(team))
                || (match.getAwayScore() > match.getHomeScore() && match.getAwayTeam().equals(team));
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
