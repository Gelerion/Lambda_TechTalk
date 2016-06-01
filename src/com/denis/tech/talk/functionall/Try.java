package com.denis.tech.talk.functionall;

import java.util.concurrent.Callable;

public class Try<T> {
    private T value;
    private Throwable error;

    private Try(T value) {
        this.value = value;
    }

    private Try(Throwable error) {
        this.error = error;
    }

    public static <T> Try<T> success(T value) {
        return new Try<>(value);
    }

    public static <T> Try<T> failure(Throwable error) {
        return new Try<>(error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public T get() {
        return value;
    }

    public Throwable reasonOfFail() {
        return error;
    }

    // ##############################################

    public static <V> Try<V> of(Callable<V> action) {
        try {
            return success(action.call());
        } catch (Exception e) {
            return failure(e);
        }
    }

    // ##############################################

    @SuppressWarnings("unchecked")
    public <B> Try<B> map(TFunction<B, T> mappingFunction) {
        if(isSuccess()) {
            try {
                return success(mappingFunction.apply(get()));
            } catch (Throwable e) {
                return failure(e);
            }
        }
        else {
            return (Try<B>) this;
        }
    }

    @SuppressWarnings("unchecked")
    public <B> Try<B> map(TFunction<B, T> mappingFunction, TConsumer<T> finalizer) {
        if(isSuccess()) {
            try {
                return success(mappingFunction.apply(get()));
            }
            catch (Throwable e) {
                return failure(e);
            }
            finally {
                try {
                    finalizer.accept(get());
                } catch (Throwable e2) {
                    return failure(e2);
                }
            }
        }
        else {
            return (Try<B>) this;
        }
    }


}
