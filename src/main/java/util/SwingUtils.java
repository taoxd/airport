package util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.*;

/**
 * @Description: swing相关工具类
 * @Author: taoxudong
 * @CreateDate: 2019/6/29 10:49
 * @Version: 1.0
 */
public class SwingUtils {

    /**
     * 获取树对象
     *
     * @param jf
     * @return
     */
    public static JTree getMenuTree(JFrame jf) {
        JTree tree = null;
        //获得大容器
        Container contentPane = jf.getContentPane();
        //获得所有组件
        Component[] components = contentPane.getComponents();
        for (int i = 0; i < components.length; i++) {
            String name = components[i].getName();
            if ("treeScrollPane".equalsIgnoreCase(name)) {
                JScrollPane panel = (JScrollPane) components[i];
                JViewport viewport = (JViewport) panel.getComponent(0);
                tree = (JTree) viewport.getComponent(0);
                break;
            }
        }
        return tree;
    }

    /**
     * 获得rightJpanel
     *
     * @param jf
     */
    public static JPanel getRightPanel(JFrame jf) {
        JPanel panel = null;
        //获得大容器
        Container contentPane = jf.getContentPane();
        //获得所有组件
        Component[] components = contentPane.getComponents();
        for (int i = 0; i < components.length; i++) {
            String name = components[i].getName();
            if ("rightPanel".equalsIgnoreCase(name)) {
                panel = (JPanel) components[i];
                break;
            }
        }
        return panel;

    }


    /**
     * 添加树节点
     *
     * @param jf
     * @param newNodeName
     * @param treePath
     */
    public static void addNode(JFrame jf, String newNodeName, TreePath treePath) {

        JTree tree = SwingUtils.getMenuTree(jf);
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();

        MutableTreeNode nodeNew = new DefaultMutableTreeNode();
        nodeNew.setUserObject(newNodeName);

        MutableTreeNode selectTreeNode = (MutableTreeNode) treePath.getLastPathComponent();
        int count = selectTreeNode.getChildCount();

        treeModel.insertNodeInto(nodeNew, selectTreeNode, count);

        TreePath NewPath = treePath.pathByAddingChild(nodeNew);
        if (!tree.isVisible(NewPath)) {
            tree.makeVisible(NewPath);
        }

    }


    public static void delNode(JFrame jf) {
        JTree tree = SwingUtils.getMenuTree(jf);
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (!node.isRoot()) {
            DefaultMutableTreeNode nodeNext = node.getNextSibling();
            if (nodeNext == null) {
                nodeNext = (DefaultMutableTreeNode) node.getParent();
            }
            model.removeNodeFromParent(node);
            tree.setSelectionPath(new TreePath(nodeNext.getPath()));
        }

    }


    /**
     * 进行文件的拷贝，利用带缓冲的字节流
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFileByBuffer(File srcFile, File destFile) throws IOException {
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件:" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不是文件");
        }
        BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destFile));
        int c;
        while ((c = bis.read()) != -1) {
            bos.write(c);
            bos.flush();//带缓冲必须刷新缓冲区
        }
        bis.close();
        bos.close();
    }
}
