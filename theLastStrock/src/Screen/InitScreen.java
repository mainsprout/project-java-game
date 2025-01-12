package Screen;

import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import Listener.clickSound;

public class InitScreen extends JFrame {
    private JLabel initBackground = new JLabel();
    private JButton startButton = new JButton(new ImageIcon("images/component/startBar.png"));
    private ImageIcon C_startButton = new ImageIcon("images/component/C_startBar.png");
    private JButton quitButton = new JButton(new ImageIcon("images/component/quitBar.png"));
    private ImageIcon C_quitButton = new ImageIcon("images/component/C_quitBar.png");

    private Clip clip;

    public InitScreen(){
        super("the Last Stroke");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initComponents();

        try {
            clip = AudioSystem.getClip();
            File audioFile = new File("music/init_music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        setVisible(true);
    }

    public void initComponents(){
        initBackground.setIcon(new ImageIcon("images/background/init_background.png"));
        initBackground.setBounds(0, 0, 1200, 720);
        add(initBackground);

        startButton.setBounds(410, 370, 337, 73);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        initBackground.add(startButton);
        startButton.setRolloverIcon(C_startButton);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                clip.close();
                new MainScreen();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                clickSound.clickSound();
            }
        });

        quitButton.setBounds(410, 450, 337, 73);
        quitButton.setBorderPainted(false);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        initBackground.add(quitButton);
        quitButton.setRolloverIcon(C_quitButton);
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                clickSound.clickSound();
            }
        });
    }





}
