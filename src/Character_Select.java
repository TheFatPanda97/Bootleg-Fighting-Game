import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Character_Select extends All_Windows {

    private boolean firstDownload;

    private ImageIcon CSelect = new ImageIcon("src/Resource/Screen_Background/player selection.jpg");
    private ImageIcon imgHighlight = new ImageIcon("src/Resource/Decoration/Button Highlight.png");

    private ImageIcon WizFace = new ImageIcon("src/Resource/Mugshots/RWizFace.png");
    private ImageIcon KakaFace = new ImageIcon("src/Resource/Mugshots/KakaFace.png");
    private ImageIcon RobFace = new ImageIcon("src/Resource/Mugshots/RobFace.png");

    private ImageIcon P1Select = new ImageIcon("src/Resource/Hexagon/P1.png");
    private ImageIcon P2Select = new ImageIcon("src/Resource/Hexagon/P2.png");
    private ImageIcon Together = new ImageIcon("src/Resource/Hexagon/Together.png");

    public ImageIcon RNormWizStat = new ImageIcon("src/Resource/Wizard/R_Norm_Wiz_Stat_v1.gif");
    public ImageIcon LNormWizStat = new ImageIcon("src/Resource/Wizard/L_Norm_Wiz_Stat_v1.gif");

    public ImageIcon RNormRobStat = new ImageIcon("src/Resource/Robot/R_Norm_Rob_Stat_v2.gif");
    public ImageIcon LNormRobStat = new ImageIcon("src/Resource/Robot/L_Norm_Rob_Stat_v2.gif");

    public ImageIcon RKakaStat = new ImageIcon("src/Resource/Kakashi/RKakaStat.gif");
    public ImageIcon LKakaStat = new ImageIcon("src/Resource/Kakashi/LKakaStat.gif");

    private ImageIcon Fire = new ImageIcon("src/Resource/Decoration/Fire.gif");

    private ImageIcon FJap = new ImageIcon("src/Resource/Fighting_Background/Japan.gif");
    private ImageIcon FCad = new ImageIcon("src/Resource/Fighting_Background/Canada.gif");
    private ImageIcon FIdk = new ImageIcon("src/Resource/Fighting_Background/IDK.gif");
    private ImageIcon FChi = new ImageIcon("src/Resource/Fighting_Background/China.gif");
    private ImageIcon FFra = new ImageIcon("src/Resource/Fighting_Background/France.gif");
    private ImageIcon FUSA = new ImageIcon("src/Resource/Fighting_Background/USA.gif");

    private JLabel[] allCharacter = new JLabel[3];
    private ImageIcon[] P1allCharacter = new ImageIcon[3];
    private ImageIcon[] P2allCharacter = new ImageIcon[3];

    private ImageIcon[][] allKakaImg = new ImageIcon[4][6];
    private ArrayList<Integer> allKakaData = new ArrayList<>();

    private JLabel lblHighlight = new JLabel();
    private JLabel lblKaka = new JLabel();
    private JLabel lblWiz = new JLabel();
    private JLabel lblRob = new JLabel();
    private JLabel lblP1 = new JLabel();
    private JLabel lblP2 = new JLabel();
    private JLabel lblBigP1 = new JLabel();
    private JLabel lblBigP2 = new JLabel();
    private JLabel lblmapSelect = new JLabel();

    private JTextField P1Name, P2Name;

    private JLabel[] allFire = new JLabel[6];

    private JLabel lblMapArea = new JLabel();

    private ImageIcon[] allMaps = new ImageIcon[]{FCad, FUSA, FIdk, FFra, FChi, FJap};

    private Button btnMain = new Button(2);
    private Button btnStart = new Button(0);

    private int P1At = 0;
    private int P2At = 2;

    private final int CORNER_DIS = 20;
    private final int TEXT_WIDTH = 200;
    private final int TEXT_HEIGHT = 40;

    private final int ICON_SIZE = 100;
    private final int FIRE_SIZE = 32;

    private final int ICON_X = 600;
    private final int ICON_Y =590;

    private final int MAP_OFFSET = 162;
    private final int MAP_WIDTH = 300;
    private final int MAP_HEIGHT = 162;

    private final int SELECT_LIMIT = 0;
    private final int SELECT_KAKASHI = 1;

    private final int NAME_LIMIT = 13;

    private final Font NAME_FONT = new Font("Aerial", Font.BOLD, 30);

    private boolean downloadSuccess;

    private Color transparent = new Color(0, 0, 0, 0);


    public Character_Select() {

        setAllCharacter();
        setAllFires();

        addLabel(background, imgRescaler(CSelect, width, height), INTX, INTY, false);

        P1Name = new JTextField();
        P1Name.setBounds(CORNER_DIS, height - TEXT_HEIGHT - CORNER_DIS, TEXT_WIDTH, TEXT_HEIGHT);
        P1Name.setFont(NAME_FONT);
        P1Name.setForeground(Color.white);
        P1Name.setOpaque(false);
        P1Name.setBackground(transparent);
        P1Name.setCaretColor(Color.WHITE);
        add(P1Name, 0);

        P2Name = new JTextField();
        P2Name.setBounds(width - TEXT_WIDTH - CORNER_DIS, height - TEXT_HEIGHT - CORNER_DIS, TEXT_WIDTH, TEXT_HEIGHT);
        P2Name.setFont(NAME_FONT);
        P2Name.setForeground(Color.white);
        P2Name.setBackground(transparent);
        P2Name.setOpaque(false);
        P2Name.setCaretColor(Color.WHITE);
        add(P2Name, 0);

        addLabel(lblKaka, imgRescaler(KakaFace, ICON_SIZE, ICON_SIZE), ICON_X, ICON_Y);
        addLabel(lblWiz, imgRescaler(WizFace, ICON_SIZE, ICON_SIZE), lblKaka.getX() - lblKaka.getWidth(), ICON_Y);
        addLabel(lblRob, imgRescaler(RobFace, ICON_SIZE, ICON_SIZE), lblKaka.getX() + lblKaka.getWidth(), ICON_Y);
        addLabel(lblBigP1, RNormWizStat, CORNER_DIS, P1Name.getY() - RNormWizStat.getIconHeight() - CORNER_DIS);
        addLabel(lblBigP2, LNormRobStat, width - LNormRobStat.getIconWidth() - CORNER_DIS * 5, P2Name.getY() - LNormRobStat.getIconHeight() - CORNER_DIS);
        addLabel(lblP1, imgRescaler(P1Select, ICON_SIZE, ICON_SIZE), lblWiz.getX(), lblWiz.getY());
        addLabel(lblP2, imgRescaler(P2Select, ICON_SIZE, ICON_SIZE), lblRob.getX(), lblRob.getY());
        addLabel(lblmapSelect, imgRescaler(Together, FIRE_SIZE, FIRE_SIZE), DX, DY);


        selectMap(5);

        lblMapArea.setBounds(lblWiz.getX(), lblWiz.getY() - MAP_OFFSET, MAP_WIDTH, MAP_HEIGHT);
        lblMapArea.setIcon(imgRescaler(FJap, lblMapArea.getWidth(), lblMapArea.getHeight()));
        lblMapArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {


            }
        });

        Main.fightWindow.background.setIcon(imgRescaler(FJap, width, height));
        Main.fightWindow.background.setBounds(INTX, INTY, width, height);

        lblHighlight.setIcon(imgHighlight);
        lblHighlight.setBounds(DX, DY, imgHighlight.getIconWidth(), imgHighlight.getIconHeight());

        btnMain.setLocation(CORNER_DIS, CORNER_DIS);
        btnMain.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                setVisible(false);
                Main.introWindow.setVisible(true);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                centerSetter(lblHighlight, btnMain);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                remove(lblHighlight);

            }
        });

        btnStart.setLocation(width - btnMain.getWidth() - CORNER_DIS, CORNER_DIS);
        btnStart.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                startGame();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                centerSetter(lblHighlight, btnStart);

            }

            @Override
            public void mouseExited(MouseEvent e) {

                remove(lblHighlight);

            }
        });

        background.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                requestFocus();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        allFire[0].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(0);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FCad);

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        allFire[1].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FUSA);

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        allFire[2].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(2);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FIdk);

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        allFire[3].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(3);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FFra);

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        allFire[4].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(4);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FChi);
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        allFire[5].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectMap(5);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                previewmap(FJap);

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                setTogether();

                if (e.getKeyCode() == KeyEvent.VK_D && P1At < allCharacter.length - 1) {

                    setAt(lblP1, ++P1At);
                    setBig(lblBigP1, P1allCharacter, P1At);

                } else if (e.getKeyCode() == KeyEvent.VK_A && P1At > SELECT_LIMIT) {

                    setAt(lblP1, --P1At);
                    setBig(lblBigP1, P1allCharacter, P1At);

                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && P2At < allCharacter.length - 1) {

                    setAt(lblP2, ++P2At);
                    setBig(lblBigP2, P2allCharacter, P2At);

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && P2At > SELECT_LIMIT) {

                    setAt(lblP2, --P2At);
                    setBig(lblBigP2, P2allCharacter, P2At);

                }

                setTogether();


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        add(lblMapArea, 0);
        add(lblHighlight, 0);
        add(btnMain, 0);
        add(btnStart, 0);


    }

    private void setAllFires() {

        for (int i = 0; i < allFire.length; i++) {

            allFire[i] = new JLabel();
            allFire[i].setSize(FIRE_SIZE, FIRE_SIZE);
            allFire[i].setIcon(imgRescaler(Fire, allFire[i].getWidth(), allFire[i].getHeight()));
            add(allFire[i], 0);

        }

        allFire[0].setLocation(150, 150);
        allFire[1].setLocation(130, 250);
        allFire[2].setLocation(380, 250);
        allFire[3].setLocation(580, 200);
        allFire[4].setLocation(990, 280);
        allFire[5].setLocation(1120, 250);

    }

    private void setAllCharacter() {

        allCharacter[0] = lblWiz;
        allCharacter[1] = lblKaka;
        allCharacter[2] = lblRob;

        P1allCharacter[0] = RNormWizStat;
        P1allCharacter[1] = RKakaStat;
        P1allCharacter[2] = RNormRobStat;

        P2allCharacter[0] = LNormWizStat;
        P2allCharacter[1] = LKakaStat;
        P2allCharacter[2] = LNormRobStat;

    }

    private void addLabel(JLabel a, ImageIcon b, int x, int y) {


        a.setIcon(b);
        a.setBounds(x, y, b.getIconWidth(), b.getIconHeight());
        add(a, 0);


    }

    private void addLabel(JLabel a, ImageIcon b, int x, int y, boolean f) {


        a.setIcon(b);
        a.setBounds(x, y, b.getIconWidth(), b.getIconHeight());
        add(a);


    }

    private void setAt(JLabel a, int b) {

        a.setLocation(allCharacter[b].getX(), allCharacter[b].getY());

    }

    private void setBig(JLabel a, ImageIcon[] b, int c) {

        if (b[c] != null) {

            a.setSize(b[c].getIconWidth(), b[c].getIconHeight());

        }
        a.setIcon(b[c]);

    }

    private void setTogether() {

        if (lblP1.getX() == lblP2.getX()) {

            lblP2.setIcon(imgRescaler(Together, ICON_SIZE, ICON_SIZE));

        } else {

            lblP2.setIcon(imgRescaler(P2Select, ICON_SIZE, ICON_SIZE));
        }

    }

    private void remove(JLabel a) {

        a.setLocation(DX, DY);

    }

    private void startGame() {

        if (startGameCondition()) {

            if (!firstDownload) {

                Main.fightWindow.setPLayer(P1At, P2At, P1Name.getText(), P2Name.getText());

            }

            setVisible(false);
            Main.fightWindow.setVisible(true);
            Main.fightWindow.countDownTimer.start();
            Player.setGameOver(false);

        }

    }

    private boolean startGameCondition() {

        if (P1Name.getText().equals("") || P2Name.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "Please choose a name for both fighters");
            return false;


        } else if (P1Name.getText().toCharArray().length > NAME_LIMIT || P2Name.getText().toCharArray().length > NAME_LIMIT) {

            JOptionPane.showMessageDialog(null, "Name has to be shorter than 14 letters");
            return false;


        } else if (isNumber(P1Name.getText()) || isNumber(P2Name.getText())) {

            JOptionPane.showMessageDialog(null, "Name can't be all numbers");
            return false;

        } else if (P1Name.getText().toLowerCase().equals(P2Name.getText().toLowerCase())) {

            JOptionPane.showMessageDialog(null, "Players can't have same name");
            return false;

        } else if (firstDownload) {

            Main.fightWindow.setPLayer(P1At, P2At, P1Name.getText(), P2Name.getText(), allKakaImg, allKakaData);
            return true;

        } else if (P1At == SELECT_KAKASHI || P2At == SELECT_KAKASHI) {

            if (!Main.rw.getDLC()) {

                String code = JOptionPane.showInputDialog(null, "Kakashi is a DLC \nWhisper into Shawn's ears: \"SHAWN SO SMART O GOD\" and he will tell you the DLC code\nDeen you are not allowed");

                if (code == null) {

                    return false;

                } else if (code.equals("SHAWN SO SMART O GOD")) {

                    JOptionPane.showMessageDialog(null, "Start Download");

                    try {

                        URL RWalk = new URL("https://i.imgur.com/PkpuyZd.gif");
                        URL RJump = new URL("https://i.imgur.com/hnzWgcC.gif");
                        URL RBlock = new URL("https://i.imgur.com/Qwou1ue.gif");
                        URL RPunch = new URL("https://i.imgur.com/UjlyCbg.gif");
                        URL RKick = new URL("https://i.imgur.com/otqNPpl.gif");
                        URL RTele = new URL("https://i.imgur.com/RaMeagp.gif");
                        URL RSuper = new URL("https://i.imgur.com/6yXOe6s.gif");
                        URL RSummon = new URL("https://i.imgur.com/dKgzUn1.gif");

                        URL LWalk = new URL("https://i.imgur.com/xU6FVnk.gif");
                        URL LJump = new URL("https://i.imgur.com/RbDXHiU.gif");
                        URL LBlock = new URL("https://i.imgur.com/zHreuI0.gif");
                        URL LPunch = new URL("https://i.imgur.com/3U0jsuj.gif");
                        URL LKick = new URL("https://i.imgur.com/musMFut.gif");
                        URL LTele = new URL("https://i.imgur.com/D8ZlWW5.gif");
                        URL LSuper = new URL("https://i.imgur.com/m4Qs2VM.gif");
                        URL LSummon = new URL("https://i.imgur.com/HPfNxBv.gif");

                        URL KakaData = new URL("https://pastebin.com/raw/WWRjgPyq");

                        JOptionPane.showMessageDialog(null, "found the data...");

                        Scanner read = new Scanner(new InputStreamReader(KakaData.openStream()));

                        while (read.hasNext()) {

                            allKakaData.add(Integer.parseInt(read.nextLine()));

                        }

                        Main.rw.selectionSort(allKakaData);

                        //setting pics for the first time download
                        allKakaImg[0][0] = RKakaStat;
                        allKakaImg[0][1] = new ImageIcon(RJump);
                        allKakaImg[0][3] = new ImageIcon(RSuper);
                        allKakaImg[0][4] = new ImageIcon(RSummon);
                        allKakaImg[1][0] = new ImageIcon(LWalk);
                        allKakaImg[1][1] = new ImageIcon(RBlock);
                        allKakaImg[1][2] = new ImageIcon(RWalk);
                        allKakaImg[1][3] = new ImageIcon(RPunch);
                        allKakaImg[1][4] = new ImageIcon(RKick);
                        allKakaImg[1][5] = new ImageIcon(RTele);

                        JOptionPane.showMessageDialog(null, "so very close my friend");

                        allKakaImg[2][0] = LKakaStat;
                        allKakaImg[2][1] = new ImageIcon(LJump);
                        allKakaImg[2][3] = new ImageIcon(LSuper);
                        allKakaImg[2][4] = new ImageIcon(LSummon);
                        allKakaImg[3][0] = new ImageIcon(LWalk);
                        allKakaImg[3][1] = new ImageIcon(LBlock);
                        allKakaImg[3][2] = new ImageIcon(RWalk);
                        allKakaImg[3][3] = new ImageIcon(LPunch);
                        allKakaImg[3][4] = new ImageIcon(LKick);
                        allKakaImg[3][5] = new ImageIcon(LTele);

                        JOptionPane.showMessageDialog(null, "I can almost touch it");

                        //storing the images so you don't have to internet to play the DLC the second time
                        Files.copy(RWalk.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaWalk.gif"));
                        Files.copy(RJump.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaJump.gif"));
                        Files.copy(RBlock.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaBlock.gif"));
                        Files.copy(RPunch.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaPunch.gif"));
                        Files.copy(RKick.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaKick.gif"));
                        Files.copy(RTele.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaTeleport.gif"));
                        Files.copy(RSuper.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaSuper.gif"));
                        Files.copy(RSummon.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/RKakaSummon.gif"));

                        Files.copy(LWalk.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaWalk.gif"));
                        Files.copy(LJump.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaJump.gif"));
                        Files.copy(LBlock.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaBlock.gif"));
                        Files.copy(LPunch.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaPunch.gif"));
                        Files.copy(LKick.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaKick.gif"));
                        Files.copy(LTele.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaTeleport.gif"));
                        Files.copy(LSuper.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaSuper.gif"));
                        Files.copy(LSummon.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/Kakashi/DLC/LKakaSummon.gif"));

                        Files.copy(KakaData.openStream(), Paths.get(System.getProperty("user.dir") + "/src/Resource/All_Data/KakaData.txt"));

                        firstDownload = true;
                        Main.fightWindow.setPLayer(P1At, P2At, P1Name.getText(), P2Name.getText(), allKakaImg, allKakaData);

                        JOptionPane.showMessageDialog(null, "and the DLC is downloaded. Enjoy being overpowered :)");
                        downloadSuccess = true;

                        return true;

                    } catch (UnknownHostException e) {

                        JOptionPane.showMessageDialog(null, "no internet");

                    } catch (NoSuchFileException e) {

                        JOptionPane.showMessageDialog(null, "storing path is wrong");

                    } catch (MalformedURLException e) {

                        JOptionPane.showMessageDialog(null, "wrong web address");

                    } catch (FileAlreadyExistsException e) {

                        JOptionPane.showMessageDialog(null, "file already exist");

                    } catch (IOException e) {

                        JOptionPane.showMessageDialog(null, "something is wrong");

                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Wrong code");

                }


            } else {

                return true;

            }

            return false;

        }

        return true;

    }

    private void selectMap(int i) {

        lblmapSelect.setLocation(allFire[i].getX(), allFire[i].getY());
        Main.fightWindow.background.setIcon(imgRescaler(allMaps[i], width, height));
        Main.fightWindow.background.setBounds(INTX, INTY, width, height);

    }

    private void previewmap(ImageIcon a) {

        lblMapArea.setIcon(imgRescaler(a, lblMapArea.getWidth(), lblMapArea.getHeight()));


    }

    private boolean isNumber(String s) {

        try {

            Integer.parseInt(s);
            return true;

        } catch (NumberFormatException ex) {

            return false;
        }

    }

    boolean getdownloadSucces() {

        return downloadSuccess;

    }


}
