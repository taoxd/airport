package view;

import config.Constant;
import config.Menu;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.StringUtils;
import util.DOMUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量广播词实例，比如你好
 *
 * @author ZYY
 */

public class ConstBroadChn extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea resourceTextArea;
    private TreePath treePath;
    private JComboBox languageComboBox;
    private JLabel audioName;
    private Element element;
    private Document document;
    private Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);


    public ConstBroadChn(TreePath treePath) {
        this.treePath = treePath;
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        element = (Element) document.selectSingleNode("//resource[@value='" + treePath.getLastPathComponent().toString() + "']");

    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 992, 796);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label_2 = new JLabel("常量广播词:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(56, 70, 91, 31);
        this.add(label_2);

        JLabel constLabel = new JLabel();
        constLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        constLabel.setBounds(161, 70, 600, 31);
        constLabel.setText(treePath.getLastPathComponent().toString());
        this.add(constLabel);

        JLabel label = new JLabel("语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(106, 132, 54, 31);
        this.add(label);

        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Eng", "Jpn", "Kor", "Fre", "Ger", "Rus"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(161, 129, 91, 31);
        languageComboBox.setBackground(Color.WHITE);
        this.add(languageComboBox);


        JLabel label_1 = new JLabel("新增语种广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(31, 194, 129, 31);
        this.add(label_1);

        //资源名称
        resourceTextArea = new JTextArea();

        JScrollPane js = new JScrollPane(resourceTextArea);
        //分别设置水平和垂直滚动条自动出现
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //自动换行
        resourceTextArea.setLineWrap(true);
        resourceTextArea.setColumns(5);
        resourceTextArea.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceTextArea.setBounds(161, 192, 500, 130);
        js.setBounds(161, 192, 500, 130);
        this.add(js);

        JButton changeLineButton = new JButton("换行");
        changeLineButton.setFont(new Font("宋体", Font.PLAIN, 16));
        changeLineButton.setFocusPainted(false);
        changeLineButton.setBackground(new Color(56, 145, 255));
        changeLineButton.setBounds(680, 192, 91, 31);
        changeLineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resourceName = resourceTextArea.getText();
                if (!StringUtils.isEmpty(resourceName)) {
                    StringBuilder append = new StringBuilder(resourceName).append("\\n      ");
                    resourceTextArea.setText(append.toString());
                }
            }
        });
        this.add(changeLineButton);


        //音频路径
        audioName = new JLabel();
        audioName.setBounds(290, 350, 370, 31);
        audioName.setOpaque(true);//设置组件JLabel不透明，只有设置为不透明，设置背景色才有效
        audioName.setBackground(new Color(238, 238, 238));
        this.add(audioName);


        JButton audioButton = new JButton("导入音频");
        audioButton.setFont(new Font("宋体", Font.PLAIN, 16));
        audioButton.setBackground(new Color(56, 145, 255));
        audioButton.setBounds(161, 350, 99, 31);
        audioButton.setFocusPainted(false);
        audioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Element pathElement = (Element) tempDocument.selectSingleNode("//path");
                String importRadio = pathElement.attributeValue("importRadio");

                JFileChooser fc = new JFileChooser(importRadio);
                int val = fc.showOpenDialog(null);    //文件打开对话框
                if (val == fc.APPROVE_OPTION) {
                    //正常选择文件
                    String name = fc.getSelectedFile().getName();
                    if (!name.substring(0, name.lastIndexOf(".")).matches(Constant.regexZ)) {
                        JOptionPane.showMessageDialog(null, "音频名称不能为中文!", "提示", 1);
                        return;
                    }
                    audioName.setText(fc.getSelectedFile().toString());
                } else {
                    //未正常选择文件，如选择取消按钮
                    audioName.setText("");
                }
            }
        });
        this.add(audioButton);

        JButton delButton = new JButton("删除");
        delButton.setFocusPainted(false);
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBackground(new Color(56, 145, 255));
        delButton.setBounds(161, 410, 99, 31);
        this.add(delButton);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "删除提示", 0);
                //确认删除
                if (i == JOptionPane.YES_OPTION) {
                    //删除树节点
                    JFrame jf = (JFrame) (getRootPane().getParent());
                    SwingUtils.delNode(jf);
                    //删除xml
                    element.getParent().getParent().remove(element.getParent());
                    //写xml文件
                    try {
                        DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(56, 145, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(390, 410, 93, 31);
        submitButton.setFocusPainted(false);
        this.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resourceName = resourceTextArea.getText();
                String audioNameText = audioName.getText();

                //判断空
                if (StringUtils.isEmpty(resourceName)) {
                    JOptionPane.showMessageDialog(null, "请输入广播词!", "提示", 1);
                    return;
                }
                //更新导入音频路径
                if (!StringUtils.isEmpty(audioNameText)) {
                    File audioFile = new File(audioNameText);
                    String parentPath = audioFile.getParent();
                    Element pathElement = (Element) tempDocument.selectSingleNode("//path");
                    pathElement.addAttribute("importRadio", parentPath);
                    try {
                        //写xml文件
                        DOMUtils.writeXMLToFile(tempDocument, Constant.TEMP_PATH + Constant.TEMP_FILE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                submitData(getNewId());
                //提交完之后，清空
                resourceTextArea.setText("");
                audioName.setText("");
            }
        });
        return this;
    }

    //提交数据
    public void submitData(Map<String, String> map) {
        //根据语言判断
        Element hashValueElement = document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue/resource[@value='" + treePath.getLastPathComponent().toString() + "']").getParent();
        String hashValueId = hashValueElement.attributeValue("id");

        Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue[@id='" + hashValueId + "']/resource[@language='" + languageComboBox.getSelectedItem().toString() + "']");

        if (node != null) {
            JOptionPane.showMessageDialog(null, "存在相同资源！", "提示", 1);
            return;
        } else {
            addXML(getNewId());
        }
    }

    public void addXML(Map<String, String> map) {
        //音频路径
        String audioPath = audioName.getText();

        Element hashValueElement = (Element) document.selectSingleNode("//hashValue[@id='" + map.get("id") + "']");

        Element resourceElement = hashValueElement.addElement("resource");
        resourceElement.addAttribute("language", languageComboBox.getSelectedItem().toString());
        resourceElement.addAttribute("value", resourceTextArea.getText());
        //获取路径中的文件名
        if (StringUtils.isEmpty(audioPath)) {
            resourceElement.addAttribute("url", "");
        } else {
            resourceElement.addAttribute("url", new File(audioPath).getName());
        }
        resourceElement.addAttribute("hashId", map.get("hashId"));
        try {
            if (!StringUtils.isEmpty(audioPath)) {
                //长传音频文件(放在第一位置，如果文件不存在，直接报错)
                File srcFile = new File(audioName.getText());
                SwingUtils.copyFileByBuffer(srcFile, new File(Constant.UPLOAD_VOICE_PATH + "\\" + srcFile.getName()));
            }
            //写xml文件
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
            //动态添加节点
            JFrame jf = (JFrame) (getRootPane().getParent());
            SwingUtils.addNode(jf, resourceTextArea.getText(), treePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getNewId() {
        Map<String, String> map = new HashMap<>();
        String typeId = Menu.CONSTANT_BROAD.getCode();
        Node resourceNode = document.selectSingleNode("//resourceType[@typeId='" + typeId + "']/hashValue/resource[@value='" + treePath.getLastPathComponent().toString() + "']");
        String id = resourceNode.getParent().attributeValue("id");
        Element lastResourceElement = (Element) document.selectSingleNode("//hashValue[@id='" + id + "']/resource[last()]");
        String lastHashId = lastResourceElement.attributeValue("hashId");
        String hashId = id + (Integer.valueOf(lastHashId.replace(id, "")) + 1);
        map.put("id", id);
        map.put("hashId", hashId);
        return map;
    }

}
