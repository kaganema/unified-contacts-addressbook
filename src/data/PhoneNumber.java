package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;

/**
* A single phone number. Instantiated upon making a Contact, called during listing. */
public class PhoneNumber {
    String type;
    String number;

    SimpleStringProperty phoneDomain;
    SimpleStringProperty phoneNumber;

    // Additional checker text to send back if value is invalid
    String report;

    //Pattern ph = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
    Pattern ph;
    Matcher mp;

    /*public PhoneNumber(String t, String value) {
        type = t;
        number = value;
    }*/

    /**
     * A new phone number instance constructed from the text fields
     * @param t the appropriate phone number type
     * @param value the phone number to call
     */
    public PhoneNumber(String t, String value) {
        type = t;
        number = value;
        phoneDomain = new SimpleStringProperty(t);
        phoneNumber = new SimpleStringProperty(value);
    }

    //TODO: Check for phone number region.
    public void verify(Pattern p) {
        mp = p.matcher(number);
        if (!mp.find()) {
            report = "Invalid phone number";
        } else {
            number = mp.toString();
        }
    }

    public void toPhone(Pattern p) {
        mp = p.matcher(number);
        if (!mp.find()) {
            report = "Invalid phone number";
        } else {
            number = mp.toString();
        }
    }

    @Override
    public String toString() {
        return this.type + ": " + this.number;
    }

    // JavaFx properties and bindings

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getPhoneDomain() {
        return phoneDomain.get();
    }

    public SimpleStringProperty phoneDomainProperty() {
        return phoneDomain;
    }

    public void setPhoneDomain(String phoneDomain) {
        this.phoneDomain.set(phoneDomain);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
