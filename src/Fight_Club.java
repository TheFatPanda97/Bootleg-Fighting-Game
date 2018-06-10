import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * where the fighting happens
 */
public class Fight_Club extends All_Windows {

    //variables that set player 2 back the correct amount
    private final int NORMAL_HITBACK = 100;
    private final int SUPER_HITBACK = 300;
    private final int CONT_HITBACK = 10;

    private final int SUP_LOS_HP = 4;

    private final int RFACE = 0;
    private final int LFACE = 2;

    private final int NORMP_LOS_MGC = 15;
    private final int SUP_LOS_MGC = 2;

    private final int SUP_DILUTE = 100;

    //count down
    private final int REAL_COUNT = 30;
    private int count = REAL_COUNT;

    private final int X_OFFSET = 6;
    private final int Y_OFFSET = 30;
    private final int WIDTH_OFFSET = 350;

    private final int WIN_HEIGHT = 50;

    private final int DIR_TIME = 20;
    private final int CLI_TIME = 10;
    private final int CTD_TIME = 1000;

    private final int SEL_WIZARD = 0;
    private final int SEL_KAKASHI = 1;
    private final int SEL_ROBOT = 2;

    private final int BULLET_STOP = -2;

    private boolean firstBlood = false;

    private String P1Name, P2Name;

    private ImageIcon countDown = new ImageIcon("src/Resource/Hexagon/Count Down.png");
    private ImageIcon over = new ImageIcon("src/Resource/Decoration/GameOver.gif");

    private JLabel lblCountBackground = new JLabel();
    private JLabel lblRealCount = new JLabel(count + "", SwingConstants.CENTER);
    private JLabel lblKO = new JLabel();
    private JLabel lblWin = new JLabel("", SwingConstants.CENTER);

    private Timer directionTimer;
    private Timer collisionTimer;
    public Timer countDownTimer;

    public Player P1;
    public Player P2;

    private Button btnMain = new Button(BTN_MAIN);

    private Font fttFont = new Font("Aerial", Font.BOLD, 40);

    //default constructor
    public Fight_Club() {

        //the countdown screen hexagon
        lblCountBackground.setIcon(countDown);
        lblCountBackground.setSize(countDown.getIconWidth(), countDown.getIconHeight());
        lblCountBackground.setLocation(getWidth() / 2 - lblCountBackground.getWidth() / 2, INTY);

        //the actual countdown label
        lblRealCount.setSize(countDown.getIconWidth(), countDown.getIconHeight());
        lblRealCount.setForeground(Color.white);
        lblRealCount.setLocation(lblCountBackground.getX(), lblCountBackground.getY());
        lblRealCount.setFont(fttFont);

        //the game over label
        lblKO.setSize(over.getIconWidth(), over.getIconHeight());
        lblKO.setLocation(width / 2 - lblKO.getWidth() / 2, height / 2 - lblRealCount.getWidth() / 2);
        lblKO.setIcon(over);
        lblKO.setVisible(false);

        //displays who won
        lblWin.setSize(over.getIconWidth() + WIDTH_OFFSET, WIN_HEIGHT);
        lblWin.setLocation(width / 2 - lblWin.getWidth() / 2, lblKO.getY() + lblKO.getHeight());
        lblWin.setFont(fttFont);
        lblWin.setForeground(Color.white);
        lblWin.setVisible(false);

        //main menu button
        btnMain.setLocation(width / 2 - btnMain.getWidth() / 2, lblWin.getY() + lblWin.getHeight() + Y_OFFSET);
        btnMain.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                lblKO.setVisible(false);
                lblWin.setVisible(false);
                btnMain.setVisible(false);
                restart();
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
        btnMain.setVisible(false);

        add(btnMain, 0);
        add(lblWin, 0);
        add(lblKO, 0);
        add(lblRealCount, 0);
        add(lblCountBackground, 0);

        //determine which direction a player is facing at all times
        directionTimer = new Timer(DIR_TIME, e -> {

            //is player 1 is on the right of player 2
            if (P1.getX() >= P2.getX() + P2.getWidth()) {

                //player 2 face right
                //player 1 face left
                P1.facing = LFACE;
                P2.facing = RFACE;

                //vice versa
            } else if (P2.getX() >= P1.getX() + P1.getWidth()) {

                P1.facing = RFACE;
                P2.facing = LFACE;

            }

        });

        //determine if a player hurts another
        collisionTimer = new Timer(CLI_TIME, e -> {

            //if either player is dead, then game over
            if (P1.hpMagic.dead()) {

                endGame(P2Name);

            } else if (P2.hpMagic.dead()) {

                endGame(P1Name);

            }

            //if either players hit each other, then move them back a little back
            if (hitProjectile(P1, P2)) {

                P2.setBack(NORMAL_HITBACK);

            }

            if (hitProjectile(P2, P1)) {

                P1.setBack(NORMAL_HITBACK);


            }

            //this applies to robot, his super move is a projectile, but does more damage than a normal projectile
            if (superProjectile(P1, P2)) {

                P2.setBack(CONT_HITBACK);

            }

            if (superProjectile(P2, P1)) {

                P1.setBack(CONT_HITBACK);


            }


            //punch and kick, and super move
            if (hitEachOther(P1, P2) && P1.isAttacking() && !P2.beingHit) {

                dmgSet(P1, P2);

            }


            if (hitEachOther(P2, P1) && P2.isAttacking() && !P1.beingHit) {

                dmgSet(P2, P1);

            }

            //super move
            if (hitEachOther(P1, P2) && P1.isSuper() && !P2.beingHit) {

                dmgSuperSet(P1, P2);

            }


            if (hitEachOther(P2, P1) && P2.isSuper() && !P1.beingHit) {

                dmgSuperSet(P2, P1);

            }

            //this applies to kakashi's super move
            if (P1.whichCharacter[KAKASHI] && P1.isSuper() && hitEachOther(P1, P2) && P1.chidori) {

                gettingChidori(P1, P2);

            }

            if (P2.whichCharacter[KAKASHI] && P2.isSuper() && hitEachOther(P2, P1) && P2.chidori) {

                gettingChidori(P2, P1);

            }


        });

        //countdown
        countDownTimer = new Timer(CTD_TIME, e -> {

            count--;
            lblRealCount.setText(count + "");

            //if count down is zero
            if (count == 0) {

                //if both character have the same amount of hp, then no one wins
                if (P1.hpMagic.hp.getWidth() == P2.hpMagic.hp.getWidth()) {

                    endGame("NO ONE");

                    //player 1 wins
                } else if (P1.hpMagic.hp.getWidth() > P2.hpMagic.hp.getWidth()) {

                    endGame(P1Name);

                    //player 2 wins
                } else {

                    endGame(P2Name);

                }

            }


        });


    }

