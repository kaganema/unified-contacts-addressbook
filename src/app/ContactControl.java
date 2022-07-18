package app;

//import java.util.List;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import data.*;

/**
 * Form controller for adding data into a contact.
 */
public class ContactControl {
    ObservableList<String> collection;

    private Pattern pt;
    private Matcher mt;

    // Temporary map for storing a collection of accounts, usually used to add more content.
    private static Map<String, String> sites;

    // Form controls for new contact details.
    @FXML VBox form;

    @FXML ChoiceBox<String> contactType;
    @FXML TextField contactName;

    @FXML ChoiceBox<String> phoneType;
    @FXML
    TextField pnumber;

    @FXML TextField primaryEmail;
    @FXML
    ChoiceBox<String> addressType;

    @FXML
    Region accountList;

    @FXML private VBox altAccount;

    // Account text fields
    @FXML TextField hostSite;
    @FXML TextField accountLink;

    // A message instance to be created for errors.
    private Text warningText;

    // Generic user id index. Used for when a user doesn't provide the site name.
    private int id;

    public void initialize() {
        //typeList.setAll(Category.values().toString().split(","));
        //contactType.setItems(typeList);
        contactType.setItems(FXCollections.observableArrayList("Work", "Family", "Friend", "Shop", "Support", "Agent", "Account"));
        collection = FXCollections.observableArrayList("Work", "Home");
        this.addressType.setItems(collection);
        collection.add("Mobile");
        this.phoneType.setItems(collection);
        sites = new HashMap<>();
        //soc = new ArrayList<>();
        id = 0;
    }


    /*
    * All form validation will be processed here. */

    public boolean validate(String exp, String txt) {
        // Since only one case of validation has a unique flag..
        pt = (exp.equals("(^$|^.*@.*\\.\\w*$)")) ? Pattern.compile(exp, Pattern.CASE_INSENSITIVE) : Pattern.compile(exp);
        mt = pt.matcher(txt);
        return mt.find();
    }

    public void validateNumber(String n) {
        pt = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        mt = pt.matcher(pnumber.getText());
        if (mt.find()) {
            //member.setPhone(new PhoneNumber(phoneType.getValue(), phoneset.getText()));
        } // else set invalid
    }

    /**
     * Get a valid phone number that takes international codes and various formats into account.
     * Solution for valid phone number pattern: https://codingnconcepts.com/java/java-regex-to-validate-phone-number/
     * @return a valid match.
     */
    private boolean validateNumber() {
        //pt = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        pt = Pattern.compile("(^$)|(^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$)");
        mt = pt.matcher(pnumber.getText());
        // Return true or false.
        return mt.find();
    }

    /*public void validateNumber() {
        pt = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        mt = pt.matcher(pnumber.getText());
        if (mt.find()) {
            //member.setPhone(new PhoneNumber(phoneType.getValue(), phoneset.getText()));
        } // else set invalid
    }*/

    public String validNumber() {
        pt = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        mt = pt.matcher(pnumber.getText());
        if (!mt.find()) {
            //member.setPhone(new PhoneNumber(phoneType.getValue(), phoneset.getText()));
        } // else set invalid
        return mt.toString();
    }

    // Option: Change the parameter once the control has been obtained.

    // Sites that provided solutions:
    // http://emailregex.com/
    // https://regexpattern.com/email-address/
    /**
     * Check for a valid email address, which accepts any domain and any email provider. Empty values can be passed.
     * This, however, does not check if the email actually being checked actually exists.
     * @return a valid email address format (but not its existence)
     */
    @FXML
    public boolean validEmail() {
//        pt = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
//                "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
//                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
//                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
//                "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        pt = Pattern.compile("(^$|^.*@.*\\.\\w*$)", Pattern.CASE_INSENSITIVE);
        //pt = Pattern.compile("(^$)|(^.*@.*\\.\\w*$)");
        mt = pt.matcher(primaryEmail.getText());
        return mt.find();
    }

