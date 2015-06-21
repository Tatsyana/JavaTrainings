package com.itclass.phonebook;

import com.itclass.phonebook.domain.Category;
import com.itclass.phonebook.domain.NumberPhone;
import com.itclass.phonebook.domain.PhoneBook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

/**
 * @author Tatsyana.
 */
public class Main {

    public static void main(String[] args) {

        List<PhoneBook> bookList = new ArrayList<>();
        //int index = 0;
        String key = "";
        while (true) {
            System.out.println("Press add or a to add:");
            System.out.println("Press del or d to delete: ");
            System.out.println("Press edit or e to edit: ");
            System.out.println("Press sort or s to sort ");
            System.out.println("Press print to print: ");
            System.out.println("Press 0 for exit");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                key = br.readLine();
            } catch (IOException e) {
                System.err.println("Ошибка ввода." + e);
            }
            key = key.toLowerCase();
            switch (key) {
                case "add":
                case "a":
                    PhoneBook phoneBook;
                    try {
                        phoneBook = readPhone();
                    } catch (Exception e) {
                        System.err.println("Ошибка ввода: " + e);
                        break;
                    }
                    bookList.add(phoneBook);
                    break;
                case "del":
                case "d":
                    printBooks(bookList);
                    long deleteId = readId(br);
                    PhoneBook phone = findPhone(bookList, deleteId);
                    if (phone == null) {
                        System.out.println("Такого адресата в книге не существует");
                        break;
                    }
                    bookList.remove(phone);
//                    index = Collections.binarySearch(bookList, new PhoneBook(id, null, null, null, null), PhoneBook.byId);
//                    System.out.println(index);
//                    bookList.remove(index);
                    break;
                case "edit":
                case "e":
                    // предложить вывести всех
                    printBooks(bookList);
                    long editId = readId(br);
                    if (findPhone(bookList, editId) == null) {
                        System.out.println("Такого адресата в книге не существует");
                        break;
                    }
                    editPhone(findPhone(bookList, editId));
                    break;
                case "sort":
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
                            Collections.sort(bookList, PhoneBook.compareByLastName);
                            break;
                        case "2":
                            Collections.sort(bookList, PhoneBook.compareByFirstName);
                            break;
                        case "3":
                            Collections.sort(bookList, PhoneBook.compareByTypeOfNumber);
                            break;
                        case "4":
                            Collections.sort(bookList, PhoneBook.compareByCategory);
                            break;
                        default:
                            System.out.println("Некорректный ввод.");
                            break;
                    }
                    //осознанно не поставлен break: после сортировки вывод информации
                case "print":
                    printBooks(bookList);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Вы ыыели неверное значение. Повторите попытку. ");
                    break;

            }
        }

    }

    // метод вносит изменения во все поля, требует доработки, чтобы можно было пропустить поле, оставив, предыдущее значение в этом поле
    private static void editPhone(PhoneBook phone) {

        System.out.println("Измененное имя: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstName = null;
        try {
            firstName = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);
        }
        phone.setFirstName(firstName);

        System.out.println("Измененная фамилия: ");
        String lastName = null;
        try {
            lastName = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода." + e);
        }
        phone.setLastName(lastName);

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
        phone.setPhone(new NumberPhone(number, type));

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
        phone.setCategory(cat);
    }

    // просит вести id и возвращает id в виде long
    private static long readId(BufferedReader br) {
        System.out.println("Введите id: ");
        long id = 0l;
        try {
            String del = br.readLine();
            id = Long.parseLong(del);
        } catch (Exception e) {
            System.err.println(e.getMessage() + e);
        }
        return id;
    }

    // выводит все объекты коллекции (их значения)
    private static void printBooks(List<PhoneBook> bookList) {
//        for (PhoneBook phoneBook : bookList) {
//            System.out.println(phoneBook.getId());
//        }
        for (PhoneBook a : bookList) {
            System.out.println(a.toString());
        }
    }

    private static PhoneBook findPhone(List<PhoneBook> bookList, long id) {
        for (PhoneBook phoneBook : bookList) {
            if (phoneBook.getId() == id) {
                return phoneBook;
            }
        }
        return null;
    }

    // создает новый контакт и возвращает готовый объект в коллекцию для добавления
    public static PhoneBook readPhone() throws Exception{
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

        return PhoneBook.create(name, fam, numberPhone, cat);
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
