package modelo;

import java.util.Date;

public class Alarma_Maquina {

	private int Id_Alarma;
	private int Id_Maquina;
	private Date fecha_Inicial_Alarma;
	
	public Alarma_Maquina(int id_Alarma, int id_Maquina,
			Date fecha_Inicial_Alarma) {
		super();
		Id_Alarma = id_Alarma;
		Id_Maquina = id_Maquina;
		this.fecha_Inicial_Alarma = fecha_Inicial_Alarma;
	}

	public Alarma_Maquina() {
		super();
	}

	public int getId_Alarma() {
		return Id_Alarma;
	}

	public void setId_Alarma(int id_Alarma) {
		Id_Alarma = id_Alarma;
	}

	public int getId_Maquina() {
		return Id_Maquina;
	}

	public void setId_Maquina(int id_Maquina) {
		Id_Maquina = id_Maquina;
	}

	public Date getFecha_Inicial_Alarma() {
		return fecha_Inicial_Alarma;
	}

	public void setFecha_Inicial_Alarma(Date fecha_Inicial_Alarma) {
		this.fecha_Inicial_Alarma = fecha_Inicial_Alarma;
	}
	
	
	
}
