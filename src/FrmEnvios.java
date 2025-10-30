import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import datos.EnMemoriaEnvioRepository;
import datos.EnvioRepository;
import entidades.Envio;
import entidades.MedioTransporte;
import estilos.TablaRendererZebra;
import estilos.UiTheme;
import servicios.CalculadoraTarifa;
import servicios.CalculadoraTarifaBase;
import servicios.EnvioService;

public class FrmEnvios extends JFrame {

    private final EnvioRepository repo = new EnMemoriaEnvioRepository();
    private final CalculadoraTarifa calculadora = new CalculadoraTarifaBase();
    private final EnvioService service = new EnvioService(repo, calculadora);

    private final JTextField txtCliente = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtPeso = new JTextField();
    private final JTextField txtDistancia = new JTextField();
    private final JComboBox<MedioTransporte> cmbMedio = new JComboBox<>();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[] { "Código", "Cliente", "Peso (kg)", "Distancia (km)", "Medio", "Tarifa ($)" }, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public FrmEnvios() {
        UiTheme.apply();
        setTitle("ServiEntregan't");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(UiTheme.cardBorder(), "Gestión de Envíos"));

        cmbMedio.setModel(new DefaultComboBoxModel<>(MedioTransporte.values()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Código (opcional): si se deja vacío se autogenera
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Código (opcional):", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        form.add(new JLabel("Cliente:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        form.add(new JLabel("Peso (kg):", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtPeso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        form.add(new JLabel("Distancia (km):", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtDistancia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        form.add(new JLabel("Medio:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(cmbMedio, gbc);

        // Botones
        JPanel acciones = new JPanel();
        JButton btnAgregar = new JButton("Agregar", icono("/iconos/agregar.png"));
        btnAgregar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnAgregar.setIconTextGap(6);
        btnAgregar.setBackground(UiTheme.ACENTO);

        JButton btnEliminar = new JButton("Eliminar", icono("/iconos/eliminar.png"));
        btnEliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnEliminar.setIconTextGap(6);
        btnEliminar.setBackground(UiTheme.PRIMARIO);

        JButton btnListar = new JButton("Refrescar", icono("/iconos/refrescar.png"));
        btnListar.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnListar.setIconTextGap(6);
        btnListar.setBackground(UiTheme.PRIMARIO);

        acciones.add(btnAgregar);
        acciones.add(btnEliminar);
        acciones.add(btnListar);

        btnAgregar.addActionListener(this::onAgregar);
        btnEliminar.addActionListener(this::onEliminar);
        btnListar.addActionListener(e -> refrescarTabla());

        // Tabla
        JScrollPane sp = new JScrollPane(tabla);
        tabla.setFillsViewportHeight(true);

        tabla.setRowHeight(28);
        tabla.setDefaultRenderer(Object.class, new TablaRendererZebra());
        tabla.getTableHeader().setReorderingAllowed(false);

        add(form, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);
    }

    private void onAgregar(ActionEvent e) {
        try {
            service.crearEnvio(
                    txtCodigo.getText(),
                    txtCliente.getText(),
                    txtPeso.getText(),
                    txtDistancia.getText(),
                    (MedioTransporte) cmbMedio.getSelectedItem());
            limpiarFormulario();
            refrescarTabla();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEliminar(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un envío en la tabla.");
            return;
        }
        String codigo = (String) modelo.getValueAt(fila, 0);
        if (service.eliminar(codigo)) {
            refrescarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible eliminar el envío.");
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        cmbMedio.setSelectedIndex(0);
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);
        List<Envio> lista = service.listar();
        for (Envio e : lista) {
            modelo.addRow(new Object[] {
                    e.getCodigo(),
                    e.getCliente(),
                    e.getPesoKg(),
                    e.getDistanciaKm(),
                    e.getMedio(),
                    String.format("%,.0f", e.getTarifa())
            });
        }
    }

    // Escala los íconos a 20x20 píxeles
    private static ImageIcon icono(String ruta) {
        java.net.URL url = FrmEnvios.class.getResource(ruta);
        if (url == null)
            return null;
        Image img = new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
