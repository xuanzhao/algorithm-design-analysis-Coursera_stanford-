/******************************************************************************
 * Programming lec1.QuickSortAssignment #1.1 - Week 1, July 2016.
 *
 * Download the text file here.
 * IntegerArray.txt
 *
 * This file contains all of the 100,000 integers between 1 and 100,000
 * (inclusive) in some order, with no integer repeated.
 *
 * Your task is to compute the number of inversions in the file given,
 * where the ith row of the file indicates the ith entry of an array.
 *
 * Because of the large size of this array, you should implement the fast
 * divide-and-conquer algorithm covered in the video lectures.
 *
 * The numeric answer for the given input file should be typed in the space
 * below.
 *
 * So if your answer is 1198233847, then just type 1198233847 in the space
 * provided without any space / commas / any other punctuation marks. You can
 * make up to 5 attempts, and we'll use the best one for grading.
 *
 * (We do not require you to submit your code, so feel free to use any
 * programming language you want --- just type the final numeric answer in the
 * following space.)
 *
 * [TIP: before submitting, first test the correctness of your program on some
 * small test files or your own devising. Then post your best test cases to
 * the discussion forums to help your fellow students!]
 *
 *****************************************************************************/
package lec1;
import java.util.*;
import java.io.*;



public class MergeSortAssignment {

    private static long countInversion(ArrayList<Integer> list) {
        return sortAndCount(list, 0, list.size()-1);
    }

    private static long sortAndCount(ArrayList<Integer> list, int start, int end) {
        if (start >= end) {
            return 0;
        } else {
            int mid = (end - start) / 2 + start;
            long x = sortAndCount(list, start, mid);
            long y = sortAndCount(list, mid+1, end);
            long z = countInversion(list, start, mid+1, end);

            return (x + y + z);
        }
    }

	private static long countInversion(ArrayList<Integer> list, int start, int mid, int end) {
        int i = start, j = mid;
        int invCount = 0;

        int  k = start;
        int[] temp = new int[list.size()];
        while (i < mid && j <= end) {
            if (list.get(i) <= list.get(j)) {
                temp[k++] = list.get(i++);
            } else {
                temp[k++] = list.get(j++);
                invCount += mid - i;
            }
        }
        while (i < mid) {
            temp[k++] = list.get(i++);
        }
        while (j <= end) {
            temp[k++] = list.get(j++);
        }

        for (int l = start; l <= end; l++) {
            list.set(l, temp[l]);
        }

//        ArrayList<Integer> sortedArray = new ArrayList<Integer>();
//        for (int k = 0; k < list.size(); k++) {
//            if (i < mid && j <= end) {
//                if (list.get(i) < list.get(j)) {
//                    sortedArray.add(list.get(i));
//                    i += 1;
//                } else {
//                    sortedArray.add(list.get(j));
//                    j += 1;
//                    invCount += (mid - i);
//                }
//            } else {
//                if (i < mid) {
//                    sortedArray.add(list.get(i));
//                    i += 1;
//                } else if (j <= end){
//                    sortedArray.add(list.get(j));
//                    j += 1;
//                }
//            }
//        }
//
//        int b = 0;
//        for (int a = start; a <= end; a++) {
//            list.set(a, sortedArray.get(b));
//            b += 1;
//        }
        return invCount;
    }

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File("IntegerArray.txt");
		Scanner in = new Scanner(file);
		ArrayList<Integer> list = new ArrayList();
		while (in.hasNext()) {
			list.add(in.nextInt());
		}

//        ArrayList<Integer> list = new ArrayList<Integer>() ;
//        list.add(1);
//        list.add(6);
//        list.add(4);
//        list.add(2);
//        list.add(5);
//        list.add(3);
        System.out.println(countInversion(list));
        System.out.println(list);
    }


}

















