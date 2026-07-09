package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.DetalleVenta;
import Clases.Venta;

public class VentaDAO {
	
	    public int insertarVenta(Venta v, List<DetalleVenta> detalles) {
	        Connection cn = Conexion.getConnection();

	        try {
	            cn.setAutoCommit(false); // 🔥 transacción

	            // 1. INSERT VENTA
	            String sqlVenta = "INSERT INTO Venta (id_cliente, id_usuario, subtotal, igv, total) VALUES (?, ?, ?, ?, ?)";

	            PreparedStatement ps = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, v.getIdCliente());
	            ps.setInt(2, v.getIdUsuario());
	            ps.setDouble(3, v.getSubtotal());
	            ps.setDouble(4, v.getIgv());
	            ps.setDouble(5, v.getTotal());

	            ps.executeUpdate();

	            ResultSet rs = ps.getGeneratedKeys();
	            rs.next();
	            int idVenta = rs.getInt(1);

	            // 2. INSERT DETALLES
	            String sqlDet = "INSERT INTO detalle_venta (id_venta, id_item, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

	            PreparedStatement psDet = cn.prepareStatement(sqlDet);

	            for (DetalleVenta d : detalles) {
	                psDet.setInt(1, idVenta);
	                psDet.setInt(2, d.getIdItem());
	                psDet.setInt(3, d.getCantidad());
	                psDet.setDouble(4, d.getPrecioUnitario());
	                psDet.addBatch();
	            }

	            psDet.executeBatch();

	            cn.commit(); // 🔥 confirmar todo

	            return idVenta;

	        } catch (Exception e) {
	            try { cn.rollback(); } catch (Exception ex) {}
	            e.printStackTrace();
	            return 0;
	        }
	    }
	    
	    //GENERAR NUMERO CORRELATIVO
	    //METODO PARA GENERAR NUMERO DE COMPROBANTE 
	    public String generarNumeroComprobante(String tipo) {

	        String serie = tipo.equals("FACTURA") ? "F001" : "B001";

	        int ultimo = obtenerUltimoNumero(tipo);
	        int nuevo = ultimo + 1;

	        return serie + "-" + String.format("%08d", nuevo);
	    }
	    //METODO PARA OBTENER EL ULTIMO NUMERO
	    public int obtenerUltimoNumero(String tipo) {
	        Connection cn = Conexion.getConnection();
	        int numero = 0;

	        String sql = "SELECT MAX(CAST(SUBSTRING(numero_comprobante, 6, 8) AS INT)) "
	                   + "FROM Venta WHERE tipo_comprobante = ?";

	        try (PreparedStatement ps = cn.prepareStatement(sql)) {

	            ps.setString(1, tipo);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                numero = rs.getInt(1);
	            }

	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }

	        return numero;
	    }
	    
	
	    public void registrarVenta(Venta v, List<DetalleVenta> lista) {
	        Connection con = null;
	        PreparedStatement psVenta = null;
	        PreparedStatement psDetalle = null;
	        PreparedStatement psStock = null;
	        ResultSet rs = null;

	        try {

	            con = Conexion.getConnection();
	            con.setAutoCommit(false);

	            // =========================
	            // INSERT VENTA
	            // =========================
	            String sqlVenta = "INSERT INTO Venta "
	                    + "(subtotal, igv, total, id_cliente, id_usuario, numero_comprobante, tipo_comprobante) "
	                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);

	            psVenta.setDouble(1, v.getSubtotal());
	            psVenta.setDouble(2, v.getIgv());
	            psVenta.setDouble(3, v.getTotal());
	            psVenta.setInt(4, v.getIdCliente());
	            psVenta.setInt(5, v.getIdUsuario());
	            psVenta.setString(6, v.getNumeroComprobante());
	            psVenta.setString(7, v.getTipoComprobante());

	            psVenta.executeUpdate();

	            // obtener id venta
	            rs = psVenta.getGeneratedKeys();
	            int idVenta = 0;

	            if (rs.next()) {
	                idVenta = rs.getInt(1);
	            }

	            // =========================
	            // INSERT DETALLES
	            // =========================
	            String sqlDetalle = "INSERT INTO detalle_venta "
	                    + "(id_venta, id_item, cantidad, precio_unitario) "
	                    + "VALUES (?, ?, ?, ?)";
	                   
	            psDetalle = con.prepareStatement(sqlDetalle);
	            
	            // =========================
	            // STOCK UPDATE (solo productos)
	            // =========================
	            String sqlStock =
	                    "UPDATE Producto SET stock = stock - ? WHERE id_item = ? AND stock >= ?";

	            psStock = con.prepareStatement(sqlStock);

	            // =========================
	            // CONSULTA TIPO ITEM
	            // =========================
	            String sqlTipo = "SELECT tipo FROM Item WHERE id_item = ?";
	            PreparedStatement psTipo = con.prepareStatement(sqlTipo);

	            for (DetalleVenta d : lista) {

	                // INSERT DETALLE
	                psDetalle.setInt(1, idVenta);
	                psDetalle.setInt(2, d.getIdItem());
	                psDetalle.setInt(3, d.getCantidad());
	                psDetalle.setDouble(4, d.getPrecioUnitario());
	                psDetalle.addBatch();

	                // OBTENER TIPO
	                psTipo.setInt(1, d.getIdItem());

	                ResultSet rsTipo = psTipo.executeQuery();

	                if (rsTipo.next()) {

	                    String tipo = rsTipo.getString("tipo");

	                    // SOLO PRODUCTO DESCUENTA STOCK
	                    if ("PRODUCTO".equalsIgnoreCase(tipo)) {

	                        psStock.setInt(1, d.getCantidad());
	                        psStock.setInt(2, d.getIdItem());
	                        psStock.setInt(3, d.getCantidad());

	                        int filas = psStock.executeUpdate();

	                        if (filas == 0) {
	                            rsTipo.close();
	                            throw new Exception(
	                                    "Stock insuficiente para el item ID: " + d.getIdItem()
	                            );
	                        }
	                    }
	                }

	                rsTipo.close();
	            }
	            psDetalle.executeBatch();
	            
	            con.commit();

	        } catch (Exception e) {

	            try {
	                if (con != null) con.rollback();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }

	            e.printStackTrace();

	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (psVenta != null) psVenta.close();
	                if (psDetalle != null) psDetalle.close();
	                if (con != null) con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }    
}
