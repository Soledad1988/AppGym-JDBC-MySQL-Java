package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.ReporteCuotasController;
import controller.ReporteIngresosController;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class ReporteClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ReporteIngresosController reporteControler;
    private ReporteCuotasController reporteCuotas;
    private JTextField textTotal;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteClientes frame = new ReporteClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReporteClientes() throws SQLException {
    	super("Reporte Clientes");
    	this.reporteControler = new ReporteIngresosController();
    	this.reporteCuotas = new ReporteCuotasController();
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenido(container);
    	
    	JLabel lblNewLabel = new JLabel("Reporte de Clientes");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
    	lblNewLabel.setBounds(309, 21, 218, 36);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblPeriodo = new JLabel("Periodo");
    	lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblPeriodo.setBounds(170, 108, 100, 22);
    	getContentPane().add(lblPeriodo);
    	
    	JComboBox<String> BoxPeriodo = new JComboBox<>();
    	BoxPeriodo.setModel(new DefaultComboBoxModel<>(new String[] {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
    	BoxPeriodo.setBounds(429, 105, 148, 22);
    	getContentPane().add(BoxPeriodo);
    	
    	// Ejemplo de cómo agregar un JComboBox para seleccionar el año
    	// Ejemplo: Añadir los últimos 5 años
    	JComboBox<Integer> boxAño = new JComboBox<>();
    	int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    	for (int i = currentYear; i > currentYear - 5; i--) {
    		boxAño.addItem(i);
    	}
    	boxAño.setBounds(429, 140, 148, 22); // Ajusta las coordenadas según tu diseño
    	getContentPane().add(boxAño);
    	
    	textTotal = new JTextField();
    	textTotal.setEditable(false);
    	textTotal.setBounds(455, 497, 86, 20);
    	getContentPane().add(textTotal);
    	textTotal.setColumns(10);
    	
    	
    	// Nuevo botón para aplicar el filtro
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(600, 105, 150, 22);
        getContentPane().add(btnFiltrar);

        // Agregar un ActionListener al botón de filtro
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el mes seleccionado en el JComboBox
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;
                int añoSeleccionado = (int) boxAño.getSelectedItem();
                // Limpiar la tabla antes de cargar los nuevos datos
                limpiarTabla();

                // Recargar la tabla con los clientes del mes seleccionado
                cargarTablaPorMes(numeroMes, añoSeleccionado);
            }
        });
        
        
     // Botón para calcular la suma por mes
        JButton btnCalcularSuma = new JButton("Calcular");
        btnCalcularSuma.setBounds(300, 497, 120, 25);
        getContentPane().add(btnCalcularSuma);
        
        JLabel lblAnio = new JLabel("Año");
        lblAnio.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAnio.setBounds(170, 141, 100, 22);
        getContentPane().add(lblAnio);

        // Agregar un ActionListener al botón para manejar la acción
        btnCalcularSuma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el mes seleccionado del JComboBox
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;
                int añoSeleccionado = (int) boxAño.getSelectedItem();
                // Llamar a la función realizarSumaPorMes y mostrar el resultado en textTotal
                double resultadoSuma = 0;
                try {
                    resultadoSuma = reporteControler.realizarSumaPorMes(numeroMes, añoSeleccionado);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                textTotal.setText(String.valueOf(resultadoSuma));
            }
        });
    	
    	
    	
    }
    
 // Método para limpiar la tabla antes de cargar nuevos datos
    private void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
    }
    
 // Método para cargar la tabla con clientes del mes seleccionado
    private void cargarTablaPorMes(int numeroMes, int año) {
        limpiarTabla(); // Limpiar la tabla antes de cargar nuevos datos

        List<Map<String, String>> clientes = new ArrayList<>();

        try {
            // Utiliza el método listarClientesPorMes de tu ReporteController
            clientes = this.reporteCuotas.reporteCuotasPorMes(numeroMes, año);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clientes.forEach(cliente -> modelo.addRow(
                new Object[] {
                        cliente.get("Fecha Pago"),
                        cliente.get("Nombre"),
                        cliente.get("Apellido"),
                        cliente.get("Monto"),
                        cliente.get("Estado")
                }));
    }
    
    
    @SuppressWarnings("serial")
	private void configurarTablaDeContenido(Container container) {
        tabla = new JTable(){
        	//agregamos color a los estados
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);

                // Suponiendo que la columna "Estado" es la quinta columna (índice 4)
                if (column == 4) {
                    Object value = getModel().getValueAt(row, column);
                    if (value != null) {
                        if ("Pagado".equals(value.toString())) {
                            component.setForeground(Color.GREEN);
                        } else if ("Debe".equals(value.toString())) {
                            component.setForeground(Color.RED);
                        } else {
                            component.setForeground(Color.BLACK); // Color por defecto
                        }
                    } else {
                        component.setForeground(Color.BLACK); // Color por defecto si es null
                    }
                } else {
                    // Para otras columnas, puedes mantener el color por defecto
                    component.setForeground(Color.BLACK);
                }

                return component;
            }
        };

        modelo = new DefaultTableModel();
        modelo.addColumn("Fecha Pago");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Monto");
        modelo.addColumn("Estado");
    
        tabla.setModel(modelo);
        
        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        container.add(tabla);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 205, 760, 280);
        container.add(scrollPane); 

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void cargarTabla() {
        List<Map<String, String>> clientes = new ArrayList<Map<String, String>>();

        try {
        	clientes = this.reporteControler.reporteClientes();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clientes.forEach(cliente -> modelo.addRow(
                new Object[] {
                		cliente.get("fechaAlta"),
                		cliente.get("nombre"),
                		cliente.get("apellido"),
                		cliente.get("precio"),
                		cliente.get("pago")}));
    }
}