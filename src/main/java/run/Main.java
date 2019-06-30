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
        //模版文件夹
        File file1 = new File(Constant.UPLOAD_BROADCAST_PATH);
        if (!file1.exists()){
            file1.mkdirs();
        }
        //声音文件夹
        File file2 = new File(Constant.UPLOAD_VOICE_PATH);
        if (!file2.exists()){
            file2.mkdirs();
        }
        //XML命名文件夹
        File file3 = new File(Constant.TEMP_PATH);
        if (!file3.exists()){
            file3.mkdirs();
        }

        new MainFrame().init();
    }
}
