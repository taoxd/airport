package view;

import config.Constant;
import config.Menu;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 首页
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 13:55
 * @Version: 1.0
 */
public class Index extends JPanel {

    //语种选择,默认Chn
    private String languageTemp = "Chn";

    private Document document;

    //可变广播词类型数组
    private String[] variableTypeArr;

    //固定广播词容器
    private JList constBroadList;

    //固定广播词数组
    private String[] constArr;

    //XML命名数组
    private String[] chnXMLArr;

    //模版内容
    private JTextArea templateContent;

    private JComboBox xmlComboBox;

    private JTextField chnField;
    private JTextField engField;


    private List<Element> templateElementList = new ArrayList<>();

    public Index() {
        this.setName("index");
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        variableBroadType();
        getXMLName();
        constBroad();
    }

    /**
     * 获得固定广播词
     */
    public void constBroad() {
        List<String> constList = new ArrayList<>();
        List<Element> constResList = document.selectNodes("//resourceType[@flag='" + Constant.CONST + "']/hashValue/resource[@language='" + languageTemp + "']");
        if (CollectionUtils.isNotEmpty(constResList)) {
            for (Element constRes : constResList) {
                String value = constRes.attributeValue("value");
                constList.add(value);
            }
        }
        constArr = constList.toArray(new String[]{});
    }

    /**
     * 获取xml文件
     */
    public void getXMLName() {
        List<String> list = new ArrayList<>();
        Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
        List<Element> tempList = tempDocument.selectNodes("//resource");
        if (CollectionUtils.isNotEmpty(tempList)) {
            for (Element temp : tempList) {
                String chnXML = temp.attributeValue("chnXML");
                list.add(chnXML);
            }
        }
        chnXMLArr = list.toArray(new String[]{});
    }

    /**
     * 获取变量广播词类型
     */
    public void variableBroadType() {
        List<String> typeList = new ArrayList<>();
        //typeList.add("无");
        List<Element> variableTypeList = document.selectNodes("//resourceType[@flag='" + Constant.VARIABLE + "']");
        if (CollectionUtils.isNotEmpty(variableTypeList)) {
            for (Element variableType : variableTypeList) {
                String type = variableType.attributeValue("type");
                typeList.add(type);
            }
        }
        variableTypeArr = typeList.toArray(new String[]{});
    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 977, 778);
        this.setBackground(new Color(255, 255, 255));
        this.setForeground(new Color(255, 255, 255));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel lblXml = new JLabel("XML命名:");
        lblXml.setFont(new Font("宋体", Font.PLAIN, 16));
        lblXml.setBounds(239, 44, 81, 15);
        this.add(lblXml);

        xmlComboBox = new JComboBox();
        xmlComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        xmlComboBox.setModel(new DefaultComboBoxModel(chnXMLArr));
        xmlComboBox.setBounds(318, 36, 105, 31);
        this.add(xmlComboBox);

