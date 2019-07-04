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

    private static final String format = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);

    //导出读取源文件
    public static final String EXPORT_SRC_DIR = "C:\\DHKC";
    //导出文件类型
    public static final String EXPORT_DEST_DIR = new StringBuilder("\\DHKC_").append(format).append(".zip").toString();
    //导入音频默认打开文件目录
    public static final String IMPORT_VOICE_OPEN_URL = "F:\\";
    //音频上传路径
    public static final String UPLOAD_VOICE_PATH = "C:\\DHKC\\voice";
    //资源文件路径
    public static final String UPLOAD_RESOURCE_PATH = "C:\\DHKC\\resourceXML";
    //广播模版路径
    public static final String UPLOAD_BROADCAST_PATH = "C:\\DHKC\\templateXML";
    //资源文件名
    public static final String RESOURCE_NAME = "\\resource.xml";


    //常量标识
    public static final String CONST = "const";
    //变量标识
    public static final String VARIABLE = "variable";


    public static final String SELECTION = "下拉框";
    public static final String SELECTION_VALUE = "selection";

    public static final String EDITBOX = "输入框";
    public static final String EDITBOX_VALUE = "editBox";

    //新增类别的文件路径   xml命名  中英文
    public static final String TEMP_PATH = "C:\\DHKC\\temp";
    //新增类别的文件名
    public static final String TEMP_FILE = "\\temp.xml";

}
