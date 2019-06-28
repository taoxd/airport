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
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主菜单
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 11:14
 * @Version: 1.0
 */
public class MainFrame extends JFrame {
    private JPanel leftPanel;
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

        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode(Menu.RESOURCE_ADD.getName());//资源添加

        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode(Menu.CONSTANT_BROAD.getName());//常量广播词
        List<Element> resourceList = document.selectNodes("//resourceType[@typeId='" + Menu.CONSTANT_BROAD.getCode() + "']/hashValue/resource[@language='Chn']");
        if (CollectionUtils.isNotEmpty(resourceList)) {
            for (Element ele : resourceList) {
                child31.add(new DefaultMutableTreeNode(ele.attributeValue("value")));
            }
        }

        DefaultMutableTreeNode child32 = new DefaultMutableTreeNode(Menu.VARIABLE_BROAD.getName());//变量广播词
        List<DefaultMutableTreeNode> treeNodes = variableTree(document);
        for (DefaultMutableTreeNode node : treeNodes) {
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
        return tree;
    }

    /**
     * 获取最新变量广播词
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
        // 设置树显示根节点句柄
        tree.setShowsRootHandles(true);
        tree.setPreferredSize(new Dimension(200, 0));
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                treeSelect(e);
            }
        });
        // 创建滚动面板，包裹树（因为树节点展开后可能需要很大的空间来显示，所以需要用一个滚动面板来包裹）
        JScrollPane scrollPane = new JScrollPane(tree);

        leftPanel = new JPanel();
        leftPanel.setName("leftPanel");
        leftPanel.setBackground(Color.RED);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBounds(5, 5, 800, 0);
        leftPanel.add(scrollPane);
        container.add(leftPanel, BorderLayout.WEST);

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
        if (Menu.CONSTANT_BROAD.getName().equals(selectLastPathName)) {//常量广播词
            JPanel panel = new ConstantBroad().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Menu.VARIABLE_BROAD.getName().equals(selectLastPathName)) {//点击变量广播词
            JPanel panel = new VariableBroad().init();
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
        } else if (pathCount == 4 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//比如点击了城市
            JPanel panel = new VariableTypeBroad(selectLastPathName).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else if (pathCount == 5 && Menu.VARIABLE_BROAD.getName().equals(e.getPath().getPathComponent(2).toString())) {//比如点击了中国
            String category = e.getPath().getParentPath().getLastPathComponent().toString();
            String variableBroadName = e.getPath().getLastPathComponent().toString();
            JPanel panel = new VariableThird(category, variableBroadName).init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else {
            JLabel l = new JLabel(e.getPath().toString());
            l.setBounds(5, 190, 250, 20);
            rightPanel.add(l);

        }

        rightPanel.repaint();
    }

}
