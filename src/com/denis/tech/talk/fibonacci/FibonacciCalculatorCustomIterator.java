package com.denis.tech.talk.fibonacci;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Non effective way
public class FibonacciCalculatorCustomIterator {
    private static final long SIZE = 35;
    //extends BiFunction work with same types (consume and return)
    private BinaryOperator<BigInteger> action = (x, y) -> x.add(y);

    //iterative way
    public BigInteger sequentiallySumStream() {
        return stream().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    public BigInteger parallelSumStream() {
        return stream().parallel().limit(SIZE).reduce(BigInteger.ZERO, action);
    }

    public static Stream<BigInteger> stream() {
        Iterator<BigInteger> bit = new FibonacciIterator();
        Spliterator<BigInteger> bsSplit = Spliterators.spliteratorUnknownSize(bit,
            Spliterator.ORDERED | Spliterator.SORTED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
        return StreamSupport.stream(bsSplit, false);

    }

    static class FibonacciIterator implements Iterator<BigInteger> {
        private BigInteger first = BigInteger.ZERO;
        private BigInteger second = BigInteger.ONE;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public BigInteger next() {
            BigInteger tmp = second.add(first);
            first = second;
            second = tmp;
            return first;
        }
    }


    public static void main(String[] args) {
        FibonacciCalculatorCustomIterator calc = new FibonacciCalculatorCustomIterator();
        System.out.println("Size " + SIZE);
        System.out.println("Custom iterator way (sequential): " + calc.sequentiallySumStream());
    }
}
