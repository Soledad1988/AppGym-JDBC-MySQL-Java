package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexion.Conexion;
import dao.ClienteDAO;

public class ReporteController {
	
	private ClienteDAO clienteDAO;
	
	//listar reporte
		public List<Map<String,String>> reporte() throws SQLException {
			Conexion factory = new Conexion();
			final Connection con = factory.recuperaConexion();
			  
				final PreparedStatement statement = con.prepareStatement("SELECT fechaAlta, nombre, apellido, precio FROM clientes");
		        
				statement.execute();

		        ResultSet resultSet = statement.getResultSet();

		        List<Map<String, String>> resultado = new ArrayList<>();

		      //leemos el contendio para agregrlo a un listado
		        while (resultSet.next()) {
		            Map<String, String> fila = new HashMap<>();
		            fila.put("fechaAlta", String.valueOf(resultSet.getDate("fechaAlta")));
		            fila.put("nombre", resultSet.getString("nombre"));
		            fila.put("apellido", resultSet.getString("apellido"));
		            fila.put("precio", String.valueOf(resultSet.getDouble("precio")));

		            resultado.add(fila);
		        }
				
			return resultado;
		
		}

}
