package controller;

import java.sql.SQLException;

import javax.swing.JFrame;

import vista.ListaClientesFrame;

public class GymMein {

	public static void main(String[] args) throws SQLException {
		
		ListaClientesFrame listaClientes = new ListaClientesFrame();
		listaClientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
