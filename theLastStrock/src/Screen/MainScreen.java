package Screen;

import Artist.ArtistMotion;
import Listener.clickSound;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static Screen.MainScreen.*;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;

public class MainScreen extends JFrame{
    private static JLabel streetBackground = new JLabel();
    private JLabel effectBackground = new JLabel();
    public static JLabel album = new JLabel();

    private static JLabel canvas = new JLabel();
    public static JLabel temp = new JLabel();
    public static JLabel artist = new JLabel();
    public JLabel man = new JLabel();
    public JLabel cat = new JLabel();
    public JLabel battery = new JLabel();;
    static ArtistMotion motion = new ArtistMotion();
    ImageIcon[] walk_right = motion.getMotion(1);
    ImageIcon[] walk_left = motion.getMotion(2);
    static ImageIcon[] drawing = motion.getMotion(3);
    private static int index = 0;
    private int k =2;
    final int speed = 30, motionSpeed = 3;
    private static boolean enableAdapter = true;
    DrawScreen drawPanel = new DrawScreen();
    static JButton gallery;
    private MyModalDialog dialog;
    private Clip mainStreetClip;
    private Clip walking;
    private boolean start = true;
    private int D_day = 5;
    public JLabel message;
    public JLabel catmessage;
    public static Timer timer;
    public static Timer manTimer;
    public static Timer catTimer;
    public JLabel startMessage;
    public JLabel EndingMessage;
    private static int manTalk = 0;
    private static int catTalk = 0;
    public static Image returnImage = null;
    Random random = new Random();

