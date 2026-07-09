package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Servicio;

public class ServicioDAO {
	
	public List<Servicio> listarServicios() {
	    List<Servicio> lista = new ArrayList<>();
	    
	    // CAMBIA 'e.nombre' POR EL NOMBRE REAL DE LA COLUMNA DE TU TABLA EMPLEADO
	    // CAMBIA 'i.nombre' POR EL NOMBRE REAL DE LA COLUMNA DE TU TABLA ITEM
	    String sql = "SELECT i.id_item, i.nombre, i.descripcion, i.precioVenta, \r\n"
	    		+ "                 s.activo \r\n"
	    		+ "                 FROM Item i \r\n"
	    		+ "                 INNER JOIN Servicio s ON i.id_item = s.id_item \r\n"
	    		+ "                 WHERE i.tipo = 'SERVICIO'";

	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Servicio s = new Servicio();
	            s.setIdItem(rs.getInt("id_item"));
	            // Usamos getString("nombre") porque ya no hay ambigüedad al poner i.nombre en el SELECT
	            s.setNombre(rs.getString("nombre")); 
	            s.setDescripcion(rs.getString("descripcion"));
	            s.setPrecioVenta(rs.getDouble("precioVenta"));
	            s.setActivo(rs.getBoolean("activo"));
	            
	            lista.add(s);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error en listarServicios: " + e.getMessage());
	    }
	    return lista;
	}
	public boolean insertar(Servicio s) {
	    // SQL 1: Insertar en la tabla padre (Item)
	    String sqlItem = "INSERT INTO Item (nombre, descripcion, precioVenta, tipo) VALUES (?, ?, ?, 'SERVICIO')";
	    // SQL 2: Insertar en la tabla hija (Servicio), usando el SCOPE_IDENTITY() para obtener el ID recién creado
	    String sqlServicio = "INSERT INTO Servicio (id_item, activo) VALUES (?, ?)";
	    
	    Connection con = null;
	    PreparedStatement psItem = null;
	    PreparedStatement psServicio = null;
	    ResultSet rs = null;

	    try {
	        con = Conexion.getConnection();
	        con.setAutoCommit(false); // COMENZAMOS LA TRANSACCIÓN

	        // 1. Insertar el Item
	        psItem = con.prepareStatement(sqlItem, PreparedStatement.RETURN_GENERATED_KEYS);
	        psItem.setString(1, s.getNombre());
	        psItem.setString(2, s.getDescripcion());
	        psItem.setDouble(3, s.getPrecioVenta());
	        psItem.executeUpdate();

	        // Obtener el ID generado por el IDENTITY
	        rs = psItem.getGeneratedKeys();
	        int idGenerado = 0;
	        if (rs.next()) {
	            idGenerado = rs.getInt(1);
	        }

	        // 2. Insertar el Servicio usando el ID obtenido
	        psServicio = con.prepareStatement(sqlServicio);
	        psServicio.setInt(1, idGenerado);
	        psServicio.setBoolean(2, s.isActivo());
	        psServicio.executeUpdate();

	        con.commit(); // Si todo sale bien, guardamos los cambios
	        return true;
	        
	    } catch (SQLException e) {
	        try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        e.printStackTrace();
	        return false;
	    } finally {
	        // Cerrar recursos...
	    }
	}
	public boolean modificar(Servicio s) {
	    // 1. SQL para actualizar la tabla base (Item)
	    String sqlItem = "UPDATE Item SET nombre = ?, descripcion = ?, precioVenta = ? WHERE id_item = ?";
	    // 2. SQL para actualizar la tabla específica (Servicio)
	    String sqlServicio = "UPDATE Servicio SET activo = ? WHERE id_item = ?";
	    
	    Connection con = null;
	    try {
	        con = Conexion.getConnection();
	        con.setAutoCommit(false); // Iniciar transacción

	        // Actualizar Item
	        PreparedStatement psItem = con.prepareStatement(sqlItem);
	        psItem.setString(1, s.getNombre());
	        psItem.setString(2, s.getDescripcion());
	        psItem.setDouble(3, s.getPrecioVenta());
	        psItem.setInt(4, s.getIdItem());
	        psItem.executeUpdate();

	        // Actualizar Servicio
	        PreparedStatement psServicio = con.prepareStatement(sqlServicio);
	        psServicio.setBoolean(1, s.isActivo());
	        psServicio.setInt(2, s.getIdItem());
	        psServicio.executeUpdate();

	        con.commit(); // Confirmar cambios
	        return true;
	    } catch (SQLException e) {
	        try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean existeServicio(String nombre, String descripcion) {
	    String sql = "SELECT COUNT(*) FROM Item WHERE nombre = ? AND descripcion = ?";
	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, nombre);
	        ps.setString(2, descripcion);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // Retorna true si ya existe
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public boolean eliminar(int idItem) {
	    // SQL 1: Eliminar de Servicio (Hijo)
	    String sqlServicio = "DELETE FROM Servicio WHERE id_item = ?";
	    // SQL 2: Eliminar de Item (Padre)
	    String sqlItem = "DELETE FROM Item WHERE id_item = ?";
	    
	    Connection con = null;
	    try {
	        con = Conexion.getConnection();
	        con.setAutoCommit(false); // Iniciar transacción

	        // 1. Eliminar el registro hijo primero
	        PreparedStatement psServicio = con.prepareStatement(sqlServicio);
	        psServicio.setInt(1, idItem);
	        psServicio.executeUpdate();

	        // 2. Eliminar el registro padre
	        PreparedStatement psItem = con.prepareStatement(sqlItem);
	        psItem.setInt(1, idItem);
	        psItem.executeUpdate();

	        con.commit(); // Confirmar
	        return true;
	    } catch (SQLException e) {
	        try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        e.printStackTrace();
	        return false;
	    }
	}

}
