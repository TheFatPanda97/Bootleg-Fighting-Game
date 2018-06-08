import javax.swing.*;

import java.awt.*;


public class All_Windows extends JFrame {

    public static int width = 1300;
    public static int height = 700;

    public final int DX = 5000;
    public final int DY = 5000;

    protected final int PNUM1 = 1;
    protected final int PNUM2 = 2;

    protected final int WIZARD = 0;
    protected final int ROBOT = 1;
    protected final int KAKASHI = 2;

    protected final int INTX = 0;
    protected final int INTY = 0;

    protected final int BTN_START = 0;
    protected final int BTN_EXIT = 1;
    protected final int BTN_MAIN = 2;
    protected final int BTN_HIGH = 3;

    protected JLabel background = new JLabel();

    public All_Windows() {

        setSize(width, height);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);

    }

    //resize images to correct size
    protected ImageIcon imgRescaler(ImageIcon img, int w, int h) {

        //complete magic here
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));

    }

    protected void centerSetter(JLabel a, JLabel b) {

        a.setLocation(b.getX() + (b.getWidth()) / 2 - (a.getWidth() / 2), b.getY() - a.getHeight() - 10);

    }

}

