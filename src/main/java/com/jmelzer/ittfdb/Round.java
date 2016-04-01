package com.jmelzer.ittfdb;

/**
 * Created by J. Melzer on 31.03.2016.
 * Result round
 */
public enum Round {
    PRE, L256, L128, L64, L32, L16, QUARTER, SEMI, FINAL;

    public static Round next(Round r) {
        switch (r) {
            case L128:
                return L64;
            case L64:
                return L32;
            case L32:
                return L16;
            case L16:
                return QUARTER;
            case QUARTER:
                return SEMI;
            case SEMI:
                return FINAL;
            default:
                throw new IllegalArgumentException(r.toString());
        }
    }
    public static Round translate(String s) {
        switch (s) {
            case "Quarter Final":
                return QUARTER;
            case "Round of 16":
                return L16;
            case "Round of 32":
                return L32;
            case "Final":
                return FINAL;
            case "Semi Final":
                return SEMI;
            case "Round of 64":
                return L64;
            case "Round of 128":
                return L128;
            case "Round of 256":
                return L256;
            default:
                throw new IllegalArgumentException(s);
        }
    }
}
