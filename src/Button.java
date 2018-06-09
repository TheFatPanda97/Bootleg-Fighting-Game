import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** in charge of the button in all classes*/
public class Button extends JLabel {

    //all button assets
    private ImageIcon[][] allButtonImg = new ImageIcon[4][2];
    private ImageIcon SStart = new ImageIcon("src/Resource/Buttons/SStart.png");
    private ImageIcon HStart = new ImageIcon("src/Resource/Buttons/HStart.png");
    private ImageIcon SExit = new ImageIcon("src/Resource/Buttons/SExit.png");
    private ImageIcon HExit = new ImageIcon("src/Resource/Buttons/HExit.png");
    private ImageIcon SMain = new ImageIcon("src/Resource/Buttons/SMain.png");
    private ImageIcon HMain = new ImageIcon("src/Resource/Buttons/HMain.png");
    private ImageIcon SHigh = new ImageIcon("src/Resource/Buttons/SHigh.png");
    private ImageIcon HHigh = new ImageIcon("src/Resource/Buttons/HHigh.png");

    private final int INT_WIDTH = 250;
    private final int INT_HEIGHT = 80;

    private final int IMG_STATIC = 0;
    private final int IMG_HIGH = 1;


    public Button(int set) {

        setSize(INT_WIDTH, INT_HEIGHT);
        setAllButtonImg();
        setIcon(allButtonImg[set][IMG_STATIC]);//set the initial icon of button

        //change icon of the button depending on what event is triggered
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                setIcon(allButtonImg[set][IMG_HIGH]);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                setIcon(allButtonImg[set][IMG_STATIC]);

            }
        });

    }

    //set all the images in button array
    private void setAllButtonImg() {

        allButtonImg[0][0] = SStart;
        allButtonImg[0][1] = HStart;
        allButtonImg[1][0] = SExit;
        allButtonImg[1][1] = HExit;
        allButtonImg[2][0] = SMain;
        allButtonImg[2][1] = HMain;
        allButtonImg[3][0] = SHigh;
        allButtonImg[3][1] = HHigh;

    }

}
