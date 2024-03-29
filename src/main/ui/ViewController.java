package ui;

import model.*;

import javax.swing.*;

public class ViewController {
    private static ViewController INSTANCE;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String JSON_STORE = "./data/mypets.json";

    private final JFrame frame;
    private MyPets myPets;
    private ViewAbstract currentPanel;

    private ViewController() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() {
        currentPanel = new LoadView();
        frame.setTitle(currentPanel.getFrameTitle());
        frame.add(currentPanel);
    }

    public void navigateTo(ViewAbstract newPanel) {
        frame.remove(currentPanel);
        currentPanel = newPanel;
        frame.setTitle(newPanel.getFrameTitle());
        frame.add(currentPanel);

        frame.revalidate();
        frame.repaint();
    }

    public static ViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewController();
        }
        return INSTANCE;
    }

    public MyPets getMyPets() {
        return this.myPets;
    }

    public void setMyPets(MyPets myPets) {
        this.myPets = myPets;
    }
}