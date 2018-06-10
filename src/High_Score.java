import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//high score window
public class High_Score extends All_Windows {

    private JList List;//stores the all the recorded high scores
    private JScrollPane ScrollView = new JScrollPane();//displays the high scores
    private ArrayList<String> items = new ArrayList<>();//temporary store for the high score
    private ImageIcon IntroScreen = new ImageIcon("src/Resource/Screen_Background/Intro Screen.gif");
    private ImageIcon imgPlay = new ImageIcon("src/Resource/Decoration/Game Play.gif");

    private JLabel lblGamePlay = new JLabel();

    private Button btnMain = new Button(BTN_MAIN);

    private final int SIDE_LENGTH = 20;
    private final int SCRL_WIDTH = 100;
    private final int SCRL_HEIGHT = 450;

    private final int GAMEPLAY_OFFSET = 20;
    private final int GAMEPLAY_WIDTH = 870;

    //default constructor
    public High_Score() {

        //main menu button
        btnMain.setLocation(width - SIDE_LENGTH - btnMain.getWidth(), SIDE_LENGTH);
        btnMain.addMouseListener(new MouseListener() {
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

        //background
        background.setIcon(imgRescaler(IntroScreen, width, height));

        //displays high score
        ScrollView.setBounds(SIDE_LENGTH, SIDE_LENGTH, SCRL_WIDTH, SCRL_HEIGHT);

        //just a gif of some game play
        lblGamePlay.setBounds(ScrollView.getX() + ScrollView.getWidth() + GAMEPLAY_OFFSET, ScrollView.getY(), GAMEPLAY_WIDTH, ScrollView.getHeight());
        lblGamePlay.setIcon(imgRescaler(imgPlay, lblGamePlay.getWidth(), lblGamePlay.getHeight()));

        add(btnMain);
        add(ScrollView);
        add(lblGamePlay);
        add(background);

    }

    //adds and set new scores
    private void setScore(Stack a) {

        //clears the current high score
        items.clear();

        //adds the high score from stack
        for (Node i = a.top; i != null; i = i.next) {

            items.add(i.name + "    " + i.score);

        }

        //displays the new high scores again
        List = new JList(items.toArray());
        ScrollView.setViewportView(List);

    }

    //reads from saved file of high scores and use the above method to display it
    public void updateScore() {

        Main.rw.readFile();
        setScore(Main.rw.getStackInfo());


    }


}
