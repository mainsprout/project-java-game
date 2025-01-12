package Screen;

import javax.swing.*;
import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Screen.MainScreen.artistDrawing;
import static Screen.MainScreen.setTempCanvas;

public class ImagePanel extends JPanel {
    private static BufferedImage bufferedImage;
    private Point firstPointer = new Point(0, 0);
    private Point secondPointer = new Point(0, 0);
    private String type = null;
    private int strokeSize = 5;

    public static Color getColor() {
        return color;
    }

    private static Color color = null;

    public ImagePanel(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        setupListeners();
    }

    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                firstPointer.setLocation(e.getX(), e.getY());
                secondPointer.setLocation(e.getX(), e.getY());
                artistDrawing();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                artistDrawing();
                upDate();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(type == null){
                    JOptionPane.showMessageDialog(null, "도구를 선택해주세요",
                            "안내", JOptionPane.INFORMATION_MESSAGE);
                }
                if ("펜".equals(type) || "지우개".equals(type)) {
                    secondPointer.setLocation(e.getX(), e.getY());
                    updatePaint();
                    firstPointer.setLocation(secondPointer);
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }


    public void setType(String type) {
        this.type = type;
    }

    public static void upDate(){
        Image tmp = bufferedImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        setTempCanvas(dimg);
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public void setColor(Color color){
        this.color = color;
    }

    private void updatePaint() {
        Graphics2D g = bufferedImage.createGraphics();
        if ("펜".equals(type)) {
            if(color == null){
                JOptionPane.showMessageDialog(null, "색깔을 선택해주세요",
                        "안내", JOptionPane.INFORMATION_MESSAGE);
            }
            g.setColor(color);
            g.setStroke(new BasicStroke(strokeSize));
            g.drawLine(firstPointer.x, firstPointer.y, secondPointer.x, secondPointer.y);
        }
        else if ("지우개".equals(type)){
            g.setColor(Color.white);
            g.setStroke(new BasicStroke(strokeSize));
            g.drawLine(firstPointer.x, firstPointer.y, secondPointer.x, secondPointer.y);
        }
        g.dispose();
        repaint();
    }

    public static class CustomSliderUI extends MetalSliderUI {
        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setColor(new Color(0x2A2356));
            g2D.fillRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }
    }


}
