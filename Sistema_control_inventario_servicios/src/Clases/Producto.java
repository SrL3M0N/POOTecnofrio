package Clases;

public class Producto {

	// 1. Atributos de la tabla 'producto' (Específicos)
    private int idItem;          // PK (de la tabla Item) y FK
    private int stock;
    private String codigoBarras;
    private double costo;        
    private int idCategoria;     // FK
    private int idProveedor;     // FK
    
    // 2. Atributos de la tabla 'Item' (Herencia)
    private String nombre;       
    private String descripcion;  // ¡Faltaba este!
    private double precioVenta;  // ¡Faltaba este!
    private String tipo;         // 'PRODUCTO' o 'SERVICIO'
    
    // 3. Atributos auxiliares para mostrar en la JTable (Joins)
    private String nombreCategoria; 
    private String nombreProveedor;
	
	
	public Producto() {
	}


	public int getIdItem() {
		return idItem;
	}


	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public String getCodigoBarras() {
		return codigoBarras;
	}


	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public int getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}


	public int getIdProveedor() {
		return idProveedor;
	}


	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
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


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNombreCategoria() {
		return nombreCategoria;
	}


	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}


	public String getNombreProveedor() {
		return nombreProveedor;
	}


	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	@Override
	public String toString() {
	    return nombre;
	}
	
}
