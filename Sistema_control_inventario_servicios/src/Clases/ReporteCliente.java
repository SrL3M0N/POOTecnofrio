package Clases;

import java.util.Date;

public class ReporteCliente {


    private int idCliente;
    private String cliente;
    private String documento;
    private String telefono;
    private String email;
    private int cantidadVentas;
    private double totalComprado;
    private Date ultimaCompra;
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCantidadVentas() {
		return cantidadVentas;
	}
	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}
	public double getTotalComprado() {
		return totalComprado;
	}
	public void setTotalComprado(double totalComprado) {
		this.totalComprado = totalComprado;
	}
	public Date getUltimaCompra() {
		return ultimaCompra;
	}
	public void setUltimaCompra(Date ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}
    
    
	
}
