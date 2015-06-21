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
                System.err.println("������ �����." + e);
            }
            key = key.toLowerCase();
            switch (key) {
                case "add":
                case "a":
                    PhoneBook phoneBook;
                    try {
                        phoneBook = readPhone();
                    } catch (Exception e) {
                        System.err.println("������ �����: " + e);
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
                        System.out.println("������ �������� � ����� �� ����������");
                        break;
                    }
                    bookList.remove(phone);
//                    index = Collections.binarySearch(bookList, new PhoneBook(id, null, null, null, null), PhoneBook.byId);
//                    System.out.println(index);
//                    bookList.remove(index);
                    break;
                case "edit":
                case "e":
                    // ���������� ������� ����
                    printBooks(bookList);
                    long editId = readId(br);
                    if (findPhone(bookList, editId) == null) {
                        System.out.println("������ �������� � ����� �� ����������");
                        break;
                    }
                    editPhone(findPhone(bookList, editId));
                    break;
                case "sort":
                    String sort = "";
                    System.out.println("1-���������� �� �������");
                    System.out.println("2-���������� �� �����");
                    System.out.println("3-���������� �� ���� ������");
                    System.out.println("4-���������� �� ���������");
                    try {
                        sort = br.readLine();
                    } catch (IOException e) {
                        System.err.println("������ �����." + e);
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
                            System.out.println("������������ ����.");
                            break;
                    }
                    //��������� �� ��������� break: ����� ���������� ����� ����������
                case "print":
                    printBooks(bookList);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("�� ����� �������� ��������. ��������� �������. ");
                    break;

            }
        }

    }

    // ����� ������ ��������� �� ��� ����, ������� ���������, ����� ����� ���� ���������� ����, �������, ���������� �������� � ���� ����
    private static void editPhone(PhoneBook phone) {

        System.out.println("���������� ���: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstName = null;
        try {
            firstName = br.readLine();
        } catch (IOException e) {
            System.err.println("������ �����." + e);
        }
        phone.setFirstName(firstName);

        System.out.println("���������� �������: ");
        String lastName = null;
        try {
            lastName = br.readLine();
        } catch (IOException e) {
            System.err.println("������ �����." + e);
        }
        phone.setLastName(lastName);

        System.out.println("���������� ����� ��������: ");
        String number = null;
        String type = null;
        try {
            number = br.readLine();
            validateNumberPhone(number);

            System.out.println("���������� ��� ��������: ");
            System.out.println("1-��������: ");
            System.out.println("2-�������: ");
            System.out.println("3-���������: ");
            type = br.readLine();
            switch (type) {
                case "1":
                    type = "��������";
                    break;
                case "2":
                    type = "�������";
                    break;
                case "3":
                    type = "���������";
                    break;
                default:
                    type = "�� ���������";
                    break;
            }
        } catch (IOException e) {
            System.err.println("������ �����." + e);
        }
        phone.setPhone(new NumberPhone(number, type));

        System.out.println("���������� ���������: ");
        Category cat;
        System.out.println("������� 1 - �����: ");
        System.out.println("������� 2 - ������: ");
        System.out.println("������� 3 - �������: ");
        try {
            String key = br.readLine();
            cat = Category.getCategory(key);
        } catch (Exception e) {
            System.err.println(e.getMessage() + e);
            cat = Category.NONE;
        }
        phone.setCategory(cat);
    }

    // ������ ����� id � ���������� id � ���� long
    private static long readId(BufferedReader br) {
        System.out.println("������� id: ");
        long id = 0l;
        try {
            String del = br.readLine();
            id = Long.parseLong(del);
        } catch (Exception e) {
            System.err.println(e.getMessage() + e);
        }
        return id;
    }

    // ������� ��� ������� ��������� (�� ��������)
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

    // ������� ����� ������� � ���������� ������� ������ � ��������� ��� ����������
    public static PhoneBook readPhone() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("�������� ���: ");
        String name = br.readLine();

        System.out.println("�������� �������: ");
        String fam = br.readLine();

        System.out.println("�������� ����� ��������: ");
        String number = br.readLine();
        validateNumberPhone(number);

        System.out.println("��� ������: ");
        System.out.println("1-��������: ");
        System.out.println("2-�������: ");
        System.out.println("3-���������: ");
        String type = br.readLine();
        switch (type) {
            case "1":
                type = "��������";
                break;
            case "2":
                type = "�������";
                break;
            case "3":
                type = "���������";
                break;
            default:
                type = "�� ���������";
                break;
        }
        NumberPhone numberPhone = new NumberPhone(number, type);
        System.out.println("���������: ");
        System.out.println("������� 1 - ������: ");
        System.out.println("������� 2 - �����: ");
        System.out.println("������� 3 - �������: ");
        String key = br.readLine();
        Category cat = Category.getCategory(key);

        return PhoneBook.create(name, fam, numberPhone, cat);
    }

    public static int validateNumberPhone(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.err.println("�������� ������" + e);
            throw new NumberFormatException("�������� ������: " + e);
        }
    }
}
