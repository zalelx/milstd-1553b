package view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

class NewMenuController {
    private int amountOfED;

    @FXML
    public void SetStatus() {
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/fxml/SetStatus.fxml"));

    }

    void setAmountOfED(int amountOfED) {
        this.amountOfED = amountOfED;
    }
}