package Gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Form_Creditos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form_Creditos frame = new Form_Creditos();
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
	public Form_Creditos() {
		// CORREGIDO: Se cambia EXIT_ON_CLOSE por DISPOSE_ON_CLOSE para no cerrar todo el sistema
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 798, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(Form_Creditos.class.getResource("/Images/cred.png")));
			lblNewLabel.setBounds(10, 62, 475, 289);
			contentPane.add(lblNewLabel);
		}
		{
			lblNewLabel_1 = new JLabel("Creditos");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 27));
			lblNewLabel_1.setBounds(174, 11, 123, 40);
			contentPane.add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("- CHUMACERO CHERO, Saul Emilio");
			lblNewLabel_2.setBounds(508, 131, 214, 20);
			contentPane.add(lblNewLabel_2);
		}
		{
			lblNewLabel_3 = new JLabel("- TIMOTEO VENTURO, Ronal Steven");
			lblNewLabel_3.setBounds(508, 162, 214, 20);
			contentPane.add(lblNewLabel_3);
		}
		{
			lblNewLabel_4 = new JLabel("- CARCHERI MENACHO, Italo Randu");
			lblNewLabel_4.setBounds(508, 193, 214, 20);
			contentPane.add(lblNewLabel_4);
		}
		{
			lblNewLabel_5 = new JLabel("- QUISPE CIPRIAN EDGAR, Alexander Yamil");
			lblNewLabel_5.setBounds(508, 224, 214, 20);
			contentPane.add(lblNewLabel_5);
		}
		{
			lblNewLabel_6 = new JLabel("- QUINTERO GONZALEZ, Luis Alejandro");
			lblNewLabel_6.setBounds(508, 255, 214, 20);
			contentPane.add(lblNewLabel_6);
		}
		{
			lblNewLabel_7 = new JLabel("     Desarrolladores");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel_7.setBounds(508, 80, 150, 40);
			contentPane.add(lblNewLabel_7);
		}

	}
}