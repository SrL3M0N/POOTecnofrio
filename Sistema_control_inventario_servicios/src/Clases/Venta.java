package Clases;

public class Venta {
    private int idVenta;
    private int idCliente;
    private int idUsuario;
    private double subtotal;
    private double igv;
    private double total;
    private String numeroComprobante;
    private String tipoComprobante;
    
	public Venta() {
	}

	public Venta(int idVenta, int idCliente, int idUsuario, double subtotal, double igv, double total,
			String numeroComprobante, String tipoComprobante) {
		super();
		this.idVenta = idVenta;
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.subtotal = subtotal;
		this.igv = igv;
		this.total = total;
		this.numeroComprobante = numeroComprobante;
		this.tipoComprobante = tipoComprobante;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getIgv() {
		return igv;
	}

	public void setIgv(double igv) {
		this.igv = igv;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	
}
