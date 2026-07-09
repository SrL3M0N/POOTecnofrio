package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Clases.Cliente;
import Clases.Conexion;

public class ClienteDAO {
	
	public List<Cliente> listarClientes() {

	    List<Cliente> lista = new ArrayList<>();

	    String sql = "SELECT * FROM Cliente";

	    try {

	        Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Cliente c = new Cliente();

	            c.setIdCliente(rs.getInt("id_cliente"));
	            c.setNombres(rs.getString("nombres"));
	            c.setApellidos(rs.getString("apellidos"));
	            c.setTelefono(rs.getString("telefono"));
	            c.setEmail(rs.getString("email"));
	            c.setDocumento(rs.getString("documento"));

	            lista.add(c);
	        }

	    } catch (Exception e) {
	        System.out.println("Error listar clientes: " + e);
	    }

	    return lista;
	}

	public boolean registrarCliente(Cliente cliente) {

	    String sql = "INSERT INTO Cliente(nombres, apellidos, telefono, email, documento) "
	               + "VALUES(?,?,?,?,?)";

	    try {

	        Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setString(1, cliente.getNombres());
	        ps.setString(2, cliente.getApellidos());
	        ps.setString(3, cliente.getTelefono());
	        ps.setString(4, cliente.getEmail());
	        ps.setString(5, cliente.getDocumento());

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        System.out.println("Error al registrar cliente: " + e.getMessage());
	        return false;
	    }
	}
	public boolean modificarCliente(Cliente cliente) {

	    String sql = "UPDATE Cliente "
	               + "SET nombres=?, apellidos=?, telefono=?, email=?, documento=? "
	               + "WHERE id_cliente=?";

	    try {
	        Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setString(1, cliente.getNombres());
	        ps.setString(2, cliente.getApellidos());
	        ps.setString(3, cliente.getTelefono());
	        ps.setString(4, cliente.getEmail());
	        ps.setString(5, cliente.getDocumento());
	        ps.setInt(6, cliente.getIdCliente());

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        System.out.println("Error al modificar: " + e.getMessage());
	        return false;
	    }
	}
	public boolean eliminarCliente(int idCliente) {

	    String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

	    try {
	        Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, idCliente);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        System.out.println("Error al eliminar: " + e.getMessage());
	        return false;
	    }
	}
}
