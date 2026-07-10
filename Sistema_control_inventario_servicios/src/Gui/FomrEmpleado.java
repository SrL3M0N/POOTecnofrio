package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import DAO.EmpleadoDAO;
import Clases.Empleado;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
public class FomrEmpleado extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtIDEMPLE;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField txtNombre;
	private JTextField txtDocumento;
	private JLabel lblNewLabel_4;
	private JTextField txtTele;
	private JButton btnNewButton;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnActualizar;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel modelo = new DefaultTableModel();
	private EmpleadoDAO dao = new EmpleadoDAO();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FomrEmpleado frame = new FomrEmpleado();
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
	public FomrEmpleado() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblNewLabel = new JLabel(" GESTIÓN DE EMPLEADOS");
			lblNewLabel.setBounds(140, 10, 146, 13);
			contentPane.add(lblNewLabel);
		}
		{
			txtIDEMPLE = new JTextField();
			txtIDEMPLE.setBounds(122, 45, 96, 18);
			contentPane.add(txtIDEMPLE);
			txtIDEMPLE.setColumns(10);
		}
		{
			lblNewLabel_1 = new JLabel("ID Empleado");
			lblNewLabel_1.setBounds(10, 48, 102, 12);
			contentPane.add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("Nombres");
			lblNewLabel_2.setBounds(10, 70, 61, 12);
			contentPane.add(lblNewLabel_2);
		}
		{
			lblNewLabel_3 = new JLabel("Documento");
			lblNewLabel_3.setBounds(10, 92, 84, 12);
			contentPane.add(lblNewLabel_3);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(122, 67, 96, 18);
			contentPane.add(txtNombre);
		}
		{
			txtDocumento = new JTextField();
			txtDocumento.setColumns(10);
			txtDocumento.setBounds(122, 89, 96, 18);
			contentPane.add(txtDocumento);
		}
		{
			lblNewLabel_4 = new JLabel("Teléfono");
			lblNewLabel_4.setBounds(10, 114, 84, 12);
			contentPane.add(lblNewLabel_4);
		}
		{
			txtTele = new JTextField();
			txtTele.setColumns(10);
			txtTele.setBounds(122, 111, 96, 18);
			contentPane.add(txtTele);
		}
		{
			btnNewButton = new JButton("Limpiar campos");
			btnNewButton.addActionListener(this);
			btnNewButton.setBounds(10, 149, 117, 20);
			contentPane.add(btnNewButton);
		}
		{
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(this);
			btnGuardar.setBounds(154, 149, 84, 20);
			contentPane.add(btnGuardar);
		}
		{
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(this);
			btnEliminar.setBounds(248, 149, 84, 20);
			contentPane.add(btnEliminar);
		}
		{
			btnActualizar = new JButton("Actualizar");
			btnActualizar.addActionListener(this);
			btnActualizar.setBounds(342, 149, 84, 20);
			contentPane.add(btnActualizar);
		}
		{
			scrollPane = new JScrollPane();
			scrollPane.addMouseListener(this);
			scrollPane.setBounds(10, 180, 416, 160);
			contentPane.add(scrollPane);
			{
				table = new JTable();
				table.addMouseListener(this);
				scrollPane.setViewportView(table);
			}
		}
		modelo = new DefaultTableModel();

		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Documento");
		modelo.addColumn("Telefono");

		table.setModel(modelo);
		listarEmpleados();
	}
	private void listarEmpleados(){
	    modelo.setRowCount(0);
	    for(Empleado e : dao.listar()){
	        modelo.addRow(new Object[]{
	                e.getIdEmpleado(),
	                e.getNombres(),
	                e.getDocumento(),
	                e.getTelefono()
	        });
	    }
	}
	private void limpiar(){
	    txtIDEMPLE.setText("");
	    txtNombre.setText("");
	    txtDocumento.setText("");
	    txtTele.setText("");
	    txtIDEMPLE.requestFocus();
	}
	private boolean validarCampos() {
	    String nombre = txtNombre.getText().trim();
	    String documento = txtDocumento.getText().trim();
	    String telefono = txtTele.getText().trim();
	    // Validar nombre
	    if(nombre.isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Ingrese el nombre del empleado");
	        txtNombre.requestFocus();
	        return false;
	    }
	    if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        JOptionPane.showMessageDialog(this,
	                "El nombre solo debe contener letras");
	        txtNombre.requestFocus();
	        return false;
	    }
	    if(nombre.length() < 3) {
	        JOptionPane.showMessageDialog(this,
	                "El nombre debe tener mínimo 3 caracteres");
	        txtNombre.requestFocus();
	        return false;
	    }
	    // Validar documento

	    if(documento.isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Ingrese el documento");
	        txtDocumento.requestFocus();
	        return false;
	    }
	    if(!documento.matches("\\d+")) {
	        JOptionPane.showMessageDialog(this,
	                "El documento solo debe contener números");
	        txtDocumento.requestFocus();
	        return false;
	    }
	    if(documento.length()!=8) {
	        JOptionPane.showMessageDialog(this,
	                "El documento debe tener 8 dígitos");
	        txtDocumento.requestFocus();
	        return false;
	    }
	    // Validar teléfono
	    if(telefono.isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Ingrese el teléfono");
	        txtTele.requestFocus();
	        return false;
	    }
	    if(!telefono.matches("\\d+")) {
	        JOptionPane.showMessageDialog(this,
	                "El teléfono solo debe contener números");
	        txtTele.requestFocus();
	        return false;
	    }
	    if(telefono.length()!=9) {
	        JOptionPane.showMessageDialog(this,
	                "El teléfono debe tener 9 dígitos");
	        txtTele.requestFocus();
	        return false;
	    }
	    return true;
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnActualizar) {
			do_btnActualizar_actionPerformed(e);
		}
		if (e.getSource() == btnEliminar) {
			do_btnEliminar_actionPerformed(e);
		}
		if (e.getSource() == btnGuardar) {
			do_btnGuardar_actionPerformed(e);
		}
		if (e.getSource() == btnNewButton) {
			do_btnNewButton_actionPerformed(e);
		}
	}
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		limpiar();
	}
	protected void do_btnGuardar_actionPerformed(ActionEvent e) {
		
		if(!validarCampos()){
	        return;
	    }
		
		if (dao.existeDocumento(txtDocumento.getText().trim())) {
		    JOptionPane.showMessageDialog(this,
		            "El DNI ya se encuentra registrado.",
		            "Documento duplicado",
		            JOptionPane.WARNING_MESSAGE);

		    txtDocumento.requestFocus();
		    txtDocumento.selectAll();
		    return;
		}

	    Empleado emp = new Empleado();
	    emp.setNombres(txtNombre.getText());
	    emp.setDocumento(txtDocumento.getText());
	    emp.setTelefono(txtTele.getText());

	    if(dao.insertar(emp)){

	        JOptionPane.showMessageDialog(this,
	                "Empleado registrado");

	        listarEmpleados();
	        limpiar();

	    }else{

	        JOptionPane.showMessageDialog(this,
	                "Error al registrar");

	    }
	}
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		  if(txtIDEMPLE.getText().trim().isEmpty()) {

		        JOptionPane.showMessageDialog(this,
		                "Seleccione un empleado para eliminar",
		                "Advertencia",
		                JOptionPane.WARNING_MESSAGE);

		        return;
		    }


		    int respuesta = JOptionPane.showConfirmDialog(this,
		            "¿Desea eliminar este empleado?",
		            "Confirmar eliminación",
		            JOptionPane.YES_NO_OPTION);


		    if(respuesta != JOptionPane.YES_OPTION){
		        return;
		    }


		    int id = Integer.parseInt(txtIDEMPLE.getText());


		    if(dao.eliminar(id)){

		        JOptionPane.showMessageDialog(this,
		                "Empleado eliminado correctamente");


		        listarEmpleados();
		        limpiar();


		    }else{

		        JOptionPane.showMessageDialog(this,
		                "No se puede eliminar este empleado porque tiene usuarios registrados",
		                "Empleado en uso",
		                JOptionPane.WARNING_MESSAGE);
		    }
	}
	protected void do_btnActualizar_actionPerformed(ActionEvent e) {
		 if(txtIDEMPLE.getText().trim().isEmpty()){

		        JOptionPane.showMessageDialog(this,
		                "Seleccione un empleado de la tabla");

		        return;
		    }


		    if(!validarCampos()){
		        return;
		    }


		    Empleado emp = new Empleado();

		    emp.setIdEmpleado(Integer.parseInt(txtIDEMPLE.getText()));
		    emp.setNombres(txtNombre.getText());
		    emp.setDocumento(txtDocumento.getText());
		    emp.setTelefono(txtTele.getText());


		    if(dao.actualizar(emp)){

		        JOptionPane.showMessageDialog(this,
		                "Empleado actualizado");

		        listarEmpleados();
		        limpiar();

		    }else{

		        JOptionPane.showMessageDialog(this,
		                "No se pudo actualizar");

		    }
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			do_table_mouseClicked(e);
		}
		if (e.getSource() == scrollPane) {
			do_scrollPane_mouseClicked(e);
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	protected void do_scrollPane_mouseClicked(MouseEvent e) {
	}
	protected void do_table_mouseClicked(MouseEvent e) {
		int fila = table.getSelectedRow();
	    if (fila == -1) {
	        return;
	    }
	    txtIDEMPLE.setText(table.getValueAt(fila,0).toString());
	    txtNombre.setText(table.getValueAt(fila,1).toString());
	    txtDocumento.setText(table.getValueAt(fila,2).toString());
	    txtTele.setText(table.getValueAt(fila,3).toString());
	}
}
