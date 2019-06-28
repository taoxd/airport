package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 变量广播词类型点击，比如城市
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 14:56
 * @Version: 1.0
 */
public class VariableTypeBroad {

    private static final long serialVersionUID = 1L;

    private JComboBox languageComboBox;
    private JTextField resourceTextField;
    private JLabel audioName;
    private String selectName;


    public VariableTypeBroad(String selectName) {
        //获取点击的变量广播词类型名称
        this.selectName = selectName;
    }

    public JPanel init() {
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        //setBounds(100, 100, 657, 331);
        JPanel contentPane = new JPanel();
        contentPane.setForeground(Color.BLACK);
        contentPane.setBackground(Color.WHITE);
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        //setContentPane(contentPane);

        JLabel languageLabel = new JLabel("语种:");
        languageLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        languageLabel.setBounds(38, 49, 54, 15);
        contentPane.add(languageLabel);

        //语种下拉框
        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(89, 36, 91, 36);
        contentPane.add(languageComboBox);

        //资源
        JLabel resourceLabel = new JLabel("资源:");
        resourceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceLabel.setBounds(38, 109, 54, 15);
        contentPane.add(resourceLabel);

        //资源名称
        resourceTextField = new JTextField();
        resourceTextField.setBounds(89, 101, 385, 36);
        contentPane.add(resourceTextField);
        resourceTextField.setColumns(10);

        //导入音频显示文件名
        audioName = new JLabel();
        audioName.setBounds(89, 149, 299, 15);
        contentPane.add(audioName);

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
        contentPane.add(audioButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(89, 174, 93, 36);
        contentPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeToXML();
            }
        });

        return contentPane;
    }

    //xml处理
    public void writeToXML() {
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        //Node lastHashValueNode = document.selectSingleNode("//resourceType[@type='" + selectName + "']/hashValue[last()]");
        updateXML(document,getNewId());
    }


    public void updateXML(Document document, Map<String, String> map) {
        Element resourceTypeElement = (Element) document.selectSingleNode("//resourceType[@type='" + selectName + "']");
        Element hashValueElement = resourceTypeElement.addElement("hashValue");
        hashValueElement.addAttribute("id",map.get("id"));
        Element resource = hashValueElement.addElement("resource");
        resource.addAttribute("language", languageComboBox.getSelectedItem().toString());
        resource.addAttribute("value", resourceTextField.getText());
        resource.addAttribute("url", audioName.getText());
        resource.addAttribute("hashId", map.get("hashId"));
        try {
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getNewId() {
        Map<String, String> map = new HashMap<>();
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Element ele = (Element) document.selectSingleNode("//resourceType[@type='" + selectName + "']");
        String typeId = ele.attributeValue("typeId");
        String id;
        Node node = document.selectSingleNode("//resourceType[@type='" + selectName + "']/hashValue[last()]");
        if (node == null) {
            id = typeId + 1;
        } else {
            String lastId = ((Element) node).attributeValue("id");
            id = typeId + String.valueOf(Integer.valueOf(lastId.replace(typeId, "")) + 1);
        }
        map.put("id", id);
        map.put("hashId", id + 1);

        return map;
    }
}
