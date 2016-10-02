package edu.ufl.sayak;

import edu.stanford.nlp.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created by sayak on 18/9/16.
 */
public class FOMarkovModel {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(
                                                new File("src/edu/ufl/sayak/markov_tag.txt"))));
        String line = null;
        String[] words = null;
        while((line = br.readLine()) != null) {
            words = line.split(",");
        }
        System.out.println("Word Length :: " + words.length);
        HashMap<String, Integer> wordCount = new HashMap<>();
        List<String> wordList = new ArrayList<>();
        for (String word:
             words) {
            //System.out.println(word);
            wordList.add(word);
            if(wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
        }
        System.out.println("Number of states :: " + wordCount.size());
        for (Map.Entry<String, Integer> entry:
             wordCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        Collection<String> bigrams = StringUtils.getNgrams(wordList, 2, 2);
        HashMap<String, Integer> bigramMap = new HashMap<>();
        for (String word:
             bigrams) {
            //System.out.println(word);
            if(bigramMap.containsKey(word)) {
                bigramMap.put(word, bigramMap.get(word) + 1);
            } else {
                bigramMap.put(word, 1);
            }
        }
        HashMap<String, Double> bigramProb = new HashMap<>();
        HashMap<String, Double> bigramProbLaplaceSmoothing = new HashMap<>();
        for (Map.Entry<String, Integer> entry:
             bigramMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            String[] tokens = entry.getKey().split(" ");
            bigramProb.put("P(" + tokens[1] + "|" + tokens[0] + ")", (double)entry.getValue() / wordCount.get(tokens[0]));
            bigramProbLaplaceSmoothing.put("P(" + tokens[1] + "|" + tokens[0] + ")",
                    (double)(entry.getValue() + 1) / (wordCount.get(tokens[0]) + wordCount.size()));
        }
        for (Map.Entry<String, Double> entry:
             bigramProb.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("###Laplace Smoothing###");
        for (Map.Entry<String, Double> entry:
                bigramProbLaplaceSmoothing.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
