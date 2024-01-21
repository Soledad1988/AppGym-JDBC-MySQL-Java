package vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.ReporteGastosController;
import gym.modelo.Cliente;
import gym.modelo.Gastos;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class ReporteGastos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    //********************************
    private JPanel contentPane;
    private JLabel labelPeriodo;
	

	private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
	

    //private ClienteController clienteController;
    private ReporteGastosController reporteControler;
    private JTextField textTotal;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteGastos frame = new ReporteGastos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReporteGastos() throws SQLException {
    	super("Reporte de Gastos");
    	this.reporteControler = new ReporteGastosController();
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenido(container);
    	
    	JLabel lblNewLabel = new JLabel("Reporte de Gastos");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
    	lblNewLabel.setBounds(309, 21, 218, 36);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblPeriodo = new JLabel("Periodo");
    	lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblPeriodo.setBounds(170, 108, 100, 22);
    	getContentPane().add(lblPeriodo);
    	
    	JComboBox BoxPeriodo = new JComboBox();
    	BoxPeriodo.setModel(new DefaultComboBoxModel(new String[] {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
    	BoxPeriodo.setBounds(429, 105, 148, 22);
    	getContentPane().add(BoxPeriodo);
    	
    	// Ejemplo de cómo agregar un JComboBox para seleccionar el año
    	JComboBox<Integer> boxAño = new JComboBox<>();
    	for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 2000; i--) {
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
        JButton btnFiltrar = new JButton("Filtrar por Mes");
        btnFiltrar.setBounds(600, 105, 150, 22);
        getContentPane().add(btnFiltrar);

        // Agregar un ActionListener al botón de filtro
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;
                int añoSeleccionado = (int) boxAño.getSelectedItem();

                limpiarTabla();
                cargarTablaPorMes(numeroMes, añoSeleccionado);
            }
        });
        
        // Botón para calcular la suma por mes
           JButton btnCalcularSuma = new JButton("Calcular Suma");
           btnCalcularSuma.setBounds(300, 497, 120, 25);
           getContentPane().add(btnCalcularSuma);
    	
        // Agregar un ActionListener al botón para manejar la acción
           btnCalcularSuma.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	        int numeroMes = BoxPeriodo.getSelectedIndex() + 1;
        	        int añoSeleccionado = (int) boxAño.getSelectedItem();

        	        double resultadoSuma = 0;
        	        try {
        	            resultadoSuma = reporteControler.obtenerSumaCostosPorMes(numeroMes, añoSeleccionado);
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

        List<Gastos> gastos = new ArrayList<>();

        try {
            // Utiliza el método listarGastosPorMes de tu ReporteController
            gastos = this.reporteControler.listarGastosPorMes(numeroMes, año);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modelo.addRow(
                new Object[] {
                        gasto.getFechaGasto(),
                        gasto.getNombreGasto(),
                        gasto.getDescripcion(),
                        gasto.getCosto()
                }));
    }
    
    private void configurarTablaDeContenido(Container container) {
    	
    	tabla = new JTable();

        modelo = new DefaultTableModel();
        modelo.addColumn("Fecha Gasto");
        modelo.addColumn("Nombre Gasto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Costo");

        tabla.setModel(modelo); // Asegúrate de establecer el modelo en la tabla

        cargarTabla();

        JScrollPane scrollPane = new JScrollPane(tabla); // Añadir la tabla a un JScrollPane
        scrollPane.setBounds(10, 205, 760, 280);
        container.add(scrollPane); // Agregar el JScrollPane al contenedor

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    private void cargarTabla() {
        limpiarTabla(); // Limpiar la tabla antes de cargar nuevos datos

        List<Gastos> gastos = new ArrayList<>();

        try {
            gastos = this.reporteControler.reporteGastos();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modelo.addRow(
                new Object[] {
                        gasto.getFechaGasto(),
                        gasto.getNombreGasto(),
                        gasto.getDescripcion(),
                        gasto.getCosto()
                }));
    }

}