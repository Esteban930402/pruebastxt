package resources;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class imagePanel extends JPanel {
    private Image backgroundImage;

    public imagePanel(String rutaImagen) {
        backgroundImage = new ImageIcon(rutaImagen).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
/*

    @Override
    public void paint(Graphics g) {
        backgroundImage = new ImageIcon(getClass().getResource("/resources/GuiFiles/mainMenuBackground.jpg")).getImage();
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
    }*/
}
