import javax.swing.*;

public class Wizard extends Player {

    ImageIcon RNormWizStat = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Stat_v1.gif");
    ImageIcon RNormWizWalk = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Walk_v1.gif");
    ImageIcon RNormWizBlock = new ImageIcon("src/Resource/Wizard/R_Wiz_Block.gif");
    ImageIcon RNormWizJump = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Jump_v2.gif");
    ImageIcon RNormWizPunch = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Punch_v3.gif");
    ImageIcon RNormWizShot = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Shot_v1.gif");
    ImageIcon RNormWizSpin = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_SPin_v1.gif");
    ImageIcon RNormWizLight = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Lightning_v4.gif");

    ImageIcon LNormWizStat = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Stat_v1.gif");
    ImageIcon LNormWizWalk = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Walk_v1.gif");
    ImageIcon LNormWizBlock = new ImageIcon("src/Resource/Wizard/L_Wiz_Block.gif");
    ImageIcon LNormWizJump = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Jump_v2.gif");
    ImageIcon LNormWizPunch = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Punch_v3.gif");
    ImageIcon LNormWizShot = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Shot_v1.gif");
    ImageIcon LNormWizSpin = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_SPin_v1.gif");
    ImageIcon LNormWizLight = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Lightning_v4.gif");

    Wizard(JComponent RootPane, int WPN) {

        whichPlayerNum = WPN;

        setWizPics(whichPlayerNum);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum, RootPane);
        setMoveSpeed(8);
        setProjectSpeed(30);


        stopAct(10, e -> {

            //punch
            if (allBoolMove[1][3] && count == 18) {

                if (facingLeft()) {

                    moveHorizontal(WizPunchDistance);

                }
                stopMoving();
                reset(1, 3);
                stopTimer.stop();

                //kick
            } else if (allBoolMove[1][4] && count == 18) {

                setLocation(getLocation().x, getY() - spinDown);
                stopMoving();
                reset(1, 4);
                stopTimer.stop();

                //shoot
            } else if (allBoolMove[1][5] && count == 25) {

                stopMoving();
                if (facingLeft()) {

                    moveHorizontal(WizShootDistance);

                }

                reset(1, 5);
                stopTimer.stop();

                //super
            } else if (allBoolMove[0][3] && count == 30) {

                stopMoving();
                emergencyStop = false;
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

            setIcon(RNormWizStat);
            setBounds(0, FightClub.height - RNormWizStat.getIconHeight() - COMMON_FLOOR, RNormWizStat.getIconWidth(), RNormWizStat.getIconHeight());


        } else if (whichPlayerNum == 2) {

            setIcon(LNormWizStat);
            setBounds(FightClub.width - LNormWizStat.getIconWidth() - 30, FightClub.height - LNormWizStat.getIconHeight() - COMMON_FLOOR, LNormWizStat.getIconWidth(), LNormWizStat.getIconHeight());

        }

    }

    void punch() {

        if (!isAttacking() && !GameOver) {

            punchSetback();
            super.punch();

        }

    }

    void kick() {

        if (!isAttacking() && !atTop && !GameOver) {

            kickSetback();
            super.kick();

        }

    }

    void shoot() {

        if (!isAttacking() && hpMagic.hasMagic(PROJECTILE_MGI) && !isBlocking() && !GameOver) {

            hpMagic.decMagic(PROJECTILE_MGI);
            bulletCreation();
            bulletTimer.start();
            shootSetback();
            super.shoot();

        }

    }

    void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !GameOver) {

            super.superPower();
            emergencyStop = true;
            setLocation(getX(), FightClub.height - allPic[0][3].getIconHeight() + lightUp);

        }

    }

    void bulletCreation() {

        allBulltes.add(new Projectile(this, facing, 0));
        super.bulletCreation();

    }

    void kickSetback() {

        setLocation(getX(), getY() + spinDown);

    }

    void shootSetback() {

        if (facingLeft()) {

            moveHorizontal(-WizShootDistance);

        }

    }

    void punchSetback() {

        if (facingLeft()) {

            moveHorizontal(-WizPunchDistance);

        }

    }

    //setup pics
    void setWizPics(int whichPlayerNum) {

        //setting pics
        allPic[0][0] = RNormWizStat;
        allPic[0][1] = RNormWizJump;
        allPic[1][0] = LNormWizWalk;
        allPic[1][1] = RNormWizBlock;
        allPic[1][2] = RNormWizWalk;
        allPic[0][3] = RNormWizLight;
        allPic[1][3] = RNormWizPunch;
        allPic[1][4] = RNormWizSpin;
        allPic[1][5] = RNormWizShot;

        allPic[2][0] = LNormWizStat;
        allPic[2][1] = LNormWizJump;
        allPic[3][0] = LNormWizWalk;
        allPic[3][1] = LNormWizBlock;
        allPic[3][2] = RNormWizWalk;
        allPic[2][3] = LNormWizLight;
        allPic[3][3] = LNormWizPunch;
        allPic[3][4] = LNormWizSpin;
        allPic[3][5] = LNormWizShot;


        setIcon(RNormWizStat);
        setBounds(0, FightClub.height - RNormWizStat.getIconHeight() - COMMON_FLOOR, RNormWizStat.getIconWidth(), RNormWizStat.getIconHeight());

        whichCharacter[0] = true;
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

    }


}
