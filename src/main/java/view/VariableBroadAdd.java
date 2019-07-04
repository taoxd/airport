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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 变量广播词(新增类别)
 *
 * @author ZYY
 */
public class VariableBroadAdd extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField variableTypeField;
    private JComboBox comboBox;
    private TreePath treePath;

    public VariableBroadAdd(TreePath treePath) {
        this.treePath = treePath;
    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 549, 376);
        //JPanel contentPane = new JPanel();
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label = new JLabel("新增变量类别:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(47, 72, 111, 31);
        this.add(label);

        //变量类别输入框
        variableTypeField = new JTextField();
        variableTypeField.setBounds(159, 72, 206, 31);
        this.add(variableTypeField);
        variableTypeField.setColumns(10);

        JLabel showTypeLabel = new JLabel("展示类型:");
        showTypeLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        showTypeLabel.setBounds(79, 136, 79, 31);
        this.add(showTypeLabel);

        comboBox = new JComboBox();
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        comboBox.setModel(new DefaultComboBoxModel(new String[]{Constant.SELECTION, Constant.EDITBOX}));
        comboBox.setBounds(159, 136, 206, 31);
        this.add(comboBox);

        JButton submitButton = new JButton("提交");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(new Color(56, 145, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(159, 210, 99, 31);
        this.add(submitButton);

        //提交按钮
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //变量类型不能为空
                if (StringUtils.isEmpty(variableTypeField.getText())) {
                    JOptionPane.showMessageDialog(null, "请输入变量类别!", "提示", 1);
                } else {
                    writeToXML();
                    //提交完清空变量类型
                    variableTypeField.setText("");
                }
            }
        });
        return this;
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
            //写XML
            DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
            //动态添加节点
            JFrame jf = (JFrame) (getRootPane().getParent());
            SwingUtils.addNode(jf, variableTypeField.getText(), treePath);
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
