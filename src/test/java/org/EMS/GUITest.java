package org.EMS;

import org.EMS.BackEnd.GUI.GUI;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GUITest {

    private FrameFixture window;
    private GUI gui;

    @BeforeAll
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install(); // Ensures Swing runs on the Event Dispatch Thread (EDT)
    }

    @BeforeEach
    public void setup() {
        SwingUtilities.invokeLater(() -> {
            gui = new GUI(); // ✅ Ensure GUI is initialized
        });

        try {
            Thread.sleep(1500); // ✅ Wait a bit for GUI to initialize & ✅ Give enough time for GUI to initialize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull(gui.getFrame(), "❌ Frame should not be null"); // ✅ Prevent NullPointerException
        window = new FrameFixture(gui.getFrame()); // ✅ Get the GUI frame
        window.show(); // ✅ Show the GUI before running tests
    }

    @Test
    @Order(1)
    public void testTitle() {
        JFrame frame = gui.getFrame();
        assertNotNull(frame, "❌ Frame should not be null");
        assertEquals("Employee Management System", frame.getTitle(), "❌ Title should be 'Employee Management System'");
        System.out.println("✅ testTitle PASSED");
    }

    @Test
    @Order(2)
    public void testLabelExists() {
        window.label(JLabelMatcher.withText("Employee Management System")).requireText("Employee Management System");
        System.out.println("✅ testLabelExists PASSED");
    }



    @AfterEach
    public void tearDown() {
        if (window != null) {
            window.cleanUp(); // ✅ Close the GUI after each test
        }
    }
}
