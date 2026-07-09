package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Item;

public class ItemDAO {
	
	public List<Item> listarProductos(){

	    List<Item> lista = new ArrayList<>();

	    String sql =
	    		"SELECT i.id_item, i.nombre, i.precioVenta, p.stock, i.tipo " +
	    		"FROM Item i " +
	    		"INNER JOIN Producto p ON p.id_item = i.id_item " +
	    		"WHERE i.tipo='PRODUCTO'";

	    try(Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()){

	        while(rs.next()){

	            Item item = new Item();

	            item.setIdItem(rs.getInt("id_item"));
	            item.setNombre(rs.getString("nombre"));
	            item.setPrecio(rs.getDouble("precioVenta"));
	            item.setStock(rs.getInt("stock"));
	            item.setTipo(rs.getString("tipo"));

	            lista.add(item);
	        }

	    }catch(Exception e){
	        System.out.println(e.getMessage());
	    }

	    return lista;
	}
	
	public List<Item> listarServicios(){

	    List<Item> lista = new ArrayList<>();

	    String sql =
	    	    "SELECT i.id_item, i.nombre, i.precioVenta, i.tipo " +
	    	    	    "FROM Item i " +
	    	    	    "INNER JOIN Servicio s ON s.id_item = i.id_item " +
	    	    	    "WHERE i.tipo = 'SERVICIO' AND s.activo = 1";

	    try(Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()){

	        while(rs.next()){

	            Item item = new Item();

	            item.setIdItem(rs.getInt("id_item"));
	            item.setNombre(rs.getString("nombre"));
	            item.setPrecio(rs.getDouble("precioVenta"));
	            item.setTipo(rs.getString("tipo"));

	            lista.add(item);
	        }

	    }catch(Exception e){
	        System.out.println(e.getMessage());
	    }

	    return lista;
	}

	public List<Item> listarTodos(){

	    List<Item> lista = new ArrayList<>();

	    String sql =
	    "SELECT i.id_item, i.nombre, i.precioVenta, i.tipo, p.stock " +
	    "FROM Item i " +
	    "LEFT JOIN Producto p ON p.id_item = i.id_item";

	    try(Connection cn = Conexion.getConnection();
	        PreparedStatement ps = cn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()){

	        while(rs.next()){

	            Item item = new Item();

	            item.setIdItem(rs.getInt("id_item"));
	            item.setNombre(rs.getString("nombre"));
	            item.setPrecio(rs.getDouble("precioVenta"));
	            item.setTipo(rs.getString("tipo"));

	            // SOLO productos tendrán stock
	            item.setStock(rs.getInt("stock"));

	            lista.add(item);
	        }

	    }catch(Exception e){
	        System.out.println(e.getMessage());
	    }

	    return lista;
	}


}
