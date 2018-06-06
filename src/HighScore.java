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
    private JLabel lblBackground = new JLabel();
    private Button btnInfo = new Button(2);
    private final int SIDE_LENGTH = 20;


    HighScore() {

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

        add(btnInfo);
        add(ScrollView);
        add(lblBackground);

    }

    void setScore(Stack a) {

        items.clear();

        for (Node i = a.top; i != null; i = i.next) {

            items.add(i.name + "    " + i.score);

        }


        List = new JList(items.toArray());
        ScrollView.setViewportView(List);

    }


//    //resize images to correct size
//    ImageIcon imgRescaler(ImageIcon img, int w, int h) {
//
//        //complete magic here
//        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
//
//    }

    void updateScore() {

        Main.rw.readFile();
        setScore(Main.rw.getStackInfo());


    }


}
