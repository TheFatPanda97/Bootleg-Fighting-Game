import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Bar extends JLabel {

    private final int RHP_XMOVE = 6;
    private final int LHP_XMOVE = 7;
    private final int HP_Y = 16;
    private final int HP_WIDTH = 300;
    private final int HP_HEIGHT = 35;

    private final int RMGC_XMOVE = 4;
    private final int LMGC_XMOVE = 109;
    private final int MGC_Y = 16;
    private final int MGC_WIDTH = 200;
    private final int MGC_HEIGHT = 25;

    public static final int MGC_ADD = 20;

    private int whichPlayerNum;

    private ImageIcon RBar = new ImageIcon("src/Resource/Status/RBar.png");
    private ImageIcon LBar = new ImageIcon("src/Resource/Status/LBar.png");

    private ImageIcon RWizFace = new ImageIcon("src/Resource/Mugshots/RWizFace.png");
    private ImageIcon LWizFace = new ImageIcon("src/Resource/Mugshots/LWizFace.png");

    private ImageIcon RobFace = new ImageIcon("src/Resource/Mugshots/RobFace.png");
    private ImageIcon KakaFace = new ImageIcon("src/Resource/Mugshots/KakaFace.png");

    private JLabel mugshot = new JLabel();
    public JLabel hp = new JLabel();
    private JLabel magic = new JLabel();


    public Bar(int WPN, boolean[] whichPlayer) {

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

    public boolean dead() {

        return hp.getWidth() <= 0;


    }

    public void decHP(int deduct, boolean isBlocking) {

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

    public void decMagic(int deduct) {

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

    public void addMagic() {

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

    public void addMagic(int a) {

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

    public void firstBlood() {

        magic.setSize(magic.getWidth() + MGC_ADD * 2, MGC_HEIGHT);

        if (whichPlayerNum == 2) {

            magic.setLocation(magic.getLocation().x - MGC_ADD * 2, magic.getY());

        }

    }

    public boolean hasMagic(int enough) {

        if (magic.getWidth() >= enough) {

            return true;

        }

        return false;

    }


}
