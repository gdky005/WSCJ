import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 绝地求生-大逃杀
 *
 * 目录数据
 * <p>
 * Jsoup 解析文档：http://www.open-open.com/jsoup/
 * <p>
 * Created by WangQing on 2017/10/4.
 */
public class BattlegroundsGame {
    private static String url = "http://www.gamersky.com/z/playbattlegrounds/";

    private static Document document;
    private static Elements questions;
    private static ArrayList<String> answers;
    private static ArrayList<String> questionStrs;
    private static boolean nextIsOk;
    private static StringBuilder stringBuilder;


    public static void main(String[] args) {
        try {
            document = Jsoup.parse(new URL(url), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element word = document.select("div.Mid_L").get(0);


        //获取大分类
        Elements bigElement = word.select("div.MidLtit");
        String bigTitle = bigElement.get(0).text();
        System.out.println("大标题：\n\t" + bigTitle);

//        攻略合集
        Elements glKinds = word.select("div.MidLcon.GLHJ");
        Elements allGonglue = glKinds.select("div.GLHJ-2");

        rmbb(glKinds);
        getOtherKinds(allGonglue);
    }

    /**
     * 入门必备
     */
    private static void rmbb(Elements glKinds) {
        Elements sTitle = glKinds.select("span.GLHJtit");
        String name = sTitle.get(0).text();
        System.out.println("小标题：\n\t" + name);

        Elements imgliklistElements = glKinds.select("ul.imgliklist");

        Elements elements = imgliklistElements.select("li.img");
        System.out.println("获取 " + name + " 带图片属性：");
        getElement(elements);

        Elements linkElements = imgliklistElements.select("li.lik").select("div.link");
        System.out.println("获取 " + name + " link 属性：");
        getElement(linkElements);
    }

    /**
     * 获取 攻略合集 里面 除过 入门必备 的其他分类
     */
    private static void getOtherKinds(Elements allGonglue) {
        for (Element element: allGonglue) {
            Elements sTitle = element.select("span.GLHJtit");
            String name = sTitle.get(0).text();
            System.out.println("小标题：\n\t" + name);

            Elements imgliklistElements = element.select("ul.liklist").select("li.line1");
            System.out.println("获取 " + name + " 带图片属性：");
            getElement(imgliklistElements);
        }
    }

    /**
     * 获取相关属性
     *
     * @param elements
     */
    private static void getElement(Elements elements) {

        for (Element element :
                elements) {
            String name = element.text();
            String refer = element.getElementsByTag("a").attr("href");
            String pic = null;
            try {
                pic = element.getElementsByTag("img").attr("src");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("\t属性是：\n\t\t" +
                    "name:" + name + "\r\n\t\t" +
                    "refer:" + refer + "\r\n\t\t" +
                    "pic:" + pic + "\r\n\t\t"
            );

            System.out.println("\r");
        }
    }

}
