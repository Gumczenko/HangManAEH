import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HangmanGame extends JFrame {
    private String[] words = {"studia", "uczelnia", "wisielec", "parabola", "sesja",
            "salka", "kalambury", "deklaracja", "legitymacja", "algorytm", "programowanie"};
    private String word = words[new Random().nextInt(words.length)];
    private int remainingGuesses;
    private char[] wordDisplayArray;
    private JLabel wordDisplay;
    private JLabel remainingGuessesLabel;
    private JLabel winLabel;
    private JLabel lossLabel;
    private JTextField guessInput;
    private JTextArea guessHistory;
    private JButton guessButton;
    private String guessHistoryText = "";
    private Set<Character> guessedLetters = new HashSet<>();
    private int difficulty;
    private int winCount = 0;
    private int lossCount = 0;
    private static final String HISTORY_FILE = "game_history.txt";
    private int width = 400;
    private int height = 600;

    public HangmanGame(int difficulty) {
        this.difficulty = difficulty;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Wisielec AEH");

        loadGameHistory();
        setDifficulty(difficulty);
        initializeWordDisplayArray();

        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                saveGameHistory();
            }
        });

        pack();
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.CYAN);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        wordDisplay = new JLabel(getFormattedWordDisplay(), SwingConstants.CENTER);
        wordDisplay.setFont(new Font("Arial", Font.BOLD, 16));
        wordDisplay.setOpaque(true);
        wordDisplay.setBackground(Color.WHITE);
        mainPanel.add(wordDisplay, gbc);

        JPanel inputPanel = new JPanel();
        guessInput = new JTextField(5);
        guessInput.setFont(new Font("Arial", Font.BOLD, 12));
        guessInput.setBackground(new Color(238, 238, 238));
        guessInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (guessInput.getText().length() >= 1 || !Character.isLetter(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        guessButton = new JButton("Zatwierdź");
        guessButton.setFont(new Font("Arial", Font.BOLD, 12));
        guessButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        guessButton.addActionListener(e -> makeGuess());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.add(guessInput);
        inputPanel.add(guessButton);
        mainPanel.add(inputPanel, gbc);

        getRootPane().setDefaultButton(guessButton);

        remainingGuessesLabel = new JLabel("Pozostałe próby: " + remainingGuesses);
        remainingGuessesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(remainingGuessesLabel, gbc);

        winLabel = new JLabel("Wygrane: " + winCount);
        winLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(winLabel, gbc);

        lossLabel = new JLabel("Przegrane: " + lossCount);
        lossLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(lossLabel, gbc);

        guessHistory = new JTextArea(5, 10);
        guessHistory.setFont(new Font("Arial", Font.BOLD, 12));
        guessHistory.setBackground(new Color(238, 238, 238));
        guessHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(guessHistory);
        scrollPane.setPreferredSize(new Dimension(150, 100));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Użyte litery"));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        mainPanel.add(scrollPane, gbc);

        return mainPanel;
    }

    private void initializeWordDisplayArray() {
        wordDisplayArray = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordDisplayArray[i] = '_';
        }
    }

    private String getFormattedWordDisplay() {
        StringBuilder formattedDisplay = new StringBuilder();
        for (char c : wordDisplayArray) {
            formattedDisplay.append(c).append(" ");
        }
        return formattedDisplay.toString().trim();
    }

    private void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 1: remainingGuesses = 10; break;
            case 2: remainingGuesses = 7; break;
            case 3: remainingGuesses = 5; break;
            default: remainingGuesses = 7; break;
        }
    }

    private void makeGuess() {
        String guess = guessInput.getText().trim().toLowerCase();
        if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
            char guessedChar = guess.charAt(0);
            if (guessedLetters.contains(guessedChar)) {
                JOptionPane.showMessageDialog(this, "Ta litera została już użyta.");
                return;
            }
            guessedLetters.add(guessedChar);

            boolean correct = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guessedChar) {
                    wordDisplayArray[i] = guessedChar;
                    correct = true;
                }
            }
            if (!correct) {
                remainingGuesses--;
                guessHistoryText += guessedChar + "\n";
            }
            updateDisplay();

            if (remainingGuesses <= 0) {
                endGame(false);
            } else if (word.equals(new String(wordDisplayArray))) {
                endGame(true);
            }
        }
        guessInput.setText("");
    }

    private void updateDisplay() {
        guessHistory.setText(guessHistoryText);
        wordDisplay.setText(getFormattedWordDisplay());
        remainingGuessesLabel.setText("Pozostałe próby: " + remainingGuesses);
    }

    private void endGame(boolean won) {
        if (won) {
            JOptionPane.showMessageDialog(this, "Gratulacje, wygrałeś!");
            winCount++;
            winLabel.setText("Wygrane: " + winCount);
        } else {
            JOptionPane.showMessageDialog(this, "Koniec gry! Hasło to: " + word);
            lossCount++;
            lossLabel.setText("Przegrane: " + lossCount);
        }
        askToPlayAgain();
    }

    private void askToPlayAgain() {
        String[] options = {"✅", "❌"};
        int playAgain = JOptionPane.showOptionDialog(this, "Chcesz zagrać jeszcze raz?", "Koniec Gry",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (playAgain == JOptionPane.YES_OPTION) {
            selectDifficulty();
        } else {
            saveGameHistory();
            System.exit(0);
        }
    }

    private void selectDifficulty() {
        Object[] options = {"Łatwy", "Średni", "Trudny"};
        int difficulty = JOptionPane.showOptionDialog(this, "Wybierz poziom trudności:", "Wisielec poziom trudności",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (difficulty == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        } else {
            this.difficulty = difficulty + 1;
            resetGame();
        }
    }

    private void resetGame() {
        word = words[new Random().nextInt(words.length)];
        initializeWordDisplayArray();
        guessedLetters.clear();
        guessHistoryText = "";
        setDifficulty(this.difficulty);
        updateDisplay();
        guessInput.setText("");
        setSize(this.width, this.height);
    }

    private void saveGameHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            writer.write(winCount + "\n");
            writer.write(lossCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGameHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            winCount = Integer.parseInt(reader.readLine());
            lossCount = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            winCount = 0;
            lossCount = 0;
        }
    }

    public static void main(String[] args) {
        Object[] options = {"Łatwy", "Średni", "Trudny"};
        int difficulty = JOptionPane.showOptionDialog(null, "Wybierz poziom trudności:", "Wisielec poziom trudności",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (difficulty == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        } else {
            new HangmanGame(difficulty + 1);
        }
    }
}