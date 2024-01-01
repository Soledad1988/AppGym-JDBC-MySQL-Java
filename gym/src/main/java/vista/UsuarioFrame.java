package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;
import controller.UsuarioController;
import dao.UsuarioDAO;
import gym.modelo.Usuario;
import gym.modelo.Rol;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class UsuarioFrame extends JFrame {

	private JPanel contentPane;
	MenuFrame menu;
	 private JTextField textUsuario;
	 private JLabel lblMensaje;
	 private JPasswordField textPassword;
	 private UsuarioDAO usuarioDAO;
	 private Set<Rol> rolesUsuario;
	 private Usuario usuarioLogueado;
	
	public static void main(String[] args) {
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

	
	public UsuarioFrame() {
		super("Gym-Fitness");
		
		 try {
	            Connection connection = new Conexion().recuperaConexion();
	            this.usuarioDAO = new UsuarioDAO(connection);
	        } catch (SQLException e) {
	            e.printStackTrace();  // Manejo adecuado de la excepción según tus necesidades
	        }
		
		
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
		
		JButton IniciarSesion = new JButton("Iniciar Sesión");
		IniciarSesion.setBounds(71, 224, 138, 23);
		contentPane.add(IniciarSesion);
		
		IniciarSesion.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) { 
            	try {
            		abrirIniciarSesion();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });
		
		JLabel logo = new JLabel("New label");
		logo.setIcon(new ImageIcon("C:\\Users\\brent\\eclipse-workspace\\gym.png"));
		logo.setBounds(325, 84, 274, 243);
		contentPane.add(logo);
		
		JLabel lblBienvenidoAlSistema = new JLabel("Bienvenido al sistema de registros");
		lblBienvenidoAlSistema.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 26));
		lblBienvenidoAlSistema.setBounds(138, 54, 365, 40);
		contentPane.add(lblBienvenidoAlSistema);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(42, 122, 75, 17);
		contentPane.add(lblUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(127, 122, 96, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(42, 165, 75, 17);
		contentPane.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(127, 164, 96, 20);
		contentPane.add(textPassword);

		setLocationRelativeTo(null);
		
		   lblMensaje = new JLabel("");
		    lblMensaje.setForeground(Color.RED);
		    lblMensaje.setBounds(42, 200, 200, 20);

		    if (contentPane != null) {
		        contentPane.add(lblMensaje);
		    }
       
	}
	
	   
	   private void abrirIniciarSesion2() throws SQLException {
		    String usuario = textUsuario.getText();
		    String password = textPassword.getText();

		    List<Usuario> usuarios = new UsuarioController().buscar(usuario, password);

		    if (usuarios.isEmpty()) {
		        lblMensaje.setText("Usuario o contraseña incorrectos");
		    } else {
		        // Autenticación exitosa, puedes abrir la siguiente ventana aquí
		        //menu = new MenuFrame();
		        menu.mostrarVentana();  // Asegúrate de tener un método similar en tu MenuFrame
		        setVisible(false);  // Oculta la ventana actual
		    }
		}
	   
	   public void mostrarVentana() {
	        setVisible(true);
	    }
	   
	  /* private void abrirMenuFrame(Set<Rol> roles) {
		    MenuFrame menuFrame = new MenuFrame(roles);
		    menuFrame.setVisible(true);
		    // Resto del código para cerrar la ventana actual, etc.
		}
	   
	   private void abrirMenuFrame2() {
		    MenuFrame menuFrame = new MenuFrame();
		    menuFrame.setVisible(true);
		    // Resto del código para cerrar la ventana actual, etc.
		}*/
	   
	   private void abrirMenuFrame() {
		    if (usuarioLogueado != null) {
		        // Obtener roles del usuario desde la base de datos
		        Set<Rol> roles = obtenerRolesDesdeBaseDeDatos(usuarioLogueado.getIdUsuario());

		        // Pasar los roles a MenuFrame
		        menu = new MenuFrame(roles);
		        menu.mostrarVentana();
		        setVisible(false);
		    } else {
		        // Manejar el caso en el que usuarioLogueado es null
		        System.out.println("El usuarioLogueado es null");
		    }
		}

	   
	   private void abrirIniciarSesion() throws SQLException {
		    String usuario = textUsuario.getText();
		    String password = textPassword.getText();

		    List<Usuario> usuarios = new UsuarioController().buscar(usuario, password);

		    if (!usuarios.isEmpty()) {
		        usuarioLogueado = usuarios.get(0);  // Utiliza la variable de instancia en lugar de declarar una nueva
		        // Obtener roles del usuario desde la base de datos
		        Set<Rol> roles = obtenerRolesDesdeBaseDeDatos(usuarioLogueado.getIdUsuario());

		        // Habilitar o deshabilitar botones según roles
		        habilitarBotonesSegunRoles(roles);

		        // Abrir MenuFrame y pasar roles
		        abrirMenuFrame();

		    } else {
		        lblMensaje.setText("Usuario o contraseña incorrectos");
		    }
		}
	   
	   private void habilitarBotonesSegunRoles(Set<Rol> roles) {
		   
		    // Lógica para habilitar o deshabilitar botones según roles
		    if (roles.contains(Rol.ADMINISTRADOR)) {
		        // Habilitar botones de administrador
		    	
		    } else {
		        // Deshabilitar botones de administrador
		        // btnAgregarUsuario.setEnabled(false);
		        // btnEliminarUsuario.setEnabled(false);
		    }

		    // Lógica común para roles de usuario regular
		    // btnVerPerfil.setEnabled(true);
		    // ...
		}
		   
		   private Set<Rol> obtenerRolesDesdeBaseDeDatos(Integer idUsuario) {
		        try {
		            return usuarioDAO.obtenerRolesDesdeBaseDeDatos(idUsuario);
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return Collections.emptySet();
		        }
		    }

		   
}		    