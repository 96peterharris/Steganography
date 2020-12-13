package home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Import {
    private String path = "";
    private String readedContent = "";
    private HashMap<Character, ArrayList<Integer>> keyMap;
    private ArrayList<Integer> keyArr;

    protected Import() {
        this.path = "";
        this.readedContent = "";
        this.keyMap = new HashMap<>();
        this.keyArr = new ArrayList<>();
    }

    protected void importText(Button button) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = (Stage) button.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            this.path = file.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null){
                    this.readedContent += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected byte[] importByte(Button button) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp"));
        Stage stage = (Stage) button.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            this.path = file.getAbsolutePath();
            Path path = Paths.get(this.path);
            byte[] data = Files.readAllBytes(path);
            return data;
        }
        return new byte[0];
    }


    private void jsonToMap() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.keyMap =  objectMapper.readValue(this.readedContent, new TypeReference<HashMap<Character, ArrayList<Integer>>>(){});
    }

    protected String getPath() {
        return this.path;
    }

    protected String getReadedContent() {return this.readedContent;}
    protected HashMap<Character, ArrayList<Integer>> getKeyMap() { return this.keyMap;}
    protected ArrayList<Integer> getKeyArr(){ return this.keyArr;}
}
