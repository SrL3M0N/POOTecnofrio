package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Utilidades.BusquedaClientes;
import Utilidades.BusquedaItems;
import Utilidades.BusquedaProveedores;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Clases.DetalleVenta;
import Clases.PdfVenta;
import Clases.Sesion;
import Clases.Venta;
import DAO.ClienteDAO;
import DAO.VentaDAO;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormVenta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBuscarProveedor;
	private JLabel lblFecha;
	private JTextField txtFecha;
	private JPanel panel;
	private JLabel lblCliente;
	private JTextField txtCliente;
	private JPanel panel_2;
	private JTextField txtItem;
	private JLabel lblProducto;
	private JLabel lblPrecio;
	private JTextField txtPrecio;
	private JLabel lblCantidad;
	private JTextField txtCantidad;
	private JTable table;
	private JButton btnAgregarItem;
	private JTextField txtIGV;
	private JLabel lblC;
	private JButton btnGuardar;
	private JTextField txtUsuario;
	private JLabel lblCdigoVenta;
	private JTextField txtTotal;
	private JLabel lblTotal;
	private JButton btnBuscarItem;
	private JLabel lblSubtotal;
	private JTextField txtSubtotal;
	private JLabel lblTipoDeComprobante;
	private JTextField txtNumeroComprobante;
	private JLabel lblNumeroDeComprobante;
	private JComboBox cboTipoComprobante;
	private int idClienteSeleccionado;
	private JLabel lblStock;
	private JTextField txtStock;
	private JLabel lblImporte;
	private JTextField txtImporte;
	private int idItem;
	private DefaultTableModel modelo;

	private List<DetalleVenta> listaDetalles = new ArrayList<>();
	
	private VentaDAO ventaDAO = new VentaDAO();
	
	private String tipoItem;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormVenta frame = new FormVenta();
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
	public FormVenta() {
		
	
		//ArrayList<DetalleVenta> listaDetalles = new ArrayList<>();
		//DefaultTableModel modelo;
		
		setTitle("Registrar Venta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 876, 641);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			panel = new JPanel();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Informaci\u00F3n Venta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setBounds(37, 22, 775, 148);
			contentPane.add(panel);
			panel.setLayout(null);
			{
				txtFecha = new JTextField();
				txtFecha.setEditable(false);
				txtFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
				txtFecha.setBounds(552, 50, 155, 20);
				panel.add(txtFecha);
				txtFecha.setColumns(10);
			}
			{
				lblFecha = new JLabel("Fecha");
				lblFecha.setBounds(552, 25, 46, 14);
				panel.add(lblFecha);
			}
			{
				txtUsuario = new JTextField();
				txtUsuario.setEditable(false);
				txtUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
				txtUsuario.setColumns(10);
				txtUsuario.setBounds(23, 50, 161, 20);
				panel.add(txtUsuario);
			}
			{
				lblCdigoVenta = new JLabel("Vendedor");
				lblCdigoVenta.setBounds(23, 25, 99, 14);
				panel.add(lblCdigoVenta);
			}
			{
				lblCliente = new JLabel("Cliente");
				lblCliente.setBounds(285, 25, 46, 14);
				panel.add(lblCliente);
			}
			{
				txtCliente = new JTextField();
				txtCliente.setEditable(false);
				txtCliente.setBounds(285, 50, 155, 20);
				panel.add(txtCliente);
				txtCliente.setColumns(10);
			}
			{
				btnBuscarProveedor = new JButton("");
				btnBuscarProveedor.setBounds(450, 50, 26, 20);
				panel.add(btnBuscarProveedor);
				btnBuscarProveedor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						do_btnBuscarProveedor_actionPerformed(e);
					}
				});
				btnBuscarProveedor.setIcon(new ImageIcon(FormVenta.class.getResource("/Images/lupa.png")));
			}
		}
		{
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Detalle Venta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_2.setBounds(37, 181, 775, 110);
			contentPane.add(panel_2);
			{
				txtItem = new JTextField();
				txtItem.setEditable(false);
				txtItem.setColumns(10);
				txtItem.setBounds(20, 61, 191, 20);
				panel_2.add(txtItem);
			}
			{
				lblProducto = new JLabel("Producto / Servicio");
				lblProducto.setBounds(22, 36, 153, 14);
				panel_2.add(lblProducto);
			}
			{
				lblPrecio = new JLabel("Precio");
				lblPrecio.setBounds(478, 36, 101, 14);
				panel_2.add(lblPrecio);
			}
			{
				txtPrecio = new JTextField();
				txtPrecio.setEditable(false);
				txtPrecio.setColumns(10);
				txtPrecio.setBounds(480, 61, 86, 20);
				panel_2.add(txtPrecio);
			}
			{
				lblCantidad = new JLabel("Cantidad");
				lblCantidad.setBounds(367, 36, 101, 14);
				panel_2.add(lblCantidad);
			}
			{
				txtCantidad = new JTextField();
				txtCantidad.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						do_txtCantidad_keyReleased(e);
					}
				});
				txtCantidad.setColumns(10);
				txtCantidad.setBounds(368, 61, 86, 20);
				panel_2.add(txtCantidad);
			}
			{
				btnBuscarItem = new JButton("");
				btnBuscarItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						do_btnBuscarItem_actionPerformed(e);
					}
				});
				btnBuscarItem.setIcon(new ImageIcon(FormVenta.class.getResource("/Images/lupa.png")));
				btnBuscarItem.setBounds(221, 61, 26, 20);
				panel_2.add(btnBuscarItem);
			}
			{
				btnAgregarItem = new JButton("");
				btnAgregarItem.setIcon(new ImageIcon(FormVenta.class.getResource("/Images/icon-icons.png")));
				btnAgregarItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						do_btnNewButton_actionPerformed(e);
					}
				});
				btnAgregarItem.setBounds(689, 37, 63, 44);
				panel_2.add(btnAgregarItem);
			}
			{
				lblStock = new JLabel("Stock");
				lblStock.setBounds(255, 36, 101, 14);
				panel_2.add(lblStock);
			}
			{
				txtStock = new JTextField();
				txtStock.setEditable(false);
				txtStock.setColumns(10);
				txtStock.setBounds(257, 61, 86, 20);
				panel_2.add(txtStock);
			}
			{
				lblImporte = new JLabel("Importe");
				lblImporte.setBounds(589, 36, 101, 14);
				panel_2.add(lblImporte);
			}
			{
				txtImporte = new JTextField();
				txtImporte.setEditable(false);
				txtImporte.setColumns(10);
				txtImporte.setBounds(584, 61, 86, 20);
				panel_2.add(txtImporte);
			}
		}
		{
		    modelo = new DefaultTableModel();
		    modelo.addColumn("ID");
			modelo.addColumn("Producto / Servicio");
			modelo.addColumn("Cantidad");
			modelo.addColumn("Precio");
			modelo.addColumn("Importe");

			table = new JTable(modelo);
			
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setWidth(0);

			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(37, 306, 639, 260);
			contentPane.add(scroll);
			
		}
		{
			txtIGV = new JTextField();
			txtIGV.setEditable(false);
			txtIGV.setColumns(10);
			txtIGV.setBounds(714, 383, 98, 20);
			contentPane.add(txtIGV);
		}
		{
			lblC = new JLabel("IGV (18%)");
			lblC.setBounds(711, 358, 101, 14);
			contentPane.add(lblC);
		}
		{
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_1_actionPerformed(e);
				}
			});
			btnGuardar.setBounds(714, 474, 98, 23);
			contentPane.add(btnGuardar);
		}
		{
			txtTotal = new JTextField();
			txtTotal.setEditable(false);
			txtTotal.setColumns(10);
			txtTotal.setBounds(714, 433, 98, 20);
			contentPane.add(txtTotal);
		}
		{
			lblTotal = new JLabel("TOTAL");
			lblTotal.setBounds(711, 408, 101, 14);
			contentPane.add(lblTotal);
		}
		{
			lblSubtotal = new JLabel("SUBTOTAL");
			lblSubtotal.setBounds(712, 302, 101, 14);
			contentPane.add(lblSubtotal);
		}
		{
			txtSubtotal = new JTextField();
			txtSubtotal.setEditable(false);
			txtSubtotal.setColumns(10);
			txtSubtotal.setBounds(712, 327, 98, 20);
			contentPane.add(txtSubtotal);
		}
		setLocationRelativeTo(null);
		//capturar fecha
		txtFecha.setText(
			    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			);
		
		  txtUsuario.setText(Sesion.nombreEmpleado);
		  {
		  	lblTipoDeComprobante = new JLabel("Tipo de comprobante");
		  	lblTipoDeComprobante.setBounds(285, 81, 113, 14);
		  	panel.add(lblTipoDeComprobante);
		  }
		  {
		  	txtNumeroComprobante = new JTextField();
		  	txtNumeroComprobante.setFont(new Font("Tahoma", Font.BOLD, 11));
		  	txtNumeroComprobante.setEditable(false);
		  	txtNumeroComprobante.setColumns(10);
		  	txtNumeroComprobante.setBounds(23, 106, 161, 20);
		  	panel.add(txtNumeroComprobante);
		  }
		  {
		  	lblNumeroDeComprobante = new JLabel("Número de comprobante");
		  	lblNumeroDeComprobante.setBounds(23, 81, 155, 14);
		  	panel.add(lblNumeroDeComprobante);
		  }
		  {
		  	cboTipoComprobante = new JComboBox();
		  	cboTipoComprobante.addActionListener(new ActionListener() {
		  		public void actionPerformed(ActionEvent e) {
		  			do_cboTipoComprobante_actionPerformed(e);
		  		}
		  	});
		  	cboTipoComprobante.setModel(new DefaultComboBoxModel(new String[] {"BOLETA", "FACTURA"}));
		  	cboTipoComprobante.setBounds(285, 105, 155, 22);
		  	panel.add(cboTipoComprobante);
		  }	
		  	  
	}
	/*
	//Item modal
	public void setItemSeleccionadoModal(int id, String nombre, int stock, double precioventa, String tipo) {
	    this.idItem = id;
	    
	    this.txtItem.setText(nombre);
	    this.txtStock.setText(String.valueOf(stock));
	    this.txtPrecio.setText(String.valueOf(precioventa));
	    
	    this.tipoItem = tipo;
	}
	*/
	public void setItemSeleccionadoModal(int id,
            String nombre,
            int stock,
            double precioventa,
            String tipo) {

			this.idItem = id;
			
			this.txtItem.setText(nombre);
			this.txtStock.setText(String.valueOf(stock));
			this.txtPrecio.setText(String.valueOf(precioventa));
			
			this.tipoItem = tipo;
			
		    // VALIDAR SI ES PRODUCTO O SERVICIO
		    if (tipo.equalsIgnoreCase("SERVICIO")) {

		        txtCantidad.setText("1");
		        txtCantidad.setEditable(false); // Bloquea el campo
		        calcularImporte();              // Calcula Precio x 1

		    } else { // PRODUCTO

		        txtCantidad.setText("");
		        txtCantidad.setEditable(true); // Habilita nuevamente
		        txtImporte.setText("");

		    }
}
	
	
	//SE TRAE EL ID Y EL NOMBRE DEL MODAL
	public void setClienteSeleccionadoModal(int id, String nombre) {
	    this.idClienteSeleccionado = id;
	    this.txtCliente.setText(nombre);
	}
	
	//BUSCAR CLIENTE
	protected void do_btnBuscarProveedor_actionPerformed(ActionEvent e) {
	    BusquedaClientes modal = new BusquedaClientes(this, txtCliente);
	    modal.setVisible(true);
	}
	//GUARDAR VENTA
	protected void do_btnNewButton_1_actionPerformed(ActionEvent e) {
		
		
	    try {

	        if (listaDetalles.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Agrega productos");
	            return;
	        }

	        if (idClienteSeleccionado == 0) {
	            JOptionPane.showMessageDialog(this, "Seleccione cliente");
	            return;
	        }
	        
	        Venta v = new Venta();
	     // Ahora txtSubtotal.getText() tendrá "150.00" y parseDouble funcionará sin caídas
		    v.setSubtotal(Double.parseDouble(txtSubtotal.getText()));
		    v.setIgv(Double.parseDouble(txtIGV.getText()));
		    v.setTotal(Double.parseDouble(txtTotal.getText()));
	        v.setIdCliente(idClienteSeleccionado);
	        v.setIdUsuario(Sesion.idUsuario);
	        v.setNumeroComprobante(txtNumeroComprobante.getText());
	        v.setTipoComprobante(cboTipoComprobante.getSelectedItem().toString());
	        ventaDAO.registrarVenta(v, listaDetalles);  
	        JOptionPane.showMessageDialog(this, "Venta registrada correctamente");
	       
	        /*
	        //PARA ABRI LA ORDEN DE SERVICIO
	        List<DetalleVenta> servicios = obtenerServicios(listaDetalles);

	        if (!servicios.isEmpty()) {

	            FormOrdenServicio frm = new FrmOrdenServicio  (
	                v.getIdVenta(),
	                servicios
	            );

	            frm.setVisible(true);
	        }
	       */
	        
	        // 3. Generar PDF ANTES de limpiar
	        PdfVenta pdf = new PdfVenta();

	        pdf.generarPDF(
	                txtCliente.getText(),
	                txtUsuario.getText(),
	                txtFecha.getText(),
	                txtNumeroComprobante.getText(),
	                modelo,
	                txtSubtotal.getText(),
	                txtIGV.getText(),
	                txtTotal.getText()
	        );
	        
	        limpiarTodo();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al guardar venta");
	    }
	}
	private void limpiarTodo() {

	    modelo.setRowCount(0);
	    listaDetalles.clear();

	    txtSubtotal.setText("");
	    txtIGV.setText("");
	    txtTotal.setText("");

	    txtCliente.setText("");
	    txtNumeroComprobante.setText("");

	    idClienteSeleccionado = 0;
	}
	

	//Agregar Item AL DETALLE
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		System.out.println("Tipo Item = " + tipoItem);
		int cantidad = Integer.parseInt(txtCantidad.getText());

		if (cantidad <= 0) {
		    JOptionPane.showMessageDialog(this, "Cantidad inválida");
		    return;
		}

		if (tipoItem.equalsIgnoreCase("PRODUCTO")) {

		    int stock = Integer.parseInt(txtStock.getText());

		    if (cantidad > stock) {

		        JOptionPane.showMessageDialog(this,
		            "Stock insuficiente\nStock disponible: " + stock);

		        return;
		    }
		}
		System.out.println("Tipo Item = " + tipoItem);
        
		boolean encontrado = false;

		int cantidadNueva = Integer.parseInt(txtCantidad.getText());
		double precio = Double.parseDouble(txtPrecio.getText());

		for (int i = 0; i < modelo.getRowCount(); i++) {

		    int idExistente =
		        Integer.parseInt(modelo.getValueAt(i, 0).toString());

		    if (idExistente == idItem) {

		        int cantidadActual =
		            Integer.parseInt(modelo.getValueAt(i, 2).toString());

		        int nuevaCantidad = cantidadActual + cantidadNueva;

		        double nuevoImporte = nuevaCantidad * precio;

		        modelo.setValueAt(nuevaCantidad, i, 2);
		        modelo.setValueAt(nuevoImporte, i, 4);

		        // 🔥 ACTUALIZAR LISTA DETALLE TAMBIÉN
		        for (DetalleVenta dv : listaDetalles) {
		            if (dv.getIdItem() == idItem) {
		                dv.setCantidad(nuevaCantidad);
		                dv.setPrecioUnitario(precio);
		                break;
		            }
		        }

		        encontrado = true;
		        break;
		    }
		}

		if (!encontrado) {

		    double importe = cantidadNueva * precio;

		    modelo.addRow(new Object[] {
		        idItem,
		        txtItem.getText(),
		        cantidadNueva,
		        precio,
		        importe
		    });

		    // 🔥 AGREGAR A LISTA DETALLE
		    DetalleVenta d = new DetalleVenta();
		    d.setIdItem(idItem);
		    d.setCantidad(cantidadNueva);
		    d.setPrecioUnitario(precio);
		    listaDetalles.add(d);
		}
	    limpiarCampos();
	    calcularTotales(); // 🔥 IMPORTANTE
	}
	
	private void limpiarCampos() {
	    txtItem.setText("");
	    txtStock.setText("");
	    txtCantidad.setText("");
	    txtPrecio.setText("");
	    txtImporte.setText("");
	}
	protected void do_cboTipoComprobante_actionPerformed(ActionEvent e) {
		 VentaDAO dao = new VentaDAO();
		
	    String tipo = cboTipoComprobante.getSelectedItem().toString();

	    String numero = dao.generarNumeroComprobante(tipo);

	    txtNumeroComprobante.setText(numero);
	}
	protected void do_btnBuscarItem_actionPerformed(ActionEvent e) {
		
	    BusquedaItems modal = new BusquedaItems(this, txtItem);
	    modal.setVisible(true);
	}
	
	//CALCULAR EL IMPORTE 
	private void calcularImporte() {
	    try {
	        if (txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
	            txtImporte.setText("0.00");
	            return;
	        }

	        int cantidad = Integer.parseInt(txtCantidad.getText());
	        double precio = Double.parseDouble(txtPrecio.getText());

	        double importe = cantidad * precio;

	        // 🔥 CORRECCIÓN: Forzamos el uso de Locale.US para que use punto (.) en vez de coma (,)
	        txtImporte.setText(String.format(java.util.Locale.US, "%.2f", importe));

	    } catch (NumberFormatException e) {
	        txtImporte.setText("0.00");
	    }
	}
	//EVENTO PARA QUE LO CALCULE EL IMPORTE AUTOMATICAMENTE
	protected void do_txtCantidad_keyReleased(KeyEvent e) {
		 calcularImporte();
	}
	//CALCULAR TOTALES
	private void calcularTotales() {

	    double subtotal = 0;

	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        int cantidad = Integer.parseInt(modelo.getValueAt(i, 2).toString());
	        double precio = Double.parseDouble(modelo.getValueAt(i, 3).toString());

	        subtotal += cantidad * precio;
	    }

	    double igv = subtotal * 0.18;
	    double total = subtotal + igv;

	    // 🔥 CORRECCIÓN: Forzamos Locale.US en todos los totales de la interfaz gráfica
	    txtSubtotal.setText(String.format(java.util.Locale.US, "%.2f", subtotal));
	    txtIGV.setText(String.format(java.util.Locale.US, "%.2f", igv));
	    txtTotal.setText(String.format(java.util.Locale.US, "%.2f", total));
	}
	
	public int obtenerCantidadReservada(int idProducto) {

	    int cantidad = 0;

	    for (int i = 0; i < modelo.getRowCount(); i++) {

	        int idTabla = Integer.parseInt(
	                modelo.getValueAt(i, 0).toString());

	        if (idTabla == idProducto) {

	            cantidad += Integer.parseInt(
	                    modelo.getValueAt(i, 2).toString());
	        }
	    }

	    return cantidad;
	}
}
