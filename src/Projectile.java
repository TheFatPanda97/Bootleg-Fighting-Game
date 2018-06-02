import javax.swing.*;
import java.awt.event.ActionListener;

public class Projectile extends JLabel {

    ImageIcon RShot = new ImageIcon(getClass().getResource("RShot.gif"));
    ImageIcon LShot = new ImageIcon(getClass().getResource("LShot.gif"));
    ImageIcon RExplosion = new ImageIcon(getClass().getResource("RExplosion.gif"));
    ImageIcon LExplosion = new ImageIcon(getClass().getResource("LExplosion.gif"));
    ImageIcon RRobSuper = new ImageIcon(getClass().getResource("RRobSuper.gif"));
    ImageIcon LRobSuper = new ImageIcon(getClass().getResource("LRobSuper.gif"));


    ImageIcon[][] allShot = new ImageIcon[3][4];

    int face = -1;
    int count;

    boolean explode = false;
    boolean supsBullet = false;

    Timer explosionTimer;
    Timer stopTimer;

    public Projectile(JLabel icon, int facing, int WPN) {

        setShots();
        setIcon(allShot[WPN][facing]);
        setBounds(icon.getX() + 30, Math.round(icon.getY() + icon.getHeight() / 2), allShot[WPN][facing].getIconWidth(), allShot[WPN][facing].getIconHeight());
        setOpaque(false);

        explosionAct(40, e -> {

            if (count == 4) {

                count = 0;
                RExplosion.getImage().flush();
                LExplosion.getImage().flush();
                remove();
                explosionTimer.stop();

            }

            count++;

        });

    }

    public Projectile(JLabel icon, int facing, int additionalHeight, int WPN) {

        setShots();
        setIcon(allShot[WPN][facing]);
        setBounds(icon.getX() + 30, Math.round(icon.getY() + icon.getHeight() / 2) + additionalHeight, allShot[WPN][facing].getIconWidth(), allShot[WPN][facing].getIconHeight());
        setOpaque(false);

        explosionAct(20, e -> {

            if (count == 4) {

                count = 0;
                RExplosion.getImage().flush();
                LExplosion.getImage().flush();
                remove();
                explosionTimer.stop();

            }

            count++;

        });

    }

    public Projectile(JLabel icon, int facing, int WPN, int whichPlayerNum, boolean sups) {

        supsBullet = sups;
        setShots();
        setIcon(allShot[WPN][facing + WPN]);
        setBounds(icon.getX() + 30, icon.getY() + icon.getHeight() - allShot[WPN][facing + WPN].getIconHeight(), allShot[WPN][facing + WPN].getIconWidth(), allShot[WPN][facing + WPN].getIconHeight());
        setOpaque(false);

        stopAct(20, e -> {

            if (count == 35) {

                count = 0;
                allShot[WPN][facing + WPN].getImage().flush();
                remove();

                if (whichPlayerNum == 1) {

                    Main.fightWindow.P2.beingSuped = false;

                } else if (whichPlayerNum == 2) {

                    Main.fightWindow.P1.beingSuped = false;

                }

                stopTimer.stop();


            }

            count++;

        });

        stopTimer.start();

    }

    void moveHorizon(int m) {

        setLocation(getLocation().x + m, getY());

    }

    void setExplosion() {

        if (face == 0) {

            setIcon(RExplosion);

        } else if (face == 2) {

            setIcon(LExplosion);

        }


    }

    void setShots() {

        allShot[0][0] = RShot;
        allShot[0][2] = LShot;

        allShot[1][0] = RShot;
        allShot[1][2] = LShot;

        allShot[1][1] = RRobSuper;
        allShot[1][3] = LRobSuper;


    }

    void explosionAct(int delay, ActionListener actionListener) {

        explosionTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    void stopAct(int delay, ActionListener actionListener) {

        stopTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    void remove() {

        //   face = -2;
        setLocation(200, 2000);

    }


}
