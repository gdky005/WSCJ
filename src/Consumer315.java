import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Yunr on 2017/9/25 14:58
 */
public class Consumer315 {


    private static Document document;
    private static Elements questions;
    private static ArrayList<String> answers;
    private static ArrayList<String> questionStrs;
    private static boolean nextIsOk;
    private static StringBuilder stringBuilder;

    public static void main(String[] args) {

        try {
            document = Jsoup.parse(new URL("http://www.12315.cn/knowledge/knowledgeView?zlcode=410b2db796184a6082317c3032b331d2"), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Element word = document.select("div.WordSection1").get(0);//文章主题，个数为1,所以直接取第一个

        questions = word.select("p.MsoNormal > span[style=\"font-size:16.0pt;font-family:仿宋_GB2312;color:black\"]");
        questionStrs = new ArrayList<>();


        //我的天，里面有重复的、
        nextIsOk = false;

        for (Element question : questions) {

            if (nextIsOk) {
                questionStrs.add(question.text());
                nextIsOk = false;
                continue;
            }

            String s = question.text().substring(0, 1);

            try {
                Integer.parseInt(s);
                nextIsOk = true;
            } catch (Exception e) {
                nextIsOk = false;
            }
        }


        answers = new ArrayList<>();

        int qIndex = 0;//question Index
        stringBuilder = new StringBuilder("");
        Elements p = word.select("p");

        System.out.println(p.size());
        for (int i = 0; i < p.size(); i++) {


            if (qIndex < 50) {
                //说明word循环到了新的问题节点，上一个问题节点应该结束存储答案
                if (p.get(i).text().equals((qIndex + 1) + "." + questionStrs.get(qIndex))) {


                    if (i == 0) {
                        System.out.println("Continue");
                        qIndex++;
                        continue;
                    }

                    answers.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder("");
                    qIndex++;
                } else {
                    stringBuilder.append(p.get(i).text()).append("\n");
                }
            } else {
                stringBuilder.append(p.get(i).text()).append("\n");
            }
        }

        answers.add(stringBuilder.toString().trim());


        //测试
        for (int i = 0; i < questionStrs.size(); i++) {
            System.out.println((i + 1) + "." + questionStrs.get(i));
            System.out.println(answers.get(i));
            System.out.println("\n\n");
        }

    }
}
