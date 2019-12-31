import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or Comparator was null.");
        }
        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;
        // swappedIndex is the last index that swapped
        int swappedIndex = 0;

        while (start < end && swapped) {
            /* Reset swapped flag when entering the loop
            because it might be true from a previous iteration
            */
            swapped = false;

            /* Loop from left to right like you would in bubble sort.
            Remember that you need to find the last swapped position
            and start from it for the backwards loop for efficiency.
             */
            for (int k = start; k < end; k++) {
                if (comparator.compare(arr[k], arr[k + 1]) > 0) {
                    T temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                    swapped = true;
                    swappedIndex = k;
                }
            }
            if (!swapped) {
                break;
            }
            end = swappedIndex;
            if (swapped) {
                swapped = false;
                for (int k = end; k > start; k--) {
                    if (comparator.compare(arr[k], arr[k - 1]) < 0) {
                        T temp = arr[k];
                        arr[k] = arr[k - 1];
                        arr[k - 1] = temp;
                        swapped = true;
                        swappedIndex = k;
                    }
                }
            }
            start = swappedIndex;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array to be sorted "
                    + "can't be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null.");
        }

        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j > -1 && comparator.compare(temp, arr[j]) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array to be sorted "
                    + "can't be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null.");
        }
        int minIndex;
        for (int i = arr.length - 1; i > 0; i--) {
            minIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) > 0) {
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array to be sorted "
                    + "can't be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null.");
        }

        if (arr.length > 1) {
            int middleIndex = (int) (arr.length / 2);
            int leftSize = (int) (arr.length / 2);
            int rightSize = arr.length - leftSize;
            T[] leftArr = (T[]) new Object[leftSize];
            T[] rightArr = (T[]) new Object[rightSize];

            for (int i = 0; i < leftSize; i++) {
                leftArr[i] = arr[i];
            }
            for (int i = leftSize; i < arr.length; i++) {
                rightArr[i - leftSize] = arr[i];
            }

            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            int leftIndex = 0;
            int rightIndex = 0;
            int currentIndex = 0;
            while (leftIndex < middleIndex
                    && rightIndex < arr.length - middleIndex) {
                if (comparator.compare(leftArr[leftIndex],
                        rightArr[rightIndex]) <= 0) {
                    arr[currentIndex] = leftArr[leftIndex];
                    leftIndex++;
                } else {
                    arr[currentIndex] = rightArr[rightIndex];
                    rightIndex++;
                }
                currentIndex++;
            }

            while (leftIndex < middleIndex) {
                arr[currentIndex] = leftArr[leftIndex];
                leftIndex++;
                currentIndex++;
            }
            while (rightIndex < arr.length - middleIndex) {
                arr[currentIndex] = rightArr[rightIndex];
                rightIndex++;
                currentIndex++;
            }
        }
    }



    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can't be null.");
        }

        // Get maximum value in the array
        int maxInt = arr[0];
        int maxlength = 1;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxInt) {
                maxInt = Math.abs(arr[i]);
            }
        }
        while ((maxInt) >= 10) {
            maxlength++;
            maxInt = maxInt / 10;
        }

        List<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        int divnumber = 1;

        for (int i = 0; i < maxlength; i++) {
            for (Integer num: arr) {
                buckets[((num / divnumber) % 10) + 9].add(num);
            }

            int index = 0;
            for (int k = 0; k < buckets.length; k++) {
                for (Integer num: buckets[k]) {
                    arr[index++] = num;
                }
                buckets[k].clear();
            }
            divnumber = divnumber * 10;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     *          the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *          1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can't be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Rand can't be null.");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Index is not an int anywhere "
                    + "from 1 to the array's length.");
        }
        return kthSelect(k, arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper method for kthSelect
     *
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     * the array was sorted; the 'k' in "kth select"; e.g. if k ==
     * 1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start starting index to sort from
     * @param end last index to sort until
     * @return the kth smallest element
     */
    private static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                      Random rand, int start, int end) {
        int pivot = rand.nextInt(end - start) + start;
        swap(arr, start, pivot);
        int i = start + 1;
        int j = end - 1;
        while (i <= j) {
            while (j > start && j >= i
                    && comparator.compare(arr[j], arr[start]) >= 0) {
                j--;
            }
            while (i <= end && j >= i
                    && comparator.compare(arr[i], arr[start]) <= 0) {
                i++;
            }
            if (j > i) {
                swap(arr, i++, j--);
            }
        }
        swap(arr, start, j);
        if (j == k - 1) {
            return arr[j];
        }
        if (j > k - 1) {
            return kthSelect(k, arr, comparator, rand, start, j);
        }
        if (j < k - 1) {
            return kthSelect(k, arr, comparator, rand, i, end);
        }
        return null;
    }

    /**
     * Helper method to swap array elements
     *
     * @param <T> data type to sort
     * @param arr The array we swap elements in
     * @param index1 Index of the first element we swap
     * @param index2 Index of the second element we swap
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
