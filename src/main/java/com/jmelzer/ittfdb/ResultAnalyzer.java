package com.jmelzer.ittfdb;

import java.util.List;

/**
 * Created by J. Melzer on 31.03.2016.
 * Like queries.
 */
public class ResultAnalyzer {
    //e.g.
    // GAC Group 2015 IxTTF World Tour Swedish Open : Round of 16
    public static String printResults(Player player) {
        StringBuilder buff = new StringBuilder();

        buff.append("{|  class=\"wikitable sortable\"\n" +
                "|- class=\"hintergrundfarbe6\"\n" +
                "! Verband!!   Veranstaltung!!   Jahr!!   Ort!!   Land!!   Einzel!!   Doppel!!   Mixed!!   Team\n");
        List<Result> results = player.getResults();

        boolean newT = true;

        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            if (newT) {
                buff.append(printTournamentResult(result));
            }

            if (i + 1 < results.size()) {
                Result nextResult = results.get(i + 1);
                if (!nextResult.getTournament().equals(result.getTournament())) {
                    newT = true;
                } else {
                    newT = false;
                }
            }

        }
        buff.append("|}");
        return buff.toString();
    }

    private static String printTournamentResult(Result result) {
        //do wiki style
        return "|-\n" +
                "| GER || " + result.getTournament().getType() + " || " +
                result.getTournament().getYear() +  " || " +
                result.getTournament().getCity() + " || " +
                result.getTournament().getCountry() + " || " +
                 result.getRoundAndMedal() + " || || ||\n";
    }
}
