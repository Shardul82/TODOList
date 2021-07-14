package edu.niu.cs.z1888485.model;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  Item                                 *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: Item class is defined in this file. It has item    *
 *          constructor, getDescription, setDescription,       *
 *          getId and setId methods.                           *
 *                                                             *
 ***************************************************************/
public class Item {
    private String description;
    private int id;

    // Constructor for Item class
    public Item(int id, String description) {
        this.description = description;
        this.id = id;
    }

    // it returns description
    public String getDescription() {
        return description;
    }

    // it sets the description values
    public void setDescription(String description) {
        this.description = description;
    }

    // it returns id
    public int getId() {
        return id;
    }

    // it sets the id
    public void setId(int id) {
        this.id = id;
    }
}
