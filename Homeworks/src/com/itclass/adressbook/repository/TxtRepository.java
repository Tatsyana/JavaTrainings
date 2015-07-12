package com.itclass.adressbook.repository;

import com.itclass.adressbook.domain.Category;
import com.itclass.adressbook.domain.NumberPhone;
import com.itclass.adressbook.domain.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Tatsyana.
 */
public class TxtRepository implements Repository<Record, Long> {

    final String FILE_PATH;

    public TxtRepository(String file) {

        FILE_PATH = file;

    }

    @Override
    public List<Record> getAll() {

        List<Record> list = readFromFile();
        return list;
    }


    @Override
    public void add(Record entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Unable to add record, because it is null.");
        }
        writeToFile(entity);


    }


    @Override
    public void remove(Record entity) {

        List<Record> records = getAll();
        records.remove(entity);
        writeToFile(records);
    }

    @Override
    public Record find(Long id) {
        List<Record> list = getAll();
        for (Record record : list) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }

    public void sort(Comparator<? super Record> comparator) {

        List<Record> list = getAll();
        Collections.sort(list, comparator);
        writeToFile(list);
    }


    private void writeToFile(List<Record> records) {

        try (
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(FILE_PATH));
                PrintWriter out = new PrintWriter(bufferedWriter)
        ) {
            for (Record record : records) {

                out.format("%d %s,%s,%s,%s\n",
                        record.getId(),
                        record.getFirstName(),
                        record.getLastName(),
                        record.getPhone().getNumber(),
                        record.getCategory().getId());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void writeToFile(Record entity) {

        try (
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(FILE_PATH,true));
                PrintWriter out = new PrintWriter(bufferedWriter)
        ) {
            out.format("%d %s,%s,%s,%s\n",
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getPhone().getNumber(),
                    entity.getCategory().getId());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Record> readFromFile() {
        List<Record> records = new ArrayList<>();

        try( BufferedReader reader =
                     new BufferedReader( new FileReader(FILE_PATH))) {

            String s;

            while ( (s=reader.readLine())!=null ) {

                String[] strings = s.trim().split(" ");

                if(strings.length!=6) {
                    continue;
                }
                long id = Long.parseLong(strings[0].trim());
                String firstName = strings[1].trim();
                String lastName = strings[2].trim();
                String  type = (strings[3].trim());
                String number = (strings[4].trim());
                String cat = (strings[5].trim());
                Category categ = Category.getCategory(cat);
                NumberPhone num = new NumberPhone(type, number);
                Record phoneRecord = new
                        Record (id, firstName, lastName, num, categ);

                records.add(phoneRecord);

            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


        return  records;
    }
}


