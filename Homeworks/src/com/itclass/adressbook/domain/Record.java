package com.itclass.adressbook.domain;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Tatsyana.
 */
public class Record {
    private static AtomicLong uniqueId = new AtomicLong();
    private long id;
    private String firstName;
    private String lastName;
    private NumberPhone phone;
    private Category category;

    public Record() {
        this.id = uniqueId.getAndIncrement();
        firstName = "Unknown Name";
        lastName = "Unknown Last Name";
        phone = new NumberPhone("Unknown type", "None");
        category = Category.NONE;

    }

    public Record(long id, String firstName, String lastName, NumberPhone phone, Category category) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.category = category;
    }

    public static Record create(String firstName, String lastName, NumberPhone phone, Category category){
        long id = uniqueId.getAndIncrement();
        return new Record(id,firstName, lastName,phone,category);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public NumberPhone getPhone() {
        return phone;
    }

    public void setPhone(NumberPhone phone) {
        this.phone = phone;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // анонимные nested-классы (можно вызывать по имени внешнего класса)
//  TODO: java 8 лямды
    public static Comparator<Record> compareById = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return Long.compare(o1.id,o2.id);

        }
    };

    public static Comparator<Record> compareByLastName= new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getLastName().compareTo(o2.getLastName());

        }
    };

    public static Comparator<Record> compareByFirstName= new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());

        }
    };

    public static Comparator<Record> compareByTypeOfNumber= new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getPhone().getType().compareTo(o2.getPhone().getType());

        }
    };

    public static Comparator<Record> compareByCategory = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getCategory().compareTo(o2.getCategory());

        }
    };

//    @Override
//    public String toString() {
//        return "Record{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", phone=" + phone +
//                ", category=" + category +
//                '}';
//    }
// переопределенный метод toString()
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(getId())
                .append(" ")
                .append(getFirstName())
                .append(" ").append(getLastName())
                .append(" ")
                .append(getPhone().getNumber())
                .append(" ")
                .append(getPhone().getType())
                .append(" ")
                .append(getCategory().name());
        return str.toString();
    }


}