    //sets which player is fighting
    public void setPLayer(int P1At, int P2At, String P1N, String P2N) {

        lblRealCount.setText(count + "");
        P1Name = P1N;
        P2Name = P2N;

        if (P1At == SEL_WIZARD) {

            P1 = new Wizard(PNUM1);

        } else if (P1At == SEL_KAKASHI) {

            P1 = new Kakashi(PNUM1);

        } else if (P1At == SEL_ROBOT) {

            P1 = new Robot(PNUM1);

        }

        if (P2At == SEL_WIZARD) {

            P2 = new Wizard(PNUM2);

        } else if (P2At == SEL_KAKASHI) {

            P2 = new Kakashi(PNUM2);

        } else if (P2At == SEL_ROBOT) {

            P2 = new Robot(PNUM2);

        }

        KakaRemove(P1At, P2At);
        add(P1.hpMagic);
        add(P2.hpMagic);
        add(P1);
        add(P2);
        add(background);
        collisionTimer.start();
        directionTimer.start();
    }

    //this is used for when the DLC is first downloaded, the new files are not detected by the current compile
    public void setPLayer(int P1At, int P2At, String P1N, String P2N, ImageIcon[][] p, ArrayList<Integer> d) {

        lblRealCount.setText(count + "");
        P1Name = P1N;
        P2Name = P2N;

        //identical to the above method but the image assets and stop timer numbers are accessed through the parameters
        if (P1At == SEL_WIZARD) {

            P1 = new Wizard(PNUM1);

        } else if (P1At == SEL_KAKASHI) {

            P1 = new Kakashi(PNUM1, p, d);

        } else if (P1At == SEL_ROBOT) {

            P1 = new Robot(PNUM1);

        }

        if (P2At == SEL_WIZARD) {

            P2 = new Wizard(PNUM2);

        } else if (P2At == SEL_KAKASHI) {

            P2 = new Kakashi(PNUM2, p, d);

        } else if (P2At == SEL_ROBOT) {

            P2 = new Robot(PNUM2);

        }

        KakaRemove(P1At, P2At);
        add(P1.hpMagic);
        add(P2.hpMagic);
        add(P1);
        add(P2);
        add(background);
        collisionTimer.start();
        directionTimer.start();
    }

