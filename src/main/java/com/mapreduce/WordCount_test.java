package com.mapreduce;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WordCount_test {
    static HashMap<String, Integer> wordsMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("data/NginxData.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = StringUtils.split(line, "\t");
                String domain = fields[4];
                count(domain);
            }
            IOUtils.closeStream(br);
            File file = new File("data/word_count.txt");
            if (file.exists()) throw new RuntimeException("输出文件已经存在");
            FileWriter fw = new FileWriter(file, true);
            for (Map.Entry<String, Integer> ent : wordsMap.entrySet()) {
                fw.write(ent.getKey() + "\t" + ent.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void count(String line) {
        String[] words = line.split(" ");
        Integer count;
        for (String word : words) {
            count = wordsMap.get(word);
            if (count == null)
                wordsMap.put(word, 1);
            else
                wordsMap.put(word, count + 1);
        }
    }
}
