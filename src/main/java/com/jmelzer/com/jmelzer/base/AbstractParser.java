package com.jmelzer.com.jmelzer.base;

import java.util.Locale;

/**
 * Created by J. Melzer on 30.03.2016.
 */
public abstract class AbstractParser {
    static public class ParseResult {
        public String result;

        public int end;

        public ParseResult(String result, int end) {
            trim(result);
            this.end = end;
        }

        private void trim(String result) {
            if (result != null) {
                //non-breaking space
                this.result = result.trim().replace("\u00A0", " ");
            }
        }

        public boolean isEmpty() {
            return result == null || result.isEmpty();
        }
    }


    protected ParseResult readBetweenOpenTag(String page, int start, String tagStart, String tagEnd) {
        ParseResult result = readBetween(page, start, tagStart, tagEnd, false);
        if (result != null) {
            ParseResult result2 = readBetween(result.result, 0, ">", null, false);
            result2.end = result.end;
            return result2;
        }
        return null;
    }

    protected ParseResult readBetween(String page, int start, String tagStart, String tagEnd, boolean ignoreCase) {
        String toSearch = "";
        if (ignoreCase) {
            //todo ??
            toSearch = page.toLowerCase(Locale.GERMAN);
        } else {
            toSearch = page;
        }
        int s = start;
        int l = 0;
        if (tagStart != null) {
            s = toSearch.indexOf(tagStart, start);
            if (s == -1) {
                return null;
            }
            l = tagStart.length();
        }
        int idxEndTag = 0;
        int end = 0;

        if (tagEnd != null) {
            idxEndTag = toSearch.indexOf(tagEnd, s + l);
            end = idxEndTag + tagEnd.length();
        } else {
            end = idxEndTag = page.length();
        }
        if (idxEndTag == -1) {
            return new ParseResult(null, end);
        } else {
            return new ParseResult(page.substring(s + l, idxEndTag), end);
        }
    }

    protected ParseResult readBetween(String page, int start, String tagStart, String tagEnd) {
        return readBetween(page, start, tagStart, tagEnd, false);
    }


    protected String[] tableRowAsArray(String tr, int validsize) {
        String[] arr = new String[validsize];
        int startIdx = 0;
        for (int i = 0; i < validsize; i++) {
            ParseResult td = readBetweenOpenTag(tr, startIdx, "<td", "</td>");
            if (td != null) {
                startIdx = td.end;
                arr[i] = td.result.trim();
            } else {
                arr[i] = "";
            }
        }
        return arr;
    }
}
