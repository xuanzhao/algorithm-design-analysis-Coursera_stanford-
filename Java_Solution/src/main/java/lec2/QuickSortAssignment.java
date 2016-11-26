package lec2;

import com.sun.tools.corba.se.idl.InterfaceGen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by ken on 11/26/2016 AD.
 * recursive version.
 */
public class QuickSortAssignment {

    private static int comparisions;

//    private static void swap(ArrayList<Integer> list, int idx1, int idx2) {
//        int tempVal = list.get(idx1);
//        list.set(idx1, list.get(idx2));
//        list.set(idx2, tempVal);
//    }

    private static int lookforMid(ArrayList<Integer> list, int left, int right, int mid) {
        if (list.get(left) > list.get(mid) && list.get(left) < list.get(right) ||
                list.get(left) > list.get(right) && list.get(left) < list.get(mid)) {
            return left;
        }
        if (list.get(mid) > list.get(left) && list.get(mid) < list.get(right) ||
                list.get(mid) > list.get(right) && list.get(mid) < list.get(left)) {
            return mid;
        }
        if (list.get(right) > list.get(left) && list.get(right) < list.get(mid) ||
                list.get(right) > list.get(mid) && list.get(right) < list.get(left)) {
            return right;
        }
        return mid;
    }

    private static int qsortPartition(ArrayList<Integer> list, int left, int right, int pivot) {
        comparisions += (right - left);

        int pVal = list.get(pivot);
        if (pivot != left) {
            Collections.swap(list, left, pivot);
        }

        int i = left + 1;
        for (int j = left + 1; j <= right; j++) {
            if (list.get(j) < pVal) {
                Collections.swap(list, i, j);
                i += 1;
            }
        }
        Collections.swap(list, i-1, left);
        return (i - 1);
    }

    private static void quickSortHelper(ArrayList<Integer> list, int left, int right) {
        if (left >= right) return;

        int midValIdx = lookforMid(list, left, right, (right-left) / 2 + left);
        int pivot = qsortPartition(list, left, right, midValIdx);

        // notice that pivot is in the right position, so do not need sort again.
        quickSortHelper(list, left, pivot-1);
        quickSortHelper(list, pivot+1, right);

    }


    public static ArrayList<Integer> quickSort(ArrayList<Integer> list) {
        quickSortHelper(list, 0, list.size() - 1);
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Scanner in = new Scanner(new File("QuickSort.txt"));
        while (in.hasNext()) {
            list.add(in.nextInt());
        }
        System.out.println(quickSort(list));
        System.out.println(comparisions);
    }
}
