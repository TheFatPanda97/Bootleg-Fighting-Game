import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//DLC character
public class Kakashi extends Player {

    private ImageIcon RKakaStat;
    private ImageIcon RKakaWalk;
    private ImageIcon RKakaBlock;
    private ImageIcon RKakaJump;
    private ImageIcon RKakaPunch;
    private ImageIcon RKakaKick;
    private ImageIcon RKakaTele;
    private ImageIcon RKakaChi;
    private ImageIcon RKakaSummon;

    private ImageIcon LKakaStat;
    private ImageIcon LKakaWalk;
    private ImageIcon LKakaBlock;
    private ImageIcon LKakaJump;
    private ImageIcon LKakaPunch;
    private ImageIcon LKakaKick;
    private ImageIcon LKakaTele;
    private ImageIcon LKakaChi;
    private ImageIcon LKakaSummon;

    private ImageIcon Genjutsu = new ImageIcon("src/Resource/Kakashi/Genjutsu.gif");
    private ImageIcon temp;//stores the temporary map of the fight game

    private int superFacing = 0;//determines the facing when the super move is conducted
    private int countUp = 0;

    private Timer pauseTimer;//kakashi second super move timer

    private ArrayList<Integer> allkakaData;
    //index of which the moves stop, stored in allkakaData
    private final int IMOVE_SPEED = 0;
    private final int IPROJ_SPEED = 3;
    private final int IPUNCH_KICK_SPEED = 1;
    private final int ISHOOT_SPEED = 2;
    private final int ISUPER_SPEED = 5;
    private final int ISTOP_SPEED = 4;
    private final int ISUPER_CONTI_SPEED = 6;

    private final int PAUSE_TIMER = 1000;
    private final int COUNT_LIMIT = 2;

    //default constructor
    public Kakashi(int WPN) {

        super();

        //reads from txt the DLC data
        allkakaData = Main.rw.readDLCData();

        whichPlayerNum = WPN;

        setKakaIntState();
        setKakaPics();
        setInitLoc(whichPlayerNum, LKakaStat, RKakaStat);
        setWhichPlayerKeys(whichPlayerNum);
        setMoveSpeed(allkakaData.get(IMOVE_SPEED));
        setProjectSpeed(allkakaData.get(IPROJ_SPEED));

        //top magic and health bar
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

        movementTimer.start();

    }

    //identical to the top constructor except all images and stop timer data is passed in as parameter, used for for when the DLc is first downloaded
    public Kakashi(int WPN, ImageIcon[][] p, ArrayList<Integer> d) {

        super();

        whichPlayerNum = WPN;
        allkakaData = d;

        setKakaIntState();
        setKakaPics(p);
        setInitLoc(whichPlayerNum, LKakaStat, RKakaStat);
        setWhichPlayerKeys(whichPlayerNum);
        setMoveSpeed(d.get(IMOVE_SPEED));
        setProjectSpeed(d.get(IPROJ_SPEED));

        hpMagic = new Bar(whichPlayerNum, whichCharacter);

        movementTimer.start();

    }

