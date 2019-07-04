package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.StringUtils;
import util.DOMUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 弹窗
 *
 * @author ZYY
 */
public class BulletBox extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel rightPanel;
    private JTextField chnField;
    private JTextField engField;

    /**
     * Create the frame.
     */
    public BulletBox(JPanel rightPanel) {
        this.rightPanel = rightPanel;

    }

    public JDialog init() {

        setName("JDialog");
        setTitle("新增模版");
        //获取屏幕的宽度高度
        int screenWidth = (int) this.getToolkit().getScreenSize().getWidth();
        int screenHeight = (int) this.getToolkit().getScreenSize().getHeight();

        int width = 350;
        int height = 250;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        //contentPane = new JPanel();
        //this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label = new JLabel("中文:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(30, 30, 50, 40);
        this.add(label);

        chnField = new JTextField();
        chnField.setBounds(80, 34, 206, 31);
        this.add(chnField);
        chnField.setColumns(10);

        JLabel lblNewLabel = new JLabel("英文:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(30, 90, 50, 43);
        this.add(lblNewLabel);

        engField = new JTextField();
        engField.setBounds(80, 94, 206, 31);
        this.add(engField);
        engField.setColumns(10);

        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(56, 145, 255));
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(120, 150, 99, 31);
        this.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
                String chnText = chnField.getText();
                String engText = engField.getText();

                //判断不能为空
                if (StringUtils.isEmpty(chnText)) {
                    JOptionPane.showMessageDialog(null, "中文不能为空！", "提示", 1);
                    return;
                }
                if (StringUtils.isEmpty(engText)) {
                    JOptionPane.showMessageDialog(null, "英文不能为空！", "提示", 1);
                    return;
                }


                if (!StringUtils.isEmpty(chnText) && !StringUtils.isEmpty(engText)) {
                    Node resourceNode = tempDocument.selectSingleNode("//resource[@chnXML='" + chnText + "' or @engXML='" + engText + "']");
                    //有相同的中英文XML文件
                    if (resourceNode != null) {
                        JOptionPane.showMessageDialog(null, "存在相同命名的XML", "提示", 1);
                        return;
                    } else {
                        Element rootElement = tempDocument.getRootElement();
                        Element resource = rootElement.addElement("resource");
                        resource.addAttribute("chnXML", chnText);
                        resource.addAttribute("engXML", engText);
                        try {
                            //写xml文件
                            DOMUtils.writeXMLToFile(tempDocument, Constant.TEMP_PATH + Constant.TEMP_FILE);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        //重新加载首页，以获得最新数据
                        loadNewData();
                        //关闭弹框
                        dispose();
                    }
                }

            }
        });
        return this;
    }

    //加载首页
    public void loadNewData() {
        rightPanel.removeAll();
        rightPanel.setLayout(null);
        JPanel panel = new Index().init();
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setBounds(-90, 2, 900, 766);
        rightPanel.add(panel);
        rightPanel.repaint();
    }
}
