package modelo;

import java.util.Date;
import java.util.List;

public class Ventas_Maquina {

	private int consecutivo;
	private int idMaquina;
	private Date fechaInicial;
	private Date fechaFinal;
	private List<Ventas_Receta> detalle;
	private double valor;
	
	public Ventas_Maquina() {
		super();
	}

	public Ventas_Maquina(int consecutivo, int idMaquina, Date fechaInicial,
			Date fechaFinal, List<Ventas_Receta>detalle, double valor) {
		super();
		this.consecutivo = consecutivo;
		this.idMaquina = idMaquina;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.detalle=detalle;
		this.valor=valor;
	}

	public int getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public int getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(int idMaquina) {
		this.idMaquina = idMaquina;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public List<Ventas_Receta> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Ventas_Receta> detalle) {
		this.detalle = detalle;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
}
