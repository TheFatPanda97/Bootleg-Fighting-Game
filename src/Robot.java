import javax.swing.*;

public class Robot extends Player {

    ImageIcon RNormRobStat = new ImageIcon(getClass().getResource("R_Norm_Rob_Stat_v2.gif"));
    ImageIcon RNormRobWalk = new ImageIcon(getClass().getResource("R_Norm_Rob_Walk_v2.gif"));
    ImageIcon RNormRobJump = new ImageIcon(getClass().getResource("R_Norm_Rob_Jump_v1.gif"));
    ImageIcon RRobBlock = new ImageIcon(getClass().getResource("R_Rob_Block.gif"));
    ImageIcon RNormRobPunch = new ImageIcon(getClass().getResource("R_Norm_Rob_Punch_v2.gif"));
    ImageIcon RNormRobShot = new ImageIcon(getClass().getResource("R_Norm_Rob_Gun_v2.gif"));
    ImageIcon RNormRobKick = new ImageIcon(getClass().getResource("R_Norm_Rob_Kick_v2.gif"));
    ImageIcon RNormRobSlam = new ImageIcon(getClass().getResource("R_Norm_Rob_Ground_Slam_v2.gif"));

    ImageIcon LNormRobStat = new ImageIcon(getClass().getResource("L_Norm_Rob_Stat_v2.gif"));
    ImageIcon LNormRobWalk = new ImageIcon(getClass().getResource("L_Norm_Rob_Walk_v2.gif"));
    ImageIcon LNormRobJump = new ImageIcon(getClass().getResource("L_Norm_Rob_Jump_v1.gif"));
    ImageIcon LRobBlock = new ImageIcon(getClass().getResource("L_Rob_Block.gif"));
    ImageIcon LNormRobPunch = new ImageIcon(getClass().getResource("L_Norm_Rob_Punch_v2.gif"));
    ImageIcon LNormRobShot = new ImageIcon(getClass().getResource("L_Norm_Rob_Gun_v2.gif"));
    ImageIcon LNormRobKick = new ImageIcon(getClass().getResource("L_Norm_Rob_Kick_v2.gif"));
    ImageIcon LNormRobSlam = new ImageIcon(getClass().getResource("L_Norm_Rob_Ground_Slam_v2.gif"));

    Robot(JComponent RootPane, int WPN) {

        super();
        whichPlayerNum = WPN;

        setRobPics(whichPlayerNum);
        setWhichPlayer(whichPlayerNum, RootPane);
        setInitLoc(whichPlayerNum);
        setMoveSpeed(11);
        setProjectSpeed(20);

        stopAct(10, e -> {

            //punch
            if (allBoolMove[1][3] && count == 18) {

                if (facingLeft()) {

                    moveHorizontal(RobPunchDistance);

                }


                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == 30) {

                stopMoving();

                if (facingLeft()) {

                    moveHorizontal(RobKickDistance);

                }


                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == 25) {


                if (facingLeft()) {

                    moveHorizontal(RobShootDistance);

                }

                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == 30) {

                stopMoving();
                reset(0, 3);
                setLocGround();
                stopTimer.stop();

            }

            count++;

        });

        movementTimer.start();


    }

    void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setIcon(RNormRobStat);
            setBounds(0, FightClub.height - RNormRobStat.getIconHeight() - COMMON_FLOOR, RNormRobStat.getIconWidth(), RNormRobStat.getIconHeight());


        } else if (whichPlayerNum == 2) {

            setIcon(LNormRobStat);
            setBounds(FightClub.width - LNormRobStat.getIconWidth() - 30, FightClub.height - LNormRobStat.getIconHeight() - COMMON_FLOOR, LNormRobStat.getIconWidth(), LNormRobStat.getIconHeight());

        }

    }

    void setRobPics(int whichPlayerNum) {

        allPic[0][0] = RNormRobStat;
        allPic[0][1] = RNormRobJump;
        allPic[1][0] = LNormRobWalk;
        allPic[1][1] = RRobBlock;
        allPic[1][2] = RNormRobWalk;
        allPic[0][3] = RNormRobSlam;
        allPic[1][3] = RNormRobPunch;
        allPic[1][4] = RNormRobKick;
        allPic[1][5] = RNormRobShot;

        allPic[2][0] = LNormRobStat;
        allPic[2][1] = LNormRobJump;
        allPic[3][0] = LNormRobWalk;
        allPic[3][1] = LRobBlock;
        allPic[3][2] = RNormRobWalk;
        allPic[2][3] = LNormRobSlam;
        allPic[3][3] = LNormRobPunch;
        allPic[3][4] = LNormRobKick;
        allPic[3][5] = LNormRobShot;

        whichPlayer[1] = true;
        hpMagic = new Bar(whichPlayerNum, whichPlayer);


    }


}
