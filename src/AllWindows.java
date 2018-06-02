import javax.swing.*;

import java.awt.*;


public class AllWindows extends JFrame {

    static int width = 1300;
    static int height = 700;
    final int DX = 5000;
    final int DY = 5000;


    JLabel background = new JLabel();

    public AllWindows() {

        setSize(width, height);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);

    }

    //resize images to correct size
    ImageIcon imgRescaler(ImageIcon img, int w, int h) {

        //complete magic here
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));

    }

    void centerSetter(JLabel a, JLabel b) {

        a.setLocation(b.getX() + (b.getWidth()) / 2 - (a.getWidth() / 2), b.getY() - a.getHeight() - 10);

    }

}

