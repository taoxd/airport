package view;

import config.Constant;
import config.Menu;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.StringUtils;
import util.DOMUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.*;

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
    private JList<String> constBroadList;

    //固定广播词数组
    private String[] constArr;

    //XML命名数组
    private String[] chnXMLArr;

    //模版内容
    private JTextArea templateContent;

    private JComboBox xmlComboBox;

    private JTextField chnField;
    private JTextField engField;
    //定义初始值为1
    private Map<String, Integer> initMap;


    private List<Element> templateElementList = new ArrayList<>();

    public Index() {
        this.setName("index");
        initMap();
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        variableBroadType();
        getXMLName();
        constBroad();
    }

    //初始化数据
    public void initMap() {
        initMap = new HashMap<>();
        initMap.put("c", 1);
        initMap.put("s", 1);
        initMap.put("e", 1);
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
        label.setBounds(234, 165, 72, 15);
        this.add(label);

        JComboBox languageCcomboBox = new JComboBox();
        languageCcomboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        languageCcomboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn", "Eng"}));
        languageCcomboBox.setBounds(318, 156, 225, 31);
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
        label_1.setBounds(217, 220, 88, 31);
        this.add(label_1);

        System.out.print("固定广播词数组: " + Arrays.toString(constArr));
        //固定广播词列表
        constBroadList = new JList(constArr);
        constBroadList.setBackground(SystemColor.control);
        constBroadList.setForeground(new Color(0, 0, 0));
        constBroadList.setFont(new Font("宋体", Font.PLAIN, 16));
        //constBroadList.setLineWrap(true);
        constBroadList.setBounds(318, 220, 400, 192);
        //constBroadList.setListData(constArr);
        JScrollPane s = new JScrollPane(constBroadList);
        s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        s.setBounds(318, 220, 400, 192);
        this.add(s);
        ListModel<String> model = constBroadList.getModel();
        for (int i =0;i<model.getSize();i++){
            System.out.print(model.getElementAt(i));

        }


        constBroadList.addMouseListener(new MouseListener() {
            //鼠标单击事件
            @Override
            public void mouseClicked(MouseEvent e) {
                String constBroad = constBroadList.getSelectedValue().toString();
                Element resourceElement = (Element) document.selectSingleNode("//resourceType[@flag='" + Constant.CONST + "']/hashValue/resource[@value='" + constBroad + "' and @language='" + languageTemp + "']");
                String hashId = resourceElement.attributeValue("hashId");
                Element constElement = DocumentHelper.createElement("const");

                Integer c = initMap.get("c");
                constElement.addAttribute("name", "c" + c);
                initMap.put("c", c + 1);
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

        JLabel label_2 = new JLabel("可变广播词:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(216, 450, 93, 31);
        this.add(label_2);

        JComboBox variableBroadComboBox = new JComboBox();
        variableBroadComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        variableBroadComboBox.setModel(new DefaultComboBoxModel(variableTypeArr));
        variableBroadComboBox.setBounds(318, 450, 225, 31);
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

                    String str = show.substring(0, 1);
                    Integer num = initMap.get(str);
                    Element variableElement = DocumentHelper.createElement(show);
                    variableElement.addAttribute("name", str + num);
                    initMap.put(str, num + 1);
                    variableElement.addAttribute("typeId", typeId);
                    variableElement.addAttribute("hashId", hashId);
                    templateElementList.add(variableElement);

                    templateContent.append(value);
                }
            }
        });

        JLabel label_3 = new JLabel("模版内容:");
        label_3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_3.setBounds(232, 515, 72, 25);
        this.add(label_3);

        /**
         * jTextArea.append("输入内容"+"\r\n");
         * 这样，每次输入的话，都是另起一行的。
         */
        templateContent = new JTextArea();
        //自动换行
        templateContent.setLineWrap(true);
        //不可编辑
        //templateContent.setEditable(false);
        templateContent.setFont(new Font("宋体", Font.PLAIN, 16));
        //templateContent.setBounds(317, 534, 340, 86);
        templateContent.setPreferredSize(new Dimension(400, 130));
        Dimension preferredSize = templateContent.getPreferredSize();
        JScrollPane jScrollPane = new JScrollPane(templateContent);
        jScrollPane.setBounds(317, 515, (int) preferredSize.getWidth(), (int) preferredSize.getHeight());
        this.add(jScrollPane);

        JButton resetButton = new JButton("重置");
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
        resetButton.setBounds(316, 680, 93, 31);
        this.add(resetButton);

        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //中文别名
                String chnFieldText = chnField.getText();
                //英文别名
                String engFieldText = engField.getText();
                //模版内容
                String text = templateContent.getText();

                if (xmlComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "XML命名不能为空！请新增类别...", "提示", 1);
                    return;
                }
                if (StringUtils.isEmpty(chnFieldText)) {
                    JOptionPane.showMessageDialog(null, "广播词中文名不能为空！", "提示", 1);
                    return;
                }
                if (StringUtils.isEmpty(engFieldText)) {
                    JOptionPane.showMessageDialog(null, "英文别名不能为空！", "提示", 1);
                    return;
                }
                if (StringUtils.isEmpty(text)) {
                    JOptionPane.showMessageDialog(null, "请添加模版内容！", "提示", 1);
                    return;
                }


                //提交数据
                //submitData();
            }
        });
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(564, 680, 93, 31);
        this.add(submitButton);

        this.setVisible(true);

        return this;
    }

    public void submitData() {

        //xml命名_中文
        String chnXml = xmlComboBox.getSelectedItem().toString();
        //xml英文名
        String engXML = DOMUtils.getEngXMLName(chnXml);
        //中文别名
        String chnFieldText = chnField.getText();
        //英文别名
        String engFieldText = engField.getText();

        //获取临时temp.xml
        Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
        Element resourceElement = (Element) tempDocument.selectSingleNode("//resource[@chnXML='" + chnXml + "' and @engXML='" + engXML + "']");

        //获取模版document
        StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                .append("\\").append(engXML).append(".xml");
        Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());
        Element templateGroupElement = templateDocument.getRootElement();

        //没有根节点，即新文件
        if (templateGroupElement == null) {
            Element rootElement = templateDocument.addElement("templateGroup");
            rootElement.addAttribute("id", "1").addAttribute("grCaption", chnXml)
                    .addAttribute("grCaptionEng", engXML);

            Element templateElement = rootElement.addElement("template");
            templateElement.addAttribute("index", "1").addAttribute("caption", chnFieldText)
                    .addAttribute("captionEng", engFieldText);

            Element templateObjsElement = templateElement.addElement("templateObjs");
            templateObjsElement.addAttribute("language", languageTemp).addAttribute("playCheckBox", "0");
            for (Element ele : templateElementList) {
                templateObjsElement.add(ele);
            }

            //将模版内容写入temp.xml
            Element contentElement = resourceElement.addElement("content");
            contentElement.addAttribute("index", "1");
            contentElement.addAttribute("language", languageTemp);
            contentElement.addAttribute("value", templateContent.getText());
        }

        Node templateNode = templateDocument.selectSingleNode("//template[@caption='" + chnFieldText + "' and @captionEng='" + engFieldText + "']");
        Node templateObjsNode = templateDocument.selectSingleNode("//template[@caption='" + chnFieldText + "' and @captionEng='" + engFieldText + "']/templateObjs[@language='" + languageTemp + "']");

        //有根目录而没有相应的template
        if (templateGroupElement != null && templateNode == null) {
            String lastIndex = "1";
            Node node = templateDocument.selectSingleNode("//template[last()]");
            if (node == null) {
                lastIndex = "1";
            } else {
                Integer index = Integer.valueOf(((Element) node).attributeValue("index"));
                lastIndex = String.valueOf(index + 1);
            }

            Element templateElement = templateGroupElement.addElement("template");
            templateElement.addAttribute("index", lastIndex).addAttribute("caption", chnFieldText)
                    .addAttribute("captionEng", engFieldText);

            Element templateObjsElement = templateElement.addElement("templateObjs");
            templateObjsElement.addAttribute("language", languageTemp).addAttribute("playCheckBox", "0");
            for (Element ele : templateElementList) {
                templateObjsElement.add(ele);
            }

            //将模版内容写入temp.xml
            Element contentElement = resourceElement.addElement("content");
            contentElement.addAttribute("index", lastIndex);
            contentElement.addAttribute("language", languageTemp);
            contentElement.addAttribute("value", templateContent.getText());

        }


        if (templateGroupElement != null && templateNode != null && templateObjsNode != null) {
            JOptionPane.showMessageDialog(this, "已存在相同模版", "提示", 1);
            return;
        }


        if (templateGroupElement != null && templateNode != null && templateObjsNode == null) {
            Element templateElement = (Element) templateNode;
            Element templateObjsElement = templateElement.addElement("templateObjs");
            templateObjsElement.addAttribute("language", languageTemp).addAttribute("playCheckBox", "0");
            for (Element ele : templateElementList) {
                templateObjsElement.add(ele);
            }

            //将模版内容写入temp.xml
            Element contentElement = resourceElement.addElement("content");
            contentElement.addAttribute("index", templateElement.attributeValue("index"));
            contentElement.addAttribute("language", languageTemp);
            contentElement.addAttribute("value", templateContent.getText());

        }

        try {
            //写xml文件
            DOMUtils.writeXMLToFile(templateDocument, templatePath.toString());
            //写xml文件
            DOMUtils.writeXMLToFile(tempDocument, Constant.TEMP_PATH + Constant.TEMP_FILE);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JFrame jf = (JFrame) (getRootPane().getParent());
        JTree menuTree = SwingUtils.getMenuTree(jf);

        TreeNode rootTreeNode = (TreeNode) menuTree.getModel().getRoot();//取得tree的根节点
        //是否有相同中文xml
        boolean haveSameChnXML = visitAllNodes(rootTreeNode, chnXml);
        if (!haveSameChnXML) {
            //模版列表路径
            TreePath treePath = menuTree.getPathForRow(2);
            //添加节点
            SwingUtils.addNode(jf, chnXml, treePath);
        }

        //清空模版内容
        templateContent.setText("");
        //清空
        templateElementList.clear();
        chnField.setText("");
        engField.setText("");

        //初始化数据
        initMap();


    }

    public boolean visitAllNodes(TreeNode node, String chnXML) {

        //默认没有相同中文xml
        boolean isSameChnXML = false;
        TreeNode templateList = null;
        if (node.getChildCount() >= 0) {//判断是否有子节点
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                String nodeName = ((DefaultMutableTreeNode) n).getUserObject().toString();
                //从根节点查找模版列表
                if (Menu.TEMPLATE_LIST.getName().equals(nodeName)) {
                    templateList = n;
                    break;
                }
            }
        }

        if (templateList.getChildCount() > 0) {
            for (Enumeration temp = templateList.children(); temp.hasMoreElements(); ) {
                TreeNode tempNode = (TreeNode) temp.nextElement();
                String nodeName = ((DefaultMutableTreeNode) tempNode).getUserObject().toString();
                //从模版列表查找是否有相同中文xml
                if (chnXML.equals(nodeName)) {
                    isSameChnXML = true;
                    break;
                }
            }

        }
        return isSameChnXML;

    }
}
