package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import gym.modelo.Gastos;



public class GastoDAO {

	final private Connection con;

	public GastoDAO(Connection con) {
		this.con = con;
	}
	
	//guardar
    public void guardar(Gastos gastos) {
		try {
			String sql = "INSERT INTO gastos (periodoGasto, nombreGasto, tipo, costo) VALUES (?, ?, ?, ?)";
			try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stm.setString(1, gastos.getPeriodoGasto());
				stm.setString(2, gastos.getNombreGasto());
				stm.setString(3, gastos.getTipo());
				stm.setDouble(4, gastos.getCosto());
				stm.execute();
				try (ResultSet rst = stm.getGeneratedKeys()) {
					while (rst.next()) {
						gastos.setIdGasto(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    public void eliminar(Integer idGasto) {
    	String sql = "DELETE FROM gastos WHERE idGasto = ?";
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			stm.setInt(1, idGasto);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    public void actualizar(String nombreGasto, String tipo, Double costo, Integer idGasto) {
        String sql = "UPDATE gastos SET nombreGasto = ?, tipo = ?, costo = ? WHERE idGasto = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, nombreGasto);
            stm.setString(2, tipo);
            stm.setDouble(3, costo);
            stm.setInt(4, idGasto);

            int rowsUpdated = stm.executeUpdate();
            System.out.println(rowsUpdated + " fila(s) modificadas.");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    public List<Gastos> listar() {
		List<Gastos> gasto = new ArrayList<Gastos>();
		try {
			String sql = "SELECT idGasto, periodoGasto, nombreGasto, tipo, costo FROM gastos";

			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnCliente(gasto, pstm);
			}
			return gasto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
  		
    
    private void transformarResultSetEnCliente(List<Gastos> gasto, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Gastos gastos = new Gastos(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
						rst.getDouble(5));
				gasto.add(gastos);
			}
		}				
	}
}
