package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.ClienteController;
import gym.modelo.Cliente;

import java.sql.Date;



public class AltaClientesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDireccion;
    private JTextField textoNombre, textoApellido, textoDireccion;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonMenu;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;
    private MenuFrame menuFrame;
    
    public static JDateChooser textFechaIngreso;
    private JTextField textPrecio;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaClientesFrame frame = new AltaClientesFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public AltaClientesFrame() throws SQLException {
        super("Clientes");

        this.clienteController = new ClienteController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        
        JLabel labelFecha = new JLabel("Fecha Ingreso");
        labelFecha.setForeground(Color.BLACK);
        labelFecha.setBounds(10, 33, 240, 15);
        getContentPane().add(labelFecha);
        
        /*----------------------------------*/
        
        textFechaIngreso = new JDateChooser();
        textFechaIngreso.getCalendarButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        textFechaIngreso.getCalendarButton().setBackground(SystemColor.textHighlight);
        textFechaIngreso.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
        textFechaIngreso.setBounds(10, 48, 265, 23);
        textFechaIngreso.getCalendarButton().setBounds(268, 0, 21, 33);
        textFechaIngreso.setBackground(Color.WHITE);
        textFechaIngreso.setBorder(new LineBorder(SystemColor.window));
        textFechaIngreso.setDateFormatString("yyyy-MM-dd");
        textFechaIngreso.setFont(new Font("Roboto", Font.PLAIN, 18));
		//panel.add(textFechaIngreso);
        getContentPane().add(textFechaIngreso);
        
        JLabel lblPrecio = new JLabel("Precio");
        lblPrecio.setForeground(Color.BLACK);
        lblPrecio.setBounds(10, 220, 240, 15);
        getContentPane().add(lblPrecio);
        
        textPrecio = new JTextField();
        textPrecio.setBounds(10, 234, 265, 20);
        getContentPane().add(textPrecio);
        
        JLabel Logo = new JLabel("");
        Logo.setIcon(new ImageIcon("C:\\Users\\brent\\eclipse-workspace\\gym.png"));
        Logo.setBounds(401, 48, 281, 212);
        getContentPane().add(Logo);
        
        JLabel lblTitulo = new JLabel("Registro Clientes");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(275, 0, 213, 30);
        getContentPane().add(lblTitulo);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Id");
        modelo.addColumn("Fecha Alta");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Pago");


        cargarTabla();

        tabla.setBounds(10, 286, 760, 240);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonMenu = new JButton("Menù");
        
        botonEliminar.setBounds(15, 530, 80, 20);
        botonModificar.setBounds(105, 530, 80, 20);
        botonMenu.setBounds(195, 530, 80, 20);
        

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonMenu);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre");
        labelApellido = new JLabel("Apellido");
        labelDireccion = new JLabel("Direccion");

        labelNombre.setBounds(10, 82, 240, 15);
        labelApellido.setBounds(10, 128, 240, 15);
        labelDireccion.setBounds(10, 174, 240, 15);
        
        labelNombre.setForeground(Color.BLACK);
        labelApellido.setForeground(Color.BLACK);
        labelDireccion.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoApellido = new JTextField();
        textoDireccion = new JTextField();

        // TODO
        
        textoNombre.setBounds(10, 97, 265, 20);
        textoApellido.setBounds(10, 143, 265, 20);
        textoDireccion.setBounds(10, 189, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(15, 265, 80, 20);
        botonLimpiar.setBounds(105, 265, 80, 20);

        container.add(labelNombre);
        container.add(labelApellido);
        container.add(labelDireccion);
        container.add(textoNombre);
        container.add(textoApellido);
        container.add(textoDireccion);
        container.add(botonGuardar);
        container.add(botonLimpiar);
       
        
    }

    

    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					guardar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                limpiarTabla();
                cargarTabla();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
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
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());

                    // Actualizar los campos con los valores de la interfaz
                    textoNombre.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 2));
                    textoApellido.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 3));
                    textoDireccion.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 4));

                    // Luego, puedes imprimir los valores para verificar
                    System.out.println("ID to update: " + id);
                    System.out.println("Texto Nombre: " + textoNombre.getText());
                    System.out.println("Texto Apellido: " + textoApellido.getText());
                    System.out.println("Texto Direccion: " + textoDireccion.getText());

                    // Finalmente, llamar al método actualizar del controlador
                    this.clienteController.actualizar(textoNombre.getText(), textoApellido.getText(), textoDireccion.getText(), id);

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
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
                    this.clienteController.eliminar(id);	
            
                    modelo.removeRow(tabla.getSelectedRow());

                    JOptionPane.showMessageDialog(this, " Item eliminado con éxito!");
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
    
     
   private List<Cliente> ListarClientes() {
		return this.clienteController.listar();
   }
     
   private void cargarTabla() {			       
	    //Llenar Tabla
		List<Cliente> cliente = ListarClientes();
		try {
			for (Cliente clientes : cliente) {
				modelo.addRow(new Object[] { clientes.getId(), clientes.getFechaAlta(), clientes.getNombre(), clientes.getApellido(), 
						clientes.getDireccion(),clientes.getPrecio() });
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
  
    
    private void guardar() throws SQLException {
             
    	String fechaIngreso = ((JTextField)textFechaIngreso.getDateEditor().getUiComponent()).getText();
    	Double precio = Double.parseDouble(textPrecio.getText());
		
		 Cliente cliente = new Cliente(java.sql.Date.valueOf(fechaIngreso),
          		textoNombre.getText(), textoApellido.getText(), textoDireccion.getText(),precio);	
		 	
		this.clienteController.guardar(cliente);	
 			
 		JOptionPane.showMessageDialog(this, "Registrado con éxito!");

 	    this.limpiarFormulario();
    							
    }
    
   
    private void limpiarFormulario() {
    	this.textFechaIngreso.setDate(null);
        this.textoNombre.setText("");
        this.textoApellido.setText("");
        this.textoDireccion.setText("");
        this.textPrecio.setText("");
    }

}
