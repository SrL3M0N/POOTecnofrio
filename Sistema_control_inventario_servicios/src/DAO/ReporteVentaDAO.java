package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import Clases.Conexion;
import Clases.ReporteVenta;

public class ReporteVentaDAO {

    public List<ReporteVenta> listarVentas(java.util.Date fechaInicio, java.util.Date fechaFin) {

        List<ReporteVenta> lista = new ArrayList<>();

        String sql = "SELECT v.id_venta, v.fecha, v.tipo_comprobante, v.numero_comprobante, "
                + "c.nombres + ' ' + c.apellidos AS cliente, "
                + "i.nombre AS item, i.tipo, "
                + "dv.cantidad, dv.precio_unitario, "
                + "(dv.cantidad * dv.precio_unitario) AS importe, "
                + "v.total, u.usuario "
                + "FROM Venta v "
                + "INNER JOIN Cliente c ON v.id_cliente = c.id_cliente "
                + "INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta "
                + "INNER JOIN Item i ON dv.id_item = i.id_item "
                + "INNER JOIN Usuario u ON v.id_usuario = u.id_usuario "
                + "WHERE CAST(v.fecha AS DATE) BETWEEN ? AND ? "
                + "ORDER BY v.fecha DESC";

        try (Connection cn = Conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReporteVenta r = new ReporteVenta();

                r.setIdVenta(rs.getInt("id_venta"));
                r.setFecha(rs.getTimestamp("fecha"));
                r.setTipoComprobante(rs.getString("tipo_comprobante"));
                r.setNumeroComprobante(rs.getString("numero_comprobante"));
                r.setCliente(rs.getString("cliente"));
                r.setItem(rs.getString("item"));
                r.setTipo(rs.getString("tipo"));
                r.setCantidad(rs.getInt("cantidad"));
                r.setPrecioUnitario(rs.getDouble("precio_unitario"));
                r.setImporte(rs.getDouble("importe"));
                r.setTotal(rs.getDouble("total"));
                r.setUsuario(rs.getString("usuario"));

                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
