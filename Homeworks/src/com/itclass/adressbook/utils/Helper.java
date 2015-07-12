package com.itclass.adressbook.utils;

import com.itclass.adressbook.domain.Category;
import com.itclass.adressbook.domain.NumberPhone;
import com.itclass.adressbook.domain.Record;
import com.itclass.adressbook.repository.InMemoryRepository;
import com.itclass.adressbook.repository.Repository;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tatsyana.
 */
public class Helper {

    private Repository<Record,Long> repository;

    private static Helper instance = null;

    public Helper(Repository<Record, Long> repository) {
        this.repository = repository;
    }

    public static Helper getInstance() {
        if (instance != null) {
            return instance;
        }
        throw new IllegalStateException("Helper was not initialized");
    }

    public static void init(Repository<Record, Long> repository) {
        if (repository != null) {
            instance = new Helper(repository);
        } else {
            throw new IllegalArgumentException("Repository is null");
        }
    }

    public void addRecord() {
        Record record = null;
            try {
                record = readPhone();
            } catch (Exception e) {
                System.err.println("Ошибка ввода: " + e);
        }

        repository.add(record);
    }

    public static Record readPhone() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String name = null;
        while (true) {
            System.out.println("Добавить имя: ");
            name = br.readLine();
            try {
                validateName(name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        String fam;
        while (true) {
        System.out.println("Добавьте фамилию: ");
         fam = br.readLine();
            try {
                validateName(fam);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        String number;
        while(true) {
        System.out.println("Добавить номер телефона (не менее 6-ти символов): ");
        number = br.readLine();

            try {
                validateNumberPhone(number);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }


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

    public void deleteRecord(){

        repository.remove(repository.find(readId()));
    }

  //  public static void deleteRecord(InMemoryRepository bookList){

  //      bookList.remove(readId(bookList)); перегрузить метод remove по id InMemoryRepository
  //  }


    public  void editRecord(){

        Record record = repository.find(readId());
        System.out.println("Измененное имя: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstName = null;
        while (true) {
            try {
                firstName = br.readLine();
                validateName(firstName);
            } catch (IOException e) {
                System.err.println("Ошибка ввода." + e);//ввод разделен от изменения
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        record.setFirstName(firstName);

        System.out.println("Измененная фамилия: ");
        String lastName = null;
        while (true) {
            try {
                lastName = br.readLine();
                validateName(lastName);
            } catch (IOException e) {
                System.err.println("Ошибка ввода." + e);
            }catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        record.setLastName(lastName);

        System.out.println("Измененный номер телефона: ");
        String number = null;
        String type = null;
        while (true) {
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
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
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
        repository.update();
    }

    public   void sortRecord() {

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
                repository.sort(Record.compareByLastName);
                break;
            case "2":
                repository.sort(Record.compareByFirstName);
                break;
            case "3":
                repository.sort(Record.compareByTypeOfNumber);
                break;
            case "4":
                repository.sort(Record.compareByCategory);
                break;
            default:
                System.out.println("Некорректный ввод.");
                break;
        }
    }

    public  void printAllRecord(){
        final List<Record> list = repository.getAll();
        for (Record a : list) {
            System.out.println(a.toString());
        }
    }

//    public static int validateNumberPhone(String number) {
//        try {
//            return Integer.parseInt(number);
//        } catch (NumberFormatException e) {
//            System.err.println("Неверный формат" + e);
//            throw new NumberFormatException("Неверный формат: " + e);
//        }
//    }

    public static void validateNumberPhone(String number) throws IOException, ValidationException {
        final String PHONE_NUMBER_PATTERN =
                "^(\\s*\\d\\s*){6,12}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN, Pattern.CASE_INSENSITIVE);
     //   String inputText = number;
            Matcher matcher = pattern.matcher(number);
            if (!matcher.matches()) {
                throw new ValidationException("Неверный формат: " );
            }

    }


    public static void validateName(String name) throws IOException, ValidationException {
        final String NAME_PATTERN =
                "^[a-zA-Z|\\s]{1,100}$";
        Pattern pattern = Pattern.compile(NAME_PATTERN, Pattern.CASE_INSENSITIVE);
      //  String inputText = name;
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ValidationException("Неверный формат: " );
        }

    }


























}
