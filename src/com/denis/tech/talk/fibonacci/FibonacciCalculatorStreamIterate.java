package com.denis.tech.talk.fibonacci;

import java.math.BigInteger;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class FibonacciCalculatorStreamIterate {
    private static final long SIZE = 35;
    //extends BiFunction work with same types (consume and return)
    private BinaryOperator<BigInteger> action = (x, y) -> x.add(y);

    public BigInteger sequentiallySumStream() {
        return stream().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    public BigInteger parallelSumStream() {
        return stream().parallel().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    public static Stream<BigInteger> stream() {
        //supplier
        return Stream.iterate(
                new BigInteger[] { BigInteger.ZERO, BigInteger.ONE }, //seed, acts as first value
                (BigInteger[] p) -> new BigInteger[] { p[1], p[0].add(p[1]) } // stream advance
        )
                .map((BigInteger[] p) -> p[1]);
    }

    public static void main(String[] args) {
        FibonacciCalculatorStreamIterate calc = new FibonacciCalculatorStreamIterate();
        System.out.println("Size " + SIZE);
        System.out.println("Stream iterate way (sequential): " + calc.sequentiallySumStream());
        System.out.println("Stream iterate way (parallel): " + calc.parallelSumStream());
    }
}
