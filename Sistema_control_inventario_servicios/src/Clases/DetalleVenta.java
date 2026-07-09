package Clases;

public class DetalleVenta {

    private int idItem;
    private int cantidad;
    private double precioUnitario;
    
	public DetalleVenta() {
		super();
	}

	public DetalleVenta(int idItem, int cantidad, double precioUnitario) {
		super();
		this.idItem = idItem;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

    
    
}
