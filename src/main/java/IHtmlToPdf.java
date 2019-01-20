import java.util.List;

public interface IHtmlToPdf {

    public String getResponse (String url) throws Exception;

    public void parseMenu(String response);

    public String parseBody(String response);


}
