package com.denis.tech.talk.fibonacci;


import java.math.BigInteger;
import java.util.function.BinaryOperator;

// int up to 46
// long up to 91
public class FibonacciCalculatorSequentialIterative {
    private static final long SIZE = 35;
    //extends BiFunction work with same types (consume and return)
    private BinaryOperator<BigInteger> action = (x, y) -> x.add(y);

    //iterative way
    public BigInteger sequentiallySum() {
        BigInteger first = BigInteger.ZERO;
        BigInteger second = BigInteger.ONE;
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < SIZE; i++) {
            BigInteger tmp = second.add(first);
            first = second;
            second = tmp;
            sum = action.apply(sum, first);
        }

        return sum;
    }


    public static void main(String[] args) {
        FibonacciCalculatorSequentialIterative calc = new FibonacciCalculatorSequentialIterative();
        System.out.println("Size " + SIZE);
        System.out.println("Iterative way (sequential): " + calc.sequentiallySum());
    }
}
