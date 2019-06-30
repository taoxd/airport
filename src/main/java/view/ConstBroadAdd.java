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
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 添加常量广播词
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 14:56
 * @Version: 1.0
 */
public class ConstBroadAdd extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox languageComboBox;
    private JTextField resourceTextField;
    private JLabel audioName;
    private TreePath treePath;
    private Document document;

    public ConstBroadAdd(TreePath treePath) {
        this.treePath = treePath;
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Node node = document.selectSingleNode("//resourceType[@flag='" + Constant.CONST + "']");
        if (node == null) {
            Element rootElement = document.getRootElement();
            Element element = rootElement.addElement("resourceType");
            element.addAttribute("typeId", Menu.CONSTANT_BROAD.getCode());
            element.addAttribute("flag", Constant.CONST);
            element.addAttribute("show", Constant.CONST);
        }

    }

    public JPanel init() {
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        //setBounds(100, 100, 657, 331);
        //JPanel contentPane = new JPanel();
        this.setForeground(Color.BLACK);
        this.setBackground(Color.WHITE);
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel languageLabel = new JLabel("语种:");
        languageLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        languageLabel.setBounds(38, 49, 54, 15);
        this.add(languageLabel);

        //语种下拉框
        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(89, 36, 91, 36);
        this.add(languageComboBox);

        //资源
        JLabel resourceLabel = new JLabel("资源:");
        resourceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceLabel.setBounds(38, 109, 54, 15);
        this.add(resourceLabel);

        //资源名称
        resourceTextField = new JTextField();
        resourceTextField.setBounds(89, 101, 385, 36);
        this.add(resourceTextField);
        resourceTextField.setColumns(10);

        //导入音频显示文件名
        audioName = new JLabel();
        audioName.setBounds(89, 149, 299, 15);
        this.add(audioName);

        JButton audioButton = new JButton("导入音频");
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

        audioButton.setFont(new Font("宋体", Font.PLAIN, 12));
        audioButton.setBackground(new Color(30, 144, 255));
        audioButton.setForeground(new Color(240, 248, 255));
        audioButton.setBounds(503, 104, 91, 28);
        this.add(audioButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(89, 174, 93, 36);
        this.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitData();
            }
        });
        return this;
    }

    //提交数据
    public void submitData() {
        String resourceName = resourceTextField.getText();
        String audioNameText = audioName.getText();

        //资源名称和音频不能为空
        if (!StringUtils.isEmpty(resourceName) && !StringUtils.isEmpty(audioNameText)) {

            //避免添加重复值,根据常量类型查看
            Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue/resource[@value='" + resourceName + "']");
            //新增
            if (null == node) {
                addXML(getNewId());
            }
        }
        //提交完之后，清空
        resourceTextField.setText("");
        audioName.setText("");
    }

    public void addXML(Map<String, String> map) {
        Element resourceTypeElement = (Element) document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']");

        Element hashValueElement = resourceTypeElement.addElement("hashValue");
        hashValueElement.addAttribute("id", map.get("id"));

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
        String id;
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Node lastHashValueNode = document.selectSingleNode("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue[last()]");
        if (lastHashValueNode == null) {
            id = typeId + 1;
        } else {
            String lastId = ((Element) lastHashValueNode).attributeValue("id");
            id = typeId + (Integer.valueOf(lastId.replace(typeId, "")) + 1);
        }
        map.put("id", id);
        map.put("hashId", id + 1);

        return map;
    }
}
