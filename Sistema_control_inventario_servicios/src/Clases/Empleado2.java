package Clases;

public class Empleado2 {
	
	private int idEmpleado;
    private String nombres;
    private String documento;
    private String email;
    private String telefono;
    private int idTipoEmpleado;
    private int idDisponibilidad;
    
    private String nombreTipo; // Nuevo: para mostrar "Técnico" en vez de ID 1
    private String nombreDisp; // Nuevo: para mostrar "Disponible" en vez de ID 2
    
	public Empleado2() {
		
	}

	public Empleado2(int idEmpleado, String nombres, String documento, String email, String telefono, int idTipoEmpleado,
			int idDisponibilidad, String nombreTipo, String nombreDisp) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombres = nombres;
		this.documento = documento;
		this.email = email;
		this.telefono = telefono;
		this.idTipoEmpleado = idTipoEmpleado;
		this.idDisponibilidad = idDisponibilidad;
		this.nombreTipo = nombreTipo;
		this.nombreDisp = nombreDisp;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}


	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getIdTipoEmpleado() {
		return idTipoEmpleado;
	}

	public void setIdTipoEmpleado(int idTipoEmpleado) {
		this.idTipoEmpleado = idTipoEmpleado;
	}

	public int getIdDisponibilidad() {
		return idDisponibilidad;
	}

	public void setIdDisponibilidad(int idDisponibilidad) {
		this.idDisponibilidad = idDisponibilidad;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	public String getNombreDisp() {
		return nombreDisp;
	}
	public void setNombreDisp(String nombreDisp) {
		this.nombreDisp = nombreDisp;
	}

	@Override
	public String toString() {
	    return nombres;
	}
}
