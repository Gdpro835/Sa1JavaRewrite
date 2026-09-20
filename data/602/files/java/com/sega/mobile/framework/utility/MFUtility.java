package com.sega.mobile.framework.utility;

import java.util.Random;
import java.util.Vector;

public class MFUtility {
    private static final Random rnd = new Random(System.currentTimeMillis());

    public static int getRandomNumber() {
        return rnd.nextInt();
    }

    public static int getRandomNumber(int maxValue) {
        return Math.abs(rnd.nextInt() % maxValue);
    }

    public static int[] getRandomNumberSequence(int maxValue) {
        int[] ret = new int[maxValue];
        for (int i = 0; i < maxValue; i++) {
            ret[i] = i;
        }
        for (int i2 = 0; i2 < maxValue; i2++) {
            int pos = getRandomNumber(maxValue);
            int tmp = ret[i2];
            ret[i2] = ret[pos];
            ret[pos] = tmp;
        }
        return ret;
    }

    public static int getRandomNumber(int firstValue, int secondValue) {
        return getRandomNumber(Math.abs(firstValue - secondValue)) + Math.min(firstValue, secondValue);
    }

    public static int[] getRandomNumberSequence(int firstValue, int secondValue) {
        int[] ret = getRandomNumberSequence(Math.abs(firstValue - secondValue));
        int minValue = Math.min(firstValue, secondValue);
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ret[i] + minValue;
        }
        return ret;
    }

    public static int getRandomArrayNumber(int[] array) {
        return array[getRandomNumber(array.length)];
    }

    public static short getRandomArrayNumber(short[] array) {
        return array[getRandomNumber(array.length)];
    }

    public static byte getRandomArrayNumber(byte[] array) {
        return array[getRandomNumber(array.length)];
    }

    public static Object getRandomObject(Object[] array) {
        return array[getRandomNumber(array.length)];
    }

    public static Object getRandomObject(Vector<?> vector) {
        return vector.elementAt(getRandomNumber(vector.size()));
    }

    public static int[] shuffleArray(int[] array) {
        int[] tmp = getRandomNumberSequence(array.length);
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[tmp[i]];
        }
        return ret;
    }

    public static short[] shuffleArray(short[] array) {
        int[] tmp = getRandomNumberSequence(array.length);
        short[] ret = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[tmp[i]];
        }
        return ret;
    }

    public static byte[] shuffleArray(byte[] array) {
        int[] tmp = getRandomNumberSequence(array.length);
        byte[] ret = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[tmp[i]];
        }
        return ret;
    }

    public static Object[] shuffleArray(Object[] array) {
        int[] tmp = getRandomNumberSequence(array.length);
        Object[] ret = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[tmp[i]];
        }
        for (int i2 = 0; i2 < array.length; i2++) {
            array[i2] = ret[i2];
        }
        return array;
    }

    public static Vector<Object> shuffleVector(Vector<Object> vector) {
        int[] tmp = getRandomNumberSequence(vector.size());
        Object[] ret = new Object[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            ret[i] = vector.elementAt(tmp[i]);
        }
        vector.removeAllElements();
        for (Object addElement : ret) {
            vector.addElement(addElement);
        }
        return vector;
    }

    public static int getValueInRange(int value, int min, int max) {
        if (value < min) {
            value = min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static final boolean pointInRegion(int x, int y, int regionX, int regionY, int regionW, int regionH) {
        return x > regionX && y > regionY && x < regionX + regionW && y < regionY + regionH;
    }

    public static final void revertArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int tmp = array[i];
            array[i] = array[(array.length - 1) - i];
            array[(array.length - 1) - i] = tmp;
        }
    }

    public static final void revertArray(short[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            short tmp = array[i];
            array[i] = array[(array.length - 1) - i];
            array[(array.length - 1) - i] = tmp;
        }
    }

    public static final void revertArray(byte[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            byte tmp = array[i];
            array[i] = array[(array.length - 1) - i];
            array[(array.length - 1) - i] = tmp;
        }
    }

    public static final void revertArray(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object tmp = array[i];
            array[i] = array[(array.length - 1) - i];
            array[(array.length - 1) - i] = tmp;
        }
    }
}
