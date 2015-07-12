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
    public List<Record> list=null;

    public TxtRepository(String file) {

        FILE_PATH = file;

    }

    public List<Record> getList(){

        if(list==null){

            list= getAll();
        }
        return list;
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


        getList().remove(entity);
        writeToFile(getList());
    }

    @Override
    public Record find(Long id) {

        for (Record record :  getList()) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }

    public void sort(Comparator<? super Record> comparator) {


        Collections.sort(getList(), comparator);
        writeToFile(getList());
    }

    @Override
    public void update() {
        writeToFile(getList());
    }

//    private void writeToFile(){
//
//        try (
//                BufferedWriter bufferedWriter =
//                        new BufferedWriter(new FileWriter(FILE_PATH));
//                PrintWriter out = new PrintWriter(bufferedWriter)
//        ) {
//            bufferedWriter.write("");
//            bufferedWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void clearFile() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bufferedWriter);
            out.write("");
            writer.close();
          //  bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(List<Record> records) {

      //  clearFile();
       // File file = new File(FILE_PATH);
        try (
                FileWriter writer = new FileWriter(FILE_PATH);
                BufferedWriter bufferedWriter =
                        new BufferedWriter(writer);
                PrintWriter out = new PrintWriter(bufferedWriter)

        ) {

            for (Record record : records) {
                out.format("%d;%s;%s;%s;%s;%s\n",
                        record.getId(),
                        record.getFirstName(),
                        record.getLastName(),
                        record.getPhone().getNumber(),
                        record.getPhone().getType(),
                        record.getCategory().getId());
            }
//            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void writeToFile(Record entity) {

        try (
                FileWriter writer = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter =
                        new BufferedWriter(writer);
                PrintWriter out = new PrintWriter(bufferedWriter)
        ) {
            out.format("%d;%s;%s;%s;%s;%s\n",
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getPhone().getNumber(),
                    entity.getPhone().getType(),
                    entity.getCategory().getId());

           // writer.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Record> readFromFile() {
        List<Record> records = new ArrayList<>();

        try(
                FileReader freader = new FileReader(FILE_PATH);
                BufferedReader reader =
                     new BufferedReader(freader)) {

            String s;

            while ( (s=reader.readLine())!=null ) {

                String[] strings = s.trim().split(";");

//                if(strings.length!=6) {
//                    continue;
//                }
                long id = Long.parseLong(strings[0].trim());
                String firstName = strings[1].trim();
                String lastName = strings[2].trim();
                String number = (strings[3].trim());
                String type = (strings[4].trim());
                String cat = (strings[5].trim());
                Category categ = Category.getCategory(cat);
                NumberPhone num = new NumberPhone(type, number);
                Record phoneRecord = new
                        Record (id, firstName, lastName, num, categ);

                records.add(phoneRecord);

            }
        freader.close();
            reader.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return  records;
    }


}


