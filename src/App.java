import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements  LinguagemConstants {

	public static Scene scene;
	public static Stage stage;
	public static Linguagem parser;
	public static File file;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception, ParseException {
    	stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:fxml/gui.fxml"));
        VBox root = loader.<VBox>load();
        scene = new Scene(root);
        primaryStage.setTitle("Compiler");
        primaryStage.setScene(scene);
        primaryStage.show();
        addTextImputAreaACursorListener();
    }
    
    private void addTextImputAreaACursorListener() {
        TextArea ta = (TextArea) App.scene.lookup("#text_input");
        Label la = (Label) App.scene.lookup("#label_cursor_coordinate");
        ta.caretPositionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            	String rawText = ta.getText().substring(0, newValue.intValue());
                String[] lineCharacters = rawText.split("\n");
                int lines = lineCharacters.length;
                String lastLineCharacters = lineCharacters[lines - 1];
                int colluns = lastLineCharacters.length();
                char c = '\0';
                if(newValue.intValue() > 0)
                	 c = rawText.charAt(newValue.intValue() - 1);		
                if(c == '\n')
                	la.setText("Line: "+ (lines + 1) + " Collun: " + 0);
                else
                	la.setText("Line: "+ lines + " Collun: " + colluns);
            }
        });
	}

	public void readFile(){
        try{
        	
         //   for(Token t = App.parser.getNextToken(); t.kind != App.EOF; t = App.parser.getNextToken())
       //     	to.appendText("Token kind: " + t.kind + " Image: " + t.image + "\n");
                    
        }
        catch (Exception e)
        {
          System.out.println("NOK.");
          System.out.println(e.getMessage());
          Linguagem.ReInit(System.in);
        }
        catch (Error e)
        {
          System.out.println("Oops.");
          System.out.println(e.getMessage());
        }
        
        for(Token t = parser.getNextToken(); t.kind != EOF; t = parser.getNextToken())
      	  System.out.println(""+t.kind);
    }
}
