import javax.swing.*;
import java.awt.*;

/**
 * in charge of health and magic bar
 */
public class Bar extends JLabel {

    private final int RHP_XMOVE = 6;
    private final int LHP_XMOVE = 7;
    private final int HP_Y = 16;
    private final int HP_WIDTH = 300;
    private final int HP_HEIGHT = 34;

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

            //initial state of hp bar is green and good
            hp.setBackground(Color.green);
            hp.setBounds(mugshot.getWidth() + RHP_XMOVE, HP_Y, HP_WIDTH, HP_HEIGHT);
            hp.setOpaque(true);

            //initial state of hp bar is blue and good
            magic.setBackground(Color.cyan);
            magic.setBounds(mugshot.getWidth() + RMGC_XMOVE, MGC_Y + Y_OFFSET, INT_WIDTH, MGC_HEIGHT);
            magic.setOpaque(true);

            //same as player 1 code except location of bar is top right
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

        //adds all elements to bar instance
        add(hp);
        add(magic);
        add(mugshot);

    }

    //as the title suggests, it detects if the hp is depleted
    public boolean dead() {

        //if the hp is less than 0
        return hp.getWidth() <= INT_WIDTH;

    }

    //deducts health
    public void decHP(int deduct, boolean isBlocking) {

        //is the other player is not blocking
        if (!isBlocking) {

            //the hp reduce depending what attack is carried out
            hp.setSize(hp.getWidth() - deduct, hp.getHeight());

            //player 2's HP bar needs to be right as health goes down, or else the bar would deplete from the right instead of left
            if (whichPlayerNum == PNUM2) {

                //moves the bar left depending on how much HP is taken
                P2BarSet(hp, deduct);

            }

        } else {

            //if the other player is blocking, then the hp reduce by half depending what attack is carried out
            hp.setSize(hp.getWidth() - deduct / HALF_MGC, hp.getHeight());

            //same as if the other player is blocking
            if (whichPlayerNum == PNUM2) {

                P2BarSet(hp, deduct / HALF_MGC);

            }

        }

        //change HP bar color depending on how much health is left
        colorChange();

    }

    //deducts magic
    public void decMagic(int deduct) {

        //if the amount of magic left is smaller than the amount deducted, then set it to 0
        if (magic.getWidth() - deduct <= INT_WIDTH) {

            magic.setSize(INT_WIDTH, MGC_HEIGHT);

        } else {

            //just deduct normally
            magic.setSize(magic.getWidth() - deduct, MGC_HEIGHT);

        }

        //if player 2
        if (whichPlayerNum == PNUM2) {

            //if the there's is no magic, set it to the initial location
            if (magic.getWidth() == INT_WIDTH) {

                magic.setLocation(LMGC_XMOVE + MGC_WIDTH, magic.getY());

            } else {

                //if not then just move right by "deduct" amount
                P2BarSet(magic, deduct);

            }

        }

    }

    //adds magic (default)
    public void addMagic() {

        //if adding magic will exceed max amount of magic
        if (magic.getWidth() + MGC_ADD >= MGC_WIDTH) {

            //set to max amount of magic
            magic.setSize(MGC_WIDTH, MGC_HEIGHT);

        } else {

            //if not then just add magic
            magic.setSize(magic.getWidth() + MGC_ADD, MGC_HEIGHT);

        }

        //similar to decMagic
        if (whichPlayerNum == PNUM2) {

            if (magic.getWidth() == MGC_WIDTH) {

                magic.setLocation(LMGC_XMOVE, magic.getY());

            } else {

                P2BarSet(magic, -MGC_ADD);

            }

        }

    }

    //adds specific amount of magic
    public void addMagic(int a) {

        //identical to addMagic() except the amount of magic added is different
        if (magic.getWidth() + a >= MGC_WIDTH) {

            magic.setSize(MGC_WIDTH, MGC_HEIGHT);

        } else {

            magic.setSize(magic.getWidth() + a, MGC_HEIGHT);

        }


        if (whichPlayerNum == PNUM2) {

            if (magic.getWidth() == MGC_WIDTH) {

                magic.setLocation(LMGC_XMOVE, magic.getY());

            } else {

                P2BarSet(magic, -a);

            }

        }

    }

    //if a player attacks first, they get extra magic
    public void firstBlood() {

        //amount of magic added is double
        magic.setSize(magic.getWidth() + MGC_ADD * DOUBLE_MGC, MGC_HEIGHT);

        //P2 bar location offset
        if (whichPlayerNum == PNUM2) {

            P2BarSet(magic, - MGC_ADD * DOUBLE_MGC);

        }

    }

    //detects if a player has enough for tasks
    public boolean hasMagic(int enough) {

        if (magic.getWidth() >= enough) {

            return true;

        }

        return false;

    }

    //changes health bar color
    private void colorChange() {

        if (hp.getWidth() <= HP_WIDTH / 2) {

            //if less than half health, then change to yellow
            hp.setBackground(Color.yellow);

            if (hp.getWidth() <= HP_WIDTH / 3) {

                //if less than third health, then change to red
                hp.setBackground(Color.red);

            }

        }

    }

    //methods that set the bar to correct location for player 2
    private void P2BarSet(JLabel a, int deduct) {

        a.setLocation(a.getLocation().x + deduct, a.getY());

    }


}
