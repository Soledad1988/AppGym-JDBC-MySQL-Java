package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import gym.modelo.Cuota;

public class CuotasDAO {

	final private Connection con;

	public CuotasDAO(Connection con) {
		this.con = con;
	}

	
	public void asignarCuota(Integer idCliente, Double monto, Date fechaPago) throws SQLException {
		String sql = "INSERT INTO cuotas (clienteId, monto, fechaPago) VALUES (?, ?, ?)";
		System.out.println("Consulta SQL: " + sql);
	    
	    try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    	stm.setInt(1, idCliente);
	    	stm.setDouble(2, monto);
	    	stm.setDate(3, fechaPago);
	        
	        stm.executeUpdate();
	      
	    }  catch (SQLException e) {
	        
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	 private void transformarResultSetEnCuota(List<Cuota> cuotas, PreparedStatement pstm) throws SQLException {
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Cuota cuota = new Cuota(rst.getInt(1), rst.getInt(2), rst.getDouble(3), rst.getDate(4));
					cuotas.add(cuota);
				}
			}				
		}
    
}
