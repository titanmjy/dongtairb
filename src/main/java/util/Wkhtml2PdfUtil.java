package util;

import java.io.File;

public class Wkhtml2PdfUtil {

    //wkhtmltopdf在系统中的路径
    private static final String toPdfTool = "D:\\Program Files\\wkhtmltox-0.12.5-1.mxe-cross-win64\\wkhtmltox\\bin\\wkhtmltopdf.exe";

    /**
     * html转pdf
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append(" --header-line");//页眉下面的线
        cmd.append(" --margin-top 3cm ");//设置页面上边距 (default 10mm)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