    //stop timer actionlistener
    private void stopAct() {

        stopTimer = new Timer(STOP_TIMER, e -> {

            //punch
            if (allBoolMove[1][3] && count == allkakaData.get(IPUNCH_KICK_SPEED)) {

                //if the facing left, kakashi distance has to be moved slightly right
                if (facingLeft()) {

                    moveHorizontal(KakaPunchDistance);

                }

                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == allkakaData.get(IPUNCH_KICK_SPEED)) {

                stopMoving();
                reset(1, 4);
                stopTimer.stop();

                //teleport
            } else if (allBoolMove[1][5] && count == allkakaData.get(ISHOOT_SPEED)) {

                //kakashi will teleport to the other side of the arena when certain amount of timer has passed
                teleport();
                stopMoving();
                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == allkakaData.get(ISUPER_SPEED)) {

                teleport(superFacing);
                chidori = true;

                //Genjustsu continued
            } else if (allBoolMove[0][4] && count == allkakaData.get(ISTOP_SPEED)) {

                //if the other player gets hit with this move, then stop the other player form moving
                if (whichPlayerNum == PNUM1 && !Main.fightWindow.P2.isBlocking()) {

                    Main.fightWindow.P2.dontMove = true;

                } else if (whichPlayerNum == PNUM2 && !Main.fightWindow.P1.isBlocking()) {

                    Main.fightWindow.P1.dontMove = true;

                }

                //the current map is saved and replaced with the super move background
                temp = (ImageIcon) Main.fightWindow.background.getIcon();
                Main.fightWindow.background.setIcon(imgRescaler(Genjutsu, All_Windows.width, All_Windows.height));
                reset(0, 4);
                stopTimer.stop();
                pauseTimer.start();


                //super continued
            } else if (allBoolMove[0][3] && count == allkakaData.get(ISUPER_CONTI_SPEED)) {

                chidori = false;
                stopMoving();
                moveVertical(-KakaSuperDistance);//stop move makes kakashi slightly up, so its 'y' is reseted
                reset(0, 3);

                //if the other player was getting hit, reset their location to normal
                if (whichPlayerNum == PNUM1 && Main.fightWindow.P2.beingSuped) {

                    supReset(Main.fightWindow.P2);

                } else if (whichPlayerNum == PNUM2 && Main.fightWindow.P1.beingSuped) {

                    supReset(Main.fightWindow.P1);

                }

                stopTimer.stop();

            }

            //movement of character when doing super move
            if (allBoolMove[0][3] && count >= allkakaData.get(ISUPER_SPEED)) {

                //facing right go left, because kakashi will teleport to the left
                if (superFacing == RFACE) {

                    moveHorizontal(-moveSpeed * 2);

                    //vice versa
                } else {

                    moveHorizontal(moveSpeed * 2);

                }


            }

            count++;

        });

    }

    //second super move duration timer
    private void pauseAct() {

        pauseTimer = new Timer(PAUSE_TIMER, e -> {

            //if the other player wasn't hit by the super move, then stop the timer
            if (whichPlayerNum == PNUM1 && !Main.fightWindow.P2.dontMove) {

                pauseTimer.stop();

            } else if (whichPlayerNum == PNUM2 && !Main.fightWindow.P1.dontMove) {

                pauseTimer.stop();

            }

            //after 2 seconds, the super move stops
            if (countUp == COUNT_LIMIT) {

                //the other player is allowed to moving again
                if (whichPlayerNum == PNUM1) {

                    Main.fightWindow.P2.dontMove = false;

                } else if (whichPlayerNum == PNUM2) {

                    Main.fightWindow.P1.dontMove = false;

                }

                //background reset to normal
                Main.fightWindow.background.setIcon(temp);
                countUp = 0;
                pauseTimer.stop();

            }

            countUp++;

        });
    }

