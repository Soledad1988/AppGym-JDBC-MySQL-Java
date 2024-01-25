package controller;

import java.awt.EventQueue;
import java.sql.SQLException;
import vista.UsuarioFrame;

public class GymMein {

	public static void main(String[] args) throws SQLException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioFrame frame = new UsuarioFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		
	}
}
