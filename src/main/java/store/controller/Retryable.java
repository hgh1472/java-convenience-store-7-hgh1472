package store.controller;

@FunctionalInterface
public interface Retryable<T> {
    T execute();
}
