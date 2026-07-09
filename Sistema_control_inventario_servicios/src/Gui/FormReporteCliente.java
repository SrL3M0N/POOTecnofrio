package Gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Clases.ReporteCliente;
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

public class FormReporteCliente extends JFrame {
	
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
					FormReporteCliente frame = new FormReporteCliente();
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
	public FormReporteCliente() {
		setTitle("Reporte de Clientes");
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
        modelo.addColumn("Cliente");
        modelo.addColumn("Documento");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Email");
        modelo.addColumn("Ventas");
        modelo.addColumn("Total Comprado");
        modelo.addColumn("Última Compra");

        table.setModel(modelo);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        table.getColumnModel().getColumn(7).setPreferredWidth(130);
        
    }
	
	
    private void listarClientes() {
        if (fech_Inicio.getDate() == null || Fech_Fin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar fechas.");
            return;
        }
        
        // Obtener el modelo ya existente (que ya tiene las columnas)
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); // Vaciar solo los datos

        ReporteClienteDAO dao = new ReporteClienteDAO();
        List<ReporteCliente> lista = dao.listarClientes(fech_Inicio.getDate(), Fech_Fin.getDate());

        for (ReporteCliente r : lista) {
            modelo.addRow(new Object[] {
                    r.getIdCliente(),
                    r.getCliente(),
                    r.getDocumento(),
                    r.getTelefono(),
                    r.getEmail(),
                    r.getCantidadVentas(),
                    r.getTotalComprado(),
                    r.getUltimaCompra()
            });
        }
        // No hace falta reconfigurar anchos aquí si ya se hizo al inicio
    }
	
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		listarClientes();
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
