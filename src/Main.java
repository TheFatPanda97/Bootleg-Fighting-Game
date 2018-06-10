
/**
 * Shawn Hu
 * Finished Jun 10
 * Fight Club
 * */
public class Main {

    public static Read_Write rw = new Read_Write();//File IO
    public static Fight_Club fightWindow = new Fight_Club();//Actual fighting window
    public static Character_Select CSelectWindow = new Character_Select();//character selection window
    public static High_Score ScoreWindow = new High_Score();//high score window
    public static Intro introWindow = new Intro();//main menu winodw

    public static void main(String[] args) {

        //display the main menu window
        introWindow.setVisible(true);

    }
}
