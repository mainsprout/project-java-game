package Listener;

import Screen.ImagePanel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SizeSliderListener implements ChangeListener {
    private ImagePanel imagePanel;

    public SizeSliderListener(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int strokeSize = source.getValue();
        imagePanel.setStrokeSize(strokeSize);
    }
}
