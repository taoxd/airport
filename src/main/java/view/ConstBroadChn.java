package view;

import config.Constant;
import config.Menu;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
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
    private JTextField resourceTextField;
    private TreePath treePath;
    private JComboBox languageComboBox;
    private JLabel audioName;
    private Element element;
    private Document document;


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
        label_2.setBounds(56, 70, 91, 36);
        this.add(label_2);

        JLabel constLabel = new JLabel();
        constLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        constLabel.setBounds(161, 70, 400, 36);
        constLabel.setText(treePath.getLastPathComponent().toString());
        this.add(constLabel);


        JLabel label = new JLabel("语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(106, 142, 54, 22);
        this.add(label);

        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Eng"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(161, 132, 91, 36);
        this.add(languageComboBox);


        JLabel label_1 = new JLabel("新增语种广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(31, 194, 129, 24);
        this.add(label_1);


        resourceTextField = new JTextField();
        resourceTextField.setBounds(161, 190, 385, 36);
        this.add(resourceTextField);
        resourceTextField.setColumns(10);

        audioName = new JLabel();
        audioName.setBounds(161, 236, 299, 15);
        this.add(audioName);


        JButton audioButton = new JButton("导入音频");
        audioButton.setFont(new Font("宋体", Font.PLAIN, 12));
        audioButton.setBackground(new Color(30, 144, 255));
        audioButton.setForeground(new Color(240, 248, 255));
        audioButton.setBounds(572, 193, 91, 28);
        audioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(Constant.IMPORT_VOICE_OPEN_URL);
                int val = fc.showOpenDialog(null);    //文件打开对话框
                if (val == fc.APPROVE_OPTION) {
                    //正常选择文件
                    audioName.setText(fc.getSelectedFile().toString());
                } else {
                    //未正常选择文件，如选择取消按钮
                    audioName.setText("未选择文件");
                }
            }
        });
        this.add(audioButton);


        JButton delButton = new JButton("删除");
        delButton.setForeground(Color.WHITE);
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBackground(new Color(30, 144, 255));
        delButton.setBounds(180, 296, 93, 36);
        this.add(delButton);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
        });



        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(409, 296, 93, 36);
        this.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resourceName = resourceTextField.getText();
                String audioNameText = audioName.getText();
                //资源名称和音频不能为空
                if (resourceName != null && !"".equals(resourceName) && audioNameText != null && !"".equals(audioNameText)){
                    submitData(getNewId());
                }
                //提交完之后，清空
                resourceTextField.setText("");
                audioName.setText("");
            }
        });

        return this;

    }

    //提交数据
    public void submitData(Map<String, String> map) {
        //根据语言判断
        Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue[@id='" + map.get("id") + "']/resource[@language='" + languageComboBox.getSelectedItem().toString() + "']");
        //新增
        if (null == node) {
            addXML(document, getNewId());
        }
    }

    public void addXML(Document document, Map<String, String> map) {
        Element hashValueElement = (Element) document.selectSingleNode("//hashValue[@id='" + map.get("id") + "']");

        Element resourceElement = hashValueElement.addElement("resource");
        resourceElement.addAttribute("language", languageComboBox.getSelectedItem().toString());
        resourceElement.addAttribute("value", resourceTextField.getText());
        //获取路径中的文件名
        resourceElement.addAttribute("url", new File(audioName.getText()).getName());
        resourceElement.addAttribute("hashId", map.get("hashId"));
        try {
            //长传音频文件(放在第一位置，如果文件不存在，直接报错)
            File srcFile = new File(audioName.getText());
            SwingUtils.copyFileByBuffer(srcFile, new File(Constant.UPLOAD_VOICE_PATH + "\\" + srcFile.getName()));
            //写xml文件
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
            //动态添加节点
            JFrame jf = (JFrame) (getRootPane().getParent());
            SwingUtils.addNode(jf, resourceTextField.getText(), treePath);
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
