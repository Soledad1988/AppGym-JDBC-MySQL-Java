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
import dao.ReporteMensualDAO;
import gym.modelo.Cliente;
import gym.modelo.Gastos;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class ReporteGananciaPerdida extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private JTable tablaIngresos;
    private DefaultTableModel modelo;
    
    //********************************
    private JPanel contentPane;
    private JLabel labelPeriodo;
	

	private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
	

    //private ClienteController clienteController;
    private ReporteController reporteControler;
    private ReporteMensualDAO reporteMensual;
    private JTextField textTotal;
    private JTable tablaEgresos;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteGananciaPerdida frame = new ReporteGananciaPerdida();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReporteGananciaPerdida() {
    	super("Reporte del Mes");
    	//this.reporteControler = new ReporteController();
    	this.reporteMensual = new ReporteMensualDAO(null);
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenido(container);
    	
    	JLabel lblNewLabel = new JLabel("Reporte del Mes");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
    	lblNewLabel.setBounds(300, 11, 218, 36);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblPeriodo = new JLabel("Periodo");
    	lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	lblPeriodo.setBounds(117, 61, 100, 22);
    	getContentPane().add(lblPeriodo);
    	
    	JComboBox BoxPeriodo = new JComboBox();
    	BoxPeriodo.setModel(new DefaultComboBoxModel(new String[] {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"}));
    	BoxPeriodo.setBounds(376, 58, 148, 22);
    	getContentPane().add(BoxPeriodo);
    	
    	textTotal = new JTextField();
    	textTotal.setEditable(false);
    	textTotal.setBounds(455, 497, 133, 20);
    	getContentPane().add(textTotal);
    	textTotal.setColumns(10);
    	
    	
    	// Nuevo botón para aplicar el filtro
        JButton btnFiltrar = new JButton("Filtrar por Mes");
        btnFiltrar.setBounds(547, 58, 150, 22);
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
           JButton btnCalcularGananciaPerdida = new JButton("Calcular Ganancia/Perdida");
           btnCalcularGananciaPerdida.setBounds(252, 497, 168, 25);
           getContentPane().add(btnCalcularGananciaPerdida);
           
           JLabel lblTotalDeIngresos = new JLabel("Total de Ingresos");
           lblTotalDeIngresos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
           lblTotalDeIngresos.setBounds(10, 94, 218, 36);
           getContentPane().add(lblTotalDeIngresos);
           
           tablaEgresos = new JTable();
           tablaEgresos.setBounds(10, 322, 760, 164);
           getContentPane().add(tablaEgresos);
           
           JLabel lblTotalDeEgresos = new JLabel("Total de Egresos");
           lblTotalDeEgresos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
           lblTotalDeEgresos.setBounds(10, 288, 218, 36);
           getContentPane().add(lblTotalDeEgresos);
    	
        // Agregar un ActionListener al botón para manejar la acción
        btnCalcularGananciaPerdida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el mes seleccionado del JComboBox
                int numeroMes = BoxPeriodo.getSelectedIndex() + 1;

                // Llamar a la función realizarSumaPorMes y mostrar el resultado en textTotal
                double resultadoSuma = 0;
                try {
                    resultadoSuma = reporteMensual.obtenerSumaCostosPorMes(numeroMes);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                textTotal.setText(String.valueOf(resultadoSuma));
            }
        });
    }
    
 // Método para limpiar la tabla antes de cargar nuevos datos
    private void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaIngresos.getModel();
        model.setRowCount(0);
    }
    
 // Método para cargar la tabla con clientes del mes seleccionado
    private void cargarTablaPorMes(int numeroMes) {
        limpiarTabla(); // Limpiar la tabla antes de cargar nuevos datos

        List<Gastos> gastos = new ArrayList<>();

        try {
            // Utiliza el método listarGastosPorMes de tu ReporteController
            gastos = this.reporteMensual.listarGastosPorMes(numeroMes);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modelo.addRow(
                new Object[] {
                        gasto.getPeriodoGasto(),
                        gasto.getNombreGasto(),
                        gasto.getDescripcion(),
                        gasto.getCosto()
                }));
    }
    
    private void configurarTablaDeContenido(Container container) {
        tablaIngresos = new JTable();

        modelo = (DefaultTableModel) tablaIngresos.getModel();
        modelo.addColumn("Periodo Gasto");
        modelo.addColumn("Nombre Gasto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Costo");
    
        cargarTabla();

        tablaIngresos.setBounds(10, 129, 760, 164);

        container.add(tablaIngresos);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    private void cargarTabla() {
        limpiarTabla(); // Limpiar la tabla antes de cargar nuevos datos

        List<Gastos> gastos = new ArrayList<>();

        try {
            gastos = this.reporteMensual.reporteGastos();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modelo.addRow(
                new Object[] {
                        gasto.getPeriodoGasto(),
                        gasto.getNombreGasto(),
                        gasto.getDescripcion(),
                        gasto.getCosto()
                }));
    }
}