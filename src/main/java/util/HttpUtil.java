package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

public class HttpUtil {

    private static HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(5000)).followRedirects(HttpClient.Redirect.NORMAL).build();

    public static HttpResponse<String> getSync(String url) throws Exception{
        if(url == null){
            throw new NullPointerException("target url is null");
        }
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMillis(5009)).header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public static void downloadFile(String url, String fileName) throws IOException, InterruptedException {
        if(url == null){
            throw new NullPointerException("target url is null");
        }
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofMillis(5009)).header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36").build();
        client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(fileName)));
    }

    public static HttpResponse<String> getAsync(String url) throws Exception{
        return  null;
    }
}
