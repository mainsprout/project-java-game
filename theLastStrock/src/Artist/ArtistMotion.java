package Artist;

import javax.swing.*;

public class ArtistMotion {

    private ImageIcon[] stopMotion = {
            new ImageIcon("images/artist/walk_right_1.png")
    };

    private ImageIcon[] rightMotion = {
            new ImageIcon("images/artist/walk_right_1.png"),
            new ImageIcon("images/artist/walk_right_2.png"),
            new ImageIcon("images/artist/walk_right_3.png"),
            new ImageIcon("images/artist/walk_right_4.png"),
            new ImageIcon("images/artist/walk_right_5.png"),
            new ImageIcon("images/artist/walk_right_6.png"),
            new ImageIcon("images/artist/walk_right_7.png")
    };
    private ImageIcon[] leftMotion = {
            new ImageIcon("images/artist/walk_left_1.png"),
            new ImageIcon("images/artist/walk_left_2.png"),
            new ImageIcon("images/artist/walk_left_3.png"),
            new ImageIcon("images/artist/walk_left_4.png"),
            new ImageIcon("images/artist/walk_left_5.png"),
            new ImageIcon("images/artist/walk_left_6.png"),
            new ImageIcon("images/artist/walk_left_7.png")
    };
    private ImageIcon[] drawMotion = {
            new ImageIcon("images/artist/drawing_1.png"),
            new ImageIcon("images/artist/drawing_2.png")
    };

    public ImageIcon[] getMotion(int motion){
        if(motion == 1){
            return rightMotion;
        }
        else if(motion == 2){
            return leftMotion;
        }
        else if(motion == 3){
            return drawMotion;
        }
        return stopMotion;
    }
}
