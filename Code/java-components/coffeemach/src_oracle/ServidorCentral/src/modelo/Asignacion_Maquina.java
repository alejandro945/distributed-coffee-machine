package modelo;

public class Asignacion_Maquina {

	private Maquina maquina;
	private Operador operador;
		
	public Asignacion_Maquina() {
		super();
	}

	public Asignacion_Maquina(Maquina maquina, Operador operador) {
		super();
		this.maquina = maquina;
		this.operador = operador;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}
	
	
}
