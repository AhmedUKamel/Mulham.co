package org.ahmedukamel.mulham.generator;

import java.util.Random;

public class Generator {
    public static int generateTokenCode() {
        return new Random().nextInt(876_544) + 123_456;
    }

    public static String generateCouponCode() {
        final Random random = new Random();
        StringBuilder builder = new StringBuilder();

        int length = 8 + random.nextInt(5);
        for (int index = 0; index < length; index++) {
            int number = random.nextInt(37);
            if (number < 10) {
                builder.append('0' + number);
            } else {
                builder.append('A' + number - 10);
            }
        }

        return builder.toString();
    }
}