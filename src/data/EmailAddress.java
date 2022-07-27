package data;

import java.util.*;

import javafx.beans.*;
import javafx.beans.property.*;
import javafx.beans.value.*;

/**
 * A single email address belonging to a member. Instantiated upon making a Contact, called during listing.
 */
public class EmailAddress {
    String type;
    String address;

    // Property and binding versions.
    SimpleStringProperty typeProperty;
    SimpleStringProperty addressText;

    // A text to display errors on form.
    String note;

    /**
     * Instantiate Email data upon getting values from the text fields
     * @param t email address type for specific purposes
     * @param value the email address
     */
    public EmailAddress(String t, String value) {
        type = t;
        address = value;
        typeProperty = new SimpleStringProperty(t);
        addressText = new SimpleStringProperty(value);
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.type + ":" + this.address;
    }

    // JavaFX binding properties

    public String getTypeProperty() {
        return typeProperty.get();
    }

    public SimpleStringProperty typePropertyProperty() {
        return typeProperty;
    }

    public void setTypeProperty(String typeProperty) {
        this.typeProperty.set(typeProperty);
    }

    public String getAddressText() {
        return addressText.get();
    }

    public SimpleStringProperty addressTextProperty() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText.set(addressText);
    }
}
