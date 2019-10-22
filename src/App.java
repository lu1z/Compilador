import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements Linguagem20192Constants {

	public static Linguagem20192 parser;
	public static Scene scene;
	public static Stage stage;
	public static File file;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(new URL("file:fxml/gui.fxml"));
		VBox root = loader.<VBox> load();
		scene = new Scene(root);
		primaryStage.setTitle("Compiler");
		primaryStage.setScene(scene);
		primaryStage.show();
		addTextImputAreaACursorListener();
		addTextImputAreaTextLIstener();
		fix();
	}

	private void fix() {
    	TextArea ti = (TextArea) App.scene.lookup("#text_input"); 
    	ScrollBar scrollBarv = (ScrollBar)ti.lookup(".scroll-bar:vertical");
    	scrollBarv.setDisable(true);
    	scrollBarv.setOpacity(0);
	}

	private void addTextImputAreaTextLIstener() {
		TextArea ta = (TextArea) App.scene.lookup("#text_input");
		Label la = (Label) App.scene.lookup("#label_line_number");
		ta.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String rawText = ta.getText();
				String[] lineCharacters = rawText.split("\n");
				int lines = lineCharacters.length;
				StringBuilder lineNumbersString = new StringBuilder();
				for (int i = 1; i <= lines; i++)
					lineNumbersString.append(i).append("\n");
				la.setText(lineNumbersString.toString());
			}
		});
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
				if(lines < 1) {
					lines = rawText.length();
					la.setText("Line: " + (lines + 1) + " Collun: " + 1);
					return;
				}
				String lastLineCharacters = lineCharacters[lines - 1];
				int colluns = lastLineCharacters.length();
				char c = '\0';
				if (newValue.intValue() > 0)
					c = rawText.charAt(newValue.intValue() - 1);
				if (c == '\n')
					la.setText("Line: " + (lines + 1) + " Collun: " + 1);
				else
					la.setText("Line: " + lines + " Collun: " + (colluns + 1));
			}
		});
	}

	public static void listTokens(TextArea ti, TextArea to) {
		String input = ti.getText();
		to.clear();
		Reader reader = new StringReader(input);
		try {
			parser = new Linguagem20192(reader);		
			for (Token t = App.parser.getNextToken(); t.kind != App.EOF; t = App.parser.getNextToken()) {
				to.appendText("Lexeme : " + App.tokenImage[t.kind] + " line: " + t.beginLine + " collun: "
						+ t.beginColumn + " Category: " + category(t.kind) + " Category number: " + t.kind + " Image: "
						+ t.image + "\n");
			}
		} catch (TokenMgrError er) {
			to.setText(er.getMessage());
			System.out.println(er.getMessage());
			parser = new Linguagem20192(reader);
		} catch (Error er) {
			to.setText(er.getMessage());
			System.out.println(er.getMessage());
			parser = new Linguagem20192(reader);
		}
	}
	
	public static void compileSyntax(TextArea ti, TextArea to) {
		String input = ti.getText();
		to.clear();
		Reader reader = new StringReader(input);
		try {
			parser = new Linguagem20192(reader);		
			parser.syntax();
			to.setText("parse successful");
		} catch (TokenMgrError er) {
			to.setText(er.getMessage());
			System.out.println(er.getMessage());
			parser = new Linguagem20192(reader);
		} catch (Error er) {
			to.setText(er.getMessage());
			System.out.println(er.getMessage());
			parser = new Linguagem20192(reader);
		} catch (ParseException er) {
			to.setText(getHeaderExeption(er) + er.getMessage());
			System.out.println(er.getMessage());
			parser = new Linguagem20192(reader);
		}
	}

	private static String addErrorType(String message) {
		String s = message.replaceFirst(".*\".*\".*\"(.*)\"", "$1");
		s = message.replaceFirst(".*<EOF>.*(.*)", "$1");	
		return s;
	}

	public static String category(int kind) {
		if (kind >= 14 & kind <= 19)
			return "RELACIONAL OPERATORS";
		if (kind >= 20 & kind <= 26)
			return "RESERVED SIMBOLS";
		if (kind >= 27 & kind <= 33)
			return "ARITMETIC OPERATORS";
		if (kind >= 34 & kind <= 36)
			return "LOGIC OPERATORS";
		if (kind >= 37 & kind <= 40)
			return "DATA TYPES";
		if (kind >= 41 & kind <= 45)
			return "CONSTANTS";
		if (kind >= 46 & kind <= 62)
			return "RESERVED WORDS";
		if (kind >= 63 & kind <= 63)
			return "IDENTIFIER";
		if (kind >= 64 & kind <= 68)
			return "AUXILIARIES";
		return "MICELANIOUS";
	}
	
	private static String getHeaderExeption(ParseException er) {
		int id = parser.getExeptionId();
		switch (id) {
			case 0 : ;
				return "Error in Epsylon choice.\n";
			case 1 : ;
				return "Error in general program extructure.\n";
			case 2 :
				return "Error in variable and constant declaration extructure.\n";
			case 3 :
				return "Error in constant declaration extructure.\n"; 
			case 4 :
				return "Error in variable declaration extructure.\n"; 
			case 5 :
				return "Error in indentifier list extructure.\n"; 
			case 6 :
				return "Error in atribution command extructure.\n"; 
			case 7 :
				return "Error in expression instance.\n"; 
			case 8 :
				return "Error in indentifier indexation.\n"; 
			case 9 :
				return "Error in command list extructure.\n"; 
			case 10 :
				return "Error in purpose comentary.\n"; 
			case 11 : 
				return "Error in data input command extructure.\n";
			case 12 : 
				return "Error in data output command extructure.\n";
			case 13 : 
				return "Error in data selection command extructure.\n";
			case 14 : 
				return "Error in data repetition command extructure.\n";
			default :
				return "Error in parsing operation.\n";		
		}
	}
	
}