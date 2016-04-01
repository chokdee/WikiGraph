package com.jmelzer.wikigraph;

import com.jmelzer.com.jmelzer.base.AbstractParser;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by J. Melzer on 30.03.2016.
 */
public class ReadDataFromHtml extends AbstractParser {

    public TimeSeries read(String page) {
        if (page == null) return null;
        TimeSeries s1 = new TimeSeries("Platzierungen");

        ParseResult result = readBetween(page, 0, "<table bgcolor=\"#FFFFFF\"", "</table>");
        int n = 0;
        int c = 0;
        while (true) {
            ParseResult tr = readBetween(result.result, n, "<tr", "</tr>");
            if (tr == null) break;

            n = tr.end;

            if (c++ < 2) continue; //skip header


            String[] cells = tableRowAsArray(tr.result, 5);
            for (String cell : cells) {
                System.out.println("cell = " + cell);
            }
            try {
                int pos = Integer.valueOf(cells[3]);
                if (pos > 0)
                    s1.add(createMonth(cells[0], cells[1]), pos);
            } catch (SeriesException | NumberFormatException e) {
//                e.printStackTrace();
            }
        }
        return s1;
    }

    private Month createMonth(String cell, String cell1) {
        SimpleDateFormat df = new SimpleDateFormat("MMMMM", Locale.ENGLISH);
        try {
            return new Month(df.parse(cell).getMonth() + 1, Integer.valueOf(cell1));
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

}
