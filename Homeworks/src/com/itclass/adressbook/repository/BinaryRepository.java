package com.itclass.adressbook.repository;
import com.itclass.adressbook.domain.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Tatsyana.
 */
public class BinaryRepository implements Repository<Record, Long> {


    final String FILE_PATH;
    public List<Record> list = null;

    public BinaryRepository(String file) {

        FILE_PATH = file;

    }

    @Override
    public List<Record> getAll() {
        if (list == null) {
            list = deserizlization();
        }
        return list;
    }


    @Override
    public void add(Record entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Unable to add record, because it is null.");
        }
        getAll().add(entity);
        serizlization(entity);
    }

    @Override
    public void remove(Record entity) {

        getAll().remove(entity);
        serizlization(getAll());
    }

    @Override
    public Record find(Long id) {

        for (Record record : getAll()) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }

    public void sort(Comparator<? super Record> comparator) {


        Collections.sort(getAll(), comparator);
        serizlization(getAll());
    }

    @Override
    public void update() {
        serizlization(getAll());
    }

    // TODO все ошибки св€занные с чтением файла должные пробрасыватьс€ из функции
    public List<Record> deserizlization() {

        List<Record> records = new ArrayList<>();
        Record result = null;
        File file = new File(FILE_PATH);

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream)) {

            while (fileInputStream.available() > 0) {

                result = (Record) objectOutputStream.readObject();
                records.add(result);
            }
            objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // TODO Ёта ошибка не должна обрабатыватьс€ здесь, максимум что здесь должно быть это логирование
            e.printStackTrace();
        }

        return records;
    }

    //TODO оп€чатка, нужно serialization
    public void serizlization(Record entity) {
        File file = new File(FILE_PATH);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serizlization(List<Record> list) {
        File file = new File(FILE_PATH);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            for (Record entity : list) {
                objectOutputStream.writeObject(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
