package DAO;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Clases.Conexion;
import Clases.Producto;

public class ProductoDAO {
	public List<Producto> obtenerListaProductos() {
		List<Producto> lista = new ArrayList<>();
	    String sql = "SELECT \r\n"
	    		+ "    p.id_item, \r\n"
	    		+ "    p.codigoBarras, \r\n"
	    		+ "    i.nombre AS nombre_producto, \r\n"
	    		+ "    i.descripcion, \r\n"
	    		+ "    p.id_categoria, \r\n"
	    		+ "    c.nombre AS nombre_categoria, \r\n"
	    		+ "    p.id_proveedor, \r\n"
	    		+ "    pr.razon_social AS nombre_proveedor, \r\n"
	    		+ "    p.costo, \r\n"
	    		+ "    i.precioVenta, \r\n"
	    		+ "    p.stock\r\n"
	    		+ "FROM producto p\r\n"
	    		+ "INNER JOIN Item i ON p.id_item = i.id_item\r\n"
	    		+ "INNER JOIN Categoria_Producto c ON p.id_categoria = c.id_categoria\r\n"
	    		+ "INNER JOIN Proveedor pr ON p.id_proveedor = pr.id_proveedor;";
	    
	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	        	// Dentro de tu bucle while(rs.next()) en el DAO:
	        	Producto p = new Producto();
	        	p.setIdItem(rs.getInt("id_item"));           // Necesario para lógica de edición
	        	p.setCodigoBarras(rs.getString("codigoBarras"));
	        	p.setNombre(rs.getString("nombre_producto"));
	        	p.setDescripcion(rs.getString("descripcion"));

	        	p.setIdCategoria(rs.getInt("id_categoria")); // Necesario para lógica de edición
	        	p.setNombreCategoria(rs.getString("nombre_categoria"));

	        	p.setIdProveedor(rs.getInt("id_proveedor")); // Necesario para lógica de edición
	        	p.setNombreProveedor(rs.getString("nombre_proveedor"));

	        	p.setCosto(rs.getDouble("costo"));
	        	p.setPrecioVenta(rs.getDouble("precioVenta"));
	        	p.setStock(rs.getInt("stock"));

	        	lista.add(p);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}
	
