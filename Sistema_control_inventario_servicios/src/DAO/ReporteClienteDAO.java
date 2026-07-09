package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.ReporteCliente;

public class ReporteClienteDAO {

	public List<ReporteCliente> listarClientes(java.util.Date fechaInicio, java.util.Date fechaFin) {

	    List<ReporteCliente> lista = new ArrayList<>();

	    String sql = "SELECT "
	            + "c.id_cliente, "
	            + "c.nombres + ' ' + c.apellidos AS cliente, "
	            + "c.documento, "
	            + "c.telefono, "
	            + "c.email, "
	            + "COUNT(v.id_venta) AS cantidad_ventas, "
	            + "ISNULL(SUM(v.total), 0) AS total_comprado, "
	            + "MAX(v.fecha) AS ultima_compra "
	            + "FROM Cliente c "
	            + "LEFT JOIN Venta v ON c.id_cliente = v.id_cliente "
	            + "AND CAST(v.fecha AS DATE) BETWEEN ? AND ? "
	            + "GROUP BY c.id_cliente, c.nombres, c.apellidos, c.documento, c.telefono, c.email "
	            + "ORDER BY total_comprado DESC";

	    try (Connection cn = Conexion.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql)) {

	        ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
	        ps.setDate(2, new java.sql.Date(fechaFin.getTime()));

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            ReporteCliente r = new ReporteCliente();

	            r.setIdCliente(rs.getInt("id_cliente"));
	            r.setCliente(rs.getString("cliente"));
	            r.setDocumento(rs.getString("documento"));
	            r.setTelefono(rs.getString("telefono"));
	            r.setEmail(rs.getString("email"));
	            r.setCantidadVentas(rs.getInt("cantidad_ventas"));
	            r.setTotalComprado(rs.getDouble("total_comprado"));
	            r.setUltimaCompra(rs.getTimestamp("ultima_compra"));

	            lista.add(r);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
}
