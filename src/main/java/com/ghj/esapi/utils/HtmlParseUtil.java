package com.ghj.esapi.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @author ghj
 * @Description 爬取素材
 * @date 2020/5/14 15:49
 */
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        String url = "https://search.jd.com/Search?keyword=java";
        File html = new File("d:\\Pictures\\jd.html");
        Document document = Jsoup.parse(new URL(url), 3000);
//        Document document = Jsoup.parse(html, "utf-8");
        Element element = document.getElementById("J_goodsList");
        Elements lis = element.getElementsByTag("li");
        File path = new File("d:\\Pictures\\爬虫图片");
        if (!path.exists()) {
            path.mkdir();
        }

        for (Element el : lis) {
            String title = el.getElementsByClass("p-name").eq(0).text();
            String price = el.getElementsByClass("p-price").eq(0).text();
            String shop = el.getElementsByClass("p-shop").eq(0).text();
            String img = el.getElementsByTag("img").eq(0).attr("src");
            System.out.println("******************************");
            System.out.println(title);
            System.out.println(price);
            System.out.println(shop);
            System.out.println(img);
            //下载图片
            int len;
            byte[] buff = new byte[1024];
            URL imgUrl = new URL("http:" + img);
            URLConnection connection = imgUrl.openConnection();
            InputStream is = connection.getInputStream();
            String filename = UUID.randomUUID().toString();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(path + "/" + filename + ".jpg"));
            while ((len = is.read(buff)) > 0) {
                os.write(buff, 0, len);
            }
            os.close();
            is.close();
            System.out.println("保存图片成功");
        }
    }
}
