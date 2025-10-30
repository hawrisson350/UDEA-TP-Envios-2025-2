package estilos;

// === estilos/TablaRendererZebra.java ===
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TablaRendererZebra extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            c.setBackground((row % 2 == 0) ? UiTheme.ZEBRA_1 : UiTheme.ZEBRA_2);
            c.setForeground(UiTheme.TEXTO);
        } else {
            c.setBackground(UIManager.getColor("Table.selectionBackground"));
            c.setForeground(UIManager.getColor("Table.selectionForeground"));
        }

        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            jc.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.BORDE_SUAVE));
        }
        return c;
    }
}
