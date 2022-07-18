package data;

public class Account {
    // Name of service of where the account is hosted.
    private String site;
    // Link to the account holder
    private String link;

    /*
     * Pass in the site that the account is hosted on (for easy identification, followed by the actual link.*/
    public Account(String host, String url) {
        this.site = host;
        this.link = url;
    }

    public String getLink() {
        return link;
    }

    /*
     * Establish hyperlink to the site.
     */

    /*
     * Deletes account from the list data.
     */
}
