package ui;

import model.DateFormatter;
import model.MyPets;
import model.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public abstract class ViewAbstract extends JPanel {
    protected static final SimpleDateFormat SDF = DateFormatter.SDF;
    protected static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    protected ViewController viewController = ViewController.getInstance();
    protected MyPets myPets = viewController.getMyPets();
    protected String frameTitle = "";

    protected JButton getButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(90, 45));
        button.setFont(FONT);
        return button;
    }

    /*
    protected JButton getNavigationButton(String label, ViewAbstract nextView) {
        JButton navigationButton = getButton(label);
        navigationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.navigateTo(nextView);
            }
        });
        return navigationButton;
    }
    */

    public String getFrameTitle() {
        return this.frameTitle;
    }
}