    public MainScreen(){
        super("the Last Stroke");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        startMessage = new JLabel("기억나는 것은 아무것도 없었다.");
        startMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        startMessage.setHorizontalAlignment(JLabel.CENTER);
        startMessage.setForeground(Color.WHITE);
        startMessage.setBounds(0, 0, getWidth(), getHeight());
        startMessage.setBackground(Color.black);
        startMessage.setOpaque(true);
        startMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(startMessage);
                repaint();
            }
        });
        add(startMessage);

        EndingMessage = new JLabel("GAME OVER.");
        EndingMessage.setFont(new Font("Serif", Font.PLAIN, 20));
        EndingMessage.setHorizontalAlignment(JLabel.CENTER);
        EndingMessage.setForeground(Color.WHITE);
        EndingMessage.setBounds(0, 0, getWidth(), getHeight());
        EndingMessage.setBackground(Color.black);
        EndingMessage.setOpaque(true);
        EndingMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                mainStreetClip.close();
                new InitScreen();
            }
        });
        EndingMessage.setVisible(false);
        add(EndingMessage);

        MainComponents();

        timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                D_day--;
                switch (D_day){
                    case 4:
                        battery.setIcon(new ImageIcon("images/component/battery_d4.png"));
                        break;
                    case 3:
                        battery.setIcon(new ImageIcon("images/component/battery_d3.png"));
                        break;
                    case 2:
                        battery.setIcon(new ImageIcon("images/component/battery_d2.png"));
                        break;
                    case 1:
                        battery.setIcon(new ImageIcon("images/component/battery_d1.png"));
                        break;
                    case 0:
                        if(catTalk == 3 && manTalk == 2){
                            dispose();
                            new HappyEnding();
                            mainStreetClip.close();
                            break;
                        }
                        EndingMessage.setVisible(true);
                        repaint();
                        break;
                }
            }
        });
        timer.start();

        manTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setVisible(false);
                repaint();
                manTimer.stop();
            }
        });

        catTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catmessage.setVisible(false);
                repaint();
                catTimer.stop();
            }
        });

        try {
            mainStreetClip = AudioSystem.getClip();
            File audioFile = new File("music/main_music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            mainStreetClip.open(audioStream);
            mainStreetClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        setVisible(true);
    }

    public void MainComponents(){
        battery.setIcon(new ImageIcon("images/component/battery_d5.png"));
        battery.setBounds(6, 15, 396,66);
        add(battery);

        gallery = new JButton(new ImageIcon("images/icon/gallery.png"));

        message = new JLabel("뭐야");
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setBackground(Color.white);
        message.setOpaque(true);
        message.setBounds(man.getX()+10, 100, 150, 30);
        add(message);
        message.setVisible(false);

        catmessage = new JLabel("냐옹");
        catmessage.setHorizontalAlignment(JLabel.CENTER);
        catmessage.setBackground(Color.white);
        catmessage.setOpaque(true);
        catmessage.setBounds(cat.getX()+10, 100, 150, 30);
        add(catmessage);
        catmessage.setVisible(false);

        dialog = new MyModalDialog(this);
        gallery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
                requestFocusInWindow();
            }
        });
        gallery.setBounds(1120, 20, 40, 40);
        add(gallery);

        drawPanel.setBounds(getWidth()/3, 0, 800, getHeight());
        drawPanel.setBackground(new Color(255, 255, 255, 200));
        add(drawPanel);
        drawPanel.setVisible(false);

        effectBackground.setIcon(new ImageIcon("images/background/background_effect.png"));
        effectBackground.setBounds(0, 0, 6400, 720);
        add(effectBackground);

        artist.setIcon(new ImageIcon("images/artist/walk_right_1.png"));
        artist.setBounds(100, 300, 220, 360);
        add(artist);

        dialog = new MyModalDialog(this);
        album.setBounds(streetBackground.getX()+50, 200, 280, 350);
        album.setBackground(new Color(0xD2DCE6));
        album.setOpaque(true);
        album.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "그림을 전시할까?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                            dialog.setVisible(true);
                            requestFocusInWindow();
                            if(returnImage == null){
                                JOptionPane.showMessageDialog(null, "선택한 그림이 없다",
                                        "안내", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                album.setIcon(new ImageIcon(returnImage));
                                returnImage = null;
                            }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                clickSound.clickSound();
                album.setBackground(new Color(0xF2F2EB));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                album.setBackground(new Color(0xD2DCE6));
                repaint();
            }
        });
        add(album);

        cat.setIcon(new ImageIcon("images/npc/cat.png"));
        cat.setBounds(2700, 420, 148, 217);
        cat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(catTalk == 0){
                    catmessage.setVisible(true);
                    catTimer.start();
                    catmessage.setLocation(cat.getX(), 380);
                    repaint();
                    catTalk = 1;
                }else if(catTalk == 1){
                    if(catTimer.isRunning()) return;
                    catmessage.setText("배고파. 치즈 좀 그려줘");
                    catmessage.setVisible(true);
                    catTimer.start();
                    catmessage.setLocation(cat.getX(), 380);
                    repaint();
                    catTalk = 2;
                }
                else if(catTalk == 2){
                    int result = JOptionPane.showConfirmDialog(null,
                            "치즈 그림을 건넬까?", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dialog.setVisible(true);
                        requestFocusInWindow();
                        if(returnImage != null) catTalk = 3;
                    }
                } else if (catTalk == 3) {
                    if(!catmessage.isVisible()){
                        int r = random.nextInt(4);
                        String[] catTalk = {"이걸로 쥐를 잡을거야", "고마워!", "냐옹", "넌 최고의 화가야."};
                        catmessage.setText(catTalk[r]);
                        catmessage.setVisible(true);
                        catTimer.start();
                        repaint();
                    }
                }

            }
        });

        add(cat);

        man.setIcon(new ImageIcon("images/npc/man.png"));
        man.setBounds(5200, 220, 220, 440);
        man.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(manTalk == 0){
                    message.setVisible(true);
                    manTimer.start();
                    message.setLocation(man.getX(), 130);
                    repaint();
                    manTalk = 1;
                }else if(manTalk == 1){
                    int result = JOptionPane.showConfirmDialog(null,
                            "행복해지는 그림을 건넬까?", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dialog.setVisible(true);
                        requestFocusInWindow();
                        if(returnImage != null) manTalk = 2;
                    }
                } else if (manTalk == 2) {
                    if(!message.isVisible()){
                        int r = random.nextInt(4);
                        String[] manTalk = {"오랜만이네, 이런거", "...고마워", "나도 옛날에는...", "마음에 들어"};
                        message.setText(manTalk[r]);
                        message.setVisible(true);
                        manTimer.start();
                        repaint();
                    }
                }

            }
        });
        add(man);

        temp.setBounds(115, 230, 245, 255);
        add(temp);

        canvas.setIcon(new ImageIcon("images/component/canvas.png"));
        canvas.setBounds(1040, 225, 261, 273);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!enableAdapter) return;
                int result = JOptionPane.showConfirmDialog(null,
                        "무언가 떠올려볼까?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    canvas.setIcon(new ImageIcon("images/component/canvas.png"));
                    streetBackground.setLocation(-930, streetBackground.getY());
                    effectBackground.setLocation(-930, effectBackground.getY());
                    artist.setLocation(100, artist.getY());
                    canvas.setLocation(110, canvas.getY());
                    enableAdapter = false;
                    drawPanel.setVisible(true);
                    gallery.setVisible(false);
                    timer.stop();
                    temp.setIcon(null);
                    temp.setVisible(true);
                    album.setVisible(false);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!enableAdapter) return;
                canvas.setIcon(new ImageIcon("images/component/C_canvas.png"));

                clickSound.clickSound();
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(!enableAdapter) return;
                canvas.setIcon(new ImageIcon("images/component/canvas.png"));
            }
        });
        add(canvas);

        streetBackground.setIcon(new ImageIcon("images/background/main_background.png"));
        streetBackground.setBounds(0, 0, 6400, 720);
        add(streetBackground);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!enableAdapter) return;

                int keyCode = e.getKeyCode();

                switch (keyCode){
                    case KeyEvent.VK_RIGHT,VK_D:
                        if(start){
                            playWalkingSound();
                        }
                        k++;
                        if(k % motionSpeed == 0){
                            k = 0;
                            artist.setIcon(walk_right[(index++)%7]);
                            if(artist.getX() <= getWidth()/3){
                                artist.setLocation(artist.getX()+speed, artist.getY());
                            }else if(streetBackground.getX() >= -4500){
                                //System.out.println(streetBackground.getX());
                                streetBackground.setLocation(streetBackground.getX()-speed, streetBackground.getY());
                                effectBackground.setLocation(effectBackground.getX()-speed, effectBackground.getY());
                                canvas.setLocation(canvas.getX()-speed, canvas.getY());
                                cat.setLocation(streetBackground.getX()+2700, cat.getY());
                                man.setLocation(streetBackground.getX()+5200, man.getY());
                                album.setLocation(streetBackground.getX()+50, album.getY());
                                message.setLocation(man.getX()+10, message.getY());
                                catmessage.setLocation(cat.getX()+10, 380);
                            }
                        }

                        break;
                    case KeyEvent.VK_LEFT,VK_A:
                        if(start){
                            playWalkingSound();
                        }
                        k++;
                        if(k % motionSpeed == 0){
                            k = 0;
                            artist.setIcon(walk_left[(index++)%7]);
                            if(streetBackground.getX() <= -30){
                                streetBackground.setLocation(streetBackground.getX()+speed, streetBackground.getY());
                                effectBackground.setLocation(effectBackground.getX()+speed, effectBackground.getY());
                                canvas.setLocation(canvas.getX()+speed, canvas.getY());
                                cat.setLocation(streetBackground.getX()+2700, cat.getY());
                                man.setLocation(streetBackground.getX()+5200, man.getY());
                                album.setLocation(streetBackground.getX()+50, album.getY());
                                message.setLocation(man.getX()+10, message.getY());
                                catmessage.setLocation(cat.getX()+10, 380);
                            }else if(artist.getX() > 100){
                                artist.setLocation(artist.getX()-speed, artist.getY());
                            }
                        }

                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!enableAdapter) return;

                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_RIGHT,VK_D:
                        k=2;index=0;
                        artist.setIcon(walk_right[6]);
                        walking.close();
                        start = true;
                        break;
                    case  KeyEvent.VK_LEFT,VK_A:
                        k = 2; index=0;
                        artist.setIcon(walk_left[6]);
                        walking.close();
                        start = true;
                        break;
                }

            }
        });
    }

    public static void artistDrawing(){
        artist.setIcon(drawing[(index++)%2]);
    }

    public static void setYesAdapter(){
        enableAdapter = true;
        gallery.setVisible(true);
        timer.start();
        album.setBounds(streetBackground.getX()+50, 200, 280, 350);
        album.setVisible(true);
        temp.setVisible(false);
    }

    private void playWalkingSound(){
        try {
            walking = AudioSystem.getClip();
            File audioFile = new File("music/effect/walking.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            walking.open(audioStream);
            walking.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException eu) {
            throw new RuntimeException(eu);
        } catch (IOException eu) {
            throw new RuntimeException(eu);
        } catch (LineUnavailableException eu) {
            throw new RuntimeException(eu);
        }
        start = false;
    }

    public static void setTempCanvas(Image image){
        temp.setIcon(new ImageIcon(image));
        temp.repaint();
    }

}

