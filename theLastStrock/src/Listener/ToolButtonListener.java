package Listener;

import Screen.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ToolButtonListener implements ActionListener {
    private ImagePanel imagePanel;

    public ToolButtonListener(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String type = b.getToolTipText();
        imagePanel.setType(type);
    }
}
