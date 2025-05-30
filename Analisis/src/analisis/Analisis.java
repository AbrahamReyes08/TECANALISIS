package analisis;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Analisis {
//ABRAHAM REYES
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Cuántos elementos deseas en el arreglo? ");
        int size = scanner.nextInt();

        int[] originalArray = generarArregloAleatorio(size);

        test("Insertion Sort", originalArray.clone(), Analisis::insertionSort);
        test("Heap Sort", originalArray.clone(), Analisis::heapSort);
        test("Quick Sort", originalArray.clone(), arr -> quickSort(arr, 0, arr.length - 1));
        test("Merge Sort", originalArray.clone(), arr -> Mergesort(arr, 0, arr.length - 1));
        test("Selection Sort", originalArray.clone(), Analisis::selectionSort);
        test("Bubble Sort", originalArray.clone(), arr -> bubbleSort(arr, arr.length));

        scanner.close();
    }

    interface SortFunction {
        void sort(int[] arr);
    }

    static void test(String name, int[] original, SortFunction sortFunc) {
        int[] arr = original.clone();
        long startTime = System.nanoTime();
        sortFunc.sort(arr);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // milisegundos
        System.out.println(name + ":");
        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("Time: " + duration + " ms\n");
    }

    static int[] generarArregloAleatorio(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }
    
    //InsertSort----------------------------------------------------------------
    
    static void insertionSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // Heapify------------------------------------------------------------------
    
    static void heapify(int arr[], int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }

    static void heapSort(int arr[]) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    //Swap----------------------------------------------------------------------
    
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    //MergeSort-----------------------------------------------------------------
    
    static void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int L[] = new int[n1], R[] = new int[n2];
        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    static void Mergesort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            Mergesort(arr, l, m);
            Mergesort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    //SelectionSort-------------------------------------------------------------
    
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[min_idx]) min_idx = j;
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;
        }
    }

    //BubbleSort----------------------------------------------------------------
    
    static void bubbleSort(int arr[], int n) {
        int temp;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
