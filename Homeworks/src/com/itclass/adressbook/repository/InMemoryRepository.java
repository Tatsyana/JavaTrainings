package com.itclass.adressbook.repository;

import com.itclass.adressbook.domain.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Tatsyana.
 */
public class InMemoryRepository  implements Repository<Record, Long> {

    List<Record> list;

    private static InMemoryRepository instance;

    private InMemoryRepository() {

        list= new ArrayList<>();
    }

    public static InMemoryRepository getInstance(){
         if(instance==null){
             instance = new InMemoryRepository();
         }
        return instance;
    }

    @Override
    public List<Record> getAll() {

        return list;
    }

//    @Override
//    public Record get(long id) {
//        return null;
//    }

    @Override
    public void add(Record entity) {

        if(entity == null){
            throw new IllegalArgumentException("Unable to add record, because it is null.");
        }
        list.add(entity);
    }

    @Override
    public void remove(Record entity) {
        list.remove(entity);
    }

    @Override
    public Record find(Long id){
        for (Record record : list) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }

    public void sort(Comparator<? super Record> comparator){
        Collections.sort(list, comparator);
    }

    @Override
    public void update() {

    }
}
