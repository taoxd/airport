package config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: 常量
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 15:00
 * @Version: 1.0
 */
public class Constant {

    //校验不能输入中文
    public static final String regexZ = "^\\w+$";

    private static final String format = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    //项目当前路径
    private static final String CURRENT_PATH = System.getProperty("user.dir");

    //资源文件夹
    public static final String DHKC_DIR = new StringBuilder(CURRENT_PATH).append("\\DHKC").toString();
    public static final String ZIP_DIR = new StringBuilder(CURRENT_PATH).append("\\ZIP").toString();

    //导出压缩文件
    public static final String EXPORT_DEST_DIR = new StringBuilder(ZIP_DIR).append("\\DHKC_").append(format).append(".zip").toString();
    //音频上传路径
    public static final String UPLOAD_VOICE_PATH = new StringBuilder(DHKC_DIR).append("\\voice").toString();
    //资源文件路径
    public static final String UPLOAD_RESOURCE_PATH = new StringBuilder(DHKC_DIR).append("\\resourceXML").toString();
    //广播模版路径
    public static final String UPLOAD_BROADCAST_PATH = new StringBuilder(DHKC_DIR).append("\\templateXML").toString();
    //资源文件名
    public static final String RESOURCE_NAME = "\\resource.xml";
    //新增类别的文件路径   xml命名  中英文
    public static final String TEMP_PATH = new StringBuilder(DHKC_DIR).append("\\temp").toString();
    //新增类别的文件名
    public static final String TEMP_FILE = "\\temp.xml";

    //常量标识
    public static final String CONST = "const";
    //变量标识
    public static final String VARIABLE = "variable";


    public static final String SELECTION = "下拉框";
    public static final String SELECTION_VALUE = "selection";

    public static final String EDITBOX = "输入框";
    public static final String EDITBOX_VALUE = "editBox";
}
