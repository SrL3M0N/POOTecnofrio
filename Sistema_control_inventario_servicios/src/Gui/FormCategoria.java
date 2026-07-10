package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Clases.Conexion;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;

public class FormCategoria extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JTextField txtNombre;
	private JLabel lblDescripcion;
	private JLabel lblEstado;
	private JComboBox<String> cboEstado;
	private JButton btnNewButton;
	private JButton btnEliminar;
	
	// Variables globales de la tabla
	private JTable tablaCategorias;
	private DefaultTableModel modeloTabla;
	private int idSeleccionado = -1; // Nos servirá para saber si estamos editando o eliminando
	private JButton btnModificar;
	private JScrollPane scrollPane_1;
	private JTable table;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JTextArea txtDescripcion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FormCategoria dialog = new FormCategoria();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FormCategoria() {
	
		// 2. Y adentro del constructor SOLO la inicializas (SIN volver a poner "JTable" adelante):
        tablaCategorias = new JTable(); 
        // (Aquí irá el código del JScrollPane que agregaste en el Paso 1)
        JScrollPane scrollPane = new JScrollPane(tablaCategorias);
        
        tablaCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                do_table_mouseClicked(e);
            }
        });
        scrollPane.setBounds(347, 42, 599, 245);
        getContentPane().add(scrollPane);

		
		setTitle("Categorias");
		setBounds(100, 100, 986, 374);
		setLocationRelativeTo(null);//Importante que vaya de debajo Bounds
		getContentPane().setLayout(null);
		{
			lblNewLabel = new JLabel("Nombre");
			lblNewLabel.setBounds(36, 49, 64, 14);
			getContentPane().add(lblNewLabel);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setBounds(123, 46, 214, 20);
			getContentPane().add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			lblDescripcion = new JLabel("Descripcion");
			lblDescripcion.setBounds(36, 80, 77, 14);
			getContentPane().add(lblDescripcion);
		}
		{
			lblEstado = new JLabel("Estado");
			lblEstado.setBounds(36, 151, 64, 14);
			getContentPane().add(lblEstado);
		}
		{

			cboEstado = new JComboBox<String>();
			cboEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"Activo", "Inactivo"}));
			cboEstado.setBounds(123, 147, 214, 22);
			getContentPane().add(cboEstado);
		}
		{
			btnNewButton = new JButton("Guardar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnNewButton_actionPerformed(e);
				}
			});
			btnNewButton.setBounds(36, 195, 301, 23);
			getContentPane().add(btnNewButton);
		}
		{
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnEliminar_actionPerformed(e);
				}
			});
			btnEliminar.setBounds(36, 263, 301, 23);
			getContentPane().add(btnEliminar);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					do_btnModificar_actionPerformed(e);
				}
			});
			btnModificar.setBounds(36, 229, 301, 23);
			getContentPane().add(btnModificar);
		}
		{
			txtDescripcion = new JTextArea();
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setBounds(123, 77, 214, 59);
			getContentPane().add(txtDescripcion);
		}
		// LLAMADA OBLIGATORIA: Para que la tabla no aparezca vacía al abrir
	    mostrarDatosEnTabla();
	}
	
	//GUARDAR
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		String nombre = txtNombre.getText().trim();
	    String descripcion = txtDescripcion.getText().trim();
	    int estadoBit = (cboEstado.getSelectedIndex() == 0) ? 1 : 0;
	    
	    // 1. Validación básica de vacío
	    if (nombre.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El nombre de la categoría es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
	        txtNombre.requestFocus();
	        return;
	    }
	    
	    // 🔥 CORRECCIÓN: Validación para que NO sea puro número ni símbolos especiales
	    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        JOptionPane.showMessageDialog(null, "El nombre de la categoría solo puede contener letras y espacios.", "Validación", JOptionPane.WARNING_MESSAGE);
	        txtNombre.requestFocus();
	        return;
	    }
	    
	    // 2. Verificación de duplicados
	    String sqlVerificar = "SELECT COUNT(*) FROM Categoria_Producto WHERE nombre = ?";
	    String sqlInsert = "INSERT INTO Categoria_Producto (nombre, descripcion, estado) VALUES (?, ?, ?)";
	    
	    try (Connection con = Conexion.getConnection()) {     
	        // --- Paso A: Verificar ---
	        try (PreparedStatement psVer = con.prepareStatement(sqlVerificar)) {
	            psVer.setString(1, nombre);
	            ResultSet rs = psVer.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                JOptionPane.showMessageDialog(null, "Error: Esta categoría ya existe.", "Duplicado", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	        }
	        // --- Paso B: Insertar (Solo si llega hasta aquí) ---
	        try (PreparedStatement psIns = con.prepareStatement(sqlInsert)) {
	            psIns.setString(1, nombre);
	            psIns.setString(2, descripcion);
	            psIns.setInt(3, estadoBit);
	            
	            int res = psIns.executeUpdate();
	            if (res > 0) {
	                JOptionPane.showMessageDialog(null, "¡Guardado con éxito!");
	                mostrarDatosEnTabla(); // Refrescar la tabla
	                // Limpiar campos
	                txtNombre.setText("");
	                txtDescripcion.setText("");
	                cboEstado.setSelectedIndex(0);
	            }
	        }
	            
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
	    }

	}
	//MOSTRAR DATOS EN LA TABLA 
	public void mostrarDatosEnTabla() {	
	    // 1. Cabeceras de la tabla visual
	    String[] titulos = {"ID", "Nombre", "Descripción", "Estado"};
	 // 2. CREACIÓN DEL MODELO BLOQUEADO: Sobreescribimos el método interno
	    DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Al poner 'false', NINGUNA celda se podrá editar en pantalla
	        }
	    };   
	    // CORREGIDO: Nombre exacto de la tabla y sus columnas según tu script
	    String sql = "SELECT id_categoria, nombre, descripcion, estado FROM Categoria_Producto";
	    
	    // Usamos tu clase de conexión 'Conexion' (o 'PedroConnection', la que de el "Conectado")
	    try (Connection con = Conexion.getConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        String[] fila = new String[4];
	        
	        while (rs.next()) {
	            // CORREGIDO: Jalamos las etiquetas exactas de SQL Server
	            fila[0] = rs.getString("id_categoria");
	            fila[1] = rs.getString("nombre");
	            fila[2] = rs.getString("descripcion");
	            
	            // Tratamiento para el campo BIT (1 = Activo, 0 = Inactivo)
	            boolean estadoBit = rs.getBoolean("estado");
	            fila[3] = estadoBit ? "Activo" : "Inactivo";
	            
	            modelo.addRow(fila);
	        }      
	        // Se lo acoplamos a tu JTable (ajusta 'tablaCategorias' si tu variable se llama diferente)
	        tablaCategorias.setModel(modelo);
	        
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error general: " + e.getMessage());
	    }
	}	   
	//ELIMIANR
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		// 1. Validación: Que haya un ID seleccionado
        if (idSeleccionado == -1 || idSeleccionado == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una categoría haciendo clic en la tabla.");
            return;
        }  
        // 2. Confirmación de seguridad
        int confirmar = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar esta categoría?\nEsta acción no se puede deshacer.", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            
            // 3. Sentencia SQL DELETE
            String sql = "DELETE FROM Categoria_Producto WHERE id_categoria = ?";
            
            try (Connection con = Conexion.getConnection(); 
                 PreparedStatement ps = con.prepareStatement(sql)) {
                
                ps.setInt(1, idSeleccionado);
                
                int resultado = ps.executeUpdate();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Categoría eliminada correctamente.");
                    
                    // 4. Refrescar tabla y limpiar campos
                    mostrarDatosEnTabla();
                    txtNombre.setText("");
                    txtDescripcion.setText("");
                    cboEstado.setSelectedIndex(0);
                    idSeleccionado = -1; // Limpiamos el ID
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, es probable que la categoría esté asociada a productos existentes: " + ex.getMessage());
            }
        }
		
	}
	//EVENTO A LA TABLA 
	protected void do_table_mouseClicked(MouseEvent e) {
		int filaSeleccionada = tablaCategorias.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            // Capturamos el ID en la variable global para cuando uses Modificar o Eliminar
            idSeleccionado = Integer.parseInt(tablaCategorias.getValueAt(filaSeleccionada, 0).toString());
            
            // Extraemos los textos de la fila seleccionada
            String nombre = tablaCategorias.getValueAt(filaSeleccionada, 1).toString();
            String descripcion = tablaCategorias.getValueAt(filaSeleccionada, 2).toString();
            
            // --- AQUÍ ESTÁ LA CLAVE PARA EL COMBOBOX ---
            // Leemos el texto de la columna 3 (Estado) que dice "Activo" o "Inactivo"
            String estado = tablaCategorias.getValueAt(filaSeleccionada, 3).toString();
            
            // Pasamos los datos que ya tenías listos a los cuadros de texto
            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);
            
            // Sincronizamos el ComboBox según el texto de la fila
            if (estado.equalsIgnoreCase("Activo")) {
                cboEstado.setSelectedIndex(0); // Mueve el combo a la posición 0 ("Activo")
            } else if (estado.equalsIgnoreCase("Inactivo")) {
                cboEstado.setSelectedIndex(1); // Mueve el combo a la posición 1 ("Inactivo")
            }
        } 
	}
	//MODIFICAR 
	protected void do_btnModificar_actionPerformed(ActionEvent e) {
		// 1. Validación: Que haya un ID seleccionado
        if (idSeleccionado == -1 || idSeleccionado == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione primero una fila de la tabla dando clic.");
            return;
        }
        // 2. Captura de datos
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        int estadoBit = (cboEstado.getSelectedIndex() == 0) ? 1 : 0;
        
        // 3. Validación básica de vacío
        if (nombre.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El nombre de la categoría es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
	        txtNombre.requestFocus();
	        return;
	    }
        
        // 🔥 CORRECCIÓN: Validación para que NO sea puro número ni símbolos especiales al actualizar
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        JOptionPane.showMessageDialog(null, "El nombre de la categoría solo puede contener letras y espacios.", "Validación", JOptionPane.WARNING_MESSAGE);
	        txtNombre.requestFocus();
	        return;
	    }
        
        // 4. Sentencia SQL con WHERE para el ID
        String sql = "UPDATE Categoria_Producto SET nombre = ?, descripcion = ?, estado = ? WHERE id_categoria = ?";
        
        try (Connection con = Conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {      
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, estadoBit);
            ps.setInt(4, idSeleccionado); // Usamos la variable global
            
            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "¡Categoría actualizada correctamente!");            
                // 5. Refrescar y limpiar
                mostrarDatosEnTabla();
                txtNombre.setText("");
                txtDescripcion.setText("");
                cboEstado.setSelectedIndex(0);
                idSeleccionado = -1; // Reseteamos el ID tras modificar
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + ex.getMessage());
        }
		
	}
}