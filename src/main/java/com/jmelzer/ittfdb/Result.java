package com.jmelzer.ittfdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by J. Melzer on 30.03.2016.
 */
public class Result implements Comparable<Result> {

    Tournament tournament;

    Player player1;
    Player player2;

    //4:0 e.g.
    String result;

    //Round of 32
    Round round;

    //11:50 26/3/2016
    String date;

    //Women's Singles
    Category category;

    boolean won;

    final static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public Result(Tournament tournament, Player player1, Player player2, String result, Round round, String date, Category category, boolean won) {
        this.tournament = tournament;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
        this.round = round;
        this.date = date;
        if (date == null || date.isEmpty()) {
            System.err.println(tournament);
            throw new RuntimeException();
        }
        this.category = category;
        this.won = won;
    }

    Date getRealDate() {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.err.println("date null in " + tournament);
            e.printStackTrace();
            return new Date(0);
        }
    }

    public String getRoundAndMedal() {
        if (round == Round.FINAL && won) {
            return "style=\"background-color:gold;\" |Sieger";
        }

        if (round == Round.FINAL && !won) {
            return "style=\"background-color:silver;\" |Silber";
        }
        if (round == Round.SEMI) {
            return "style=\"background-color:#cc9966;\" |Halbfinale";
        }
        if (round == Round.L16) {
            return "Letzte 16";
        }
        if (round == Round.QUARTER) {
            return "Viertelfinale";
        }
        if (round == Round.L32) {
            return "Letzte 32";
        }
        if (round == Round.L64) {
            return "Letzte 64";
        }
        if (round == Round.L128) {
            return "Letzte 128";
        }

        return "";
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public String getResult() {
        return result;
    }

    public Round getRound() {
        return round;
    }

    public String getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Result{" +
                "tournament=" + tournament +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", result='" + result + '\'' +
                ", round='" + round + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int compareTo(Result o) {
        return getRealDate().compareTo(o.getRealDate());
    }
}
