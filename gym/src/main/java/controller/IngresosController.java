package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import conexion.Conexion;
import dao.IngresosDAO;
import gym.modelo.Cliente;

public class IngresosController {
	
	private IngresosDAO ingresoDAO;
    
    public IngresosController() throws SQLException {
        Connection connection = new Conexion().recuperaConexion();
        this.ingresoDAO = new IngresosDAO(connection);
    }
    
    public List<Cliente> buscarPorApellido(String apellido) throws SQLException {
        return this.ingresoDAO.buscarPorApellido(apellido);
    }
    
    public void asignarPagos(int idCliente, double montoPago, String periodoPago) throws SQLException {
        ingresoDAO.asignarPago(idCliente, montoPago, periodoPago);
    }
}
