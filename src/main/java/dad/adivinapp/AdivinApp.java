package dad.adivinapp;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdivinApp extends Application {

	private TextField nombreText;
	private Button comprobarButton;
	private Label instruccionesLabel;
	private VBox root;
	private Alert alert;

	int cont;
	Random r = new Random();
	int random = r.nextInt(100 - 1) + 1;

	@Override
	public void start(Stage primaryStage) throws Exception {

		instruccionesLabel = new Label();
		instruccionesLabel.setText("Introduce un número del 1 al 100");

		nombreText = new TextField();
		// Texto que aparece dentro del componente cuando está vacío
		nombreText.setPromptText("Escribe aquí el número");

		comprobarButton = new Button("Comprobar");
		// e -> onSaludarAction(e) == this::onSaludarAction
		comprobarButton.setOnAction(this::onComprobarAction);
		// No hará falta tabular para ejecutar el botón
		comprobarButton.setDefaultButton(true);
		// Instrucciones que indican que debe hacer usuario
		comprobarButton.setTooltip(new Tooltip("Comprobar si se adivinó el número"));

		root = new VBox();
		// Alinear los componentes de la escena en el centro de la ventana
		root.setAlignment(Pos.CENTER);
		// Se puede usar addAll porque no es un ArrayList
		root.getChildren().addAll(instruccionesLabel, nombreText, comprobarButton);
		// Evita que los componentes se expandan
		root.setFillWidth(false);
		root.setSpacing(5);

		// Tamaño de la escena, NO de la ventana
		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("AdivinApp");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void onComprobarAction(ActionEvent e) {
		try {
			String numero = nombreText.getText();
			adivinarNumero(numero);
		} catch (NumberFormatException ex) {
			error();
		}

	}

	private void adivinarNumero(String numero) {
		int num = Integer.parseInt(numero);

		if (num < 1 || num > 100) {
			error();
		} else {
			if (num > random) {
				cont++;
				advertenciaMenor(num);

			} else if (num < random) {
				cont++;
				advertenciaMayor(num);

			} else if (num == random) {
				informacion(cont);
				cont = 0;
				random = r.nextInt(100 - 1) + 1;
			}
		}
	}

	private void error() {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("AdivinApp");
		alert.setHeaderText("Error");
		alert.setContentText("El caracter introducido no es válido.");

		alert.showAndWait();
	}

	private void informacion(int cont) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AdivinApp");
		alert.setHeaderText("¡Has ganado!");
		alert.setContentText("Solo has necesitado " + cont + " intentos." + "\n Vuelve a jugar y hazlo mejor.");

		alert.showAndWait();
	}

	private void advertenciaMayor(int num) {
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("AdivinApp");
		alert.setHeaderText("¡Has fallado!");
		alert.setContentText("El número a advinar es mayor que " + num + "\n Vuelve a intentarlo.");

		alert.showAndWait();

	}

	private void advertenciaMenor(int num) {
		alert = new Alert(AlertType.WARNING);
		alert.setTitle("AdivinApp");
		alert.setHeaderText("¡Has fallado!");
		alert.setContentText("El número a advinar es menor que " + num + "\n Vuelve a intentarlo.");

		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
