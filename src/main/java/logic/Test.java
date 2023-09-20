package logic;

import java.io.IOException;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.getConnection();
//        List<Article> newArticles = controller.crawlAndRetrieveArticles(5, 10,
//                "https://www.binance.com/en/blog/community/binance-secure-asset-fund-for-users-valued-at-$1bn-421499824684903373");
//        System.out.println(newArticles.size());

//        controller.addWordsFromArticles(Crawl.getArticleList());
//        System.out.println("Article list size: " + Crawl.getArticleList().size());
//
//        System.out.println("DB size: "+controller.sizeOfDB());
//        controller.fetchFromLink(
//                "https://www.binance.com/en/blog/tax/binance-tax-watch-crypto-tax-developments-in-june-and-july-2023-8309877208077496337"
//        );
//        controller.closeConnection();
    }
}
