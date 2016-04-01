package com.jmelzer.ittfdb;

import com.jmelzer.com.jmelzer.base.AbstractParser;
import com.jmelzer.com.jmelzer.base.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by J. Melzer on 30.03.2016.
 * Parse the webpages
 */
public class ParseITTFResults extends AbstractParser {
    final String regex = "<a.*Page=[1-9][0-9]?#.*>Next";

    public Player readAllPages(String url, String name, String playerId) {
        String page = Client.getPage(url);

        Player player = new Player(playerId, name);
        read(page, player);
        while (hasNextPage(page)) {
            String next = readNextUrl(page);
            next = getHttpAndDomain(url) + "/competitions/" + next;
            System.out.println("next = " + next);
            page = Client.getPage(next);
            read(page, player);
        }
        return player;
    }

    public static String getHttpAndDomain(String url) {
        if (url == null) {
            return null;
        }
        return url.substring(0, url.indexOf(".com") + 4);
    }

    private String readNextUrl(String page) {
        Pattern pattern = Pattern.compile(regex);
        Matcher regexMatcher = pattern.matcher(page);
        boolean b = regexMatcher.find();
        if (b) {
            String s = regexMatcher.group(0);
            String url = readBetween(s, 0, "\"", "\"").result;
            return url;
        }
        throw new RuntimeException();
    }

    boolean hasNextPage(String page) {

        Pattern pattern = Pattern.compile(regex);
        Matcher regexMatcher = pattern.matcher(page);
        boolean b = regexMatcher.find();
        if (b) {
            System.out.println(regexMatcher.group(0));
        }
        return (b);
    }

    Player read(String page, String name, String playerId) {
        return read(page, new Player(playerId, name));
    }

    Player read(String page, Player player) {
        if (page == null) {
            return null;
        }

        ParseResult result = readBetween(page, 0, "<table cellspacing=\"1\" bordercolor=\"#000080\" style=\"border-collapse: collapse; border-width: 1px\" width=\"100%\">",
                "</table>");

        int n = 0;
        int c = 0;
        while (true) {
            ParseResult tr = readBetween(result.result, n, "<tr", "</tr>");
            if (tr == null) {
                break;
            }

            n = tr.end;

            String[] cells = tableRowAsArray(tr.result, 6);
            if (cells[1].isEmpty()) {
                break;
            }

//            for (String cell : cells) {
//                System.out.println("cell = " + cell);
//            }
            ParseResult resultTourEtc = readBetweenOpenTag(cells[0], 0, "<font", "</font>");
            String tourEtc = resultTourEtc.result;

            //first line is tourname
            ParseResult tourResult = readBetween(tourEtc, 0, null, "<br>");
            String tourName = tourResult.result;
            ParseResult roundResult = readBetween(tourEtc, tourResult.end, null, "<br>");
            String roundName = roundResult.result;
            ParseResult catResult = readBetween(tourEtc, roundResult.end, null, "<b>");
            String catName = catResult.result;
            Category cat = null;
            System.out.println("cat = " + catName);
            try {
                cat = Category.translate(catName);
            } catch (IllegalArgumentException e) {
                continue;
            }
            System.out.println("cat = " + cat);

            String date = readBetween(cells[0], resultTourEtc.end, null, null).result;

            //cell[1] contains player
            Player p1 = readPlayer(cells[1]);
            Player p2 = readPlayer(cells[3]);
            String resultStr = readBetweenOpenTag(cells[5], 0, "<font", "<br>").result;

            boolean won = cells[5].contains(player.getName());

            try {
                Result tournamenResult = new Result(new Tournament(tourName,
                        parseTypeFromTourname(tourName, cat),
                        parseCityFromTourname(tourName),
                        parseCountryFromTourname(tourName),
                        parseYearFromTourname(tourName)),
                        p1,
                        p2,
                        resultStr,
                        Round.translate(roundName),
                        date,
                        cat,
                        won);
                player.addResult(tournamenResult);
            } catch (RuntimeException e) {
                System.err.println("Ignoring tournament '" + tourName + "'");
            }

        }

        return player;
    }

    String parseYearFromTourname(String tourName) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher regexMatcher = pattern.matcher(tourName);
        boolean b = regexMatcher.find();
        if (b) {
            return regexMatcher.group(0);
        }
        throw new RuntimeException();
    }

    String parseCountryFromTourname(String tourName) {
        String arr[] = tourName.split(",", 6);
        return arr[arr.length - 1].trim();
    }

    String parseCityFromTourname(String tourName) {
        String arr[] = tourName.split(",", 6);
        return translateCity(arr[arr.length - 2].trim());
    }

    private String translateCity(String city) {
        switch (city) {
            case "Warsaw":
                return "Warschau";
            case "Kuwait City":
                return "Kuwait Stadt";
        }
        return city;
    }

    private String parseTypeFromTourname(String name, Category cat) {
        String result = null;
        if (name.contains("ITTF World Tour") || name.contains("ITTF EURO AFRICA CIRCUIT")
                || name.contains("ITTF Pro Tour")) {
            result = "Pro Tour";
        }
        if (name.contains("ITTF European Table Tennis Championships")) {
            result = "Europameisterschaft";
        }
        if (name.contains("European Championships")) {
            result = "Europameisterschaft";
        }
        if (name.contains("European Games")) {
            result = "Europaspiele";
        }
        if (name.contains("World Cup")) {
            result = "World Cup";
        }
        if (name.contains("Europe TOP 12")) {
            result = "EURO-TOP12";
        }
        if (name.contains("World Table Tennis Championships")) {
            result = "Weltmeisterschaft";
        }
        if (name.contains("Asian Championships")) {
            result = "Asienspiele";
        }
        if (name.contains("Junior Open") || name.contains("ITTF Junior Circuit")) {
            result = "ITTF Junior Circuit";
        }
        if (name.contains("English Open")) {
            result = "Pro Tour";
        }
        if (name.contains("World Junior Table Tennis Championships")) {
            result = "Jugend-Weltmeisterschaft";
        }

        if (cat == Category.M_U21_SINGLE) {
            result += " (U21)";
        }
        if (result == null) {
            throw new RuntimeException(name);
        }
        return result;
    }

    private Player readPlayer(String cell) {
        String parsablePlayerName = readBetweenOpenTag(cell, 0, "<a", "</a>").result;
        String id = readBetween(cell, 0, "P_ID=", "&").result;
        String name = cleanPlayerName(parsablePlayerName);
        return new Player(id, name);
    }

    String cleanPlayerName(String parsablePlayerName) {
        if (parsablePlayerName.contains("^")) {
            return parsablePlayerName.substring(0, parsablePlayerName.indexOf("^")).trim();
        } else {
            return parsablePlayerName.substring(0, parsablePlayerName.lastIndexOf(" ")).trim();

        }
    }

}
