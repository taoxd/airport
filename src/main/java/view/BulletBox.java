package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
/**
 * 弹窗
 * @author ZYY
 *
 */
public class BulletBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BulletBox frame = new BulletBox();
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
	public BulletBox() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 549, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel label = new JLabel("中文:");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(108, 72, 50, 43);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(159, 72, 206, 43);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("英文:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(108, 136, 50, 43);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(159, 138, 206, 43);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton button = new JButton("提交");
		button.setBackground(new Color(30, 144, 255));
		button.setForeground(new Color(255, 255, 255));
		button.setFont(new Font("宋体", Font.PLAIN, 16));
		button.setBounds(202, 246, 99, 36);
		contentPane.add(button);
		
		
	}
}
