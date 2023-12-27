package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gym.modelo.Rol;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Button;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	private JButton botonNuevoClientes;
	private JButton btnNuevoUsuario;
    private JButton btnListarClientes;
	 
	 AltaClientesFrame listaClientesFrame;
	 AltaGastosFrame altaGastosFrame;
	 ReporteClientes reporteClientes;
	 ReporteGastos reporteGastos;
	 RegistroCuotas registroIngresos;
	
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
		super("Gym-Fitness");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo = new JLabel("Gym - FITNESS");
		titulo.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 26));
		titulo.setBounds(245, 11, 180, 40);
		contentPane.add(titulo);
		
		JButton botonNuevoClientes = new JButton("Nuevo Cliente");
		botonNuevoClientes.setBounds(22, 105, 146, 23);
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
		listaClientes.setBounds(107, 139, 138, 23);
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
		reporteGastos.setBounds(177, 183, 138, 23);
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
		
		JButton btnAltaGastos = new JButton("Registrar Gastos");
		btnAltaGastos.setBounds(22, 183, 145, 23);
		contentPane.add(btnAltaGastos);
		
		btnAltaGastos.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirAltaGastos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
	
		JButton botonRegistroCuotas = new JButton("Registro Cuotas");
		botonRegistroCuotas.setBounds(178, 105, 146, 23);
		contentPane.add(botonRegistroCuotas);
		
		JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.setBounds(22, 272, 145, 23);
		contentPane.add(btnNuevoUsuario);
		
		JButton btnCerrarSesesio = new JButton("Cerrar Sesión");
		btnCerrarSesesio.setBounds(392, 272, 138, 23);
		contentPane.add(btnCerrarSesesio);
		
		botonRegistroCuotas.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirCuotas();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });

		setLocationRelativeTo(null);
		
       
	}
	

	private void abrirNuevoCliente() throws SQLException {
	       listaClientesFrame = new AltaClientesFrame();
	    }
	   
	   private void abrirReporte() throws SQLException {
	       reporteClientes = new ReporteClientes();
	    }
	   
	   private void abrirReporteGastos() throws SQLException {
	       reporteGastos = new ReporteGastos();
	    }
	   
	   private void abrirAltaGastos() throws SQLException {
		   altaGastosFrame = new AltaGastosFrame();
	   }
	   
	   private void abrirCuotas() throws SQLException {
		   registroIngresos = new RegistroCuotas();
		   registroIngresos.setVisible(true);
	   }
	   
	   public void mostrarVentana() {
	        setVisible(true);
	    }
	   
	    public MenuFrame(Set<Rol> roles) {
	    	 btnNuevoUsuario = new JButton("Nuevo Usuario");
	         btnListarClientes = new JButton("Listar Clientes");
	        // Lógica para habilitar o deshabilitar botones según roles
	        if (roles.contains(Rol.ADMINISTRADOR)) {
	            btnNuevoUsuario.setEnabled(true);
	            btnListarClientes.setEnabled(true);
	        } else {
	            btnNuevoUsuario.setEnabled(false);
	            btnListarClientes.setEnabled(false);
	        }
	    }
}
