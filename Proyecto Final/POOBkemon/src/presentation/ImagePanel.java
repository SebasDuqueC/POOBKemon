package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Clase que representa un panel personalizado con una imagen de fondo.
 * Extiende la clase `JPanel` y permite cargar y mostrar una imagen de fondo
 * escalada al tamaño del panel.
 */

public class ImagePanel extends JPanel {
    private BufferedImage backgroundImage;

    /**
     * Constructor de la clase `ImagePanel`.
     * Este constructor carga una imagen de fondo desde la ruta especificada y la asigna al panel.
     * Si no se puede cargar la imagen, se imprime un mensaje de error en la consola.
     *
     * @param imagePath La ruta de la imagen de fondo que se desea cargar.
     */

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