package com.itclass.adressbook.domain;

/**
 * @author Tatsyana.
 */
public interface Predicate<T> {

    boolean predicate(T value, String str);
}