	public void guardarProducto(Producto p) {
	    // 1. SQL para insertar en Item (Padre)
	    String sqlItem = "INSERT INTO Item (nombre, descripcion, precioVenta, tipo) VALUES (?, ?, ?, 'PRODUCTO')";
	    
	    // 2. SQL para insertar en Producto (Hija)
	    String sqlProd = "INSERT INTO producto (id_item, id_categoria, id_proveedor, stock, costo, codigoBarras) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    Connection con = null;
	    try {
	        con = Conexion.getConnection();
	        con.setAutoCommit(false); // Inicia transacción
	        
	        // --- PASO 1: Insertar en Item ---
	        PreparedStatement psItem = con.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);
	        psItem.setString(1, p.getNombre());
	        psItem.setString(2, p.getDescripcion());
	        psItem.setDouble(3, p.getPrecioVenta());
	        psItem.executeUpdate();
	        
	        // Obtener el ID generado automáticamente por el IDENTITY(1,1)
	        ResultSet rs = psItem.getGeneratedKeys();
	        int idItemGenerado = 0;
	        if (rs.next()) {
	            idItemGenerado = rs.getInt(1);
	        }
	        
	        // --- PASO 2: Insertar en Producto ---
	        PreparedStatement psProd = con.prepareStatement(sqlProd);
	        psProd.setInt(1, idItemGenerado);
	        psProd.setInt(2, p.getIdCategoria());
	        psProd.setInt(3, p.getIdProveedor());
	        psProd.setInt(4, p.getStock());
	        psProd.setDouble(5, p.getCosto());
	        psProd.setString(6, p.getCodigoBarras());
	        psProd.executeUpdate();
	        
	        con.commit(); // Todo salió bien, confirmamos cambios
	        
	    } catch (Exception e) {
	        try { if (con != null) con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
	        System.out.println("Error en transacción de guardado: " + e.getMessage());
	    } finally {
	        try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	        
	}
	
	public void modificarProducto(Producto p) {
	    // Tabla 'Item' contiene los datos básicos
	    String sqlItem = "UPDATE Item SET nombre = ?, descripcion = ?, precioVenta = ? WHERE id_item = ?";
	    
	    // Tabla 'Producto' contiene los datos técnicos de inventario
	    String sqlProducto = "UPDATE producto SET stock = ?, codigoBarras = ?, costo = ?, id_categoria = ?, id_proveedor = ? WHERE id_item = ?";

	    Connection con = null;
	    try {
	        con = Conexion.getConnection(); // Asegúrate de que este método devuelva una conexión válida
	        con.setAutoCommit(false); // Iniciar transacción

	        // 1. Actualizar tabla Item
	        try (PreparedStatement psItem = con.prepareStatement(sqlItem)) {
	            psItem.setString(1, p.getNombre());
	            psItem.setString(2, p.getDescripcion());
	            psItem.setDouble(3, p.getPrecioVenta());
	            psItem.setInt(4, p.getIdItem());
	            psItem.executeUpdate();
	        }

	        // 2. Actualizar tabla Producto
	        try (PreparedStatement psProd = con.prepareStatement(sqlProducto)) {
	            psProd.setInt(1, p.getStock());
	            psProd.setString(2, p.getCodigoBarras());
	            psProd.setDouble(3, p.getCosto());
	            psProd.setInt(4, p.getIdCategoria());
	            psProd.setInt(5, p.getIdProveedor());
	            psProd.setInt(6, p.getIdItem());
	            psProd.executeUpdate();
	        }

	        con.commit(); // Confirmar ambos cambios
	        System.out.println("Producto actualizado correctamente en ambas tablas.");

	    } catch (SQLException e) {
	        if (con != null) {
	            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        }
	        // Este mensaje te ayudará a identificar si el error viene de la Foreing Key de Proveedor
	        throw new RuntimeException("Error crítico al modificar el producto: " + e.getMessage());
	    } finally {
	        if (con != null) {
	            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	
	// método de validación PARA EVITAR DUPLICADOS AL GUARDAR
	public boolean existeCodigoBarras(String codigoBarras) {
	    String sql = "SELECT COUNT(*) FROM producto WHERE codigoBarras = ?";
	    try (Connection con = Conexion.getConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, codigoBarras);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0; // Retorna true si ya existe
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public boolean existeNombreProducto(String nombre) {
	    // Buscamos en la tabla Item
	    String sql = "SELECT COUNT(*) FROM Item WHERE nombre = ?";
	    try (Connection con = Conexion.getConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, nombre);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public void eliminarProducto(int idItem) {
	    // 1. Eliminar primero en la tabla hija (Producto)
	    String sqlProducto = "DELETE FROM Producto WHERE id_item = ?";
	    // 2. Eliminar luego en la tabla padre (Item)
	    String sqlItem = "DELETE FROM Item WHERE id_item = ?";

	    Connection con = null;
	    try {
	        con = Conexion.getConnection();
	        con.setAutoCommit(false); // Iniciar transacción

	        // Eliminar producto
	        try (PreparedStatement psProd = con.prepareStatement(sqlProducto)) {
	            psProd.setInt(1, idItem);
	            psProd.executeUpdate();
	        }

	        // Eliminar ítem
	        try (PreparedStatement psItem = con.prepareStatement(sqlItem)) {
	            psItem.setInt(1, idItem);
	            psItem.executeUpdate();
	        }

	        con.commit(); // Confirmar cambios
	        System.out.println("Producto eliminado correctamente.");
	        
	    } catch (SQLException e) {
	        if (con != null) {
	            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        }
	        throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
	    } finally {
	        if (con != null) {
	            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	//AJUSTAR EL STOCK 
	public ArrayList<Producto> listarProductoStock() {

	    ArrayList<Producto> lista = new ArrayList<>();

	    Connection cn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {

	        cn = Conexion.getConnection();

	        String sql =
	            "SELECT i.id_item, i.nombre, p.stock " +
	            "FROM Item i " +
	            "INNER JOIN Producto p ON i.id_item = p.id_item " +
	            "ORDER BY i.nombre";

	        ps = cn.prepareStatement(sql);

	        rs = ps.executeQuery();

	        while (rs.next()) {

	            Producto p = new Producto();

	            p.setIdItem(rs.getInt("id_item"));
	            p.setNombre(rs.getString("nombre"));
	            p.setStock(rs.getInt("stock"));

	            lista.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
	
	//ACTUALIZAR STOCk
	public int actualizarStock(int idItem, int nuevoStock) {

	    int estado = 0;

	    Connection cn = null;
	    PreparedStatement ps = null;

	    try {

	        cn = Conexion.getConnection();

	        String sql =
	            "UPDATE Producto SET stock = ? WHERE id_item = ?";

	        ps = cn.prepareStatement(sql);

	        ps.setInt(1, nuevoStock);
	        ps.setInt(2, idItem);

	        estado = ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return estado;
	}
	
	//GENERAR CODIGO DE BARRAS 
	public String generarCodigoBarras() {

	    String codigoInicial = "77500000000001";
	    String sql = "SELECT MAX(codigoBarras) AS ultimo FROM Producto";

	    try (Connection cn = Conexion.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            String ultimo = rs.getString("ultimo");

	            if (ultimo != null && !ultimo.isEmpty()) {
	                long numero = Long.parseLong(ultimo);
	                numero++;
	                return String.valueOf(numero);
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("Error al generar código de barras: " + e.getMessage());
	    }

	    return codigoInicial;
	}
	
}
