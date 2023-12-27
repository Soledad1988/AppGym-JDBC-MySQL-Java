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


public class AltaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelRol;
    private JTextField textoNombreUsuario, textoRol;
    private JButton botonGuardar, botonModificar, botonEliminar, botonMenu;
    private DefaultTableModel modelo;
    private UsuarioController usuarioController;
    private JTable tabla;
    private JPasswordField textoPassword;
    private JComboBox listaRoles;
    
    
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
        lblUsuariosGenerados.setBounds(89, 262, 213, 30);
        getContentPane().add(lblUsuariosGenerados);
        
        tabla = new JTable(modelo);
        
       // tabla = new JTable();
        tabla.setBounds(10, 303, 390, 117);
        getContentPane().add(tabla);
        
        textoPassword = new JPasswordField();
        textoPassword.setBounds(183, 127, 130, 20);
        getContentPane().add(textoPassword);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
    	
    	modelo = new DefaultTableModel(new Object[][]{}, new String[] { "IdUsuario", "Nombre Usuario", "Rol Asigando" });

    	/*modelo = new DefaultTableModel();
    	modelo.addColumn("IdUsuario");
    	modelo.addColumn("Nombre Usuario");
    	modelo.addColumn("Password");*/

               
        cargarTabla();

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonMenu = new JButton("Menú");
        
        botonEliminar.setBounds(264, 219, 98, 20);
        botonModificar.setBounds(155, 219, 93, 20);
        botonMenu.setBounds(155, 431, 98, 20);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonMenu);

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
        botonGuardar.setBounds(29, 219, 99, 20);

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
        
        cargarRoles();

    }

    
    private void volverMenu() {
        // Cerrar la ventana actual
        dispose();

        // Crear y mostrar la instancia del menú
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
        
    }


    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }
    
    private void actualizar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }

        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer idUsuario = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

                    // Actualizar los campos con los valores de la interfaz
                    textoNombreUsuario.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 2));
                    textoPassword.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 3));


                    // Luego, puedes imprimir los valores para verificar
                    System.out.println("ID to update: " + idUsuario);
                    System.out.println("Texto Nombre: " + textoNombreUsuario.getText());
                    System.out.println("Texto Apellido: " + textoPassword.getText());

                    // Finalmente, llamar al método actualizar del controlador
                    this.usuarioController.actualizar(textoNombreUsuario.getText(), textoPassword.getText(), idUsuario);

                    JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
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
	    // Llenar Tabla
	    List<Usuario> usuarios = ListarUsuarios();
	    try {
	        for (Usuario usuario : usuarios) {
	            modelo.addRow(new Object[] { usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getRoles() });
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
  
    
  /*  private void guardar2() throws SQLException {
		
		 Usuario usuario = new Usuario(textoNombreUsuario.getText(), 
				 			textoPassword.getText());	
		 	
		this.usuarioController.guardar(usuario);	
 			
 		JOptionPane.showMessageDialog(this, "Registrado con éxito!");

 	    this.limpiarFormulario();
    							
    }*/
    
   private void guardar() {
	    try {
	        String nombreUsuario = textoNombreUsuario.getText();
	        String password = textoPassword.getText();
	        String rolSeleccionado = (String) listaRoles.getSelectedItem(); // Obtener el rol seleccionado

	        if (nombreUsuario.isEmpty() || password.isEmpty() || rolSeleccionado == null) {
	            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
	            return;
	        }

	        Usuario usuario = new Usuario(nombreUsuario, password);
	        usuario.addRol(Rol.valueOf(rolSeleccionado));

	        try {
	            this.usuarioController.guardar(usuario, rolSeleccionado);
	            JOptionPane.showMessageDialog(this, "Registrado con éxito!");
	            limpiarFormulario();
	        } catch (RuntimeException ex) {
	            JOptionPane.showMessageDialog(this, "Error al guardar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            ex.printStackTrace(); // Puedes ajustar el manejo de la excepción según tus necesidades
	        }
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, "Rol seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace(); // Puedes ajustar el manejo de la excepción según tus necesidades
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
   
    private void limpiarFormulario() {
    	
        this.textoNombreUsuario.setText("");
        this.textoPassword.setText("");
        this.textoRol.setText("");
        
    }
}
