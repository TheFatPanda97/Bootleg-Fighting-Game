import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    private ImageIcon temp;

    private int superFacing = 0;
    private int countUp = 0;

    private Timer pauseTimer;

    private ArrayList<Integer> allkakaData;

    public Kakashi(int WPN) {

        super();

        allkakaData = Main.rw.readKakaData();

        whichPlayerNum = WPN;

        setKakaIntState();
        setKakaPics(whichPlayerNum);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum);
        setMoveSpeed(allkakaData.get(0));
        setProjectSpeed(allkakaData.get(3));

        movementTimer.start();

    }

    public Kakashi(int WPN, ImageIcon[][] p, ArrayList<Integer> d) {

        super();

        whichPlayerNum = WPN;
        allkakaData = d;

        setKakaIntState();
        setKakaPics(whichPlayerNum, p);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum);
        setMoveSpeed(d.get(0));
        setProjectSpeed(d.get(3));

        movementTimer.start();

    }

    private void stopAct() {

        stopTimer = new Timer(10, e -> {

            //punch
            if (allBoolMove[1][3] && count == allkakaData.get(1)) {

                if (facingLeft()) {

                    moveHorizontal(KakaPunchDistance);

                }
                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == allkakaData.get(1)) {

                stopMoving();
                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == allkakaData.get(2)) {

                teleport();
                stopMoving();
                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == allkakaData.get(5)) {

                teleport(superFacing);
                chidori = true;

                //super continued
            } else if (allBoolMove[0][4] && count == allkakaData.get(4)) {

                if (whichPlayerNum == 1 && !Main.fightWindow.P2.isBlocking()) {

                    Main.fightWindow.P2.dontMove = true;
                    temp = (ImageIcon) Main.fightWindow.background.getIcon();
                    Main.fightWindow.background.setIcon(imgRescaler(Genjutsu, All_Windows.width, All_Windows.height));

                } else if (whichPlayerNum == 2 && !Main.fightWindow.P1.isBlocking()) {

                    Main.fightWindow.P1.dontMove = true;
                    temp = (ImageIcon) Main.fightWindow.background.getIcon();
                    Main.fightWindow.background.setIcon(imgRescaler(Genjutsu, All_Windows.width, All_Windows.height));

                }

                reset(0, 4);
                stopTimer.stop();
                pauseTimer.start();


                //super continued
            } else if (allBoolMove[0][3] && count == allkakaData.get(6)) {

                chidori = false;
                stopMoving();
                moveVertical(-KakaSuperDistance);
                reset(0, 3);

                if (whichPlayerNum == 1) {

                    Main.fightWindow.P2.reset(1, 1);
                    Main.fightWindow.P2.beingSuped = false;
                    Main.fightWindow.P2.setLocation(Main.fightWindow.P2.getX(), getY());

                } else {

                    Main.fightWindow.P1.reset(1, 1);
                    Main.fightWindow.P1.beingSuped = false;
                    Main.fightWindow.P1.setLocation(Main.fightWindow.P1.getX(), getY());

                }
                stopTimer.stop();

            }


            if (allBoolMove[0][3] && count >= allkakaData.get(5)) {

                if (superFacing == 0) {

                    moveHorizontal(-moveSpeed * 2);

                } else {

                    moveHorizontal(moveSpeed * 2);

                }


            }

            count++;

        });

    }

    private void pauseAct() {

        pauseTimer = new Timer(1000, e -> {

            if (whichPlayerNum == 1 && !Main.fightWindow.P2.dontMove) {

                pauseTimer.stop();

            } else if (whichPlayerNum == 2 && !Main.fightWindow.P1.dontMove) {

                pauseTimer.stop();

            }

            if (countUp == 2) {

                if (whichPlayerNum == 1 && !Main.fightWindow.P2.isBlocking()) {

                    Main.fightWindow.P2.stopMoving();
                    Main.fightWindow.P2.dontMove = false;

                } else if (whichPlayerNum == 2 && !Main.fightWindow.P1.isBlocking()) {

                    Main.fightWindow.P1.stopMoving();
                    Main.fightWindow.P1.dontMove = false;

                }

                Main.fightWindow.background.setIcon(temp);
                countUp = 0;
                pauseTimer.stop();

            }

            countUp++;

        });
    }

    private void summon() {

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

    public void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !GameOver) {

            super.superPower();
            superSetback();
            superFacing = facing;

        }

    }

    public void bulletCreation() {

    }

    private void punchSetback() {

        if (facingLeft()) {

            moveHorizontal(-KakaPunchDistance);

        }

    }

    private void superSetback() {

        moveVertical(KakaSuperDistance);

    }

    private void setKakaIntState() {

        if (whichPlayerNum == 1) {

            addKeyBinder(KeyEvent.VK_T, "P1Summon", e -> summon());

        } else {

            addKeyBinder(KeyEvent.VK_I, "P2Summon", e -> summon());

        }

        stopAct();
        pauseAct();

    }

    public void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setIcon(RKakaStat);
            setBounds(0, Fight_Club.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());


        } else if (whichPlayerNum == 2) {

            setIcon(LKakaStat);
            setBounds(Fight_Club.width - LKakaStat.getIconWidth() - 30, Fight_Club.height - LKakaStat.getIconHeight() - COMMON_FLOOR, LKakaStat.getIconWidth(), LKakaStat.getIconHeight());

        }

    }

    //setup pics
    private void setKakaPics(int whichPlayerNum) {

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


        setIcon(RKakaStat);
        setBounds(0, Fight_Club.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());

        whichCharacter[2] = true;
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

    }

    //setup pics

    private void setKakaPics(int whichPlayerNum, ImageIcon[][] p) {

        allPic = p;

        RKakaStat = allPic[0][0];
        LKakaStat = allPic[2][0];
        RKakaChi = allPic[0][3];

        setIcon(RKakaStat);
        setBounds(0, Fight_Club.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());

        whichCharacter[2] = true;
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

    }

    private void teleport() {

        if (facingRight()) {

            setLocation(All_Windows.width - RKakaChi.getIconWidth(), getY());

        } else {

            setLocation(0, getY());

        }

    }

    private void teleport(int face) {

        if (face == 0) {

            setLocation(All_Windows.width - RKakaChi.getIconWidth(), getY());

        } else {

            setLocation(0, getY());

        }

    }

    //resize images to correct size

    private ImageIcon imgRescaler(ImageIcon img, int w, int h) {

        //complete magic here
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));

    }


}
