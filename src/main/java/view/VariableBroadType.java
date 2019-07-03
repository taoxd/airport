package view;

import config.Constant;
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
 * @Description: 变量zhongwen
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 14:56
 * @Version: 1.0
 */
public class VariableBroadType extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox languageComboBox;
    private JTextField resourceTextField;
    private JLabel audioName;
    private Element element;
    private Document document;
    private TreePath treePath;


    public VariableBroadType(TreePath treePath) {
        this.treePath = treePath;
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        element = (Element) document.selectSingleNode("//resourceType[@type='" + treePath.getLastPathComponent().toString() + "']");
    }

    public JPanel init() {
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        //setBounds(100, 100, 657, 331);
        this.setForeground(Color.BLACK);
        this.setBackground(Color.WHITE);
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel languageLabel = new JLabel("语种:");
        languageLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        languageLabel.setBounds(38, 62, 54, 15);
        this.add(languageLabel);

        //语种下拉框
        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(89, 50, 91, 36);
        this.add(languageComboBox);

        //资源
        JLabel resourceLabel = new JLabel("资源:");
        resourceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceLabel.setBounds(38, 130, 54, 15);
        this.add(resourceLabel);

        //资源名称
        resourceTextField = new JTextField();
        resourceTextField.setBounds(89, 120, 385, 36);
        this.add(resourceTextField);
        resourceTextField.setColumns(10);

        //导入音频显示文件名
        audioName = new JLabel();
        audioName.setBounds(89, 170, 299, 15);
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
        audioButton.setBackground(new Color(42, 163, 255));
        audioButton.setBounds(503, 123, 91, 28);
        this.add(audioButton);

        JButton delButton = new JButton("删除");
        delButton.setBackground(new Color(42, 163, 255));
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBounds(120, 200, 93, 36);
        this.add(delButton);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "删除提示", 0);
                if (i==JOptionPane.YES_OPTION){
                    //删除树节点
                    JFrame jf = (JFrame) (getRootPane().getParent());
                    SwingUtils.delNode(jf);
                    //删除xml
                    element.getParent().remove(element);
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
        submitButton.setBackground(new Color(42, 163, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(320, 200, 93, 36);
        this.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resourceName = resourceTextField.getText();
                String audioNameText = audioName.getText();

                if (StringUtils.isEmpty(resourceName)) {
                    JOptionPane.showMessageDialog(null, "请输入资源！", "提示", 1);
                    return;
                }
                if (StringUtils.isEmpty(audioNameText)) {
                    JOptionPane.showMessageDialog(null, "请导入音频！", "提示", 1);
                    return;
                }
                writeToXML();
            }
        });

        return this;
    }

    //xml处理
    public void writeToXML() {
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);

        Node node = document.selectSingleNode("//resourceType[@type='" + treePath.getLastPathComponent().toString() + "']/hashValue/resource[@language='" + languageComboBox.getSelectedItem().toString() + "' and @value='" + resourceTextField.getText() + "']");
        if (node!=null){
            JOptionPane.showMessageDialog(null,"存在相同资源！","提示",1);
            return;
        }
        updateXML(document, getNewId());
        //提交完之后，清空
        resourceTextField.setText("");
        audioName.setText("");
    }


    public void updateXML(Document document, Map<String, String> map) {
        Element resourceTypeElement = (Element) document.selectSingleNode("//resourceType[@type='" + treePath.getLastPathComponent().toString() + "']");
        Element hashValueElement = resourceTypeElement.addElement("hashValue");
        hashValueElement.addAttribute("id", map.get("id"));
        Element resource = hashValueElement.addElement("resource");
        resource.addAttribute("language", languageComboBox.getSelectedItem().toString());
        resource.addAttribute("value", resourceTextField.getText());
        resource.addAttribute("url", new File(audioName.getText()).getName());
        resource.addAttribute("hashId", map.get("hashId"));
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
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Element ele = (Element) document.selectSingleNode("//resourceType[@type='" + treePath.getLastPathComponent().toString() + "']");
        String typeId = ele.attributeValue("typeId");
        String id;
        Node node = document.selectSingleNode("//resourceType[@type='" + treePath.getLastPathComponent().toString() + "']/hashValue[last()]");
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
