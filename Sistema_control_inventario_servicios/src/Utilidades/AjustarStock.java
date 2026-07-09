package Utilidades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Clases.Producto;
import DAO.ProductoDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AjustarStock extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JLabel lblStockActual;
	private JTextField txtStockActual;
	private JLabel lblCantidad;
	private JTextField txtNuevoStock;
	private JButton btnNewButton;
	private JComboBox cboProducto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AjustarStock dialog = new AjustarStock();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AjustarStock() {
		setTitle("Ajustar Stock");
		setBounds(100, 100, 390, 283);
		getContentPane().setLayout(null);
		{
			lblNewLabel = new JLabel("Producto");
			lblNewLabel.setBounds(28, 52, 78, 14);
			getContentPane().add(lblNewLabel);
		}
		{
			lblStockActual = new JLabel("Stock Actual");
			lblStockActual.setBounds(28, 93, 78, 14);
			getContentPane().add(lblStockActual);
		}
		{
			txtStockActual = new JTextField();
			txtStockActual.setEditable(false);
			txtStockActual.setColumns(10);
			txtStockActual.setBounds(123, 90, 109, 20);
			getContentPane().add(txtStockActual);
		}
		{
			lblCantidad = new JLabel("Nuevo Stock");
			lblCantidad.setBounds(28, 136, 84, 14);
			getContentPane().add(lblCantidad);
		}
		{
			txtNuevoStock = new JTextField();
			txtNuevoStock.setColumns(10);
			txtNuevoStock.setBounds(123, 133, 109, 20);
			getContentPane().add(txtNuevoStock);
		}
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(28, 179, 306, 23);
			getContentPane().add(btnNewButton);
		}
		{
			cboProducto = new JComboBox();
			cboProducto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_cboProducto_actionPerformed(e);
				}
			});
			cboProducto.setBounds(123, 48, 211, 22);
			getContentPane().add(cboProducto);
		}
		setLocationRelativeTo(null);
		cargarProductos();
	}
	
	private void cargarProductos() {

	    cboProducto.removeAllItems();
	    ProductoDAO dao = new ProductoDAO();

	    for (Producto p : dao.listarProductoStock()) {
	        cboProducto.addItem(p);
	    }
	}
	
	protected void do_cboProducto_actionPerformed(ActionEvent e) {
	    Producto p = (Producto) cboProducto.getSelectedItem();

	    if (p != null) {
	        txtStockActual.setText(String.valueOf(p.getStock()));
	    }
	}
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		
	    if (!txtStockActual.getText().trim().matches("\\d+")) {
	        JOptionPane.showMessageDialog(
	            this,
	            "El stock inicial solo puede contener números.",
	            "Validación",
	            JOptionPane.WARNING_MESSAGE
	        );
	        txtStockActual.requestFocus();
	        return;
	    }
	    
		
		  Producto producto = (Producto) cboProducto.getSelectedItem();

		    if (producto == null) {
		        JOptionPane.showMessageDialog(this,
		                "Seleccione un producto");
		        return;
		    }

		    String texto = txtNuevoStock.getText().trim();

		    if (texto.isEmpty()) {
		        JOptionPane.showMessageDialog(this,
		                "Ingrese el nuevo stock");
		        txtNuevoStock.requestFocus();
		        return;
		    }

		    int nuevoStock;

		    try {

		        nuevoStock = Integer.parseInt(texto);

		        if (nuevoStock < 0) {

		            JOptionPane.showMessageDialog(this,
		                    "El stock no puede ser negativo");

		            return;
		        }

		    } catch (NumberFormatException ex) {

		        JOptionPane.showMessageDialog(this,
		                "Ingrese un número válido");

		        txtNuevoStock.requestFocus();

		        return;
		    }

		    ProductoDAO dao = new ProductoDAO();

		    int estado = dao.actualizarStock(
		            producto.getIdItem(),
		            nuevoStock);

		    if (estado > 0) {

		        JOptionPane.showMessageDialog(this,
		                "Stock actualizado correctamente");

		        txtStockActual.setText(
		                String.valueOf(nuevoStock));

		        txtNuevoStock.setText("");

		    } else {

		        JOptionPane.showMessageDialog(this,
		                "No se pudo actualizar el stock");
		    }
	}
}
