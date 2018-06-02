import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JLabel {

    ImageIcon[][] allButtonImg = new ImageIcon[4][2];
    ImageIcon SStart = new ImageIcon(getClass().getResource("SStart.png"));
    ImageIcon HStart = new ImageIcon(getClass().getResource("HStart.png"));
    ImageIcon SExit = new ImageIcon(getClass().getResource("SExit.png"));
    ImageIcon HExit = new ImageIcon(getClass().getResource("HExit.png"));
    ImageIcon SMain = new ImageIcon(getClass().getResource("SMain.png"));
    ImageIcon HMain = new ImageIcon(getClass().getResource("HMain.png"));
    ImageIcon SHigh = new ImageIcon(getClass().getResource("SHigh.png"));
    ImageIcon HHigh = new ImageIcon(getClass().getResource("HHigh.png"));



    Button(int set) {

        setSize(250, 80);
        setAllButtonImg();
        setIcon(allButtonImg[set][0]);

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

                setIcon(allButtonImg[set][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                setIcon(allButtonImg[set][0]);

            }
        });

    }

    void setAllButtonImg() {

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
