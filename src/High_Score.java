import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class High_Score extends All_Windows {

    private JList List;
    private JScrollPane ScrollView = new JScrollPane();
    private ArrayList<String> items = new ArrayList<>();
    private ImageIcon IntroScreen = new ImageIcon("src/Resource/Screen_Background/Intro Screen.gif");
    private ImageIcon imgPlay = new ImageIcon("src/Resource/Decoration/Game Play.gif");


    private JLabel lblBackground = new JLabel();
    private JLabel lblGamePlay = new JLabel();

    private Button btnInfo = new Button(BTN_MAIN);

    private final int SIDE_LENGTH = 20;
    private final int SCRL_WIDTH = 100;
    private final int SCRL_HEIGHT = 450;

    private final int GAMEPLAY_OFFSET = 20;
    private final int GAMEPLAY_WIDTH = 870;


    public High_Score() {

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


        lblBackground.setBounds(INTX, INTY, width, height);
        lblBackground.setIcon(imgRescaler(IntroScreen, width, height));

        ScrollView.setBounds(SIDE_LENGTH, SIDE_LENGTH, SCRL_WIDTH, SCRL_HEIGHT);

        lblGamePlay.setBounds(ScrollView.getX() + ScrollView.getWidth() + GAMEPLAY_OFFSET, ScrollView.getY(), GAMEPLAY_WIDTH, ScrollView.getHeight());
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
