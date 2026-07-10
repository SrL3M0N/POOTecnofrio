package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Clases.Conexion;
import Clases.Empleado;

public class EmpleadoDAO {

    // INSERTAR
    public boolean insertar(Empleado emp) {

    	String sql = "INSERT INTO Empleado(nombres, documento, telefono) VALUES(?,?,?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getNombres());
            ps.setString(2, emp.getDocumento());
            ps.setString(3, emp.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // LISTAR
    public List<Empleado> listar(){

        List<Empleado> lista = new ArrayList<>();

        String sql="SELECT * FROM Empleado";

        try(Connection con=Conexion.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){

                Empleado emp=new Empleado();

                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombres(rs.getString("nombres"));
                emp.setDocumento(rs.getString("documento"));
                emp.setTelefono(rs.getString("telefono"));

                lista.add(emp);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR
    public Empleado buscar(int id){

        String sql="SELECT * FROM Empleado WHERE id_empleado=?";

        try(Connection con=Conexion.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                Empleado emp=new Empleado();

                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombres(rs.getString("nombres"));
                emp.setDocumento(rs.getString("documento"));
                emp.setTelefono(rs.getString("telefono"));

                return emp;

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Empleado emp){

        String sql="UPDATE Empleado SET nombres=?,documento=?,telefono=? WHERE id_empleado=?";

        try(Connection con=Conexion.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1, emp.getNombres());
            ps.setString(2, emp.getDocumento());
            ps.setString(3, emp.getTelefono());
            ps.setInt(4, emp.getIdEmpleado());

            return ps.executeUpdate()>0;

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // ELIMINAR
    public boolean eliminar(int id){

    	 String verificar =
    			    "SELECT COUNT(*) FROM Usuario WHERE id_empleado=?";


    			    String eliminar =
    			    "DELETE FROM Empleado WHERE id_empleado=?";


    			    try(Connection con = Conexion.getConnection()){


    			        PreparedStatement ps1 = con.prepareStatement(verificar);

    			        ps1.setInt(1,id);

    			        ResultSet rs = ps1.executeQuery();


    			        if(rs.next() && rs.getInt(1)>0){

    			            return false;
    			        }


    			        PreparedStatement ps2 = con.prepareStatement(eliminar);

    			        ps2.setInt(1,id);


    			        return ps2.executeUpdate()>0;



    			    }catch(SQLException e){

    			        e.printStackTrace();

    			    }


    			    return false;
    }
    
    public boolean existeDocumento(String documento) {

        String sql = "SELECT COUNT(*) FROM Empleado WHERE documento = ?";

        try (Connection cn = Conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, documento);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
