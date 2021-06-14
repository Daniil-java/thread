package ru.geekbrains;

import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) throws InterruptedException{
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public static void secondMethod() throws InterruptedException{
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        float[] leftHalf = new float[arr.length/2];
        float[] rightHalf = new float[arr.length/2];
        System.arraycopy(arr, 0, leftHalf, 0, arr.length/2);
        System.arraycopy(arr, arr.length/2, rightHalf, 0, arr.length/2);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < leftHalf.length; i++) {
                arr[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < rightHalf.length; i++) {
                arr[i] = (float) (rightHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        } );

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


        float[] mergedArray = new float[size];
        System.arraycopy(leftHalf, 0, mergedArray, 0, leftHalf.length);
        System.arraycopy(rightHalf, 0, mergedArray, leftHalf.length, rightHalf.length);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");

    }

}
