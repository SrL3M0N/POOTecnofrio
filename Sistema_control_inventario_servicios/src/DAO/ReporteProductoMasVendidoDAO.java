package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.ReporteProductoMasVendidos;

public class ReporteProductoMasVendidoDAO {

	    public List<ReporteProductoMasVendidos> listarProductosMasVendidos(
	            java.util.Date fechaInicio, java.util.Date fechaFin) {

	        List<ReporteProductoMasVendidos> lista = new ArrayList<>();

	        String sql = "SELECT "
	                + "i.id_item, "
	                + "i.nombre AS producto, "
	                + "SUM(dv.cantidad) AS cantidad_vendida, "
	                + "SUM(dv.cantidad * dv.precio_unitario) AS total_recaudado "
	                + "FROM detalle_venta dv "
	                + "INNER JOIN Venta v ON dv.id_venta = v.id_venta "
	                + "INNER JOIN Item i ON dv.id_item = i.id_item "
	                + "WHERE i.tipo = 'PRODUCTO' "
	                + "AND CAST(v.fecha AS DATE) BETWEEN ? AND ? "
	                + "GROUP BY i.id_item, i.nombre "
	                + "ORDER BY cantidad_vendida DESC";

	        try (Connection cn = Conexion.getConnection();
	             PreparedStatement ps = cn.prepareStatement(sql)) {

	            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
	            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                ReporteProductoMasVendidos r = new ReporteProductoMasVendidos();

	                r.setIdItem(rs.getInt("id_item"));
	                r.setProducto(rs.getString("producto"));
	                r.setCantidadVendida(rs.getInt("cantidad_vendida"));
	                r.setTotalRecaudado(rs.getDouble("total_recaudado"));

	                lista.add(r);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return lista;
	    }
}
