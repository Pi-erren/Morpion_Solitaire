package application;

import components.Grid;
import components.Line;
import components.PlayedPoint;
import components.Point;
import game.GameManagerFX;
import game.Mode;
import helpers.DefaultCoordinates5;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MSGridController {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private GridPane gameGrid;
	@FXML
	private Text nomJoueur;
	@FXML
	private Text mode;
	@FXML
	private Text score;
	
	private GameManagerFX gameManager;
	private Button[][] buttonGrid = new Button[Grid.getSize()][Grid.getSize()];
	
	public void initGameManager() {
        this.gameManager = GameManagerFX.getInstance();
        updateGridUI();
        updateLabels();
    }
	
	private void drawLines() {
	    Grid grid = gameManager.getGrid();
	    for (components.Line line : grid.getLines()) {
	        // Obtenir les points de départ et de fin de la ligne
	        Set<Point> ends = line.getEndsOfLine();
	        Point[] endPoints = ends.toArray(new Point[0]);
	        Point startPoint = endPoints[0];
	        Point endPoint = endPoints[1];

	        // Trouver les boutons correspondants
	        Button startButton = buttonGrid[startPoint.getY()][startPoint.getX()];
	        Button endButton = buttonGrid[endPoint.getY()][endPoint.getX()];

	        // Créer une ligne graphique
	        javafx.scene.shape.Line guiLine = new javafx.scene.shape.Line();
	        guiLine.setStartX(startButton.getLayoutX() + startButton.getWidth() / 2);
	        guiLine.setStartY(startButton.getLayoutY() + startButton.getHeight() / 2);
	        guiLine.setEndX(endButton.getLayoutX() + endButton.getWidth() / 2);
	        guiLine.setEndY(endButton.getLayoutY() + endButton.getHeight() / 2);
	        guiLine.setStroke(javafx.scene.paint.Color.RED);

	        // Ajouter la ligne à la grille
	        gameGrid.getChildren().add(guiLine);
	    }
	}
	
	private void updateGridUI() {
        Grid grid = gameManager.getGrid();
        HashSet<Integer> defaultPointsHashes = DefaultCoordinates5.getValues();
        gameGrid.getChildren().clear();
        for (int y = 0; y < Grid.getSize(); y++) {
            for (int x = 0; x < Grid.getSize(); x++) {
            	
            	final int finalX = x;
                final int finalY = y;
                
            	Button button = new Button();
                button.setMinSize(25, 25);
                button.setText("*");
                button.setFont(new Font(12));
                button.setStyle("-fx-background-color: white;");
               
                if (grid.getPoint(x, y).isPlayed() && !defaultPointsHashes.contains(Objects.hash(x, y))) {
                	PlayedPoint playedPoint = (PlayedPoint) grid.getPoint(x, y);
                    button.setText(String.valueOf(playedPoint.getId()));
                }
                else if (grid.getPoint(x, y).isPlayed()) button.setText("X");
                else button.setText("*");
                
                button.setOnAction(event -> handleGridButtonAction(finalX, finalY));
                buttonGrid[y][x] = button;
                gameGrid.add(button, x, y);
            }
        }
        drawLines();
    }
	
	private void handleGridButtonAction(int x, int y) {
	    gameManager.playAt(x, y); // Jouez le point à la position (x, y)
	    updateGridUI(); // Mettre à jour l'interface utilisateur
	    updateLabels(); // Mettre à jour les informations du joueur
	}
	
	public void updateLabels() {
		if (gameManager != null) {
            nomJoueur.setText(gameManager.getPlayerName());
            if (mode.getText().isEmpty()) {
                if (Mode.getNumber() == 4 && Mode.getType().toString().equals("T")) mode.setText("4T");
        	    if (Mode.getNumber() == 4 && Mode.getType().toString().equals("D")) mode.setText("4D");
        	    if (Mode.getNumber() == 5 && Mode.getType().toString().equals("T")) mode.setText("5T");
        	    if (Mode.getNumber() == 5 && Mode.getType().toString().equals("D")) mode.setText("5D");
            }
    	    score.setText(gameManager.getScore());
        }
	}
	
	public void switchToMenu (ActionEvent event) {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(MSMenuApp.menuScene);
	    stage.show();
	}
	
	public void checkBGSound (ActionEvent event) {
		if (MSMenuApp.bgSound.getStatus() == MediaPlayer.Status.PLAYING) MSMenuApp.bgSound.pause();
		else MSMenuApp.bgSound.play();
	}
}