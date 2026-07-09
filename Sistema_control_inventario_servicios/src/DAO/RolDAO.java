package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Rol;

public class RolDAO {

   //LISTAR 
    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre FROM Rol ORDER BY id_rol";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar roles en RolDAO: " + e.getMessage());
        }
        return lista;
    }

    // REGISTRAR
    public boolean registrarRol(Rol rol) {
        String sql = "INSERT INTO Rol (nombre) VALUES (?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombre());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar rol en RolDAO: " + e.getMessage());
            return false;
        }
    }

    //ELIMINAR
    public boolean eliminarRol(int idRol) throws SQLException {
        String sql = "DELETE FROM Rol WHERE id_rol = ?";

   
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRol);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }
}