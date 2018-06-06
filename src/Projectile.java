import javax.swing.*;
import java.awt.event.ActionListener;

public class Projectile extends JLabel {

    private ImageIcon RShot = new ImageIcon("src/Resource/Shot/RShot.gif");
    private ImageIcon LShot = new ImageIcon("src/Resource/Shot/LShot.gif");
    private  ImageIcon RExplosion = new ImageIcon("src/Resource/Shot_Explosion/RExplosion.gif");
    private  ImageIcon LExplosion = new ImageIcon("src/Resource/Shot_Explosion/LExplosion.gif");
    private  ImageIcon RRobSuper = new ImageIcon("src/Resource/Shot/RRobSuper.gif");
    private  ImageIcon LRobSuper = new ImageIcon("src/Resource/Shot/LRobSuper.gif");

    private ImageIcon[][] allShot = new ImageIcon[3][4];

    public int face = -1;
    private int count;

    public boolean explode = false;
    public boolean supsBullet = false;

    public Timer explosionTimer;
    private Timer stopTimer;

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

    public void moveHorizon(int m) {

        setLocation(getLocation().x + m, getY());

    }

    public void setExplosion() {

        if (face == 0) {

            setIcon(RExplosion);

        } else if (face == 2) {

            setIcon(LExplosion);

        }


    }

    private void setShots() {

        allShot[0][0] = RShot;
        allShot[0][2] = LShot;

        allShot[1][0] = RShot;
        allShot[1][2] = LShot;

        allShot[1][1] = RRobSuper;
        allShot[1][3] = LRobSuper;


    }

    private void explosionAct(int delay, ActionListener actionListener) {

        explosionTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    private void stopAct(int delay, ActionListener actionListener) {

        stopTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    public void remove() {

        //   face = -2;
        setLocation(200, 2000);

    }


}
