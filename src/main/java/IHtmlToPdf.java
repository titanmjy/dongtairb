/**
 * 网站教程转换为pdf接口
 */
public interface IHtmlToPdf {

    /**
     * 转换目录
     * @param response
     */
    public void parseMenu(String response);

    /**
     * 转换每章内容
     * @param response
     * @return
     */
    public String parseBody(String response);


}
