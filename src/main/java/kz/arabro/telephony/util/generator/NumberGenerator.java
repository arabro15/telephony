package kz.arabro.telephony.util.generator;

public final class NumberGenerator {
    public static int getRandomNumber(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static long getRandomNumber(long min, long max) {
        return min + (long) (Math.random() * ((max - min) + 1L));
    }

    private NumberGenerator() {}
}
