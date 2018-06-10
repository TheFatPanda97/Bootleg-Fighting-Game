import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//bullet that's wizard and robot shoot, also the super move of robot
public class Projectile extends JLabel {

    private ImageIcon RShot = new ImageIcon("src/Resource/Shot/RShot.gif");
    private ImageIcon LShot = new ImageIcon("src/Resource/Shot/LShot.gif");
    private  ImageIcon RExplosion = new ImageIcon("src/Resource/Shot_Explosion/RExplosion.gif");
    private  ImageIcon LExplosion = new ImageIcon("src/Resource/Shot_Explosion/LExplosion.gif");
    private  ImageIcon RRobSuper = new ImageIcon("src/Resource/Shot/RRobSuper.gif");
    private  ImageIcon LRobSuper = new ImageIcon("src/Resource/Shot/LRobSuper.gif");

    private ImageIcon[][] allShot = new ImageIcon[2][4];//stores all image assests

    public int face = -1;//which way the bullet is facing
    private int count;

    public boolean explode = false;
    public boolean supsBullet = false;

    public Timer explosionTimer;
    private Timer stopTimer;

    private final int NORM_SHOT = 0;
    private final int SUPS_SHOT = 1;

    private final int INTX_OFFSET = 30;

    private final int EXPLOSION_TIMER = 40;
    private final int STOP_TIMER = 20;

    private final int COUNT_LIMIT = 4;
    private final int SUPER_COUNT_LIMIT = 35;

    private final int PNUM1 = 1;
    private final int PNUM2 = 2;

    private final int RFACE = 0;
    private final int LFACE = 2;

    private final Point REMOVE_TO = new Point(5000, 5000);//point which the bullet is sent to once they are removed

    //default constructor
    public Projectile(JLabel icon, int facing) {

        setShots();
        setIcon(allShot[NORM_SHOT][facing]);
        setBounds(icon.getX() + INTX_OFFSET, Math.round(icon.getY() + icon.getHeight() / 2), allShot[NORM_SHOT][facing].getIconWidth(), allShot[NORM_SHOT][facing].getIconHeight());//sets the bullet in the middle of the player

        //records when the bullet has exploded
        explosionAct(EXPLOSION_TIMER, e -> {

            if (count == COUNT_LIMIT) {

                count = 0;
                RExplosion.getImage().flush();//resets the right explosion gif
                LExplosion.getImage().flush();//resets the left explosion gif
                remove();
                explosionTimer.stop();

            }

            count++;

        });

    }

    //identical to the top except the bullet is placed with additionalHeight
    public Projectile(JLabel icon, int facing, int additionalHeight) {

        setShots();
        setIcon(allShot[NORM_SHOT][facing]);
        setBounds(icon.getX() + INTX_OFFSET, Math.round(icon.getY() + icon.getHeight() / 2) + additionalHeight, allShot[NORM_SHOT][facing].getIconWidth(), allShot[NORM_SHOT][facing].getIconHeight());
        setOpaque(false);

        explosionAct(EXPLOSION_TIMER, e -> {

            if (count == COUNT_LIMIT) {

                count = 0;
                RExplosion.getImage().flush();
                LExplosion.getImage().flush();
                remove();
                explosionTimer.stop();

            }

            count++;

        });

    }

    //robot's super move
    public Projectile(JLabel icon, int facing, int whichPlayerNum, boolean sups) {

        supsBullet = sups;//records if the current projectile is a super move
        setShots();
        setIcon(allShot[SUPS_SHOT][facing + SUPS_SHOT]);
        setBounds(icon.getX() + INTX_OFFSET, icon.getY() + icon.getHeight() - allShot[SUPS_SHOT][facing + SUPS_SHOT].getIconHeight(), allShot[SUPS_SHOT][facing + SUPS_SHOT].getIconWidth(), allShot[SUPS_SHOT][facing + SUPS_SHOT].getIconHeight());
        setOpaque(false);

        //stops the super move after a certain amount of time
        stopAct(STOP_TIMER, e -> {

            if (count == SUPER_COUNT_LIMIT) {

                count = 0;
                allShot[SUPS_SHOT][facing + SUPS_SHOT].getImage().flush();//rest the super move bullet
                remove();

                if (whichPlayerNum == PNUM1) {

                    Main.fightWindow.P2.beingSuped = false;

                } else if (whichPlayerNum == PNUM2) {

                    Main.fightWindow.P1.beingSuped = false;

                }

                stopTimer.stop();


            }

            count++;

        });

        stopTimer.start();

    }

    //moves the bullet horizontally
    public void moveHorizon(int m) {

        setLocation(getLocation().x + m, getY());

    }

    //set the image to explosion icon
    public void setExplosionIcon() {

        if (face == RFACE) {

            setIcon(RExplosion);

        } else if (face == LFACE) {

            setIcon(LExplosion);

        }


    }

    //set image in correct index in array
    private void setShots() {

        //how convenient that the facing corresponds with the correct images
        allShot[0][0] = RShot;
        allShot[0][2] = LShot;

        allShot[1][0] = RShot;
        allShot[1][2] = LShot;

        allShot[1][1] = RRobSuper;
        allShot[1][3] = LRobSuper;

    }

    //explosion timer actionlistener
    private void explosionAct(int delay, ActionListener actionListener) {

        explosionTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    //stop timer actionlistener
    private void stopAct(int delay, ActionListener actionListener) {

        stopTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    //moves the bullet outside the frame
    public void remove() {

        setLocation(REMOVE_TO);

    }


}
