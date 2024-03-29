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

import javax.swing.JButton;
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
import controller.CuotaController;
import gym.modelo.Cliente;


public class RegistroCuotas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelApellido;
    private JTextField textoApellido;
    private JButton botonAsignarCuota, botonBuscar;
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
        labelFecha.setBounds(240, 448, 99, 15);
        getContentPane().add(labelFecha);
        
        textFechaPago = new JDateChooser();
        textFechaPago.getCalendarButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        textFechaPago.getCalendarButton().setBackground(SystemColor.textHighlight);
        textFechaPago.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
        textFechaPago.setBounds(323, 445, 175, 20);
        textFechaPago.getCalendarButton().setBounds(268, 0, 21, 33);
        textFechaPago.setBackground(Color.WHITE);
        textFechaPago.setBorder(new LineBorder(SystemColor.window));
        textFechaPago.setDateFormatString("yyyy-MM-dd");
        textFechaPago.setFont(new Font("Roboto", Font.PLAIN, 18));
		//panel.add(textFechaIngreso);
        getContentPane().add(textFechaPago);
        
        JLabel lblMonto = new JLabel("Monto");
        lblMonto.setForeground(Color.BLACK);
        lblMonto.setBounds(27, 448, 57, 15);
        getContentPane().add(lblMonto);
        
        textMonto = new JTextField();
        textMonto.setBounds(74, 445, 144, 20);
        getContentPane().add(textMonto);
        
        JLabel lblTitulo = new JLabel("Registro Cuotas");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(275, 0, 213, 30);
        getContentPane().add(lblTitulo);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        
        tabla.setModel(modelo);
        
        // Ocultar la columna de ID
        tabla.removeColumn(tabla.getColumnModel().getColumn(0));


        tabla.setBounds(16, 112, 760, 172);
        

        container.add(tabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(16, 112, 760, 262);
        container.add(scrollPane);
        
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelApellido = new JLabel("Apellido");
        labelApellido.setBounds(60, 65, 125, 15);
        labelApellido.setForeground(Color.BLACK);

        textoApellido = new JTextField();
        
        textoApellido.setBounds(163, 62, 125, 20);

        botonAsignarCuota = new JButton("Asignar Cuota");
        botonBuscar = new JButton("Buscar");
        botonAsignarCuota.setBounds(588, 445, 138, 20);
        botonBuscar.setBounds(426, 62, 99, 20);
        
        container.add(labelApellido);
        container.add(textoApellido);
        container.add(botonAsignarCuota);
        container.add(botonBuscar);
       
        
    }

 
    private void configurarAccionesDelFormulario() {
        
        botonAsignarCuota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
                limpiarCampos();
            }
        });

        
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientesPorApellido();
            }
        });
        

    }
 
     
   private List<Cliente> ListarClientes() {
		return this.clienteController.listar();
   }
     
   @SuppressWarnings("unused")
private void cargarTabla() {               
	    // Llenar Tabla
	    List<Cliente> clientes = ListarClientes();
	    try {
	        for (Cliente cliente : clientes) {
	            modelo.addRow(new Object[] {
	                //cliente.getId(),
	                cliente.getNombre(),
	                cliente.getApellido()
	            });
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
   
   private void buscarClientesPorApellido() {
	   String apellido = textoApellido.getText().trim();

	    try {
	        List<Cliente> clientes = clienteController.buscarPorApellido(apellido);

	        DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
	        modeloTabla.setRowCount(0);

	        if (clientes.isEmpty()) {
	            // Mostrar mensaje si no se encuentran clientes
	            JOptionPane.showMessageDialog(this, "No se encontró clientes con el apellido: " + apellido);
	        } else {
	            // Agregar clientes encontrados a la tabla
	            for (Cliente cliente : clientes) {
	                Object[] fila = {
	                    cliente.getId(), 
	                    cliente.getNombre(), 
	                    cliente.getApellido(), 
	                    false
	                };
	                modeloTabla.addRow(fila);
	            }
	        }
	    } catch (RuntimeException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al buscar clientes.");
	    }
   }
   
   private void guardar() {
	    try {
	        int filaSeleccionada = tabla.getSelectedRow();

	        if (filaSeleccionada != -1) {
	            Integer idCliente = Integer.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString()); // Índice 0 para el ID   
	            String fechaPago = ((JTextField) textFechaPago.getDateEditor().getUiComponent()).getText();
	            Double monto = Double.parseDouble(textMonto.getText());

	            this.cuotaController.asignarCuota(idCliente, monto, java.sql.Date.valueOf(fechaPago));

	            JOptionPane.showMessageDialog(this, "Registrado con éxito!");
	            this.limpiarCampos();
	        } else {
	            JOptionPane.showMessageDialog(this, "Seleccione una fila antes de guardar.");
	        }
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
	    }
	}

    @SuppressWarnings("static-access")
	private void limpiarCampos() {
    	this.textFechaPago.setDate(null);
        this.textoApellido.setText("");
        this.textMonto.setText("");
    }

}
