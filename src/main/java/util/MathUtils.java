package util;

import java.util.stream.LongStream;

public class MathUtils {

    public static long factorial(long n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return 1;
        return LongStream.rangeClosed(2, n)
                .reduce(1, (n1, n2) -> n1 * n2);
    }

    public static long factorialRecursively(long n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return 1;
        return n * factorialRecursively(n - 1);
    }

    public static long combination(long n, long m) {
        return factorial(n) / (factorial(m) * factorial(n - m));
    }

    public static long combinationWithRepetitions(long n, long m) {
        return factorial(n + m - 1) / (factorial(m) * factorial(n - 1));
    }

    public static long permutation(long n, long m) {
        return factorial(n) / factorial(n - m);
    }

    public static long permutationWithRepetitions(long n, long m) {
        return ((long) Math.pow(n, m));
    }
}