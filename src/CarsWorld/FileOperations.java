import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileOperations {
    AlertBoxes ab = new AlertBoxes();
    boolean isTrue = false;

    public boolean LoginFileRead(String user, String pass, String FileName) {
        try {
            Scanner sc = new Scanner(new File(FileName));

            while (sc.hasNext()) {
                String tempUser = user;
                String tempPass = pass;
                String checkUser = sc.next();
                String CheckPass = sc.next();

                if (tempUser.equals(checkUser) && tempPass.equals(CheckPass)) {
                    isTrue = true;
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 101: An error occured!");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops! Error has occured.");
            errorAlert.showAndWait();
        }
        return isTrue;
    }

    public void FileWrite(String[] carBrandStr, String[] carModelStr, String[] carCategoryStr, String[] carYearStr, String[] carPriceStr, String FileName, boolean append) {
        try {
            FileWriter fw = new FileWriter(FileName, append);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < carBrandStr.length; i++) {
                bw.write(carBrandStr[i] + "," + carModelStr[i] + "," + carCategoryStr[i] + "," + carYearStr[i] + "," + carPriceStr[i] + "\n");
            }

            bw.close();
        } catch (IOException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 102: An error occured!2");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops! Error has occured.");
            errorAlert.showAndWait();
        }
    }

    public void FeedBackFileWrite(String[] feedback, String FileName, boolean append) {
        try {
            FileWriter fw = new FileWriter(FileName, append);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String s : feedback) {
                bw.write(s + "\n");
            }

            bw.close();
        } catch (IOException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 103: An error occurred!3");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops! Error has occurred.");
            errorAlert.showAndWait();
        }
    }

    public void DeleteDuplicateData(String FileName) {
        try {
            String input = null;
            File tempFile = new File(FileName);
            File inputFile = new File("carddetails.txt");
            Scanner sc = new Scanner(tempFile);
            FileWriter fw = new FileWriter(inputFile, false);
            Set<String> set = new HashSet<String>();

            while (sc.hasNextLine()) {
                input = sc.nextLine();
                if (set.add(input)) {
                    fw.append(input).append("\n");
                }
            }

            fw.flush();
            sc.close();
            fw.close();
        } catch (Exception e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 104: An Error Occurred");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops! An error occurred. Sorry for the inconvenience.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }

    }

    public void SortFileData(String FileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            ArrayList<String> str = new ArrayList<>();
            String line = " ";

            while ((line = reader.readLine()) != null) {
                str.add(line);
            }

            reader.close();
            Collections.sort(str);
            FileWriter writer = new FileWriter("tempcardetails.txt");

            for (String s : str) {
                writer.write(s);
                writer.write("\r\n");
            }

            writer.close();
            DeleteDuplicateData("tempcardetails.txt");
        } catch (Exception e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 105: An Error Occurred");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops !An error occurred.Sorry for the inconvenience.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    public void ArrayToQueue(Queue<String> carBrandQueue, Queue<String> carModelQueue, Queue<String> carCategoryQueue, Queue<String> carPriceQueue, Queue<String> carYearQueue, String FileName) {
        try {
            ArrayList<String> brand = new ArrayList<>();
            ArrayList<String> model = new ArrayList<>();
            ArrayList<String> category = new ArrayList<>();
            ArrayList<String> year = new ArrayList<>();
            ArrayList<String> price = new ArrayList<>();
            List<String> tempData = Files.readAllLines(Path.of(FileName));

            for (String line : tempData) {
                String[] fields = line.split(",");
                brand.add(fields[0]);
                model.add(fields[1]);
                category.add(fields[2]);
                year.add(fields[3]);
                price.add(fields[4]);
            }

            carBrandQueue.addAll(brand);
            carModelQueue.addAll(model);
            carCategoryQueue.addAll(category);
            carYearQueue.addAll(year);
            carPriceQueue.addAll(price);

        } catch (Exception e) {
            ab.ErrorAlert();
        }

    }

    public void FileUpdate(ArrayList<String> carBrandAL, ArrayList<String> carModelAL, ArrayList<String> carCategoryAL, ArrayList<String> carYearAL, ArrayList<String> carPriceAL, String FileName, boolean append) {
        try {
            FileWriter fw = new FileWriter(FileName, append);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < carBrandAL.size(); i++) {
                bw.write(carBrandAL.get(i) + ", " + carModelAL.get(i) + "," + carYearAL.get(i) + "," + carPriceAL.get(i) + "\n");
            }

            bw.close();
        } catch (Exception e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error 106: An Error Occurred");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Oops! An error occurred. Sorry for the inconvenience.");
            errorAlert.showAndWait();
        }
    }
}
 	
