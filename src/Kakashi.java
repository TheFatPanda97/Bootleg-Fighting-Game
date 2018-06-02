import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class Kakashi extends Player {

    ImageIcon RKakaStat;
    ImageIcon RKakaWalk;
    ImageIcon RKakaBlock;
    ImageIcon RKakaJump;
    ImageIcon RKakaPunch;
    ImageIcon RKakaKick;
    ImageIcon RKakaTele;
    ImageIcon RKakaChi;

    ImageIcon LKakaStat;
    ImageIcon LKakaWalk;
    ImageIcon LKakaBlock;
    ImageIcon LKakaJump;
    ImageIcon LKakaPunch;
    ImageIcon LKakaKick;
    ImageIcon LKakaTele;
    ImageIcon LKakaChi;

    int superFacing = 0;

    public Kakashi(JComponent RootPane, int WPN) {

        whichPlayerNum = WPN;

        setKakaPics(whichPlayerNum);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum, RootPane);
        setMoveSpeed(13);
        setProjectSpeed(30);


        stopAct(10, e -> {

            //punch
            if (allBoolMove[1][3] && count == 18) {

                if (facingLeft()) {

                    setLocation(getLocation().x + WizPunchDistance, getY());

                }
                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == 18) {

                stopMoving();
                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == 25) {

                teleport();
                stopMoving();
                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == 140) {

                superFacing = facing;
                teleport();
                chidori = true;

                //super continued
            } else if (allBoolMove[0][3] && count == 210) {

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

            if (allBoolMove[0][3] && count >= 140) {

                if (superFacing == 0) {

                    moveHorizontal(-moveSpeed * 2);

                } else {

                    moveHorizontal(moveSpeed * 2);

                }


            }

            count++;

        });


        movementTimer.start();

    }

    public Kakashi(JComponent RootPane, int WPN, ImageIcon[][] p) {

        whichPlayerNum = WPN;

        setKakaPics(whichPlayerNum, p);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum, RootPane);
        setMoveSpeed(13);
        setProjectSpeed(30);


        stopAct(10, e -> {

            //punch
            if (allBoolMove[1][3] && count == 18) {

                if (facingLeft()) {

                    setLocation(getLocation().x + WizPunchDistance, getY());

                }
                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == 18) {

                stopMoving();
                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == 25) {

                teleport();
                stopMoving();
                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == 140) {

                superFacing = facing;
                teleport();
                chidori = true;

                //super continued
            } else if (allBoolMove[0][3] && count == 210) {

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

            if (allBoolMove[0][3] && count >= 140) {

                if (superFacing == 0) {

                    moveHorizontal(-moveSpeed * 2);

                } else {

                    moveHorizontal(moveSpeed * 2);

                }


            }

            count++;

        });


        movementTimer.start();

    }

    void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setIcon(RKakaStat);
            setBounds(0, FightClub.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());


        } else if (whichPlayerNum == 2) {

            setIcon(LKakaStat);
            setBounds(FightClub.width - LKakaStat.getIconWidth() - 30, FightClub.height - LKakaStat.getIconHeight() - COMMON_FLOOR, LKakaStat.getIconWidth(), LKakaStat.getIconHeight());

        }

    }

    //setup pics
    void setKakaPics(int whichPlayerNum) {

        RKakaStat = new ImageIcon(getClass().getResource("RKakaStat.gif"));
        RKakaWalk = new ImageIcon(getClass().getResource("RKakaWalk.gif"));
        RKakaBlock = new ImageIcon(getClass().getResource("RKakaBlock.gif"));
        RKakaJump = new ImageIcon(getClass().getResource("RKakaJump.gif"));
        RKakaPunch = new ImageIcon(getClass().getResource("RKakaPunch.gif"));
        RKakaKick = new ImageIcon(getClass().getResource("RKakaKick.gif"));
        RKakaTele = new ImageIcon(getClass().getResource("RKakaTeleport.gif"));
        RKakaChi = new ImageIcon(getClass().getResource("RKakaSuper.gif"));

        LKakaStat = new ImageIcon(getClass().getResource("LKakaStat.gif"));
        LKakaWalk = new ImageIcon(getClass().getResource("LKakaWalk.gif"));
        LKakaBlock = new ImageIcon(getClass().getResource("LKakaBlock.gif"));
        LKakaJump = new ImageIcon(getClass().getResource("LKakaJump.gif"));
        LKakaPunch = new ImageIcon(getClass().getResource("LKakaPunch.gif"));
        LKakaKick = new ImageIcon(getClass().getResource("LKakaKick.gif"));
        LKakaTele = new ImageIcon(getClass().getResource("RKakaTeleport.gif"));
        LKakaChi = new ImageIcon(getClass().getResource("LKakaSuper.gif"));

        //setting pics
        allPic[0][0] = RKakaStat;
        allPic[0][1] = RKakaJump;
        allPic[1][0] = LKakaWalk;
        allPic[1][1] = RKakaBlock;
        allPic[1][2] = RKakaWalk;
        allPic[0][3] = RKakaChi;
        allPic[1][3] = RKakaPunch;
        allPic[1][4] = RKakaKick;
        allPic[1][5] = RKakaTele;

        allPic[2][0] = LKakaStat;
        allPic[2][1] = LKakaJump;
        allPic[3][0] = LKakaWalk;
        allPic[3][1] = LKakaBlock;
        allPic[3][2] = RKakaWalk;
        allPic[2][3] = LKakaChi;
        allPic[3][3] = LKakaPunch;
        allPic[3][4] = LKakaKick;
        allPic[3][5] = LKakaTele;


        setIcon(RKakaStat);
        setBounds(0, FightClub.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());

        whichPlayer[2] = true;
        hpMagic = new Bar(whichPlayerNum, whichPlayer);

    }

    //setup pics
    void setKakaPics(int whichPlayerNum, ImageIcon[][] p) {

        allPic = p;

        RKakaStat = allPic[0][0];
        LKakaStat = allPic[2][0];
        RKakaChi = allPic[0][3];

        setIcon(RKakaStat);
        setBounds(0, FightClub.height - RKakaStat.getIconHeight() - COMMON_FLOOR, RKakaStat.getIconWidth(), RKakaStat.getIconHeight());

        whichPlayer[2] = true;
        hpMagic = new Bar(whichPlayerNum, whichPlayer);

    }

    void teleport() {

        if (facingRight()) {

            setLocation(AllWindows.width - RKakaChi.getIconWidth(), getY());

        } else {

            setLocation(0, getY());

        }

    }


}
