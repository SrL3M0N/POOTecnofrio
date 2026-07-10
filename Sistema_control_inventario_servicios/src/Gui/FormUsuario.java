package Gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField; // Importación necesaria para el campo de contraseña
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Color;
import java.awt.Font;

import Clases.Usuario;
import Clases.Rol;
import DAO.UsuarioDAO;
import DAO.RolDAO;

public class FormUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsaurio;
	private JPasswordField txtContrasena; // Cambiado de JTextField a JPasswordField
	private JComboBox<Clases.Empleado2> cboEmpleado;
	
	private JTable tableUsuarios;
	private JComboBox<String> cboEstado;
	private JComboBox<Rol> cboRol;
	
	private int idUsuarioSeleccionado = 0; 
	private int idEmpleadoSeleccionado = 0; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormUsuario frame = new FormUsuario();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FormUsuario() {
		setTitle("Gestión de Usuarios - Refrigeración Tecnofrío");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 793, 450); 
		getContentPane().setLayout(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 777, 411);
		getContentPane().add(contentPane);
		
		txtUsaurio = new JTextField();
		txtUsaurio.setColumns(10);
		txtUsaurio.setBounds(107, 11, 154, 20);
		contentPane.add(txtUsaurio);
		
		// Inicialización cambiada a JPasswordField
		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(107, 50, 154, 20);
		contentPane.add(txtContrasena);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(21, 53, 62, 14);
		contentPane.add(lblContrasea);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(21, 14, 62, 14);
		contentPane.add(lblUsuario);
		
		// --- BOTONES ---
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarUsuario();
			}
		});
		btnGuardar.setBounds(21, 240, 240, 23);
		contentPane.add(btnGuardar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarUsuario();
			}
		});
		btnModificar.setBounds(21, 274, 240, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
		btnEliminar.setBounds(21, 308, 240, 23);
		contentPane.add(btnEliminar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});
		btnLimpiar.setBounds(21, 342, 240, 23);
		contentPane.add(btnLimpiar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 50, 466, 315);
		contentPane.add(scrollPane);
		
		tableUsuarios = new JTable();
		tableUsuarios.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "ID", "Usuario", "Estado", "Rol", "Empleado", "idRol", "idEmp", "Clave" }
		) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		});
		
		tableUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tableUsuarios.getSelectedRow();
				if (fila >= 0) {
					idUsuarioSeleccionado = Integer.parseInt(tableUsuarios.getValueAt(fila, 0).toString());
					txtUsaurio.setText(tableUsuarios.getValueAt(fila, 1).toString());
					cboEstado.setSelectedItem(tableUsuarios.getValueAt(fila, 2).toString());
					txtContrasena.setText(tableUsuarios.getValueAt(fila, 7).toString());
					
					idEmpleadoSeleccionado = Integer.parseInt(tableUsuarios.getValueAt(fila, 6).toString());
					for (int i = 0; i < cboEmpleado.getItemCount(); i++) {
						if (cboEmpleado.getItemAt(i).getIdEmpleado() == idEmpleadoSeleccionado) {
							cboEmpleado.setSelectedIndex(i);
							break;
						}
					}
					
					
					int idRolFila = Integer.parseInt(tableUsuarios.getValueAt(fila, 5).toString());
					for (int i = 0; i < cboRol.getItemCount(); i++) {
						if (cboRol.getItemAt(i).getIdRol() == idRolFila) {
							cboRol.setSelectedIndex(i);
							break;
						}
					}
				}
			}
		});
		scrollPane.setViewportView(tableUsuarios);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(21, 85, 64, 14);
		contentPane.add(lblEstado);
		
		cboEstado = new JComboBox<String>();
		cboEstado.addItem("Activo");
		cboEstado.addItem("Inactivo");
		cboEstado.setBounds(107, 81, 154, 22);
		contentPane.add(cboEstado);
		
		JLabel lblRol = new JLabel("Rol");
		lblRol.setBounds(21, 118, 64, 14);
		contentPane.add(lblRol);
		
		cboRol = new JComboBox<Rol>();
		cboRol.setBounds(107, 114, 154, 22);
		contentPane.add(cboRol);
		
		JLabel lblEmpleado = new JLabel("Empleado");
		lblEmpleado.setBounds(21, 151, 80, 14);
		contentPane.add(lblEmpleado);
		
		cboEmpleado = new JComboBox<Clases.Empleado2>();
		cboEmpleado.setBounds(107, 147, 154, 22); 
		contentPane.add(cboEmpleado);

		cboEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clases.Empleado2 empSeleccionado = (Clases.Empleado2) cboEmpleado.getSelectedItem();
				if (empSeleccionado != null) {
					idEmpleadoSeleccionado = empSeleccionado.getIdEmpleado();
				}
			}
		});

		ajustarColumnasTabla();
		cargarRoles();
		cargarEmpleados(); 
		mostrarDatos();
		setLocationRelativeTo(null);
	}

	// =========================================================================
	// LÓGICA DE CONTROL (CRUD)
	// =========================================================================

	private void ajustarColumnasTabla() {
		int[] columnasAOcultar = {5, 6, 7};
		for (int col : columnasAOcultar) {
			tableUsuarios.getColumnModel().getColumn(col).setMinWidth(0);
			tableUsuarios.getColumnModel().getColumn(col).setMaxWidth(0);
			tableUsuarios.getColumnModel().getColumn(col).setPreferredWidth(0);
		}
	}

	private void cargarRoles() {
		cboRol.removeAllItems();
		RolDAO dao = new RolDAO();
		List<Rol> lista = dao.listarRoles();
		for (Rol r : lista) {
			cboRol.addItem(r);
		}
	}

	private void cargarEmpleados() {
		cboEmpleado.removeAllItems(); 
		DAO.EmpleadoDAO2 dao = new DAO.EmpleadoDAO2();
		List<Clases.Empleado2> lista = dao.listarEmpleadosConDetalle(); 
		for (Clases.Empleado2 e : lista) {
			cboEmpleado.addItem(e); 
		}
	}

	private void mostrarDatos() {
		DefaultTableModel modelo = (DefaultTableModel) tableUsuarios.getModel();
		modelo.setRowCount(0);
		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> lista = dao.listarUsuarios();
		
		for (Usuario u : lista) {
			modelo.addRow(new Object[] {
				u.getIdUsuario(),
				u.getUsuario(),
				u.isEstado() ? "Activo" : "Inactivo",
				u.getNombreRol(),
				u.getNombreEmpleado(),
				u.getIdRol(),
				u.getIdEmpleado(),
				u.getClave()
			});
		}
	}

	private void guardarUsuario() {
		String user = txtUsaurio.getText().trim();
		String pass = String.valueOf(txtContrasena.getPassword()).trim();
		Rol rolSeleccionado = (Rol) cboRol.getSelectedItem();

		if (idEmpleadoSeleccionado == 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado válido.", "Validación", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (user.isEmpty() || pass.isEmpty() || rolSeleccionado == null) {
			JOptionPane.showMessageDialog(this, "Complete todos los campos necesarios.", "Validación", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar formato de correo electrónico
		if (!user.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
		    JOptionPane.showMessageDialog(this,
		            "Ingrese un correo electrónico válido.",
		            "Validación",
		            JOptionPane.WARNING_MESSAGE);
		    txtUsaurio.requestFocus();
		    return;
		}
		// Instanciamos el DAO para realizar las comprobaciones en la Base de Datos
		UsuarioDAO dao = new UsuarioDAO();

		// 1. VALIDACIÓN DEL NOMBRE DE USUARIO EXISTENTE
		if (dao.existeNombreUsuario(user)) {
			JOptionPane.showMessageDialog(this, "Nombre Usado, ingrese otro", "Usuario Duplicado", JOptionPane.ERROR_MESSAGE);
			txtUsaurio.requestFocus();
			return;
		}

		// 2. VALIDACIÓN DEL ID DE EMPLEADO YA USADO
		if (dao.existeIdEmpleado(idEmpleadoSeleccionado)) {
			JOptionPane.showMessageDialog(this, "Id ya usado, Ingrese otro", "Empleado con Usuario", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Usuario u = new Usuario();
		u.setUsuario(user);
		u.setClave(pass);
		u.setEstado(cboEstado.getSelectedItem().toString().equals("Activo"));
		u.setIdRol(rolSeleccionado.getIdRol());
		u.setIdEmpleado(idEmpleadoSeleccionado); 

		if (dao.registrarUsuario(u)) {
			JOptionPane.showMessageDialog(this, "¡Usuario creado de forma exitosa!");
			mostrarDatos();
			limpiarCampos();
		}
	}

	private void modificarUsuario() {
		int fila = tableUsuarios.getSelectedRow();
		if (idUsuarioSeleccionado == 0 || fila < 0) {
			JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (idEmpleadoSeleccionado == 0) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado válido.", "Validación", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String user = txtUsaurio.getText().trim();
		String pass = String.valueOf(txtContrasena.getPassword()).trim();
		
		// Recuperamos los datos que originalmente estaban en la tabla antes de ser editados en las cajas
		String usuarioOriginal = tableUsuarios.getValueAt(fila, 1).toString();
		int idEmpOriginal = Integer.parseInt(tableUsuarios.getValueAt(fila, 6).toString());

		UsuarioDAO dao = new UsuarioDAO();

		// Comprobar usuario duplicado SOLO si el usuario escribió un nombre diferente al original
		if (!user.equalsIgnoreCase(usuarioOriginal) && dao.existeNombreUsuario(user)) {
			JOptionPane.showMessageDialog(this, "Nombre Usado, ingrese otro", "Usuario Duplicado", JOptionPane.ERROR_MESSAGE);
			txtUsaurio.requestFocus();
			return;
		}

		// Comprobar ID empleado usado SOLO si se seleccionó un empleado diferente al original
		if (idEmpleadoSeleccionado != idEmpOriginal && dao.existeIdEmpleado(idEmpleadoSeleccionado)) {
			JOptionPane.showMessageDialog(this, "Id ya usado, Ingrese otro", "Empleado con Usuario", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Usuario u = new Usuario();
		u.setIdUsuario(idUsuarioSeleccionado);
		u.setUsuario(user);
		u.setClave(pass);
		u.setEstado(cboEstado.getSelectedItem().toString().equals("Activo"));
		u.setIdRol(((Rol) cboRol.getSelectedItem()).getIdRol());
		u.setIdEmpleado(idEmpleadoSeleccionado);

		if (dao.modificarUsuario(u)) {
			JOptionPane.showMessageDialog(this, "¡Usuario actualizado correctamente!");
			mostrarDatos();
			limpiarCampos();
		}
	}

	private void eliminarUsuario() {
		if (idUsuarioSeleccionado == 0) {
			JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			UsuarioDAO dao = new UsuarioDAO();
			if (dao.eliminarUsuario(idUsuarioSeleccionado)) {
				JOptionPane.showMessageDialog(this, "Usuario eliminado del sistema.");
				mostrarDatos();
				limpiarCampos();
			}
		}
	}

	private void limpiarCampos() {
		txtUsaurio.setText("");
		txtContrasena.setText("");
		
		if (cboEmpleado.getItemCount() > 0) {
			cboEmpleado.setSelectedIndex(0);
		}
		
		cboEstado.setSelectedIndex(0);
		if (cboRol.getItemCount() > 0) cboRol.setSelectedIndex(0);
		idUsuarioSeleccionado = 0;
		idEmpleadoSeleccionado = 0;
		tableUsuarios.clearSelection();
		mostrarDatos(); 
	}
}