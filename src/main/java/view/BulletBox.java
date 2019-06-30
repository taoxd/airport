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

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 549, 376);
        //contentPane = new JPanel();
        //this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label = new JLabel("中文:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(108, 72, 50, 43);
        this.add(label);

        chnField = new JTextField();
        chnField.setBounds(159, 72, 206, 43);
        this.add(chnField);
        chnField.setColumns(10);

        JLabel lblNewLabel = new JLabel("英文:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(108, 136, 50, 43);
        this.add(lblNewLabel);

        engField = new JTextField();
        engField.setBounds(159, 138, 206, 43);
        this.add(engField);
        engField.setColumns(10);

        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(202, 246, 99, 36);
        this.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Document tempDocument = DOMUtils.getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
                String chnText = chnField.getText();
                String engText = engField.getText();
                if (!StringUtils.isEmpty(chnText) && !StringUtils.isEmpty(engText)) {
                    Node resourceNode = tempDocument.selectSingleNode("//resource[@chnXML='" + chnText + "' or @engXML='" + engText + "']");
                    if (resourceNode == null) {
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
                    }
                }

                //重新加载首页，以获得最新数据
                loadNewData();
                //关闭弹框
                dispose();
            }
        });
        return this;
    }

    //加载首页
    public void loadNewData() {
        rightPanel.removeAll();
        JPanel panel = new Index().init();
        panel.setBackground(Color.GREEN);
        panel.setBounds(5, 5, 800, 800);
        rightPanel.add(panel);
        rightPanel.repaint();
    }
}
