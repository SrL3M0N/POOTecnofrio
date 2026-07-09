package Clases;

public class Item {
	
    private int idItem;
    private String nombre;
    private int stock;
    private double precio;
    private String tipo;
    
	public Item() {
	}

	public Item(int idItem, String nombre, int stock, double precio, String tipo) {
		super();
		this.idItem = idItem;
		this.nombre = nombre;
		this.stock = stock;
		this.precio = precio;
		this.tipo = tipo;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
