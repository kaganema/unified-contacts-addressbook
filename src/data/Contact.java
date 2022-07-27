package data;

import java.io.*;
import java.util.*;
import javafx.beans.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.ObservableMap;

/**
* Class to represent a single contact member. */
public class Contact {
    private String name;
    private Category purpose;

    // Property binding version, used for sending data across multiple scenes.
    private SimpleStringProperty cName;
    private SimpleStringProperty typeProperty;

    private PhoneNumber phone;
    private EmailAddress email;
    // May be used for future versions
    //private Map<String, String> numbers;
    //private Map<String, String> emails;
    private Map<String, String> accounts;

    private SimpleObjectProperty phoneData;
    private SimpleStringProperty primaryPhoneNumber;
    private SimpleObjectProperty emailData;
    private SimpleStringProperty primaryEmailAddress;

    private SimpleMapProperty<String, String> wAdd;

    public Contact(String c, Category flag, PhoneNumber ph) {
        this.name = c;
        this.purpose = flag;
        this.phone = ph;
    }

    public Contact(String c, Category flag, EmailAddress e) {
        this.name = c;
        this.purpose = flag;
        this.email = e;
    }

    public Contact(String c, Category flag, PhoneNumber ph, EmailAddress e) {
        this.name = c;
        this.purpose = flag;
        this.phone = ph;
        this.email = e;
        this.cName = new SimpleStringProperty(c);
        this.typeProperty = new SimpleStringProperty(flag.name());
        this.phoneData = new SimpleObjectProperty(ph);
        this.emailData = new SimpleObjectProperty(e);
    }

    /*
    * Alternative property option. */
    public Contact(String c, String flag, PhoneNumber ph, EmailAddress e) {
        this.name = c;
        this.purpose = Category.valueOf(flag);
        this.phone = ph;
        this.email = e;
        this.cName = new SimpleStringProperty(c);
        this.typeProperty = new SimpleStringProperty(flag);
        this.phoneData = new SimpleObjectProperty(ph);
        this.emailData = new SimpleObjectProperty(e);
    }


    /**
     * Create a new member from the text entered and add to the table.
     * @param c the contact's name
     * @param flag the contact's association with the user
     * @param ph the contact's phone number and its usage
     * @param e the email address and its domain
     * @param links other external web addresses or accounts of the contact.
     */
    public Contact(String c, String flag, PhoneNumber ph, EmailAddress e, Map<String, String> links) {
        this.name = c;
        this.purpose = Category.valueOf(flag);
        this.phone = ph;
        this.email = e;
        // Store as hash map after passing in from creation (or update)
        this.accounts = new HashMap<>(links);
        //this.accounts = FXCollections.observableMap(links);  Didn't work!
        this.cName = new SimpleStringProperty(c);
        this.typeProperty = new SimpleStringProperty(flag);
        this.phoneData = new SimpleObjectProperty(ph);
        this.primaryPhoneNumber = new SimpleStringProperty(phone.number);
        this.emailData = new SimpleObjectProperty(e);
        this.primaryEmailAddress = new SimpleStringProperty(e.address);
        //this.accounts = new SimpleMapProperty<>();
        this.wAdd = new SimpleMapProperty<>();
    }

    public ObservableMap<String, String> getwAdd() {
        return wAdd.get();
    }

    public SimpleMapProperty<String, String> wAddProperty() {
        return wAdd;
    }

    public void setwAdd(ObservableMap<String, String> wAdd) {
        this.wAdd.set(wAdd);
    }

    @Override
    public String toString() {
        return this.name + ":" + this.purpose.toString() + " " + this.phone.toString()
                + " " + this.email.toString() + " " + this.accounts.toString();
    }

    public String getName() {
        return name;
    }

    /*public Category setPurpose(String f) {
        return Category.valueOf(f);
    }*/

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public void setNumber(String n) {
        this.phone.number = n;
    }

    public EmailAddress getEmail() { return email; }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public Map<String, String> getAccounts() {
        return this.accounts;
    }

    public void addAccount(String site, String link) {
        this.accounts.put(site, link);
    }

    // Delete data where the key is the selected target
    public void deleteAccount(String site) {
        this.accounts.remove(this.accounts.get(site));
    }

    public String getcName() {
        return cName.get();
    }

    public SimpleStringProperty cNameProperty() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName.set(cName);
    }

    public String getTypeProperty() {
        return typeProperty.get();
    }

    public SimpleStringProperty typePropertyProperty() {
        return typeProperty;
    }

    public void setTypeProperty(String typeProperty) {
        this.typeProperty.set(typeProperty);
    }

    public Object getPhoneData() {
        return phoneData.get();
    }

    public SimpleObjectProperty phoneDataProperty() {
        return phoneData;
    }

    public void setPhoneData(Object phoneData) {
        this.phoneData.set(phoneData);
    }

    public Object getEmailData() {
        return emailData.get();
    }

    public SimpleObjectProperty emailDataProperty() {
        return emailData;
    }

    public void setEmailData(Object emailData) {
        this.emailData.set(emailData);
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber.get();
    }

    public SimpleStringProperty primaryPhoneNumberProperty() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber.set(primaryPhoneNumber);
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress.get();
    }

    public SimpleStringProperty primaryEmailAddressProperty() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress.set(primaryEmailAddress);
    }

    /*
    * Add a URL link to email or chat on external service if
    * the account link actually exists. */
    public void setHyperLink(String link) {
        // TODO: Check if it exists.
        // TODO: Set the hyperlink.
    }
}
