package me.reichenberg.ryan.entities;

import me.reichenberg.ryan.comparators.LeagueTableEntryComparator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// Mutable entity - maintains state
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
        matches.forEach(match -> {
            Pair<LeagueTableEntry, LeagueTableEntry> leagueEntries = getLeagueEntriesForMatch(match);
            updateLeagueTableEntries(match, leagueEntries.getLeft(), leagueEntries.getRight());
        });

        return table
                .values()
                .stream()
                .sorted(new LeagueTableEntryComparator())
                .collect(Collectors.toList());
    }

    private void updateLeagueTableEntries(Match match, LeagueTableEntry home, LeagueTableEntry away) {
        Pair<MatchOutcome, MatchOutcome> outcomes = determineMatchOutcomes(match, home.getTeamName());
        table.put(home.getTeamName(), createNewTableEntry(home, match.getHomeScore(), match.getAwayScore(), outcomes.getLeft()));
        table.put(away.getTeamName(), createNewTableEntry(away, match.getAwayScore(), match.getHomeScore(), outcomes.getRight()));
    }

    private LeagueTableEntry createNewTableEntry(LeagueTableEntry team, int goalsFor, int goalsAgainst, MatchOutcome outcome) {
        LeagueTableEntry.LeagueTableEntryBuilder builder = LeagueTableEntry.LeagueTableEntryBuilder.newBuilder();

        builder.withTeamName(team.getTeamName())
        .withPlayed(team.getPlayed() + 1)
        .withWon(team.getWon())
        .withLost(team.getLost())
        .withDrawn(team.getDrawn())
        .withPoints(team.getPoints())
        .withGoalsFor(team.getGoalsFor() + goalsFor)
        .withGoalsAgainst(team.getGoalsAgainst() + goalsAgainst);
        calculateWinDrawLoss(builder, outcome);
        return builder.build();
    }

    private void calculateWinDrawLoss(LeagueTableEntry.LeagueTableEntryBuilder builder, MatchOutcome outcome) {
        switch(outcome) {
            case WIN:
                builder.incrementWin();
                break;
            case DRAW:
                builder.incrementDraw();
                break;
            default:
                builder.incrementLoss();
        }
    }


    /**
     * Determines outcomes of the match E.g. which team won and which team lost
     * @param match
     * @param home
     * @return Pair of outcomes (left = outcome for home team, right = outcome for away team)
     */
    private Pair<MatchOutcome, MatchOutcome> determineMatchOutcomes(Match match, String home) {
        if (match.getHomeScore() == match.getAwayScore()) {
            return Pair.of(MatchOutcome.DRAW, MatchOutcome.DRAW);
        } else if (isTeamWinner(match, home)) {
            return Pair.of(MatchOutcome.WIN, MatchOutcome.LOSE);
        } else {
            return Pair.of(MatchOutcome.LOSE, MatchOutcome.WIN);
        }

    }

    /**
     * Check if the passed in team is the winner
     * @param match
     * @param team
     * @return true if the team won
     */
    private boolean isTeamWinner(Match match, String team) {
        return match.getHomeScore() > match.getAwayScore() && match.getHomeTeam().equals(team);
    }

    /**
     * Look up Map for Team entries otherwise create them if thet don't exist
     * @param match
     * @return Pair of team entries (left = home, right = away)
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
