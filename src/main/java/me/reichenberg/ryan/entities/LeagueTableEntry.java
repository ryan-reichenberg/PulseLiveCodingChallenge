package me.reichenberg.ryan.entities;

import java.util.Objects;

// Immutable class = DTO
public class LeagueTableEntry {
    /**
     * name of this team
     */
    private final String teamName;
    /**
     * total games played
     */
    private final int played;
    /**
     * total games won
     */
    private final int won;
    /**
     * total games drawn
     */
    private final int drawn;
    /**
     * total games lost
     */
    private final int lost;
    /**
     * total goals scored by this team
     */
    private final int goalsFor;
    /**
     * total goals against this team
     */
    private final int goalsAgainst;
    /**
     * Average absolute score difference.
     */
    private final int goalDifference;
    /**
     * total points (3 for wins, and 1 for draws, 0 for loss)
     */
    private final int points;

    public LeagueTableEntry(String teamName) {
        this.teamName = teamName;
        this.played = 0;
        this.won = 0;
        this.drawn = 0;
        this.lost = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.goalDifference = 0;
        this.points = 0;
    }

    public LeagueTableEntry(String teamName, int played, int won, int drawn, int lost, int goalsFor, int goalsAgainst, int goalDifference, int points) {
        this.teamName = teamName;
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public int getPoints() {
        return points;
    }

    @Override
    // TODO: The only attribute that matters is the team name
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueTableEntry that = (LeagueTableEntry) o;
        return played == that.played && won == that.won && drawn == that.drawn && lost == that.lost && goalsFor == that.goalsFor && goalsAgainst == that.goalsAgainst && goalDifference == that.goalDifference && points == that.points && Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, played, won, drawn, lost, goalsFor, goalsAgainst, goalDifference, points);
    }

    @Override
    public String toString() {
        return "LeagueTableEntry{" +
                "teamName='" + teamName + '\'' +
                ", played=" + played +
                ", won=" + won +
                ", drawn=" + drawn +
                ", lost=" + lost +
                ", goalsFor=" + goalsFor +
                ", goalsAgainst=" + goalsAgainst +
                ", goalDifference=" + goalDifference +
                ", points=" + points +
                '}';
    }
    
    public static class LeagueTableEntryBuilder {
        /**
         * name of this team
         */
        private String teamName;
        /**
         * total games played
         */
        private int played;
        /**
         * total games won
         */
        private int won;
        /**
         * total games drawn
         */
        private int drawn;
        /**
         * total games lost
         */
        private int lost;
        /**
         * total goals scored by this team
         */
        private int goalsFor;
        /**
         * total goals against this team
         */
        private int goalsAgainst;
        /**
         * total points (3 for wins, and 1 for draws, 0 for loss)
         */
        private int points;

        public static LeagueTableEntryBuilder newBuilder() {
            return new LeagueTableEntryBuilder();
        }

        public LeagueTableEntryBuilder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public LeagueTableEntryBuilder withPlayed(int played) {
            this.played = played;
            return this;
        }

        public LeagueTableEntryBuilder withWon(int won) {
            this.won = won;
            return this;
        }

        public LeagueTableEntryBuilder withDrawn(int drawn) {
            this.drawn = drawn;
            return this;
        }

        public LeagueTableEntryBuilder withLost(int lost) {
            this.lost = lost;
            return this;
        }

        public LeagueTableEntryBuilder incrementWin() {
            this.won += 1;
            return this;
        }

        public LeagueTableEntryBuilder incrementDraw() {
            this.drawn += 1;
            return this;
        }

        public LeagueTableEntryBuilder incrementLoss() {
            this.lost += 1;
            return this;
        }

        public LeagueTableEntryBuilder withGoalsFor(int goalsFor) {
            this.goalsFor = goalsFor;
            return this;
        }

        public LeagueTableEntryBuilder withGoalsAgainst(int goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
            return this;
        }

        public LeagueTableEntryBuilder withPoints(int points) {
            this.points = points;
            return this;
        }

        public LeagueTableEntry build() {
            return new LeagueTableEntry(teamName, played, won, drawn, lost, goalsFor, goalsAgainst,
                    goalsFor - goalsAgainst, points);
        }
    }
}