        JButton addTypeButton = new JButton("新增类别");
        addTypeButton.setFont(new Font("宋体", Font.PLAIN, 16));
        addTypeButton.setForeground(new Color(255, 255, 255));
        addTypeButton.setBackground(new Color(30, 144, 255));
        addTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //将RightPanel传到弹框
                JFrame jf = (JFrame) (getRootPane().getParent());
                JDialog init = new BulletBox(SwingUtils.getRightPanel(jf)).init();
                init.setVisible(true);
            }
        });
        addTypeButton.setBounds(564, 37, 104, 31);
        this.add(addTypeButton);

        JLabel chnLable = new JLabel("广播词中文名-Chn:");
        chnLable.setFont(new Font("宋体", Font.PLAIN, 16));
        chnLable.setBounds(167, 105, 142, 15);
        this.add(chnLable);

        chnField = new JTextField();
        chnField.setBounds(318, 98, 105, 31);
        this.add(chnField);
        chnField.setColumns(10);


        JLabel engLable = new JLabel("英文别名-Eng:");
        engLable.setFont(new Font("宋体", Font.PLAIN, 16));
        engLable.setBounds(449, 105, 105, 15);
        this.add(engLable);

        engField = new JTextField();
        engField.setBounds(563, 98, 105, 31);
        this.add(engField);
        engField.setColumns(10);

        JLabel label = new JLabel("语种选择:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(234, 169, 72, 15);
        this.add(label);

        JComboBox languageCcomboBox = new JComboBox();
        languageCcomboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        languageCcomboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn", "Eng"}));
        languageCcomboBox.setBounds(318, 161, 225, 31);
        this.add(languageCcomboBox);

        languageCcomboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                //当前语种选择
                String language = languageCcomboBox.getSelectedItem().toString();
                if (!language.equals(languageTemp)) {
                    //设置变量
                    languageTemp = language;

                    constBroad();

                    constBroadList.setListData(constArr);

                    templateContent.setText("");


                    System.out.println("选择了新语种: " + language);
                }
            }
        });


        JLabel label_1 = new JLabel("固定广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(217, 231, 88, 31);
        this.add(label_1);

        constBroadList = new JList();
        constBroadList.setBackground(SystemColor.control);
        constBroadList.setForeground(new Color(0, 0, 0));
        constBroadList.setFont(new Font("宋体", Font.PLAIN, 16));
        //constBroadList.setLineWrap(true);
        constBroadList.setBounds(318, 235, 340, 192);
        this.add(constBroadList);
        constBroadList.setListData(constArr);
        constBroadList.addMouseListener(new MouseListener() {
            //鼠标单击事件
            @Override
            public void mouseClicked(MouseEvent e) {
                String constBroad = constBroadList.getSelectedValue().toString();
                Element resourceElement = (Element) document.selectSingleNode("//resourceType[@flag='" + Constant.CONST + "']/hashValue/resource[@value='" + constBroad + "' and @language='" + languageTemp + "']");
                String hashId = resourceElement.attributeValue("hashId");
                Element constElement = DocumentHelper.createElement("const");
                constElement.addAttribute("name", "c");
                constElement.addAttribute("typeId", Menu.CONSTANT_BROAD.getCode());
                constElement.addAttribute("hashId", hashId);
                templateElementList.add(constElement);

                templateContent.append(constBroad);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        JLabel label_2 = new JLabel("可变广告词:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(216, 464, 93, 31);
        this.add(label_2);

        JComboBox variableBroadComboBox = new JComboBox();
        variableBroadComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        variableBroadComboBox.setModel(new DefaultComboBoxModel(variableTypeArr));
        variableBroadComboBox.setBounds(318, 464, 225, 31);
        this.add(variableBroadComboBox);
        variableBroadComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                //点击可变广播词类型
                String variableBroadType = variableBroadComboBox.getSelectedItem().toString();

                Element resourceTypeElement = (Element) document.selectSingleNode("//resourceType[@flag='" + Constant.VARIABLE + "' and @type='" + variableBroadType + "'][1]");
                String typeId = resourceTypeElement.attributeValue("typeId");
                //selection或者editBox
                String show = resourceTypeElement.attributeValue("show");


                Node node = document.selectSingleNode("//resourceType[@flag='" + Constant.VARIABLE + "' and @type='" + variableBroadType + "']/hashValue/resource[@language='" + languageTemp + "'][1]");
                if (node != null) {
                    String value = ((Element) node).attributeValue("value");
                    String hashId = ((Element) node).attributeValue("hashId");


                    Element variableElement = DocumentHelper.createElement(show);
                    variableElement.addAttribute("name", show.substring(0, 1));
                    variableElement.addAttribute("typeId", typeId);
                    variableElement.addAttribute("hashId", hashId);
                    templateElementList.add(variableElement);

                    templateContent.append(value);
                }
            }
        });


        JLabel label_3 = new JLabel("模版内容:");
        label_3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_3.setBounds(232, 534, 72, 25);
        this.add(label_3);

        templateContent = new JTextArea(10, 20);
        //templateContent.setHorizontalAlignment(SwingConstants.RIGHT);
        templateContent.setEditable(false);
        templateContent.setFont(new Font("宋体", Font.PLAIN, 16));
        templateContent.setBounds(317, 534, 340, 86);
        this.add(templateContent);
        templateContent.setColumns(20);

        JButton resetButton = new JButton("重置");
        resetButton.setForeground(new Color(255, 255, 255));
        resetButton.setBackground(new Color(30, 144, 255));
        resetButton.setFont(new Font("宋体", Font.PLAIN, 16));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //清空模版内容
                templateContent.setText("");
                //清空
                templateElementList.clear();
            }
        });
        resetButton.setBounds(316, 651, 93, 31);
        this.add(resetButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //xml命名_中文
                String chnXml = xmlComboBox.getSelectedItem().toString();

                Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
                Element element = (Element) tempDocument.selectSingleNode("//resource[@chnXML='" + chnXml + "']");
                String engXML = element.attributeValue("engXML");

                StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                        .append("\\").append(engXML).append(".xml");
                Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());

                Element rootElement = templateDocument.addElement("templateGroup");
                rootElement.addAttribute("id", "1").addAttribute("grCaption", chnXml)
                        .addAttribute("grCaptionEng", engXML);

                Element templateElement = rootElement.addElement("template");
                templateElement.addAttribute("index", "1").addAttribute("caption", chnField.getText())
                        .addAttribute("captionEng", engField.getText());

                Element templateObjsElement = templateElement.addElement("templateObjs");
                templateObjsElement.addAttribute("language",languageTemp).addAttribute("playCheckBox","0");

                for (Element ele : templateElementList) {
                    templateObjsElement.add(ele);
                }
                try {
                    //写xml文件
                    DOMUtils.writeXMLToFile(templateDocument, templatePath.toString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(564, 651, 93, 31);
        this.add(submitButton);

        return this;
    }


}
