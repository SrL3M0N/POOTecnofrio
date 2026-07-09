package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Utilidades.CboCategoria;

public class CategoriaDAO {
	public List<CboCategoria> listarCategorias() {
        List<CboCategoria> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre " +
                "FROM Categoria_Producto " +
                "WHERE estado = 1";
        
        try (Connection con = Conexion.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Creamos el objeto usando el constructor de tu clase CboCategoria
                CboCategoria cat = new CboCategoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre")
                );
                lista.add(cat);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