class MyModalDialog extends JDialog{
    private static JLabel background = new JLabel();
    private static JLabel saveImage_1;
    private static JLabel saveImage_2;
    private static JLabel saveImage_3;

    private static int num = 0;
    public MyModalDialog(JFrame frame){
        super(frame, "Main Memory", true);
        setLayout(null);
        setSize(985, 500);
        setLocationRelativeTo(null);

        JLabel galleryName = new JLabel("Memory");
        galleryName.setBounds(10, 5, 200, 45);
        galleryName.setFont(new Font("Serif", Font.BOLD, 30));
        add(galleryName);

        background.setBounds(10, 50, 945, 400);
        background.setBackground(new Color(0xE0D9F6));
        background.setOpaque(true);
        add(background);



    }

    public static void newPaintSave(Image img){
        switch (num%3){
            case 0:
                if(saveImage_1 != null) background.remove(saveImage_1);
                saveImage_1 = new JLabel(new ImageIcon(img));
                saveImage_1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        returnImage = img;
                        clickSound.clickSound();
                        Window win = SwingUtilities.getWindowAncestor(saveImage_1);
                        if (win != null) {
                            win.dispose();
                        }
                    }
                });
                saveImage_1.setBounds(10, 20, 300, 300);
                background.add(saveImage_1);
                background.repaint();
                num++;
                break;
            case 1:
                if(saveImage_2 != null) background.remove(saveImage_2);
                saveImage_2 = new JLabel(new ImageIcon(img));
                saveImage_2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        returnImage = img;
                        clickSound.clickSound();
                        Window win = SwingUtilities.getWindowAncestor(saveImage_2);
                        if (win != null) {
                            win.dispose();
                        }
                    }
                });
                saveImage_2.setBounds(320, 20, 300, 300);
                background.add(saveImage_2);
                background.repaint();
                num++;
                break;
            case 2:
                if(saveImage_3 != null) background.remove(saveImage_3);
                saveImage_3 = new JLabel(new ImageIcon(img));
                saveImage_3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        returnImage = img;
                        clickSound.clickSound();
                        Window win = SwingUtilities.getWindowAncestor(saveImage_3);
                        if (win != null) {
                            win.dispose();
                        }
                    }
                });
                saveImage_3.setBounds(630, 20, 300, 300);
                background.add(saveImage_3);
                background.repaint();
                num++;
                break;
        }
    }
}
