package com.jmelzer.wikigraph;

import org.jfree.data.time.TimeSeries;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 */
public class ReadDataFromHtmlTest {

    @Test
    public void testRead() throws Exception {
        ReadDataFromHtml reader = new ReadDataFromHtml();
        assertNull(reader.read(null));

        TimeSeries series = reader.read(FileUtil.readFile("SAWETTABUT.htm"));
        assertNotNull(series);
        assertEquals(77, series.getItemCount());


    }


}