package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.border.BevelBorder;

/**
 * 广告词模版
 * @author ZYY
 *
 */
public class AdvertisingWord extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdvertisingWord frame = new AdvertisingWord();
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
	public AdvertisingWord() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 793);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(211, 211, 211));
		desktopPane.setBounds(93, 137, 788, 49);
		contentPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel("内容");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(220, 10, 78, 36);
		desktopPane.add(lblNewLabel);
		
		JLabel label = new JLabel("操作");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(670, 14, 54, 28);
		desktopPane.add(label);
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211)));
		desktopPane_1.setBackground(new Color(255, 255, 255));
		desktopPane_1.setBounds(93, 186, 586, 198);
		contentPane.add(desktopPane_1);
		desktopPane_1.setLayout(null);
		
		JDesktopPane desktopPane_2 = new JDesktopPane();
		desktopPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211)));
		desktopPane_2.setBackground(Color.WHITE);
		desktopPane_2.setBounds(93, 384, 586, 198);
		contentPane.add(desktopPane_2);
		desktopPane_2.setLayout(null);
		
		JDesktopPane desktopPane_3 = new JDesktopPane();
		desktopPane_3.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211)));
		desktopPane_3.setBounds(678, 186, 203, 198);
		contentPane.add(desktopPane_3);
		desktopPane_3.setBackground(Color.WHITE);
		
		JButton button = new JButton("删除");
		button.setBackground(new Color(255, 255, 255));
		button.setFont(new Font("宋体", Font.PLAIN, 16));
		button.setBounds(52, 80, 93, 44);
		desktopPane_3.add(button);
		
		JDesktopPane desktopPane_4 = new JDesktopPane();
		desktopPane_4.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211), new Color(211, 211, 211)));
		desktopPane_4.setBackground(Color.WHITE);
		desktopPane_4.setBounds(678, 384, 203, 198);
		contentPane.add(desktopPane_4);
		
		JButton button_1 = new JButton("删除");
		button_1.setBackground(new Color(255, 255, 255));
		button_1.setFont(new Font("宋体", Font.PLAIN, 16));
		button_1.setBounds(52, 77, 93, 44);
		desktopPane_4.add(button_1);
	}
}
