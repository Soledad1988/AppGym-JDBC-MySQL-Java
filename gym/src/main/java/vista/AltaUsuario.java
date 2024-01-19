package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.UsuarioController;
import gym.modelo.Cliente;
import gym.modelo.Rol;
import gym.modelo.Usuario;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;


public class AltaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelRol;
    private JTextField textoNombreUsuario, textoRol;
    private JButton botonGuardar, botonModificar, botonEliminar, botonMenu, botonReporteUsuario;
    private DefaultTableModel modelo;
    private UsuarioController usuarioController;
    private JTable tabla;
    private JPasswordField textoPassword;
    private JComboBox listaRoles;
    private ReporteUsuarioFrame inicioSesion;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaUsuario frame = new AltaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public AltaUsuario() throws SQLException {
        super("Alta Nuevo Usuario");

        this.usuarioController = new UsuarioController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        JLabel lblTitulo = new JLabel("Alta Nuevo Usuario");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(89, 23, 213, 30);
        getContentPane().add(lblTitulo);
        
        JLabel lblUsuariosGenerados = new JLabel("Usuarios Generados");
        lblUsuariosGenerados.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblUsuariosGenerados.setBounds(89, 202, 213, 30);
        getContentPane().add(lblUsuariosGenerados);
        
        tabla = new JTable(modelo);
        
       // tabla = new JTable();
        tabla.setBounds(10, 253, 390, 117);
        getContentPane().add(tabla);
        
        textoPassword = new JPasswordField();
        textoPassword.setBounds(183, 127, 130, 20);
        getContentPane().add(textoPassword);
        
        JButton botonReporteUsuario = new JButton("Reporte");
        botonReporteUsuario.setBounds(211, 411, 93, 20);
        getContentPane().add(botonReporteUsuario);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
    	
    	tabla = new JTable();

        modelo = new DefaultTableModel();
        modelo.addColumn("IdUsuario");
        modelo.addColumn("Nombre Usuario");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Rol Asigando");

        tabla.setModel(modelo); 
               
        cargarTabla();

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonMenu = new JButton("Menú");
        botonReporteUsuario = new JButton("Reporte");
        
        botonEliminar.setBounds(267, 381, 98, 20);
        botonModificar.setBounds(158, 381, 93, 20);
        botonMenu.setBounds(89, 412, 93, 20);
        botonReporteUsuario.setBounds(211, 412, 93, 20);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonMenu);
        container.add(botonReporteUsuario);
        
        JScrollPane scrollPane = new JScrollPane(tabla); 
        scrollPane.setBounds(10, 243, 390, 127);
        container.add(scrollPane); 


        setSize(424, 499);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre Usuario");
        labelApellido = new JLabel("Password");
        labelRol = new JLabel("Rol Asignado");

        labelNombre.setBounds(36, 84, 118, 15);
        labelApellido.setBounds(36, 130, 118, 15);
        labelRol.setBounds(36, 176, 112, 15);
        
        labelNombre.setForeground(Color.BLACK);
        labelApellido.setForeground(Color.BLACK);
        labelRol.setForeground(Color.BLACK);

        textoNombreUsuario = new JTextField();
        textoRol = new JTextField();

        // TODO
        
        textoNombreUsuario.setBounds(183, 82, 130, 20);
       // textoRol.setBounds(183, 171, 130, 20);
        
       
        listaRoles = new JComboBox<>();
        listaRoles.setBounds(183, 171, 130, 20);
        container.add(listaRoles);

        botonGuardar = new JButton("Guardar");
        botonGuardar.setBounds(32, 381, 99, 20);

        container.add(labelNombre);
        container.add(labelApellido);
        container.add(labelRol);
        container.add(textoNombreUsuario);
        container.add(textoRol);
        container.add(botonGuardar);
       
        
    }

    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				guardar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminar();
                limpiarTabla();
                cargarTabla();
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	actualizar();
            }
            
        });
        
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	volverMenu();          	
            }
            
        });
        
        botonReporteUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
					try {
						reporteInicioSesion();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				         	
            }
            
        });
        
        cargarRoles();

    }

    
    private void volverMenu() {
        dispose();

        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);        
    }
    
    
    private void reporteInicioSesion() throws SQLException {
    	inicioSesion = new ReporteUsuarioFrame();
    	inicioSesion.setVisible(true); 
	    }


    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }
    
    private void actualizar() {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Integer idUsuario = Integer.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString());
        String nombreUsuario = (String) modelo.getValueAt(filaSeleccionada, 1);
        String password = (String) modelo.getValueAt(filaSeleccionada, 2); // Ahora obteniendo la contraseña de la tabla

        this.usuarioController.actualizar(nombreUsuario, password, idUsuario);

        JOptionPane.showMessageDialog(this, "Registro modificado con éxito");
    }
 
    
    private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer idUsuario = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
                    this.usuarioController.eliminar(idUsuario);	
            
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, " Item eliminado con éxito!");
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
    
     
   private List<Usuario> ListarUsuarios() {
		return this.usuarioController.listar();
   }
  
   private void cargarTabla() {
	    List<Usuario> usuarios = ListarUsuarios();
	    try {
	        for (Usuario usuario : usuarios) {
	            modelo.addRow(new Object[] { 
	                usuario.getIdUsuario(), 
	                usuario.getNombreUsuario(), 
	                usuario.getContrasena(),
	                usuario.getRolesAsString() // Usar getRolesAsString()
	            });
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}
     
   private void cargarRoles() {
	    // Obtener los valores del enum Rol
	    Rol[] roles = Rol.values();

	    // Crear un array de nombres de roles
	    String[] nombresRoles = new String[roles.length];
	    for (int i = 0; i < roles.length; i++) {
	        nombresRoles[i] = roles[i].toString();
	    }

	    // Configurar el modelo de la lista desplegable
	    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombresRoles);
	    listaRoles.setModel(comboBoxModel);
	}
   
   private void guardar() {
	    try {
	        String nombreUsuario = textoNombreUsuario.getText();
	        String password = textoPassword.getText();
	        String rolSeleccionado = (String) listaRoles.getSelectedItem(); // Obtener el rol seleccionado

	        if (nombreUsuario.isEmpty() || password.isEmpty() || rolSeleccionado == null) {
	            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
	            return;
	        }

	        // Asegúrate de que el valor del rol coincida con los valores del enum Rol
	        Rol rol;
	        try {
	            rol = Rol.valueOf(rolSeleccionado.replace(" ", "_").toUpperCase());
	        } catch (IllegalArgumentException e) {
	            JOptionPane.showMessageDialog(this, "Rol seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
	            return; // No continuar si el rol no es válido
	        }

	        Usuario usuario = new Usuario(nombreUsuario, password);
	        usuario.addRol(rol);

	        this.usuarioController.guardar(usuario, rolSeleccionado);
	        JOptionPane.showMessageDialog(this, "Registrado con éxito!");
	        limpiarFormulario();
	    } catch (RuntimeException ex) {
	        JOptionPane.showMessageDialog(this, "Error al guardar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace(); // Puedes ajustar el manejo de la excepción según tus necesidades
	    }
	}
   
   
    private void limpiarFormulario() {
        this.textoNombreUsuario.setText("");
        this.textoPassword.setText("");
        this.textoRol.setText("");
    }
}
