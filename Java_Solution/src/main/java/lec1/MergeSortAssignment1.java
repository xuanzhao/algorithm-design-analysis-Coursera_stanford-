package lec1;

/**
 * @author Ken91
 *
 * Find the number of inversions in an array.
 * (two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j)
 *
 * Input format:
 * the file contains all of the 100,000 integers between 1 and 100,000
 * (inclusive) in some order, with no integer repeated.
 *
 * Output format:
 * An integer indicates the number of inversions in the input array.
 *
 * Time complexity: O(n log n)
 */

import java.io.*;
import java.util.*;

public class MergeSortAssignment1 {

    private static long count = 0;

    private static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() == 1) {
            return list;
        }
        int mid = list.size() / 2;
        List<Integer> leftList = mergeSort(list.subList(0, mid));
        List<Integer> rightList = mergeSort(list.subList(mid, list.size()));
        List<Integer> sortedList = mergeList(leftList, rightList);

        return sortedList;
    }

    private static List<Integer> mergeList(List<Integer> leftList, List<Integer> rightList) {
        List<Integer> sortedList = new ArrayList<Integer>();
        int i = 0, j = 0;

        while (sortedList.size() < leftList.size() + rightList.size()) {
            if (i < leftList.size() && (j >= rightList.size() || leftList.get(i) <= rightList.get(j))) {
                sortedList.add(leftList.get(i++));
            } else {
                sortedList.add(rightList.get(j++));
                count += leftList.size() - i;
            }
        }

        return sortedList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner testFile = new Scanner(new File("IntegerArray.txt"));
        List<Integer> list = new ArrayList<Integer>();
        while (testFile.hasNextInt()) {
            list.add(testFile.nextInt());
        }
        mergeSort(list);
        System.out.println(count);
    }
}