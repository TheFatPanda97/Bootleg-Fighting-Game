import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//all character extend to this super class
public class Player extends JLabel {

    protected static boolean GameOver = false;
    protected boolean atTop = false;//whether a player is flying
    protected boolean emergencyStop = false;//this for if a wizard is doing the super move in air
    protected boolean beingSuped = false;
    protected boolean[][] allBoolMove = new boolean[2][6];
    protected boolean[] whichCharacter = new boolean[3];
    public boolean chidori;
    public boolean beingHit;

    //stores all button clicks
    InputMap im = Main.fightWindow.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap ap = Main.fightWindow.getRootPane().getActionMap();

    //all player image assets
    protected ImageIcon[][] allPic = new ImageIcon[4][6];

    //all bullets of a player
    protected ArrayList<Projectile> allBulltes = new ArrayList<>();

    Timer movementTimer;
    Timer jumpTimer;
    Timer stopTimer;
    Timer bulletTimer;

    protected final int COMMON_FLOOR = 80;//the floor height

    public static final int PROJECTTILE_DMG = 14;
    public static final int PUNCH_DMG = 12;
    public static final int SUPER_DMG = 140;

    protected final int PROJECTILE_MGI = 25;
    protected final int SUPER_MGI = 200;

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
    protected int facing = 0;

    protected int whichPlayerNum = 0;

    protected final int JUMP_HEIGHT = 55;
    protected final int ROB_SHOOT = -40;

    protected final int MOVE_TIMER = 10;
    protected final int STOP_TIMER = 10;
    protected final int JUMP_TIMER = 20;
    protected final int BULLET_TIMER = 20;

    protected final int RFACE = 0;
    protected final int LFACE = 2;

    protected final int DBULLET_FACE = -1;

    protected final int PNUM1 = 1;
    protected final int PNUM2 = 2;

    protected final int INTX = 0;

    protected final int INTX_OFFSET = 30;

    protected final int WIZARD = 0;
    protected final int ROBOT = 1;
    protected final int KAKASHI = 2;

    protected Bar hpMagic;//health and magic
    protected boolean dontMove;//player not allowed to move

    //default constructor
    public Player() {

        //sets the character selected to null
        for (int i = 0; i < whichCharacter.length; i++) {

            whichCharacter[i] = false;

        }

        //stops the player from moving
        stopMoving();

        //movement actionlister for timer
        moveAct(MOVE_TIMER, e -> {

            //stops the player from moving outside the frame
            outOfBounds();

            //if pressed D or right
            if (allBoolMove[1][2] && !dontMove) {

                //move right
                moveHorizontal(moveSpeed);
                //     set(1,2,allPic);

            }

            //if press A or left
            if (allBoolMove[1][0] && !dontMove) {

                //move left
                moveHorizontal(-moveSpeed);

            }


        });

        //jump actionlistener
        jumpAct(JUMP_TIMER, e -> {

            //player height stops increasing
            if (jumpHeight == 0) {

                atTop = true;

            }

            //if not at top, the player's Y location decreases exponentially
            if (!atTop) {

                setLocation(getX(), getLocation().y - jumpHeight);
                jumpHeight -= jumpSpeed;

            //if not at top, the player's Y location increases exponentially
            } else {

                setLocation(getX(), getLocation().y + jumpHeight);
                jumpHeight += jumpSpeed;

            }

            //if the wizard is doing super move in mid air, stop jump timer
            if (emergencyStop) {

                atTop = false;
                jumpHeight = JUMP_HEIGHT;// reset the jump to default
                emergencyStop = false;
                jumpTimer.stop();

            }

            //if the player lands on the floor, then stop this timer
            if (getLocation().y >= Fight_Club.height - getHeight() - COMMON_FLOOR) {

                atTop = false;
                reset(0, 1);
                jumpHeight = JUMP_HEIGHT;
                jumpTimer.stop();

            }


        });

        //bullet actionlister
        bulletAct(BULLET_TIMER, e -> {

            //loops though the array containing all bullet on frame of the player
            for (int i = 0; i < allBulltes.size(); i++) {

                //determines the facing of the bullet when it's first fired, it's same as where the player is facing
                if (allBulltes.get(i).face == DBULLET_FACE) {

                    allBulltes.get(i).face = facing;

                }

                //if the bullet is facing right then move it right
                if (allBulltes.get(i).face == RFACE) {

                    allBulltes.get(i).moveHorizon(projectSpeed);

                //face left then move left
                } else if (allBulltes.get(i).face == LFACE) {

                    allBulltes.get(i).moveHorizon(-projectSpeed);

                }

                //if the bullet moves out out the frame, then remove it
                if (allBulltes.get(i).getX() >= Fight_Club.width || allBulltes.get(i).getX() <= INTX) {

                    allBulltes.get(i).remove();

                }

            }


        });


    }

