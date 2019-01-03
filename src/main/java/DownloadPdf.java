import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DownloadPdf {
    private static String urlPrefix = "http://digital.dtxww.com/Media/dtrb/";
    private List<String> urls;

    public List<String> getUrls(){
        return urls;
    }

    public DownloadPdf(){
        this.urls = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2018);
        for(int m=0;m<12;m++) {
            calendar.set(Calendar.MONTH, m);
            for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                calendar.set(Calendar.DAY_OF_MONTH, i);
                String day = dateFormat.format(calendar.getTime());
                this.urls.add(urlPrefix+day);
            }
        }
    }


    public static void main(String[] args) {
        DownloadPdf downloadPdf = new DownloadPdf();
        String first = downloadPdf.getUrls().get(1);
        System.out.println(first);
        downloadPdf.download(first);
    }


    public void download(String url){
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(5000)).followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMillis(5009)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Document document = Jsoup.parse(response.body());
            Elements indexLis = document.select("div.tab_content").select(".tab_1>li");
            Elements next = indexLis.next();
            System.out.println(next.text());

            // download target pdf begins
            String href = document.select("#pdf_toolbar>a").attr("href");
            if(href == ""|| href == null){
                return;
            }
            client.send(HttpRequest.newBuilder().uri(URI.create(href)).build(),HttpResponse.BodyHandlers.ofFile(Paths.get("name.pdf")));
            // download ends
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
