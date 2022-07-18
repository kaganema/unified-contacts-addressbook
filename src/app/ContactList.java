package app;

import java.util.*;
import java.io.*;
//import javax.xml.*;
import data.Contact;
import javafx.collections.*;

/**
* Manages saved data in the person's contact list. Includes collection of saved data. */
public class ContactList {
    // The list of contacts and their associated data.
    public static List<Contact> savedData = new ArrayList<>();
    public static ObservableList<Contact> contactList = FXCollections.observableArrayList();
    // The file to save to
    File data;

    // Debugger for checking added elements in list.
    public static void printList() {
        if (savedData.isEmpty()) System.out.println("List is empty");
        else System.out.println(savedData.size());
        System.out.println(savedData);
        for (Contact data: savedData) {
            System.out.println(data);
        }
        //for (Map.Entry<String, String> data: savedData.get(0).getAccounts().entrySet()) System.out.println(data);
        System.out.println(contactList);
    }
}
