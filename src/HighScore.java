import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class HighScore extends AllWindows {

    private JList List;
    private JScrollPane ScrollView = new JScrollPane();
    private ArrayList<String> items = new ArrayList<>();
    private ImageIcon IntroScreen = new ImageIcon("src/Resource/Screen_Background/Intro Screen.gif");
    private ImageIcon imgPlay = new ImageIcon("src/Resource/Decoration/Game Play.gif");


    private JLabel lblBackground = new JLabel();
    private JLabel lblGamePlay = new JLabel();

    private Button btnInfo = new Button(2);
    private final int SIDE_LENGTH = 20;


    public HighScore() {

        btnInfo.setLocation(width - SIDE_LENGTH - btnInfo.getWidth(), SIDE_LENGTH);
        btnInfo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                setVisible(false);
                Main.introWindow.setVisible(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        lblBackground.setBounds(0, 0, width, height);
        lblBackground.setIcon(imgRescaler(IntroScreen, width, height));

        ScrollView.setBounds(SIDE_LENGTH, SIDE_LENGTH, 100, 450);

        lblGamePlay.setBounds(ScrollView.getX() + ScrollView.getWidth() + 20, ScrollView.getY(), 870, ScrollView.getHeight());
        lblGamePlay.setIcon(imgRescaler(imgPlay, lblGamePlay.getWidth(), lblGamePlay.getHeight()));

        add(btnInfo);
        add(ScrollView);
        add(lblGamePlay);
        add(lblBackground);

    }

    private void setScore(Stack a) {

        items.clear();

        for (Node i = a.top; i != null; i = i.next) {

            items.add(i.name + "    " + i.score);

        }


        List = new JList(items.toArray());
        ScrollView.setViewportView(List);

    }

    public void updateScore() {

        Main.rw.readFile();
        setScore(Main.rw.getStackInfo());


    }


}
