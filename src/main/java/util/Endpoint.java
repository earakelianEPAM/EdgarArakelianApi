package util;

public class Endpoint {

    private Endpoint() {
    }

    public static final String TRELLO_URI = "https://api.trello.com/1";

    public static final String BOARD = "/boards";
    public static final String BOARD_ID = "/boards/{id}";

    public static final String CARD = "/cards";
    public static final String CARD_ID = "/cards/{id}";

    public static final String LISTS = "/lists";
    public static final String CHECKLISTS = "/checklists";
    public static final String CHECKLISTS_ID = "/checklists/{id}";

}

