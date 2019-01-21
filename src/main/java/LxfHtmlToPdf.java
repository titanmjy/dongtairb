import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HttpUtil;
import util.Wkhtml2PdfUtil;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class LxfHtmlToPdf implements IHtmlToPdf {
    private String startUrl;
    private List<String> menuList;
    private String fileName;
    private static String domain = "https://www.liaoxuefeng.com";
    private static String template = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
                "<head>" +
                    "<meta charset=\"UTF-8\">" +
                "</head>" +
                "<body>" +
                "</body>" +
            "</html>";

    public LxfHtmlToPdf(String name,String startUrl){
        this.fileName = name;
        this.startUrl = startUrl;
        this.menuList = new ArrayList();
    }

    public void parseMenu(String response) {
        Document doc = Jsoup.parse(response);
        Element ulElements = doc.select(".uk-nav").select(".uk-nav-side").get(1);
        Elements aElements = ulElements.select("a.x-wiki-index-item");
        for(Element e : aElements){
            this.menuList.add(domain  + e.attr("href"));
        }
    }

    public String parseBody(String response) {
        Document doc = Jsoup.parse(response);
        String title = doc.select("h4").text();
        String content = doc.select(".x-main-content").html();
//        constract the html
        Document document = Jsoup.parse(template);
        Element body = document.select("body").first();
        body.append("<h1>"+title+"</h1>");
        body.append(content);
        return document.html();
    }

    public void run(){
        try {
            HttpResponse<String> response = HttpUtil.getSync(this.startUrl);
            parseMenu(response.body());
            for(int i = 0;i<menuList.size();i++){
                HttpResponse<String> contentResponse = HttpUtil.getSync(menuList.get(i));
                String content = parseBody(contentResponse.body());
                File f = new File(i+".html");
                FileUtils.writeStringToFile(f,content,"UTF-8");
                Wkhtml2PdfUtil.convert(i+".html",i+".pdf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LxfHtmlToPdf htmlToPdf = new LxfHtmlToPdf("lxfpython","https://www.liaoxuefeng.com/wiki/0014316089557264a6b348958f449949df42a6d3a2e542c000");
        htmlToPdf.run();
    }

}
