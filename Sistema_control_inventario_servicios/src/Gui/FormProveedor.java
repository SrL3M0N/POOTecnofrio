package Gui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.sql.Date;
import java.util.List;
import javax.swing.JTable;

import DAO.ProveedorDAO;
import Clases.Proveedor;
import Clases.Conexion;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class FormProveedor extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtRazonSoci;
	private JTextField txtNumDocu;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnBuscar;
	private JComboBox<String> cmbEstado;
		

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormProveedor frame = new FormProveedor();
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
	public FormProveedor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 770, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTRO DE PROVEEDORES");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(185, 10, 207, 12);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(10, 54, 44, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("RAZÓN SOCIAL:");
		lblNewLabel_1_1.setBounds(10, 76, 95, 12);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("N° DE RUC:");
		lblNewLabel_2.setBounds(10, 98, 113, 12);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("TELEFONO:");
		lblNewLabel_2_1.setBounds(10, 120, 95, 12);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("EMAIL:");
		lblNewLabel_2_2.setBounds(10, 142, 95, 12);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("DIRECCIÓN:");
		lblNewLabel_2_2_1.setBounds(10, 164, 95, 12);
		contentPane.add(lblNewLabel_2_2_1);
		
		txtID = new JTextField();
		txtID.setBounds(149, 51, 194, 18);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtRazonSoci = new JTextField();
		txtRazonSoci.setColumns(10);
		txtRazonSoci.setBounds(149, 73, 194, 18);
		contentPane.add(txtRazonSoci);
		
		txtNumDocu = new JTextField();
		txtNumDocu.setColumns(10);
		txtNumDocu.setBounds(149, 95, 194, 18);
		contentPane.add(txtNumDocu);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(149, 117, 194, 18);
		contentPane.add(txtTelefono);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(149, 139, 194, 18);
		contentPane.add(txtEmail);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(149, 162, 194, 18);
		contentPane.add(txtDireccion);
		
		JLabel lblNewLabel_2_2_1_1 = new JLabel("ESTADO:");
		lblNewLabel_2_2_1_1.setBounds(10, 200, 95, 12);
		contentPane.add(lblNewLabel_2_2_1_1);
		
		cmbEstado = new JComboBox<>();
		cmbEstado.setBounds(115, 196, 96, 20);
		contentPane.add(cmbEstado);

		cmbEstado.addItem("Activo");
		cmbEstado.addItem("Inactivo");
		{
			btnNuevo = new JButton("Limpiar campos");
			btnNuevo.addActionListener(this);
			btnNuevo.setBounds(10, 226, 113, 20);
			contentPane.add(btnNuevo);
		}
		{
			btnGuardar = new JButton("GUARDAR");
			btnGuardar.setBounds(127, 226, 84, 20);
			contentPane.add(btnGuardar);
		}
		{
			btnEditar = new JButton("EDITAR");
			btnEditar.addActionListener(this);
			btnEditar.setBounds(221, 226, 84, 20);
			contentPane.add(btnEditar);
		}
		{
			btnEliminar = new JButton("ELIMINAR");
			btnEliminar.addActionListener(this);
			btnEliminar.setBounds(315, 226, 84, 20);
			contentPane.add(btnEliminar);
		}
		{
			btnBuscar = new JButton("BUSCAR");
			btnBuscar.addActionListener(this);
			btnBuscar.setBounds(409, 226, 84, 20);
			contentPane.add(btnBuscar);
		}
		{
			table = new JTable();
			table.addMouseListener(this);

			scrollPane = new JScrollPane(table);
			scrollPane.setBounds(20, 256, 714, 225);

			contentPane.add(scrollPane);
			btnGuardar.addActionListener(this);
		}
		
		// CORREGIDO: Bloqueo de edición manual directo en las celdas de la JTable
		modelo = new DefaultTableModel(null, new String[]{"ID", "Razón Social", "RUC", "Teléfono", "Email", "Dirección", "Estado", "Fecha Registro"}) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(modelo);
		
		listar();
		setLocationRelativeTo(null);
	}
	
	ProveedorDAO dao = new ProveedorDAO();
	DefaultTableModel modelo;
	private JScrollPane scrollPane;
	private JTable table;
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			do_btnBuscar_actionPerformed(e);
		}
		if (e.getSource() == btnEliminar) {
			do_btnEliminar_actionPerformed(e);
		}
		if (e.getSource() == btnEditar) {
			do_btnEditar_actionPerformed(e);
		}
		if (e.getSource() == btnNuevo) {
	        do_btnNuevo_actionPerformed(e);
	    }
	    if (e.getSource() == btnGuardar) {
	        guardar();
	    }
	}
	
	protected void do_btnNuevo_actionPerformed(ActionEvent e) {		
	    limpiar();
	}
	
	public void listar() {
	    modelo.setRowCount(0);
	    List<Proveedor> lista = dao.listarProveedor();

	    for (Proveedor p : lista) {
	        modelo.addRow(new Object[] {
	            p.getId_proveedor(),
	            p.getRazon_social(),
	            p.getNum_documento(),
	            p.getTelefono(),
	            p.getEmail(),
	            p.getDireccion(),
	            p.isEstado() ? "Activo" : "Inactivo",
	            p.getFecha_registro()
	        });
	    }
	}
	
	public void limpiar() {
	    txtID.setText("");
	    txtRazonSoci.setText("");
	    txtNumDocu.setText("");
	    txtTelefono.setText("");
	    txtEmail.setText("");
	    txtDireccion.setText("");
	    cmbEstado.setSelectedIndex(0);
	    table.clearSelection();
	}
	
	public void guardar() {
		// 1. Razón social obligatoria
	    if (txtRazonSoci.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "La razón social es obligatoria");
	        txtRazonSoci.requestFocus();
	        return;
	    }

	    // 2. RUC obligatorio
	    if (txtNumDocu.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Ingrese el RUC");
	        txtNumDocu.requestFocus();
	        return;
	    }

	    // 3. RUC debe tener 11 dígitos
	    if (!txtNumDocu.getText().trim().matches("\\d{11}")) {
	        JOptionPane.showMessageDialog(this, "El RUC debe tener 11 dígitos");
	        txtNumDocu.requestFocus();
	        return;
	    }

	    // 4. Teléfono obligatorio
	    if (txtTelefono.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Ingrese el teléfono");
	        txtTelefono.requestFocus();
	        return;
	    }
	    
	    // 🔥 CORRECCIÓN: Validar que el teléfono tenga exactamente 9 números al guardar
	    if (!txtTelefono.getText().trim().matches("\\d{9}")) {
	        JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 9 dígitos");
	        txtTelefono.requestFocus();
	        return;
	    }

	    // 5. Validar email (si se ingresó)
	    if (!txtEmail.getText().trim().isEmpty()) {
	        if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
	            JOptionPane.showMessageDialog(this, "Correo electrónico inválido");
	            txtEmail.requestFocus();
	            return;
	        }
	    }

	    // 6. Crear objeto
	    Proveedor p = new Proveedor();
	    p.setRazon_social(txtRazonSoci.getText().trim());
	    p.setNum_documento(txtNumDocu.getText().trim());
	    p.setTelefono(txtTelefono.getText().trim());
	    p.setEmail(txtEmail.getText().trim());
	    p.setDireccion(txtDireccion.getText().trim());
	    p.setEstado(cmbEstado.getSelectedItem().toString().equals("Activo"));
	    p.setFecha_registro(new java.sql.Date(System.currentTimeMillis()));

	    // 7. Guardar
	    dao.insertar(p);

	    listar();
	    limpiar();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			do_table_mouseClicked(e);
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	protected void do_table_mouseClicked(MouseEvent e) {
		seleccionarFila();
	}
	
	public void seleccionarFila() {
	    int fila = table.getSelectedRow();
	    if (fila == -1) {
	        return;
	    }
	    txtID.setText(table.getValueAt(fila, 0).toString());
	    txtRazonSoci.setText(table.getValueAt(fila, 1).toString());
	    txtNumDocu.setText(table.getValueAt(fila, 2).toString());
	    txtTelefono.setText(table.getValueAt(fila, 3).toString());
	    txtEmail.setText(table.getValueAt(fila, 4) != null ? table.getValueAt(fila, 4).toString() : "");
	    txtDireccion.setText(table.getValueAt(fila, 5) != null ? table.getValueAt(fila, 5).toString() : "");
	    cmbEstado.setSelectedItem(table.getValueAt(fila, 6).toString());
	}
	
	protected void do_btnEditar_actionPerformed(ActionEvent e) {
		editar();
	}
	
	public void editar() {
		if (txtID.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla para editar");
			return;
		}
		
		if (txtRazonSoci.getText().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(this, "La razón social es obligatoria");
		    txtRazonSoci.requestFocus();
		    return;
		}

		if (txtNumDocu.getText().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Ingrese el RUC");
		    txtNumDocu.requestFocus();
		    return;
		}

		if (!txtNumDocu.getText().trim().matches("\\d{11}")) {
		    JOptionPane.showMessageDialog(this, "El RUC debe tener 11 dígitos");
		    txtNumDocu.requestFocus();
		    return;
		}

		if (txtTelefono.getText().trim().isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Ingrese el teléfono");
		    txtTelefono.requestFocus();
		    return;
		}

		if (!txtTelefono.getText().trim().matches("\\d{9}")) {
		    JOptionPane.showMessageDialog(this, "El teléfono debe tener 9 dígitos");
		    txtTelefono.requestFocus();
		    return;
		}

		if (!txtEmail.getText().trim().isEmpty()) {
			if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			    JOptionPane.showMessageDialog(this, "Correo electrónico inválido");
			    txtEmail.requestFocus();
			    return;
			}
		}

		int op = JOptionPane.showConfirmDialog(
	            this,
	            "¿Desea actualizar este proveedor?",
	            "Confirmar",
	            JOptionPane.YES_NO_OPTION);

	    if (op != JOptionPane.YES_OPTION) {
	        return;
	    }

	    Proveedor p = new Proveedor();
	    p.setId_proveedor(Integer.parseInt(txtID.getText().trim()));
	    p.setRazon_social(txtRazonSoci.getText().trim());
	    p.setNum_documento(txtNumDocu.getText().trim());
	    p.setTelefono(txtTelefono.getText().trim());
	    p.setEmail(txtEmail.getText().trim());
	    p.setDireccion(txtDireccion.getText().trim());
	    p.setEstado(cmbEstado.getSelectedItem().toString().equals("Activo"));

	    dao.editar(p);

	    JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");

	    listar();
	    limpiar();
	}
	
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		eliminar();
	}

	public void eliminar() {
	    if (txtID.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
	        return;
	    }

	    int op = JOptionPane.showConfirmDialog(
	                    this,
	                    "¿Eliminar proveedor?",
	                    "Confirmar",
	                    JOptionPane.YES_NO_OPTION);

	    if (op == JOptionPane.YES_OPTION) {
	        dao.eliminar(Integer.parseInt(txtID.getText().trim()));
	        listar();
	        limpiar();
	    }
	}
	
	public void buscar() {
	    if (txtID.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Ingrese el ID del proveedor");
	        return;
	    }
	    try {
	        int id = Integer.parseInt(txtID.getText().trim());
	        Proveedor p = dao.buscar(id);
	        if (p != null) {
	            txtRazonSoci.setText(p.getRazon_social());
	            txtNumDocu.setText(p.getNum_documento());
	            txtTelefono.setText(p.getTelefono());
	            txtEmail.setText(p.getEmail());
	            txtDireccion.setText(p.getDireccion());
	            cmbEstado.setSelectedItem(p.isEstado() ? "Activo" : "Inactivo");
	        } else {
	            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
	        }
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this, e.getMessage());
	    }
	}
	
	protected void do_btnBuscar_actionPerformed(ActionEvent e) {
		buscar();
	}
}