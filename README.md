# unified-contacts-addressbook
Personal project to familiarise desktop UI controls. Provides ability to add phones, emails, and web accounts. 

## Overview
The unified contacts manager is an address book that stores phones, emails, and web sites or web accounts. The table is where the contacts the user has added is stored. Each row in the main table (the contacts) represent a member that was added to the list.

## Install
This application requires a Java Runtime Environment (at least version 8). Once downloaded from the "Release" section, the app can be run from the folder location of where it was stored. 

## Manual
### Interface
The top panel have tools to manage your list. You can create a new contact to the list, save all added content, or load from a previous session. 

In the centre panel are the category filterers as dropdowns and a table representing the contacts. The filterers each create a table that will match the categories of their list. Each dropdown will contain different categories represented by their appropriate content: by contact member, phone number, or email address. The table will display the members by rows. Each column represent the elements: Contact member name, phone number, and primary email. 

To the right of the main table is a smaller table representing the web addresses belonging to the member. The table is divided into two columns: The name of the web site or account type and the address online. There is a form at the bottom of the table to add a new web location to the member's list.

### Usage
If you are running the app for the first time, the app will create a new folder, "unified-contacts-data", and a file inside it, "contacts.txt", inside the home directory of the user. "contacts.txt" is where the data of elements from the table will be stored.

To set a new contact to the list, click on the "New" button. This will create another window that allows you to enter the data. First option is a dropdown which will categorise the contact member as a whole. For example: is this contact a friend, family, work colleage, shop, etc. The first textfield allows you to enter the name of the contact. The following two rows will contain textfields and a category dropdown for the phone number and email address respectively. The phone will accept any national phone code and a maximum of ten digits. The email address will accept any address that contains the "@" symbol followed by the dot before the top-level domain (com, net, org, etc). 
The last form at the bottom of the window is for the multiple web addresses you can add. The first field is how you want to refer to the address in general, the second one is for the address itself. **It will accept any link format as long as it complies with Hypertext Transfer Protocol (with or withou security), regardless of whether the site actually exists.**
Note: You can leave any of the textfields empty if you don't want to fill the data right away. They can always be edited later. Make sure, however, that none of the fields contains *invalid formats*.
Once you have the relevant information filled, you can click "Confirm" at the bottom of the window. If successfull, the window will close and the data will added to a row in the *contacts* table.

If you wish to add the same member but with different phone numbers or email address, the only way possible is to add another member. You can change the way it is written to avoid any conflicts with the same member. (For example, switch to all caps or lowercase, or ommit a letter or two.) This also applies to adding site names for the web addresses, although two different members can share the same site name.

You can resize the *contacts* table to get more of the data by clicking on the edges of the column titles.

Click on a row of a member to view any of their web locations on the right side. If you wish to another account or address, add another site title and the address in their respective text fields and the click add. If a site name already exists on the left column, a random generic one will be generated with a random number attached. If no address is given, an underscore is placed to make the distinction when reading in the file.

Click or double-click on a cell in the *contacts* table to edit the elements. Press enter to update.

If you wish to only see the members based on what type they are, or what type of phone number or email address they have, click on the dropdown lists above the *contacts* table. The first dropdown filters the table by categories of the member, the second by phone number, and the third by email address. 
**There is an issue with the filtering however (as of version 1+). They are not dependent on each other, meaning that any filter you make will only change on what you last selected, but they won't combine the filters to specify the members. (Changing the phone number drop down only changes the table based on what phone type you selected, but then select a category from the members drop down list and will render the table to only the categories of the member and not phone number.)**

To keep the current data you have provided, click on "Save" to store them in a textual format. If you open the "contacts.txt" file in another app, you can see that each member is written to a line in the file, with each element separated by spaces, the category and value with a colon, and the web addresses are stored as a hash map surrounded by curly brackets ({}). 
If you wish to update the table with the saved data, or reload the data after reopening the app, click on the "Load" button and you can see that all the contacts are re-written in the appropriate cells.
