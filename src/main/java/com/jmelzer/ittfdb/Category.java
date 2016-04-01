package com.jmelzer.ittfdb;


/**
 * Created by J. Melzer on 31.03.2016.
 */
public enum Category {
    W_SINGLE, M_SINGLE, M_JUNIOR_SINGLE, M_U21_SINGLE, W_JUNIOR_SINGLE;

    public static Category translate(String value) {
        switch (value) {
            case "Women's Singles":
                return W_SINGLE;
            case "U21 Men's Singles":
                return M_U21_SINGLE;
            case "Junior Boys' Singles":
                return M_JUNIOR_SINGLE;
            case "Men's Singles":
                return M_SINGLE;
            default:
                throw new IllegalArgumentException(value);

        }
    }
}