package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImagePanel extends JPanel {
    private BufferedImage backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("main/resources/images/background.png")));
        } catch (IOException | NullPointerException e) {
            System.err.println("No se pudo cargar la imagen: " + e.getMessage());
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Dibuja la imagen escalada para ajustarla al panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}