package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Cliente;
import DAO.ClienteDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtNombres;
	private JLabel lblApellidos;
	private JLabel lblTelefono;
	private JLabel lblEmail;
	private JLabel lblDocumento;
	private JTextField txtApellidos;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDocumento;
	private JButton btnNewButton;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnLimpiar;
	private JTable table;
	private JScrollPane scrollPane;
	private int idCliente = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormCliente frame = new FormCliente();
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
	public FormCliente() {
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 828, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblNewLabel = new JLabel("Nombres");
			lblNewLabel.setBounds(30, 48, 62, 14);
			contentPane.add(lblNewLabel);
		}
		{
			txtNombres = new JTextField();
			txtNombres.setBounds(116, 45, 154, 20);
			contentPane.add(txtNombres);
			txtNombres.setColumns(10);
		}
		{
			lblApellidos = new JLabel("Apellidos");
			lblApellidos.setBounds(30, 87, 62, 14);
			contentPane.add(lblApellidos);
		}
		{
			lblTelefono = new JLabel("Telefono");
			lblTelefono.setBounds(30, 122, 62, 14);
			contentPane.add(lblTelefono);
		}
		{
			lblEmail = new JLabel("Email");
			lblEmail.setBounds(30, 161, 62, 14);
			contentPane.add(lblEmail);
		}
		{
			lblDocumento = new JLabel("Documento");
			lblDocumento.setBounds(30, 201, 76, 14);
			contentPane.add(lblDocumento);
		}
		{
			txtApellidos = new JTextField();
			txtApellidos.setColumns(10);
			txtApellidos.setBounds(116, 84, 154, 20);
			contentPane.add(txtApellidos);
		}
		{
			txtTelefono = new JTextField();
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(116, 119, 154, 20);
			contentPane.add(txtTelefono);
		}
		{
			txtEmail = new JTextField();
			txtEmail.setColumns(10);
			txtEmail.setBounds(116, 158, 154, 20);
			contentPane.add(txtEmail);
		}
		{
			txtDocumento = new JTextField();
			txtDocumento.setColumns(10);
			txtDocumento.setBounds(116, 198, 154, 20);
			contentPane.add(txtDocumento);
		}
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(30, 245, 240, 23);
			contentPane.add(btnNewButton);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnModificar_actionPerformed(e);
				}
			});
			btnModificar.setBounds(30, 279, 240, 23);
			contentPane.add(btnModificar);
		}
		{
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnEliminar_actionPerformed(e);
				}
			});
			btnEliminar.setBounds(30, 313, 240, 23);
			contentPane.add(btnEliminar);
		}
		{
			btnLimpiar = new JButton("Limpiar");
			btnLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnLimpiar_actionPerformed(e);
				}
			});
			btnLimpiar.setBounds(30, 347, 240, 23);
			contentPane.add(btnLimpiar);
		}
			{
				scrollPane = new JScrollPane();
				scrollPane.setBounds(308, 45, 466, 257);
				contentPane.add(scrollPane);
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						do_table_mouseClicked(e);
					}
				});
				scrollPane.setViewportView(table);
				table.setModel(new javax.swing.table.DefaultTableModel(
					    new Object [][] {},
					    new String [] {
					        "ID",
					        "Nombres",
					        "Apellidos",
					        "Teléfono",
					        "Email",
					        "Documento"
					    }
					));
				table.getColumnModel().getColumn(0).setMinWidth(0);
				table.getColumnModel().getColumn(0).setMaxWidth(0);
				table.getColumnModel().getColumn(0).setWidth(0);
				table.getColumnModel().getColumn(0).setPreferredWidth(0);
				
				table.getColumnModel().getColumn(4).setPreferredWidth(100);//
			}
		listarClientes();
		setLocationRelativeTo(null);
	}
	//METODOS 
	public void eliminarCliente() {

	    if (idCliente == 0) {
	        JOptionPane.showMessageDialog(null,
	                "Seleccione un cliente");
	        return;
	    }

	    int respuesta = JOptionPane.showConfirmDialog(
	            null,
	            "¿Desea eliminar este cliente?",
	            "Confirmar",
	            JOptionPane.YES_NO_OPTION);

	    if (respuesta == JOptionPane.YES_OPTION) {

	        ClienteDAO dao = new ClienteDAO();

	        if (dao.eliminarCliente(idCliente)) {

	            JOptionPane.showMessageDialog(null,
	                    "Cliente eliminado correctamente");

	            listarClientes();
	            limpiarCampos();
	            idCliente = 0;

	        } else {

	            JOptionPane.showMessageDialog(null,
	                    "No se pudo eliminar");
	        }
	    }
	}
	
	public void limpiarCampos() {
	    txtNombres.setText("");
	    txtApellidos.setText("");
	    txtTelefono.setText("");
	    txtEmail.setText("");
	    txtDocumento.setText("");

	    txtNombres.requestFocus();
	}
	
	public void listarClientes() {

	    ClienteDAO dao = new ClienteDAO();

	    DefaultTableModel modelo =
	            (DefaultTableModel) table.getModel();
	    
	    // Limpiar la tabla
	    modelo.setRowCount(0);
	    for (Cliente c : dao.listarClientes()) {

	        Object fila[] = {
	            c.getIdCliente(),
	            c.getNombres(),
	            c.getApellidos(),
	            c.getTelefono(),
	            c.getEmail(),
	            c.getDocumento()
	        };

	        modelo.addRow(fila);
	    }
	}
	public void modificarCliente() {
		

	    if (idCliente == 0) {
	        JOptionPane.showMessageDialog(null,
	                "Seleccione un cliente");
	        return;
	    }
	    
	    if (!validarCamposCliente()) {
	        return;
	    }


	    Cliente cliente = new Cliente();

	    cliente.setIdCliente(idCliente);
	    cliente.setNombres(txtNombres.getText());
	    cliente.setApellidos(txtApellidos.getText());
	    cliente.setTelefono(txtTelefono.getText());
	    cliente.setEmail(txtEmail.getText());
	    cliente.setDocumento(txtDocumento.getText());

	    ClienteDAO dao = new ClienteDAO();

	    if (dao.modificarCliente(cliente)) {

	        JOptionPane.showMessageDialog(null,
	                "Cliente modificado correctamente");

	        listarClientes();
	        limpiarCampos();
	        idCliente = 0;

	    } else {

	        JOptionPane.showMessageDialog(null,
	                "Error al modificar");
	    }
	}
	//GUARDAR
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
	    if (!validarCamposCliente()) {
	        return;
	    }

	    Cliente cliente = new Cliente();
	    cliente.setNombres(txtNombres.getText());
	    cliente.setApellidos(txtApellidos.getText());
	    cliente.setTelefono(txtTelefono.getText());
	    cliente.setEmail(txtEmail.getText());
	    cliente.setDocumento(txtDocumento.getText());

	    ClienteDAO dao = new ClienteDAO();

	    if (dao.registrarCliente(cliente)) {

	        JOptionPane.showMessageDialog(this,
	                "Cliente registrado correctamente");

	        listarClientes();
	        limpiarCampos();

	    } else {

	        JOptionPane.showMessageDialog(this,
	                "El cliente ya fue registrado");
	    }
	}
	protected void do_table_mouseClicked(MouseEvent e) {
	    int fila = table.getSelectedRow();

	    idCliente = Integer.parseInt(
	        table.getValueAt(fila, 0).toString()
	    );
	    
        txtNombres.setText(table.getValueAt(fila, 1).toString());
        txtApellidos.setText(table.getValueAt(fila, 2).toString());
        txtTelefono.setText(table.getValueAt(fila, 3).toString());
        txtEmail.setText(table.getValueAt(fila, 4).toString());
        txtDocumento.setText(table.getValueAt(fila, 5).toString());
	}
	protected void do_btnLimpiar_actionPerformed(ActionEvent e) {
		  limpiarCampos();
	}
	//MODIFICAR 
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
		modificarCliente();
	}
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
	     eliminarCliente();
	}
	
	private boolean validarCamposCliente() {

	    String nombres = txtNombres.getText().trim();
	    String apellidos = txtApellidos.getText().trim();
	    String telefono = txtTelefono.getText().trim();
	    String email = txtEmail.getText().trim();
	    String documento = txtDocumento.getText().trim();

	    if (nombres.isEmpty() || apellidos.isEmpty() || telefono.isEmpty()
	            || email.isEmpty() || documento.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
	        return false;
	    }

	    if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras");
	        txtNombres.requestFocus();
	        return false;
	    }

	    if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras");
	        txtApellidos.requestFocus();
	        return false;
	    }

	    if (!telefono.matches("\\d{9}")) {
	        JOptionPane.showMessageDialog(this, "El teléfono debe contener 9 números");
	        txtTelefono.requestFocus();
	        return false;
	    }

	    if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	        JOptionPane.showMessageDialog(this, "Ingrese un email válido");
	        txtEmail.requestFocus();
	        return false;
	    }

	    if (!documento.matches("\\d{8}")) {
	        JOptionPane.showMessageDialog(this, "El documento debe contener 8 números");
	        txtDocumento.requestFocus();
	        return false;
	    }

	    return true;
	}
}
