package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

/**
 * @Description: 添加常量广播词
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 14:56
 * @Version: 1.0
 */
public class JFrameRun extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrameRun frame = new JFrameRun();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JFrameRun() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 657, 331);
        JPanel contentPane = new JPanel();
        contentPane.setForeground(Color.BLACK);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel languageLabel = new JLabel("语种:");
        languageLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        languageLabel.setBounds(38, 49, 54, 15);
        contentPane.add(languageLabel);

        //语种下拉框
        JComboBox<String> languageComboBox = new JComboBox<String>();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn"}));
        languageComboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        languageComboBox.setToolTipText("1");
        languageComboBox.setBounds(89, 36, 91, 36);
        contentPane.add(languageComboBox);

        //资源
        JLabel resourceLabel = new JLabel("资源:");
        resourceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        resourceLabel.setBounds(38, 109, 54, 15);
        contentPane.add(resourceLabel);

        //资源名称
        JTextField resourceTextField = new JTextField();
        resourceTextField.setBounds(89, 101, 385, 36);
        contentPane.add(resourceTextField);
        resourceTextField.setColumns(10);

        //导入音频显示文件名
        JLabel audioName = new JLabel();
        audioName.setBounds(89, 149, 299, 15);
        contentPane.add(audioName);

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
        audioButton.setBackground(new Color(30, 144, 255));
        audioButton.setForeground(new Color(240, 248, 255));
        audioButton.setBounds(503, 104, 91, 28);
        contentPane.add(audioButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(89, 174, 93, 36);
        contentPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(languageComboBox.getSelectedIndex() + "," + languageComboBox.getSelectedItem());
                System.out.println(resourceTextField.getText());
                System.out.println(audioName.getText());
                writeXML();
            }
        });
    }

    public void writeXML() {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("resources");
        Element actionElement1 = rootElement.addElement("resourceType");
        actionElement1.addAttribute("typeId", "100");
        Element forwardElement1 = actionElement1.addElement("hashValue");
        forwardElement1.addAttribute("id", "111");
        Element forwardElement2 = actionElement1.addElement("resource");
        forwardElement2.addAttribute("language", "chn");
        forwardElement2.addAttribute("value", "fail.jsp");
        forwardElement2.addAttribute("url", "fail.jsp");
        forwardElement2.addAttribute("hashId", "11111");
        try {
            /**
             *  指定文本的写出的格式：
             *      紧凑格式 ：项目上线时候使用
             *      漂亮格式：开发调试的时候使用
             */
            //OutputFormat format=OutputFormat.createCompactFormat();  //紧凑格式:去除空格换行
            OutputFormat format = OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
            /**
             * 指定生成的xml文档的编码影响了：
             *         1.xml文档保存时的编码
             *         2.xml文档声明的encoding编码（Xml解析时的编码）
             * 结论：使用该方法生成Xml文档可以避免中文乱码问题
             */
            format.setEncoding("UTF-8");
            /** 将document中的内容写入文件中 */
            XMLWriter writer = new XMLWriter(new FileWriter(new File(
                    "f://resource.xml")), format);
            writer.write(document);
            writer.close();
            /** 执行成功,需返回1 */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
