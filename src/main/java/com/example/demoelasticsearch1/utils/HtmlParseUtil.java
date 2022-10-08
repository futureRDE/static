package com.example.demoelasticsearch1.utils;

import com.example.demoelasticsearch1.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlParseUtil {
    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseJD("合成").forEach(System.out::println);
    }
    public List<Content> parseJD(String keywords) throws Exception{
        // 爬取京东页面，可换成自己的
        String url = "https://search.jd.com/Search?keyword="+keywords;
        Document document =  Jsoup.parse(new URL(url),30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        ArrayList<Content> goodsList = new ArrayList<>();

        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            Content content = new Content();
            content.setTitle(title);
            content.setPrice(price);
            content.setImg(img);
            goodsList.add(content);
        }
        return goodsList;
    }
}
