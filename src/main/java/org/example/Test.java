package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.getConnection();
//        Crawl.crawler(1,
//                "https://www.binance.com/en/blog",
//                new ArrayList<>());
//        controller.addWordsFromArticles(Crawl.getArticleList());
//        controller.addArticleToDB(Crawl.getArticleList());
//        System.out.println(Crawl.getArticleList().size());

        System.out.println("DB size: "+controller.sizeOfDB());
        controller.closeConnection();
    }
}