    //if the player is facing left
    protected boolean facingLeft() {

        if (facing == LFACE) {

            return true;

        }

        return false;

    }

    //if the player is facing right
    protected boolean facingRight() {

        if (facing == RFACE) {

            return true;

        }

        return false;

    }

    //set player 1 or 2 control
    protected void setWhichPlayerKeys(int whichPlayerNum) {

        //player 1 at the beginning face right
        if (whichPlayerNum == PNUM1) {

            facing = RFACE;
            setKeyBindingP1();//set player 1 control

        //player 2 face left
        } else if (whichPlayerNum == PNUM2) {

            facing = LFACE;
            setKeyBindingP2();//sets player 2 controls

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

    //sets which button is pressed and the corresponding image
    protected void set(int w, int h) {

        allBoolMove[w][h] = true;

        if (facing == RFACE) {

            setSize(allPic[w][h].getIconWidth(), allPic[w][h].getIconHeight());
            setIcon(allPic[w][h]);

        //face left has another set of image with same row, but different column
        } else if (facing == LFACE) {

            setSize(allPic[w + facing][h].getIconWidth(), allPic[w + facing][h].getIconHeight());
            setIcon(allPic[w + facing][h]);

        }


    }

    //resets which button is pressed
    protected void reset(int w, int h) {

        allBoolMove[w][h] = false;//that button is set to false
        count = 0;//this integer records when a button

        //if the current instance is player 1, then player 2 can be hit again
        if (whichPlayerNum == PNUM1) {

            Main.fightWindow.P2.beingHit = false;

        //vice versa
        } else if (whichPlayerNum == PNUM2) {

            Main.fightWindow.P1.beingHit = false;

        }

        //resets the gifs
        allPic[w + facing][h].getImage().flush();
        allPic[w][h].getImage().flush();

        //if the player is not doing other attack, then set it to
        if (isAllBoolFalse(allBoolMove)) {

            setIcon(allPic[facing][0]);
            setSize(allPic[facing][0].getIconWidth(), allPic[facing][0].getIconHeight());

        }

    }

    //sets the player to the ground
    protected void setLocGround() {

        //uses the static image size to determine where to put the character
        setLocation(getX(), Fight_Club.height - allPic[0][0].getIconHeight() - COMMON_FLOOR);

    }

    //moves player backwards depending on which way they are facing
    protected void setBack(int a) {

        //if facing left, then move right
        if (facing == LFACE) {

            moveHorizontal(a);

        //vice-versa
        } else if (facing == RFACE) {

            moveHorizontal(-a);

        }

    }

    //initial initial location of the players
    protected void setInitLoc(int whichPlayerNum, ImageIcon LFace, ImageIcon RFace) {


        if (whichPlayerNum == PNUM1) {

            setIcon(RFace);
            setBounds(INTX, Fight_Club.height - RFace.getIconHeight() - COMMON_FLOOR, RFace.getIconWidth(), RFace.getIconHeight());


        } else if (whichPlayerNum == PNUM2) {

            setIcon(LFace);
            setBounds(Fight_Club.width - LFace.getIconWidth() - INTX_OFFSET, Fight_Club.height - LFace.getIconHeight() - COMMON_FLOOR, LFace.getIconWidth(), LFace.getIconHeight());

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

        //sets the movement punch
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

        //sets the movement punch
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

    //pressing block button
    private void PBlock() {

        //is the player isn't attacking, jumping, getting super moved, or the game isn't over yet
        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver) {

            stopMoving();
            set(1, 1);//sets the icon to the blocking image

        }


    }

    //release block button
    private void RBlock() {

        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver) {

            reset(1, 1);//sets the image back to static or what ever move the player is still doing
        }

    }

    //press left button
    private void PLeft() {

        //similar to above method conditions except the action can't be conducted when the player is blocking and don't move is kakaashi's second super move
        if (!isAttacking() && !isBlocking() && !GameOver && !dontMove) {

            set(1, 0);

        }

    }

    //press right button
    private void PRight() {

        if (!isAttacking() && !isBlocking() && !GameOver && !dontMove) {

            set(1, 2);

        }

    }

    //release left button
    private void RLeft() {

        if (!isAttacking() && !isBlocking() && !GameOver && !dontMove) {

            reset(1, 0);

        }

    }

    //release right button
    private void RRight() {

        if (!isAttacking() && !isBlocking() && !GameOver && !dontMove) {

            reset(1, 2);

        }

    }

    private void jump() {

        if (!isAttacking() && !isJumping() && !beingSuped && !GameOver && !dontMove) {

            set(0, 1);
            jumpHeight = JUMP_HEIGHT;//initiates jump timer
            jumpTimer.start();

        }

    }

    //the methods below doesn't have conditions because it's different for every character
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

    //method for adding key moves
    protected void addKeyBinder(int KeyCode, String id, ActionListener actionListener) {

        //input maps records which key has a action associated with it
        im.put(KeyStroke.getKeyStroke(KeyCode, 0, false), "Pressed Once " + id);

        //action map conducts the action passed in
        ap.put("Pressed Once " + id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });


    }

    //similar to above method but there's a release action as well
    private void addKeyBinder(int KeyCode, String id, ActionListener actionListenerP, ActionListener actionListenerR) {

        im.put(KeyStroke.getKeyStroke(KeyCode, 0, false), "Pressed " + id);
        im.put(KeyStroke.getKeyStroke(KeyCode, 0, true), "Released " + id);//onkeyrelase set to true to carry a action when a key is release

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

    //removes kakashi's special key
    protected void removeKakaKeyBinder() {

        //I and T key
        im.remove(KeyStroke.getKeyStroke("T"));
        im.remove(KeyStroke.getKeyStroke("I"));

        ap.remove(KeyStroke.getKeyStroke("T"));
        ap.remove(KeyStroke.getKeyStroke("I"));

    }

    //pass in actionlister for moveTimer
    private void moveAct(int delay, ActionListener actionListener) {

        movementTimer = new Timer(delay, e -> {

            actionListener.actionPerformed(e);

        });
    }

    //methods below are the same methods above
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

    //stops the player from walking out of the frame
    private void outOfBounds() {

        if (getX() + getWidth() >= Fight_Club.width) {

            setLocation(Fight_Club.width - getWidth(), getY());

        } else if (getX() <= INTX) {

            setLocation(0, getY());

        }

    }

    //sets all movement boolean to false, thus stop the player from moving
    protected void stopMoving() {

        //boolean for recording which button is pressed
        for (int i = 0; i < allBoolMove.length; i++) {

            for (int j = 0; j < allBoolMove[0].length; j++) {

                allBoolMove[i][j] = false;

            }

        }


    }

    //adds bullet to screen bullet
    protected void bulletCreation() {

        //the actual bullet is created in the sub class
        Main.fightWindow.add(allBulltes.get(allBulltes.size()-1), 0);//the created bullet is added on the top of the main screen

    }

    //checks if no action is conducted
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

    //checks if the player is attacking, excluding super moves
    public boolean isAttacking() {

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

    //if super move is pressed
    protected boolean isSuper() {

        if (allBoolMove[0][3]) {

            return true;

        }

        else if (allBoolMove[0][4]) {

            return true;

        }

        return false;

    }

    protected boolean isBlocking() {

        return allBoolMove[1][1];

    }

    public static void setGameOver(boolean g) {

        GameOver = g;

    }


}
