import javax.swing.*;

public class Wizard extends Player {

    private ImageIcon RNormWizStat = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Stat_v1.gif");
    private ImageIcon RNormWizWalk = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Walk_v1.gif");
    private ImageIcon RNormWizBlock = new ImageIcon("src/Resource/Wizard/R_Wiz_Block.gif");
    private ImageIcon RNormWizJump = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Jump_v2.gif");
    private ImageIcon RNormWizPunch = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Punch_v3.gif");
    private ImageIcon RNormWizShot = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Shot_v1.gif");
    private ImageIcon RNormWizSpin = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_SPin_v1.gif");
    private ImageIcon RNormWizLight = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Lightning_v4.gif");

    private ImageIcon LNormWizStat = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Stat_v1.gif");
    private ImageIcon LNormWizWalk = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Walk_v1.gif");
    private ImageIcon LNormWizBlock = new ImageIcon("src/Resource/Wizard/L_Wiz_Block.gif");
    private ImageIcon LNormWizJump = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Jump_v2.gif");
    private ImageIcon LNormWizPunch = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Punch_v3.gif");
    private ImageIcon LNormWizShot = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Shot_v1.gif");
    private ImageIcon LNormWizSpin = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_SPin_v1.gif");
    private ImageIcon LNormWizLight = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Lightning_v4.gif");

    public Wizard(JComponent RootPane, int WPN) {

        super();
        whichPlayerNum = WPN;

        setWizPics(whichPlayerNum);
        setInitLoc(whichPlayerNum);
        setWhichPlayer(whichPlayerNum, RootPane);
        setMoveSpeed(8);
        setProjectSpeed(30);


        stopAct(e -> {

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

    public void setInitLoc(int whichPlayerNum) {

        if (whichPlayerNum == 1) {

            setIcon(RNormWizStat);
            setBounds(0, Fight_Club.height - RNormWizStat.getIconHeight() - COMMON_FLOOR, RNormWizStat.getIconWidth(), RNormWizStat.getIconHeight());


        } else if (whichPlayerNum == 2) {

            setIcon(LNormWizStat);
            setBounds(Fight_Club.width - LNormWizStat.getIconWidth() - 30, Fight_Club.height - LNormWizStat.getIconHeight() - COMMON_FLOOR, LNormWizStat.getIconWidth(), LNormWizStat.getIconHeight());

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

    public void superPower() {

        if (!isAttacking() && hpMagic.hasMagic(SUPER_MGI) && !isBlocking() && !GameOver && !dontMove) {

            super.superPower();
            emergencyStop = true;
            setLocation(getX(), Fight_Club.height - allPic[0][3].getIconHeight() + lightUp);

        }

    }

    public void bulletCreation() {

        allBulltes.add(new Projectile(this, facing, 0));
        super.bulletCreation();

    }

    private void kickSetback() {

        setLocation(getX(), getY() + spinDown);

    }

    private void shootSetback() {

        if (facingLeft()) {

            moveHorizontal(-WizShootDistance);

        }

    }

    private void punchSetback() {

        if (facingLeft()) {

            moveHorizontal(-WizPunchDistance);

        }

    }

    //setup pics
    private void setWizPics(int whichPlayerNum) {

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
        setBounds(0, Fight_Club.height - RNormWizStat.getIconHeight() - COMMON_FLOOR, RNormWizStat.getIconWidth(), RNormWizStat.getIconHeight());

        whichCharacter[0] = true;
        hpMagic = new Bar(whichPlayerNum, whichCharacter);

    }


}
