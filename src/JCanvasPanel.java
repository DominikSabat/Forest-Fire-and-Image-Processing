import javax.swing.*;
import java.awt.*;


public class JCanvasPanel extends JPanel {

    DataManager dm;

    public JCanvasPanel(DataManager dm) {
        this.dm = dm;
    }

    @Override
        public void paint(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(dm.img, 0, 0, this);
        }

    @Override
    public void repaint() {
        super.repaint();
    }
}