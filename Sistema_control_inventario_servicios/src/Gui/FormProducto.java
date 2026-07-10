package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Producto;
import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import Utilidades.AjustarStock;
import Utilidades.BusquedaProveedores;
import Utilidades.CboCategoria;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FormProducto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JTextField txtCosto;
	private JLabel lblCodigoBarras;
	private JTextField txtCodBarras;
	private JLabel lblCosto;
	private JTextField txtStock;
	private JComboBox cboCategoria;
	private JLabel lblCategoria;
	private JLabel lblProvedor;
	private JLabel lblNombre;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnBuscarProveedor;
	private JScrollPane scrollPane;
	private JTextField txtProveedor;
	private int idItemSeleccionado = 0; 
    private int idProveedorSeleccionado = 0;
    private int idCategoriaSeleccionada = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			FormProducto dialog = new FormProducto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public FormProducto() {
		

		
		
		cboCategoria = new JComboBox();
		setTitle("Productos");
		setBounds(100, 100, 1034, 533);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);		
		contentPanel.setLayout(null);	
		
		
/**************************************************************************/
		//IMPORTANTE PARA CARGAR LA TABLA 
		// 1. Crear el scrollPane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(370, 39, 595, 325);
		contentPanel.add(scrollPane);

		// 2. Crear la tabla
		table = new JTable();
		scrollPane.setViewportView(table);

		// 4. Configurar modelo y datos
		table.setModel(new javax.swing.table.DefaultTableModel(
		        new Object [][] {},
		        new String [] {
		        		"ID_Item",       // 0 - Oculta
		                "ID_Cat",        // 1 - Oculta
		                "ID_Prov",       // 2 - Oculta
		                "Cod. Barras",   // 3
		                "Producto",      // 4
		                "Descripción",   // 5
		                "Categoría",     // 6
		                "Proveedor",     // 7
		                "Costo",         // 8
		                "Precio Venta",  // 9
		                "Stock"          // 10	
		        }
		    ) 
		    {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return false;
		        }
		    });
		// 2. JUSTO AQUÍ: Desactivas el auto-resize para que el scroll funcione
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    //Ocultar columnas

	    //Ajustar el anchs de las columnas
	    //Item
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);
		//IdCat
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);
		//IDProv
		table.getColumnModel().getColumn(2).setMinWidth(0);
		table.getColumnModel().getColumn(2).setMaxWidth(0);
		table.getColumnModel().getColumn(2).setWidth(0);
		

	    table.getColumnModel().getColumn(3).setPreferredWidth(120); // CodigoBarras
	    table.getColumnModel().getColumn(4).setPreferredWidth(200); // Producto
	    table.getColumnModel().getColumn(5).setPreferredWidth(200); // Descripcion
	    table.getColumnModel().getColumn(6).setPreferredWidth(150); // Categoria
	    table.getColumnModel().getColumn(7).setPreferredWidth(120); // Proveedor
	    table.getColumnModel().getColumn(8).setPreferredWidth(70); // Costo
	    table.getColumnModel().getColumn(9).setPreferredWidth(75); // PrecioVenta
	    table.getColumnModel().getColumn(10).setPreferredWidth(65); // Stock
	    //EVENTO MOUSE CLICK A LA TABLA 
	    table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            do_table_mouseClicked(e);
	        }
	    });
	    /***CBO***/
	    
		// 5. Cargar datos
		cargarCategorias();
		cargarTabla();

		{
			lblNewLabel = new JLabel("Stock Inicial");
			lblNewLabel.setBounds(32, 308, 76, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			txtCosto = new JTextField();
			txtCosto.setBounds(118, 243, 211, 20);
			contentPanel.add(txtCosto);
			txtCosto.setColumns(10);
		}
		{
			lblCodigoBarras = new JLabel("Codigo Barras");
			lblCodigoBarras.setBounds(28, 46, 90, 14);
			contentPanel.add(lblCodigoBarras);
		}
		{
			txtCodBarras = new JTextField();
			txtCodBarras.setBounds(114, 43, 211, 20);
			txtCodBarras.setColumns(10);
			contentPanel.add(txtCodBarras);
		}
		{
			lblCosto = new JLabel("Costo");
			lblCosto.setBounds(32, 246, 46, 14);
			contentPanel.add(lblCosto);
		}
		{
			txtStock = new JTextField();
			txtStock.setBounds(118, 305, 211, 20);
			txtStock.setColumns(10);
			contentPanel.add(txtStock);
		}
		{
			cboCategoria.setBounds(113, 179, 211, 22);
			contentPanel.add(cboCategoria);
		}
		{
			lblCategoria = new JLabel("Categoria");
			lblCategoria.setBounds(28, 183, 76, 14);
			contentPanel.add(lblCategoria);
		}
		{
			lblProvedor = new JLabel("Provedor");
			lblProvedor.setBounds(32, 214, 76, 14);
			contentPanel.add(lblProvedor);
		}
		{
			lblNombre = new JLabel("Producto");
			lblNombre.setBounds(28, 83, 76, 14);
			contentPanel.add(lblNombre);
		}
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(32, 343, 297, 23);
			contentPanel.add(btnNewButton);
		}
		{
			btnModificar = new JButton("Modificar datos - No stock");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnModificar_actionPerformed(e);
				}
			});
			btnModificar.setBounds(32, 377, 297, 23);
			contentPanel.add(btnModificar);
		}
		{
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnEliminar_actionPerformed(e);
				}
			});
			btnEliminar.setBounds(32, 411, 297, 23);
			contentPanel.add(btnEliminar);
		}
		{
			btnBuscarProveedor = new JButton("");
			btnBuscarProveedor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnBuscar_1_actionPerformed(e);
				}
			});
			btnBuscarProveedor.setIcon(new ImageIcon(FormProducto.class.getResource("/Images/lupa.png")));
			btnBuscarProveedor.setBounds(333, 213, 26, 20);
			contentPanel.add(btnBuscarProveedor);
		}
		{
			txtProveedor = new JTextField();
			txtProveedor.setEditable(false);
			txtProveedor.setColumns(10);
			txtProveedor.setBounds(118, 214, 211, 20);
			contentPanel.add(txtProveedor);
		}
		{
			btnLimpiar = new JButton("Limpiar");
			btnLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnLimpiar_actionPerformed(e);
				}
			});
			btnLimpiar.setBounds(32, 445, 297, 23);
			contentPanel.add(btnLimpiar);
		}
		{
			txtProducto = new JTextField();
			txtProducto.setBounds(115, 80, 210, 20);
			contentPanel.add(txtProducto);
			txtProducto.setColumns(10);
		}
		{
			lblDescripcion = new JLabel("Descripcion");
			lblDescripcion.setBounds(28, 120, 76, 14);
			contentPanel.add(lblDescripcion);
		}
		{
			lblPrecioVenta = new JLabel("Precio Venta");
			lblPrecioVenta.setBounds(32, 277, 76, 14);
			contentPanel.add(lblPrecioVenta);
		}
		{
			txtPrecioVenta = new JTextField();
			txtPrecioVenta.setColumns(10);
			txtPrecioVenta.setBounds(118, 274, 210, 20);
			contentPanel.add(txtPrecioVenta);
		}
		{
			txtDescripcion = new JTextArea();
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setBounds(118, 117, 206, 49);
			contentPanel.add(txtDescripcion);
		}
		{
			btnAjustarStock = new JButton("Ajustar Stock");
			btnAjustarStock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnAjustarStock_actionPerformed(e);
				}
			});
			btnAjustarStock.setIcon(new ImageIcon(FormProducto.class.getResource("/Images/icon-icons (3).png")));
			btnAjustarStock.setBounds(380, 375, 139, 25);
			contentPanel.add(btnAjustarStock);
		}
		cargarTabla();
		generarCodigoBarrasAutomatico();
	}
	/*********************CBO****************************************************/
	// 1. Declaras el DAO en tu formulario
	private CategoriaDAO categoriaDAO = new CategoriaDAO();
	private JButton btnLimpiar;
	private JTextField txtProducto;
	private JLabel lblDescripcion;
	private JLabel lblPrecioVenta;
	private JTextField txtPrecioVenta;
	private JTextArea txtDescripcion;
	private JButton btnAjustarStock;
	// 2. El método que tú escribes en tu clase FormProductos
	public void cargarCategorias() {
	    cboCategoria.removeAllItems(); // Limpias por si acaso    
	    // Llamas al DAO que ya creaste para obtener los datos
	    List<CboCategoria> lista = categoriaDAO.listarCategorias();     
	    // Llenas el combo
	    for (CboCategoria c : lista) {
	        cboCategoria.addItem(c); 
	    }
	}
