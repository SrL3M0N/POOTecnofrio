package Clases;

public class Sesion {
	
    public static int idUsuario;
    public static int idEmpleado;
    public static int idRol;

    public static String usuario;
    public static String nombreEmpleado;
    public static String rol;

    
	public Sesion() {

	}


	public static int getIdUsuario() {
		return idUsuario;
	}


	public static void setIdUsuario(int idUsuario) {
		Sesion.idUsuario = idUsuario;
	}


	public static int getIdEmpleado() {
		return idEmpleado;
	}


	public static void setIdEmpleado(int idEmpleado) {
		Sesion.idEmpleado = idEmpleado;
	}


	public static int getIdRol() {
		return idRol;
	}


	public static void setIdRol(int idRol) {
		Sesion.idRol = idRol;
	}


	public static String getUsuario() {
		return usuario;
	}


	public static void setUsuario(String usuario) {
		Sesion.usuario = usuario;
	}


	public static String getNombreEmpleado() {
		return nombreEmpleado;
	}


	public static void setNombreEmpleado(String nombreEmpleado) {
		Sesion.nombreEmpleado = nombreEmpleado;
	}


	public static String getRol() {
		return rol;
	}


	public static void setRol(String rol) {
		Sesion.rol = rol;
	}
 
}
