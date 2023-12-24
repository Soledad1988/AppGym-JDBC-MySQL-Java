package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.CuotaController;
import gym.modelo.Cliente;
import gym.modelo.Cuota;


public class RegistroCuotas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelApellido;
    private JTextField textoApellido;
    private JButton botonAsignarCuota, botonBuscar, botonMenu;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;
    private CuotaController cuotaController;
    
    public static JDateChooser textFechaPago;
    private JTextField textMonto;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroCuotas frame = new RegistroCuotas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public RegistroCuotas() throws SQLException {
        super("Cuotas");
        

        this.clienteController = new ClienteController();
        this.cuotaController = new CuotaController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        
        JLabel labelFecha = new JLabel("Fecha Pago");
        labelFecha.setForeground(Color.BLACK);
        labelFecha.setBounds(449, 326, 117, 15);
        getContentPane().add(labelFecha);
        
        /*----------------------------------*/
        
        textFechaPago = new JDateChooser();
        textFechaPago.getCalendarButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        textFechaPago.getCalendarButton().setBackground(SystemColor.textHighlight);
        textFechaPago.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
        textFechaPago.setBounds(549, 326, 175, 23);
        textFechaPago.getCalendarButton().setBounds(268, 0, 21, 33);
        textFechaPago.setBackground(Color.WHITE);
        textFechaPago.setBorder(new LineBorder(SystemColor.window));
        textFechaPago.setDateFormatString("yyyy-MM-dd");
        textFechaPago.setFont(new Font("Roboto", Font.PLAIN, 18));
		//panel.add(textFechaIngreso);
        getContentPane().add(textFechaPago);
        
        JLabel lblMonto = new JLabel("Monto");
        lblMonto.setForeground(Color.BLACK);
        lblMonto.setBounds(83, 326, 117, 15);
        getContentPane().add(lblMonto);
        
        textMonto = new JTextField();
        textMonto.setBounds(167, 323, 144, 20);
        getContentPane().add(textMonto);
        
        JLabel lblTitulo = new JLabel("Registro Cuotas");
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


        //cargarTabla();

        tabla.setBounds(16, 112, 760, 172);
        botonMenu = new JButton("Menú");
        botonMenu.setBounds(449, 366, 98, 20);
        

        container.add(tabla);
        container.add(botonMenu);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelApellido = new JLabel("Apellido");
        labelApellido.setBounds(60, 65, 125, 15);
        labelApellido.setForeground(Color.BLACK);

        textoApellido = new JTextField();

        // TODO
        
        textoApellido.setBounds(163, 62, 125, 20);

        botonAsignarCuota = new JButton("Asignar Cuota");
        botonBuscar = new JButton("Buscar");
        botonAsignarCuota.setBounds(236, 366, 138, 20);
        botonBuscar.setBounds(426, 62, 99, 20);
        
        

        container.add(labelApellido);
        container.add(textoApellido);
        container.add(botonAsignarCuota);
        container.add(botonBuscar);
       
        
    }

    

    private void configurarAccionesDelFormulario() {
    	
       /* botonAsignarCuota.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	asignarPagos();
            	buscarClientesPorApellido();
				
                limpiarTabla();
                cargarTabla();
            }
        });*/
        
     // Configuración de acciones para el botón de asignación de pagos
        botonAsignarCuota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de asignación de pagos
                guardar();
                limpiarCampos();
            }
        });

        
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de búsqueda y actualización de la tabla
                buscarClientesPorApellido();
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
 
    
    
     
   private List<Cliente> ListarClientes() {
		return this.clienteController.listar();
   }
     
   private void cargarTabla() {			       
	    //Llenar Tabla
		List<Cliente> cliente = ListarClientes();
		try {
			for (Cliente clientes : cliente) {
				modelo.addRow(new Object[] { clientes.getId(), clientes.getFechaAlta(), clientes.getNombre(), clientes.getApellido(), 
						clientes.getDireccion(),clientes.getTelefono() });
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
  
    
  /* private void guardar() {
	    try {
	        
	            Integer idCliente = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
	            String fechaPago = ((JTextField) textFechaPago.getDateEditor().getUiComponent()).getText();
	            Double monto = Double.parseDouble(textMonto.getText());

	            this.cuotaController.asignarCuota(idCliente, monto, java.sql.Date.valueOf(fechaPago));

	            JOptionPane.showMessageDialog(this, "Registrado con éxito!");
	            this.limpiarFormulario();
	        
	    } catch (SQLException | NumberFormatException e) {
	        // Manejar excepciones adecuadamente, mostrar mensaje de error, etc.
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
	    }
	}*/
   
   private void buscarClientesPorApellido() {
       String apellido = textoApellido.getText().trim();

       try {
           List<Cliente> clientes = clienteController.buscarPorApellido(apellido);

           DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
           modeloTabla.setRowCount(0);

           for (Cliente cliente : clientes) {
               Object[] fila = {cliente.getId(), cliente.getNombre(), cliente.getApellido(), false};
               modeloTabla.addRow(fila);
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error al buscar clientes.");
       }
   }
   


   
   private void guardar() {
	    try {
	        int filaSeleccionada = tabla.getSelectedRow();

	        if (filaSeleccionada != -1) {
	        	Integer idCliente = Integer.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString());
	            String fechaPago = ((JTextField) textFechaPago.getDateEditor().getUiComponent()).getText();
	            Double monto = Double.parseDouble(textMonto.getText());

	            this.cuotaController.asignarCuota(idCliente, monto, java.sql.Date.valueOf(fechaPago));

	            JOptionPane.showMessageDialog(this, "Registrado con éxito!");
	            this.limpiarCampos();
	        } else {
	            JOptionPane.showMessageDialog(this, "Seleccione una fila antes de guardar.");
	        }
	    } catch (SQLException | NumberFormatException e) {
	        // Manejar excepciones adecuadamente, mostrar mensaje de error, etc.
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
	    }
	}

    
   
    private void limpiarCampos() {
    	this.textFechaPago.setDate(null);
        this.textoApellido.setText("");
        this.textMonto.setText("");
    }

}
