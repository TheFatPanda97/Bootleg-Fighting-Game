import javax.swing.*;
import java.awt.*;

/**in charge of health and magic bar */
public class Bar extends JLabel {

    private final int RHP_XMOVE = 6;
    private final int LHP_XMOVE = 7;
    private final int HP_Y = 16;
    private final int HP_WIDTH = 300;
    private final int HP_HEIGHT = 35;

    private final int INTX = 0;
    private final int INTY = 0;
    private final int INT_WIDTH = 0;

    private final int DOUBLE_MGC = 2;
    private final int HALF_MGC = 2;

    private final int Y_OFFSET = 50;

    private final int RMGC_XMOVE = 4;
    private final int LMGC_XMOVE = 109;
    private final int MGC_Y = 16;
    private final int MGC_WIDTH = 200;
    private final int MGC_HEIGHT = 25;

    //number assigned to which player it is
    private final int PNUM1 = 1;
    private final int PNUM2 = 2;

    //number assigned to each type of player
    private final int WIZARD = 0;
    private final int ROBOT = 1;
    private final int KAKASHI = 2;

    public static final int MGC_ADD = 20;

    //stores which player the bar instance is
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

    // constructor
    public Bar(int WPN, boolean[] whichPlayer) {

        whichPlayerNum = WPN;
        setOpaque(false);

        //if player 1
        if (whichPlayerNum == PNUM1) {

            //set the health and magic bar to top left
            setBounds(INTX, INTY, LBar.getIconWidth(), LBar.getIconHeight());
            setIcon(LBar);

            //depending on which player is selected, the correct mugshot face will be selected
            if (whichPlayer[WIZARD]) {

                mugshot.setBounds(INTX, INTY, RWizFace.getIconWidth(), RWizFace.getIconHeight());
                mugshot.setIcon(RWizFace);


            } else if (whichPlayer[ROBOT]) {

                mugshot.setBounds(INTX, INTY, RobFace.getIconWidth(), RobFace.getIconHeight());
                mugshot.setIcon(RobFace);

            } else if (whichPlayer[KAKASHI]) {

                mugshot.setBounds(INTX, INTY, KakaFace.getIconWidth(), KakaFace.getIconHeight());
                mugshot.setIcon(KakaFace);

            }

            hp.setBackground(Color.green);
            hp.setBounds(mugshot.getWidth() + RHP_XMOVE, HP_Y, HP_WIDTH, HP_HEIGHT);
            hp.setOpaque(true);

            magic.setBackground(Color.cyan);
            magic.setBounds(mugshot.getWidth() + RMGC_XMOVE, MGC_Y + Y_OFFSET, INT_WIDTH, MGC_HEIGHT);
            magic.setOpaque(true);


        } else if (whichPlayerNum == PNUM2) {

            setBounds(Fight_Club.width - RBar.getIconWidth(), INTY, RBar.getIconWidth(), RBar.getIconHeight());
            setIcon(RBar);

            if (whichPlayer[WIZARD]) {

                mugshot.setBounds(getWidth() - RobFace.getIconWidth(), INTY, LWizFace.getIconWidth(), LWizFace.getIconHeight());
                mugshot.setIcon(LWizFace);


            } else if (whichPlayer[ROBOT]) {


                mugshot.setBounds(getWidth() - RobFace.getIconWidth(), INTY, RobFace.getIconWidth(), RobFace.getIconHeight());
                mugshot.setIcon(RobFace);

            } else if (whichPlayer[KAKASHI]) {


                mugshot.setBounds(getWidth() - KakaFace.getIconWidth(), INTY, KakaFace.getIconWidth(), KakaFace.getIconHeight());
                mugshot.setIcon(KakaFace);

            }

            hp.setBackground(Color.green);
            hp.setBounds(LHP_XMOVE, HP_Y, HP_WIDTH, HP_HEIGHT);
            hp.setOpaque(true);

            magic.setBackground(Color.cyan);
            magic.setBounds(LMGC_XMOVE + MGC_WIDTH, MGC_Y + Y_OFFSET, INT_WIDTH, MGC_HEIGHT);
            magic.setOpaque(true);

        }

        add(hp);
        add(magic);
        add(mugshot);

    }

    public boolean dead() {

        return hp.getWidth() <= INT_WIDTH;


    }

    public void decHP(int deduct, boolean isBlocking) {

        if (!isBlocking) {

            hp.setSize(hp.getWidth() - deduct, hp.getHeight());

            if (whichPlayerNum == PNUM2) {

                hp.setLocation(hp.getLocation().x + deduct, hp.getY());

            }

        } else {

            hp.setSize(hp.getWidth() - deduct / HALF_MGC, hp.getHeight());

            if (whichPlayerNum == PNUM2) {

                hp.setLocation(hp.getLocation().x + deduct / HALF_MGC, hp.getY());

            }

        }

        if (hp.getWidth() <= HP_WIDTH / 2) {

            hp.setBackground(Color.yellow);

            if (hp.getWidth() <= HP_WIDTH / 3) {

                hp.setBackground(Color.red);

            }

        }

    }

    public void decMagic(int deduct) {

        if (magic.getWidth() - deduct <= INT_WIDTH) {

            magic.setSize(INT_WIDTH, MGC_HEIGHT);

        } else {

            magic.setSize(magic.getWidth() - deduct, MGC_HEIGHT);

        }


        if (whichPlayerNum == PNUM2) {

            if (magic.getWidth() <= INT_WIDTH) {

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


        if (whichPlayerNum == PNUM2) {

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

        magic.setSize(magic.getWidth() + MGC_ADD * DOUBLE_MGC, MGC_HEIGHT);

        if (whichPlayerNum == PNUM2) {

            magic.setLocation(magic.getLocation().x - MGC_ADD * DOUBLE_MGC, magic.getY());

        }

    }

    public boolean hasMagic(int enough) {

        if (magic.getWidth() >= enough) {

            return true;

        }

        return false;

    }


}
