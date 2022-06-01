package com.shehan.bloom.filter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utilize BloomFilter data structure and implements a spell checker library
 * @author Shehan Charuka
 */
public class SpellChecker {

    private static final int DEFAULT_BITMAP_SIZE = 2100000;
    private static final int DEFAULT_HASH_COUNT = 4;
    private static final String UNIX_FILE_PATH = "/usr/share/dict/words";
    private static final String DEFAULT_FILE_PATH = "/words.txt";

    private String actualFilePath;
    private BloomFilter bloomFilter;

    public SpellChecker() throws SpellCheckerException, IOException {
        actualFilePath = null;
        initializeSpellChecker(null, null, null);
    }

    /**
     *
     * @param bitMapSize Pass null to proceed with default value
     * @param hashCount Pass null to proceed with default value
     * @param filePath Pass null to proceed with default value
     * @throws SpellCheckerException
     * @throws IOException
     */
    public SpellChecker(String bitMapSize, String hashCount, String filePath) throws SpellCheckerException, IOException {
        actualFilePath = null;
        initializeSpellChecker(bitMapSize, hashCount, filePath);
    }

    /**
     * Initialize the properties of SpellChecker based on the passed arguments. Uses default values in case of null arguments.
     * @param bitMapSize
     * @param hashCount
     * @param filePath
     * @throws SpellCheckerException
     * @throws IOException
     */
    private void initializeSpellChecker(String bitMapSize, String hashCount, String filePath) throws SpellCheckerException, IOException {
        int numBitMapSize = parseBitmapSizeInput(bitMapSize);
        int numHashCount = parseHashCountInput(hashCount);

        try {
            bloomFilter = new BloomFilter(numBitMapSize, numHashCount);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Could not initialize Bloom-Filter - NoSuchAlgorithmException");
            throw new SpellCheckerException("Could not initialize Bloom-Filter - NoSuchAlgorithmException - "+BloomFilter.HASH_ALGORITHM);
        }
        System.out.println("Initialized Bloom-Filter");
        System.out.println("\t\t Bitmap Size - "+numBitMapSize+" , Hash Count - "+numHashCount);
        System.out.println();

        loadDictionary(filePath);
    }

    /**
     * Determine the size of bitmap
     * @param bmSize
     * @return
     */
    private int parseBitmapSizeInput(String bmSize) {
        int m;
        if(bmSize==null || bmSize.isEmpty()){
            m = DEFAULT_BITMAP_SIZE;
            System.out.println("Bitmap size is not provided. Using default value!");
        } else {
            try {
                m = Integer.parseInt(bmSize);
            } catch(NumberFormatException ex){
                m = DEFAULT_BITMAP_SIZE;
                System.out.println("Invalid Bitmap size is provided. Using default value!");
            }
        }
        return m;
    }

    /**
     * Determine the hash count to be used
     * @param hCount
     * @return
     */
    private int parseHashCountInput(String hCount) {
        int k;
        if(hCount==null || hCount.isEmpty()){
            k = DEFAULT_HASH_COUNT;
            System.out.println("Hash Count size is not provided. Using default value!");
        } else {
            try {
                k = Integer.parseInt(hCount);
            } catch(NumberFormatException ex){
                k = DEFAULT_HASH_COUNT;
                System.out.println("Invalid Hash Count is provided. Using default value!");
            }
        }
        return k;
    }

    /**
     * Prepare the data structure based on a provided file or default file, to do the lookups
     * @param filePath
     * @throws IOException
     */
    private void loadDictionary(String filePath) throws IOException {
        if(filePath==null || filePath.isEmpty()) {
            System.out.println("Dictionary file path is not provided. Using default value!");
        } else {
            if (Files.exists(Paths.get(filePath))) {
                actualFilePath = filePath;
            } else {
                System.out.println("Invalid Dictionary file path is provided. Using default value!");
            }
        }

        if(actualFilePath==null){
            if (Files.exists(Paths.get(UNIX_FILE_PATH))) {
                actualFilePath = UNIX_FILE_PATH;
            } else {
                actualFilePath = DEFAULT_FILE_PATH;
            }
        }

        BufferedReader br = null;
        try {
            if(actualFilePath.equals(DEFAULT_FILE_PATH)){
                br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(actualFilePath)));
            } else {
                br = new BufferedReader(new FileReader(actualFilePath));
            }

            String line;

            while ((line = br.readLine()) != null) {
                bloomFilter.insert(line);
            }

            System.out.println("Dictionary loaded successfully - " + actualFilePath);
            System.out.println();
        } catch (FileNotFoundException ex) {
            System.out.println("Load Dictionary failed due to FileNotFoundException");
            throw ex;
        } catch (IOException ex) {
            System.out.println("Load Dictionary failed due to IOException");
            throw ex;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("WARNING: IOException while closing BufferedReader");
            }
        }
    }

    /**
     * Wrapped lookup function
     * @param word
     * @return
     */
    public boolean lookup(String word){
        return bloomFilter.lookup(word);
    }

    /**
     * Uses to determine the false positive status for successful lookups
     * @param word
     * @return
     */
    public boolean isFalsePositive(String word) {
        boolean isFalsePositive = false;

        if (actualFilePath == null) {
            System.out.println("File Path is not set. Seems dictionary is not loaded. Cannot determine false positives");
            return false;
        }

        BufferedReader br;
        try {
            if(actualFilePath.equals(DEFAULT_FILE_PATH)){
                br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(actualFilePath)));
            } else {
                br = new BufferedReader(new FileReader(actualFilePath));
            }
            Stream<String> stream = br.lines();
            Optional<String> lineHavingTarget = stream.filter(l -> l.contains(word)).findFirst();

            if (!lineHavingTarget.isPresent()) {
                isFalsePositive = true;
            }
        } catch (IOException ex) {
            System.out.println("IOException. Cannot determine false positives");
        }

        return isFalsePositive;
    }
    

}
