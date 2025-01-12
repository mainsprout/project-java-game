package Screen;

import javax.swing.*;
import java.awt.*;

public class HappyEnding extends JFrame {
    private JLabel happy;

    public HappyEnding(){
        super("Ending");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        happy = new JLabel("재%부/*팅 중.?..");
        happy.setFont(new Font("Serif", Font.PLAIN, 20));
        happy.setHorizontalAlignment(JLabel.CENTER);

        happy.setBounds(0, Main.SCREEN_HEIGHT / 2 - 10, Main.SCREEN_WIDTH, 20);

        add(happy);
        setVisible(true);
    }
}
