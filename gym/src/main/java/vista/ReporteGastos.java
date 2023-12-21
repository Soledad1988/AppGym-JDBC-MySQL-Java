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
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.ClienteController;
import controller.ReporteController;
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
    private ReporteController reporteControler;
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

    public ReporteGastos() {
    	super("Reporte de Gastos");
    	this.reporteControler = new ReporteController();
    	
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
                // Obtener el mes seleccionado en el JComboBox
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;

                // Limpiar la tabla antes de cargar los nuevos datos
                limpiarTabla();

                // Recargar la tabla con los clientes del mes seleccionado
                cargarTablaPorMes(numeroMes);
            }
        });
        
        
        // Botón para calcular la suma por mes
           JButton btnCalcularSuma = new JButton("Calcular Suma");
           btnCalcularSuma.setBounds(300, 497, 120, 25);
           getContentPane().add(btnCalcularSuma);
    	
        // Agregar un ActionListener al botón para manejar la acción
        btnCalcularSuma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el mes seleccionado del JComboBox
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;

                // Llamar a la función realizarSumaPorMes y mostrar el resultado en textTotal
                double resultadoSuma = 0;
                try {
                    resultadoSuma = reporteControler.obtenerSumaCostosPorMes(numeroMes);
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
    private void cargarTablaPorMes(int numeroMes) {
        limpiarTabla(); // Limpiar la tabla antes de cargar nuevos datos

        List<Gastos> gastos = new ArrayList<>();

        try {
            // Utiliza el método listarGastosPorMes de tu ReporteController
            gastos = this.reporteControler.listarGastosPorMes(numeroMes);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modelo.addRow(
                new Object[] {
                        gasto.getPeriodoGasto(),
                        gasto.getNombreGasto(),
                        gasto.getTipo(),
                        gasto.getCosto()
                }));
    }
    
    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Periodo Gasto");
        modelo.addColumn("Nombre Gasto");
        modelo.addColumn("Tipo");
        modelo.addColumn("Costo");
    
        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        container.add(tabla);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

   /* private void cargarTabla() {
        List<Map<String, String>> gastos = new ArrayList<Map<String, String>>();

        try {
        	gastos = this.reporteControler.reporteGastos();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(producto -> modelo.addRow(
                new Object[] {
                		producto.get("Periodo"),
                        producto.get("Gasto"),
                        producto.get("Tipo"),
                        producto.get("Costo")}));
    }*/
    
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
                        gasto.getPeriodoGasto(),
                        gasto.getNombreGasto(),
                        gasto.getTipo(),
                        gasto.getCosto()
                }));
    }

}