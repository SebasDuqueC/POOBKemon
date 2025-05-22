package Tests;

import domain.Movimiento;
import domain.MovimientoCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.PokemonMoveSelector;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PoobkemonTests {

    private PokemonMoveSelector selector;

    @BeforeEach
    void setUp() {
        JFrame parent = new JFrame();
        selector = new PokemonMoveSelector(parent, "Dragonite");
    }

    @Test
    void testSeleccionarExactamenteCuatroMovimientos() {
        List<JCheckBox> checkboxes = selector.moveCheckboxes;
        for (int i = 0; i < 4; i++) {
            checkboxes.get(i).setSelected(true);
        }
        selector.btnConfirmar.doClick();
        assertTrue(selector.isConfirmed());
        assertEquals(4, selector.getMovimientosSeleccionados().size());
    }

    @Test
    void testSeleccionarMenosDeCuatroMovimientos() {
        List<JCheckBox> checkboxes = selector.moveCheckboxes;
        for (int i = 0; i < 3; i++) {
            checkboxes.get(i).setSelected(true);
        }
        selector.btnConfirmar.doClick();
        assertFalse(selector.isConfirmed());
    }

    @Test
    void testSeleccionarMasDeCuatroMovimientos() {
        List<JCheckBox> checkboxes = selector.moveCheckboxes;
        for (int i = 0; i < 5; i++) {
            checkboxes.get(i).setSelected(true);
        }
        selector.btnConfirmar.doClick();
        assertFalse(selector.isConfirmed());
    }

    @Test
    void testCancelarSeleccion() {
        selector.btnCancelar.doClick();
        assertFalse(selector.isConfirmed());
    }

    // Nuevos tests

    @Test
    void testMovimientosSeleccionadosCorrectos() {
        List<JCheckBox> checkboxes = selector.moveCheckboxes;
        checkboxes.get(0).setSelected(true);
        checkboxes.get(1).setSelected(true);
        checkboxes.get(2).setSelected(true);
        checkboxes.get(3).setSelected(true);

        selector.btnConfirmar.doClick();

        List<Movimiento> movimientosSeleccionados = selector.getMovimientosSeleccionados();
        assertEquals(4, movimientosSeleccionados.size());
        assertEquals(MovimientoCatalogo.lanzallamas().getNombre(), movimientosSeleccionados.get(0).getNombre());
    }

    @Test
    void testNoSeleccionarMovimientos() {
        selector.btnConfirmar.doClick();
        assertFalse(selector.isConfirmed());
        assertTrue(selector.getMovimientosSeleccionados().isEmpty());
    }

    @Test
    void testSeleccionarMovimientosDuplicados() {
        List<JCheckBox> checkboxes = selector.moveCheckboxes;
        checkboxes.get(0).setSelected(true);
        checkboxes.get(0).setSelected(true); // Selección duplicada
        checkboxes.get(1).setSelected(true);
        checkboxes.get(2).setSelected(true);
        checkboxes.get(3).setSelected(true);

        selector.btnConfirmar.doClick();

        assertTrue(selector.isConfirmed());
        assertEquals(4, selector.getMovimientosSeleccionados().size());
    }
}