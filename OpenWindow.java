import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.util.*;
import javafx.scene.paint.Color;
public class OpenWindow extends Application {
    private Stage primaryStage;
    private Scene menuScene;
    private Scene gameScene;
    private Scene settingsScene;
    private Scene playerSetupScene;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Button[][] gridButtons;
    private Label turnLabel;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private char[][] gameBoard;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Puissance 4");

        createMenuScene();
        createGameScene();
        createSettingsScene();

        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false); // Disable resizing
        primaryStage.show();
    }

    private void createMenuScene() {
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        Button playButton = new Button("Jouer");
        Button settingsButton = new Button("Paramètres");
        Button exitButton = new Button("Quitter");

        playButton.setOnAction(e -> primaryStage.setScene(playerSetupScene));
        settingsButton.setOnAction(e -> primaryStage.setScene(settingsScene));
        exitButton.setOnAction(e -> primaryStage.close());

        menuLayout.getChildren().addAll(playButton, settingsButton, exitButton);
        menuScene = new Scene(menuLayout, 800, 600);
    }

    private void createGameScene() {
        createPlayerSetupScene();

        HBox gameLayout = new HBox(20);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.setPadding(new Insets(20));

        VBox leftSide = new VBox(10);
        leftSide.setAlignment(Pos.CENTER);

        turnLabel = new Label();
        turnLabel.setStyle("-fx-font-size: 16px");

        GridPane gameGrid = new GridPane();
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.setHgap(5);
        gameGrid.setVgap(5);
        gameGrid.setStyle("-fx-background-color: blue; -fx-padding: 10;");

        gridButtons = new Button[ROWS][COLS];
        gameBoard = new char[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button cell = new Button();
                cell.setPrefSize(60, 60);
                cell.setStyle("-fx-background-color: white; -fx-background-radius: 30;");
                final int currentCol = col;
                cell.setOnAction(e -> handleMove(currentCol));
                gridButtons[row][col] = cell;
                gameBoard[row][col] = ' ';
                gameGrid.add(cell, col, row);
            }
        }

        leftSide.getChildren().addAll(turnLabel, gameGrid);

        VBox rightSide = new VBox(20);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(20));
        rightSide.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");

        Label player1Info = new Label();
        Label player2Info = new Label();
        Button backToMenuButton = new Button("Retour au menu principal");
        backToMenuButton.setOnAction(e -> {
            resetGame();
            primaryStage.setScene(menuScene);
        });

        rightSide.getChildren().addAll(player1Info, player2Info, backToMenuButton);

        gameLayout.getChildren().addAll(leftSide, rightSide);
        gameScene = new Scene(gameLayout, 800, 600);
        gameScene.setOnKeyPressed(event -> primaryStage.setResizable(false)); // Disable resizing
    }

    private void createPlayerSetupScene() {
        VBox setupLayout = new VBox(20);
        setupLayout.setAlignment(Pos.CENTER);
        setupLayout.setPadding(new Insets(20));

        TextField player1Name = new TextField();
        TextField player2Name = new TextField();
        player1Name.setPromptText("Nom du Joueur 1");
        player2Name.setPromptText("Nom du Joueur 2");

        // Création des ComboBox pour la sélection des couleurs
        ComboBox<String> player1ColorChoice = new ComboBox<>();
        ComboBox<String> player2ColorChoice = new ComboBox<>();

        // Ajout des couleurs disponibles
        player1ColorChoice.getItems().addAll(
            "Rouge", "Jaune", "Bleu", "Vert", "Orange", "Violet", "Rose"
        );
        player2ColorChoice.getItems().addAll(
            "Rouge", "Jaune", "Bleu", "Vert", "Orange", "Violet", "Rose"
        );

        // Sélection des couleurs par défaut
        player1ColorChoice.setValue("Rouge");
        player2ColorChoice.setValue("Jaune");

        // Map pour convertir les noms de couleurs en codes CSS
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("Rouge", "red");
        colorMap.put("Jaune", "yellow");
        colorMap.put("Bleu", "lightblue");
        colorMap.put("Vert", "lightgreen");
        colorMap.put("Orange", "orange");
        colorMap.put("Violet", "purple");
        colorMap.put("Rose", "pink");

        Button startGameButton = new Button("Commencer la partie");
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        startGameButton.setOnAction(e -> {
            String name1 = player1Name.getText().trim();
            String name2 = player2Name.getText().trim();
            String color1 = player1ColorChoice.getValue();
            String color2 = player2ColorChoice.getValue();

            if (name1.isEmpty() || name2.isEmpty()) {
                errorLabel.setText("Tous les champs doivent être remplis !");
                return;
            }

            if (name1.equals(name2)) {
                errorLabel.setText("Les joueurs doivent avoir des noms différents !");
                return;
            }

            if (color1.equals(color2)) {
                errorLabel.setText("Les joueurs doivent avoir des couleurs différentes !");
                return;
            }

            player1 = new Player(name1, colorMap.get(color1));
            player2 = new Player(name2, colorMap.get(color2));
            currentPlayer = player1;
            updateTurnLabel();
            primaryStage.setScene(gameScene);
        });

        setupLayout.getChildren().addAll(
            new Label("Détails du Joueur 1:"),
            player1Name,
            new Label("Choisir une couleur:"),
            player1ColorChoice,
            new Label("Détails du Joueur 2:"),
            player2Name,
            new Label("Choisir une couleur:"),
            player2ColorChoice,
            errorLabel,
            startGameButton
        );

        playerSetupScene = new Scene(setupLayout, 800, 600);
        playerSetupScene.setOnKeyPressed(event -> primaryStage.setResizable(false)); // Disable resizing
    }

    private void createSettingsScene() {
        VBox settingsLayout = new VBox(20);
        settingsLayout.setAlignment(Pos.CENTER);

        Button backToMenuButton = new Button("Back to Menu");
        backToMenuButton.setOnAction(e -> primaryStage.setScene(menuScene));

        settingsLayout.getChildren().add(backToMenuButton);
        settingsScene = new Scene(settingsLayout, 800, 600);
        settingsScene.setOnKeyPressed(event -> primaryStage.setResizable(false)); // Disable resizing
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleMove(int col) {
        int row = getLowestEmptyRow(col);
        if (row == -1) return;

        gameBoard[row][col] = currentPlayer.getColor().charAt(0);
        Button button = gridButtons[row][col];
        button.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 30;",
            currentPlayer.getColor().toLowerCase()));

        if (checkWin(row, col)) {
            showGameOverDialog(currentPlayer.getName() + " wins!");
            return;
        }

        if (isBoardFull()) {
            showGameOverDialog("It's a draw!");
            return;
        }

        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        updateTurnLabel();
    }

    private void showGameOverDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin de la partie");
        alert.setHeaderText(message);
        alert.setContentText("Que souhaitez-vous faire ?");

        ButtonType newGame = new ButtonType("Nouvelle partie");
        ButtonType mainMenu = new ButtonType("Menu principal");
        ButtonType exit = new ButtonType("Quitter");

        alert.getButtonTypes().setAll(newGame, mainMenu, exit);

        alert.showAndWait().ifPresent(response -> {
            if (response == newGame) {
                resetGame();
                primaryStage.setScene(playerSetupScene);
            } else if (response == mainMenu) {
                resetGame();
                primaryStage.setScene(menuScene);
            } else if (response == exit) {
                primaryStage.close();
            }
        });
    }

    private int getLowestEmptyRow(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (gameBoard[row][col] == ' ') {
                return row;
            }
        }
        return -1;
    }

    private boolean checkWin(int row, int col) {
        return checkHorizontal(row) || checkVertical(col) ||
               checkDiagonal1() || checkDiagonal2();
    }

    private boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (gameBoard[0][col] == ' ') return false;
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                gameBoard[row][col] = ' ';
                gridButtons[row][col].setStyle("-fx-background-color: white; -fx-background-radius: 30;");
            }
        }
        currentPlayer = player1;
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        turnLabel.setText("Tour actuel : " + currentPlayer.getName());
    }

    class Player {
        private String name;
        private String color;

        public Player(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public String getName() { return name; }
        public String getColor() { return color; }
    }

    private boolean checkHorizontal(int row) {
        for (int col = 0; col <= COLS - 4; col++) {
            if (gameBoard[row][col] != ' ' &&
                gameBoard[row][col] == gameBoard[row][col + 1] &&
                gameBoard[row][col] == gameBoard[row][col + 2] &&
                gameBoard[row][col] == gameBoard[row][col + 3]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical(int col) {
        for (int row = 0; row <= ROWS - 4; row++) {
            if (gameBoard[row][col] != ' ' &&
                gameBoard[row][col] == gameBoard[row + 1][col] &&
                gameBoard[row][col] == gameBoard[row + 2][col] &&
                gameBoard[row][col] == gameBoard[row + 3][col]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal1() {
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (gameBoard[row][col] != ' ' &&
                    gameBoard[row][col] == gameBoard[row + 1][col + 1] &&
                    gameBoard[row][col] == gameBoard[row + 2][col + 2] &&
                    gameBoard[row][col] == gameBoard[row + 3][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal2() {
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = COLS - 1; col >= 3; col--) {
                if (gameBoard[row][col] != ' ' &&
                    gameBoard[row][col] == gameBoard[row + 1][col - 1] &&
                    gameBoard[row][col] == gameBoard[row + 2][col - 2] &&
                    gameBoard[row][col] == gameBoard[row + 3][col - 3]) {
                    return true;
                }
            }
        }
        return false;
    }
}
