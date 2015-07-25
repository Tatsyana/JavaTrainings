package com.itclass.adressbook;

import com.itclass.adressbook.domain.Category;
import com.itclass.adressbook.domain.NumberPhone;
import com.itclass.adressbook.domain.Record;
import com.itclass.adressbook.repository.InMemoryRepository;
import com.itclass.adressbook.utils.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tatsyana.
 */
public class Main {


    public static void main(String[] args) throws IOException {
      //  System.out.println(Category.FAMILY.name());

        InMemoryRepository.getInstance().add(Record.create("���������", "������", new NumberPhone("���������", "37545"), Category.NONE));
        InMemoryRepository.getInstance().add(Record.create("�������", "����������", new NumberPhone("��������", "12345"), Category.COLLEAGUE));
        InMemoryRepository.getInstance().add(Record.create("�������", "������������", new NumberPhone("�������", "2345"), Category.FAMILY));
    //    Repository<Record, Long> repository = new BinaryRepository("Homeworks/src/binary.txt");
        Helper.init(InMemoryRepository.getInstance());
        String key = "";
        while (true) {
            System.out.println("Press add or a to add: ");
            System.out.println("Press del or d to delete: ");
            System.out.println("Press edit or e to edit: ");
            System.out.println("Press sort or s to sort ");
            System.out.println("Press print to print: ");
            System.out.println("Press filter or f to print: ");
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
                    Helper.getInstance().addRecord();
                    break;
                case "del":
                case "d":
                    Helper.getInstance().printAllRecord();
                    Helper.getInstance().deleteRecord();
                    break;
                case "edit":
                case "e":
                    // ���������� ������� ����
                    Helper.getInstance().printAllRecord();
                    Helper.getInstance().editRecord();
                    break;
                case "sort":
                case "s":
                    Helper.getInstance().sortRecord();

                    //��������� �� ��������� break: ����� ���������� ����� ����������
                case "p":
                case "print":
                    Helper.getInstance().printAllRecord();
                    //        printBooks(bookList);
                    break;
                case "f":
                case "filter":
                    Helper.getInstance().printAllRecord(Helper.getInstance().filter());
                    break;
                case "0":
                    return;
                default:
                    System.out.println("�� ����� �������� ��������. ��������� �������. ");
                    break;
            }
        }

    }
//public static void main(String[] args) {
//        String file = "Homeworks/src/records.txt";
//        TxtRepository repository = new TxtRepository(file);
//        String key = "";
//        while (true) {
//            System.out.println("Press add or a to add: ");
//            System.out.println("Press del or d to delete: ");
//            System.out.println("Press edit or e to edit: ");
//            System.out.println("Press sort or s to sort ");
//            System.out.println("Press print to print: ");
//            System.out.println("Press 0 for exit");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            try {
//                key = br.readLine();
//            } catch (IOException e) {
//                System.err.println("������ �����." + e);
//            }
//            key = key.toLowerCase();
//            switch (key) {
//                case "add":
//                case "a":
//                    Helper.addRecord();
//                    break;
//                case "del":
//                case "d":
//                    Helper.printAllRecord();
//                    Helper.deleteRecord();
//                    break;
//                case "edit":
//                case "e":
//                    // ���������� ������� ����
//                    Helper.printAllRecord();
//                    Helper.editRecord();
//                    break;
//                case "sort":
//                    Helper.sortRecord();
//
//                    //��������� �� ��������� break: ����� ���������� ����� ����������
//                case "print":
//                    Helper.printAllRecord();
//                    //        printBooks(bookList);
//                    break;
//                case "0":
//                    return;
//                default:
//                    System.out.println("�� ����� �������� ��������. ��������� �������. ");
//                    break;
//            }
//        }
//
//    }
}
