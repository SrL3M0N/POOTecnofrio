package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Usuario;

public class UsuarioDAO {

	// 1. MÉTODO PARA EL INICIO DE SESIÓN (LOGIN)
	public Usuario login(String usuario, String clave) {
		Usuario u = null;
		String sql = """
		    SELECT
		        u.id_usuario,
		        u.usuario,
		        u.id_rol,
		        u.id_empleado,
		        e.nombres AS empleado,
		        r.nombre AS rol
		    FROM Usuario u
		    INNER JOIN Empleado e ON u.id_empleado = e.id_empleado
		    INNER JOIN Rol r ON u.id_rol = r.id_rol
		    WHERE u.usuario = ? AND u.clave = ? AND u.estado = 1
		""";

		try (Connection cn = Conexion.getConnection();
		     PreparedStatement ps = cn.prepareStatement(sql)) {

			ps.setString(1, usuario);
			ps.setString(2, clave);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					u = new Usuario();
					u.setIdUsuario(rs.getInt("id_usuario"));
					u.setUsuario(rs.getString("usuario"));
					u.setIdRol(rs.getInt("id_rol"));
					u.setIdEmpleado(rs.getInt("id_empleado"));
					u.setNombreEmpleado(rs.getString("empleado"));
					u.setNombreRol(rs.getString("rol"));
				}
			}
		} catch (Exception e) {
			System.out.println("Error Login: " + e.getMessage());
		}
		return u;
	}

	// 2. MÉTODO PARA LISTAR TODOS LOS USUARIOS (Para llenar el JTable)
	public List<Usuario> listarUsuarios() {
		List<Usuario> lista = new ArrayList<>();
		String sql = """
		    SELECT 
		        u.id_usuario, 
		        u.usuario, 
		        u.clave, 
		        u.estado, 
		        u.id_rol, 
		        u.id_empleado,
		        e.nombres AS empleado, 
		        r.nombre AS rol
		    FROM Usuario u
		    INNER JOIN Empleado e ON u.id_empleado = e.id_empleado
		    INNER JOIN Rol r ON u.id_rol = r.id_rol
		""";

		try (Connection cn = Conexion.getConnection();
		     PreparedStatement ps = cn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Usuario u = new Usuario();
				u.setIdUsuario(rs.getInt("id_usuario"));
				u.setUsuario(rs.getString("usuario"));
				u.setClave(rs.getString("clave"));
				u.setEstado(rs.getBoolean("estado"));
				u.setIdRol(rs.getInt("id_rol"));
				u.setIdEmpleado(rs.getInt("id_empleado"));
				u.setNombreEmpleado(rs.getString("empleado"));
				u.setNombreRol(rs.getString("rol"));
				lista.add(u);
			}
		} catch (Exception e) {
			System.out.println("Error al listar usuarios: " + e.getMessage());
		}
		return lista;
	}

	// 3. MÉTODO PARA REGISTRAR UN NUEVO USUARIO (INSERT)
	public boolean registrarUsuario(Usuario u) {
		String sql = "INSERT INTO Usuario (usuario, clave, estado, id_rol, id_empleado) VALUES (?, ?, ?, ?, ?)";
		try (Connection cn = Conexion.getConnection();
		     PreparedStatement ps = cn.prepareStatement(sql)) {

			ps.setString(1, u.getUsuario());
			ps.setString(2, u.getClave());
			ps.setBoolean(3, u.isEstado());
			ps.setInt(4, u.getIdRol());
			ps.setInt(5, u.getIdEmpleado());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println("Error al registrar usuario: " + e.getMessage());
			return false;
		}
	}

	// 4. MÉTODO PARA MODIFICAR UN USUARIO EXISTENTE (UPDATE)
	public boolean modificarUsuario(Usuario u) {
		String sql = "UPDATE Usuario SET usuario = ?, clave = ?, estado = ?, id_rol = ?, id_empleado = ? WHERE id_usuario = ?";
		try (Connection cn = Conexion.getConnection();
		     PreparedStatement ps = cn.prepareStatement(sql)) {

			ps.setString(1, u.getUsuario());
			ps.setString(2, u.getClave());
			ps.setBoolean(3, u.isEstado());
			ps.setInt(4, u.getIdRol());
			ps.setInt(5, u.getIdEmpleado());
			ps.setInt(6, u.getIdUsuario());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println("Error al modificar usuario: " + e.getMessage());
			return false;
		}
	}

	// 5. MÉTODO PARA ELIMINAR UN USUARIO (DELETE)
	public boolean eliminarUsuario(int idUsuario) {
		String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
		try (Connection cn = Conexion.getConnection();
		     PreparedStatement ps = cn.prepareStatement(sql)) {

			ps.setInt(1, idUsuario);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			System.out.println("Error al eliminar usuario: " + e.getMessage());
			return false;
		}
	}
	// Método para buscar si un empleado existe por su ID y traer su nombre
	public String buscarEmpleadoPorId(int idEmpleado) {
	    String nombreCompleto = null;
	    String sql = "SELECT nombres FROM Empleado WHERE id_empleado = ?";
	    
	    try (Connection cn = Conexion.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql)) {
	        
	        ps.setInt(1, idEmpleado);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                nombreCompleto = rs.getString("nombres");
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error al buscar empleado: " + e.getMessage());
	    }
	    return nombreCompleto; // Retorna null si no existe
	}
	// Método para verificar si el nombre de usuario ya existe
	public boolean existeNombreUsuario(String usuario) {
	    String sql = "SELECT COUNT(*) FROM Usuario WHERE usuario = ?";
	    try (Connection cn = Conexion.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql)) {
	        
	        ps.setString(1, usuario);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // Retorna true si ya existe
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error al verificar usuario: " + e.getMessage());
	    }
	    return false;
	}

	// Método para verificar si el ID de empleado ya tiene un usuario asignado
	public boolean existeIdEmpleado(int idEmpleado) {
	    String sql = "SELECT COUNT(*) FROM Usuario WHERE id_empleado = ?";
	    try (Connection cn = Conexion.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql)) {
	        
	        ps.setInt(1, idEmpleado);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // Retorna true si ya existe
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error al verificar ID de empleado: " + e.getMessage());
	    }
	    return false;
	}
}