package Utilidades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Cliente;
import DAO.ClienteDAO;
import DAO.EmpleadoDAO;
import Gui.FormServicio;
import Gui.FormVenta;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BusquedaClientes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private FormVenta formPadre;
	private JTextField textField;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BusquedaClientes dialog = new BusquedaClientes(null, new JTextField());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BusquedaClientes(FormVenta formVenta, JTextField txtDestino) {
		this.formPadre = formVenta;
        this.textField = txtDestino;
        
		setBounds(100, 100, 712, 398);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombres", "Apellidos", "Documento"}, 0
            ) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setModel(model);
            
			scrollPane = new JScrollPane();
			scrollPane.setBounds(68, 52, 577, 259);
			contentPanel.add(scrollPane);
	
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						do_table_mouseClicked(e);
					}
				});
				scrollPane.setViewportView(table);

	

		
	    cargarClientes();
		setLocationRelativeTo(null);
	}
	
	private void cargarClientes() {

	    ClienteDAO dao = new ClienteDAO();

	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);

	    for (Cliente c : dao.listarClientes()) {
	        model.addRow(new Object[]{
	            c.getIdCliente(),
	            c.getNombres(),
	            c.getApellidos(),
	            c.getDocumento()
	        });
	    }
	}
	private void seleccionarCliente() {

	    int fila = table.getSelectedRow();

	    if (fila != -1) {

	        int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
	        String nombre = table.getValueAt(fila, 1).toString()
	                      + " " + table.getValueAt(fila, 2).toString();

	        formPadre.setClienteSeleccionadoModal(id, nombre);

	        dispose();

	    } else {
	        JOptionPane.showMessageDialog(this, "Selecciona una fila");
	    }
	}

	
	protected void do_table_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
        	seleccionarCliente();
        }
	}
}
