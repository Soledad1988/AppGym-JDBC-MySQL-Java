package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gym.modelo.Rol;
import gym.modelo.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Button;
import java.awt.EventQueue;

public class MenuFrame extends JFrame {

	 private JPanel contentPane;
	 private AltaClientesFrame listaClientesFrame;
	 private AltaGastosFrame altaGastosFrame;
	 private ReporteClientes reporteClientes;
	 private ReporteGastos reporteGastos;
	 private RegistroCuotas registroIngresos;
	 private AltaUsuario altaUsuario;
	 private UsuarioFrame usuario;
	 
	 private JButton btnNuevoUsuario;
	 private JButton listaClientes;
	
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame menuFrame = new MenuFrame();
					menuFrame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	 
	 public MenuFrame() {
		 super("Gym-Fitness");
	       // initComponents();
	       //setLocationRelativeTo(null);
		}

	
	public MenuFrame(Set<Rol> roles) {
		super("Gym-Fitness");
		
		//initComponents();
        setLocationRelativeTo(null);
      //  habilitarBotonesSegunRoles(roles);
		
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
		
		listaClientes = new JButton("Lista Clientes");
		//JButton listaClientes = new JButton("Lista Clientes");
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
		
		btnNuevoUsuario = new JButton("Nuevo Usuario");
		//JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.setBounds(22, 272, 145, 23);
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
		btnCerrarSesesio.setBounds(392, 272, 138, 23);
		
		btnCerrarSesesio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	cerrarSesion();          	
            }
            
        });
		
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
		
		habilitarBotonesSegunRoles(roles);
	
       
	}
	
	/* private void initComponents() {
	        // Aquí colocas la lógica de inicialización común para ambos constructores
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 647, 375);
	        // ... otros componentes y configuraciones
	    }*/
	
    
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
		        } else {
		            // Deshabilitar botones de administrador
		            btnNuevoUsuario.setVisible(false);
		            listaClientes.setVisible(false);
		        }
		    } else {
		        System.out.println("btnNuevoUsuario es null"); // Mensaje de depuración
		    }

		    // ...
		}
	   
}