    //first super move
    public void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !GameOver) {

            super.superPower();
            superSetback();
            superFacing = facing;//so kakashi will attack in a straight line

        }

    }

    //second super move
    private void summon() {

        //have enough magic is the new condition
        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !atTop && !GameOver) {

            hpMagic.decMagic(SUPER_MGI);
            stopMoving();
            set(0, 4);
            stopTimer.start();

        }

    }

    public void punch() {

        if (!isAttacking() && !GameOver && !dontMove) {

            punchSetback();
            super.punch();

        }

    }

    public void kick() {

        if (!isAttacking() && !atTop && !GameOver && !dontMove) {

            super.kick();

        }

    }

    public void shoot() {

        if (!isAttacking() && !isBlocking() && !GameOver && !dontMove) {

            super.shoot();

        }

    }

    //empty overriding method so kakashi doesn't create a bullet
    public void bulletCreation() {

    }

    //moves kakashi back slightly after he makes so moves
    private void punchSetback() {

        if (facingLeft()) {

            moveHorizontal(-KakaPunchDistance);

        }

    }

    private void superSetback() {

        moveVertical(KakaSuperDistance);

    }

    //set kakashi's extra key
    private void setKakaIntState() {

        //player 1 gets a extra 'T' key
        if (whichPlayerNum == PNUM1) {

            addKeyBinder(KeyEvent.VK_T, "P1Summon", e -> summon());

        //player 2 gets a extra 'I' key
        } else {

            addKeyBinder(KeyEvent.VK_I, "P2Summon", e -> summon());

        }

        stopAct();
        pauseAct();

    }

    //setup pics
    private void setKakaPics() {

        RKakaStat = new ImageIcon("src/Resource/Kakashi/RKakaStat.gif");
        RKakaWalk = new ImageIcon("src/Resource/Kakashi/DLC/RKakaWalk.gif");
        RKakaBlock = new ImageIcon("src/Resource/Kakashi/DLC/RKakaBlock.gif");
        RKakaJump = new ImageIcon("src/Resource/Kakashi/DLC/RKakaJump.gif");
        RKakaPunch = new ImageIcon("src/Resource/Kakashi/DLC/RKakaPunch.gif");
        RKakaKick = new ImageIcon("src/Resource/Kakashi/DLC/RKakaKick.gif");
        RKakaTele = new ImageIcon("src/Resource/Kakashi/DLC/RKakaTeleport.gif");
        RKakaChi = new ImageIcon("src/Resource/Kakashi/DLC/RKakaSuper.gif");
        RKakaSummon = new ImageIcon("src/Resource/Kakashi/DLC/RKakaSummon.gif");

        LKakaStat = new ImageIcon("src/Resource/Kakashi/LKakaStat.gif");
        LKakaWalk = new ImageIcon("src/Resource/Kakashi/DLC/LKakaWalk.gif");
        LKakaBlock = new ImageIcon("src/Resource/Kakashi/DLC/LKakaBlock.gif");
        LKakaJump = new ImageIcon("src/Resource/Kakashi/DLC/LKakaJump.gif");
        LKakaPunch = new ImageIcon("src/Resource/Kakashi/DLC/LKakaPunch.gif");
        LKakaKick = new ImageIcon("src/Resource/Kakashi/DLC/LKakaKick.gif");
        LKakaTele = new ImageIcon("src/Resource/Kakashi/DLC/RKakaTeleport.gif");
        LKakaChi = new ImageIcon("src/Resource/Kakashi/DLC/LKakaSuper.gif");
        LKakaSummon = new ImageIcon("src/Resource/Kakashi/DLC/LKakaSummon.gif");

        //setting pics
        allPic[0][0] = RKakaStat;
        allPic[0][1] = RKakaJump;
        allPic[0][3] = RKakaChi;
        allPic[0][4] = RKakaSummon;
        allPic[1][0] = LKakaWalk;
        allPic[1][1] = RKakaBlock;
        allPic[1][2] = RKakaWalk;
        allPic[1][3] = RKakaPunch;
        allPic[1][4] = RKakaKick;
        allPic[1][5] = RKakaTele;


        allPic[2][0] = LKakaStat;
        allPic[2][1] = LKakaJump;
        allPic[2][3] = LKakaChi;
        allPic[2][4] = LKakaSummon;
        allPic[3][0] = LKakaWalk;
        allPic[3][1] = LKakaBlock;
        allPic[3][2] = RKakaWalk;
        allPic[3][3] = LKakaPunch;
        allPic[3][4] = LKakaKick;
        allPic[3][5] = LKakaTele;


        whichCharacter[KAKASHI] = true;
    }

    //setup pics for the first time download
    private void setKakaPics(ImageIcon[][] p) {

        allPic = p;

        //these are the 3 images that are stored lcoally already
        RKakaStat = allPic[0][0];
        LKakaStat = allPic[2][0];
        RKakaChi = allPic[0][3];

        whichCharacter[KAKASHI] = true;
    }

    //teleport the character
    private void teleport() {

        //if the player is facing right, teleport the player to the right side of the map
        if (facingRight()) {

            setLocation(All_Windows.width - RKakaChi.getIconWidth(), getY());

        //vice-versa
        } else {

            setLocation(INTX, getY());

        }

    }

    //used for teleporting kakashi while he's doing his super move
    private void teleport(int face) {

        //uses passed in face variable instead of the one stored locally
        if (face == RFACE) {

            setLocation(All_Windows.width - RKakaChi.getIconWidth(), getY());

        } else {

            setLocation(INTX, getY());

        }

    }

    //resize images to correct size
    private ImageIcon imgRescaler(ImageIcon img, int w, int h) {

        //complete magic here
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));

    }

    //moves the other player to the correct position
    private void supReset(Player a) {

        //the other player is set to the same y location as kakashi
        a.beingSuped = false;
        a.setLocation(a.getX(), getY());

    }


}
