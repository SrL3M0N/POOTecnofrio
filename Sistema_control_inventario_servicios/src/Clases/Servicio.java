package Clases;

public class Servicio {
	private int idItem;
    private String nombre;
    private String descripcion;
    private double precioVenta;
    
    // Atributos de Servicio
    private int idEmpleado;
    private String nombreEmpleado; // Nuevo campo
    private int duracionMin;
    private String descAgenda;
    private boolean activo;
       
	public Servicio() {
	}

	public Servicio(int idItem, String nombre, String descripcion, double precioVenta, int idEmpleado,
			String nombreEmpleado, int duracionMin, String descAgenda, boolean activo) {
		this.idItem = idItem;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioVenta = precioVenta;
		this.idEmpleado = idEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.duracionMin = duracionMin;
		this.descAgenda = descAgenda;
		this.activo = activo;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public int getDuracionMin() {
		return duracionMin;
	}

	public void setDuracionMin(int duracionMin) {
		this.duracionMin = duracionMin;
	}

	public String getDescAgenda() {
		return descAgenda;
	}

	public void setDescAgenda(String descAgenda) {
		this.descAgenda = descAgenda;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
    
    


    
    

}
