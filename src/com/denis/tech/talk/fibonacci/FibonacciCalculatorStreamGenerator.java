package com.denis.tech.talk.fibonacci;

import java.math.BigInteger;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FibonacciCalculatorStreamGenerator {
    private static final long SIZE = 35;
    //extends BiFunction work with same types (consume and return)
    private BinaryOperator<BigInteger> action = (x, y) -> x.add(y);

    public BigInteger sequentiallySumStream() {
        return stream().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    public BigInteger parallelSumStream() {
        return stream().parallel().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    // - Stateful
    public static Stream<BigInteger> stream() {
        //forbidden to use in concurrent
        //supplier
        return Stream.generate(
                new Supplier<BigInteger>() {
                    private BigInteger first = BigInteger.ZERO;
                    private BigInteger second = BigInteger.ONE;

                    @Override
                    public BigInteger get() {
                        BigInteger tmp = second.add(first);
                        first = second;
                        second = tmp;
                        return first;
                    }
                }
        );
    }

    public static void main(String[] args) {
        FibonacciCalculatorStreamGenerator calc = new FibonacciCalculatorStreamGenerator();
        System.out.println("Size " + SIZE);
        System.out.println("Stream generator way (sequential): " + calc.sequentiallySumStream());
        System.out.println("Stream generator way (parallel): " + calc.parallelSumStream() + " Stateful is forbidden");
    }
}
