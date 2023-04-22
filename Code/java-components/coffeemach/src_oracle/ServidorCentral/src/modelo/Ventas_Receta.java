package modelo;

public class Ventas_Receta {

	private int idReceta;
	private int consecutivoVenta;
	private double valor_receta;
	
	public Ventas_Receta() {
		super();
	}

	public Ventas_Receta(int idReceta, int consecutivoVenta, double valor_receta) {
		super();
		this.idReceta = idReceta;
		this.consecutivoVenta = consecutivoVenta;
	}

	public int getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(int idReceta) {
		this.idReceta = idReceta;
	}

	public int getConsecutivoVenta() {
		return consecutivoVenta;
	}

	public void setConsecutivoVenta(int consecutivoVenta) {
		this.consecutivoVenta = consecutivoVenta;
	}

	public double getValor_receta() {
		return valor_receta;
	}

	public void setValor_receta(double valor_receta) {
		this.valor_receta = valor_receta;
	}
	
	
	
}
