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
        primaryStage.setResizable(false);
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
        gameScene.setOnKeyPressed(event -> primaryStage.setResizable(false));
    }

    private void createPlayerSetupScene() {
        VBox setupLayout = new VBox(20);
        setupLayout.setAlignment(Pos.CENTER);
        setupLayout.setPadding(new Insets(20));

        Label modeLabel = new Label("Mode de jeu :");
        ComboBox<String> gameModeChoice = new ComboBox<>();
        gameModeChoice.getItems().addAll("Joueur contre Joueur", "Joueur contre IA");
        gameModeChoice.setValue("Joueur contre Joueur");

        TextField player1Name = new TextField();
        player1Name.setPromptText("Nom du Joueur 1");
        ComboBox<String> player1ColorChoice = new ComboBox<>();
        player1ColorChoice.getItems().addAll("Rouge", "Jaune", "Bleu", "Vert", "Orange", "Violet", "Rose");
        player1ColorChoice.setValue("Rouge");

        VBox player2Config = new VBox(10);
        TextField player2Name = new TextField();
        player2Name.setPromptText("Nom du Joueur 2");
        ComboBox<String> player2ColorChoice = new ComboBox<>();
        player2ColorChoice.getItems().addAll("Rouge", "Jaune", "Bleu", "Vert", "Orange", "Violet", "Rose");
        player2ColorChoice.setValue("Jaune");

        VBox aiConfig = new VBox(10);
        Label difficultyLabel = new Label("Niveau de difficulté de l'IA :");
        Slider difficultySlider = new Slider(1, 10, 5);
        difficultySlider.setShowTickLabels(true);
        difficultySlider.setShowTickMarks(true);
        difficultySlider.setMajorTickUnit(1);
        difficultySlider.setBlockIncrement(1);
        difficultySlider.setSnapToTicks(true);

        aiConfig.getChildren().addAll(difficultyLabel, difficultySlider);
        aiConfig.setVisible(false);

        gameModeChoice.setOnAction(e -> {
            boolean isAIMode = gameModeChoice.getValue().equals("Joueur contre IA");
            player2Config.setVisible(!isAIMode);
            aiConfig.setVisible(isAIMode);
        });

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
            String color1 = player1ColorChoice.getValue();
            boolean isAIMode = gameModeChoice.getValue().equals("Joueur contre IA");

            if (name1.isEmpty()) {
                errorLabel.setText("Le nom du Joueur 1 doit être rempli !");
                return;
            }

            player1 = new Player(name1, colorMap.get(color1));

            if (isAIMode) {
                int difficulty = (int) difficultySlider.getValue();
                player2 = new AI("IA", colorMap.get(player2ColorChoice.getValue()), difficulty);
            } else {
                String name2 = player2Name.getText().trim();
                if (name2.isEmpty()) {
                    errorLabel.setText("Le nom du Joueur 2 doit être rempli !");
                    return;
                }
                if (name1.equals(name2)) {
                    errorLabel.setText("Les joueurs doivent avoir des noms différents !");
                    return;
                }
                player2 = new Player(name2, colorMap.get(player2ColorChoice.getValue()));
            }

            if (player1.getColor().equals(player2.getColor())) {
                errorLabel.setText("Les joueurs doivent avoir des couleurs différentes !");
                return;
            }

            currentPlayer = player1;
            updateTurnLabel();
            primaryStage.setScene(gameScene);
        });

        setupLayout.getChildren().addAll(
            modeLabel, gameModeChoice,
            new Label("Détails du Joueur 1:"),
            player1Name,
            new Label("Choisir une couleur:"),
            player1ColorChoice,
            player2Config,
            aiConfig,
            errorLabel,
            startGameButton
        );

        playerSetupScene = new Scene(setupLayout, 800, 600);
        playerSetupScene.setOnKeyPressed(event -> primaryStage.setResizable(false));
    }

    private void createSettingsScene() {
        VBox settingsLayout = new VBox(20);
        settingsLayout.setAlignment(Pos.CENTER);

        Button backToMenuButton = new Button("Back to Menu");
        backToMenuButton.setOnAction(e -> primaryStage.setScene(menuScene));

        settingsLayout.getChildren().add(backToMenuButton);
        settingsScene = new Scene(settingsLayout, 800, 600);
        settingsScene.setOnKeyPressed(event -> primaryStage.setResizable(false));
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

        if (currentPlayer instanceof AI) {
            AI ai = (AI) currentPlayer;
            int aiMove = ai.chooseMove(gameBoard);
            handleMove(aiMove);
        }
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

    enum GameMode {
        PLAYER_VS_PLAYER,
        PLAYER_VS_AI
    }

    class AI extends Player {
        private int difficulty;

        public AI(String name, String color, int difficulty) {
            super(name, color);
            this.difficulty = Math.min(Math.max(difficulty, 1), 10);
        }

        public int getDifficulty() {
            return difficulty;
        }

        public int chooseMove(char[][] gameBoard) {
            if (difficulty <= 3) {
                return makeRandomMove(gameBoard);
            } else if (difficulty <= 6) {
                return makeMediumMove(gameBoard);
            } else {
                return makeHardMove(gameBoard);
            }
        }

        private int makeRandomMove(char[][] gameBoard) {
            List<Integer> validMoves = getValidMoves(gameBoard);
            return validMoves.get(new Random().nextInt(validMoves.size()));
        }

        private int makeMediumMove(char[][] gameBoard) {
            for (int col : getValidMoves(gameBoard)) {
                int row = getLowestEmptyRow(gameBoard, col);
                if (row != -1) {
                    gameBoard[row][col] = getColor().charAt(0);
                    boolean wins = checkWinForAI(gameBoard, row, col);
                    gameBoard[row][col] = ' ';
                    if (wins) return col;
                }
            }

            char adversaryColor = (getColor().equals("red")) ? 'y' : 'r';
            for (int col : getValidMoves(gameBoard)) {
                int row = getLowestEmptyRow(gameBoard, col);
                if (row != -1) {
                    gameBoard[row][col] = adversaryColor;
                    boolean blocks = checkWinForAI(gameBoard, row, col);
                    gameBoard[row][col] = ' ';
                    if (blocks) return col;
                }
            }

            return makeRandomMove(gameBoard);
        }

        private int makeHardMove(char[][] gameBoard) {
            for (int col : getValidMoves(gameBoard)) {
                int row = getLowestEmptyRow(gameBoard, col);
                if (row != -1) {
                    gameBoard[row][col] = getColor().charAt(0);
                    boolean wins = checkWinForAI(gameBoard, row, col);
                    gameBoard[row][col] = ' ';
                    if (wins) return col;
                }
            }

            char adversaryColor = (getColor().equals("red")) ? 'y' : 'r';
            for (int col : getValidMoves(gameBoard)) {
                int row = getLowestEmptyRow(gameBoard, col);
                if (row != -1) {
                    gameBoard[row][col] = adversaryColor;
                    boolean blocks = checkWinForAI(gameBoard, row, col);
                    gameBoard[row][col] = ' ';
                    if (blocks) return col;
                }
            }

            for (int col : getValidMoves(gameBoard)) {
                int row = getLowestEmptyRow(gameBoard, col);
                if (row != -1) {
                    gameBoard[row][col] = getColor().charAt(0);
                    if (hasDoubleThreat(gameBoard, getColor().charAt(0))) {
                        gameBoard[row][col] = ' ';
                        return col;
                    }
                    gameBoard[row][col] = ' ';
                }
            }

            List<Integer> validMoves = getValidMoves(gameBoard);
            int bestCol = validMoves.get(0);
            int bestScore = -1;

            for (int col : validMoves) {
                int score = evaluateMove(gameBoard, col);
                if (score > bestScore) {
                    bestScore = score;
                    bestCol = col;
                }
            }

            return bestCol;
        }

        private boolean hasDoubleThreat(char[][] board, char color) {
            int threats = 0;
            char[][] tempBoard = new char[ROWS][COLS];

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    tempBoard[i][j] = board[i][j];
                }
            }

            for (int col = 0; col < COLS; col++) {
                int row = getLowestEmptyRow(tempBoard, col);
                if (row != -1) {
                    tempBoard[row][col] = color;
                    if (checkWinForAI(tempBoard, row, col)) {
                        threats++;
                    }
                    tempBoard[row][col] = ' ';
                    if (threats >= 2) return true;
                }
            }
            return false;
        }

        private int evaluateMove(char[][] board, int col) {
            int score = 0;
            int row = getLowestEmptyRow(board, col);
            if (row == -1) return -1000;

            score += 7 - Math.abs(col - COLS/2);

            board[row][col] = getColor().charAt(0);

            char adversaryColor = (getColor().equals("red")) ? 'y' : 'r';
            if (row > 0) {
                board[row-1][col] = adversaryColor;
                if (checkWinForAI(board, row-1, col)) {
                    score -= 50;
                }
                board[row-1][col] = ' ';
            }

            score += countPartialAlignments(board, row, col, getColor().charAt(0));

            board[row][col] = ' ';

            return score;
        }

        private int countPartialAlignments(char[][] board, int row, int col, char color) {
            int count = 0;
            int[][] directions = {{0,1}, {1,0}, {1,1}, {1,-1}};

            for (int[] dir : directions) {
                int inLine = 1;
                for (int i = 1; i < 4; i++) {
                    int newRow = row + dir[0] * i;
                    int newCol = col + dir[1] * i;
                    if (isValidPosition(newRow, newCol) && board[newRow][newCol] == color) {
                        inLine++;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < 4; i++) {
                    int newRow = row - dir[0] * i;
                    int newCol = col - dir[1] * i;
                    if (isValidPosition(newRow, newCol) && board[newRow][newCol] == color) {
                        inLine++;
                    } else {
                        break;
                    }
                }
                if (inLine >= 2) count += inLine * 2;
            }
            return count;
        }

        private boolean isValidPosition(int row, int col) {
            return row >= 0 && row < ROWS && col >= 0 && col < COLS;
        }

        private List<Integer> getValidMoves(char[][] gameBoard) {
            List<Integer> validMoves = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                if (gameBoard[0][col] == ' ') {
                    validMoves.add(col);
                }
            }
            return validMoves;
        }

        private int getLowestEmptyRow(char[][] gameBoard, int col) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (gameBoard[row][col] == ' ') {
                    return row;
                }
            }
            return -1;
        }

        private boolean checkWinForAI(char[][] board, int row, int col) {
            for (int c = Math.max(0, col - 3); c <= Math.min(col, COLS - 4); c++) {
                if (board[row][c] != ' ' &&
                    board[row][c] == board[row][c + 1] &&
                    board[row][c] == board[row][c + 2] &&
                    board[row][c] == board[row][c + 3]) {
                    return true;
                }
            }

            for (int r = Math.max(0, row - 3); r <= Math.min(row, ROWS - 4); r++) {
                if (board[r][col] != ' ' &&
                    board[r][col] == board[r + 1][col] &&
                    board[r][col] == board[r + 2][col] &&
                    board[r][col] == board[r + 3][col]) {
                    return true;
                }
            }

            for (int r = 3; r < ROWS; r++) {
                for (int c = 0; c < COLS - 3; c++) {
                    if (board[r][c] != ' ' &&
                        board[r][c] == board[r - 1][c + 1] &&
                        board[r][c] == board[r - 2][c + 2] &&
                        board[r][c] == board[r - 3][c + 3]) {
                        return true;
                    }
                }
            }

            for (int r = 0; r < ROWS - 3; r++) {
                for (int c = 0; c < COLS - 3; c++) {
                    if (board[r][c] != ' ' &&
                        board[r][c] == board[r + 1][c + 1] &&
                        board[r][c] == board[r + 2][c + 2] &&
                        board[r][c] == board[r + 3][c + 3]) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
