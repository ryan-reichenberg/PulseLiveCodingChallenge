package me.reichenberg.ryan.comparators;

import me.reichenberg.ryan.entities.LeagueTableEntry;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public class LeagueTableEntryComparator implements Comparator<LeagueTableEntry> {
    @Override
    public int compare(LeagueTableEntry o1, LeagueTableEntry o2) {
        return new CompareToBuilder()
                .append(o2.getPoints(), o1.getPoints())
                .append(o2.getGoalDifference(), o1.getGoalDifference())
                .append(o2.getGoalsFor(), o1.getGoalsFor())
                .append(o1.getTeamName(), o2.getTeamName())
                .build();
    }
}
