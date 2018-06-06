import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JLabel {

    private ImageIcon[][] allButtonImg = new ImageIcon[4][2];
    private ImageIcon SStart = new ImageIcon("src/Resource/Buttons/SStart.png");
    private ImageIcon HStart = new ImageIcon("src/Resource/Buttons/HStart.png");
    private ImageIcon SExit = new ImageIcon("src/Resource/Buttons/SExit.png");
    private ImageIcon HExit = new ImageIcon("src/Resource/Buttons/HExit.png");
    private ImageIcon SMain = new ImageIcon("src/Resource/Buttons/SMain.png");
    private ImageIcon HMain = new ImageIcon("src/Resource/Buttons/HMain.png");
    private ImageIcon SHigh = new ImageIcon("src/Resource/Buttons/SHigh.png");
    private ImageIcon HHigh = new ImageIcon("src/Resource/Buttons/HHigh.png");


    public Button(int set) {

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
