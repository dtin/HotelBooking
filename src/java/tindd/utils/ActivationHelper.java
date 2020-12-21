/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.utils;

import java.util.Random;

/**
 *
 * @author Tin
 */
public class ActivationHelper {

    private static final int MAX_RANDOM_VALUE = 999999;
    private static final int MIN_RANDOM_VALUE = 100000;

    public static int randomActivationNumber() {
        Random rd = new Random();
        return rd.nextInt((MAX_RANDOM_VALUE - MIN_RANDOM_VALUE) + 1) + MIN_RANDOM_VALUE;
    }
}
