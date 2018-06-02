import java.io.*;
import java.util.*;


public class Read_Write {

    String highFileName = "High Score.txt";
    String DLCFileName = "DLC.txt";
    String KakaFileName = "KakaData.txt";
    boolean DLC = false;
    int currentHighScore = 0;
    Stack stackInfo = new Stack();

    void readFile() {

        try {

            stackInfo.clear();
            Scanner readHigh = new Scanner(new File(System.getProperty("user.dir") + "/src/" + highFileName));
            Scanner readDLC = new Scanner(new File(System.getProperty("user.dir") + "/src/" + DLCFileName));
            System.out.print("Files found...");

            if (!readDLC.hasNext()) {

                writeMessage("No DLC Yet");

            } else {

                if (readDLC.nextLine().equals("SHAWN SO SMART O GOD")) {

                    DLC = true;

                }

            }

            while (readHigh.hasNext()) {

                String name = readHigh.nextLine();
                int score = Integer.parseInt(readHigh.nextLine());

                stackInfo.push(name, score);

            }


            System.out.println("File read successfully!!");

            readHigh.close();

            if (!stackInfo.isEmpty()) {

                currentHighScore = stackInfo.top.score;

            }

            System.out.println(currentHighScore);


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }


    }

    ArrayList<Integer> readKakaData() {

        ArrayList<Integer> tempArray = new ArrayList<>();
        try {

            Scanner readData = new Scanner(new File(System.getProperty("user.dir") + "/src/" + KakaFileName));
            System.out.print("Files found...");

            while (readData.hasNext()) {

                tempArray.add(Integer.parseInt(readData.nextLine()));

            }

            selectionSort(tempArray);


            readData.close();
            System.out.println("File read successfully!!");



        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }

        return tempArray;

    }

    void writeMessage(String s) {

        try {

            PrintWriter outputStream = new PrintWriter(System.getProperty("user.dir") + "/src/" + DLCFileName);
            System.out.print("File found during writing...");


            outputStream.println(s);
            outputStream.close();

            System.out.println("File written successfully!!");


        } catch (FileNotFoundException e) {

            System.out.println("Could not find file!!");

        }

    }

    void writeMessage(Node n) {

        try {

            PrintWriter outputStream = new PrintWriter(new FileWriter(System.getProperty("user.dir") + "/src/" + highFileName, true));
            System.out.print("File found during writing...");


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

    boolean newHighScore(String name, int s) {

        if (!stackInfo.isEmpty()) {

            currentHighScore = stackInfo.top.score;

        } else {

            currentHighScore = 0;

        }

        if (s > currentHighScore) {

            stackInfo.push(name, s);
            currentHighScore = s;
            writeMessage(stackInfo.top);
            return true;

        }

        return false;


    }

    void selectionSort(ArrayList<Integer> arr) {

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

    void switchNums(int i, int index, ArrayList<Integer> a) {

        int temp = a.get(i);
        a.set(i,a.get(index));
        a.set(index, temp);

    }

}


