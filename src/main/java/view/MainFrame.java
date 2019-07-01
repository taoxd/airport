package view;

import config.Constant;
import config.Menu;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主菜单
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 11:14
 * @Version: 1.0
 */
public class MainFrame extends JFrame {
    private JPanel rightPanel;
    private JTree tree;

    public MainFrame() {
        super("东航客舱");
        //获取最新的树
        loadTree();
    }

    public JTree loadTree() {
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Menu.DONG_HANG_KE_CANG.getName());//东航客舱
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(Menu.HOME_PAGE.getName());//主页

        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(Menu.TEMPLATE_LIST.getName());//模版列表
        List<DefaultMutableTreeNode> chnXMLList = getTemplateXMLList();
        for (DefaultMutableTreeNode chnXML : chnXMLList) {
            child2.add(chnXML);
        }

        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode(Menu.RESOURCE_ADD.getName());//资源添加

        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode(Menu.CONSTANT_BROAD.getName());//常量广播词
        List<DefaultMutableTreeNode> constTreeNodes = constTree(document);
        for (DefaultMutableTreeNode node : constTreeNodes) {
            child31.add(node);
        }

        DefaultMutableTreeNode child32 = new DefaultMutableTreeNode(Menu.VARIABLE_BROAD.getName());//变量广播词
        List<DefaultMutableTreeNode> variableTreeNodes = variableTree(document);
        for (DefaultMutableTreeNode node : variableTreeNodes) {
            child32.add(node);
        }

