package com.itclass.adressbook.repository;

import com.itclass.adressbook.domain.Predicate;
import com.itclass.adressbook.domain.Record;

import java.util.ArrayList;
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

     // TODO это из появилось только в 8-й java, лучше пока нез всего этого делать, enterprise разработка еще только перешла на 7
     default List<T> filtr(Predicate<T> predicate, String str){
      return new ArrayList<T>();}
}
