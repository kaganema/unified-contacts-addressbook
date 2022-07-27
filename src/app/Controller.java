package app;

import data.Contact;
import data.EmailAddress;
import data.PhoneNumber;

import javafx.beans.binding.Binding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import javafx.collections.transformation.FilteredList;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

/**
 * Controller class for the main panel.
 * @author Emir Atik (kaganema) (using code based on Oracle's docs and others)
 * @version 1.0 27/07/2022 */
public class Controller {
    private ObservableList<String> filters;

    private ObservableList<Contact> members;

    // Map for use in the accounts table
    private ObservableList<Map> accData;

    // The category of the member overall
    @FXML ChoiceBox<String> category;

    // The category selections for the phone and email types (work, home, etc.)
    @FXML ChoiceBox<String> phoneType;
    @FXML ChoiceBox<String> addressType;

    @FXML Button addContact;

    // The members table
    @FXML TableView<Contact> contactList;

    // Table column ids to apply editing of the features. (Need to update to use regex)
    @FXML TableColumn userName;
    @FXML TableColumn mainPhone;
    // General email account
    @FXML TableColumn genEmail;

    // Accounts map table properties
    @FXML TableView<Map> accountsTable;
    @FXML TableColumn sites;
    @FXML TableColumn links;

    // Form for adding another account
    @FXML TextField addSiteTag;
    @FXML TextField addLink;

    // Toggle between showing the phone and email purpose type.
    @FXML CheckBox tagDisplay;
    // Toggle elements, used for saving data
    private boolean ticked;
    //Object phone;
    //Object email;
    //String phone;
    //String email;

    String phoneElement;

    String name;
    @FXML Label contact;

    // The filtered list changed from the choice boxes
    private FilteredList<Contact> contactsByTags;

    @FXML Button editContact;
    @FXML Button dropContact;

    public void initialize() {
        filters = FXCollections.observableArrayList( "None", "Work", "Family", "Friend", "Shop", "Support", "Agent", "Account");
        //members = FXCollections.observableArrayList(ContactList.savedData);
        // also doable
        members = ContactList.contactList;
        // In a revised version, the initial data will be what value it is has been saved as
        this.ticked = false;
        this.tagDisplay.setSelected(ticked);
        this.name = "Name";

        // Get the app directory or make one
        try {
            ContactList.local();
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.category.setItems(filters);
        this.phoneType.setItems(FXCollections.observableArrayList("None", "Home", "Work", "Mobile"));
        this.addressType.setItems(FXCollections.observableArrayList("None", "Home", "Work", "IMAP/Business"));
        //this.contactList.setItems(members);
        this.contactList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //this.contactList.onMouseClickedProperty(loadAccountList());
        // Pass the observable list to the filtered list, from https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        //contactsByTags = new FilteredList<>(members);
        contactsByTags = new FilteredList<>(members, p -> true);
        // Create a text field on the selected cell to edit data.
        // Source from: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE
        userName.setCellFactory(TextFieldTableCell.<Contact>forTableColumn());
        mainPhone.setCellFactory(TextFieldTableCell.<Contact>forTableColumn());
        genEmail.setCellFactory(TextFieldTableCell.<Contact>forTableColumn());
        // Filtered list by tags, with help from https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // They work as filterers but they are not synchronous,
        // meaning they don't take the context of the other filters when doing the filtering.
        // Need to find a way to fix this as a bonus.
        category.valueProperty().addListener((observable) -> {
            contactsByTags.setPredicate(contact -> {
                //if (contactList.getItems().isEmpty()) return false;
                if ((category.getValue().equals("None") || category.getValue().isEmpty())) return true;
                return contact.getTypeProperty().equals(category.getValue());
            });
        });
        phoneType.valueProperty().addListener(observable -> {
            contactsByTags.setPredicate(n -> {
                if (phoneType.getValue().equals("None") || phoneType.getValue().isEmpty()) return true;
                return n.getPhone().getPhoneDomain().equals(phoneType.getValue());
            });
        });
        addressType.valueProperty().addListener(observable ->
            contactsByTags.setPredicate(e -> {
                if (addressType.getValue().equals("None") || addressType.getValue().isEmpty()) return true;
                return e.getEmail().getTypeProperty().equals(addressType.getValue());
            })
        );
        this.contactList.setItems(contactsByTags);

        //this.phoneElement = new PropertyValueFactory<>("primaryPhoneNumber");
        this.phoneElement = "primaryPhoneNumber";

        // Callback for accounts table, converting the data to string from a HashMap.
        // Source: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE
        Callback<TableColumn<Map, String>, TableCell<Map, String>> sitesData = (TableColumn<Map, String> ac) -> new
                TextFieldTableCell<>(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object.toString();
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });
        sites.setCellFactory(sitesData);
        links.setCellFactory(sitesData);
        // Set the list to the accounts table, change with the updates.
        accData = FXCollections.observableArrayList();
        this.accountsTable.setItems(accData);
    }