        DefaultMutableTreeNode child4 = new DefaultMutableTreeNode(Menu.PREVIEW_RESOURCE_XML.getName());//资源XML预览
        DefaultMutableTreeNode child5 = new DefaultMutableTreeNode(Menu.EXPORT.getName());//导出
        root.add(child1);
        root.add(child2);
        root.add(child3);
        root.add(child4);
        root.add(child5);
        child3.add(child31);
        child3.add(child32);
        tree = new JTree(root);
        tree.setBackground(Color.blue);
        tree.setName("menuTree");
        // 设置树显示根节点句柄
        tree.setShowsRootHandles(true);
        tree.setPreferredSize(new Dimension(200, 0));
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                treeSelect(e);
            }
        });
        return tree;
    }

    /**
     * 获取所有中文模版名称
     *
     * @return
     */
    public List<DefaultMutableTreeNode> getTemplateXMLList() {
        List<DefaultMutableTreeNode> templateXMLList = new ArrayList<>();

        Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);

        //获取所有模版文件
        File file = new File(Constant.UPLOAD_BROADCAST_PATH);
        File[] tempList = file.listFiles();

        if (tempList != null && tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    String engXMLName = tempList[i].getName();
                    Element xmlElement = (Element) tempDocument.selectSingleNode("//resource[@engXML='" + engXMLName.substring(0, engXMLName.lastIndexOf(".")) + "']");
                    String chnXML = xmlElement.attributeValue("chnXML");

                    DefaultMutableTreeNode chnXMLTreeNode = new DefaultMutableTreeNode(chnXML);

                    Document documentTemplate = DOMUtils.getDocumentTemplate(Constant.UPLOAD_BROADCAST_PATH + "\\" + engXMLName);
                    List<Element> templateList = documentTemplate.selectNodes("//template");
                    for (Element template : templateList) {
                        String caption = template.attributeValue("caption");
                        chnXMLTreeNode.add(new DefaultMutableTreeNode(caption));
                    }
                    chnXMLTreeNode.add(new DefaultMutableTreeNode(Menu.PREVIEW_TEMPLATE_XML.getName()));
                    templateXMLList.add(chnXMLTreeNode);
                }
            }
        }
        return templateXMLList;
    }

    /**
     * 获取最新的常量广播词
     *
     * @param document
     * @return
     */
    public List<DefaultMutableTreeNode> constTree(Document document) {
        List<DefaultMutableTreeNode> list = new ArrayList<>();

        //固定常量中文
        List<Element> resourceList = document.selectNodes("//resourceType[@flag='" + Constant.CONST + "']/hashValue/resource[@language='Chn']");
        DefaultMutableTreeNode chnTreeNode;

        for (Element resourceType : resourceList) {
            //比如你好
            String value = resourceType.attributeValue("value");

            chnTreeNode = new DefaultMutableTreeNode(value);

            //1001
            String hashValueId = resourceType.getParent().attributeValue("id");

            List<Element> languageNotChnList = document.selectNodes("//hashValue[@id='" + hashValueId + "']/resource[@language!='Chn']");
            if (CollectionUtils.isNotEmpty(languageNotChnList)) {
                for (Element languageNotChn : languageNotChnList) {
                    chnTreeNode.add(new DefaultMutableTreeNode(languageNotChn.attributeValue("value")));
                }
            }

            list.add(chnTreeNode);
        }
        return list;

    }

    /**
     * 获取最新变量广播词
     *
     * @param document
     * @return
     */
    public List<DefaultMutableTreeNode> variableTree(Document document) {
        List<DefaultMutableTreeNode> list = new ArrayList<>();

        //变量大类型，城市，数值，机型
        List<Element> resourceTypeList = document.selectNodes("//resourceType[@flag='" + Constant.VARIABLE + "']");
        DefaultMutableTreeNode treeNode;
        for (Element resourceType : resourceTypeList) {
            //比如城市
            String type = resourceType.attributeValue("type");
            treeNode = new DefaultMutableTreeNode(type);

            //城市列表--中文
            List<Element> languageChnList = document.selectNodes("//resourceType[@type='" + type + "']/hashValue/resource[@language='Chn']");
            if (CollectionUtils.isNotEmpty(languageChnList)) {
                for (Element languageChn : languageChnList) {

                    //比如中国
                    String value = languageChn.attributeValue("value");
                    DefaultMutableTreeNode chnTreeNode = new DefaultMutableTreeNode(value);

                    String hashValueId = languageChn.getParent().attributeValue("id");
                    //城市--非中文
                    List<Element> languageNotChnList = document.selectNodes("//hashValue[@id='" + hashValueId + "']/resource[@language!='Chn']");
                    if (CollectionUtils.isNotEmpty(languageNotChnList)) {
                        for (Element languageNotChn : languageNotChnList) {
                            chnTreeNode.add(new DefaultMutableTreeNode(languageNotChn.attributeValue("value")));
                        }
                    }

                    treeNode.add(chnTreeNode);
                }
            }
            list.add(treeNode);
        }
        return list;
    }

    /**
     * 初始化容器
     */
    public void init() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        // 创建滚动面板，包裹树（因为树节点展开后可能需要很大的空间来显示，所以需要用一个滚动面板来包裹）
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setName("treeScrollPane");
        scrollPane.getViewport().add(tree, null);
        container.add(scrollPane, BorderLayout.WEST);

        rightPanel = new JPanel();
        rightPanel.setName("rightPanel");
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.pink);
        //p.setPreferredSize(new Dimension(180, 400));
        container.add(rightPanel, BorderLayout.CENTER);
        this.setBounds(400, 100, 1000, 800);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    private void treeSelect(TreeSelectionEvent e) {
        rightPanel.removeAll();
        //路径个数
        int pathCount = e.getPath().getPathCount();
        //选中菜单名
        String selectLastPathName = e.getPath().getLastPathComponent().toString();
        System.out.println("路径个数: " + e.getPath());
        System.out.println("路径: " + pathCount);
        System.out.println("选择: " + e.getPath().getPathComponent(pathCount - 1));
        if (Menu.CONSTANT_BROAD.getName().equals(selectLastPathName)) {//常量广播词ConstBroadAdd
            JPanel panel = new ConstBroadAdd(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Menu.VARIABLE_BROAD.getName().equals(selectLastPathName)) {//点击变量广播词VariableBroadAdd
            JPanel panel = new VariableBroadAdd(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Menu.HOME_PAGE.getName().equals(selectLastPathName)) {//点击首页
            JPanel panel = new Index().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else if (Menu.PREVIEW_RESOURCE_XML.getName().equals(selectLastPathName)) {//点击资源XML预览

            String document = DOMUtils.documentToString(DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME), "utf8");
            JTextArea jTextArea = new JTextArea(document);
            jTextArea.setEditable(false);
            jTextArea.setBackground(Color.GREEN);
            jTextArea.setBounds(5, 5, 800, 800);
            rightPanel.add(jTextArea);
        } else if (pathCount == 4 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量中文大类VariableBroadType
            JPanel panel = new VariableBroadType(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (pathCount == 4 && Menu.CONSTANT_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//常量中文ConstBroadChn
            JPanel panel = new ConstBroadChn(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (pathCount == 5 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量中文VariableBroadChn
            JPanel panel = new VariableBroadChn(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else if (pathCount == 5 && Menu.CONSTANT_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//常量非中文ConstBroadNotChn
            JPanel panel = new ConstBroadNotChn(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (pathCount == 6 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量非中文VariableBroadNotChn
            JPanel panel = new VariableBroadNotChn(e.getPath()).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 1400, 800);
            rightPanel.add(panel);
        } else if (pathCount == 4 && Menu.TEMPLATE_LIST.getName().equals(e.getPath().getPathComponent(1).toString())) {//点击模版列表中的template
            JPanel panel = new AdvertisingWord(e.getPath()).init();
            panel.setBackground(Color.RED);
            panel.setBounds(5, 5, 1000, 1000);
            rightPanel.add(panel);
        } else if (pathCount == 2 && Menu.EXPORT.getName().equals(e.getPath().getPathComponent(1).toString())) {//点击导出


        } else {
            JLabel l = new JLabel(e.getPath().toString());
            l.setBounds(5, 190, 250, 20);
            rightPanel.add(l);

        }

        rightPanel.repaint();
    }

}
