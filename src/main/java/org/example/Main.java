package org.example;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Service service = new Service();
        Controller controller = new Controller(service);
//        controller.addNewWord("How To Grow Your Crypto Portfolio with Recurring Buy.txt");
        System.out.println(controller.getWords(5));
    }
}