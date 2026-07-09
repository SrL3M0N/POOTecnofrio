package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Clases.Sesion;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Form_principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menuInventario;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenu menuVentas;
	private JMenu menuClientes;
	private JMenu menuProveedores;
	private JMenu menuReportes;
	private JMenu menuSalir;
	private JMenu menuServicio;
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmNewMenuItem_4;
	private JMenu menuUsuarios;
	private JLabel lblUsuario;
	private JLabel lblFecha;
	private JLabel lblHora;
	private JMenu menuEmpleados;
	private JMenuItem mntmNewMenuItem_6;
	private JMenuItem mntmNewMenuItem_7;
	private JLabel lblNewLabel;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem mntmNewMenuItem_5;
	private JMenuItem mntmNewMenuItem_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form_principal frame = new Form_principal();
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
	public Form_principal() {
		
		setTitle("Sistema gestor de inventario, ventas y servicios");
		//this.setUndecorated(true);//->Quita el borde
		//this.setResizable(false);
		this.setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 810, 386);
		setLocationRelativeTo(null);

		// HORA Y FECHA DINÁMICA
		Timer timer = new Timer(1000, e -> {
			LocalDateTime ahora = LocalDateTime.now();
			String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			String fecha = ahora.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
			
			lblHora.setText(hora);
			lblFecha.setText(fecha);
		});
		timer.start();

		// MENÚ BAR
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				menuUsuarios = new JMenu("Usuarios");
				menuUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuUsuarios);
				{
					mntmNewMenuItem_6 = new JMenuItem("Usuarios");
					mntmNewMenuItem_6.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_6_actionPerformed(e);
						}
					});
					menuUsuarios.add(mntmNewMenuItem_6);
				}
				{
					mntmNewMenuItem_7 = new JMenuItem("Roles");
					mntmNewMenuItem_7.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_7_actionPerformed(e);
						}
					});
					menuUsuarios.add(mntmNewMenuItem_7);
				}
			}
			{
				menuInventario = new JMenu("Inventario");
				menuInventario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuInventario);
				{
					mntmNewMenuItem = new JMenuItem("Categoria");
					mntmNewMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_actionPerformed(e);
						}
					});
					menuInventario.add(mntmNewMenuItem);
				}
				{
					mntmNewMenuItem_1 = new JMenuItem("Productos");
					mntmNewMenuItem_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_1_actionPerformed(e);
						}
					});
					menuInventario.add(mntmNewMenuItem_1);
				}
			}
			{
				menuServicio = new JMenu("Servicios");
				menuServicio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuServicio);
				{
					mntmNewMenuItem_4 = new JMenuItem("Servicio");
					mntmNewMenuItem_4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_4_actionPerformed(e);
						}
					});
					menuServicio.add(mntmNewMenuItem_4);
				}
			}
			{
				menuClientes = new JMenu("Clientes");
				menuClientes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						do_mnNewMenu_3_mouseClicked(e);
					}
				});
				menuClientes.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuClientes);
			}
			{
				menuVentas = new JMenu("Ventas");
				menuVentas.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuVentas);
				{
					mntmNewMenuItem_2 = new JMenuItem("Venta");
					mntmNewMenuItem_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_2_actionPerformed(e);
						}
					});
					menuVentas.add(mntmNewMenuItem_2);
				}
			}
			{
				menuProveedores = new JMenu("Proveedores");
				menuProveedores.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						do_menuProveedores_mouseClicked(e);
					}
				});
				menuProveedores.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuProveedores);
			}
			{
				menuEmpleados = new JMenu("Empleados");
				menuEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuEmpleados);
			}
			{
				menuReportes = new JMenu("Reportes");
				menuReportes.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuReportes);
				{
					mntmNewMenuItem_3 = new JMenuItem("Reporte Ventas");
					mntmNewMenuItem_3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_3_actionPerformed(e);
						}
					});
					menuReportes.add(mntmNewMenuItem_3);
				}
				{
					mntmNewMenuItem_5 = new JMenuItem("Reporte Clientes");
					mntmNewMenuItem_5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_5_actionPerformed(e);
						}
					});
					menuReportes.add(mntmNewMenuItem_5);
				}
				{
					mntmNewMenuItem_8 = new JMenuItem("Reporte Producto Más Vendidos");
					mntmNewMenuItem_8.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							do_mntmNewMenuItem_8_actionPerformed(e);
						}
					});
					menuReportes.add(mntmNewMenuItem_8);
				}
			}
			menuBar.add(javax.swing.Box.createHorizontalGlue());
			{
				menuSalir = new JMenu("Cerrar Sesión");
				menuSalir.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						do_mnNewMenu_6_mouseClicked(e);
					}
				});
				menuSalir.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				menuBar.add(menuSalir);
			}
		}

		// CONTENEDOR VISUAL
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblUsuario = new JLabel("");
			lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblUsuario.setBounds(538, 33, 232, 14);
			contentPane.add(lblUsuario);
		}
		
		lblUsuario.setText("Usuario: " + Sesion.nombreEmpleado);
		{
			lblFecha = new JLabel("");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 22));
			lblFecha.setBounds(42, 272, 423, 37);
			contentPane.add(lblFecha);
		}
		{
			lblHora = new JLabel("");
			lblHora.setFont(new Font("Tahoma", Font.PLAIN, 35));
			lblHora.setBounds(72, 209, 300, 61);
			contentPane.add(lblHora);
		}
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(Form_principal.class.getResource("/Images/LogoEmpresa.jpeg")));
			lblNewLabel.setBounds(37, 11, 223, 148);
			contentPane.add(lblNewLabel);
		}
		
		// VALIDACIÓN DE SEGURIDAD POR ROL
		if ("Vendedor".equalsIgnoreCase(Sesion.rol)) {
			menuUsuarios.setEnabled(false);
			menuEmpleados.setEnabled(false);
			menuInventario.setEnabled(false);
			menuReportes.setEnabled(false);
		}
	}

	// =========================================================================
	// MÉTODOS DE ACCIÓN (EVENTOS DE BOTONES Y MENÚS)
	// =========================================================================

	// USUARIOS
	protected void do_mntmNewMenuItem_6_actionPerformed(ActionEvent e) {
		Gui.FormUsuario userForm = new FormUsuario();
		userForm.setVisible(true);
	}

	// ROLES
	protected void do_mntmNewMenuItem_7_actionPerformed(ActionEvent e) {
		Gui.FormRoles rolesForm = new FormRoles();
		rolesForm.setVisible(true);
	}

	// CERRAR SESIÓN
	protected void do_mnNewMenu_6_mouseClicked(MouseEvent e) {
		this.dispose();
		FormLogin login = new FormLogin();
		login.setVisible(true);
	}

	// CATEGORIA
	protected void do_mntmNewMenuItem_actionPerformed(ActionEvent e) {
		Gui.FormCategoria cat = new FormCategoria();
		cat.setVisible(true);
	}

	// PRODUCTOS
	protected void do_mntmNewMenuItem_1_actionPerformed(ActionEvent e) {
		Gui.FormProducto prod = new FormProducto();
		prod.setVisible(true);
	}

	// COMPLEMENTO MOUSE SERVICIO (Opcional)
	protected void do_mnNewMenu_7_mouseClicked(MouseEvent e) {
		Gui.FormServicio serv = new FormServicio();
		serv.setVisible(true);
	}

	// CLIENTES
	protected void do_mnNewMenu_3_mouseClicked(MouseEvent e) {
		Gui.FormCliente cli = new FormCliente();
		cli.setVisible(true);
	}

	// SERVICIO ACCIÓN
	protected void do_mntmNewMenuItem_4_actionPerformed(ActionEvent e) {
		Gui.FormServicio serv = new FormServicio();
		serv.setVisible(true);
	}

	// VENTAS ACCIÓN
	protected void do_mntmNewMenuItem_2_actionPerformed(ActionEvent e) {
		Gui.FormVenta vent = new FormVenta();
		vent.setVisible(true);
	}
	protected void do_menuProveedores_mouseClicked(MouseEvent e) {
		Gui.FormProveedor prov = new FormProveedor();
		prov.setVisible(true);
	}
	protected void do_mntmNewMenuItem_3_actionPerformed(ActionEvent e) {
		Gui.FormReporteVenta prov = new FormReporteVenta();
		prov.setVisible(true);
	}
	protected void do_mntmNewMenuItem_5_actionPerformed(ActionEvent e) {
		Gui.FormReporteCliente prov = new FormReporteCliente();
		prov.setVisible(true);
	}
	protected void do_mntmNewMenuItem_8_actionPerformed(ActionEvent e) {
		Gui.FormReporteProductosMasVendidos prov = new FormReporteProductosMasVendidos();
		prov.setVisible(true);
	}
}