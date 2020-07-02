package com.bhaskar.utils;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class RandomUtils {

    private static final String TAG = RandomUtils.class.getSimpleName();

    /**
     * @param min included
     * @param max included
     * @return
     */
    private static int getRandomNoInBetween(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    /**
     * @param min      included
     * @param max      included
     * @param listSize must be >= max - min
     * @return
     */
    public static List<Integer> getRandomListInBetween(int min, int max, int listSize) {
        List<Integer> randomList = new ArrayList<>();

        while (randomList.size() != listSize) {
            int randomNo = getRandomNoInBetween(min, max);
            while (randomList.contains(randomNo)) {
                randomNo = getRandomNoInBetween(min, max);
            }
            randomList.add(randomNo);
        }
        return randomList;
    }

    /***
     *
     * @param min
     * @param max
     * @param listSize
     * @return
     */
    public static List<Integer> getTHRandomList(int min, int max, int listSize) {

        List<Integer> randomListWithInterval = new ArrayList<>(listSize);
        try {
            //Log.i("getTHRandomList", "Start Time:" + (System.currentTimeMillis() / 1000));
            List<Integer> randomList = getRandomListInBetween(min, max, listSize);
            Collections.sort(randomList);

            Log.i("THRandomList", "randomList:" + randomList);

            for (int index = 0; index < randomList.size(); index++) {

                int randomNo = randomList.get(index);
                if (index == 0) {
                    randomListWithInterval.add(randomNo);
                } else {
                    int lastRandomWithInterval = randomListWithInterval.get(index - 1);

                    int interval = randomNo - lastRandomWithInterval;

                    if (interval < 0) {
                        randomNo = lastRandomWithInterval + 2;
                    } else if (interval == 0 || interval == 1) {
                        randomNo = randomNo + (2 - interval);
                    }
                    randomListWithInterval.add(randomNo);
                }
            }
        } catch (Exception e) {
            Log.e("getTHRandomList", e.getMessage() + "");
        }
        //Log.i("getTHRandomList", "End Time:" + (System.currentTimeMillis() / 1000));
        Log.i("getTHRandomList", "randomListWithInterval:" + randomListWithInterval);
        return randomListWithInterval;
    }

    public static int generateRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    /**
     * This method will be used to generate random number for all rounder game
     *
     * @param min
     * @param max
     * @param randomNumberCount
     * @return
     */
    public static List<Integer> getARRandomNumber(int min, int max, int randomNumberCount) {
        try {
            Random random = new Random();
            List<Integer> randomNumberList = new ArrayList<>();
            int difference = max - min;
            if (difference < (randomNumberCount * 3)) {
                difference = (randomNumberCount * 3);
            }
            int marginInRandomNumber = Math.round((float) difference / randomNumberCount);
            for (int i = 0; i < randomNumberCount; i++) {
                int numberRange = (marginInRandomNumber * i) + min;
                int randomNo = random.nextInt(marginInRandomNumber);
                int actualRandomNo = randomNo + numberRange;
                int lastValue;
                if (i > 0) {
                    lastValue = randomNumberList.get(i - 1);
                    if ((lastValue + 1) == actualRandomNo) {
                        actualRandomNo += 1;
                    }
                }
                randomNumberList.add(actualRandomNo);
            }
            Collections.sort(randomNumberList);
            return randomNumberList;
        } catch (Exception e) {
            Log.e(TAG, "getARRandomNumber: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method will be used to generate random runs as per random number gneration
     *
     * @param oneRunCount
     * @param fourRunCount
     * @param sixRunCount
     * @param wicketCount
     * @return
     */
    public static List<Integer> getARRandomRuns(int oneRunCount, int twoRunCount, int fourRunCount, int sixRunCount, int wicketCount) {
        try {
            List<Integer> randomRunList = new ArrayList<>();
            Map<Integer, Integer> randomMap = new HashMap<>();
            randomMap.put(1, oneRunCount);
            randomMap.put(2, twoRunCount);
            randomMap.put(3, fourRunCount);
            randomMap.put(4, sixRunCount);
            randomMap.put(5, wicketCount);

            for (int i = 1; i <= randomMap.size(); i++) {
                int value = randomMap.get(i);
                for (int j = 0; j < value; j++) {
                    randomRunList.add(i);
                }
            }
            Collections.shuffle(randomRunList);
            return randomRunList;
        } catch (Exception e) {
            Log.e(TAG, "getARRandomRuns: " + e.getMessage());
        }
        return null;
    }
}