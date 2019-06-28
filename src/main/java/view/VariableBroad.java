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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 变量广播词(新增类别)
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
        comboBox.setModel(new DefaultComboBoxModel(new String[]{Constant.SELECTION, Constant.EDITBOX}));
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
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Node lastNode = document.selectSingleNode("//resourceType[@flag='" + Constant.VARIABLE + "'][last()]");
        //避免添加相同类型
        Node checkNode = document.selectSingleNode("//resourceType[@type='" + variableTypeField.getText() + "']");
        if (checkNode == null) {
            editXML(document, lastNode);
        }
    }

    private void editXML(Document document, Node lastNode) {
        Map<String, String> newId = getNewId(lastNode);

        Element rootElement = document.getRootElement();
        Element element = rootElement.addElement("resourceType");
        element.addAttribute("typeId", newId.get("typeId"));
        element.addAttribute("type", variableTypeField.getText());
        element.addAttribute("flag", Constant.VARIABLE);
        element.addAttribute("show", getcomboBoxVal(comboBox.getSelectedItem().toString()));
        try {
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Map<String, String> getNewId(Node node) {
        Map<String, String> map = new HashMap<>();
        String typeId;
        if (node == null) {
            typeId = Menu.VARIABLE_BROAD.getCode();
        } else {
            String lastTypeId = ((Element) node).attributeValue("typeId");
            typeId = String.valueOf(Integer.valueOf(lastTypeId) + 1);
        }
        map.put("typeId", typeId);
        return map;
    }


    private String getcomboBoxVal(String name) {
        switch (name) {
            case Constant.SELECTION:
                return Constant.SELECTION_VALUE;
            case Constant.EDITBOX:
                return Constant.EDITBOX_VALUE;
        }
        return null;

    }

}
