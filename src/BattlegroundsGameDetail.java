import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 绝地求生-大逃杀
 * <p>
 * 详细页面数据
 * <p>
 * Jsoup 解析文档：http://www.open-open.com/jsoup/
 * <p>
 * Created by WangQing on 2017/10/4.
 */
public class BattlegroundsGameDetail {
    private static String url = "http://www.gamersky.com/handbook/201705/906915.shtml";
//    private static String url = "http://www.gamersky.com/handbook/201708/949747.shtml";

    private static Document document;

    public static void main(String[] args) {
        try {
            document = Jsoup.parse(new URL(url), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 以下 获取了总页数地址
        Elements pageElements = document.select("div.page_css");
        Elements elements1 = pageElements.select("a");

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < elements1.size(); i++) {
            if (i == 0 || i == elements1.size() - 1)
                continue;
            arrayList.add(elements1.get(i).attr("href"));
        }

        // 以下 获取了标题和作者
        Elements word = document.select("div.Mid2L_tit");

        String articleName = word.select("h1").text();
        String articleAuthor = word.select("div.detail").get(0).childNode(0).toString().trim();

        System.out.println("文章名称：\r\n\t" + articleName);
        System.out.println("文章作者：\r\n\t" + articleAuthor);
        System.out.println("\r\n\t下面是正文：\r\n\t");


        StringBuilder sb = new StringBuilder();

        sb.append("<h1><p align=\"center\">" + articleName + "</p></h1>\r\n");
        sb.append("<h5><p align=\"right\">" + articleAuthor + "</p></h5>");

        getContent(document, sb);

        for (String newUrl : arrayList) {
            Document newDocument = null;
            try {
                newDocument = Jsoup.parse(new URL(newUrl), 10000);
            } catch (IOException e) {
                e.printStackTrace();
            }

            sb.append("\r\n\r\n\r\n");
            getContent(newDocument, sb);
        }


        System.out.println(sb.toString());
    }

    private static void getContent(Document document, StringBuilder sb) {
        //        以下获取正文
        Elements wordDetail = document.select("div.Mid2L_con");
        Elements elements = wordDetail.select("p");
        for (int i = 0; i < elements.size(); i++) {
            if (i == 0) {
                continue;
            }

            if (i >= elements.size() - 3) {
                break;
            }
            String text = elements.get(i).outerHtml();
            text = text.replace("src=\"http://image.gamersky.com/webimg13/zhuanti/common/blank.png\" data-", "");
            sb.append(text).append("\n");
        }
    }

}
