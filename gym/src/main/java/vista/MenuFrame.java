package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	 private JButton botonNuevoClientes;
	 
	 ListaClientesFrame listaClientesFrame;
	 ReporteClientes reporteClientes;
	 ReporteGastos reporteGastos;
	
	/*public static void main(String[] args) {
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
	}*/

	
	public MenuFrame() {
		super("Gym-Fittnes");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo = new JLabel("Gym - FITTNES");
		titulo.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 26));
		titulo.setBounds(245, 11, 180, 40);
		contentPane.add(titulo);
		
		JButton botonNuevoClientes = new JButton("Nuevo Cliente");
		botonNuevoClientes.setBounds(91, 102, 115, 23);
		contentPane.add(botonNuevoClientes);
		
		botonNuevoClientes.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
					abrirNuevoCliente();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
		
		JButton listaClientes = new JButton("Lista Clientes");
		listaClientes.setBounds(91, 149, 115, 23);
		contentPane.add(listaClientes);
		
		listaClientes.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirReporte();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
		
		JButton reporteGastos = new JButton("Reporte Gastos");
		reporteGastos.setBounds(91, 200, 115, 23);
		contentPane.add(reporteGastos);
		
		reporteGastos.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirReporteGastos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
		
		JLabel logo = new JLabel("New label");
		logo.setIcon(new ImageIcon("C:\\Users\\brent\\eclipse-workspace\\gym.png"));
		logo.setBounds(326, 34, 274, 243);
		contentPane.add(logo);

		setLocationRelativeTo(null);
		
       
	}
	
	   private void abrirNuevoCliente() throws SQLException {
	       listaClientesFrame = new ListaClientesFrame();
	    }
	   
	   private void abrirReporte() throws SQLException {
	       reporteClientes = new ReporteClientes();
	    }
	   
	   private void abrirReporteGastos() throws SQLException {
	       reporteGastos = new ReporteGastos();
	    }
}
