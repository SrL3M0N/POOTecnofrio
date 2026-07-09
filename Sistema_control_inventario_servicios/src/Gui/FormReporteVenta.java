package Gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Clases.ReporteVenta;
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

public class FormReporteVenta extends JFrame {
	
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
					FormReporteVenta frame = new FormReporteVenta();
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
	public FormReporteVenta() {
		setTitle("Reporte Ventas");
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
        modelo.addColumn("Fecha");
        modelo.addColumn("Comprobante");
        modelo.addColumn("Número");
        modelo.addColumn("Cliente");
        modelo.addColumn("Item");
        modelo.addColumn("Tipo");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Importe");
        modelo.addColumn("Total");
        modelo.addColumn("Usuario");

        table.setModel(modelo);

     // IMPORTANTE: actualizar modelo antes de tocar columnas
        table.createDefaultColumnsFromModel();

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(120);
            table.getColumnModel().getColumn(4).setPreferredWidth(160);
            table.getColumnModel().getColumn(5).setPreferredWidth(180);
            table.getColumnModel().getColumn(6).setPreferredWidth(90);
            table.getColumnModel().getColumn(7).setPreferredWidth(80);
            table.getColumnModel().getColumn(8).setPreferredWidth(80);
            table.getColumnModel().getColumn(9).setPreferredWidth(80);
            table.getColumnModel().getColumn(10).setPreferredWidth(80);
            table.getColumnModel().getColumn(11).setPreferredWidth(90);
        }
    }
	
	
    private void listarVentas() {
        if (fech_Inicio.getDate() == null || Fech_Fin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar fechas.");
            return;
        }
        
        // Obtener el modelo ya existente (que ya tiene las columnas)
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); // Vaciar solo los datos

        ReporteVentaDAO dao = new ReporteVentaDAO();
        List<ReporteVenta> lista = dao.listarVentas(fech_Inicio.getDate(), Fech_Fin.getDate());

        for (ReporteVenta r : lista) {
            modelo.addRow(new Object[] {
                r.getIdVenta(), r.getFecha(), r.getTipoComprobante(),
                r.getNumeroComprobante(), r.getCliente(), r.getItem(),
                r.getTipo(), r.getCantidad(), r.getPrecioUnitario(),
                r.getImporte(), r.getTotal(), r.getUsuario()
            });
        }
        // No hace falta reconfigurar anchos aquí si ya se hizo al inicio
    }
	
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		listarVentas();
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
