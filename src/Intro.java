import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Main menu window
public class Intro extends All_Windows {

    private ImageIcon IntroScreen = new ImageIcon("src/Resource/Screen_Background/Intro Screen.gif");
    private ImageIcon imgHighlight = new ImageIcon("src/Resource/Decoration/Button Highlight.png");
    private ImageIcon imgTitle = new ImageIcon("src/Resource/Decoration/Title.png");

    private JLabel lblTitle = new JLabel();
    private JLabel lblHighlight = new JLabel();
    private Button btnStart = new Button(BTN_START);
    private Button btnExit = new Button(BTN_EXIT);
    private Button btnHigh = new Button(BTN_HIGH);

    private final int DISTANCE = 30;
    private final int BTN_Y = 450;

    //default constructor
    public Intro() {

        //reads from save file of high score
        Main.rw.readFile();

        background.setIcon(imgRescaler(IntroScreen, width, height));

        //game title
        lblTitle.setSize(imgTitle.getIconWidth(), imgTitle.getIconHeight());
        lblTitle.setIcon(imgTitle);

        lblHighlight.setIcon(imgHighlight);
        lblHighlight.setBounds(DX, DY, imgHighlight.getIconWidth(), imgHighlight.getIconHeight());

        //start button
        btnStart.setLocation(DISTANCE, BTN_Y);
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

        //high score button
        btnHigh.setLocation(btnStart.getX() + btnStart.getWidth() + DISTANCE, btnStart.getY());
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

        //exit game button
        btnExit.setLocation(btnHigh.getX() + btnHigh.getWidth() + DISTANCE, btnStart.getY());
        btnExit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {


                if (Main.CSelectWindow.getdownloadSuccess()) {

                    Main.rw.setDLC(true);
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

        //set the title to the center of the high score button
        centerSetter(lblTitle, btnHigh);

        add(lblHighlight);
        add(btnStart);
        add(btnExit);
        add(btnHigh);
        add(lblTitle);
        add(background);

    }

    //moves jlabels
    private void remove(JLabel a) {

        //moves them far, far away
        a.setLocation(DX, DY);

    }


}
