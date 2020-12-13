package home;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button menuStartBtn;

    @FXML
    private Button menuEncryptBtn;

    @FXML
    private Button menuDecryptBtn;

    @FXML
    private Button menuAboutBtn;

    @FXML
    private Button chooseFileBtnE;

    @FXML
    private Button importImgE;

    @FXML
    private Button importImgD;

    @FXML
    private Button exportImgBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private AnchorPane apStart;

    @FXML
    private AnchorPane apAbout;

    @FXML
    private AnchorPane apEncrypt;

    @FXML
    private AnchorPane apDecrypt;

    @FXML
    private TextField tfFileE;

    @FXML
    private TextField tfKeyE;

    @FXML
    private TextField tfImgE;

    @FXML
    private TextField tfImgD;

    @FXML
    private TextField tfExportE;

    @FXML
    private TextArea tfResultD;

    @FXML
    private TextArea tfTypeE;

    @FXML
    private Label warningLabelE;

    @FXML
    private Label warningLabelD;

    @FXML
    private Label lenLabel;

    Steganography st = new Steganography();
    int maxLen = 0;

    public Controller() throws NoSuchAlgorithmException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apStart.setVisible(true);
        apStart.setOpacity(1);
        tfResultD.setEditable(false);
        warningLabelE.setVisible(false);
        warningLabelD.setVisible(false);
//        tfTypeE.addEventFilter(KeyEvent.KEY_TYPED, maxLength(st.getMaxLen()));
//        setLimit();
        lenLabel.textProperty().bind(tfTypeE.textProperty().length().asString("Chars: %d" + "/" + this.maxLen));
    }
    public void handleClicks(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        if (actionEvent.getSource() == menuStartBtn) {
            apStart.setVisible(true);
            apStart.setOpacity(1);
            apAbout.setVisible(false);
            apAbout.setOpacity(0);
            apDecrypt.setVisible(false);
            apDecrypt.setOpacity(0);
            apEncrypt.setVisible(false);
            apEncrypt.setOpacity(0);
        }
        if (actionEvent.getSource() == menuAboutBtn) {
            apAbout.setVisible(true);
            apAbout.setOpacity(1);
            apStart.setVisible(false);
            apStart.setOpacity(0);
            apDecrypt.setVisible(false);
            apDecrypt.setOpacity(0);
            apEncrypt.setVisible(false);
            apEncrypt.setOpacity(0);
        }
        if (actionEvent.getSource() == menuEncryptBtn) {
            apAbout.setVisible(false);
            apAbout.setOpacity(0);
            apStart.setVisible(false);
            apStart.setOpacity(0);
            apDecrypt.setVisible(false);
            apDecrypt.setOpacity(0);
            apEncrypt.setVisible(true);
            apEncrypt.setOpacity(1);
        }
        if (actionEvent.getSource() == menuDecryptBtn) {
            apAbout.setVisible(false);
            apAbout.setOpacity(0);
            apStart.setVisible(false);
            apStart.setOpacity(0);
            apDecrypt.setVisible(true);
            apDecrypt.setOpacity(1);
            apEncrypt.setVisible(false);
            apEncrypt.setOpacity(0);
        }
        if (actionEvent.getSource() == importImgE) {
            Import importB = new Import();
            st.setBytes(importB.importByte(menuEncryptBtn));
            tfImgE.setText(importB.getPath());
            st.countMax();
            this.maxLen = st.getMaxLen();
            setLimit();
            lenLabel.textProperty().bind(tfTypeE.textProperty().length().asString("Chars: %d" + "/" + this.maxLen));
        }
        if (actionEvent.getSource() == chooseFileBtnE) {
            Import importFile = new Import();
            importFile.importText(menuEncryptBtn);
            tfFileE.setText(importFile.getPath());
            if(importFile.getReadedContent().length() > this.maxLen){
                tfTypeE.setText(importFile.getReadedContent().substring(0,this.maxLen));
            }else{
                tfTypeE.setText(importFile.getReadedContent());
            }
        }
        if (actionEvent.getSource() == exportImgBtn) {
            Export export = new Export();
            if(tfImgE.getText().equals("")){
                warningLabelE.setVisible(true);
            }else if(tfTypeE.getText().equals("")){
                warningLabelE.setText("Empty text field");
                warningLabelE.setVisible(true);
            }else{
                warningLabelE.setVisible(false);
                st.setText(tfTypeE.getText());
                st.cipher();
                export.setByteToExport(st.getBytes());
                export.exportByte(exportImgBtn);
                tfExportE.setText(export.getPath());
            }
        }
        if (actionEvent.getSource() == importImgD) {
            Import importB = new Import();
            st.setBytes(importB.importByte(menuEncryptBtn));
            st.decipher();
            tfImgD.setText(importB.getPath());
            if(st.getDecryptedText().equals("null") || st.getBytes().length == 0){
                tfResultD.clear();
                warningLabelD.setVisible(true);
            }else{
                warningLabelD.setVisible(false);
                tfResultD.setText(st.getDecryptedText());
            }
        }
        if (actionEvent.getSource() == exitBtn){
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        }

    }
    public EventHandler<KeyEvent> maxLength(final Integer i) {
        return new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                TextArea tx = (TextArea) arg0.getSource();
                if (tx.getText().length() >= i) {
                    arg0.consume();
                }

            }

        };

    }

    private void setLimit(){
        this.tfTypeE.textProperty().addListener(
                (observable,oldValue,newValue)-> {
                    if(newValue.length() > this.maxLen) tfTypeE.setText(oldValue);
                }
        );
    }

}
