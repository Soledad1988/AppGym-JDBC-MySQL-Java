package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import controller.IngresosController;
import dao.IngresosDAO;
import gym.modelo.Cliente;

import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Font;


public class RegistroIngresos extends JFrame {
	
	private JTextField textoApellido;
    private JButton botonBuscar;
    private JTable tablaClientes;
    private JCheckBox[] checkboxes;
    private JButton botonAsignarPago;
    private JTextField textoMonto;
    private IngresosController ingresosController;
    private JDateChooser textFechaPago;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroIngresos frame = new RegistroIngresos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

    } 
    

    public RegistroIngresos() {
        // Configuración de la ventana principal
        super("Asignación de Pagos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        try {
            ingresosController = new IngresosController();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al inicializar el controlador.");
            // Puedes tomar medidas adicionales según tus requisitos, como cerrar la aplicación.
        }
        
        // Creación de componentes
        textoApellido = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        tablaClientes = new JTable();
        
        

        // Configuración del modelo de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Pago");
        tablaClientes.setModel(modeloTabla);

        // Configuración del diseño de la ventana
        getContentPane().setLayout(new BorderLayout());

        // Panel para la búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Apellido:"));
        panelBusqueda.add(textoApellido);
        panelBusqueda.add(botonBuscar);
        
        textoMonto = new JTextField(10);

        // Agregar los campos de texto al panel de asignación de pagos
        
        

        // Panel para la tabla
        JScrollPane panelTabla = new JScrollPane(tablaClientes);

        // Panel para la asignación de pagos
        JPanel panelAsignacion = new JPanel();
        panelAsignacion.add(new JLabel("Monto:"));
        panelAsignacion.add(textoMonto);
        panelAsignacion.add(new JLabel("Fecha:"));
        getContentPane().add(panelAsignacion, BorderLayout.SOUTH);

        // Adición de componentes a la ventana
        getContentPane().add(panelBusqueda, BorderLayout.NORTH);
        getContentPane().add(panelTabla, BorderLayout.CENTER);
        getContentPane().add(panelAsignacion, BorderLayout.SOUTH);
        
        textFechaPago = new JDateChooser();
        textFechaPago.getCalendarButton().setFont(new Font("Dialog", Font.PLAIN, 12));
        textFechaPago.getCalendarButton().setBackground(SystemColor.textHighlight);
        textFechaPago.setFont(new Font("Dialog", Font.PLAIN, 18));
        textFechaPago.setDateFormatString("yyyy-MM-dd");
        textFechaPago.setBorder(new LineBorder(SystemColor.window));
        textFechaPago.setBackground(Color.WHITE);
        panelAsignacion.add(textFechaPago);
        botonAsignarPago = new JButton("Asignar Pago");
        panelAsignacion.add(botonAsignarPago);
        
                // Configuración de acciones para el botón de asignación de pagos
                botonAsignarPago.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Lógica de asignación de pagos
                        asignarPagos();
                    }
                });
       

        // Configuración de acciones para el botón de búsqueda
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de búsqueda y actualización de la tabla
                buscarClientesPorApellido();
            }
        });
    }

    private void buscarClientesPorApellido() {
        String apellido = textoApellido.getText().trim();

        try {
            List<Cliente> clientes = ingresosController.buscarPorApellido(apellido);;

            DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();
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
    
    
    
    private double obtenerMontoPago() {
        // Implementa la lógica para obtener el monto de pago (puedes usar un cuadro de diálogo, etc.)
        // Por ahora, devuelve un valor ficticio
        return 100.0;
    }

    private void asignarPagos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            boolean seleccionado = (boolean) modeloTabla.getValueAt(i, 3);
            if (seleccionado) {
                int idCliente = (int) modeloTabla.getValueAt(i, 0);

                // Obtener el monto y la fecha ingresados manualmente
                Double montoPago = Double.parseDouble(textoMonto.getText());
                // Obtener la fecha seleccionada del JDateChooser
                LocalDate fechaSeleccionada = Instant.ofEpochMilli(textFechaPago.getDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // Convertir la fecha a una cadena
                String fechaPago = fechaSeleccionada.toString();

                try {
                    ingresosController.asignarPagos(idCliente, montoPago, fechaPago);
                    System.out.println("Pago asignado al cliente con ID " + idCliente + " por un monto de " + montoPago +
                            " en la fecha " + fechaPago);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al asignar pagos.");
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Pagos asignados con éxito");
    }
    
}