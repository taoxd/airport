package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 变量广播词第三极，比如中国
 *
 * @author ZYY
 */
public class VariableBroadNotChn {

    private static final long serialVersionUID = 1L;
    private String category;
    private String variableBroadName;
    private JLabel audioName;
    private JComboBox languageComboBox;
    private JTextField textField;


    public VariableBroadNotChn(String category, String variableBroadName) {
        this.category = category;
        this.variableBroadName = variableBroadName;
    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 967, 569);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        //setContentPane(contentPane);

        JLabel label_2 = new JLabel("所属类别:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(141, 58, 91, 28);
        contentPane.add(label_2);

        JLabel label_3 = new JLabel(category);
        label_3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_3.setBounds(223, 58, 91, 28);
        contentPane.add(label_3);


        JLabel label_4 = new JLabel("变量广告词:");
        label_4.setFont(new Font("宋体", Font.PLAIN, 16));
        label_4.setBounds(126, 111, 91, 28);
        contentPane.add(label_4);

        JLabel label_5 = new JLabel(variableBroadName);
        label_5.setFont(new Font("宋体", Font.PLAIN, 16));
        label_5.setBounds(223, 111, 91, 28);
        contentPane.add(label_5);

        JLabel label = new JLabel("新增语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(141, 174, 91, 29);
        contentPane.add(label);

        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Eng"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(223, 167, 91, 36);
        contentPane.add(languageComboBox);

        JLabel label_1 = new JLabel("新增语种广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(93, 238, 120, 36);
        contentPane.add(label_1);

        textField = new JTextField();
        textField.setBounds(223, 236, 385, 36);
        contentPane.add(textField);
        textField.setColumns(10);

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
        audioButton.setBounds(629, 239, 91, 28);
        contentPane.add(audioButton);


        audioName = new JLabel();
        audioName.setBounds(223, 283, 299, 15);
        contentPane.add(audioName);

        JButton delButton = new JButton("删除");
        delButton.setForeground(Color.WHITE);
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBackground(new Color(30, 144, 255));
        delButton.setBounds(277, 332, 93, 36);
        contentPane.add(delButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(501, 332, 93, 36);
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
        //避免重复值
        Node node = document.selectSingleNode("//resourceType[@type='" + category + "']/hashValue/resource[@value='" + textField.getText() + "']");
        if (node == null) {
            addXML(document, getNewId());
        }
    }

    public void addXML(Document document, Map<String, String> map) {
        Element hashValueElement = document.selectSingleNode("//resourceType[@type='" + category + "']/hashValue/resource[@value='" + variableBroadName + "']").getParent();

        Element resourceElement = hashValueElement.addElement("resource");
        resourceElement.addAttribute("language", languageComboBox.getSelectedItem().toString());
        resourceElement.addAttribute("value", textField.getText());
        resourceElement.addAttribute("url", audioName.getText());
        resourceElement.addAttribute("hashId", map.get("hashId"));
        try {
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getNewId() {
        Map<String, String> map = new HashMap<>();
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Element hashValueElement = document.selectSingleNode("//resourceType[@type='" + category + "']/hashValue/resource[@value='" + variableBroadName + "']").getParent();
        String hashValueId = hashValueElement.attributeValue("id");
        Element lastResourceElement = (Element) document.selectSingleNode("//hashValue[@id='" + hashValueId + "']/resource[last()]");
        String lastHashId = lastResourceElement.attributeValue("hashId");

        String hashId = hashValueId + (Integer.valueOf(lastHashId.replace(hashValueId, "")) + 1);

        map.put("hashId", hashId);

        return map;
    }

}
