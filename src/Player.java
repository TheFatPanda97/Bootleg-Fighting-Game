import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends JLabel {


    static boolean gameOver = false;
    boolean atTop = false;
    boolean emergencyStop = false;
    boolean beingHit = false;
    boolean beingSuped = false;
    boolean[][] allBoolMove = new boolean[2][6];
    boolean[] whichPlayer = new boolean[3];
    boolean chidori = false;

    ImageIcon[][] allPic = new ImageIcon[4][6];

    ArrayList<Projectile> allBulltes = new ArrayList<>();

    Timer movementTimer;
    Timer jumpTimer;
    Timer stopTimer;
    Timer bulletTimer;
    Timer hitTimer;

    final int COMMON_FLOOR = 80;

    static final int PROJECTTILE_DMG = 14;
    static final int PUNCH_DMG = 12;
    static final int SUPER_DMG = 140;

    final int PROJECTILE_MGI = 25;
    final int SUPER_MGI = 200;

    final int INITAL_WIDTH = 40;
    final int INITAL_HEIGHT = 300;

    int jumpHeight;
    int jumpSpeed = 5;
    int moveSpeed;
    int count = 0;

    int spinDown = 80;
    int lightUp = 60;

    int RobKickDistance = 100;
    int RobPunchDistance = 100;
    int RobShootDistance = 100;

    int WizPunchDistance = 60;
    int WizShootDistance = 20;

    int KakaPunchDistance = 60;
    int KakaSuperDistance = 26;

    int projectSpeed;
    int projectStart = 0;
    int facing = 0;

    int whichPlayerNum = 0;

    final int JUMP_HEIGHT = 55;
    final int ROB_SHOOT = -40;

    Bar hpMagic;


    Player() {


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

            if (getLocation().y >= FightClub.height - getHeight() - COMMON_FLOOR) {

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


                if (allBulltes.get(i).getX() >= FightClub.width || allBulltes.get(i).getX() <= -allBulltes.get(i).getWidth()) {

                    allBulltes.get(i).remove();

                }

            }


        });

        hitAct(20, e -> {


        });


    }

    boolean facingLeft() {

        if (facing == 2) {

            return true;

        }

        return false;

    }

    boolean facingRight() {

        if (facing == 0) {

            return true;

        }

        return false;

    }

    void setWhichPlayer(int whichPlayerNum, JComponent RootPane) {


        if (whichPlayerNum == 1) {

            facing = 0;
            setKeyBindingP1(RootPane);

        } else if (whichPlayerNum == 2) {

            facing = 2;
            setKeyBindingP2(RootPane);

        }


    }

    //sets the movementTimer speed
    void setMoveSpeed(int s) {

        moveSpeed = s;

    }

    //sets the movementTimer speed
    void setProjectSpeed(int s) {

        projectSpeed = s;

    }

    void set(int w, int h) {

        allBoolMove[w][h] = true;

        if (facing == 0) {

            setSize(allPic[w][h].getIconWidth(), allPic[w][h].getIconHeight());
            setIcon(allPic[w][h]);

        } else if (facing == 2) {

            setSize(allPic[w + facing][h].getIconWidth(), allPic[w + facing][h].getIconHeight());
            setIcon(allPic[w + facing][h]);

        }


    }

    void reset(int w, int h) {

        allBoolMove[w][h] = false;
        count = 0;

        if (whichPlayerNum == 1) {

            Main.fightWindow.P2.beingHit = false;

        } else if (whichPlayerNum == 2) {

            Main.fightWindow.P1.beingHit = false;

        }


        allPic[w + facing][h].getImage().flush();

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

    void setLocGround() {

        setLocation(getX(), FightClub.height - allPic[0][0].getIconHeight() - COMMON_FLOOR);

    }

    void setBack(int a) {

        if (facing == 2) {

            moveHorizontal(a);

        } else if (facing == 0) {

            moveHorizontal(-a);

        }

    }

    void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setBounds(0, FightClub.height - INITAL_HEIGHT - COMMON_FLOOR, INITAL_WIDTH, INITAL_HEIGHT);


        } else if (whichPlayerNum == 2) {

            setBounds(FightClub.width - INITAL_WIDTH - 30, FightClub.height - INITAL_HEIGHT - COMMON_FLOOR, INITAL_WIDTH, INITAL_HEIGHT);

        }

    }


    //sets all the keybinders for player 1
    void setKeyBindingP1(JComponent RootPane) {

        //sets the movement block
        addKeyBinder(RootPane, KeyEvent.VK_S, "P1Block", e -> PBlock(), e -> RBlock());

        //sets the movement right
        addKeyBinder(RootPane, KeyEvent.VK_D, "P1Right", e -> PRight(), e -> RRight());

        //sets the movement left
        addKeyBinder(RootPane, KeyEvent.VK_A, "P1Left", e -> PLeft(), e -> RLeft());

        //sets the movement up
        addKeyBinder(RootPane, KeyEvent.VK_W, "P1Jump", e -> jump());

        //sets the movement hit
        addKeyBinder(RootPane, KeyEvent.VK_F, "P1Hit", e -> punch());

        //sets the movement kick
        addKeyBinder(RootPane, KeyEvent.VK_G, "P1Kick", e -> kick());

        //sets the movement shoot
        addKeyBinder(RootPane, KeyEvent.VK_H, "P1Shoot", e -> shoot());

        //sets the movement super
        addKeyBinder(RootPane, KeyEvent.VK_R, "P1Super", e -> superPower());


    }

    //sets all the keybinders for player 2
    void setKeyBindingP2(JComponent RootPane) {


        //sets the movement block
        addKeyBinder(RootPane, KeyEvent.VK_DOWN, "P2Block", e -> {

            PBlock();

        }, e -> {

            RBlock();

        });

        //sets the movement right
        addKeyBinder(RootPane, KeyEvent.VK_RIGHT, "P2Right", e -> {

            PRight();

        }, e -> {

            RRight();

        });

        //sets the movement left
        addKeyBinder(RootPane, KeyEvent.VK_LEFT, "P2Left", e -> {

            PLeft();

        }, e -> {

            RLeft();

        });

        //sets the movement up
        addKeyBinder(RootPane, KeyEvent.VK_UP, "P2Jump", e -> {

            jump();

        });

        //sets the movement hit
        addKeyBinder(RootPane, KeyEvent.VK_J, "P2Hit", e -> {

            punch();

        });

        //sets the movement kick
        addKeyBinder(RootPane, KeyEvent.VK_K, "P2Kick", e -> {

            kick();

        });

        //sets the movement shoot
        addKeyBinder(RootPane, KeyEvent.VK_L, "P2Shoot", e -> {

            shoot();

        });

        //sets the movement super
        addKeyBinder(RootPane, KeyEvent.VK_U, "P2Super", e -> {

            superPower();

        });


    }

    //makes player move horizontally
    void moveHorizontal(int m) {

        setLocation(getX() + m, getY());

    }

    //makes player move horizontally
    void moveVertical(int m) {

        setLocation(getX(), getY() + m);

    }

    void kickSetback() {

        if (whichPlayer[0]) {

            setLocation(getX(), getY() + spinDown);

        }

        if (facingLeft()) {

            if (whichPlayer[1]) {

                moveHorizontal(-RobKickDistance);

            }


        }

    }

    void shootSetback() {

        if (facingLeft()) {

            if (whichPlayer[0]) {

                moveHorizontal(-WizShootDistance);

            } else if (whichPlayer[1]) {

                moveHorizontal(-RobShootDistance);

            }

        }

    }

    void punchSetback() {

        if (facingLeft()) {

            if (whichPlayer[0]) {

                moveHorizontal(-WizPunchDistance);

            } else if (whichPlayer[1]) {

                moveHorizontal(-RobPunchDistance);

            } else if (whichPlayer[2]) {

                moveHorizontal(-KakaPunchDistance);

            }

        }


    }

    void superSetback() {

        if (whichPlayer[2]) {

            moveVertical(KakaSuperDistance);

        }

    }


    void PBlock() {

        if (!isAttacking() && !isJumping() && !beingSuped && !gameOver) {

            stopMoving();
            set(1, 1);

        }


    }

    void RBlock() {

        if (!isAttacking() && !isJumping() && !beingSuped && !gameOver) {

            //      hpMagic.magicTimer.stop();
            reset(1, 1);

        }

    }

    void PLeft() {

        if (!isAttacking() && !isBlocking() && !gameOver) {

            set(1, 0);

        }

    }

    void PRight() {

        if (!isAttacking() && !isBlocking() && !gameOver) {

            set(1, 2);

        }

    }

    void RLeft() {

        if (!isAttacking() && !isBlocking() && !gameOver) {

            reset(1, 0);

        }

    }

    void RRight() {

        if (!isAttacking() && !isBlocking() && !gameOver) {

            reset(1, 2);

        }

    }

    void jump() {

        if (!isAttacking() && !isJumping() && !beingSuped && !gameOver) {

            set(0, 1);
            jumpHeight = JUMP_HEIGHT;
            jumpTimer.start();

        }

    }

    void punch() {

        if (!isAttacking() && !gameOver) {

            punchSetback();

            set(1, 3);
            stopTimer.start();

        }

    }

    void kick() {

        if (!isAttacking() && !atTop && !gameOver) {

            kickSetback();
            set(1, 4);
            stopTimer.start();

        }


    }

    void shoot() {

        if (!isAttacking() && hpMagic.hasMagic(PROJECTILE_MGI) && !isBlocking() && !gameOver) {

            stopMoving();
            shootSetback();
            hpMagic.decMagic(PROJECTILE_MGI);
            set(1, 5);
            bulletCreation();
            bulletTimer.start();
            stopTimer.start();

        }

    }

    void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !gameOver) {

            stopMoving();
            set(0, 3);
            hpMagic.decMagic(SUPER_MGI);

            if (whichPlayer[0]) {

                emergencyStop = true;
                setLocation(getX(), FightClub.height - allPic[0][3].getIconHeight() + lightUp);
                set(0, 3);

            } else if (whichPlayer[1]) {

                bulletCreation(true);
                bulletTimer.start();

            } else if (whichPlayer[2]) {

                superSetback();

            }

            stopTimer.start();

        }

    }


    void addKeyBinder(JComponent comp, int KeyCode, String id, ActionListener actionListener) {

        InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap ap = comp.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyCode, 0, false), "Pressed Once " + id);

        ap.put("Pressed Once " + id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });


    }

    void addKeyBinder(JComponent comp, int KeyCode, String id, ActionListener actionListenerP, ActionListener actionListenerR) {

        InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap ap = comp.getActionMap();


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


    void moveAct(int delay, ActionListener actionListener) {

        movementTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });
    }

    void jumpAct(int delay, ActionListener actionListener) {

        jumpTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    void stopAct(int delay, ActionListener actionListener) {

        stopTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    void bulletAct(int delay, ActionListener actionListener) {

        bulletTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }

    void hitAct(int delay, ActionListener actionListener) {

        hitTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });

    }


    void outOfBounds() {

        if (getX() + getWidth() >= FightClub.width) {

            setLocation(FightClub.width - getWidth(), getY());

        } else if (getX() <= 0) {

            setLocation(0, getY());

        }

    }

    void stopMoving() {

        //boolean for recording which button is pressed
        for (int i = 0; i < 2; i++) {

            for (int j = 0; j < 6; j++) {

                allBoolMove[i][j] = false;

            }

        }


    }

    void bulletCreation() {

        if (whichPlayer[0] || whichPlayer[1]) {

            if (whichPlayer[0]) {

                allBulltes.add(new Projectile(this, facing, 0));


            } else if (whichPlayer[1]) {

                allBulltes.add(new Projectile(this, facing, ROB_SHOOT, 1));

            }

            Main.fightWindow.add(allBulltes.get(projectStart), 0);
            ++projectStart;

        }


    }

    void bulletCreation(boolean sups) {

        if (whichPlayer[1]) {

            allBulltes.add(new Projectile(this, facing, 1, whichPlayerNum, sups));

        }

        Main.fightWindow.add(allBulltes.get(projectStart), 0);
        ++projectStart;

    }


    boolean isAllBoolFalse(boolean[][] t) {

        for (boolean[] a : t) {

            for (boolean x : a) {

                if (x) {

                    return false;

                }

            }

        }

        return true;

    }

    boolean isAttacking() {

        if (allBoolMove[0][3]) {

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

    boolean isJumping() {

        return allBoolMove[0][1];

    }

    boolean isSuper() {

        return allBoolMove[0][3];

    }

    boolean isBlocking() {

        return allBoolMove[1][1];

    }

    boolean isWalking() {

        if (!allBoolMove[1][0] && !allBoolMove[1][2]) {

            return false;

        }

        return true;

    }


}