    /**
     * Upon clicking add in the accounts section, add the online accounts data to the accounts collection with the following
     * conditions:
     * 1. Check if a site name (key) is provided. If not, make a generic identifier with a number will be generated.
     * 2. If data has been entered in the site name, make sure it isn't a duplicate. Reset the site field if there is.
     * 3. An empty value or a valid link format is provided (note: this does not check if the link actually exists).
     * Reset the account section of the form upon addition.
     */
    @FXML
    public void nextAccount() {
        // check if valid link?
        warningText = new Text();
        String v = hostSite.getText().equals("") ? "site" + id++ : hostSite.getText();
        // Make sure an account from the same site doesn't exist
        if (sites.containsKey(v)) {
            // Print a message of site key already existing.
            warningText.setText("Account with " + v + " already exists." +
                    "\nHave you tried: " + v.toLowerCase() + ", " + v.toUpperCase() + "..");
            warningText.setFill(Color.FIREBRICK);
            altAccount.getChildren().add(warningText);
            hostSite.clear();
            return;
        }
        //String v = noId(hostSite.getText()) ? "site" + id++ : hostSite.getText();
        //sites.put(hostSite.getText(), accountLink.getText());
        sites.put(v, accountLink.getText());
        //soc.add(new Account(v, accountLink.getText()));
        warningText.setText("");
        altAccount.getChildren().remove(warningText);
        clearAccount();
    }

    //public void validateLink(String val) {}

    private boolean noId(String txt) {
        pt = Pattern.compile("(^$)");
        mt = pt.matcher(txt);
        return mt.find();
    }

    /**
     * Check if the link value entered is an acceptable format (doesn't mater if it is real).
     * Regex generated from https://www.regextester.com/94502
     * @param val the link the matcher is comparing the valid pattern to
     * @return a valid, or acceptable, link format
     */
    public boolean validateLink(String val) {
        pt = Pattern.compile("(^$)|((^https?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$)");
        //pt = Pattern.compile("^https?://.+\\..+\\..+\\..{3,4}");
        mt = pt.matcher(val);
        return mt.find();
    }

    /*public boolean validateLink() {
        pt = Pattern.compile("^https?://");
        mt = pt.matcher(val);
        return mt.find();
    }*/

    /**
     * Clear any data entered into the web accounts sub form.
     */
    @FXML public void clearUser() {
        // Clear the content when setting the external accounts form is reset.
        clearAccount();
    }

    /**
    * Reset the entire form, including the accounts list. */
    @FXML public void clearFormAll() {
        contactType.getSelectionModel().clearSelection();
        contactName.clear();
        pnumber.clear();
        phoneType.getSelectionModel().clearSelection();
        primaryEmail.clear();
        addressType.getSelectionModel().clearSelection();
        clearAccount();
        sites.clear();
        //soc.clear();
    }

    /**
     * Clear the accounts form input fields. */
    private void clearAccount() {
        hostSite.clear();
        accountLink.clear();
    }

    /**
    * Construct a new Contact from the given data.
    * Pass in any valid or empty data. Return error message if invalid data is in there.
     * @param event handler to close the window upon completing action (if successful). */
    @FXML
    public void processPerson(ActionEvent event) {
        Node n = (Node) event.getSource();
        warningText = new Text();
        // Check if all the data being put in are valid.
        // if any contact data is empty, pass the data anyway
        if ((validateNumber() && validEmail() && validateLink(accountLink.getText()))) {
            ContactList.savedData.add(new Contact(contactName.getText(), contactType.getValue(),
                    new PhoneNumber(phoneType.getValue(), pnumber.getText()), new EmailAddress(addressType.getValue(), primaryEmail.getText()),
                    sites));
            ContactList.contactList.add(new Contact(contactName.getText(), contactType.getValue(),
                    new PhoneNumber(phoneType.getValue(), pnumber.getText()), new EmailAddress(addressType.getValue(), primaryEmail.getText()),
                    sites));
            // if map is static, clear the whole map for the new contact.
            sites.clear();
            //soc.clear();
            form.getChildren().remove(warningText);
            // Print debug to check validation.
            ContactList.printList();
            // at the end, close window
            Stage s = (Stage) n.getScene().getWindow();
            s.close();
        }  else {
            // Respond with a message.
            warningText.setText("A contact data you have entered is invalid.");
            warningText.setFill(Color.FIREBRICK);
            form.getChildren().add(warningText);
        }
    }
}