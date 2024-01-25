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

@SuppressWarnings("serial")
public class MenuFrame extends JFrame {

	 private JPanel contentPane;
	 @SuppressWarnings("unused")
	private AltaClientesFrame listaClientesFrame;
	 @SuppressWarnings("unused")
	 private AltaGastosFrame altaGastosFrame;
	 @SuppressWarnings("unused")
	private ReporteClientes reporteClientes;
	 @SuppressWarnings("unused")
	private ReporteGastos reporteGastos;
	 private RegistroCuotas registroIngresos;
	 @SuppressWarnings("unused")
	private AltaUsuario altaUsuario;
	 @SuppressWarnings("unused")
	 private ReporteGananciaPerdida reporteGananciaPerdida;
	 
	 private JButton btnNuevoUsuario;
	 private JButton listaClientes;
	 private JButton btnGananciaPerdida;

	 
	 public MenuFrame() {
		 super("Gym-Fitness");
		}

	
	public MenuFrame(Set<Rol> roles) {
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
		botonNuevoClientes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonNuevoClientes.setBounds(31, 59, 168, 23);
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
		
		listaClientes = new JButton("Lista Clientes");
		listaClientes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listaClientes.setBounds(31, 195, 168, 23);
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
		reporteGastos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		reporteGastos.setBounds(31, 161, 168, 23);
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
		btnAltaGastos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAltaGastos.setBounds(31, 127, 168, 23);
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
		botonRegistroCuotas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonRegistroCuotas.setBounds(31, 93, 168, 23);
		contentPane.add(botonRegistroCuotas);
		
		btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.setBounds(32, 272, 168, 23);
		contentPane.add(btnNuevoUsuario);
		
		btnNuevoUsuario.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirNuevoUsuario();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
		
		JButton btnCerrarSesesio = new JButton("Cerrar Sesión");
		btnCerrarSesesio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCerrarSesesio.setBounds(392, 272, 138, 23);
		
		btnCerrarSesesio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	cerrarSesion();          	
            }
            
        });
		
		contentPane.add(btnCerrarSesesio);
		
		btnGananciaPerdida = new JButton("Reporte Ganancia/Perdida");
		btnGananciaPerdida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGananciaPerdida.setBounds(31, 229, 168, 23);
		contentPane.add(btnGananciaPerdida);
		
		btnGananciaPerdida.addActionListener(new ActionListener() {
	        	
	            public void actionPerformed(ActionEvent e) { 
	            	try {
	            		abrirReporteGananciaPerdida();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	            
	        });
		
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
	     habilitarBotonesSegunRoles(roles);
       
	}
	
    
	    private void cerrarSesion() {
	        dispose();
	        UsuarioFrame usuario = new UsuarioFrame();
	        usuario.setVisible(true);
	    }

		private void abrirNuevoCliente() throws SQLException {
	       listaClientesFrame = new AltaClientesFrame();
	    }
	   
	   private void abrirReporte() throws SQLException {
	       reporteClientes = new ReporteClientes();
	    }
	   
	   private void abrirReporteGananciaPerdida() throws SQLException {
		   reporteGananciaPerdida = new ReporteGananciaPerdida();
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
	   
	   private void abrirNuevoUsuario() throws SQLException {
	      altaUsuario = new AltaUsuario();
	    }
	  
	   
	   public void mostrarVentana() {
	        setVisible(true);
	    }
	  
	   
	   private void habilitarBotonesSegunRoles(Set<Rol> roles) {
		    System.out.println("Habilitando botones según roles"); // Mensaje de depuración

		    if (btnNuevoUsuario != null) {
		        if (roles.contains(Rol.ADMINISTRADOR)) {
		            // Habilitar botones de administrador
		            btnNuevoUsuario.setVisible(true);
		            listaClientes.setVisible(true);
		            btnGananciaPerdida.setVisible(true);
		        } else {
		            // Deshabilitar botones de administrador
		            btnNuevoUsuario.setVisible(false);
		            listaClientes.setVisible(false);
		            btnGananciaPerdida.setVisible(false);
		        }
		    } else {
		        System.out.println("btnNuevoUsuario es null"); // Mensaje de depuración
		    }

		}
}
