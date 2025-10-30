package estilos;

// === estilos/UiTheme.java ===
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;

public final class UiTheme {
    // Colores
    public static final Color PRIMARIO = new Color(0x2E7D32); // verde medio
    public static final Color PRIMARIO_OSC = new Color(0x1B5E20); // verde oscuro
    public static final Color ACENTO = new Color(0x81C784); // verde claro (para destacar)
    public static final Color FONDO = new Color(0xF4F6F3); // fondo gris verdoso suave
    public static final Color CARTA = Color.WHITE;
    public static final Color TEXTO = new Color(0x2F332E); // casi negro
    public static final Color TEXTO_SUAVE = new Color(0x5A5F58);
    public static final Color BORDE_SUAVE = new Color(0xDDE2DA);
    public static final Color ZEBRA_1 = new Color(0xF7FAF6);
    public static final Color ZEBRA_2 = new Color(0xE8F5E9);
    public static final Color SELECCION = new Color(0xC8E6C9);

    public static final Font FONT_BASE = new Font("Arial", Font.PLAIN, 13);
    public static final Font FONT_BOLD = FONT_BASE.deriveFont(Font.BOLD);

    private UiTheme() {
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_SUAVE),
                BorderFactory.createEmptyBorder(8, 10, 10, 10));
    }

    public static void apply() {
        UIDefaults d = UIManager.getLookAndFeelDefaults();

        // Colores base
        UIManager.put("Panel.background", FONDO);
        UIManager.put("OptionPane.background", FONDO);
        UIManager.put("OptionPane.messageForeground", TEXTO);
        UIManager.put("Label.foreground", TEXTO);

        // Botones
        UIManager.put("Button.background", PRIMARIO);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.select", PRIMARIO_OSC);

        // Tabla
        UIManager.put("Table.background", CARTA);
        UIManager.put("Table.foreground", TEXTO);
        UIManager.put("Table.gridColor", BORDE_SUAVE);
        UIManager.put("Table.selectionBackground", SELECCION);
        UIManager.put("Table.selectionForeground", TEXTO);
        UIManager.put("TableHeader.background", PRIMARIO);
        UIManager.put("TableHeader.foreground", Color.WHITE);

        // Fuentes
        UIManager.put("defaultFont", FONT_BASE);
        UIManager.put("Label.font", FONT_BASE);
        UIManager.put("Button.font", FONT_BOLD);
        UIManager.put("Table.font", FONT_BASE);
        UIManager.put("TableHeader.font", FONT_BOLD);
    }
}
