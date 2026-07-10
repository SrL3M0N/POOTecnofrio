package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Servicio;
import DAO.ServicioDAO;
import Utilidades.BusquedaEmpleados;
import Utilidades.BusquedaProveedores;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FormServicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblDescripcion;
	private JLabel lblPrecio;
	private JLabel lblEstado;
	private JTextField txtServicio;
	private JTextArea txtDescripcion;
	private JTextField txtPrecio;
	private JComboBox cboEstado;
	private JButton btnNewButton;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnNewButton_3;
	private JTable table;
	private JScrollPane scrollPane;
	private int idEmpleadoSeleccionado;
	private int idServicioSeleccionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormServicio frame = new FormServicio();
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
	public FormServicio() {
		setTitle("Servicios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1094, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 51, 709, 314);
		contentPane.add(scrollPane);
		
			table = new JTable();
			scrollPane.setViewportView(table); // Esto "envuelve" la tabla
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					do_table_mouseClicked(e);
				}
			});
			

			
			scrollPane.setViewportView(table);

			 // En el constructor (o donde configuras el modelo):
		    table.setModel(new javax.swing.table.DefaultTableModel(
		        new Object [][] {},
		        new String [] { "ID", "Nombre", "Descripción", "Precio", "Estado" }
		        )
		    	{
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            // Al retornar siempre 'false', ninguna celda se puede editar
		            return false; 
		        }
		    	});
		    /*
			// Opcional: Ocultar la columna ID si no quieres que el usuario la vea
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setWidth(0);
			*/
			// Ajusta esto después de cargar la tabla
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Importante para que el scroll funcione bien

			table.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
			table.getColumnModel().getColumn(2).setPreferredWidth(330); // Descripción
			table.getColumnModel().getColumn(3).setPreferredWidth(65);  // Precio
			table.getColumnModel().getColumn(4).setPreferredWidth(60);  // Activo
		
		{
			lblNewLabel = new JLabel("Servicio");
			lblNewLabel.setBounds(33, 54, 71, 14);
			contentPane.add(lblNewLabel);
		}
		{
			lblDescripcion = new JLabel("Descripcion");
			lblDescripcion.setBounds(21, 92, 71, 14);
			contentPane.add(lblDescripcion);
		}
		{
			lblPrecio = new JLabel("Precio");
			lblPrecio.setBounds(33, 155, 71, 14);
			contentPane.add(lblPrecio);
		}
		{
			lblEstado = new JLabel("Estado");
			lblEstado.setBounds(33, 203, 94, 14);
			contentPane.add(lblEstado);
		}
		{
			txtServicio = new JTextField();
			txtServicio.setBounds(99, 51, 194, 20);
			contentPane.add(txtServicio);
			txtServicio.setColumns(10);
		}
		{
			txtDescripcion = new JTextArea();
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setBounds(99, 87, 204, 45);
			contentPane.add(txtDescripcion);
		}
		{
			txtPrecio = new JTextField();
			txtPrecio.setColumns(10);
			txtPrecio.setBounds(99, 152, 194, 20);
			contentPane.add(txtPrecio);
		}

			cboEstado = new JComboBox();
			cboEstado.setBounds(99, 195, 194, 22);
			cboEstado.addItem("Activo");
			cboEstado.addItem("Inactivo");
			contentPane.add(cboEstado);
			
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(33, 236, 260, 23);
			contentPane.add(btnNewButton);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnModificar_actionPerformed(e);
				}
			});
			btnModificar.setBounds(33, 270, 260, 23);
			contentPane.add(btnModificar);
		}
		{
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnEliminar_actionPerformed(e);
				}
			});
			btnEliminar.setBounds(33, 304, 260, 23);
			contentPane.add(btnEliminar);
		}
		{
			btnNewButton_3 = new JButton("Limpiar");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_3_actionPerformed(e);
				}
			});
			btnNewButton_3.setBounds(33, 340, 260, 23);
			contentPane.add(btnNewButton_3);
		}
		{

		}
		setLocationRelativeTo(null);
		
		cargarTablaServicios(); //
	
	}
	private void cargarTablaServicios() {
	    // 1. Obtener el modelo de la tabla
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    
	    // 2. Limpiar la tabla antes de cargar los datos
	    model.setRowCount(0);
	    
	    // 3. Llamar al DAO para obtener la lista
	    ServicioDAO dao = new ServicioDAO();
	    List<Servicio> lista = dao.listarServicios();
	    
	    // 4. Recorrer la lista y añadir a la tabla
	    // En el método cargarTablaServicios:
	    for (Servicio s : lista) {
	        model.addRow(new Object[]{
	        		s.getIdItem(),
	                s.getNombre(),
	                s.getDescripcion(),
	                s.getPrecioVenta(),
	                s.isActivo() ? "Activo" : "Inactivo" // <--- Columna 7
	        });
	    }
	}
	//METODOS 
	private void cargarDatosDesdeTabla() {
	    int fila = table.getSelectedRow();
	    if (fila != -1) {
	        // Campos de texto
	        txtServicio.setText(table.getValueAt(fila, 1).toString());
	        txtDescripcion.setText(table.getValueAt(fila, 2).toString());
	        txtPrecio.setText(table.getValueAt(fila, 3).toString());
	        
	        // Carga el ComboBox de Estado (Columna 7)
	        String estado = table.getValueAt(fila, 4).toString();
	     // RECORREMOS el ComboBox para encontrar la coincidencia
	        for (int i = 0; i < cboEstado.getItemCount(); i++) {
	            if (cboEstado.getItemAt(i).toString().equalsIgnoreCase(estado)) {
	                cboEstado.setSelectedIndex(i);
	                break; // Encontrado, salimos del bucle
	            }
	        }
	        
	        // Captura el ID
	        this.idServicioSeleccionado = Integer.parseInt(table.getValueAt(fila, 0).toString());
	    }
	}
	
	//GUARDAR
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		   ServicioDAO dao = new ServicioDAO();
		if (dao.existeServicio(txtServicio.getText().trim())) {

		    JOptionPane.showMessageDialog(this,
		            "Ya existe un servicio con ese nombre.");

		    txtServicio.requestFocus();
		    txtServicio.selectAll();
		    return;
		}
		
		
		
		if (!txtServicio.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
		    JOptionPane.showMessageDialog(this, "El nombre del servicio solo puede contener letras y espacios.");
		    txtServicio.requestFocus();
		    return;
		}
		
		
		// 1. Validaciones básicas
	    if (txtServicio.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, complete los campos obligatorios.");
	        return;
	    }
	    
	    String precio = txtPrecio.getText().trim();

	    if (precio.isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Ingrese el precio.",
	                "Validación",
	                JOptionPane.WARNING_MESSAGE);
	        txtPrecio.requestFocus();
	        return;
	    }
	    
	 // Solo números enteros o decimales
	    if (!precio.matches("\\d+(\\.\\d{1,2})?")) {
	        JOptionPane.showMessageDialog(this,
	                "El precio solo puede contener números.",
	                "Validación",
	                JOptionPane.WARNING_MESSAGE);
	        txtPrecio.requestFocus();
	        txtPrecio.selectAll();
	        return;
	    }
	 // 1. Obtener valores
	    String nombre = txtServicio.getText().trim();
	    String desc = txtDescripcion.getText().trim();
	    /*
	    // 2. Validación de duplicados
	    ServicioDAO dao = new ServicioDAO();
	    if (dao.existeServicio(nombre, desc)) {
	        JOptionPane.showMessageDialog(this, "El servicio '" + nombre + "' ya existe en el sistema.");
	        return; // Detiene la ejecución
	    }*/
	    
	    
	    if (!txtPrecio.getText().matches("\\d+(\\.\\d{1,2})?")) {
	        JOptionPane.showMessageDialog(this, "El precio debe contener solo números.");
	        txtPrecio.requestFocus();
	        return;
	    }

	    // 2. Crear objeto Servicio
	    Servicio s = new Servicio();
	    s.setNombre(txtServicio.getText());
	    s.setDescripcion(txtDescripcion.getText());
	    s.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
	    s.setActivo(cboEstado.getSelectedItem().toString().equalsIgnoreCase("Activo"));
	    
	    // Asignar el idEmpleado que capturaste con la lupa
	    //s.setIdEmpleado(this.idEmpleadoSeleccionado);

	    // 3. Llamar al DAO
	    ServicioDAO dao1 = new ServicioDAO();
	    if (dao.insertar(s)) {
	        JOptionPane.showMessageDialog(this, "Servicio registrado exitosamente.");
	        cargarTablaServicios(); // Refresca la tabla
	        limpiarCampos();        // Limpia el formulario
	    } else {
	        JOptionPane.showMessageDialog(this, "Error al insertar el servicio.");
	    }
		
	}
	protected void do_table_mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) { // Un solo clic para cargar
            cargarDatosDesdeTabla();
        }
	}
	
	// Agrega este método público para que el modal pueda "escribir" el ID y el nombre
	public void setEmpleadoSeleccionadoModal(int id, String nombre) {
	    this.idEmpleadoSeleccionado = id;
	}
	
	//MODIFICAR UN EL SERVICIO
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
		String precio = txtPrecio.getText().trim();
		 // Solo números enteros o decimales
	    if (!precio.matches("\\d+(\\.\\d{1,2})?")) {
	        JOptionPane.showMessageDialog(this,
	                "El precio solo puede contener números.",
	                "Validación",
	                JOptionPane.WARNING_MESSAGE);
	        txtPrecio.requestFocus();
	        txtPrecio.selectAll();
	        return;
	    }
		
		// Validar que se haya seleccionado un ítem
	    if (this.idServicioSeleccionado == 0) {
	        JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para modificar.");
	        return;
	    }
	    // Crear el objeto con los datos de los campos
	    Servicio s = new Servicio();
	    s.setIdItem(this.idServicioSeleccionado); // ID capturado al hacer clic
	    s.setNombre(txtServicio.getText());
	    s.setDescripcion(txtDescripcion.getText());
	    s.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
	    s.setActivo(cboEstado.getSelectedItem().toString().equalsIgnoreCase("Activo"));

	    // Llamar al DAO
	    ServicioDAO dao = new ServicioDAO();
	    if (dao.modificar(s)) {
	        JOptionPane.showMessageDialog(this, "Servicio actualizado correctamente.");
	        cargarTablaServicios(); // Refresca la tabla para ver el cambio
	        limpiarCampos();        // Limpia el formulario
	    } else {
	        JOptionPane.showMessageDialog(this, "Error al actualizar el servicio.");
	    }
	}
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		if (this.idServicioSeleccionado == 0) {
	        JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para eliminar.");
	        return;
	    }

	    int confirmacion = JOptionPane.showConfirmDialog(this, 
	            "¿Está seguro de que desea eliminar este servicio?", 
	            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

	    if (confirmacion == JOptionPane.YES_OPTION) {
	        ServicioDAO dao = new ServicioDAO();
	        if (dao.eliminar(this.idServicioSeleccionado)) {
	            JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
	            cargarTablaServicios(); // Refrescar tabla
	            limpiarCampos();        // Limpiar campos
	        } else {
	            JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.");
	        }
	    }
	}
	protected void do_btnNewButton_3_actionPerformed(ActionEvent e) {
		limpiarCampos();
	}
	private void limpiarCampos() {
	    // 1. Limpiar los campos de texto
	    txtServicio.setText("");
	    txtDescripcion.setText("");
	    txtPrecio.setText("");
	    
	    // 2. Resetear el ComboBox al primer elemento
	    cboEstado.setSelectedIndex(0);
	    
	    // 3. Resetear variables de control
	    this.idServicioSeleccionado = 0;
	    this.idEmpleadoSeleccionado = 0;
	    
	    // 4. Limpiar la selección de la tabla
	    table.clearSelection();
	    
	    // 5. Opcional: Volver a habilitar el botón "Guardar" si lo tenías bloqueado
	    btnNewButton.setEnabled(true);
	    btnModificar.setEnabled(true);
	    
	    // 6. Poner el foco en el primer campo
	    txtServicio.requestFocus();
	}
}
