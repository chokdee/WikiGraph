package com.jmelzer.ittfdb;

import com.jmelzer.wikigraph.FileUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by J. Melzer on 30.03.2016.
 * test
 */
public class ParseITTFResultsTest {
    ParseITTFResults parser = new ParseITTFResults();

    @Test
    public void testRead() throws Exception {


        assertNull(parser.read(null, null, null));

        Player player = parser.read(FileUtil.readFile("ittf-results-sample-2.htm"), "HAN Ying", "118762");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println("r = " + r);
    }

    @Test
    public void hasNextPage() throws Exception {
        assertFalse(parser.hasNextPage("blabla"));
        assertTrue(parser.hasNextPage(FileUtil.readFile("ittf-results-sample.htm")));
        assertTrue(parser.hasNextPage(FileUtil.readFile("ittf-results-sample-2.htm")));
        assertFalse(parser.hasNextPage(FileUtil.readFile("ittf-results-sample-last.htm")));
    }

    @Test
    public void testIntegration() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=118762",
                "HAN Ying", "118762");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println("r = " + r);
    }

    @Test
    public void testIntegrationOVTCHAROV() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=107028",
                " OVTCHAROV Dimitrij", "107028");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println(r);
    }

    @Test
    public void testIntegrationBo() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=111606",
                "FANG Bo", "111606");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println(r);
    }

    @Test
    public void testIntegrationWalther() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=109946",
                "WALTHER Ricardo", "109946");
        assertNotNull(player);

        Collections.sort(player.getResults());
        Collections.reverse(player.getResults());

        for (Result result : player.getResults()) {
            System.out.println("result = " + result);
        }
        String r = ResultAnalyzer.printResults(player);
        System.out.println(r);
    }

    @Test
    public void testIntegrationSawettabut() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=111833",
                "SAWETTABUT Suthasini", "111833");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println(r);
    }

    @Test
    public void testparseCityFromTourname() throws Exception {
        assertEquals("Wels",
                parser.parseCityFromTourname("GAC Group 2015 ITTF World Tour, Austrian Open (Major),02 Sep 2015 - 06 Sep 2015, Wels, AUT "));
        assertEquals("Berlin",
                parser.parseCityFromTourname("GAC Group 2013 ITTF World Tour, German Open, Super Series,13 Nov 2013 - 17 Nov 2013, Berlin, GER"));

    }

    @Test
    public void parseYearFromTourname() throws Exception {
        assertEquals("2015",
                parser.parseYearFromTourname("GAC Group 2015 ITTF World Tour, Austrian Open (Major),02 Sep 2015 - 06 Sep 2015, Wels, AUT "));

    }

    @Test
    public void t() throws ParseException {
        //11:50 26/3/2016
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        formatter.parse("11:50 26/3/2016");
    }

    @Test
    public void parseCountryFromTourname() throws Exception {
        assertEquals("AUT",
                parser.parseCountryFromTourname("GAC Group 2015 ITTF World Tour, Austrian Open (Major),02 Sep 2015 - 06 Sep 2015, Wels, AUT "));
        assertEquals("GER",
                parser.parseCountryFromTourname("GAC Group 2013 ITTF World Tour, German Open, Super Series,13 Nov 2013 - 17 Nov 2013, Berlin, GER "));

    }

    @Test
    public void testIntegration2() throws Exception {
        Player player = parser.readAllPages("http://www.ittf.com/competitions/matches_per_player_all.asp?P_ID=116627",
                "LEMMER Alena", "116627");
        assertNotNull(player);

        String r = ResultAnalyzer.printResults(player);
        System.out.println("r = " + r);
    }

    @Test
    public void testcleanPlayerName() {
        assertEquals("HAN Ying", parser.cleanPlayerName("HAN Ying ^ GER"));
    }
}