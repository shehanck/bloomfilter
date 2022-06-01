package com.shehan.bloom.filter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

/**
 * Bloom-Filter data structure that can be used to check the membership of a Set
 * @author Shehan Charuka
 */
public class BloomFilter {
    public static final String HASH_ALGORITHM = "MD5";

    private final int bitMapSize;
    private final int numHashes;
    private final BitSet bitmap;
    private final MessageDigest md;

    public BloomFilter(int bitMapSize, int numHashes) throws NoSuchAlgorithmException {
        this.bitMapSize = bitMapSize;
        this.numHashes = numHashes;
        bitmap = new BitSet(bitMapSize);
        md = MessageDigest.getInstance(HASH_ALGORITHM);
    }

    /**
     * Reset the bitmap to initial state
     */
    public final void resetBitMap() {
        bitmap.clear();
    }

    /** One of 2 main operations of bloom-filter.
     * Takes a String input, calculate defined number of hashes and update the bitmap accordingly.
     *
     * @param word
     */
    public void insert(String word) {
        String wordHash = getHash(word);
       
        int hashLength = wordHash.length();
        int subHashSize = (int) (Math.ceil((double) hashLength / (double) numHashes));
        int start = 0;

        while (start < hashLength) {
            String subHash;
            if (start + subHashSize > hashLength) {
                subHash = wordHash.substring(start, hashLength);
            } else {
                subHash = wordHash.substring(start, start + subHashSize);
            }
            long subHashVal = Long.parseLong(subHash);
            int bitmapIndex = (int) (subHashVal % bitMapSize);
            bitmap.set(bitmapIndex);
            start = start + subHashSize;
        }
    }

    /** One of 2 main operations of bloom-filter.
     * Takes a String input, calculate defined number of hashes and check in the bitmap to determine the membership.
     *
     * @param word
     */
    public boolean lookup(String word) {
        boolean isAvailable = true;

        String wordHash = getHash(word);

        int hashLength = wordHash.length();
        int subHashSize = (int) (Math.ceil((double) hashLength / (double) numHashes));

        int start = 0;

        while (start < hashLength) {
            String subHash;
            if (start + subHashSize > hashLength) {
                subHash = wordHash.substring(start, hashLength);
            } else {
                subHash = wordHash.substring(start, start + subHashSize);
            }
            long subHashVal = Long.parseLong(subHash);
            int bitmapIndex = (int) (subHashVal % bitMapSize);

            if (!bitmap.get(bitmapIndex)) {
                isAvailable = false;
                break;
            }

            start = start + subHashSize;
        }

        return isAvailable;
    }

    /** Major supporting operation for the core operations of bloom-filter.
     * Takes a String input, calculate the MD5 hash and return it to the core-operation calling this.
     *
     * @param word
     */
    private String getHash(String word) {
        String wordHash;

        md.update(word.getBytes());
        wordHash = new BigInteger(1, md.digest()).toString(10);

        return wordHash;
    }

}
