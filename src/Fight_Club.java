import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Fight_Club extends All_Windows {

    private final int NORMAL_HITBACK = 100;
    private final int SUPER_HITBACK = 300;
    private final int CONT_HITBACK = 10;

    private final int SUP_LOS_HP = 4;


    private final int NORMP_LOS_MGC = 15;
    private final int SUP_LOS_MGC = 2;

    private final int SUP_DILUTE = 100;

    private final int REAL_COUNT = 90;
    private int count = REAL_COUNT;

    private boolean firstBlood = false;

    private String P1Name, P2Name;

    private ImageIcon countDown = new ImageIcon("src/Resource/Hexagon/Count Down.png");
    private ImageIcon over = new ImageIcon("src/Resource/Decoration/GameOver.gif");

    public JLabel background = new JLabel();
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

    private final int Y_OFFSET = 30;
    private final int WIDTH_OFFSET = 350;


    public Fight_Club() {

        lblCountBackground.setIcon(countDown);
        lblCountBackground.setSize(countDown.getIconWidth(), countDown.getIconHeight());
        lblCountBackground.setLocation(getWidth() / 2 - lblCountBackground.getWidth() / 2, 0);

        lblRealCount.setSize(countDown.getIconWidth(), countDown.getIconHeight());
        lblRealCount.setForeground(Color.white);
        lblRealCount.setLocation(lblCountBackground.getX(), lblCountBackground.getY());
        lblRealCount.setFont(fttFont);

        lblKO.setSize(over.getIconWidth(), over.getIconHeight());
        lblKO.setLocation(width / 2 - lblKO.getWidth() / 2, height / 2 - lblRealCount.getWidth() / 2);
        lblKO.setIcon(over);
        lblKO.setVisible(false);

        lblWin.setSize(over.getIconWidth() + 350, 50);
        lblWin.setLocation(width / 2 - lblWin.getWidth() / 2, lblKO.getY() + lblKO.getHeight());
        lblWin.setFont(fttFont);
        lblWin.setForeground(Color.white);
        lblWin.setVisible(false);

        btnMain.setLocation(width / 2 - btnMain.getWidth() / 2, lblWin.getY() + lblWin.getHeight() + 30);
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


        directionTimer = new Timer(20, e -> {

            if (P1.getX() >= P2.getX() + P2.getWidth()) {

                P1.facing = 2;
                P2.facing = 0;

            } else if (P2.getX() >= P1.getX() + P1.getWidth()) {

                P1.facing = 0;
                P2.facing = 2;

            }

        });

        collisionTimer = new Timer(10, e -> {

            //game over
            if (P1.hpMagic.dead()) {

                endGame(P2Name);

            } else if (P2.hpMagic.dead()) {

                endGame(P1Name);

            }

            if (hitProjectile(P1, P2)) {

                P2.setBack(NORMAL_HITBACK);

            }

            if (hitProjectile(P2, P1)) {

                P1.setBack(NORMAL_HITBACK);


            }

            if (superProjectile(P1, P2)) {

                P2.setBack(CONT_HITBACK);

            }

            if (superProjectile(P2, P1)) {

                P1.setBack(CONT_HITBACK);


            }


            if (hitEachOther(P1, P2) && P1.isAttacking() && !P2.beingHit) {

                dmgSet(P1, P2);

            }


            if (hitEachOther(P2, P1) && P2.isAttacking() && !P1.beingHit) {

                dmgSet(P2, P1);

            }

            if (P1.whichCharacter[2] && P1.isSuper() && hitEachOther(P1, P2) && P1.chidori) {

                gettingChidori(P1, P2);
//                P2.beingHit = false;
//                P2.beingSuped = true;
//                P2.hpMagic.decHP(SUP_LOS_HP, P2.isBlocking());
//                P2.hpMagic.decMagic(SUP_LOS_MGC);
//
//                P2.setLocation(P1.getX() + P1.getWidth() / 6, P1.getY());

            }

            if (P2.whichCharacter[2] && P2.isSuper() && hitEachOther(P2, P1) && P2.chidori) {

                gettingChidori(P2, P1);
//                P1.beingHit = false;
//                P1.beingSuped = true;
//                P1.hpMagic.decHP(SUP_LOS_HP, P1.isBlocking());
//                P1.hpMagic.decMagic(SUP_LOS_MGC);
//
//                P1.setLocation(P2.getX() + P2.getWidth() / 6, P2.getY());

            }


        });

        countDownTimer = new Timer(1000, e -> {

            count--;
            lblRealCount.setText(count + "");

            if (count == 0) {

                if (P1.hpMagic.hp.getWidth() == P2.hpMagic.hp.getWidth()) {

                    endGame("NO ONE");

                } else if (P1.hpMagic.hp.getWidth() > P2.hpMagic.hp.getWidth()) {

                    endGame(P1Name);

                } else {

                    endGame(P2Name);

                }

            }


        });


    }

    public void setPLayer(int P1At, int P2At, String P1N, String P2N) {

        lblRealCount.setText(count + "");
        P1Name = P1N;
        P2Name = P2N;

        if (P1At == 0) {

            P1 = new Wizard(1);

        } else if (P1At == 1) {

            P1 = new Kakashi(1);

        } else if (P1At == 2) {

            P1 = new Robot(1);

        }

        if (P2At == 0) {

            P2 = new Wizard(2);

        } else if (P2At == 1) {

            P2 = new Kakashi(2);

        } else if (P2At == 2) {

            P2 = new Robot(2);

        }


        add(P1.hpMagic);
        add(P2.hpMagic);
        add(P1);
        add(P2);
        add(background);
        P1.removeKakaKeyBinder();
        P2.removeKakaKeyBinder();
        collisionTimer.start();
        directionTimer.start();
    }

    public void setPLayer(int P1At, int P2At, String P1N, String P2N, ImageIcon[][] p, ArrayList<Integer> d) {

        lblRealCount.setText(count + "");
        P1Name = P1N;
        P2Name = P2N;

        if (P1At == 0) {

            P1 = new Wizard(1);

        } else if (P1At == 1) {

            P1 = new Kakashi(1, p, d);

        } else if (P1At == 2) {

            P1 = new Robot(1);

        }

        if (P2At == 0) {

            P2 = new Wizard(2);

        } else if (P2At == 1) {

            P2 = new Kakashi(2, p, d);

        } else if (P2At == 2) {

            P2 = new Robot(2);

        }


        add(P1.hpMagic);
        add(P2.hpMagic);
        add(P1);
        add(P2);
        add(background);
        collisionTimer.start();
        directionTimer.start();
    }

    private boolean hitProjectile(Player a, Player b) {

        for (Projectile x : a.allBulltes) {

            if (x.getBounds().intersects(b.getBounds()) && !x.explode && !x.supsBullet) {

                x.setExplosion();
                x.face = -2;
                x.explode = true;
                x.explosionTimer.start();

                b.hpMagic.decHP(Player.PROJECTTILE_DMG, b.isBlocking());
                b.hpMagic.decMagic(NORMP_LOS_MGC);


                return true;

            }

        }

        return false;


    }

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

    private boolean hitEachOther(JLabel a, JLabel b) {

        return a.getBounds().intersects(b.getBounds());

    }

    private void dmgSet(Player a, Player b) {

        if (!firstBlood) {

            a.hpMagic.firstBlood();
            firstBlood = true;

        }


        b.beingHit = true;


        if (!a.isSuper()) {

            a.hpMagic.addMagic();
            b.hpMagic.addMagic(Bar.MGC_ADD / 2);
            b.hpMagic.decHP(Player.PUNCH_DMG, b.isBlocking());
            b.setBack(NORMAL_HITBACK);

        } else {

            if (a.whichCharacter[0]) {

                b.hpMagic.decHP(Player.SUPER_DMG, b.isBlocking());
                b.setBack(SUPER_HITBACK);
                b.hpMagic.decMagic(SUPER_HITBACK / 2);

            } else if (a.whichCharacter[1]) {

                b.hpMagic.decHP(Player.SUPER_DMG / SUP_DILUTE, b.isBlocking());

            }

        }

    }

    private void gettingChidori(Player a, Player b) {

        b.beingHit = false;
        b.beingSuped = true;
        b.hpMagic.decHP(SUP_LOS_HP, b.isBlocking());
        b.hpMagic.decMagic(SUP_LOS_MGC);

        b.setLocation(a.getX() + b.getWidth() / 6, a.getY());

    }

    private void restart() {

        remove(P1);
        remove(P2);
        remove(P1.hpMagic);
        remove(P2.hpMagic);
        P1.hpMagic = null;
        P2.hpMagic = null;
        P1 = null;
        P2 = null;
        count = REAL_COUNT;

    }

    private void endGame(String name) {

        Player.setGameOver(true);
        lblWin.setVisible(true);
        lblKO.setVisible(true);
        btnMain.setVisible(true);
        countDownTimer.stop();
        directionTimer.stop();
        collisionTimer.stop();

        if (Main.rw.newHighScore(name, count)) {

            lblWin.setText(name + " WINS WITH NEW HIGH SCORE!!");

        } else {

            lblWin.setText(name + " WINS!!");


        }

    }


}
