package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Clases.Sesion;
import Clases.Usuario;
import DAO.UsuarioDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class FormLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblContrasea;
	private JTextField txtUsuario;
	private JLabel lblLogin;
	private JButton btnNewButton;
	private JPasswordField txtClave;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormLogin frame = new FormLogin();
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
	public FormLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblNewLabel = new JLabel("Usuario ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setBounds(275, 50, 103, 25);
			contentPane.add(lblNewLabel);
		}
		{
			lblContrasea = new JLabel("Contraseña");
			lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblContrasea.setBounds(275, 86, 103, 25);
			contentPane.add(lblContrasea);
		}
		{
			txtUsuario = new JTextField();
			txtUsuario.setBounds(382, 54, 174, 20);
			contentPane.add(txtUsuario);
			txtUsuario.setColumns(10);
		}
		{
			lblLogin = new JLabel("Bienvenido");
			lblLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblLogin.setBounds(373, 11, 135, 25);
			contentPane.add(lblLogin);
		}
		{
			btnNewButton = new JButton("Login");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnNewButton.setBounds(280, 134, 278, 23);
			contentPane.add(btnNewButton);
		}
		{
			txtClave = new JPasswordField();
			txtClave.setBounds(384, 86, 170, 20);
			contentPane.add(txtClave);
		}
		{
			lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon(FormLogin.class.getResource("/Images/LogoEmpresa.jpeg")));
			lblNewLabel_1.setBounds(25, 34, 210, 111);
			contentPane.add(lblNewLabel_1);
		}
		setLocationRelativeTo(null);
	}
	//BOTON INICIAR SESION 
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		UsuarioDAO dao = new UsuarioDAO();

		Usuario u = dao.login(
		        txtUsuario.getText().trim(),
		        String.valueOf(txtClave.getPassword())
		);

		if (u != null) {

		    Sesion.idUsuario = u.getIdUsuario();
		    Sesion.idEmpleado = u.getIdEmpleado();
		    Sesion.idRol = u.getIdRol();

		    Sesion.usuario = u.getUsuario();
		    Sesion.nombreEmpleado = u.getNombreEmpleado();
		    Sesion.rol = u.getNombreRol();

		    Form_principal frm = new Form_principal();
		    frm.setVisible(true);

		    this.dispose();

		} else {

		    JOptionPane.showMessageDialog(
		            this,
		            "Usuario o contraseña incorrectos"
		    );
		}
	}
}
