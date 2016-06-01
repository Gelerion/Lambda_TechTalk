package com.denis.tech.talk.functionall;

@FunctionalInterface
public interface TConsumer<T> {

    void accept(T t) throws Throwable;
}
