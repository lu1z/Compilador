import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application implements  Linguagem20191Constants {

	public static Scene scene;
	public static Stage stage;
	public static Linguagem20191 parser;
	public static File file;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception, ParseException {
    	stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:fxml/gui.fxml"));
        AnchorPane root = loader.<AnchorPane>load();
        scene = new Scene(root);
        primaryStage.setTitle("Compiler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void readFile(){
        try{
        	System.out.println ("Reading the file " + ""+ " ..." );
            parser = new Linguagem20191(new java.io.FileInputStream(""));
                    
        }
        catch(java.io.FileNotFoundException e) {
                  System.out.println ("The file " +"" + " was not found.");
            return;
         }
        catch (Exception e)
        {
          System.out.println("NOK.");
          System.out.println(e.getMessage());
          Linguagem20191.ReInit(System.in);
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
