package com.itclass.adressbook.utils;

import com.itclass.adressbook.domain.Category;
import com.itclass.adressbook.domain.NumberPhone;
import com.itclass.adressbook.domain.Record;
import com.itclass.adressbook.repository.InMemoryRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Tatsyana.
 */
public class Helper {

    public static void addRecord() {
        Record record = null;
        try {
            record = readPhone();
        } catch (Exception e) {
            System.err.println("Ошибка ввода: " + e);
        }
        InMemoryRepository.getInstance().add(record);
    }

    public static Record readPhone() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Добавить имя: ");
        String name = br.readLine();

        System.out.println("Добавьте фамилию: ");
        String fam = br.readLine();

        System.out.println("Добавить номер телефона: ");
        String number = br.readLine();
        validateNumberPhone(number);

        System.out.println("Тип номера: ");
        System.out.println("1-Домашний: ");
        System.out.println("2-Рабочий: ");
        System.out.println("3-Мобильный: ");
        String type = br.readLine();
        switch (type) {
            case "1":
                type = "Домашний";
                break;
            case "2":
                type = "Рабочий";
                break;
            case "3":
                type = "Мобильный";
                break;
            default:
                type = "Не определен";
                break;
        }
        NumberPhone numberPhone = new NumberPhone(number, type);
        System.out.println("Категория: ");
        System.out.println("Нажмите 1 - Друзья: ");
        System.out.println("Нажмите 2 - Семья: ");
        System.out.println("Нажмите 3 - Коллеги: ");
        String key = br.readLine();
        Category cat = Category.getCategory(key);

        return Record.create(name, fam, numberPhone, cat);
    }

    public static long readId(){

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите id: ");
        long id = 0l;
        try {
            String del = br.readLine();
            id = Long.parseLong(del);
        } catch (Exception e) {
            System.err.println(e.getMessage() + e);
        }
        return  id;
    }

    public static void deleteRecord(){

        InMemoryRepository.getInstance().remove(InMemoryRepository.getInstance().find(readId()));
    }

  //  public static void deleteRecord(InMemoryRepository bookList){

  //      bookList.remove(readId(bookList)); перегрузить метод remove по id InMemoryRepository
  //  }


    public static void editRecord(){

        Record record = InMemoryRepository.getInstance().find(readId());
        System.out.println("Измененное имя: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstName = null;
        try {
            firstName = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);//ввод разделен от изменения
        }
        record.setFirstName(firstName);

        System.out.println("Измененная фамилия: ");
        String lastName = null;
        try {
            lastName = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);
        }
        record.setLastName(lastName);

        System.out.println("Измененный номер телефона: ");
        String number = null;
        String type = null;
        try {
            number = br.readLine();
            validateNumberPhone(number);

            System.out.println("Измененный тип телефона: ");
            System.out.println("1-Домашний: ");
            System.out.println("2-Рабочий: ");
            System.out.println("3-Мобильный: ");
            type = br.readLine();
            switch (type) {
                case "1":
                    type = "Домашний";
                    break;
                case "2":
                    type = "Рабочий";
                    break;
                case "3":
                    type = "Мобильный";
                    break;
                default:
                    type = "Не определен";
                    break;
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);
        }
        record.setPhone(new NumberPhone(number, type));

        System.out.println("Измененная категория: ");
        Category cat;
        System.out.println("Нажмите 1 - Семья: ");
        System.out.println("Нажмите 2 - Друзья: ");
        System.out.println("Нажмите 3 - Коллеги: ");
        try {
            String key = br.readLine();
            cat = Category.getCategory(key);
        } catch (Exception e) {
            System.err.println(e.getMessage() + e);
            cat = Category.NONE;
        }
        record.setCategory(cat);
    }

    public  static  void sortRecord() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sort = "";
        System.out.println("1-Сортировка по фамилии");
        System.out.println("2-Сортировка по имени");
        System.out.println("3-Сортировка по типу номера");
        System.out.println("4-Сортировка по категории");
        try {
            sort = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);
        }
        switch (sort) {
            case "1":
                InMemoryRepository.getInstance().sort(Record.compareByLastName);
                break;
            case "2":
                InMemoryRepository.getInstance().sort(Record.compareByFirstName);
                break;
            case "3":
                InMemoryRepository.getInstance().sort(Record.compareByTypeOfNumber);
                break;
            case "4":
                InMemoryRepository.getInstance().sort(Record.compareByCategory);
                break;
            default:
                System.out.println("Некорректный ввод.");
                break;
        }
    }

    public  static  void printAllRecord(){
        final List<Record> list = InMemoryRepository.getInstance().getAll();
        for (Record a : list) {
            System.out.println(a.toString());
        }
    }

    public static int validateNumberPhone(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат" + e);
            throw new NumberFormatException("Неверный формат: " + e);
        }
    }






























}
