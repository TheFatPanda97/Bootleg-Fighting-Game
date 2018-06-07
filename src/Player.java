import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends JLabel {


    protected static boolean GameOver = false;
    protected boolean atTop = false;
    protected boolean emergencyStop = false;
    protected boolean beingSuped = false;
    protected boolean[][] allBoolMove = new boolean[2][6];
    protected boolean[] whichCharacter = new boolean[3];
    public boolean chidori;
    public boolean beingHit;

    InputMap im = Main.fightWindow.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap ap = Main.fightWindow.getRootPane().getActionMap();

    protected ImageIcon[][] allPic = new ImageIcon[4][6];

    protected ArrayList<Projectile> allBulltes = new ArrayList<>();

    Timer movementTimer;
    Timer jumpTimer;
    Timer stopTimer;
    Timer bulletTimer;
    Timer hitTimer;

    protected final int COMMON_FLOOR = 80;

    public static final int PROJECTTILE_DMG = 14;
    public static final int PUNCH_DMG = 12;
    public static final int SUPER_DMG = 140;

    protected final int PROJECTILE_MGI = 25;
    protected final int SUPER_MGI = 200;

    protected final int INITAL_WIDTH = 40;
    protected final int INITAL_HEIGHT = 300;

    protected int jumpHeight;
    protected int jumpSpeed = 5;
    protected int moveSpeed;
    protected int count = 0;

    protected int spinDown = 80;
    protected int lightUp = 60;

    protected int RobKickDistance = 100;
    protected int RobPunchDistance = 100;
    protected int RobShootDistance = 100;

    protected int WizPunchDistance = 60;
    protected int WizShootDistance = 20;

    protected int KakaPunchDistance = 60;
    protected int KakaSuperDistance = 26;

    protected int projectSpeed;
    protected int projectStart = 0;
    protected int facing = 0;

    protected int whichPlayerNum = 0;

    protected final int JUMP_HEIGHT = 55;
    protected final int ROB_SHOOT = -40;

    protected Bar hpMagic;

    protected boolean dontMove;


    public Player() {

        for (int i = 0; i < whichCharacter.length; i++) {

            whichCharacter[i] = false;

        }

        stopMoving();

        moveAct(10, e -> {

            outOfBounds();
            //if pressed D
            if (allBoolMove[1][2]) {

                //move right
                moveHorizontal(moveSpeed);
                //     set(1,2,allPic);

            }

            //if press A
            if (allBoolMove[1][0]) {

                //move left
                moveHorizontal(-moveSpeed);
                //   set(1,0,allPic);

            }


        });

        jumpAct(20, e -> {

            if (jumpHeight == 0) {

                atTop = true;

            }

            if (!atTop) {

                setLocation(getX(), getLocation().y - jumpHeight);
                jumpHeight -= jumpSpeed;

            } else {

                setLocation(getX(), getLocation().y + jumpHeight);
                jumpHeight += jumpSpeed;

            }

            if (emergencyStop) {

                atTop = false;
                jumpHeight = JUMP_HEIGHT;
                emergencyStop = false;
                jumpTimer.stop();


            }

            if (getLocation().y >= Fight_Club.height - getHeight() - COMMON_FLOOR) {

                atTop = false;
                reset(0, 1);
                jumpHeight = JUMP_HEIGHT;
                jumpTimer.stop();

            }


        });

        bulletAct(20, e -> {

            for (int i = 0; i < allBulltes.size(); i++) {

                if (allBulltes.get(i).face == -1) {

                    allBulltes.get(i).face = facing;

                }


                if (allBulltes.get(i).face == 0) {

                    allBulltes.get(i).moveHorizon(projectSpeed);

                } else if (allBulltes.get(i).face == 2) {

                    allBulltes.get(i).moveHorizon(-projectSpeed);

                }


                if (allBulltes.get(i).getX() >= Fight_Club.width || allBulltes.get(i).getX() <= -allBulltes.get(i).getWidth()) {

                    allBulltes.get(i).remove();

                }

            }


        });

        hitAct(20, e -> {


        });


    }

    protected boolean facingLeft() {

        if (facing == 2) {

            return true;

        }

        return false;

    }

    protected boolean facingRight() {

        if (facing == 0) {

            return true;

        }

        return false;

    }

    protected void setWhichPlayer(int whichPlayerNum, JComponent RootPane) {


        if (whichPlayerNum == 1) {

            facing = 0;
            setKeyBindingP1();

        } else if (whichPlayerNum == 2) {

            facing = 2;
            setKeyBindingP2();

        }


    }

    //sets the movementTimer speed
    protected void setMoveSpeed(int s) {

        moveSpeed = s;

    }

    //sets the movementTimer speed
    protected void setProjectSpeed(int s) {

        projectSpeed = s;

    }

    protected void set(int w, int h) {

        allBoolMove[w][h] = true;

        if (facing == 0) {

            setSize(allPic[w][h].getIconWidth(), allPic[w][h].getIconHeight());
            setIcon(allPic[w][h]);

        } else if (facing == 2) {

            setSize(allPic[w + facing][h].getIconWidth(), allPic[w + facing][h].getIconHeight());
            setIcon(allPic[w + facing][h]);

        }


    }

    protected void reset(int w, int h) {

        allBoolMove[w][h] = false;
        count = 0;

        if (whichPlayerNum == 1) {

            Main.fightWindow.P2.beingHit = false;

        } else if (whichPlayerNum == 2) {

            Main.fightWindow.P1.beingHit = false;

        }

        allPic[w + facing][h].getImage().flush();
        allPic[w][h].getImage().flush();

        if (isAllBoolFalse(allBoolMove)) {

            setIcon(allPic[facing][0]);
            setSize(allPic[facing][0].getIconWidth(), allPic[facing][0].getIconHeight());

        }

        for (int i = 0; i < 2; i++) {

            for (int j = 0; j < 6; j++) {

                if (allBoolMove[i][j]) {

                    setIcon(allPic[i + facing][j]);
                    setSize(allPic[i + facing][j].getIconWidth(), allPic[i + facing][j].getIconHeight());

                }

            }

        }

    }

    protected void setLocGround() {

        setLocation(getX(), Fight_Club.height - allPic[0][0].getIconHeight() - COMMON_FLOOR);

    }

    protected void setBack(int a) {

        if (facing == 2) {

            moveHorizontal(a);

        } else if (facing == 0) {

            moveHorizontal(-a);

        }

    }

    protected void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setBounds(0, Fight_Club.height - INITAL_HEIGHT - COMMON_FLOOR, INITAL_WIDTH, INITAL_HEIGHT);


        } else if (whichPlayerNum == 2) {

            setBounds(Fight_Club.width - INITAL_WIDTH - 30, Fight_Club.height - INITAL_HEIGHT - COMMON_FLOOR, INITAL_WIDTH, INITAL_HEIGHT);

        }

    }


    //sets all the keybinders for player 1
    protected void setKeyBindingP1() {

        //sets the movement block
        addKeyBinder(KeyEvent.VK_S, "P1Block", e -> PBlock(), e -> RBlock());

        //sets the movement right
        addKeyBinder(KeyEvent.VK_D, "P1Right", e -> PRight(), e -> RRight());

        //sets the movement left
        addKeyBinder(KeyEvent.VK_A, "P1Left", e -> PLeft(), e -> RLeft());

        //sets the movement up
        addKeyBinder(KeyEvent.VK_W, "P1Jump", e -> jump());

        //sets the movement hit
        addKeyBinder(KeyEvent.VK_F, "P1Hit", e -> punch());

        //sets the movement kick
        addKeyBinder(KeyEvent.VK_G, "P1Kick", e -> kick());

        //sets the movement shoot
        addKeyBinder(KeyEvent.VK_H, "P1Shoot", e -> shoot());

        //sets the movement super
        addKeyBinder(KeyEvent.VK_R, "P1Super", e -> superPower());


    }

    //sets all the keybinders for player 2
    protected void setKeyBindingP2() {


        //sets the movement block
        addKeyBinder(KeyEvent.VK_DOWN, "P2Block", e -> {

            PBlock();

        }, e -> {

            RBlock();

        });

        //sets the movement right
        addKeyBinder(KeyEvent.VK_RIGHT, "P2Right", e -> {

            PRight();

        }, e -> {

            RRight();

        });

        //sets the movement left
        addKeyBinder(KeyEvent.VK_LEFT, "P2Left", e -> {

            PLeft();

        }, e -> {

            RLeft();

        });

        //sets the movement up
        addKeyBinder(KeyEvent.VK_UP, "P2Jump", e -> {

            jump();

        });

        //sets the movement hit
        addKeyBinder(KeyEvent.VK_J, "P2Hit", e -> {

            punch();

        });

        //sets the movement kick
        addKeyBinder(KeyEvent.VK_K, "P2Kick", e -> {

            kick();

        });

        //sets the movement shoot
        addKeyBinder(KeyEvent.VK_L, "P2Shoot", e -> {

            shoot();

        });

        //sets the movement super
        addKeyBinder(KeyEvent.VK_U, "P2Super", e -> {

            superPower();

        });


    }

    //makes player move horizontally
    protected void moveHorizontal(int m) {

        setLocation(getX() + m, getY());

    }

    //makes player move horizontally
    protected void moveVertical(int m) {

        setLocation(getX(), getY() + m);

    }

    private void PBlock() {

        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver) {

            stopMoving();
            set(1, 1);

        }


    }

    private void RBlock() {

        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver) {

            //      hpMagic.magicTimer.stop();
            reset(1, 1);

        }

    }

    private  void PLeft() {

        if (!isAttacking() && !isBlocking() && !GameOver&&!dontMove) {

            set(1, 0);

        }

    }

    private  void PRight() {

        if (!isAttacking() && !isBlocking() && !GameOver&&!dontMove) {

            set(1, 2);

        }

    }

    private  void RLeft() {

        if (!isAttacking() && !isBlocking() && !GameOver&&!dontMove) {

            reset(1, 0);

        }

    }

    private  void RRight() {

        if (!isAttacking() && !isBlocking() && !GameOver&&!dontMove) {

            reset(1, 2);

        }

    }

    private  void jump() {

        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver && !dontMove) {

            set(0, 1);
            jumpHeight = JUMP_HEIGHT;
            jumpTimer.start();

        }

    }

    protected void punch() {

        set(1, 3);
        stopTimer.start();

    }

    protected void kick() {

        set(1, 4);
        stopTimer.start();

    }

    protected void shoot() {

        stopMoving();
        set(1, 5);
        stopTimer.start();

    }

    protected void superPower() {

        stopMoving();
        set(0, 3);
        hpMagic.decMagic(SUPER_MGI);
        stopTimer.start();

    }


    protected void addKeyBinder(int KeyCode, String id, ActionListener actionListener) {

        im.put(KeyStroke.getKeyStroke(KeyCode, 0, false), "Pressed Once " + id);

        ap.put("Pressed Once " + id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });


    }

    private void addKeyBinder(int KeyCode, String id, ActionListener actionListenerP, ActionListener actionListenerR) {

        im.put(KeyStroke.getKeyStroke(KeyCode, 0, false), "Pressed " + id);
        im.put(KeyStroke.getKeyStroke(KeyCode, 0, true), "Released " + id);

        ap.put("Pressed " + id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListenerP.actionPerformed(e);
            }
        });

        ap.put("Released " + id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListenerR.actionPerformed(e);
            }
        });

    }

    protected void removeKakaKeyBinder() {

//        System.out.println(Main.fightWindow.P1.whichCharacter[2]);
//        System.out.println(Main.fightWindow.P2.whichCharacter[2]);

        if (!Main.fightWindow.P1.whichCharacter[2] && !Main.fightWindow.P2.whichCharacter[2]) {

            im.remove(KeyStroke.getKeyStroke("T"));
            im.remove(KeyStroke.getKeyStroke("I"));

            ap.remove(KeyStroke.getKeyStroke("T"));
            ap.remove(KeyStroke.getKeyStroke("I"));

        }

    }


    private void moveAct(int delay, ActionListener actionListener) {

        movementTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });
    }

    private void jumpAct(int delay, ActionListener actionListener) {

        jumpTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    protected void stopAct(ActionListener actionListener) {

        stopTimer = new Timer(10, e -> {

            actionListener.actionPerformed(e);

        });

    }

    private void bulletAct(int delay, ActionListener actionListener) {

        bulletTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    private void hitAct(int delay, ActionListener actionListener) {

        hitTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }


    private  void outOfBounds() {

        if (getX() + getWidth() >= Fight_Club.width) {

            setLocation(Fight_Club.width - getWidth(), getY());

        } else if (getX() <= 0) {

            setLocation(0, getY());

        }

    }

    protected void stopMoving() {

        //boolean for recording which button is pressed
        for (int i = 0; i < 2; i++) {

            for (int j = 0; j < 6; j++) {

                allBoolMove[i][j] = false;

            }

        }


    }

    protected void bulletCreation() {

        Main.fightWindow.add(allBulltes.get(projectStart), 0);
        ++projectStart;

    }

    private boolean isAllBoolFalse(boolean[][] t) {

        for (boolean[] a : t) {

            for (boolean x : a) {

                if (x) {

                    return false;

                }

            }

        }

        return true;

    }

    public boolean isAttacking() {

        if (allBoolMove[0][3]) {

            return true;

        }

        if (allBoolMove[0][4]) {

            return true;

        }

        if (allBoolMove[1][3]) {

            return true;

        }

        if (allBoolMove[1][4]) {

            return true;

        }

        if (allBoolMove[1][5]) {

            return true;

        }

        return false;

    }

    private boolean isJumping() {

        return allBoolMove[0][1];

    }

    protected boolean isSuper() {

        return allBoolMove[0][3];

    }

    protected boolean isBlocking() {

        return allBoolMove[1][1];

    }

    public static void setGameOver(boolean g) {

        GameOver = g;

    }


}
