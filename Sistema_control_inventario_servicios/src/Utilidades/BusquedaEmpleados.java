package Utilidades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Clases.Empleado2;
import DAO.EmpleadoDAO2;
import Gui.FormProducto;
import Gui.FormServicio;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class BusquedaEmpleados extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private FormServicio formPadre;
	private JTextField textField;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BusquedaEmpleados dialog = new BusquedaEmpleados(null, new JTextField());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BusquedaEmpleados(FormServicio formServicio, JTextField txtDestino) {
		super(formServicio, "Buscar Servicio", true); // 'true' lo hace modal
		this.formPadre = formServicio;
        this.textField = txtDestino;
        
     // 1. PRIMERO inicializas el panel (si no lo has hecho)
        contentPanel.setLayout(null);
        

        
		setBounds(100, 100, 767, 439);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
			{
				scrollPane = new JScrollPane();
				scrollPane.setBounds(58, 62, 636, 269);
				contentPanel.add(scrollPane);
					
				// 2. CREAS la tabla (Aquí es donde se corrige tu error)
			     // 3. CREAS el modelo y se lo asignas
		        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nombres", "Tipo", "Disponibilidad"}, 0) {
		            @Override
		            public boolean isCellEditable(int row, int column) { return false; }
		        };
				table = new JTable();
				scrollPane.setViewportView(table);
				table.setModel(model); // AHORA 'table' ya no es null
				
							table.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									do_table_mouseClicked(e);
								}
							});
			}
			
		     // Asegúrate de que esto se ejecute después de cargar los datos o setear el modelo
		     // Asegúrate de que esto se ejecute después de cargar los datos o setear el modelo
		        table.getColumnModel().getColumn(0).setMinWidth(0);
		        table.getColumnModel().getColumn(0).setMaxWidth(0);
		        table.getColumnModel().getColumn(0).setPreferredWidth(0);
			
		cargarTablaEmpleados();
		setLocationRelativeTo(null);
	}
	
	private void cargarTablaEmpleados() {
	    EmpleadoDAO2 dao = new EmpleadoDAO2();
	    List<Empleado2> lista = dao.listarEmpleadosConDetalle(); // Usamos tu método funcional
	    
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // Limpiar datos previos
	    
	    for (Empleado2 e : lista) {
	        model.addRow(new Object[]{
	            e.getIdEmpleado(),   // Columna 0 (ID)
	            e.getNombres(),      // Columna 1
	            e.getNombreTipo(),   // Columna 2
	            e.getNombreDisp()    // Columna 3
	        });
	    }
	}
	
	private void seleccionarEmpleado() {
	    int fila = table.getSelectedRow(); // Obtiene la fila donde hiciste clic
	    
	    if (fila != -1) { // Verifica que realmente se haya seleccionado una fila
	        // Obtenemos los datos de la fila (columna 0 = ID, 1 = Nombre)
	        int id = (int) table.getValueAt(fila, 0);
	        String nombre = table.getValueAt(fila, 1).toString();
	        
	        // Enviamos al formulario padre
	        formPadre.setEmpleadoSeleccionadoModal(id, nombre);
	        
	        // Cerramos el buscador
	        dispose();
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila.");
	    }
	}
	
	protected void do_table_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
        	seleccionarEmpleado();
        }
	}
}
