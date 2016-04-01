package com.jmelzer.wikigraph;

import java.io.*;

/**
 * Created by J. Melzer on 30.03.2016.
 */
public class FileUtil {
    public static String readFile(String file) throws IOException {
        File currentDir = new File(".");
        String fn = currentDir.getAbsolutePath() + "/src/main/resources/" + file;
        System.out.println("open file " + fn);
        if (!new File(fn).exists()) throw new IOException("File "+ fn + " doesn't exists");

        InputStream in = new FileInputStream(fn);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return StringUtils.unescapeHtml3(stringBuilder.toString());
    }
}
