package lec6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/******************************************************************************
 * Programming Assignment #6.1 - Week 6, August 2016.
 *
 * Download the following text file:
 * algo1-programming_prob-2sum.txt
 *
 * The goal of this problem is to implement a variant of the 2-SUM algorithm
 * (covered in the Week 6 lecture on hash table applications).
 *
 * The file contains 1 million integers, both positive and negative (there might
 * be some repetitions!).This is your array of integers, with the ith row of the
 * file specifying the ith entry of the array.
 *
 * Your task is to compute the number of target values t in the interval
 * [-10000,10000] (inclusive) such that there are distinct numbers x,y in the
 * input file that satisfy x+y=t. (NOTE: ensuring distinctness requires a
 * one-line addition to the algorithm from lecture.)
 *
 * Write your numeric answer (an integer between 0 and 20001) in the space
 * provided.
 *
 * OPTIONAL CHALLENGE: If this problem is too easy for you, try implementing
 * your own hash table for it. For example, you could compare performance under
 * the chaining and open addressing approaches to resolving collisions.
 *
 *****************************************************************************/


public class TwoSum {

    private static final int N = 1000000;
    private static final int tMin = -10000;
    private static final int tMax = 10000;

    private static Long[] readFromFile(String filename) {
        Long[] A = new Long[N];
        try (Scanner in = new Scanner(new File(filename))) {
            int size = A.length;
            for (int i = 0; in.hasNext() && i < size; i++) {
                A[i] = in.nextLong();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return A;
    }

    private static int TwoSumBisec(Long[] array) {
        Arrays.sort(array);
        int size = array.length;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            Long x = array[i];
            for (int t = tMin; t<= tMax; t++) {
                Long y = Long.valueOf(t) - x;
                if (Arrays.binarySearch(array, y) >= 0 && x.compareTo(y) != 0) {
                    set.add(t);
                    break;
                }
            }
        }
        return set.size();
    }

    private static int TwoSumHash(Long[] array) {
        Set<Long> arraySet = new HashSet<Long>(Arrays.asList(array));
        Set<Integer> set = new HashSet<>();
        Iterator<Long> itor = arraySet.iterator();
        while (itor.hasNext()) {
            Long x = itor.next();
            for (int t = tMin; t<= tMax; t++) {
                Long y = Long.valueOf(t) - x;
                if (arraySet.contains(y) && x.compareTo(y) != 0) {
                    set.add(t);
                    break;
                }
            }
        }
        return set.size();
    }

    public static void main(String[] args) {
        Long[] A = readFromFile("algo1-programming_prob-2sum.txt");
        Long[] A1 = A.clone();
        Long[] A2 = A.clone();

        long start, end;
        start = System.currentTimeMillis();
        int count1 = TwoSumBisec(A1);
        end = System.currentTimeMillis();
        System.out.println("bi search took " + (end - start) + "milliseconds.");

        start = System.currentTimeMillis();
        int count2 = TwoSumHash(A2);
        end = System.currentTimeMillis();
        System.out.println("Hash search took " + (end - start) + "milliseconds.");

        if (count1 != count2) {
            System.out.println("two methods got different result");
        } else {
            System.out.println("The result is " + count1);
        }
    }
}