package org.cf.cloud.servicebroker.memsql.lib;

import java.util.Random;

/**
 * Created by miyer on 3/21/16.
 */
public final class PasswordGenerator {

    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 12;

    public String generateRandomString(){

        StringBuffer randomString = new StringBuffer();
        for (int i =0; i < RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randomString.append(ch);
            //System.out.println("**"+randomString+"**");
        }
        return randomString.toString();
    }

    public int getRandomNumber() {

        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            //System.out.println("++"+randomInt+"++");
            return randomInt;
        } else {
            //System.out.println("$$"+(randomInt - 1)+"$$");
            return randomInt - 1;
        }


    }

   /* public static void main(String a[]){
        PasswordGenerator msr = new PasswordGenerator();
        System.out.println(msr.generateRandomString());

    }
    */

}
