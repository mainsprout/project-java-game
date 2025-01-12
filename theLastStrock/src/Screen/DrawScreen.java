package Screen;

import Listener.SizeSliderListener;
import Listener.ToolButtonListener;
import Listener.clickSound;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import static Screen.MainScreen.setYesAdapter;

public class DrawScreen extends JPanel {
    private JPanel tool = new JPanel();
    private JPanel sizeBar = new JPanel();
    private JPanel colorPalette = new JPanel();
    private BufferedImage bufferedImage;
    private ImagePanel imagePanel;
    private JButton xButton;
    private JButton saveButton;


    private Color[] buttonColors_1 = {new Color(0x00A5E3),new Color(0x8DD7BF),
            new Color(0xFF96C5),new Color(0xFF5768), new Color(0xFFBF65),
    new Color(0xC05780), new Color(0x74737A)};

    public DrawScreen() {
        setLayout(null);
        saveButton = new JButton("SAVE");
        saveButton.setBounds(610, 20, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "여기까지 기억 할래?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    Image tmp = bufferedImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    BufferedImage dimg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g2d = dimg.createGraphics();
                    g2d.drawImage(tmp, 0, 0, null);
                    g2d.dispose();
                    MyModalDialog.newPaintSave(dimg);

                    setImageBackground(bufferedImage, Color.white);
                    imagePanel.repaint();
                    setVisible(false);
                    setYesAdapter();
                }
            }
        });
        add(saveButton);

        xButton = new JButton("x");
        xButton.setBounds(720, 20, 50, 30);
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "그만 둘까?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    setImageBackground(bufferedImage, Color.white);
                    imagePanel.repaint();
                    setVisible(false);
                    setYesAdapter();
                }
            }
        });
        add(xButton);

        bufferedImage = new BufferedImage(520, 600, BufferedImage.TYPE_INT_ARGB);
        setImageBackground(bufferedImage, Color.white);

        imagePanel = new ImagePanel(bufferedImage);
        imagePanel.setBounds(30, 60, 520, 600);
        add(imagePanel);

        setupToolPanel();
        setupSizeBar();
        setupColorPalette();

        add(tool);
        add(sizeBar);
        add(colorPalette);
    }

    private void setupToolPanel() {
        tool.setLayout(null);
        tool.setBounds(570, 60, 200, 200);
        tool.setBackground(new Color(0x586AE2));

        JLabel toolName = new JLabel("어쩐지 익숙하다");
        toolName.setForeground(new Color(0xE0D9F6));
        toolName.setHorizontalAlignment(JLabel.CENTER);
        toolName.setBounds(5, 10, 200, 20);
        tool.add(toolName);

        JButton penButton = new JButton(new ImageIcon("images/icon/brush.png"));
        penButton.setToolTipText("펜");
        penButton.setBackground(Color.white);
        penButton.setBounds(7, 45, 60, 60);
        penButton.addActionListener(new ToolButtonListener(imagePanel));
        penButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clickSound.clickSound();
            }
        });
        tool.add(penButton);

        JButton eraseButton = new JButton(new ImageIcon("images/icon/eraser.png"));
        eraseButton.setToolTipText("지우개");
        eraseButton.setBackground(Color.white);
        eraseButton.setBounds(70, 45, 60, 60);
        eraseButton.addActionListener(new ToolButtonListener(imagePanel));
        eraseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clickSound.clickSound();
            }
        });
        tool.add(eraseButton);

        JButton allPaint = new JButton(new ImageIcon("images/icon/paint_burket.png"));
        allPaint.setToolTipText("페인트통");
        allPaint.setBackground(Color.white);
        allPaint.setBounds(133, 45, 60, 60);
        allPaint.addActionListener(new ToolButtonListener(imagePanel));
        allPaint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clickSound.clickSound();
            }
        });
        allPaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageBackground(bufferedImage, ImagePanel.getColor());
                imagePanel.repaint();
                ImagePanel.upDate();
            }
        });
        tool.add(allPaint);

        JButton allErase = new JButton("전부 지우기");
        allErase.setBounds(7, 115, 186, 30);
        allErase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageBackground(bufferedImage, Color.WHITE);
                imagePanel.repaint();
                ImagePanel.upDate();
            }
        });
        tool.add(allErase);

    }

    private void setupSizeBar() {
        sizeBar.setBounds(570, 280, 200, 100);
        sizeBar.setBackground(new Color(0xC252E1));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 30, 5);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(new Color(0xC252E1));
        slider.setForeground(Color.WHITE);
        slider.setOpaque(true);
        slider.setUI(new ImagePanel.CustomSliderUI());
        slider.addChangeListener(new SizeSliderListener(imagePanel));

        sizeBar.add(slider);
    }

    private void setImageBackground(BufferedImage bi, Color color) {
        this.bufferedImage = bi;
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.dispose();
    }

    private void setupColorPalette() {
        colorPalette.setLayout(null);
        colorPalette.setBounds(570, 400, 200, 260);
        colorPalette.setBackground(new Color(0xE0D9F6));

        JLabel paletteName = new JLabel("기억나는 색깔");
        paletteName.setForeground(new Color(0x2A2356));
        paletteName.setHorizontalAlignment(JLabel.CENTER);
        paletteName.setBounds(5, 10, 200, 20);
        colorPalette.add(paletteName);

        for(int i=0; i < buttonColors_1.length; i++){
            customButton b1 = new customButton(buttonColors_1[i]);
            b1.setBounds(15+i*25, 40, 20, 25);
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    imagePanel.setColor(b1.buttonColor);
                }
            });
            b1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    b1.setSize(new Dimension(18, 22));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    b1.setSize(new Dimension(20, 25));
                }
            });
            colorPalette.add(b1);
        }

        JButton moreColor = new JButton("더 많은 색");
        moreColor.setBounds(7, 130, 186, 30);
        moreColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(null, "Color", Color.white);
                imagePanel.setColor(selectedColor);
            }
        });
        colorPalette.add(moreColor);


    }

    public class customButton extends JButton {
        private Color buttonColor;

        public customButton(Color color) {
            super();
            this.buttonColor = color;
            decorate();
        }

        protected void decorate() {
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int width = getWidth();
            int height = getHeight();

            Graphics2D graphics = (Graphics2D) g;

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(buttonColor);
            graphics.fillRoundRect(0, 0, width, height, 10, 10);

            graphics.dispose();

            super.paintComponent(g);
        }

    }




}