    /**
     * Opens a new window to add a new member, including the name, type of contact, phone number, email (as well as
     * their types) and additional places where to contact them.
     * Shifts control to ContactControl controller.
     * @throws IOException
     */
    @FXML
    public void addContactMember() throws IOException {
        Stage start = new Stage();
        Parent pop = FXMLLoader.load(getClass().getResource("creation.fxml"));
        start.setTitle("Add a new Contact");
        start.setScene(new Scene(pop, 320, 500));
        // Optional coordinates for loading a window
        //start.setX(200);
        //start.setY(150);
        start.show();
    }

    // Filter methods from selected choice
    /*@FXML void getMembers() {
        contactsByTags.setPredicate(contact -> {
            //if (contactList.getItems().isEmpty()) return false;
            if (category.getValue().equals("None") || category.getValue().isEmpty()) return false;
            return contact.getTypeProperty().equals(category.getValue());
        });
    }*/

    // On Edit Commit methods, help from code examples on https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE

    @FXML void changeName(TableColumn.CellEditEvent<Contact, String> n) {
        ((Contact) n.getTableView().getItems().get(n.getTablePosition().getRow())).setcName(n.getNewValue());
    }

    @FXML void changeNumber(TableColumn.CellEditEvent<Contact, PhoneNumber> p) {
        ((Contact) p.getTableView().getItems().get(p.getTablePosition().getRow())).setPhone(p.getNewValue());
    }

    @FXML void changeEmail(TableColumn.CellEditEvent<Contact, String> e) {
        ((Contact) e.getTableView().getItems().get(e.getTablePosition().getRow())).setPrimaryEmailAddress(e.getNewValue());
    }

    /**
    * Method to use when clicking a row. Loads the account data to the accounts table, displaying the site name and the
     * web address.
     * Map accounts to table help from https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE */
    public void loadAccountList() {
        if (contactList.getItems().size() > 0){
        try {
            accountsTable.getItems().clear();
            // Option 1: For Each iterator/generator
            /*contactList.getSelectionModel().getSelectedItem().getAccounts().forEach((s, l) -> {
                final Map<String, String> set = new HashMap<>();
                set.put("site", s);
                set.put("link", l);
                accData.add(set);
            });*/
            // Option 2: For Each loop through the entry set
            for(Map.Entry<String, String> act: contactList.getSelectionModel().getSelectedItem().getAccounts().entrySet()) {
                //altAccounts.put(act.getKey(), act.getValue());
                Map<String, String> set = new HashMap<>();
                set.put("site", act.getKey());
                set.put("link", act.getValue());
                accData.add(set);
            }
            //sendName();
            contact.setText(contactList.getSelectionModel().getSelectedItem().getcName());
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        }
    }

    // Set the name of the selected account, used with expression binding
    /*void sendName() {
        //name.replace(name, contactList.getSelectionModel().getSelectedItem().getcName());
        name = contactList.getSelectionModel().getSelectedItem().getcName();
    }*/

    /**
     * Add a new account to the member, effectively updating the table.
     */
    @FXML void addAccount() {
        // For the time being, just pass a random id for an empty key.
        String id = addSiteTag.getText().isEmpty() ? String.valueOf(Math.round(Math.random() * 100000)) : addSiteTag.getText();
        // The two conditions that a link needs to be valid (either empty or an accepted address format)
        Pattern l = Pattern.compile("(^$)|(^_$)");
        Pattern v = Pattern.compile("((^https?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$)");
        String subs = l.matcher(addLink.getText()).find() ? "_" : ((v.matcher(addLink.getText()).find()) ? addLink.getText() : "_");
        ContactList.contactList.get(contactList.getSelectionModel().getSelectedIndex()).getAccounts().put(id, subs);
        addSiteTag.clear();
        addLink.clear();
    }

    /*public void toggleTags() {
        tagDisplay.isSelected();
    }*/

    /*
    Change what gets printed in the phone number and email cells (between printing the meta or just the content itself).
     */
    @FXML void changeView() {
        if (tagDisplay.isSelected()){
            ticked = true;
            // TODO: Set the phone number property to the phoneData ObjectProperty
        } else {
            ticked = false;
            // TODO: Set the phone number property to primaryPhoneNumber String
        }
        //mainPhone.setCellValueFactory(new PropertyValueFactory<>(phoneElement));
    }

    public void setPhoneElement(String ph) {
        //mainPhone.setCellValueFactory(new)
        //phoneElement = new PropertyValueFactory(ph);
        phoneElement = ph;
    }

    // Alternative
    /*public boolean toggleTags() {
        return tagDisplay.isSelected();
    }*/

    /**
     * Delete the selected member.
     */
    @FXML
    public void deleteMember() {
        //members.remove(contactList.getSelectionModel().getSelectedItem());
        members.removeAll(contactList.getSelectionModel().getSelectedItems());
    }

    // File handling utilities.

    @FXML void loadMembers() {
        // Don't duplicate the list
        if (contactList.getItems().isEmpty())
            ContactList.load();
    }

    @FXML void storeMembers() {
        ContactList.save(contactList.getItems());
    }

}
