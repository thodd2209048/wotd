package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class Crawl {
    public static Article readAnArticle(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .get();
        String title = doc.title();
        Elements spans = doc.getElementsByClass("richtext-text");
        String content = "";
        for (Element span : spans
        ) {
            content += span.text() + " ";
        }
        return new Article(title, url, content, ZonedDateTime.now(), ZonedDateTime.now(), false);
    }
}