/**************************************************************************/
	//GUARDAR 
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		String nombreProducto = txtProducto.getText().trim();

		if (!nombreProducto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
		    JOptionPane.showMessageDialog(
		        this,
		        "El nombre del producto solo puede contener letras y espacios.",
		        "Validación",
		        JOptionPane.WARNING_MESSAGE
		    );
		    txtProducto.requestFocus();
		    return;
		}
	
		
		// 1. Validaciones básicas de campos vacíos
	    if(txtProducto.getText().isEmpty() || txtCosto.getText().isEmpty() || txtProveedor.getText().isEmpty() || txtStock.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Debe completar todos los campos, incluyendo el proveedor y stock.");
	        return;
	    }
	    // 2. FILTRO CRÍTICO: Validar que se haya seleccionado un proveedor real
	    // Si el ID es 0, significa que el usuario no usó el modal o no seleccionó nada
	    if (idProveedorSeleccionado <= 0) {
	        JOptionPane.showMessageDialog(this, "Error: Debe seleccionar un proveedor válido usando la lupa.", 
	                                      "Validación", JOptionPane.ERROR_MESSAGE);
	        return; // Detiene la ejecución aquí mismo
	    }
	    // 3. Validar Categoría
	    CboCategoria cate = (CboCategoria) cboCategoria.getSelectedItem();
	    if (cate == null || cate.getIdCategoria() <= 0) {
	        JOptionPane.showMessageDialog(this, "Debe seleccionar una categoría válida.");
	        return;
	    }   
	 // 4. Verificación de duplicados
	    ProductoDAO dao = new ProductoDAO();
	    // Si idItemSeleccionado es 0, es un registro nuevo. Si es > 0, estamos modificando.
	    if (dao.existeCodigoBarras(txtCodBarras.getText())) {
	        JOptionPane.showMessageDialog(this, "El código de barras ya existe en otro producto.");
	        return;
	    }
	    if (dao.existeNombreProducto(txtProducto.getText())) {
	        JOptionPane.showMessageDialog(this, "Error: Ya existe un producto con este nombre.");
	        return;
	    }
	    
	    //Validar costo y precio de venta
	    double costo;
	    double precioVenta;

	    try {
	        costo = Double.parseDouble(txtCosto.getText().trim());
	        precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Costo y precio de venta deben ser números válidos.");
	        return;
	    }

	    if (precioVenta <= costo) {
	        JOptionPane.showMessageDialog(this, "El precio de venta debe ser mayor que el costo.");
	        txtPrecioVenta.requestFocus();
	        return;
	    }
	    
		// Validar que el stock sea un número entero
	    if (!txtStock.getText().trim().matches("\\d+")) {
	        JOptionPane.showMessageDialog(
	            this,
	            "El stock inicial solo puede contener números enteros.",
	            "Validación",
	            JOptionPane.WARNING_MESSAGE
	        );
	        txtStock.requestFocus();
	        return;
	    }
	    
	    // 🔥 CORRECCIÓN: Validar que el stock sea estrictamente mayor que 0
	    int stockVal = Integer.parseInt(txtStock.getText().trim());
	    if (stockVal <= 0) {
	        JOptionPane.showMessageDialog(
	            this,
	            "El stock inicial debe ser un valor mayor que 0.",
	            "Validación",
	            JOptionPane.WARNING_MESSAGE
	        );
	        txtStock.requestFocus();
	        return;
	    }
	    
		// 1. Crear el objeto Producto
	    Producto p = new Producto();    
	    // 2. Extraer datos del formulario
	    p.setNombre(txtProducto.getText());
	    p.setDescripcion(txtDescripcion.getText());
	    p.setCodigoBarras(txtCodBarras.getText());
	    p.setCosto(Double.parseDouble(txtCosto.getText()));
	    p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText()));
	    p.setStock(stockVal);
	    
	    // 3. Extraer IDs (Lo más importante)
	    // Para la Categoría, extraemos el ID del objeto seleccionado en el JComboBox
	    CboCategoria cat = (CboCategoria) cboCategoria.getSelectedItem();
	    p.setIdCategoria(cat.getIdCategoria());
	    
	    // Para el Proveedor, asumo que tienes una variable global o campo que guarda el ID
	    // capturado al buscar el proveedor en tu ventana modal
	    p.setIdProveedor(idProveedorSeleccionado);
	    
	    // 4. Llamar al DAO para persistir
	    dao.guardarProducto(p);
	     
	    JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
		
	    // 5. Refrescar la tabla y limpiar
	    cargarTabla();
	    limpiarCampos();
	    generarCodigoBarrasAutomatico();
	}

	
	// Este método va DENTRO de FormProductos.java
	private void cargarTabla() {
	    ProductoDAO dao = new ProductoDAO(); // Creamos la instancia del DAO
	    List<Producto> lista = dao.obtenerListaProductos(); // Obtenemos los datos    
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // Limpiamos
	    
	    for (Producto p : lista) {
	        model.addRow(new Object[]{
	        		p.getIdItem(),          // 0
	                p.getIdCategoria(),     // 1
	                p.getIdProveedor(),     // 2
	                p.getCodigoBarras(),    // 3
	                p.getNombre(),          // 4
	                p.getDescripcion(),     // 5
	                p.getNombreCategoria(), // 6
	                p.getNombreProveedor(), // 7
	                p.getCosto(),           // 8
	                p.getPrecioVenta(),     // 9
	                p.getStock()            // 10
	        });
	    }
	}
	protected void do_table_mouseClicked(MouseEvent e) {
		int fila = table.getSelectedRow();
        if (fila != -1) {
        	
        	// 1. Captura de IDs (Variables Globales)
            idItemSeleccionado = (int) table.getValueAt(fila, 0);
            idCategoriaSeleccionada = (int) table.getValueAt(fila, 1);
            idProveedorSeleccionado = (int) table.getValueAt(fila, 2);
            
         	 // El stock no se puede editar
    	    txtStock.setEditable(false);
            
            // 2. Proyección de datos en el formulario
            txtCodBarras.setText(table.getValueAt(fila, 3).toString());
            txtProducto.setText(table.getValueAt(fila, 4).toString());
            txtDescripcion.setText(table.getValueAt(fila, 5).toString());
            
            // Si usas JComboBox para categoría, busca el item por texto
            String nombreCatTabla = table.getValueAt(fila, 6).toString();
	         // 2. Recorrer el JComboBox para buscar el objeto que coincida
	         for (int i = 0; i < cboCategoria.getItemCount(); i++) {
	             // Si tu objeto tiene un método .getNombre(), úsalo
	             // Si no, al convertirlo a String, debería darte el nombre que se ve en pantalla
	             if (cboCategoria.getItemAt(i).toString().equalsIgnoreCase(nombreCatTabla)) {
	                 cboCategoria.setSelectedIndex(i);
	                 break; // Encontramos la coincidencia, salimos del bucle
	             }
	         }
            
            txtProveedor.setText(table.getValueAt(fila, 7).toString());
            txtCosto.setText(table.getValueAt(fila, 8).toString());
            txtPrecioVenta.setText(table.getValueAt(fila, 9).toString());
            txtStock.setText(table.getValueAt(fila, 10).toString());
	    }
	}
	
	protected void do_btnLimpiar_actionPerformed(ActionEvent e) {
		limpiarCampos();
	}
	public void limpiarCampos() {
		
		txtStock.setEditable(true);
		// 1. Limpiar campos de texto
	    txtCodBarras.setText("");
	    txtProducto.setText("");
	    txtDescripcion.setText("");
	    txtCosto.setText("");
	    txtPrecioVenta.setText("");
	    txtStock.setText("");
	    txtProveedor.setText(""); // Limpia el nombre que se muestra
	    
	    // 2. Resetear variables de control (MUY IMPORTANTE)
	    idItemSeleccionado = 0;
	    idProveedorSeleccionado = 0;
	    idCategoriaSeleccionada = 0;
	    
	    // 3. Resetear componentes visuales
	    if (cboCategoria.getItemCount() > 0) {
	        cboCategoria.setSelectedIndex(0);
	    }
	    
	    // 4. Limpiar selección de tabla y foco
	    table.clearSelection();
	    txtCodBarras.requestFocus(); // Mueve el cursor al inicio para nueva entrada  
	    generarCodigoBarrasAutomatico();
	}
	//BUSCAR PROVEEDOR 
	protected void do_btnBuscar_1_actionPerformed(ActionEvent e) {
	    BusquedaProveedores modal = new BusquedaProveedores(this, txtProveedor);
	    modal.setVisible(true);
	}
	// Agrega este método público para que el modal pueda "escribir" el ID y el nombre
	public void setProveedorSeleccionadoModal(int id, String nombre) {
	    this.idProveedorSeleccionado = id;
	    this.txtProveedor.setText(nombre);
	}
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
		String nombreProducto = txtProducto.getText().trim();

		if (!nombreProducto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
		    JOptionPane.showMessageDialog(
		        this,
		        "El nombre del producto solo puede contener letras y espacios.",
		        "Validación",
		        JOptionPane.WARNING_MESSAGE
		    );
		    txtProducto.requestFocus();
		    return;
		}
		
		// 1. Validar si hay un ítem seleccionado (usando tu variable idItemSeleccionado)
	    if (idItemSeleccionado == 0) {
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto de la tabla para modificar.");
	        return;
	    }
	    
	    double costo;
	    double precioVenta;

	    try {
	        costo = Double.parseDouble(txtCosto.getText().trim());
	        precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Costo y precio de venta deben ser números válidos.");
	        return;
	    }

	    if (precioVenta <= costo) {
	        JOptionPane.showMessageDialog(
	            this,
	            "El precio de venta debe ser mayor que el costo.",
	            "Validación",
	            JOptionPane.WARNING_MESSAGE
	        );
	        txtPrecioVenta.requestFocus();
	        return;
	    }
	    
	    // 🔥 CORRECCIÓN: Validar que el stock modificado sea mayor que 0 (Especialmente preventivo si hereda datos)
	    int stockVal = Integer.parseInt(txtStock.getText().trim());
	    if (stockVal <= 0) {
	        JOptionPane.showMessageDialog(
	            this,
	            "El stock no puede ser igual o menor a 0.",
	            "Validación",
	            JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }


	    // 2. Crear objeto con los datos del formulario
	    Producto p = new Producto();
	    p.setIdItem(idItemSeleccionado); // IMPORTANTE: El ID del ítem que quieres actualizar
	    p.setNombre(txtProducto.getText());
	    p.setDescripcion(txtDescripcion.getText());
	    p.setCodigoBarras(txtCodBarras.getText());
	    p.setCosto(Double.parseDouble(txtCosto.getText()));
	    p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText()));
	    p.setStock(stockVal);
	    
	    // Categoría y Proveedor
	    CboCategoria cat = (CboCategoria) cboCategoria.getSelectedItem();
	    p.setIdCategoria(cat.getIdCategoria());
	    p.setIdProveedor(idProveedorSeleccionado); // Este valor ya está actualizado gracias al modal

	    // 3. Llamar al DAO
	    ProductoDAO dao = new ProductoDAO();
	    dao.modificarProducto(p); // Debes crear este método en tu ProductoDAO
	    
	    cargarTabla();
	    limpiarCampos();
	    idItemSeleccionado = 0; // Resetear
	    JOptionPane.showMessageDialog(this, "Producto modificado correctamente");
	}
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		if (idItemSeleccionado == 0) {
	        JOptionPane.showMessageDialog(this, "Selecciona un producto.");
	        return;
	    }

	    try {
	        ProductoDAO dao = new ProductoDAO();
	        dao.eliminarProducto(idItemSeleccionado);
	        
	        cargarTabla();
	        limpiarCampos();
	        JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
	        
	    } catch (Exception ex) {
	        // ESTO EVITARÁ QUE EL PROGRAMA SE CIERRE
	        // Aquí verás el error real en tu consola en lugar de que se cierre el programa
	        ex.printStackTrace(); 
	        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), 
	                                      "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	protected void do_btnAjustarStock_actionPerformed(ActionEvent e) {
	    AjustarStock modal = new AjustarStock();
	    modal.setModal(true);

	    modal.setVisible(true);
	    // Cuando el diálogo se cierre
	    cargarTabla(); // o el método que llena tu JTable
	}
	
	
	private void generarCodigoBarrasAutomatico() {
	    ProductoDAO dao = new ProductoDAO();
	    txtCodBarras.setText(dao.generarCodigoBarras());
	    txtCodBarras.setEditable(false);
	}
}