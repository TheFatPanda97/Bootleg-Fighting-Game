import javax.swing.*;

//robot character
public class Robot extends Player {

    private ImageIcon RNormRobStat = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Stat_v2.gif");
    private ImageIcon RNormRobWalk = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Walk_v2.gif");
    private ImageIcon RNormRobJump = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Jump_v1.gif");
    private ImageIcon RRobBlock = new ImageIcon("src/Resource/Robot/R_Rob_Block.gif");
    private ImageIcon RNormRobPunch = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Punch_v2.gif");
    private ImageIcon RNormRobShot = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Gun_v2.gif");
    private ImageIcon RNormRobKick = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Kick_v2.gif");
    private ImageIcon RNormRobSlam = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Ground_Slam_v2.gif");

    private ImageIcon LNormRobStat = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Stat_v2.gif");
    private ImageIcon LNormRobWalk = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Walk_v2.gif");
    private ImageIcon LNormRobJump = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Jump_v1.gif");
    private ImageIcon LRobBlock = new ImageIcon("src/Resource/Robot/L_Rob_Block.gif");
    private ImageIcon LNormRobPunch = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Punch_v2.gif");
    private ImageIcon LNormRobShot = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Gun_v2.gif");
    private ImageIcon LNormRobKick = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Kick_v2.gif");
    private ImageIcon LNormRobSlam = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Ground_Slam_v2.gif");

    private final int MOVE_SPEED = 11;
    private final int PROJ_SPEED = 20;

    private final int PUNCH_SPEED = 18;
    private final int KICK_SPEED = 30;
    private final int SHOOT_SPEED = 25;
    private final int SUPER_SPEED = 30;

    //default constructor
    public Robot(int WPN) {

        super();
        whichPlayerNum = WPN;

        setRobPics(whichPlayerNum);
        setWhichPlayerKeys(whichPlayerNum);
        setInitLoc(whichPlayerNum,LNormRobStat,RNormRobStat);
        setMoveSpeed(MOVE_SPEED);
        setProjectSpeed(PROJ_SPEED);

        //unique to each character, every character's move stop at a different time
        stopAct(e -> {

            //punch
            if (allBoolMove[1][3] && count == PUNCH_SPEED) {

                if (facingLeft()) {

                    moveHorizontal(RobPunchDistance);

                }


                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == KICK_SPEED) {

                stopMoving();

                if (facingLeft()) {

                    moveHorizontal(RobKickDistance);

                }


                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == SHOOT_SPEED) {


                if (facingLeft()) {

                    moveHorizontal(RobShootDistance);

                }

                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == SUPER_SPEED) {

                stopMoving();
                reset(0, 3);
                setLocGround();
                stopTimer.stop();

            }

            count++;

        });

        movementTimer.start();


    }

    public void punch() {

        if (!isAttacking() && !GameOver && !dontMove) {

            punchSetback();
            super.punch();

        }

    }

    public void kick() {

        if (!isAttacking() && !atTop && !GameOver && !dontMove) {

            kickSetback();
            super.kick();

        }

    }

    public void shoot() {

        if (!isAttacking() && hpMagic.hasMagic(PROJECTILE_MGI) && !isBlocking() && !GameOver && !dontMove) {

            hpMagic.decMagic(PROJECTILE_MGI);
            bulletCreation();
            bulletTimer.start();
            shootSetback();
            super.shoot();

        }

    }

    //similar to shoot, but the bullet is much stronger
    public void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !GameOver && !dontMove) {

            super.superPower();
            bulletCreation(true);
            bulletTimer.start();

        }

    }

    public void bulletCreation() {

        allBulltes.add(new Projectile(this, facing, ROB_SHOOT));
        super.bulletCreation();

    }

    //creates a very powerful bullet as super move
    private void bulletCreation(boolean sups) {

        allBulltes.add(new Projectile(this, facing, whichPlayerNum, sups));
        super.bulletCreation();

    }

    private void kickSetback() {

        if (facingLeft()) {

            moveHorizontal(-RobKickDistance);

        }

    }

    private void shootSetback() {

        if (facingLeft()) {

            moveHorizontal(-RobShootDistance);

        }


    }

    private void punchSetback() {

        if (facingLeft()) {

            moveHorizontal(-RobPunchDistance);

        }

    }

    //sets all robot pics
    private void setRobPics(int whichPlayerNum) {

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

        whichCharacter[ROBOT] = true;
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

    }


}
