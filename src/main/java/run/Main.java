package run;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;
import view.MainFrame;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 启动入口
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 13:39
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        //启动时创建resource文件
        File file = new File(Constant.UPLOAD_RESOURCE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

/*        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        //查询resourceType
        Node node = document.selectSingleNode("//resourceType[@typeId='204']");
        if (node == null) {
            //初始化节点
            Element rootElement = document.getRootElement();
            Element element = rootElement.addElement("resourceType");
            element.addAttribute("typeId", "204");
            element.addAttribute("type", "小时");
            element.addAttribute("flag", "variable");
            element.addAttribute("show", "editBox");
            try {
                DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


        //模版文件夹
        File file1 = new File(Constant.UPLOAD_BROADCAST_PATH);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //声音文件夹
        File file2 = new File(Constant.UPLOAD_VOICE_PATH);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        //XML命名文件夹
        File file3 = new File(Constant.TEMP_PATH);
        if (!file3.exists()) {
            file3.mkdirs();
        }

        new MainFrame().init();
    }
}
