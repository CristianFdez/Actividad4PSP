package asimetrica;

import java.io.Serializable;

public class Coche implements Serializable{
	// Esto se pone por el Serializable
	private static final long serialVersionUID = 1L;
	private String matricula, marca, modelo;
	private double precio;
	public Coche(String matricula, String marca, String modelo, double precio) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
	}
	public Coche() {
		super();
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "Coche [matricula=" + matricula + ", marca=" + marca 
				+ ", modelo=" + modelo + ", precio=" + precio + "]";
	}
	
}
