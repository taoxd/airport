package view;

import config.Constant;
import config.Menu;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;
import util.ZipCompressor;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
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
        if (CollectionUtils.isNotEmpty(chnXMLList)) {
            for (DefaultMutableTreeNode chnXML : chnXMLList) {
                child2.add(chnXML);
            }
        }

        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode(Menu.RESOURCE_ADD.getName());//资源添加

        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode(Menu.CONSTANT_BROAD.getName());//常量广播词
        List<DefaultMutableTreeNode> constTreeNodes = constTree(document);
        if (CollectionUtils.isNotEmpty(constTreeNodes)) {
            for (DefaultMutableTreeNode node : constTreeNodes) {
                child31.add(node);
            }
        }

        DefaultMutableTreeNode child32 = new DefaultMutableTreeNode(Menu.VARIABLE_BROAD.getName());//变量广播词
        List<DefaultMutableTreeNode> variableTreeNodes = variableTree(document);
        if (CollectionUtils.isNotEmpty(variableTreeNodes)) {
            for (DefaultMutableTreeNode node : variableTreeNodes) {
                child32.add(node);
            }
        }

        DefaultMutableTreeNode child30 = new DefaultMutableTreeNode(Menu.PREVIEW_RESOURCE_XML.getName());//资源XML预览

        DefaultMutableTreeNode child5 = new DefaultMutableTreeNode(Menu.EXPORT.getName());//导出
        root.add(child1);
        root.add(child2);
        root.add(child3);
        root.add(child5);
        child3.add(child30);
        child3.add(child31);
        child3.add(child32);
        tree = new JTree(root);
        //tree.setBackground(new Color(199, 230, 204));
        tree.setBackground(Color.WHITE);
        tree.setName("menuTree");
        // 设置树显示根节点句柄
        tree.setShowsRootHandles(true);
        tree.setBounds(2, 2, 195, 766);
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

        //获取所有模版文件
        File file = new File(Constant.UPLOAD_BROADCAST_PATH);
        File[] tempList = file.listFiles();

        if (tempList != null && tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    //有后缀的
                    String engXMLName = tempList[i].getName();
                    String chnXML = DOMUtils.getChnXMLName(engXMLName.substring(0, engXMLName.lastIndexOf(".")));

                    DefaultMutableTreeNode chnXMLTreeNode = new DefaultMutableTreeNode(chnXML);
                    //默认中文xml第一路径添加预览
                    chnXMLTreeNode.add(new DefaultMutableTreeNode(Menu.PREVIEW_TEMPLATE_XML.getName()));

                    StringBuilder append = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH).append("\\").append(engXMLName);
                    Document documentTemplate = DOMUtils.getDocumentTemplate(append.toString());
                    if (documentTemplate == null) continue;
                    List<Element> templateList = documentTemplate.selectNodes("//template");
                    for (Element template : templateList) {
                        String caption = template.attributeValue("caption");
                        chnXMLTreeNode.add(new DefaultMutableTreeNode(caption));
                    }
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
        //scrollPane.getViewport().add(tree, null);
        //scrollPane.setVisible(true);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setViewportBorder(BorderFactory.createEtchedBorder());

        JPanel leftPanel = new JPanel();
        JScrollPane js = new JScrollPane(tree);
        js.setName("treeScrollPane");
        //分别设置水平和垂直滚动条自动出现
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        leftPanel.setName("leftPanel");
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(238, 238, 238));
        leftPanel.setPreferredSize(new Dimension(200, 400));


        js.setBounds(2, 2, 195, 766);
        //取消导航滚动条边框
        js.setBorder(null);
        leftPanel.add(js);

        container.add(BorderLayout.WEST, leftPanel);


        rightPanel = new JPanel();
        rightPanel.setName("rightPanel");
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(238, 238, 238));
        //p.setPreferredSize(new Dimension(180, 400));
        container.add(rightPanel, BorderLayout.CENTER);

        //获取屏幕的宽度高度
        int screenWidth = (int) this.getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) this.getToolkit().getScreenSize().getHeight();

        int width = 1000;
        int height = 800;

        //设置jframe位置
        this.setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);

        //不能改变frame大小
        this.setResizable(false);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void treeSelect(TreeSelectionEvent e) {
        rightPanel.removeAll();
        //路径个数
        int pathCount = e.getPath().getPathCount();
        //选中菜单名
        String selectLastPathName = e.getPath().getLastPathComponent().toString();
        System.out.println("路径: " + e.getPath());
        System.out.println("路径个数: " + pathCount);
        System.out.println("选择: " + e.getPath().getPathComponent(pathCount - 1));
        if (Menu.CONSTANT_BROAD.getName().equals(selectLastPathName)) {//常量广播词ConstBroadAdd
            JPanel panel = new ConstBroadAdd(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 140, 789, 400);
            rightPanel.add(panel);
        } else if (Menu.VARIABLE_BROAD.getName().equals(selectLastPathName)) {//点击变量广播词VariableBroadAdd
            JPanel panel = new VariableBroadAdd(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 140, 789, 400);
            rightPanel.add(panel);
        } else if (pathCount == 1 || Menu.HOME_PAGE.getName().equals(selectLastPathName)) {//点击首页或根目录
            JPanel panel = new Index().init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(-90, 2, 900, 766);
            rightPanel.add(panel);

        } else if ((pathCount == 2 && Menu.RESOURCE_ADD.getName().equals(selectLastPathName)) || Menu.PREVIEW_RESOURCE_XML.getName().equals(selectLastPathName)) {//点击资源XML预览资源添加

            String document = DOMUtils.documentToString(DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME), "utf8");
            JTextArea jTextArea = new JTextArea(document);
            JScrollPane js = new JScrollPane(jTextArea);

            //分别设置水平和垂直滚动条自动出现
            js.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            jTextArea.setEditable(false);
            jTextArea.setBackground(new Color(238, 238, 238));
            jTextArea.setBounds(2, 2, 789, 766);
            js.setBounds(2, 2, 789, 766);
            js.setBorder(null);
            rightPanel.add(js);
        } else if (pathCount == 4 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量中文大类VariableBroadType,城市
            JPanel panel = new VariableBroadType(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 150, 789, 350);
            rightPanel.add(panel);
        } else if (pathCount == 4 && Menu.CONSTANT_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//常量中文ConstBroadChn
            JPanel panel = new ConstBroadChn(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 150, 789, 400);
            rightPanel.add(panel);
        } else if (pathCount == 5 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量中文VariableBroadChn
            JPanel panel = new VariableBroadChn(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 130, 789, 500);
            rightPanel.add(panel);

        } else if (pathCount == 5 && Menu.CONSTANT_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//常量非中文ConstBroadNotChn
            JPanel panel = new ConstBroadNotChn(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 200, 789, 400);
            rightPanel.add(panel);
        } else if (pathCount == 6 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//变量非中文VariableBroadNotChn
            JPanel panel = new VariableBroadNotChn(e.getPath()).init();
            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 200, 789, 400);
            rightPanel.add(panel);
        } else if (pathCount == 2 && Menu.TEMPLATE_LIST.getName().equals(e.getPath().getLastPathComponent().toString())) {//点击模版列表

            Document documentTemp = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
            Node node = documentTemp.selectSingleNode("//resource[1]/content");
            if (node == null) {
                //跳首页
                JPanel panel = new Index().init();
                panel.setBackground(new Color(0, 0, 0, 0));
                panel.setBounds(-90, 2, 900, 766);
                rightPanel.add(panel);
                rightPanel.repaint();
                return;

            } else {
                //没有后缀
                String engXML = node.getParent().attributeValue("engXML");
                //获取模版document
                StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                        .append("\\").append(engXML).append(".xml");
                Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());

                String document = DOMUtils.documentToString(templateDocument, "utf8");
                JTextArea jTextArea = new JTextArea(document);

                JScrollPane js = new JScrollPane(jTextArea);

                //分别设置水平和垂直滚动条自动出现
                js.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                js.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                jTextArea.setEditable(false);
                jTextArea.setBackground(new Color(238, 238, 238));
                jTextArea.setBounds(2, 2, 789, 766);
                js.setBounds(2, 2, 789, 766);
                js.setBorder(null);
                rightPanel.add(js);
            }
        } else if (pathCount == 3 && Menu.TEMPLATE_LIST.getName().equals(e.getPath().getPathComponent(1).toString())) {//点击模版列表中某个模版

            String chnXML = e.getPath().getLastPathComponent().toString();
            String engXML = DOMUtils.getEngXMLName(chnXML);
            //获取模版document
            StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                    .append("\\").append(engXML).append(".xml");
            Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());
            String document = DOMUtils.documentToString(templateDocument, "utf8");
            JTextArea jTextArea = new JTextArea(document);

            JScrollPane js = new JScrollPane(jTextArea);

            //分别设置水平和垂直滚动条自动出现
            js.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jTextArea.setEditable(false);
            jTextArea.setBackground(new Color(238, 238, 238));
            jTextArea.setBounds(2, 23, 789, 766);
            js.setBounds(2, 2, 789, 766);
            js.setBorder(null);
            rightPanel.add(js);

        } else if (pathCount == 4 && Menu.PREVIEW_TEMPLATE_XML.getName().equals(e.getPath().getLastPathComponent().toString())) {//点击模版XML预览
            String chnXML = e.getPath().getPathComponent(2).toString();
            String engXML = DOMUtils.getEngXMLName(chnXML);

            //获取模版document
            StringBuilder templatePath = new StringBuilder(Constant.UPLOAD_BROADCAST_PATH)
                    .append("\\").append(engXML).append(".xml");
            Document templateDocument = DOMUtils.getDocumentTemplate(templatePath.toString());

            String document = DOMUtils.documentToString(templateDocument, "utf8");

            JTextArea jTextArea = new JTextArea(document);

            JScrollPane js = new JScrollPane(jTextArea);

            //分别设置水平和垂直滚动条自动出现
            js.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jTextArea.setEditable(false);
            jTextArea.setBackground(new Color(238, 238, 238));
            jTextArea.setBounds(2, 2, 789, 766);
            js.setBounds(2, 2, 789, 766);
            js.setBorder(null);
            rightPanel.add(js);
        } else if (pathCount == 4 && Menu.TEMPLATE_LIST.getName().equals(e.getPath().getPathComponent(1).toString())) {//点击模版列表中的template
            JPanel panel = new AdvertisingWord(e.getPath()).init();
            JScrollPane js = new JScrollPane(panel);

            //分别设置水平和垂直滚动条自动出现
            js.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            js.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            panel.setBackground(new Color(0, 0, 0, 0));
            panel.setBounds(2, 2, 789, 766);
            js.setBounds(2, 2, 789, 766);
            js.setBorder(null);
            rightPanel.add(js);
        } else if (pathCount == 2 && Menu.EXPORT.getName().equals(e.getPath().getPathComponent(1).toString())) {//点击导出

            int i = JOptionPane.showConfirmDialog(null, "确定导出吗？", "提示", 0);
            //确认删除
            if (i == JOptionPane.YES_OPTION) {
                //列出Windows下所有可用磁盘
                File[] parts = File.listRoots();
                for (File part : parts) {
                    System.out.println(part.getAbsolutePath());
                }

                FileSystemView fsv = FileSystemView.getFileSystemView();
                File com = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
                ZipCompressor zc = new ZipCompressor(com.getPath() + Constant.EXPORT_DEST_DIR);// 压缩后的目标文件
                zc.compress(Constant.EXPORT_SRC_DIR);// 需要压缩的文件

                JOptionPane.showMessageDialog(null, "导出成功！", "提示", JOptionPane.PLAIN_MESSAGE);

            }
        } else {
            JLabel l = new JLabel(e.getPath().toString());
            l.setBounds(5, 190, 250, 20);
            rightPanel.add(l);
        }

        rightPanel.repaint();
    }

}
