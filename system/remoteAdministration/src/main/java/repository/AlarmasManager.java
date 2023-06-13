package repository;

import java.util.Date;

import com.zeroc.Ice.Communicator;

import model.AlarmaMaquina;
import model.ConexionBD;

public class AlarmasManager {

    private Communicator comunicator;

    public AlarmasManager(Communicator communicator) {
        this.comunicator = communicator;
    }

    /**
     * @param idAlarma alarm type
     */
    public int alarmaMaquina(int idAlarma, int idMaquina, Date fechainicial) {
        ConexionBD cbd = new ConexionBD(comunicator);
        cbd.conectarBaseDatos();
        ManejadorDatos md = new ManejadorDatos();
        md.setConexion(cbd.getConnection());
        int concecutivo = 0;
        AlarmaMaquina aM = new AlarmaMaquina(idAlarma, idMaquina, fechainicial);
        concecutivo = md.registrarAlarma(aM); // insert en alarma_maquina
        cbd.cerrarConexion();
        return concecutivo;
    }

    public void desactivarAlarma(int idAlarma, int idMaquina, Date fechaFinal) {
        ConexionBD cbd = new ConexionBD(comunicator);
        cbd.conectarBaseDatos();
        ManejadorDatos md = new ManejadorDatos();
        md.setConexion(cbd.getConnection());
        md.desactivarAlarma(idMaquina, idAlarma, fechaFinal);
        cbd.cerrarConexion();
    }

}
