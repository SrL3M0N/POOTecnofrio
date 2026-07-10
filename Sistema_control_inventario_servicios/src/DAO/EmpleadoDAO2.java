package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Empleado2;

public class EmpleadoDAO2 {
	/*
//MODAL
	public List<Empleado> listarEmpleadosConDetalle() {
	    List<Empleado> lista = new ArrayList<>();
	    // Ajusta los nombres de tabla 'TipoEmpleado' y 'Disponibilidad' según tu BD real
	    String sql = "SELECT e.id_empleado, e.nombres, t.nombre AS tipo, d.nombre_estado AS disp " +
	                 "FROM Empleado e " +
	                 "LEFT JOIN TipoEmpleado t ON e.id_tipo_empleado = t.id_tipo_empleado " +
	                 "LEFT JOIN EstadoDisponibilidad d ON e.id_disponibilidad = d.id_disponibilidad";
	    
	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Empleado e = new Empleado();
	            e.setIdEmpleado(rs.getInt("id_empleado"));
	            e.setNombres(rs.getString("nombres"));
	            e.setNombreTipo(rs.getString("tipo"));
	            e.setNombreDisp(rs.getString("disp"));
	            lista.add(e);
	        }
	    } catch (SQLException e) { e.printStackTrace(); }
	    return lista;
	}
*/
	

	public List<Empleado2> listarEmpleadosConDetalle() {
	    List<Empleado2> lista = new ArrayList<>();

	    String sql = "SELECT id_empleado, nombres, documento, telefono FROM Empleado";

	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Empleado2 e = new Empleado2();
	            e.setIdEmpleado(rs.getInt("id_empleado"));
	            e.setNombres(rs.getString("nombres"));
	            e.setDocumento(rs.getString("documento"));
	            e.setTelefono(rs.getString("telefono"));

	            lista.add(e);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

}
