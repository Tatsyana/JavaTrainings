package com.itclass.adressbook.repository;

import com.itclass.adressbook.domain.Record;

import java.util.Comparator;
import java.util.List;

/**
 * @author Tatsyana.
 */
public interface Repository<T, ID> {

    List<T> getAll();

    //   T get(long id);

     void add(T entity) throws IllegalArgumentException;

     void remove(T entity);

     T find(ID id);

     void sort(Comparator<? super T> comparator);

     void update();

}
