import javax.swing.*;

import java.awt.*;

/**super class for all window classes (Intro, Fight club, and high score) */
public class All_Windows extends JFrame {

    //all window width and height
    public static int width = 1300;
    public static int height = 700;

    public final int DX = 5000;
    public final int DY = 5000;

    protected final int PNUM1 = 1;
    protected final int PNUM2 = 2;

    //number assigned to each type of player
    protected final int WIZARD = 0;
    protected final int ROBOT = 1;
    protected final int KAKASHI = 2;

    protected final int INTX = 0;
    protected final int INTY = 0;

    //number assigned to each type of button
    protected final int BTN_START = 0;
    protected final int BTN_EXIT = 1;
    protected final int BTN_MAIN = 2;
    protected final int BTN_HIGH = 3;

    private final int Y_OFFSET = 10;

    //the background of every frame
    protected JLabel background = new JLabel();

    public All_Windows() {

        setSize(width, height);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);

    }

    //resize images to correct size
    protected ImageIcon imgRescaler(ImageIcon img, int w, int h) {

        /*complete magic here, jk it grabs the desired image and set it to the assigned width and height
          works well with pixel graphic but not normal images
         */
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));

    }

    //aligns first jlabel in the middle of the second jlabel
    protected void centerSetter(JLabel a, JLabel b) {

        //beautiful math
        a.setLocation(b.getX() + (b.getWidth()) / 2 - (a.getWidth() / 2), b.getY() - a.getHeight() - Y_OFFSET);

    }

}

