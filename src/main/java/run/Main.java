package run;

import config.Constant;
import view.MainFrame;

import java.io.File;

/**
* @Description:    启动入口
* @Author:         taoxudong
* @CreateDate:     2019/6/26 13:39
* @Version:        1.0
*/
public class Main {
    public static void main(String[] args) {
        //启动时创建resource文件
        File file = new File(Constant.UPLOAD_RESOURCE_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
        new MainFrame().init();
    }
}
