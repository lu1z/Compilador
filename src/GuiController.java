import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class GuiController extends VBox {
    @FXML
    public void doCompile(Event e) throws FileNotFoundException {
    	TextArea to = (TextArea) App.scene.lookup("#text_output");
    	TextArea ti = (TextArea) App.scene.lookup("#text_input");   
        App.listTokens(ti, to);
    }
    
    @FXML
    public void doExecute(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doCut(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doCopy(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doPaste(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doSaveAs(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doSave(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doNew(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doExit(Event e){
        System.out.println("Button clicked");
    }
    @FXML
    public void doOpen(Event e){
        final FileChooser fileChooser = new FileChooser();
        App.file = fileChooser.showOpenDialog(App.stage);
        String content = null;
        try {
        	content = new String(Files.readAllBytes(App.file.toPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        TextArea ta = (TextArea) App.scene.lookup("#text_input");
        if(App.file != null)
        	ta.setText(content);
        Label la = (Label) App.scene.lookup("#label_file_name");
        if(App.file != null)
        	la.setText(App.file.getName());
    }
}
