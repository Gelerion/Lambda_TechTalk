package com.denis.tech.talk.fibonacci;

public class Run {
    public static void main(String[] args) {
        FibonacciCalculatorSequentialIterative.main(args);
        System.out.println("-------------------------------------------------------");
        FibonacciCalculatorStreamGenerator.main(args);
        System.out.println("-------------------------------------------------------");
        FibonacciCalculatorStreamIterate.main(args);
    }
}
