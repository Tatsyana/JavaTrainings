package com.itclass.phonebook.domain;

/**
 * @author Tatsyana.
 */
public enum Category{
    FRIEND("1"),
    FAMILY("2"),
    COLLEAGUE("3"),
    NONE("");
    private String id;

    Category(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Category getCategory(String id) {
        for (Category category : Category.values()) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return Category.NONE;
    }

}