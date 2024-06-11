package main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private GamePanel gamePanel;
    private KeyboardHandler keyH;


    @Before
    public void setUp() {
        gamePanel = new GamePanel();
        keyH = new KeyboardHandler(gamePanel);
        gamePanel.addKeyListener(keyH);
        gamePanel.setupStuff();
        gamePanel.startGameThread();
    }

    @After
    public void tearDown() {
        gamePanel = null;
        keyH = null;
    }

    @Test
    public void testDrawToScreen() throws AWTException {

        Robot robot = new Robot(); // Utilizamos Robot para simular eventos de teclado

        // Simulamos el movimiento del cursor y la selección de la opción "Nuevo juego"
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Esperamos un tiempo para que se actualice el estado del juego
        try {
            Thread.sleep(1000); // Espera 1 segundo (ajusta según sea necesario)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificamos que drawToScreen() esté dibujando correctamente en la pantalla
        // Simulamos que el componente es visible y está en un estado válido para dibujar
        JFrame frame = new JFrame();
        frame.getContentPane().add(gamePanel); // Agregamos gamePanel al JFrame
        frame.pack();
        frame.setVisible(true);

        // Llamamos a drawToScreen() ahora que gamePanel está visible
        try {
            gamePanel.drawToScreen();
        } catch (Exception e) {
            fail("Error al dibujar en la pantalla: " + e.getMessage());
        } finally {
            frame.dispose(); // Limpiamos después de la prueba
        }
    }
    @Test
    public void testPlayMusic() throws AWTException {
        // Simulamos la reproducción de música
        gamePanel.playMusic(1);

        // Esperamos un tiempo suficiente para que la música comience a reproducirse
        try {
            Thread.sleep(2000); // Espera 2 segundos (ajusta según sea necesario)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificamos que la música esté reproduciéndose
        assertTrue(gamePanel.musica.isMusicPlaying(), "La música no está reproduciéndose correctamente");

        // Esperamos un tiempo suficiente para probar la reproducción continua
        try {
            Thread.sleep(5000); // Espera 5 segundos para probar la reproducción continua
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Detenemos la música (esto dependerá de cómo implementes la parada en tu aplicación)
        gamePanel.stopMusic();

        // Esperamos un tiempo suficiente para que la música se detenga
        try {
            Thread.sleep(1000); // Espera 1 segundo (ajusta según sea necesario)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificamos que la música se haya detenido
        assertFalse(gamePanel.musica.isMusicPlaying(), "La música no se detuvo correctamente");
    }





}