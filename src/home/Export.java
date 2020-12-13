package home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class Export {
    private String path = "";
    byte[] data;
    HashMap<Character, ArrayList<Integer>> keyMap = new HashMap<>();
    ArrayList<Integer> intArr = new ArrayList<>();
    ArrayList<ArrayList<Integer>> metaArr = new ArrayList<>();
    private ArrayList<Integer> keyArr = new ArrayList<>();

    protected Export() {
    }

    protected void exportByte(Button button) {

        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) button.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            try {
                Files.write(file.toPath(), this.data);
            } catch (IOException ex) {

            }
        }
    }

    private void stringToJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(this.keyMap);
            this.data = json.getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void exportMap(Button button) {
        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) button.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json doc", "*.json"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            stringToJson();
            try {
                Files.write(file.toPath(), this.data);
            } catch (IOException ex) {

            }
        }
    }

    protected void exportArray(Button button) {
        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) button.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json doc", "*.json"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            arrayToJson();
            try {
                Files.write(file.toPath(), this.data);
            } catch (IOException ex) {

            }
        }
    }

    protected void arrayToJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(this.intArr);
            this.data = json.getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected String getPath() {
        return this.path;
    }

    protected  void setMapToExport( HashMap<Character, ArrayList<Integer>> keyMap){ this.keyMap.clear(); this.keyMap = keyMap; }

    protected  void setArrayToExport( ArrayList<Integer> intArr){ this.intArr = intArr;}

    protected void setMetaToExport( ArrayList<ArrayList<Integer>> intArr){ this.metaArr = metaArr;}

    protected void setStringToExport(String text) {
        this.data = text.getBytes();
    }
    protected void setByteToExport(byte[] byteArr) { this.data = byteArr;};

    protected void setKeyArr(ArrayList<Integer> keyArr){
        this.keyArr.clear();
        this.keyArr = keyArr;}

    public void exportKeyArr(Button button){
        String tmp = "";
        for(int i = 0; i < this.keyArr.size(); i++){
            tmp += keyArr.get(i);
        }
        byte[] data1 = tmp.getBytes();

        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) button.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            this.path = file.getAbsolutePath();
            try {
                Files.write(file.toPath(), data1 );
            } catch (IOException ex) {

            }
        }

    }

}
