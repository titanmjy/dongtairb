public class HtmlToPdf {
    private String url;
    private static String template = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
                "<head>" +
                    "<meta charset=\"UTF-8\">" +
                "</head>" +
                "<body>" +
                    "{content}" +
                "</body>" +
            "</html>";


    public HtmlToPdf(String url){
        this.url = url;
    }

    public String getHtml(){
        return "";
    }

    
    public static void main(String[] args) {

    }
}
