package com.denis.tech.talk.functionall;

@FunctionalInterface
public interface TFunction<B, V> {

    B apply(V v) throws Throwable;
}
