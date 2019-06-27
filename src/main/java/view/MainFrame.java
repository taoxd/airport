package view;

import config.Constant;
import config.Menu;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主菜单
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 11:14
 * @Version: 1.0
 */
public class MainFrame extends JFrame {
    private JPanel rightPanel;

    public MainFrame(String title) {
        super(title);
        init();
    }

    public JTree loadTree() {
        Document document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        Node node = document.selectSingleNode("//resourceType[@typeId='" + Menu.VARIABLE_BROAD.getCode() + "']/hashValue");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Menu.DONG_HANG_KE_CANG.getName());//东航客舱
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(Menu.HOME_PAGE.getName());//主页

        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(Menu.TEMPLATE_LIST.getName());//模版列表

        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode(Menu.RESOURCE_ADD.getName());//资源添加
        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode(Menu.CONSTANT_BROAD.getName());//常量广播词
        DefaultMutableTreeNode child32 = new DefaultMutableTreeNode(Menu.VARIABLE_BROAD.getName());//变量广播词
        if (null != node) {
            Element resourceTypeElement = node.getParent();
            List<Map<String, String>> mapList = DOMUtils.getNameNodeAllKidsAttributeMap(resourceTypeElement);
            for (Map<String, String> map : mapList) {
                String type = map.get("type");
                child32.add(new DefaultMutableTreeNode(type));
            }
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
        JTree tree = new JTree(root);

        return tree;
    }


    public void init() {

        Container container = this.getContentPane();


        JTree tree = loadTree();

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

        container.add(scrollPane, BorderLayout.WEST);

        rightPanel = new JPanel();
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
        //选中菜单名
        String selectLastPathName = e.getPath().getLastPathComponent().toString();
        if (Menu.CONSTANT_BROAD.getName().equals(selectLastPathName)) {//常量广播词
            JPanel panel = new ConstantBroad().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Menu.VARIABLE_BROAD.getName().equals(selectLastPathName)) {//变量广播词
            JPanel panel = new VariableBroad().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Menu.HOME_PAGE.getName().equals(selectLastPathName)) {
            JPanel panel = new Index().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else if (Menu.PREVIEW_RESOURCE_XML.getName().equals(selectLastPathName)) {

            String document = DOMUtils.documentToString(DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME), "utf8");
            JTextArea jTextArea = new JTextArea(document);
            jTextArea.setEditable(false);
            jTextArea.setBackground(Color.GREEN);
            jTextArea.setBounds(5, 5, 800, 800);
            rightPanel.add(jTextArea);
        } else {
            JLabel l = new JLabel(e.getPath().toString());
            l.setBounds(5, 190, 250, 20);
            rightPanel.add(l);

        }

        rightPanel.repaint();
    }

}
