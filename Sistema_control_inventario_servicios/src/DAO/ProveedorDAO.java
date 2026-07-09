package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Clases.Conexion;
import Clases.Proveedor;

public class ProveedorDAO {
	//PARA MODAL PRODUCTO / PROVEEDOR
	public List<Proveedor> listarProveedorProducto() {
	    List<Proveedor> lista = new ArrayList<>();
	    // Usamos el nombre exacto de tu tabla y columnas Lista proveedores Activos
	    String sql = "SELECT id_proveedor, razon_social, num_documento FROM Proveedor WHERE estado = 1";
	    
	    try {
	        Connection con = Conexion.getConnection(); // Ajusta según tu método de conexión
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            Proveedor p = new Proveedor();
	            p.setId_proveedor(rs.getInt("id_proveedor"));
	            p.setRazon_social(rs.getString("razon_social"));
	            p.setNum_documento(rs.getString("num_documento"));
	            
	            lista.add(p);
	        }
	    } catch (Exception e) {
	        System.out.println("Error al listar proveedores: " + e.getMessage());
	    }
	    return lista;
	}
	
	  // LISTAR
    public List<Proveedor> listarProveedor() {

        List<Proveedor> lista = new ArrayList<>();

        String sql = "SELECT * FROM Proveedor";

        try {

            Connection con = Conexion.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Proveedor p = new Proveedor();

                p.setId_proveedor(
                        rs.getInt("id_proveedor"));

                p.setRazon_social(
                        rs.getString("razon_social"));

                p.setTipo_documento(
                        rs.getString("tipo_documento"));

                p.setNum_documento(
                        rs.getString("num_documento"));

                p.setTelefono(
                        rs.getString("telefono"));

                p.setEmail(
                        rs.getString("email"));

                p.setDireccion(
                        rs.getString("direccion"));

                p.setEstado(
                        rs.getBoolean("estado"));

                p.setFecha_registro(
                        rs.getDate("fecha_registro"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error al listar: "
                            + e.getMessage());
        }

        return lista;
    }
    
    // INSERTAR
    public void insertar(Proveedor p) {

        String sql =
                "INSERT INTO Proveedor "
              + "(razon_social,"
              + "tipo_documento,"
              + "num_documento,"
              + "telefono,"
              + "email,"
              + "direccion,"
              + "estado,"
              + "fecha_registro)"
              + " VALUES (?,?,?,?,?,?,?,?)";

        try {

            Connection con =
                    Conexion.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    p.getRazon_social());

            ps.setString(
                    2,
                    p.getTipo_documento());

            ps.setString(
                    3,
                    p.getNum_documento());

            ps.setString(
                    4,
                    p.getTelefono());

            ps.setString(
                    5,
                    p.getEmail());

            ps.setString(
                    6,
                    p.getDireccion());

            ps.setBoolean(
                    7,
                    p.isEstado());

            ps.setDate(
                    8,
                    p.getFecha_registro());

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error insertar: "
                            + e.getMessage());
        }
    }

    // EDITAR
    public void editar(Proveedor p) {

        String sql =
                "UPDATE Proveedor SET "
              + "razon_social=?, "
              + "tipo_documento=?, "
              + "num_documento=?, "
              + "telefono=?, "
              + "email=?, "
              + "direccion=?, "
              + "estado=? "
              + "WHERE id_proveedor=?";

        try {

            Connection con =
                    Conexion.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    p.getRazon_social());

            ps.setString(
                    2,
                    p.getTipo_documento());

            ps.setString(
                    3,
                    p.getNum_documento());

            ps.setString(
                    4,
                    p.getTelefono());

            ps.setString(
                    5,
                    p.getEmail());

            ps.setString(
                    6,
                    p.getDireccion());

            ps.setBoolean(
                    7,
                    p.isEstado());

            ps.setInt(
                    8,
                    p.getId_proveedor());

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error editar: "
                            + e.getMessage());
        }
    }

    // BUSCAR
    public Proveedor buscar(int id) {

        String sql =
                "SELECT * "
              + "FROM Proveedor "
              + "WHERE id_proveedor=?";

        Proveedor p = null;

        try {

            Connection con =
                    Conexion.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                p = new Proveedor();

                p.setId_proveedor(
                        rs.getInt(
                                "id_proveedor"));

                p.setRazon_social(
                        rs.getString(
                                "razon_social"));

                p.setTipo_documento(
                        rs.getString(
                                "tipo_documento"));

                p.setNum_documento(
                        rs.getString(
                                "num_documento"));

                p.setTelefono(
                        rs.getString(
                                "telefono"));

                p.setEmail(
                        rs.getString(
                                "email"));

                p.setDireccion(
                        rs.getString(
                                "direccion"));

                p.setEstado(
                        rs.getBoolean(
                                "estado"));

                p.setFecha_registro(
                        rs.getDate(
                                "fecha_registro"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Error buscar: "
                            + e.getMessage());
        }

        return p;
    }

    // ELIMINAR (LÓGICO)
    public void eliminar(int id) {
    	   String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";
    	    try {
    	        Connection con = Conexion.getConnection();
    	        PreparedStatement ps = con.prepareStatement(sql);
    	        ps.setInt(1, id);
    	        ps.executeUpdate();
    	        ps.close();
    	        con.close();
    	    } catch (SQLException e) 
    	    {
    	        if (e.getMessage().contains("FK_producto_Proveedor")) {
    	            JOptionPane.showMessageDialog(null,
    	                    "No se puede eliminar este proveedor porque tiene productos registrados.",
    	                    "Proveedor en uso",
    	                    JOptionPane.WARNING_MESSAGE);
    	        } 
    	        else 
    	        {
    	            JOptionPane.showMessageDialog(null,
    	                    "Error al eliminar: " + e.getMessage());
    	        }
    	    }
    }
    public boolean existeCorreo(String correo) {

        String sql = "SELECT COUNT(*) FROM Proveedor WHERE email = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
    public boolean existeRazonSocial(String razon) {

        String sql = "SELECT COUNT(*) FROM Proveedor WHERE razon_social = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, razon);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

}
