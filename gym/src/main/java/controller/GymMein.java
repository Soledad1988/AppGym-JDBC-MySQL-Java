package controller;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;

import vista.AltaClientesFrame;
import vista.MenuFrame;

public class GymMein {

	public static void main(String[] args) throws SQLException {
		
		/*ListaClientesFrame listaClientes = new ListaClientesFrame();
		listaClientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame frame = new MenuFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		
	}
}
