package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * 广告词模版
 *
 * @author ZYY
 */
public class AdvertisingWord extends JPanel {

    private Document tempDocument;

    //定义表格的数据模型
    private DefaultTableModel model;

    //定义一个表格
    private JTable table;

    //定义一个滚动面板，用于放置表格
    private JScrollPane scrollPane;

    private TreePath treePath;

    public AdvertisingWord(TreePath treePath) {
        this.treePath = treePath;
        tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);

        //xml中文名
        String chnXMLName = treePath.getPathComponent(2).toString();
        //xml英文名
        String engXMLName = DOMUtils.getEngXMLName(chnXMLName);

        StringBuilder stringBuilder = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH);
        stringBuilder.append("\\").append(engXMLName).append(".xml");
        Document documentTemplate = DOMUtils.getDocumentTemplate(stringBuilder.toString());
        Element templateElement = (Element) documentTemplate.selectSingleNode("//template[@caption='" + treePath.getLastPathComponent().toString() + "']");
        String index = templateElement.attributeValue("index");

        List<Element> contentList = tempDocument.selectNodes("//resource[@chnXML='" + chnXMLName + "' and @engXML='" + engXMLName + "']/content[@index='" + index + "']");

        //JTable的初始化数据
        String[][] datas = new String[contentList.size()][];
        for (int i = 0; i < contentList.size(); i++) {
            String value = contentList.get(i).attributeValue("value");
            String[] contentArr = {value};
            datas[i] = contentArr;
        }

        //JTable的表头标题
        String[] head = {"内容"};


        //初始化JTable的数据模型
        model = new DefaultTableModel(datas, head);

        //初始化表格
        table = new JTable(model);

        //初始化面板
        scrollPane = new JScrollPane(table);

        //设置表格单元格字体居中显示

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();

        render.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumn("内容").setCellRenderer(render);

        //设置表格宽度情况

/*        DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();

        dcm.getColumn(0).setPreferredWidth(60); //设置表格显示的最好宽度，即此时表格显示的宽度。

        dcm.getColumn(0).setMinWidth(45);//设置表格通过拖动列可以的最小宽度。

        dcm.getColumn(0).setMaxWidth(75);//设置表格通过拖动列可以的最大宽度。*/

        //给表格设置行高

        table.setRowHeight(35);
    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(5, 5, 989, 793);
        //contentPane = new JPanel();
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.YELLOW);
        desktopPane.setBounds(93, 137, 800, 800);
        scrollPane.setBounds(93, 137, 600, 400);
        //scrollPane.setLocation(93, 27);
        this.add(scrollPane);
        this.setVisible(true);

        JButton delButton = new JButton("删除");
        delButton.setBackground(new Color(35, 248, 255));
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBounds(93, 700, 93, 44);
        this.add(delButton);

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                int isDelete  = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "删除提示", 0);
                if(isDelete == JOptionPane.YES_OPTION){
                    System.out.println("确定删除啦！！！");
                    delTemplate();
                }

            }
        });
        return this;
    }

    public void delTemplate() {
        //中文别名
        String caption = treePath.getLastPathComponent().toString();
        String chnXML = treePath.getPathComponent(2).toString();
        String engXML = DOMUtils.getEngXMLName(chnXML);


        //获取模版document
        StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                .append("\\").append(engXML).append(".xml");
        Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());
        Element templateElement = (Element) templateDocument.selectSingleNode("//templateGroup[@grCaption='" + chnXML + "' and @grCaptionEng='" + engXML + "']/template[@caption='" + caption + "']");
        String index = templateElement.attributeValue("index");
        templateElement.getParent().remove(templateElement);


        List<Element> contentList = tempDocument.selectNodes("//resource[@chnXML='" + chnXML + "' and @engXML='" + engXML + "']/content[@index='" + index + "']");
        for (Element content : contentList) {
            content.getParent().remove(content);
        }

        try {
            //写xml文件
            DOMUtils.writeXMLToFile(templateDocument, templatePath.toString());
            //写xml文件
            DOMUtils.writeXMLToFile(tempDocument, Constant.TEMP_PATH + Constant.TEMP_FILE);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