    //detects if a player's bullets hits the other player
    private boolean hitProjectile(Player a, Player b) {

        for (Projectile x : a.allBulltes) {

            //if the bullets hits, it hasn't exploded yet, and it's a normal bullet
            if (x.getBounds().intersects(b.getBounds()) && !x.explode && !x.supsBullet) {

                x.setExplosionIcon();//bullet explodes images
                x.face = BULLET_STOP;//the number stops the bullet from moving
                x.explode = true;//bullet has exploded
                x.explosionTimer.start();//how the long the bullet will explode for

                //the player that got hit loses magic and health
                b.hpMagic.decHP(Player.PROJECTTILE_DMG, b.isBlocking());
                b.hpMagic.decMagic(NORMP_LOS_MGC);


                return true;

            }

        }

        return false;


    }

    //identical to the above method except damage is done continuously instead of all at once
    private boolean superProjectile(Player a, Player b) {

        for (Projectile x : a.allBulltes) {

            if (hitEachOther(x, b) && !x.explode && x.supsBullet) {

                b.beingSuped = true;
                b.hpMagic.decHP(SUP_LOS_HP, b.isBlocking());
                b.hpMagic.decMagic(SUP_LOS_MGC);


                return true;

            }


        }

        return false;


    }

    //if the player contacts each other
    private boolean hitEachOther(JLabel a, JLabel b) {

        return a.getBounds().intersects(b.getBounds());

    }

    //damage and magic gain for punching and kicking
    private void dmgSet(Player a, Player b) {

        //first blood gives extra magic, happens on the first attack
        if (!firstBlood) {

            a.hpMagic.firstBlood();
            firstBlood = true;
        }


        b.beingHit = true;
        a.hpMagic.addMagic();//player a get magic
        b.hpMagic.addMagic(Bar.MGC_ADD / 2);//player b gets half amount of normal magic
        b.hpMagic.decHP(Player.PUNCH_DMG, b.isBlocking());//player b losses health
        b.setBack(NORMAL_HITBACK);//player b moves back a little bit

    }

    //damage and magic gain for super move
    private void dmgSuperSet(Player a, Player b) {

        b.beingHit = true;

        //super moves, basically the same as the top except each character does different amount of damage
        if (a.whichCharacter[WIZARD]) {

            b.hpMagic.decHP(Player.SUPER_DMG, b.isBlocking());
            b.setBack(SUPER_HITBACK);
            b.hpMagic.decMagic(SUPER_HITBACK / 2);

        } else if (a.whichCharacter[ROBOT]) {

            b.hpMagic.decHP(Player.SUPER_DMG / SUP_DILUTE, b.isBlocking());

        }

    }

    //kakashi's super move, similar to other players's except much more damage
    private void gettingChidori(Player a, Player b) {

        b.beingHit = false;
        b.beingSuped = true;//prevents player b from blocking while they are getting hit
        b.hpMagic.decHP(SUP_LOS_HP, b.isBlocking());
        b.hpMagic.decMagic(SUP_LOS_MGC);

        b.setLocation(a.getX() + b.getWidth() / X_OFFSET, a.getY());// the location of player b is always in front of the player a

    }

    //games restart once player dies
    private void restart() {

        //this instance of the player are remvoed
        remove(P1);
        remove(P2);
        remove(P1.hpMagic);
        remove(P2.hpMagic);

        P1.hpMagic = null;
        P2.hpMagic = null;
        P1 = null;
        P2 = null;

        //countdown is reset
        count = REAL_COUNT;

    }

    //what happens when the game ends
    private void endGame(String name) {

        //all the game over label are displayed
        Player.setGameOver(true);
        lblWin.setVisible(true);
        lblKO.setVisible(true);
        btnMain.setVisible(true);
        countDownTimer.stop();
        directionTimer.stop();
        collisionTimer.stop();

        //if the winning player has a new high score
        if (Main.rw.newHighScore(name, count)) {

            lblWin.setText(name + " WINS WITH NEW HIGH SCORE!!");

            //if winning player doesn't have high score
        } else {

            lblWin.setText(name + " WINS!!");


        }

    }

    //removes kakashi's special move keys
    private void KakaRemove(int P1At, int P2At) {

        //because Kakashi has a extra move, he's extra key is removed if no one chose him
        if (P1At != SEL_KAKASHI && P2At != SEL_KAKASHI) {

            P1.removeKakaKeyBinder();
            P2.removeKakaKeyBinder();

        }

    }


}
