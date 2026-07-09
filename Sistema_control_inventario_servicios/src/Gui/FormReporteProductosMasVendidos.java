package Gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Clases.ReporteCliente;
import Clases.ReporteProductoMasVendidos;
import Clases.ReporteVenta;
import DAO.ReporteClienteDAO;
import DAO.ReporteVentaDAO;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

import DAO.ReporteProductoMasVendidoDAO; 

public class FormReporteProductosMasVendidos extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTable table;
	private JButton btnNewButton;
	private JDateChooser Fech_Fin;
	private JButton btnNewButton_1;
	private JDateChooser fech_Inicio;
	private JScrollPane scrollPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormReporteProductosMasVendidos frame = new FormReporteProductosMasVendidos();
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
	public FormReporteProductosMasVendidos() {
		setTitle("Reporte de Productos más vendidos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		setBounds(100, 100, 777, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			lblNewLabel_1 = new JLabel("Desde");
			lblNewLabel_1.setBounds(46, 48, 46, 14);
			contentPane.add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("Hasta");
			lblNewLabel_2.setBounds(407, 48, 46, 14);
			contentPane.add(lblNewLabel_2);
		}
		{
			scrollPane = new JScrollPane();
			scrollPane.setBounds(46, 112, 672, 308);
			contentPane.add(scrollPane);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		{
			btnNewButton = new JButton("Buscar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(624, 44, 89, 23);
			contentPane.add(btnNewButton);
		}
		
		fech_Inicio = new JDateChooser();
		fech_Inicio.setBounds(102,42,136,20);	
		getContentPane().add(fech_Inicio);
		{
			Fech_Fin = new JDateChooser();
			Fech_Fin.setBounds(463, 44, 136, 20);
			contentPane.add(Fech_Fin);
		}
		{
			btnNewButton_1 = new JButton("Limpiar");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_1_actionPerformed(e);
				}
			});
			btnNewButton_1.setBounds(624, 78, 89, 23);
			contentPane.add(btnNewButton_1);
		}
		
		cargarCabecera();
	}
    private void cargarCabecera() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad Vendida");
        modelo.addColumn("Total Recaudado");

        table.setModel(modelo);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
    }
	
	
    private void listarProductos() {
        if (fech_Inicio.getDate() == null || Fech_Fin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar fechas.");
            return;
        }
        
        // Obtener el modelo ya existente (que ya tiene las columnas)
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); // Vaciar solo los datos

        ReporteProductoMasVendidoDAO dao = new ReporteProductoMasVendidoDAO();
        List<ReporteProductoMasVendidos> lista = dao.listarProductosMasVendidos(fech_Inicio.getDate(), Fech_Fin.getDate());

        for (ReporteProductoMasVendidos r : lista) {
            modelo.addRow(new Object[] {
                    r.getIdItem(),
                    r.getProducto(),
                    r.getCantidadVendida(),
                    r.getTotalRecaudado()
            });
        }
        // No hace falta reconfigurar anchos aquí si ya se hizo al inicio
    }
	
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		listarProductos();
	}
	protected void do_btnNewButton_1_actionPerformed(ActionEvent e) {
		limpiar();
	}
	private void limpiar() {

	    // Limpiar fechas
	    fech_Inicio.setDate(null);
	    Fech_Fin.setDate(null);

	    // Vaciar la tabla
	    DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	    modelo.setRowCount(0);

	}
}
