
package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import gym.modelo.Cliente;


public class AltaClientesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDireccion;
    private JTextField textoNombre, textoApellido, textoDireccion;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonVerTurnos;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> boxHorario;
    private ClienteController clienteController;
    
    public static JDateChooser textFechaIngreso;
    private JTextField textoTelefono;
    
    
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
        
        textFechaIngreso = new JDateChooser();
        textFechaIngreso.setDateFormatString("dd-MM-yyyy");
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
        
        JLabel lblTelefono = new JLabel("Telefono");
        lblTelefono.setForeground(Color.BLACK);
        lblTelefono.setBounds(10, 220, 240, 15);
        getContentPane().add(lblTelefono);
        
        textoTelefono = new JTextField();
        textoTelefono.setBounds(10, 234, 265, 20);
        getContentPane().add(textoTelefono);
        
        JLabel Logo = new JLabel("");
        Logo.setIcon(new ImageIcon("C:\\Users\\brent\\eclipse-workspace\\gym.png"));
        Logo.setBounds(401, 48, 281, 212);
        getContentPane().add(Logo);
        
        JLabel lblTitulo = new JLabel("Registro Clientes");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(275, 0, 213, 30);
        getContentPane().add(lblTitulo);
        
        JLabel lblHorario = new JLabel("Horario");
        lblHorario.setForeground(Color.BLACK);
        lblHorario.setBounds(10, 260, 240, 15);
        getContentPane().add(lblHorario);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();
        
        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Fecha Alta");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Horario");
        
        tabla.setModel(modelo);
        
        cargarTabla();
        
     // Ocultar la columna de ID
        tabla.removeColumn(tabla.getColumnModel().getColumn(0));

        tabla.setBounds(10, 307, 760, 219);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        
        botonEliminar.setBounds(296, 532, 98, 20);
        botonModificar.setBounds(404, 532, 93, 20);
        

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 339, 760, 182);
        container.add(scrollPane);

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
        //JComboBox<String> boxHorario = new JComboBox<>();
        boxHorario = new JComboBox<>();
        boxHorario.setModel(new DefaultComboBoxModel<>(new String[] {
            "08:00 a 09:00", "09:00 a 10:00", 
            "10:00 a 11:00", "11:00 a 12:00", "12:00 a 13:00", 
            "13:00 a 14:00", "14:00 a 15:00", "15:00 a 16:00", 
            "16:00 a 17:00", "17:00 a 18:00", "18:00 a 19:00", 
            "19:00 a 20:00", "20:00 a 21:00", "21:00 a 22:00"
        }));

        boxHorario.setBounds(10, 275, 265, 22);
        getContentPane().add(boxHorario);
        
        textoNombre.setBounds(10, 97, 265, 20);
        textoApellido.setBounds(10, 143, 265, 20);
        textoDireccion.setBounds(10, 189, 265, 20);
        
        botonVerTurnos = new JButton("Ver Turnos");
        botonVerTurnos.setBounds(260, 308, 120, 20);
        container.add(botonVerTurnos);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(42, 308, 99, 20);
        botonLimpiar.setBounds(151, 308, 99, 20);

        container.add(labelNombre);
        container.add(labelApellido);
        container.add(labelDireccion);
        container.add(textoNombre);
        container.add(textoApellido);
        container.add(textoDireccion);
        container.add(botonGuardar);
        container.add(botonLimpiar);
        container.add(botonVerTurnos);
       
        
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

        botonVerTurnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReporteTurnos();
            }
        });

    }


    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }
    
    private void abrirReporteTurnos() {
        try {
        	TurnosFrame reporteFrame = new TurnosFrame();
            reporteFrame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    textoTelefono.setText((String) modelo.getValueAt(tabla.getSelectedRow(), 5));
                    
                    String horarioSeleccionado = (String) boxHorario.getSelectedItem();

                    // Luego, puedes imprimir los valores para verificar
                    System.out.println("ID to update: " + id);
                    System.out.println("Texto Nombre: " + textoNombre.getText());
                    System.out.println("Texto Apellido: " + textoApellido.getText());
                    System.out.println("Texto Direccion: " + textoDireccion.getText());

                    // Finalmente, llamar al método actualizar del controlador
                    this.clienteController.actualizar(textoNombre.getText(), textoApellido.getText(), 
                    		textoDireccion.getText(), textoTelefono.getText(), horarioSeleccionado , id);

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
				modelo.addRow(new Object[] { 
						clientes.getId(), 
						clientes.getFechaAlta(), 
						clientes.getNombre(), 
						clientes.getApellido(), 
						clientes.getDireccion(),
						clientes.getTelefono(),
						clientes.getHorario()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
    
   private void guardar() throws SQLException {
	    String fechaIngreso = ((JTextField) textFechaIngreso.getDateEditor().getUiComponent()).getText();
	    String horarioSeleccionado = (String) boxHorario.getSelectedItem();

	    Cliente cliente = new Cliente(
	            java.sql.Date.valueOf(fechaIngreso),
	            textoNombre.getText(),
	            textoApellido.getText(),
	            textoDireccion.getText(),
	            textoTelefono.getText());
	    cliente.setHorario(horarioSeleccionado);

	    this.clienteController.guardar(cliente);

	    JOptionPane.showMessageDialog(this, "Registrado con éxito!");

	    this.limpiarFormulario();
	}
    
	@SuppressWarnings("static-access")
	private void limpiarFormulario() {
    	this.textFechaIngreso.setDate(null);
        this.textoNombre.setText("");
        this.textoApellido.setText("");
        this.textoDireccion.setText("");
        this.textoTelefono.setText("");
    }
}
