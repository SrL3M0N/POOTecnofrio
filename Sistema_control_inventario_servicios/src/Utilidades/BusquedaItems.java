package Utilidades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Item;
import DAO.ItemDAO;
import Gui.FormVenta;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BusquedaItems extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField textField;
	private FormVenta formPadre;
	private JComboBox cboTipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BusquedaItems dialog = new BusquedaItems(null, new JTextField());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BusquedaItems(FormVenta formVenta, JTextField txtDestino) {
		this.formPadre = formVenta;
        this.textField = txtDestino;
		setBounds(100, 100, 513, 410);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			cboTipo = new JComboBox();
			cboTipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_comboBox_actionPerformed(e);
				}
			});
			cboTipo.setModel(new DefaultComboBoxModel(new String[] {"PRODUCTOS", "SERVICIOS", "TODOS"}));
			cboTipo.setBounds(165, 41, 182, 22);
			contentPanel.add(cboTipo);
		}
		{
			JLabel lblNewLabel = new JLabel("Item");
			lblNewLabel.setBounds(109, 45, 46, 14);
			contentPanel.add(lblNewLabel);
		}

	        DefaultTableModel model = new DefaultTableModel(new Object[]{           
	        		"ID",               
	                "NOMBRE",
	                "STOCK",
	                "PRECIO",
	                "TIPO"}, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) { return false; }
	        };
			table = new JTable(model);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					do_table_mouseClicked(e);
				}
			});
			table.setBounds(31, 96, 415, 232);
			contentPanel.add(table);
			// IMPORTANTE: JScrollPane
			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(31, 96, 415, 232);
			contentPanel.add(scroll);
		
		setLocationRelativeTo(null);
		 cargarTablaTodos();
		 /*
		 table.getColumnModel().getColumn(2).setMinWidth(0);
		 table.getColumnModel().getColumn(2).setMaxWidth(0);
		 table.getColumnModel().getColumn(2).setWidth(0);
		 */
	}
	
	protected void do_comboBox_actionPerformed(ActionEvent e) {
	    String opcion = cboTipo.getSelectedItem().toString();

	    if(opcion.equals("PRODUCTOS")){
	        cargarTablaProductos();
	    }else if(opcion.equals("SERVICIOS")){
	        cargarTablaServicios();
	    }else{
	        cargarTablaTodos();
	    }
	}
	private void cargarTablaProductos(){

	    DefaultTableModel modelo =
	            (DefaultTableModel) table.getModel();

	    modelo.setRowCount(0);

	    ItemDAO dao = new ItemDAO();

	    for(Item item : dao.listarProductos()){

	        int stockVisible = item.getStock();

	        if(formPadre != null){

	            int reservado =
	                formPadre.obtenerCantidadReservada(
	                    item.getIdItem());

	            stockVisible =
	                item.getStock() - reservado;

	            if(stockVisible < 0){
	                stockVisible = 0;
	            }
	        }

	        modelo.addRow(new Object[]{
	            item.getIdItem(),
	            item.getNombre(),
	            stockVisible,
	            item.getPrecio(),
	            item.getTipo()
	        });
	    }
	}
	
	
	
	private void cargarTablaServicios(){

	    DefaultTableModel modelo =
	            (DefaultTableModel) table.getModel();

	    modelo.setRowCount(0);

	    ItemDAO dao = new ItemDAO();

	    for(Item item : dao.listarServicios()){
	        modelo.addRow(new Object[]{
	            item.getIdItem(),
	            item.getNombre(),
	            "N/A", // Visualmente N/A
	            item.getPrecio(),
	            item.getTipo()
	        });
	    }
	}
	
	private void cargarTablaTodos(){

	    DefaultTableModel modelo =
	            (DefaultTableModel) table.getModel();

	    modelo.setRowCount(0);

	    ItemDAO dao = new ItemDAO();

	    for(Item item : dao.listarTodos()){

	        Object stockMostrar = "N/A";

	        if(item.getTipo().equals("PRODUCTO")){

	            int stockVisible = item.getStock();

	            if(formPadre != null){

	                int reservado =
	                    formPadre.obtenerCantidadReservada(
	                        item.getIdItem());

	                stockVisible =
	                    item.getStock() - reservado;

	                if(stockVisible < 0){
	                    stockVisible = 0;
	                }
	            }

	            stockMostrar = stockVisible;
	        }

	        modelo.addRow(new Object[]{
	            item.getIdItem(),
	            item.getNombre(),
	            stockMostrar,
	            item.getPrecio(),
	            item.getTipo()
	        });
	    }
	}
	
	protected void do_table_mouseClicked(MouseEvent e) {
		int fila = table.getSelectedRow();
	    if (fila >= 0) {
	        int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
	        String nombre = table.getValueAt(fila, 1).toString();
	        double precioventa = Double.parseDouble(table.getValueAt(fila, 3).toString());
	        String tipo = table.getValueAt(fila, 4).toString();
	        
	        // --- AQUÍ ESTÁ EL CAMBIO ---
	        Object valorStock = table.getValueAt(fila, 2);
	        int stock;
	        
	        // Si el valor es una cadena vacía o "N/A", definimos el stock como 0
	        if (valorStock == null || valorStock.toString().isEmpty() || valorStock.toString().equals("N/A")) {
	            stock = 0;
	        } else {
	            stock = Integer.parseInt(valorStock.toString());
	        }

	        formPadre.setItemSeleccionadoModal(id, nombre, stock, precioventa, tipo);
	        dispose();
	    }
	}
}
