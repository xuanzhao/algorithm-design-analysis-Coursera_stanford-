package lec6;

/**
 * Created by ken on 11/28/2016 AD.
 * Input Format:
 * The text file contains a list of the integers from 1 to 10000 in unsorted
 * order; you should treat this as a stream of numbers, arriving one by one.
 * Letting xi denote the ith number of the file, the kth median mk is defined
 * as the median of the numbers x1,…,xk. (So, if k is odd, then mk is ((k+1)/2)th
 * smallest number among x1,…,xk; if k is even, then mk is the (k/2)th smallest
 * number among x1,…,xk.)
 *
 * Output Format:
 * Return the sum of these 10000 medians, modulo 10000 (i.e., only the last 4
 * digits), that is (m1+m2+m3+⋯+m10000)mod10000.
 *
 * Time Complexity: O(log(k)) for each median
 */

import java.io.*;
import java.util.*;

public class MedianMaintenance {
    private PriorityQueue<Integer> minHeap; // right side
    private PriorityQueue<Integer> maxHeap; // left side
    private int size;

    public MedianMaintenance() {
        minHeap = new PriorityQueue<>(5000);
        maxHeap = new PriorityQueue<>(5000, Collections.reverseOrder());
        size = 0;
    }

    public void add(int n) {
        if (size == 0) {
            maxHeap.add(n);
            size+=1;
            return;
        }
        if (size % 2 == 0) {  // size will be odd, so add new item to left side (maxHeap)
            if (n > minHeap.peek()) {
                maxHeap.add(minHeap.poll());
                minHeap.add(n);
            } else {
                maxHeap.add(n);
            }
        } else {             // size will be even, so add new item to right side (minHeap)
            if (n < maxHeap.peek()) {
                minHeap.add(maxHeap.poll());
                maxHeap.add(n);
            } else {
                minHeap.add(n);
            }

        }
        size += 1;
    }


    public int median() {
        return maxHeap.peek();
    }

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        MedianMaintenance med = new MedianMaintenance();
        Scanner in = new Scanner(new File("Median.txt"));
        while (in.hasNextInt()) {
            med.add(in.nextInt());
            sum += med.median();
        }
        System.out.println("sum % 10000 is " + sum % 10000);
    }
}
