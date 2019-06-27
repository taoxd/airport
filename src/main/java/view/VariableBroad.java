package view;

import config.Constant;
import config.Menu;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 变量广播词
 *
 * @author ZYY
 */
public class VariableBroad {

    private static final long serialVersionUID = 1L;
    private JTextField variableTypeField;
    private JComboBox comboBox;

    public VariableBroad() {

    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 549, 376);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        //setContentPane(contentPane);

        JLabel label = new JLabel("新增变量类别:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(47, 72, 111, 43);
        contentPane.add(label);

        //变量类别输入框
        variableTypeField = new JTextField();
        variableTypeField.setBounds(159, 72, 206, 43);
        contentPane.add(variableTypeField);
        variableTypeField.setColumns(10);

        JLabel showTypeLabel = new JLabel("展示类型:");
        showTypeLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        showTypeLabel.setBounds(79, 136, 79, 43);
        contentPane.add(showTypeLabel);

        comboBox = new JComboBox();
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"下拉框", "输入框"}));
        comboBox.setBounds(159, 141, 206, 38);
        contentPane.add(comboBox);

        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(202, 246, 99, 36);
        contentPane.add(submitButton);

        //提交按钮
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeToXML();
            }
        });
        return contentPane;
    }

    //xml处理
    public void writeToXML() {
        File file1 = new File(Constant.UPLOAD_RESOURCE_PATH);
        //文件夹不存在，则新建新建
        if (!file1.exists()) {
            file1.mkdirs();
        }

        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.VARIABLE_BROAD.getCode() + "']/hashValue");
        //新增
        if (null == node) {
            addXML(document, getNewId(true));
        } else {//修改
            updateXML(document, getNewId(false));
        }
    }

    public void updateXML(Document document, Map<String, String> map) {

        Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.VARIABLE_BROAD.getCode() + "']/hashValue");
        Element resourceTypeElement = node.getParent();
        Element hashValueElement = resourceTypeElement.addElement("hashValue");
        hashValueElement.addAttribute("id", map.get("id"));
        hashValueElement.addAttribute("type", variableTypeField.getText());
        hashValueElement.addAttribute("show", comboBox.getSelectedItem().toString());

        try {
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addXML(Document document, Map<String, String> map) {
        Element rootElement = document.getRootElement();

        Element resourceTypeElement = rootElement.addElement("resourceType");
        resourceTypeElement.addAttribute("typeId", Menu.VARIABLE_BROAD.getCode());

        Element hashValueElement = resourceTypeElement.addElement("hashValue");
        hashValueElement.addAttribute("id", map.get("id"));
        hashValueElement.addAttribute("type", variableTypeField.getText());
        hashValueElement.addAttribute("show", comboBox.getSelectedItem().toString());
        try {
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getNewId(boolean isAdd) {
        Map<String, String> map = new HashMap<>();
        String typeId = Menu.VARIABLE_BROAD.getCode();
        map.put("typeId", typeId);
        if (isAdd) {
            String id = typeId + 1;
            map.put("id", id);
        } else {
            Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
            Element resourceTypeElement = document.selectSingleNode("//resourceType[@typeId='" + Menu.VARIABLE_BROAD.getCode() + "']/hashValue").getParent();
            List<Element> hashValueElements = resourceTypeElement.elements();
            String id = hashValueElements.get(hashValueElements.size() - 1).attributeValue("id");
            String newId = typeId + (Integer.valueOf(id.replace(Menu.VARIABLE_BROAD.getCode(), "")) + 1);
            map.put("id", newId);
        }

        return map;
    }

}
