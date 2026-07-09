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

import Clases.Item;
import Clases.Proveedor;
import DAO.ItemDAO;
import DAO.ProveedorDAO;
import Gui.FormProducto;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BusquedaProveedores extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private FormProducto formPadre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BusquedaProveedores dialog = new BusquedaProveedores(null, new JTextField());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BusquedaProveedores(FormProducto formProducto, JTextField txtDestino) {
		super(formProducto, "Buscar Proveedor", true); // 'true' lo hace modal
		this.formPadre = formProducto;
        this.textField = txtDestino;
        
    
        
		setTitle("Buscar Proveedor");
		setBounds(100, 100, 646, 389);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//1 
		// Define el modelo y la tabla una sola vez
		DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Razón Social", "RUC/DNI"}, 0)
		{
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // Esto bloquea la edición de todas las celdas
		    }
		};
		//2
		table = new JTable(model);
		//3
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				do_table_mouseClicked(e);
			}
		});

		// Configura el scrollPane usando la tabla que ya tiene el modelo
		scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 67, 536, 225);
		contentPanel.add(scrollPane);
		
		scrollPane.setViewportView(table); // Aquí le pasas la tabla que SÍ tiene las columnas
		setLocationRelativeTo(null);
		cargarTablaProveedores(); // Ahora sí funcionará
		
		//4
		// Ocultar la columna ID (que es la columna 0)
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);

	}
	//METODO
	private void cargarTablaProveedores() {
	    // 1. Instanciamos el DAO
	    ProveedorDAO dao = new ProveedorDAO();
	    
	    // 2. Traemos la lista de proveedores desde la BD
	    List<Proveedor> lista = dao.listarProveedorProducto();
	    
	    // 3. Obtenemos el modelo de la tabla
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // Limpiamos la tabla antes de cargar
	    
	    // 4. Llenamos la tabla fila por fila
	    for (Proveedor p : lista) {
	        model.addRow(new Object[]{
	            p.getId_proveedor(), 
	            p.getRazon_social(),
	            p.getNum_documento()
	
	        });
	    }
	}
	private void seleccionarProveedor() {
	    int fila = table.getSelectedRow();
	    if (fila != -1) {
	    	// 1. Obtenemos el ID y el Nombre
	        int id = Integer.parseInt(table.getValueAt(fila, 0).toString());
	        String nombre = table.getValueAt(fila, 1).toString();
	        
	     // 2. ENVIAMOS AMBOS al formulario padre
	        // Esto actualizará el campo de texto Y la variable ID global
	        formPadre.setProveedorSeleccionadoModal(id, nombre);
	                
	        dispose(); // Cerramos el modal
	    } else {
	        JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la lista.");
	    }
	}
	protected void do_table_mouseClicked(MouseEvent e) {
	        if (e.getClickCount() == 2) {
	            seleccionarProveedor();
	    }
	}
}
