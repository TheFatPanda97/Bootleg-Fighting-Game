
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Read_Write rw = new Read_Write();
    static FightClub fightWindow = new FightClub();
    static CharacterSelect CSelectWindow = new CharacterSelect();
    static HighScore ScoreWindow = new HighScore();
    static Intro introWindow = new Intro();


    public static void main(String[] args) {

//
//        JLabel lblMapArea = new JLabel();
//        lblMapArea.setBounds(0, 0, 332, 162);

//        lblMapArea.setBackground(Color.green);
//        lblMapArea.setOpaque(true);


       // CSelectWindow.setVisible(true);
       // loadWindow.setVisible(true);
        introWindow.setVisible(true);
        //introWindow.add(lblMapArea,0);
       // fightWindow.setVisible(true);
       //ScoreWindow.setVisible(true);


    }
}
