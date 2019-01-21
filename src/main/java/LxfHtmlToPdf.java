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

    public String getStartUrl(){
        return this.startUrl;
    }

    public void parseMenu(String response) {
        Document doc = Jsoup.parse(response);
        Element ulElements = doc.select(".uk-nav").select(".uk-nav-side").get(1);
        Elements aElements = ulElements.select("a.x-wiki-index-item");
        for(Element e : aElements){
            this.menuList.add(e.attr(domain + "href"));
        }
    }

    public String parseBody(String response) {
        Document doc = Jsoup.parse(response);
        String title = doc.select("h4").text();
        String content = doc.select(".x-main-content").text();
//        constract the html
        Document document = Jsoup.parse(template);
        document.append("<h1>"+title+"</h1>");
        document.append("<p>"+content+"</p>");
        return document.toString();
    }

    public static void main(String[] args) {
        LxfHtmlToPdf htmlToPdf = new LxfHtmlToPdf("lxfpython","https://www.liaoxuefeng.com/wiki/0014316089557264a6b348958f449949df42a6d3a2e542c000");
        try {
            String response = HttpUtil.getSync(htmlToPdf.getStartUrl()).body();
            htmlToPdf.parseMenu(response);

//            HttpResponse<String> resp = HttpUtil.getSync("https://www.liaoxuefeng.com/wiki/0014316089557264a6b348958f449949df42a6d3a2e542c000/001431608990315a01b575e2ab041168ff0df194698afac000");
//            String s = htmlToPdf.parseBody(resp.body());
//            File f = new File("1.html");
//            FileUtils.writeStringToFile(f,s,"UTF-8");
            System.out.println("ok");
//            Wkhtml2PdfUtil.convert("1.html","1.pdf");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
