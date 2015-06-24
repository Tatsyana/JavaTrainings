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
            System.err.println("������ �����: " + e);
        }
        InMemoryRepository.getInstance().add(record);
    }

    public static Record readPhone() throws Exception{
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

        return Record.create(name, fam, numberPhone, cat);
    }

    public static long readId(){

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("������� id: ");
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

  //      bookList.remove(readId(bookList)); ����������� ����� remove �� id InMemoryRepository
  //  }


    public static void editRecord(){

        Record record = InMemoryRepository.getInstance().find(readId());
        System.out.println("���������� ���: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstName = null;
        try {
            firstName = br.readLine();
        } catch (IOException e) {
            System.err.println("������ �����." + e);//���� �������� �� ���������
        }
        record.setFirstName(firstName);

        System.out.println("���������� �������: ");
        String lastName = null;
        try {
            lastName = br.readLine();
        } catch (IOException e) {
            System.err.println("������ �����." + e);
        }
        record.setLastName(lastName);

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
        record.setPhone(new NumberPhone(number, type));

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
        record.setCategory(cat);
    }

    public  static  void sortRecord() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println("������������ ����.");
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
            System.err.println("�������� ������" + e);
            throw new NumberFormatException("�������� ������: " + e);
        }
    }






























}
