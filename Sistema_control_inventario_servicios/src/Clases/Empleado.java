package Clases;

public class Empleado {
	
	private int idEmpleado;
    private String nombres;
    private String documento;
    private String email;
    private String telefono;
  
   
    
	public Empleado() {
		
	}

	public Empleado(int idEmpleado, String nombres, String documento, String email, String telefono, int idTipoEmpleado,
			int idDisponibilidad, String nombreTipo, String nombreDisp) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombres = nombres;
		this.documento = documento;
		this.email = email;
		this.telefono = telefono;

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

}
