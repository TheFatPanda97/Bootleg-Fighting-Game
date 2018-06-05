import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Bar extends JLabel {

    final int RHP_XMOVE = 6;
    final int LHP_XMOVE = 7;
    final int HP_Y = 16;
    final int HP_WIDTH = 300;
    final int HP_HEIGHT = 35;

    final int RMGC_XMOVE = 4;
    final int LMGC_XMOVE = 109;
    final int MGC_Y = 16;
    final int MGC_WIDTH = 200;
    final int MGC_HEIGHT = 25;

    static final int MGC_ADD = 20;

    int whichPlayerNum;

    ImageIcon RBar = new ImageIcon("src/Resource/Status/RBar.png");
    ImageIcon LBar = new ImageIcon("src/Resource/Status/LBar.png");

    ImageIcon RWizFace = new ImageIcon("src/Resource/Mugshots/RWizFace.png");
    ImageIcon LWizFace = new ImageIcon("src/Resource/Mugshots/LWizFace.png");

    ImageIcon RobFace = new ImageIcon("src/Resource/Mugshots/RobFace.png");
    ImageIcon KakaFace = new ImageIcon("src/Resource/Mugshots/KakaFace.png");

    JLabel mugshot = new JLabel();
    JLabel hp = new JLabel();
    JLabel magic = new JLabel();

    //  Timer magicTimer;


    Bar(int WPN, boolean[] whichPlayer) {

        whichPlayerNum = WPN;
        setOpaque(false);


        if (whichPlayerNum == 1) {

            setBounds(0, 0, RBar.getIconWidth(), RBar.getIconHeight());
            setIcon(RBar);


            if (whichPlayer[0]) {

                mugshot.setBounds(0, 0, RWizFace.getIconWidth(), RWizFace.getIconHeight());
                mugshot.setIcon(RWizFace);


            } else if (whichPlayer[1]) {


                mugshot.setBounds(0, 0, RobFace.getIconWidth(), RobFace.getIconHeight());
                mugshot.setIcon(RobFace);

            } else if (whichPlayer[2]) {


                mugshot.setBounds(0, 0, KakaFace.getIconWidth(), KakaFace.getIconHeight());
                mugshot.setIcon(KakaFace);

            }

            hp.setBackground(Color.green);
            hp.setBounds(mugshot.getWidth() + RHP_XMOVE, HP_Y, HP_WIDTH, HP_HEIGHT);
            hp.setOpaque(true);

            magic.setBackground(Color.cyan);
            magic.setBounds(mugshot.getWidth() + RMGC_XMOVE, MGC_Y + 50, 0, MGC_HEIGHT);
            magic.setOpaque(true);


        } else if (whichPlayerNum == 2) {

            setBounds(FightClub.width - LBar.getIconWidth(), 0, LBar.getIconWidth(), LBar.getIconHeight());
            setIcon(LBar);

            if (whichPlayer[0]) {

                mugshot.setBounds(getWidth() - RobFace.getIconWidth(), 0, LWizFace.getIconWidth(), LWizFace.getIconHeight());
                mugshot.setIcon(LWizFace);


            } else if (whichPlayer[1]) {


                mugshot.setBounds(getWidth() - RobFace.getIconWidth(), 0, RobFace.getIconWidth(), RobFace.getIconHeight());
                mugshot.setIcon(RobFace);

            } else if (whichPlayer[2]) {


                mugshot.setBounds(getWidth() - KakaFace.getIconWidth(), 0, KakaFace.getIconWidth(), KakaFace.getIconHeight());
                mugshot.setIcon(KakaFace);

            }


            hp.setBackground(Color.green);
            hp.setBounds(LHP_XMOVE, HP_Y, HP_WIDTH, HP_HEIGHT);
            hp.setOpaque(true);

            magic.setBackground(Color.cyan);
            magic.setBounds(LMGC_XMOVE + MGC_WIDTH, MGC_Y + 50, 0, MGC_HEIGHT);
            magic.setOpaque(true);

        }

        add(hp);
        add(magic);
        add(mugshot);

    }

    boolean dead() {

        return hp.getWidth() <= 0;


    }

    void decHP(int deduct, boolean isBlocking) {

        if (!isBlocking) {

            hp.setSize(hp.getWidth() - deduct, hp.getHeight());

            if (whichPlayerNum == 2) {

                hp.setLocation(hp.getLocation().x + deduct, hp.getY());

            }

        } else {

            hp.setSize(hp.getWidth() - deduct / 2, hp.getHeight());

            if (whichPlayerNum == 2) {

                hp.setLocation(hp.getLocation().x + deduct / 2, hp.getY());

            }

        }

    }

    void decMagic(int deduct) {

        if (magic.getWidth() - deduct <= 0) {

            magic.setSize(0, MGC_HEIGHT);

        } else {

            magic.setSize(magic.getWidth() - deduct, MGC_HEIGHT);

        }


        if (whichPlayerNum == 2) {

            if (magic.getWidth() <= 0) {

                magic.setLocation(LMGC_XMOVE + MGC_WIDTH, magic.getY());

            } else {

                magic.setLocation(magic.getLocation().x + deduct, magic.getY());

            }

        }

    }

    void addMagic() {

        if (magic.getWidth() + MGC_ADD >= MGC_WIDTH) {

            magic.setSize(MGC_WIDTH, MGC_HEIGHT);

        } else {

            magic.setSize(magic.getWidth() + MGC_ADD, MGC_HEIGHT);

        }


        if (whichPlayerNum == 2) {

            if (magic.getWidth() >= MGC_WIDTH) {

                magic.setLocation(LMGC_XMOVE, magic.getY());

            } else {

                magic.setLocation(magic.getLocation().x - MGC_ADD, magic.getY());

            }

        }

    }

    void addMagic(int a) {

        if (magic.getWidth() + a >= MGC_WIDTH) {

            magic.setSize(MGC_WIDTH, MGC_HEIGHT);

        } else {

            magic.setSize(magic.getWidth() + a, MGC_HEIGHT);

        }


        if (whichPlayerNum == 2) {

            if (magic.getWidth() >= MGC_WIDTH) {

                magic.setLocation(LMGC_XMOVE, magic.getY());

            } else {

                magic.setLocation(magic.getLocation().x - a, magic.getY());

            }

        }

    }

    void firstBlood() {

        magic.setSize(magic.getWidth() + MGC_ADD * 2, MGC_HEIGHT);

        if (whichPlayerNum == 2) {

            magic.setLocation(magic.getLocation().x - MGC_ADD * 2, magic.getY());

        }

    }

    boolean maxMagic() {

        return magic.getWidth() >= MGC_WIDTH;

    }

    boolean hasMagic(int enough) {

        if (magic.getWidth() >= enough) {

            return true;

        }

        return false;

    }


}
