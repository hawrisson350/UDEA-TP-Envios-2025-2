public class App {
    public static void main(String[] args) {
        try {
            for (var info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        javax.swing.SwingUtilities.invokeLater(() -> new FrmEnvios().setVisible(true));
    }
}