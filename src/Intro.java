import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Intro extends AllWindows {

    ImageIcon IntroScreen = new ImageIcon("src/Resource/Screen_Background/Intro Screen.gif");
    ImageIcon imgHighlight = new ImageIcon("src/Resource/Decoration/Button Highlight.png");

    JLabel lblHighlight = new JLabel();
    Button btnStart = new Button(0);
    Button btnExit = new Button(1);
    Button btnHigh = new Button(3);

    final int DISTANCE = 30;

    Intro() {

        Main.rw.readFile();

        background.setBounds(0, 0, width, height);
        background.setIcon(imgRescaler(IntroScreen, width, height));

        lblHighlight.setIcon(imgHighlight);
        lblHighlight.setBounds(DX, DY, imgHighlight.getIconWidth(), imgHighlight.getIconHeight());

        btnStart.setBounds(DISTANCE, 450, 250, 80);
        btnStart.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                setVisible(false);
                Main.CSelectWindow.setVisible(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                centerSetter(lblHighlight, btnStart);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                remove(lblHighlight);

            }
        });

        btnHigh.setBounds(btnStart.getX() + btnStart.getWidth() + DISTANCE, 450, 250, 80);
        btnHigh.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                setVisible(false);
                Main.ScoreWindow.updateScore();
                Main.ScoreWindow.setVisible(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                centerSetter(lblHighlight, btnHigh);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                remove(lblHighlight);

            }
        });

        btnExit.setBounds(btnHigh.getX() + btnHigh.getWidth() + DISTANCE, 450, 250, 80);
        btnExit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {


                if (Main.CSelectWindow.downloadSuccess) {

                    Main.rw.DLC = true;
                    Main.rw.writeMessage("SHAWN SO SMART O GOD");

                }
                System.exit(0);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                centerSetter(lblHighlight, btnExit);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                remove(lblHighlight);

            }
        });


        add(lblHighlight);
        add(btnStart);
        add(btnExit);
        add(btnHigh);
        add(background);

    }

    void remove(JLabel a) {

        a.setLocation(DX, DY);

    }


}
