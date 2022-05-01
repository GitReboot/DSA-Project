import java.io.*;
import java.util.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CarsWorld extends Application {

    Scene mainScene, adminLoginScene, adminDashboardScene, addDataScene, editPriceScene, deleteDataScene, userDashboardScene, searchCarByBrandScene, searchCarByModelScene, viewAllCarsScene;

    Queue<String> carBrandQueue = new LinkedList<>();
    Queue<String> carCategoryQueue = new LinkedList<>();
    Queue<String> carModelQueue = new LinkedList<>();
    Queue<String> carPriceQueue = new LinkedList<>();
    Queue<String> carYearQueue = new LinkedList<>();
    Stack<String> feedbackStack = new Stack<>();

    private final ObservableList<Record> dataList = FXCollections.observableArrayList();
    private final String carDetailsFileName = "src\\main\\resources\\cardetails.txt";
    private final String feedbackFileName = "src\\main\\resources\\feedback.txt";

    FileOperations fop = new FileOperations();
    BubbleSort bs = new BubbleSort();
    AlertBoxes ab = new AlertBoxes();

    @Override
    public void start(Stage primaryStage) {
        try {
            fop.ArrayToQueue(carBrandQueue, carModelQueue, carCategoryQueue, carPriceQueue, carYearQueue, carDetailsFileName);

            AnchorPane mainScenePane = new AnchorPane();

            Image image = new Image(new FileInputStream("src\\main\\resources\\showroom.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(600);

            Label titleLbl = new Label("CarsWorld");
            titleLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            titleLbl.setTranslateX(200);
            titleLbl.setTranslateY(300);

            Button adminLoginBtn = new Button("Admin Login");
            adminLoginBtn.setTranslateX(50);
            adminLoginBtn.setTranslateY(380);
            adminLoginBtn.setPrefSize(200, 60);
            adminLoginBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            adminLoginBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(adminLoginScene);
                }
            });

            Button userDashboardBtn = new Button("User Dashboard");
            userDashboardBtn.setTranslateX(350);
            userDashboardBtn.setTranslateY(380);
            userDashboardBtn.setPrefSize(200, 60);
            userDashboardBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            userDashboardBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(userDashboardScene);
                }
            });

            Button mainExitBtn = new Button("Exit");
            mainExitBtn.setTranslateX(200);
            mainExitBtn.setTranslateY(478);
            mainExitBtn.setPrefSize(200, 60);
            mainExitBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            mainExitBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    System.exit(0);
                }
            });

            Label footer1 = new Label("Developed By:");
            footer1.setTranslateX(10);
            footer1.setTranslateY(550);
            footer1.setFont(Font.font(null, FontWeight.BOLD, 12));

            Label footer2 = new Label("V N Suchir Vangaveeti - 2020A7PS0018U, Saatvik Ayapilla - 2020A7PS0002U");
            footer2.setTranslateX(95);
            footer2.setTranslateY(550);

            Label footer3 = new Label("Alok John Joseph - 2020A7PS0021U, Sairaj Karnik Sailesh - 2020A7PS0035U");
            footer3.setTranslateX(95);
            footer3.setTranslateY(570);

            mainScenePane.getChildren().addAll(imageView, titleLbl, adminLoginBtn, userDashboardBtn, footer1, footer2, footer3, mainExitBtn);
            mainScene = new Scene(mainScenePane, 600, 600);

            AnchorPane adminLoginScenePane = new AnchorPane();

            Label adminLoginLbl = new Label("ADMIN LOGIN");
            adminLoginLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            adminLoginLbl.setTranslateX(65);

            Label adminLbl = new Label("Enter Admin ID:");
            adminLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            adminLbl.setTranslateX(100);
            adminLbl.setTranslateY(70);

            TextField adminTF = new TextField();
            adminTF.setTranslateX(100);
            adminTF.setTranslateY(100);
            adminTF.setPrefSize(200, 20);

            Label passLbl = new Label("Enter Password:");
            passLbl.setTranslateX(100);
            passLbl.setTranslateY(150);
            passLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));

            PasswordField passPF = new PasswordField();
            passPF.setTranslateX(100);
            passPF.setTranslateY(180);
            passPF.setPrefSize(200, 20);

            Button loginBtn = new Button("Login");
            loginBtn.setTranslateX(100);
            loginBtn.setTranslateY(240);
            loginBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 20));
            loginBtn.setPrefSize(200, 60);
            loginBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String IDKey = adminTF.getText();
                    String Passkey = passPF.getText();
                    try {
                        String file = "src\\main\\resources\\logindatabase.txt";
                        boolean key = fop.LoginFileRead(IDKey, Passkey, file);
                        if (key) {
                            adminTF.clear();
                            passPF.clear();
                            primaryStage.setScene(adminDashboardScene);
                        } else if (adminTF.getText().isEmpty() && passPF.getText().isEmpty()) {
                            ab.EmptyIDPassAlert();
                        } else {
                            ab.WrongIDPassAlert();
                        }
                    } catch (Exception e) {
                        ab.ErrorAlert();
                    }
                }
            });

            Button goBackBtn = new Button("Back");
            goBackBtn.setTranslateX(15);
            goBackBtn.setTranslateY(360);
            goBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(mainScene);
                }
            });

            adminLoginScenePane.getChildren().addAll(adminLoginLbl, adminLbl, adminTF, passLbl, passPF, loginBtn, goBackBtn);
            adminLoginScene = new Scene(adminLoginScenePane, 400, 400);

            AnchorPane adminDashboardScenePane = new AnchorPane();

            Label adminDashboardLbl = new Label("ADMIN DASHBOARD");
            adminDashboardLbl.setFont(Font.font(null, FontWeight.BOLD, 36));
            adminDashboardLbl.setTranslateX(20);

            Button addDataBtn = new Button("Add Data");
            addDataBtn.setTranslateX(100);
            addDataBtn.setTranslateY(70);
            addDataBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            addDataBtn.setPrefSize(200, 60);
            addDataBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(addDataScene);
                }
            });

            Button editDataBtn = new Button("Edit Price");
            editDataBtn.setTranslateX(100);
            editDataBtn.setTranslateY(150);
            editDataBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            editDataBtn.setPrefSize(200, 60);
            editDataBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(editPriceScene);
                }
            });

            Button deleteDataBtn = new Button("Delete Data");
            deleteDataBtn.setTranslateX(100);
            deleteDataBtn.setTranslateY(230);
            deleteDataBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            deleteDataBtn.setPrefSize(200, 60);
            deleteDataBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(deleteDataScene);
                }
            });

            Button adminLogoutBtn = new Button("Log Out");
            adminLogoutBtn.setTranslateX(100);
            adminLogoutBtn.setTranslateY(310);
            adminLogoutBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            adminLogoutBtn.setPrefSize(200, 60);
            adminLogoutBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(mainScene);
                }
            });

            adminDashboardScenePane.getChildren().addAll(adminDashboardLbl, addDataBtn, editDataBtn, deleteDataBtn, adminLogoutBtn);
            adminDashboardScene = new Scene(adminDashboardScenePane, 400, 400);

            AnchorPane addDataScenePane = new AnchorPane();

            Label addDataHeadLbl = new Label("ADD DATA");
            addDataHeadLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            addDataHeadLbl.setTranslateX(300);

            Label carBrandLbl = new Label("Enter Car Brand:");
            carBrandLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            carBrandLbl.setTranslateX(50);
            carBrandLbl.setTranslateY(60);

            TextField carBrandTF = new TextField();
            carBrandTF.setTranslateX(50);
            carBrandTF.setTranslateY(90);
            carBrandTF.setPrefSize(200, 20);

            Label carModelLbl = new Label("Enter Car Model:");
            carModelLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            carModelLbl.setTranslateX(300);
            carModelLbl.setTranslateY(60);

            TextField carModelTF = new TextField();
            carModelTF.setTranslateX(300);
            carModelTF.setTranslateY(90);
            carModelTF.setPrefSize(200, 20);

            Label carYearLbl = new Label("Enter Car Year:");
            carYearLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            carYearLbl.setTranslateX(550);
            carYearLbl.setTranslateY(60);

            TextField carYearTF = new TextField();
            carYearTF.setTranslateX(550);
            carYearTF.setTranslateY(90);
            carYearTF.setPrefSize(200, 20);

            Label carPriceLbl = new Label("Enter Car Price:");
            carPriceLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            carPriceLbl.setTranslateX(50);
            carPriceLbl.setTranslateY(130);

            TextField carPriceTF = new TextField();
            carPriceTF.setTranslateX(50);
            carPriceTF.setTranslateY(160);
            carPriceLbl.setPrefSize(200, 20);

            Label carCategoryLbl = new Label("Select Car Category:");
            carCategoryLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            carCategoryLbl.setTranslateX(300);
            carCategoryLbl.setTranslateY(130);

            ComboBox<String> carCategoryCB = new ComboBox<>();
            carCategoryCB.setTranslateX(300);
            carCategoryCB.setTranslateY(160);
            carCategoryCB.setPrefSize(200, 20);

            ObservableList<String> carCategoryChoiceList = carCategoryCB.getItems();
            carCategoryChoiceList.add("Sedan");
            carCategoryChoiceList.add("Hatchback");
            carCategoryChoiceList.add("Coupe");
            carCategoryChoiceList.add("SUV");
            carCategoryChoiceList.add("Sports Car");
            carCategoryChoiceList.add("Convertible");
            carCategoryChoiceList.add("Minivan");
            carCategoryChoiceList.add("Pick-up Truck");

            Label addDataVerifyLbl = new Label();
            addDataVerifyLbl.setVisible(false);

            Button addDataToDBBtn = new Button("Add Data");
            addDataToDBBtn.setTranslateX(550);
            addDataToDBBtn.setTranslateY(130);
            addDataToDBBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            addDataToDBBtn.setPrefSize(200, 60);
            addDataToDBBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String carBrandKey = carBrandTF.getText();
                    String carModelKey = carModelTF.getText();
                    String carYearKey = carYearTF.getText();
                    String carPriceKey = carPriceTF.getText();
                    String carCategoryKey = carCategoryCB.getValue();

                    if (carBrandKey.equals("") && carModelKey.equals("") && carYearKey.equals("") && carPriceKey.equals("") && carCategoryKey.equals("")) {
                        addDataVerifyLbl.setText("Empty Data Fields! Kindly fill them.");
                        addDataVerifyLbl.setTranslateX(300);
                        addDataVerifyLbl.setTranslateY(220);
                        addDataVerifyLbl.setTextFill(Color.RED);
                        addDataVerifyLbl.setVisible(true);
                    } else {
                        carBrandQueue.add(carBrandKey);
                        carModelQueue.add(carModelKey);
                        carYearQueue.add(carYearKey);
                        carPriceQueue.add(carPriceKey);
                        carCategoryQueue.add(carCategoryKey);

                        ArrayList<String> carBrandAL = new ArrayList<>(carBrandQueue);
                        String[] carBrandStr = new String[carBrandAL.size()];
                        for (int j = 0; j < carBrandQueue.size(); j++) {
                            carBrandStr[j] = carBrandAL.get(j);
                        }

                        ArrayList<String> carModelAL = new ArrayList<>(carModelQueue);
                        String[] carModelStr = new String[carModelAL.size()];
                        for (int j = 0; j < carModelQueue.size(); j++) {
                            carModelStr[j] = carModelAL.get(j);
                        }

                        ArrayList<String> carYearAL = new ArrayList<>(carYearQueue);
                        String[] carYearStr = new String[carYearAL.size()];
                        for (int j = 0; j < carYearQueue.size(); j++) {
                            carYearStr[j] = carYearAL.get(j);
                        }

                        ArrayList<String> carPriceAL = new ArrayList<>(carPriceQueue);
                        String[] carPriceStr = new String[carPriceAL.size()];
                        for (int j = 0; j < carPriceQueue.size(); j++) {
                            carPriceStr[j] = carPriceAL.get(j);
                        }

                        ArrayList<String> carCategoryAL = new ArrayList<>(carCategoryQueue);
                        String[] carCategoryStr = new String[carCategoryAL.size()];
                        for (int j = 0; j < carCategoryQueue.size(); j++) {
                            carCategoryStr[j] = carCategoryAL.get(j);
                        }

                        bs.Sort(carBrandStr, carModelStr, carYearStr, carPriceStr, carCategoryStr, carBrandQueue.size());
                        fop.FileWrite(carBrandStr, carModelStr, carCategoryStr, carYearStr, carPriceStr, carDetailsFileName, true);
                        fop.SortFileData(carDetailsFileName);
                        addDataVerifyLbl.setText("Data Successfully Added In Database!");
                        addDataVerifyLbl.setTranslateX(300);
                        addDataVerifyLbl.setTranslateY(220);
                        addDataVerifyLbl.setTextFill(Color.GREEN);
                        addDataVerifyLbl.setVisible(true);

                        carBrandTF.clear();
                        carModelTF.clear();
                        carYearTF.clear();
                        carPriceTF.clear();
                        carCategoryCB.getSelectionModel().clearSelection();
                        carCategoryCB.setValue(null);
                    }
                }
            });

            Button addDataBackBtn = new Button("Back");
            addDataBackBtn.setTranslateX(15);
            addDataBackBtn.setTranslateY(15);
            addDataBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(adminDashboardScene);
                }
            });

            addDataScenePane.getChildren().addAll(addDataHeadLbl, carBrandLbl, carBrandTF, carCategoryLbl, carCategoryCB, carModelTF, carModelLbl, carPriceLbl, carPriceTF, carYearLbl, carYearTF, addDataVerifyLbl, addDataToDBBtn, addDataBackBtn);
            addDataScene = new Scene(addDataScenePane, 800, 300);

            AnchorPane editPriceScenePane = new AnchorPane();

            Label editPriceHeadLbl = new Label("EDIT PRICE");
            editPriceHeadLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            editPriceHeadLbl.setTranslateX(300);

            final TableView<Record> editDataTable = new TableView<>();
            editDataTable.setEditable(true);
            editDataTable.setTranslateY(180);
            editDataTable.setPrefSize(800, 420);

            TableColumn<Record, String> editDataCarBrandCol = new TableColumn<>("Car Brand");
            editDataCarBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            editDataCarBrandCol.setPrefWidth(150);
            editDataCarBrandCol.setResizable(false);

            TableColumn<Record, String> editDataCarModelCol = new TableColumn<>("Car Model");
            editDataCarModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            editDataCarModelCol.setPrefWidth(250);
            editDataCarModelCol.setResizable(false);

            TableColumn<Record, String> editDataCarYearCol = new TableColumn<>("Car Year");
            editDataCarYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            editDataCarYearCol.setPrefWidth(100);
            editDataCarYearCol.setResizable(false);

            TableColumn<Record, String> editDataCarPriceCol = new TableColumn<>("Car Price");
            editDataCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            editDataCarPriceCol.setCellFactory(TextFieldTableCell.<Record>forTableColumn());

            TableColumn<Record, String> editDataCarCategoryCol = new TableColumn<>("Car Category");
            editDataCarCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            editDataCarCategoryCol.setPrefWidth(150);
            editDataCarCategoryCol.setResizable(false);

            editDataTable.setItems(dataList);
            editDataTable.getColumns().addAll(editDataCarBrandCol, editDataCarModelCol, editDataCarCategoryCol, editDataCarYearCol, editDataCarPriceCol);

            Button editDataBackBtn = new Button("Back");
            editDataBackBtn.setTranslateX(15);
            editDataBackBtn.setTranslateY(15);
            editDataBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(adminDashboardScene);
                }
            });
            Label editHowToLbl = new Label("*Double click on the specific price cell to update the price \n\t and click on the update button to update data");
            editHowToLbl.setTranslateX(250);
            editHowToLbl.setTranslateY(140);

            Button editCarPriceBtn = new Button("Update");
            editCarPriceBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            editCarPriceBtn.setTranslateX(300);
            editCarPriceBtn.setTranslateY(70);
            editCarPriceBtn.setPrefSize(200, 60);
            editCarPriceBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    ab.DataEditedAlert();
                }
            });
            editPriceScenePane.getChildren().addAll(editPriceHeadLbl, editHowToLbl, editCarPriceBtn, editDataTable, editDataBackBtn);

            editPriceScene = new Scene(editPriceScenePane, 800, 600);
            AnchorPane deleteDataScenePane = new AnchorPane();

            Label dltDataHeadLbl = new Label("DELETE DATA");
            dltDataHeadLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            dltDataHeadLbl.setTranslateX(280);

            Label dltCarModelLbl = new Label("Enter Car Model:");
            dltCarModelLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            dltCarModelLbl.setTranslateX(100);
            dltCarModelLbl.setTranslateY(80);

            TextField dltCarModelTF = new TextField();
            dltCarModelTF.setTranslateX(100);
            dltCarModelTF.setTranslateY(110);
            dltCarModelTF.setPrefSize(280, 20);

            Button dltDataToDBBtn = new Button("Delete");
            dltDataToDBBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            dltDataToDBBtn.setTranslateX(500);
            dltDataToDBBtn.setTranslateY(80);
            dltDataToDBBtn.setPrefSize(200, 60);

            final TableView<Record> dltDataTable = new TableView<>();
            dltDataTable.setEditable(false);
            dltDataTable.setTranslateY(200);
            dltDataTable.setPrefSize(800, 400);

            TableColumn<Record, String> dltDataCarBrandCol = new TableColumn<>("Car Brand");
            dltDataCarBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            dltDataCarBrandCol.setPrefWidth(150);
            dltDataCarBrandCol.setResizable(false);

            TableColumn<Record, String> dltDataCarModelCol = new TableColumn<>("Car Model");
            dltDataCarModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            dltDataCarModelCol.setPrefWidth(250);
            dltDataCarModelCol.setResizable(false);

            TableColumn<Record, String> dltDataCarYearCol = new TableColumn<>("Car Year");
            dltDataCarYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            dltDataCarYearCol.setPrefWidth(100);
            dltDataCarYearCol.setResizable(false);

            TableColumn<Record, String> dltDataCarPriceCol = new TableColumn<>("Car Price");
            dltDataCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            dltDataCarPriceCol.setPrefWidth(150);
            dltDataCarPriceCol.setResizable(false);

            TableColumn<Record, String> dltDataCarCategoryCol = new TableColumn<>("Car Category");
            dltDataCarCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            dltDataCarCategoryCol.setPrefWidth(150);
            dltDataCarCategoryCol.setResizable(false);
            dltDataTable.setItems(dataList);
            dltDataTable.getColumns().addAll(dltDataCarBrandCol, dltDataCarModelCol, dltDataCarCategoryCol, dltDataCarYearCol, dltDataCarPriceCol);
            dltDataToDBBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String dltModelKey = dltCarModelTF.getText();
                    ArrayList<String> carBrandAL = new ArrayList<>(carBrandQueue);
                    ArrayList<String> carModelAL = new ArrayList<>(carModelQueue);
                    ArrayList<String> carYearAL = new ArrayList<>(carYearQueue);
                    ArrayList<String> carPriceAL = new ArrayList<>(carPriceQueue);
                    ArrayList<String> carCategoryAL = new ArrayList<>(carCategoryQueue);

                    int index = carModelAL.indexOf(dltModelKey);

                    carBrandAL.remove(index);
                    carModelAL.remove(index);
                    carYearAL.remove(index);
                    carPriceAL.remove(index);
                    carCategoryAL.remove(index);
                    System.out.println(carModelAL);

                    carBrandQueue.clear();
                    carModelQueue.clear();
                    carCategoryQueue.clear();
                    carYearQueue.clear();
                    carPriceQueue.clear();
                    System.out.println(carBrandQueue);
                    fop.FileUpdate(carBrandAL, carModelAL, carCategoryAL, carYearAL, carPriceAL, carDetailsFileName, false);
                    fop.ArrayToQueue(carBrandQueue, carModelQueue, carCategoryQueue, carPriceQueue, carYearQueue, carDetailsFileName);

                    System.out.println(carBrandQueue);
                    System.out.println(carModelQueue);
                    System.out.println(carCategoryQueue);
                    System.out.println(carYearQueue);
                    System.out.println(carPriceQueue);
                    dltCarModelTF.clear();

                    updateTableList();
                    dltDataTable.setItems(dataList);
                }
            });

            Button dltDataBackBtn = new Button("Back");
            dltDataBackBtn.setTranslateX(15);
            dltDataBackBtn.setTranslateY(15);
            dltDataBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(adminDashboardScene);
                }
            });

            deleteDataScenePane.getChildren().addAll(dltDataHeadLbl, dltCarModelLbl, dltCarModelTF, dltDataToDBBtn, dltDataTable, dltDataBackBtn);
            deleteDataScene = new Scene(deleteDataScenePane, 800, 600);

            AnchorPane userDashboardScenePane = new AnchorPane();

            Label userDashboardLbl = new Label("USER DASHBOARD");
            userDashboardLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            userDashboardLbl.setTranslateX(20);

            Button searchCarByBrandBtn = new Button("Search Car By Brand");
            searchCarByBrandBtn.setTranslateX(100);
            searchCarByBrandBtn.setTranslateY(70);
            searchCarByBrandBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            searchCarByBrandBtn.setPrefSize(200, 60);
            searchCarByBrandBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(searchCarByBrandScene);
                }
            });

            Button searchCarByModelBtn = new Button("Search Car By Model");
            searchCarByModelBtn.setTranslateX(100);
            searchCarByModelBtn.setTranslateY(150);
            searchCarByModelBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            searchCarByModelBtn.setPrefSize(200, 60);
            searchCarByModelBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(searchCarByModelScene);
                }
            });

            Button viewAllCarsBtn = new Button("View All Cars");
            viewAllCarsBtn.setTranslateX(100);
            viewAllCarsBtn.setTranslateY(230);
            viewAllCarsBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            viewAllCarsBtn.setPrefSize(200, 60);
            viewAllCarsBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(viewAllCarsScene);
                }
            });

            Button userExitBtn = new Button("Exit");
            userExitBtn.setTranslateX(100);
            userExitBtn.setTranslateY(310);
            userExitBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            userExitBtn.setPrefSize(200, 60);
            userExitBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    System.exit(0);
                }
            });

            Button userFeedbackBtn = new Button("Give Feedback");
            userFeedbackBtn.setTranslateX(300);
            userFeedbackBtn.setTranslateY(370);
            userFeedbackBtn.textAlignmentProperty().set(TextAlignment.CENTER);
            ArrayList<String> ratingChoiceList = new ArrayList<>();
            ratingChoiceList.add("0 Star");
            ratingChoiceList.add("1 Star");
            ratingChoiceList.add("2 Star");
            ratingChoiceList.add("3 Star");
            ratingChoiceList.add("4 Star");
            ratingChoiceList.add("5 Star");
            ChoiceDialog<String> feedbackDialog = new ChoiceDialog<>("Stars", ratingChoiceList);
            userFeedbackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    feedbackDialog.setHeaderText("Enjoying using CarsWorld App?\nHow will you rate our app?");
                    feedbackDialog.setContentText("Please select rating: ");
                    Optional<String> rating = feedbackDialog.showAndWait();
                    if (rating.isPresent()) {
                        feedbackStack.push(rating.get());
                        ArrayList<String> feedbackAL = new ArrayList<>(feedbackStack);
                        String[] feedbackStr = new String[feedbackAL.size()];
                        for (int j = 0; j < feedbackStack.size(); j++) {
                            feedbackStr[j] = feedbackAL.get(j);
                        }

                        fop.FeedBackFileWrite(feedbackStr, feedbackFileName, true);
                    }
                    feedbackDialog.setTitle("Give Feedback");
                }
            });

            userDashboardScenePane.getChildren().addAll(userDashboardLbl, searchCarByBrandBtn, searchCarByModelBtn, viewAllCarsBtn, userExitBtn, userFeedbackBtn);
            userDashboardScene = new Scene(userDashboardScenePane, 400, 420);

            AnchorPane searchCarByBrandScenePane = new AnchorPane();

            Label searchBrandHeadLbl = new Label("SEARCH CAR BY BRAND");
            searchBrandHeadLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            searchBrandHeadLbl.setTranslateX(180);

            Label searchBrandLbl = new Label("Enter Car Brand:");
            searchBrandLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            searchBrandLbl.setTranslateX(100);
            searchBrandLbl.setTranslateY(80);

            TextField searchBrandTF = new TextField();
            searchBrandTF.setTranslateX(100);
            searchBrandTF.setTranslateY(110);
            searchBrandTF.setPrefSize(280, 20);

            Button searchBrandBtn = new Button("Search");
            searchBrandBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            searchBrandBtn.setTranslateX(500);
            searchBrandBtn.setTranslateY(80);
            searchBrandBtn.setPrefSize(200, 60);

            final TableView<Record> searchBrandTable = new TableView<>();
            searchBrandTable.setEditable(false);
            searchBrandTable.setTranslateY(180);
            searchBrandTable.setPrefSize(800, 420);

            TableColumn<Record, String> searchBrandCarBrandCol = new TableColumn<>("Car Brand");
            searchBrandCarBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            searchBrandCarBrandCol.setPrefWidth(150);
            searchBrandCarBrandCol.setResizable(false);

            TableColumn<Record, String> searchBrandCarModelCol = new TableColumn<>("Car Model");
            searchBrandCarModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            searchBrandCarModelCol.setPrefWidth(250);
            searchBrandCarModelCol.setResizable(false);

            TableColumn<Record, String> searchBrandCarYearCol = new TableColumn<>("Car Year");
            searchBrandCarYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            searchBrandCarYearCol.setPrefWidth(100);
            searchBrandCarYearCol.setResizable(false);

            TableColumn<Record, String> searchBrandCarPriceCol = new TableColumn<>("Car Price");
            searchBrandCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            searchBrandCarPriceCol.setPrefWidth(150);
            searchBrandCarPriceCol.setResizable(false);

            TableColumn<Record, String> searchBrandCarCategoryCol = new TableColumn<>("Car Category");
            searchBrandCarCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            searchBrandCarCategoryCol.setPrefWidth(150);
            searchBrandCarCategoryCol.setResizable(false);
            searchBrandTable.setItems(dataList);

            searchBrandTable.getColumns().addAll(searchBrandCarBrandCol, searchBrandCarModelCol, searchBrandCarCategoryCol, searchBrandCarYearCol, searchBrandCarPriceCol);

            searchBrandBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String searchBrandKey = searchBrandTF.getText();
                    SearchByBrand(searchBrandKey, searchBrandTable);
                    searchBrandTF.clear();
                }
            });

            Button searchByBrandBackBtn = new Button("Back");
            searchByBrandBackBtn.setTranslateX(15);
            searchByBrandBackBtn.setTranslateY(140);
            searchByBrandBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    searchBrandTable.setItems(dataList);
                    primaryStage.setScene(userDashboardScene);
                }
            });

            searchCarByBrandScenePane.getChildren().addAll(searchBrandHeadLbl, searchBrandLbl, searchBrandTF, searchBrandBtn, searchBrandTable, searchByBrandBackBtn);
            searchCarByBrandScene = new Scene(searchCarByBrandScenePane, 800, 600);

            AnchorPane searchCarByModelScenePane = new AnchorPane();

            Label searchModelHeadLbl = new Label("SEARCH CAR BY MODEL");
            searchModelHeadLbl.setFont(Font.font(null, FontWeight.BOLD, 40));
            searchModelHeadLbl.setTranslateX(180);

            Label searchModelLbl = new Label("Enter Car Model:");
            searchModelLbl.setFont(Font.font(null, FontPosture.ITALIC, 16));
            searchModelLbl.setTranslateX(100);
            searchModelLbl.setTranslateY(80);

            TextField searchModelTF = new TextField();
            searchModelTF.setTranslateX(100);
            searchModelTF.setTranslateY(110);
            searchModelTF.setPrefSize(280, 20);

            Button searchModelBtn = new Button("Search");
            searchModelBtn.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            searchModelBtn.setTranslateX(500);
            searchModelBtn.setTranslateY(80);
            searchModelBtn.setPrefSize(200, 60);

            final TableView<Record> searchModelTable = new TableView<>();
            searchModelTable.setEditable(false);
            searchModelTable.setTranslateY(180);
            searchModelTable.setPrefSize(800, 420);

            TableColumn<Record, String> searchModelCarBrandCol = new TableColumn<>("Car Brand");
            searchModelCarBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            searchModelCarBrandCol.setPrefWidth(150);
            searchModelCarBrandCol.setResizable(false);

            TableColumn<Record, String> searchModelCarModelCol = new TableColumn<>("Car Model");
            searchModelCarModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            searchModelCarModelCol.setPrefWidth(250);
            searchModelCarModelCol.setResizable(false);

            TableColumn<Record, String> searchModelCarYearCol = new TableColumn<>("Car Year");
            searchModelCarYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            searchModelCarYearCol.setPrefWidth(100);
            searchModelCarYearCol.setResizable(false);

            TableColumn<Record, String> searchModelCarPriceCol = new TableColumn<>("Car Price");
            searchModelCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            searchModelCarPriceCol.setPrefWidth(150);
            searchModelCarPriceCol.setResizable(false);

            TableColumn<Record, String> searchModelCarCategoryCol = new TableColumn<>("Car Category");
            searchModelCarCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            searchModelCarCategoryCol.setPrefWidth(150);
            searchModelCarCategoryCol.setResizable(false);
            searchModelTable.setItems(dataList);

            searchModelTable.getColumns().addAll(searchModelCarBrandCol, searchModelCarModelCol, searchModelCarCategoryCol, searchModelCarYearCol, searchModelCarPriceCol);

            searchModelBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String searchModelKey = searchModelTF.getText();
                    SearchByModel(searchModelKey, searchModelTable);
                    searchModelTF.clear();
                }
            });

            Button searchByModelBackBtn = new Button("Back");
            searchByModelBackBtn.setTranslateX(15);
            searchByModelBackBtn.setTranslateY(140);
            searchByModelBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    searchModelTF.clear();
                    searchModelTable.setItems(dataList);
                    primaryStage.setScene(userDashboardScene);
                }
            });
            searchCarByModelScenePane.getChildren().addAll(searchModelHeadLbl, searchModelLbl, searchModelTF, searchModelBtn, searchModelTable, searchByModelBackBtn);
            searchCarByModelScene = new Scene(searchCarByModelScenePane, 800, 600);

            AnchorPane viewAllCarsScenePane = new AnchorPane();

            final TableView<Record> viewAllTable = new TableView<>();
            viewAllTable.setPrefSize(800, 550);
            TableColumn<Record, String> viewAllCarBrandCol = new TableColumn<>("Car Brand");
            viewAllCarBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            viewAllCarBrandCol.setPrefWidth(150);
            viewAllCarBrandCol.setResizable(false);

            TableColumn<Record, String> viewAllCarModelCol = new TableColumn<>("Car Model");
            viewAllCarModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            viewAllCarModelCol.setPrefWidth(250);
            viewAllCarBrandCol.setResizable(false);

            TableColumn<Record, String> viewAllCarYearCol = new TableColumn<>("Car Year");
            viewAllCarYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            viewAllCarYearCol.setPrefWidth(100);
            viewAllCarYearCol.setResizable(false);

            TableColumn<Record, String> viewAllCarPriceCol = new TableColumn<>("Car Price");
            viewAllCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            viewAllCarPriceCol.setPrefWidth(150);
            viewAllCarPriceCol.setResizable(false);

            TableColumn<Record, String> viewAllCarCategoryCol = new TableColumn<>("Car Category");
            viewAllCarCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            viewAllCarCategoryCol.setPrefWidth(150);
            viewAllCarCategoryCol.setResizable(false);
            viewAllTable.setItems(dataList);

            viewAllTable.getColumns().addAll(viewAllCarBrandCol, viewAllCarModelCol, viewAllCarCategoryCol, viewAllCarYearCol, viewAllCarPriceCol);

            Button viewAllBackBtn = new Button("Back");
            viewAllBackBtn.setTranslateX(15);
            viewAllBackBtn.setTranslateY(560);
            viewAllBackBtn.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent arg0) {
                    primaryStage.setScene(userDashboardScene);

                }
            });

            viewAllCarsScenePane.getChildren().addAll(viewAllTable, viewAllBackBtn);
            viewAllCarsScene = new Scene(viewAllCarsScenePane, 800, 600);

            primaryStage.setScene(mainScene);
            primaryStage.setTitle("CarsWorld");
            primaryStage.setResizable(false);
            primaryStage.show();

            readFile();
        } catch (Exception e) {
            ab.ErrorAlert();
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(carDetailsFileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                Record record = new Record(fields[0], fields[1], fields[2], fields[3], fields[4]);
                dataList.add(record);
            }
            br.close();
        } catch (Exception e) {
            ab.ErrorAlert();
        }
    }

    private void updateTableList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(carDetailsFileName));
            String line;
            dataList.clear();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                Record record = new Record(fields[0], fields[1], fields[2], fields[3], fields[4]);
                dataList.add(record);
            }
            br.close();
        } catch (Exception e) {
            ab.ErrorAlert();
        }
    }

    private void SearchByBrand(String searchBrandKey, TableView<Record> searchBrandTable) {
        ObservableList<Record> filteredDataList = FXCollections.observableArrayList();
        for (Record r : searchBrandTable.getItems()) {
            if (r.getBrand().contains(searchBrandKey) && searchBrandKey.length() > 0) {
                filteredDataList.add(r);
            }
        }

        if (searchBrandKey.equals("")) {
            searchBrandTable.setItems(dataList);
        } else {
            searchBrandTable.setItems(filteredDataList);
        }
    }

    private void SearchByModel(String searchModelKey, TableView<Record> searchModelTable) {
        ObservableList<Record> filteredDataList = FXCollections.observableArrayList();
        for (Record r : searchModelTable.getItems()) {
            if (r.getModel().contains(searchModelKey) && searchModelKey.length() > 0) {
                filteredDataList.add(r);
            }
        }

        if (searchModelKey.equals("")) {
            searchModelTable.setItems(dataList);
        } else {
            searchModelTable.setItems(filteredDataList);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
