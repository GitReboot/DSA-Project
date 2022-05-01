import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertBoxes {
    public void ErrorAlert() {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error 201: An error occurred!");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Oops! Error has occurred.");
        errorAlert.showAndWait();
    }

    public void EmptyIDPassAlert() {
        Alert emptyIDPassAlert = new Alert(AlertType.ERROR);
        emptyIDPassAlert.setTitle("Login Error!");
        emptyIDPassAlert.setHeaderText(null);
        emptyIDPassAlert.setContentText("Empty text field, kindly fill.");
        emptyIDPassAlert.showAndWait();
    }

    public void EmptyFieldsAlert() {
        Alert emptyFieldsAlert = new Alert(AlertType.ERROR);
        emptyFieldsAlert.setTitle("Empty Fields Error!");
        emptyFieldsAlert.setHeaderText(null);
        emptyFieldsAlert.setContentText("Empty text field, kindly fill");
        emptyFieldsAlert.showAndWait();
    }

    public void WrongIDPassAlert() {
        Alert wrongIDPassAlert = new Alert(AlertType.ERROR);
        wrongIDPassAlert.setTitle("Login Error");
        wrongIDPassAlert.setHeaderText(null);
        wrongIDPassAlert.setContentText("You have entered wrong ID or pass");
        wrongIDPassAlert.showAndWait();
    }

    public void DataEditedAlert() {
        Alert dataEditedAlert = new Alert(AlertType.CONFIRMATION);
        dataEditedAlert.setTitle("Data Edited");
        dataEditedAlert.setHeaderText(null);
        dataEditedAlert.setContentText("Data has been successfully edited");
        dataEditedAlert.showAndWait();
    }
}




