package view;

import config.Constant;
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
 * 变量广播词第三极，比如中国
 *
 * @author ZYY
 */
public class VariableBroadChn extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel audioName;
    private JComboBox languageComboBox;
    private JTextArea resourceTextArea;
    private TreePath treePath;
    private Element element;
    private Document document;
    private Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);


    public VariableBroadChn(TreePath treePath) {
        this.treePath = treePath;
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        element = (Element) document.selectSingleNode("//resource[@value='" + treePath.getLastPathComponent().toString() + "']");
    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 967, 569);
        this.setBackground(new Color(255, 255, 255));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label_2 = new JLabel("所属类别:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(141, 58, 91, 31);
        this.add(label_2);

        JLabel label_3 = new JLabel(treePath.getPathComponent(3).toString());
        label_3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_3.setBounds(223, 58, 91, 31);
        this.add(label_3);


        JLabel label_4 = new JLabel("变量广告词:");
        label_4.setFont(new Font("宋体", Font.PLAIN, 16));
        label_4.setBounds(126, 111, 91, 31);
        this.add(label_4);

        JLabel label_5 = new JLabel(treePath.getLastPathComponent().toString());
        label_5.setFont(new Font("宋体", Font.PLAIN, 16));
        label_5.setBounds(223, 111, 91, 31);
        this.add(label_5);

        JLabel label = new JLabel("新增语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(141, 174, 91, 31);
        this.add(label);

        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Eng", "Jpn", "Kor", "Fre", "Ger", "Rus"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(223, 174, 91, 31);
        languageComboBox.setBackground(Color.WHITE);
        this.add(languageComboBox);

        JLabel label_1 = new JLabel("新增语种广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(93, 238, 120, 31);
        this.add(label_1);

        resourceTextArea = new JTextArea();

        JScrollPane js = new JScrollPane(resourceTextArea);
        //分别设置水平和垂直滚动条自动出现
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //自动换行
        resourceTextArea.setLineWrap(true);
        //设置5行
        resourceTextArea.setColumns(5);
        resourceTextArea.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceTextArea.setBounds(223, 238, 385, 130);
        js.setBounds(223, 238, 385, 130);
        this.add(js);

        JButton audioButton = new JButton("导入音频");
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
        audioButton.setFocusPainted(false);
        audioButton.setFont(new Font("宋体", Font.PLAIN, 16));
        audioButton.setBackground(new Color(56, 145, 255));
        audioButton.setBounds(629, 238, 99, 31);
        this.add(audioButton);

        //音频文件路径名
        audioName = new JLabel();
        audioName.setBounds(223, 400, 385, 31);
        audioName.setOpaque(true);//设置组件JLabel不透明，只有设置为不透明，设置背景色才有效
        audioName.setBackground(new Color(238, 238, 238));
        this.add(audioName);

        JButton delButton = new JButton("删除");
        delButton.setFocusPainted(false);
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBackground(new Color(56, 145, 255));
        delButton.setBounds(223, 460, 93, 31);
        this.add(delButton);

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "删除提示", 0);
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
        submitButton.setFocusPainted(false);
        submitButton.setBackground(new Color(56, 145, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(400, 460, 93, 31);
        this.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = resourceTextArea.getText();
                String audioNameText = audioName.getText();

                if (StringUtils.isEmpty(text)) {
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
                writeToXML();
            }
        });

        return this;
    }

    //xml处理
    public void writeToXML() {
        Element hashValueElement = document.selectSingleNode("//resourceType[@type='" + treePath.getPathComponent(3).toString() + "']/hashValue/resource[@value='" + treePath.getLastPathComponent().toString() + "']").getParent();
        String hashValuId = hashValueElement.attributeValue("id");
        //避免重复值
        Node node = document.selectSingleNode("//hashValue[@id='" + hashValuId + "']/resource[@language='" + languageComboBox.getSelectedItem().toString() + "']");
        if (node != null) {
            JOptionPane.showMessageDialog(null, "已存在相同资源！", "提示", 1);
            return;
        } else {
            addXML(getNewId());
            resourceTextArea.setText("");
            audioName.setText("");
        }
    }

    public void addXML(Map<String, String> map) {
        //音频路径
        String audioPath = audioName.getText();

        Element hashValueElement = document.selectSingleNode("//resourceType[@type='" + treePath.getPathComponent(3).toString() + "']/hashValue/resource[@value='" + treePath.getLastPathComponent().toString() + "']").getParent();
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
        Element hashValueElement = document.selectSingleNode("//resourceType[@type='" + treePath.getPathComponent(3).toString() + "']/hashValue/resource[@value='" + treePath.getLastPathComponent().toString() + "']").getParent();
        String hashValueId = hashValueElement.attributeValue("id");
        Element lastResourceElement = (Element) document.selectSingleNode("//hashValue[@id='" + hashValueId + "']/resource[last()]");
        String lastHashId = lastResourceElement.attributeValue("hashId");

        String hashId = hashValueId + (Integer.valueOf(lastHashId.replace(hashValueId, "")) + 1);

        map.put("hashId", hashId);

        return map;
    }

}
