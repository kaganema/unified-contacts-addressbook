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
    private static File loc = new File(System.getProperty("user.home") + File.separator + "unified-contacts-data");
    private static File data = new File(loc, "contacts.txt");

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

    // File handling utilites

    /** Creates a new file upon opening app if required. Called from the Controller class.
    * Skips this part if it already exists. 
    * @throws IOException can't locate path */
    static void local() throws IOException {
        loc.mkdir();
        data.createNewFile();
    }

    /** 
    * Pass the text data of each from the file into a new instance of a Contact in the list.
    * @exception IOException no such file exists
    */
    public static void load() {
        Scanner dataList = null;
        // Create a delimiter variable for future renditions.
        Pattern items = Pattern.compile("(:| |\\{|=|}|, )");
        // Make a temporary map from reading the remaining textual data to pass into the list
        HashMap<String, String> links;
        try {
            dataList = new Scanner(new FileReader(data)).useDelimiter(items);
            while (dataList.hasNextLine()){
                // Create an array to store the first few data into the object.
                String[] contents = new String[] {dataList.next(), dataList.next(), dataList.next(), dataList.next(),
                dataList.next(), dataList.next()};
                // Get the remaining data from the current line. A section of this will be used for the map
                String[] map = dataList.nextLine().split(items.pattern());
                links = new HashMap<>();
                for (int i=2, k=3; k<map.length; i+=2, k+=2) {
                    links.put(map[i], map[k]);
                }
                contactList.add(new Contact(contents[0], contents[1],
                        new PhoneNumber(contents[2], contents[3]),
                        new EmailAddress(contents[4], contents[5]),
                        links));
            }
        } catch (IOException e) {
            System.out.println("There must be something wrong when reading the file. It's possible the file is empty!");
            e.printStackTrace();
        } finally {
            if (dataList!=null) dataList.close();
        }
    }

    /**
    * Get the updated table contents and add to the file.
    * @exception IOException file can't be located
    */
    public static void save(ObservableList<Contact> contacts) {
        try (PrintWriter text = new PrintWriter(new FileWriter(data))) {
            for (Contact c: contacts){
                text.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
