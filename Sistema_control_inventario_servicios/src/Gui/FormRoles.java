package Gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class FormRoles extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombreRol;
	
	// --- COMPONENTES AGREGADOS PARA INTEGRACIÓN CON BD ---
	private JTable tableRoles;   // La tabla que va dentro del scrollPane
	private int idRolSeleccionado = 0; // Variable global oculta para guardar el ID seleccionado

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormRoles frame = new FormRoles();
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
	public FormRoles() {
		setTitle("Gestión de Roles - Refrigeración Tecnofrío");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(24, 43, 62, 14);
		contentPane.add(lblNombre);
		
		txtNombreRol = new JTextField();
		txtNombreRol.setColumns(10);
		txtNombreRol.setBounds(96, 40, 154, 20);
		contentPane.add(txtNombreRol);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(288, 25, 304, 184);
		contentPane.add(scrollPane);
		
		// --- Inicialización del JTable ---
		tableRoles = new JTable();
		tableRoles.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "ID Rol", "Nombre del Rol" }
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Evita que se editen las celdas directamente
			}
		});
		
		// Evento al dar clic sobre las filas de la tabla
		tableRoles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tableRoles.getSelectedRow();
				if (fila >= 0) {
					// Pasamos el texto del nombre directo al campo visible
					txtNombreRol.setText(tableRoles.getValueAt(fila, 1).toString());
					
					// Guardamos el ID de forma silenciosa en nuestra variable interna
					idRolSeleccionado = Integer.parseInt(tableRoles.getValueAt(fila, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(tableRoles);
		
		// --- CONFIGURACIÓN DE BOTONES CON SUS ACCIONES ---
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarRol();
			}
		});
		btnGuardar.setBounds(22, 114, 240, 23);
		contentPane.add(btnGuardar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false); // Deshabilitado por seguridad al no requerirse actualización directa en el script
		btnModificar.setBounds(22, 148, 240, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarRol();
			}
		});
		btnEliminar.setBounds(22, 182, 240, 23);
		contentPane.add(btnEliminar);

		// Cargar de forma automática la lista al abrir la pantalla
		mostrarDatos();
		setLocationRelativeTo(null);
	}
	
	// =========================================================================
	// METODOS LOGICOS (CRUD) CONECTADOS A TU ROLDAO
	// =========================================================================
	
	private void mostrarDatos() {
		DefaultTableModel modelo = (DefaultTableModel) tableRoles.getModel();
		modelo.setRowCount(0); // Limpiar datos viejos
		
		DAO.RolDAO dao = new DAO.RolDAO();
		List<Clases.Rol> lista = dao.listarRoles();
		
		for (Clases.Rol r : lista) {
			modelo.addRow(new Object[] {
				r.getIdRol(),
				r.getNombre()
			});
		}
	}
	
	private void guardarRol() {
		String nombre = txtNombreRol.getText().trim();
		
		// 1. Validación de campo vacío
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del rol.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		// 2. CORRECCIÓN: Validación para no permitir números ni caracteres especiales
		if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
			JOptionPane.showMessageDialog(this, "El nombre del rol solo puede contener letras y espacios.", "Formato inválida", JOptionPane.WARNING_MESSAGE);
			txtNombreRol.requestFocus();
			return;
		}
		
		Clases.Rol nuevoRol = new Clases.Rol();
		nuevoRol.setNombre(nombre);
		
		DAO.RolDAO dao = new DAO.RolDAO();
		if (dao.registrarRol(nuevoRol)) {
			JOptionPane.showMessageDialog(this, "¡Rol registrado con éxito!");
			mostrarDatos();
			limpiarCampos();
		} else {
			JOptionPane.showMessageDialog(this, "Error al intentar guardar el rol.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void eliminarRol() {
		if (idRolSeleccionado == 0) {
			JOptionPane.showMessageDialog(this, "Seleccione primero un rol de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este rol?", "Confirmación", JOptionPane.YES_NO_OPTION);
		
		if (confirmar == JOptionPane.YES_OPTION) {
			DAO.RolDAO dao = new DAO.RolDAO();
			try {
				if (dao.eliminarRol(idRolSeleccionado)) {
					JOptionPane.showMessageDialog(this, "Rol eliminado correctamente.");
					mostrarDatos();
					limpiarCampos();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "No se puede eliminar: el rol está asignado a usuarios activos en el sistema.", "Restricción de Integridad", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void limpiarCampos() {
		txtNombreRol.setText("");
		idRolSeleccionado = 0;
		tableRoles.clearSelection();
	}
}