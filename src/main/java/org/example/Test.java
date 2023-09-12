package org.example;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Article article = Crawl.readAnArticle("https://www.binance.com/en/blog/nft/minting-an-nft-is-easier-than-you-think-with-binance-nft-6907048048270523401");
        System.out.println(article);
    }
}
