import java.io.*;
import java.util.*;

//file IO class
public class Read_Write {

    private String highFileName = "High Score.txt";
    private String DLCFileName = "DLC.txt";
    private String KakaFileName = "KakaData.txt";
    private String path = "/src/Resource/All_Data/";
    private boolean DLC = false;
    private int currentHighScore = 0;
    private Stack stackInfo = new Stack();

    //reads high score file
    public void readFile() {

        try {

            stackInfo.clear();//clear current stack that stores the high score
            Scanner readHigh = new Scanner(new File(System.getProperty("user.dir") + path + highFileName));//high score file
            Scanner readDLC = new Scanner(new File(System.getProperty("user.dir") + path + DLCFileName));//DLC password file
            System.out.print("Files found...");

            //if the DLC file is empty
            if (!readDLC.hasNext()) {

                writeMessage("No DLC Yet");

            } else {

                //if DLC has correct code
                if (readDLC.nextLine().equals("SHAWN SO SMART O GOD")) {

                    DLC = true;

                }

            }

            //while there's a nextline in the high score window
            while (readHigh.hasNext()) {

                //the name and score are stored in a stack
                String name = readHigh.nextLine();
                int score = Integer.parseInt(readHigh.nextLine());

                stackInfo.push(name, score);

            }


            System.out.println("File read successfully!!");

            readHigh.close();

            //if the stack isn't empty, ten record the current high score from the stack
            if (!stackInfo.isEmpty()) {

                currentHighScore = stackInfo.top.score;

            }

            System.out.println(currentHighScore);


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }


    }

    //reads and returns kakasshi's stop timer data in a arraylist
    public ArrayList<Integer> readDLCData() {

        ArrayList<Integer> tempArray = new ArrayList<>();

        try {

            Scanner readData = new Scanner(new File(System.getProperty("user.dir") + path + KakaFileName));//the DLC data
            System.out.print("Files found...");


            while (readData.hasNext()) {

                //the data is added to th arraylist
                tempArray.add(Integer.parseInt(readData.nextLine()));

            }

            //the data is not in order so sort them
            selectionSort(tempArray);


            readData.close();
            System.out.println("File read successfully!!");


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }

        return tempArray;

    }

    //writes a line in the DLC password txt
    public void writeMessage(String s) {

        try {

            PrintWriter outputStream = new PrintWriter(System.getProperty("user.dir") + path + DLCFileName);//DLC password doc
            System.out.print("File found during writing...");

            outputStream.println(s);//overrides first line with string 's'
            outputStream.close();

            System.out.println("File written successfully!!");


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }

    }

    //writes high score to high score txt
    private void writeMessage(Node n) {

        try {

            //append allows for adding new lines, instead of overwriting
            PrintWriter outputStream = new PrintWriter(new FileWriter(System.getProperty("user.dir") + path + highFileName, true));
            System.out.print("File found during writing...");

            //prints the name then there high score from node
            outputStream.println(n.name);
            outputStream.println(n.score);
            outputStream.close();

            System.out.println("File written successfully!!");


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        } catch (IOException e) {

            System.out.println("something is wrong");

        }


    }

    //returns if new high score is created
    public boolean newHighScore(String name, int s) {

        //if the stack isn't empty, then the op stack is the high score
        if (!stackInfo.isEmpty()) {

            currentHighScore = stackInfo.top.score;

        } else {

            currentHighScore = 0;

        }

        //if the new score is larger than the high score
        if (s > currentHighScore) {

            stackInfo.push(name, s);//adds it to stack
            currentHighScore = s;//records new highs score
            writeMessage(stackInfo.top);//write the new high score to the high score txt
            return true;

        }

        return false;


    }

    public void selectionSort(ArrayList<Integer> arr) {

        for (int i = 0; i < arr.size() - 1; i++) {

            int index = i;
            for (int j = i + 1; j < arr.size(); j++) {

                if (arr.get(j) < arr.get(index)) {
                    index = j;
                }

            }

            switchNums(i, index, arr);
        }

    }

    //help method for selection sort
    private void switchNums(int i, int index, ArrayList<Integer> a) {

        int temp = a.get(i);
        a.set(i, a.get(index));
        a.set(index, temp);

    }

    public boolean getDLC() {

        return DLC;

    }

    public void setDLC(boolean d) {

        DLC = d;

    }

    public Stack getStackInfo() {

        return stackInfo;

    }

}


