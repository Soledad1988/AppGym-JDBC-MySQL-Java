package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.UsuarioController;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.List;
import gym.modelo.RegistroLogin;

public class ReporteUsuarioFrame extends JFrame{

	 	private JTable table;
	    private DefaultTableModel tableModel;
	    private UsuarioController dao;

	    public ReporteUsuarioFrame() throws SQLException {
	        this.dao = new UsuarioController();
	        setTitle("Informe de Inicios de Sesión");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        initUI();
	        setLocationRelativeTo(null); // Para centrar la ventana
	    }

	    private void initUI() {
	        tableModel = new DefaultTableModel();
	        tableModel.addColumn("Nombre Usuario");
	        tableModel.addColumn("Fecha y Hora");

	        table = new JTable(tableModel);
	        cargarDatos();

	        add(new JScrollPane(table));
	    }

	    private void cargarDatos() {
	        List<RegistroLogin> registros = dao.obtenerRegistrosLogin();

	        for (RegistroLogin registro : registros) {
	            tableModel.addRow(new Object[]{
	            		registro.getNombreUsuario(), 
	            		registro.getFechaHora()});
	        }
	    }

	    
	    public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ReporteUsuarioFrame sesionFrame = new ReporteUsuarioFrame();
						sesionFrame.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
}
