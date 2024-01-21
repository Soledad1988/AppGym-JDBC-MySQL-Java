package vista;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import controller.ReporteMensualController;
import gym.modelo.Gastos;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class ReporteGananciaPerdida extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaIngresos;
    private JTable tablaEgresos;
    private DefaultTableModel modeloIngresos;
    private DefaultTableModel modeloEgresos;
    private ReporteMensualController reporteMensual;
    private JTextField textTotal;
   
    
    
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

    public ReporteGananciaPerdida() throws SQLException {
    	super("Reporte del Mes");
    	this.reporteMensual = new ReporteMensualController();
    	
    	Container container = getContentPane();
        getContentPane().setLayout(null);
        
    	configurarTablaDeContenidoGastos(container);
    	
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

                limpiarTabla();
                cargarTablaGastosPorMes(numeroMes);
                cargarTablaIngresoPorMes(numeroMes);
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
           
           tablaIngresos = new JTable();
           tablaIngresos.setBounds(10, 129, 760, 164);
           getContentPane().add(tablaIngresos);
           
           
           JLabel lblTotalDeEgresos = new JLabel("Total de Egresos");
           lblTotalDeEgresos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
           lblTotalDeEgresos.setBounds(10, 288, 218, 36);
           getContentPane().add(lblTotalDeEgresos);
    	
        // Agregar un ActionListener al botón para manejar la acción
        btnCalcularGananciaPerdida.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        	        // Obtener el mes seleccionado del JComboBox
        	        int numeroMes = BoxPeriodo.getSelectedIndex() + 1;

        	        // Llamar a la función obtenerBalancePorMes y mostrar el resultado en textTotal
        	        double resultadoBalance = 0;
        	        try {
        	            resultadoBalance = reporteMensual.obtenerBalancePorMes(numeroMes);
        	        } catch (SQLException e1) {
        	            e1.printStackTrace();
        	            // Opcionalmente, puedes manejar el error de manera más amigable para el usuario
        	            JOptionPane.showMessageDialog(null, "Error al calcular el balance: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	        }
        	        textTotal.setText(String.format("%.2f", resultadoBalance)); // Formatea a dos decimales
        	    }
        	});
    }
    
    private void limpiarTabla() {
        modeloIngresos.setRowCount(0);
        modeloEgresos.setRowCount(0);
    }
    
    
    private void cargarTablaGastosPorMes(int numeroMes) {
        List<Gastos> gastos = new ArrayList<>();

        try {
            gastos = this.reporteMensual.listarGastosPorMes(numeroMes);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gastos.forEach(gasto -> modeloEgresos.addRow(
                new Object[] {
                        gasto.getFechaGasto(),
                        gasto.getNombreGasto(),
                        gasto.getDescripcion(),
                        gasto.getCosto()
                }));
    }
    
    private void cargarTablaIngresoPorMes(int numeroMes) {

        List<Map<String, String>> clientes = new ArrayList<>();

        try {
            clientes = this.reporteMensual.reporteCuotasPagadasPorMes(numeroMes);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clientes.forEach(cliente -> modeloIngresos.addRow(
                new Object[] {
                        cliente.get("Fecha Pago"),
                        cliente.get("Nombre"),
                        cliente.get("Apellido"),
                        cliente.get("Monto"),
                        cliente.get("Estado")
                }));
    }
    
    private void configurarTablaDeContenidoGastos(Container container) {
    	// Configuración del modelo y la tabla de ingresos
        modeloIngresos = new DefaultTableModel();
        modeloIngresos.addColumn("Fecha Pago");
        modeloIngresos.addColumn("Nombre");
        modeloIngresos.addColumn("Apellido");
        modeloIngresos.addColumn("Monto");

        tablaIngresos = new JTable(modeloIngresos);
        tablaIngresos.setBounds(10, 129, 760, 164);
        JScrollPane scrollPaneIngresos = new JScrollPane(tablaIngresos);
        scrollPaneIngresos.setBounds(10, 129, 760, 164);
        container.add(scrollPaneIngresos);

        // Configuración del modelo y la tabla de egresos
        modeloEgresos = new DefaultTableModel();
        modeloEgresos.addColumn("Periodo Gasto");
        modeloEgresos.addColumn("Nombre Gasto");
        modeloEgresos.addColumn("Descripcion");
        modeloEgresos.addColumn("Costo");

        tablaEgresos = new JTable(modeloEgresos);
        tablaEgresos.setBounds(10, 322, 760, 164);
        JScrollPane scrollPaneEgresos = new JScrollPane(tablaEgresos);
        scrollPaneEgresos.setBounds(10, 322, 760, 164);
        container.add(scrollPaneEgresos);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    

  
}