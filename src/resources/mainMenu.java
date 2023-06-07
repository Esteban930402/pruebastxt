package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainMenu extends JFrame  {
    private void startTimer(){
        timer.start();
    }
    private int estado=0;
    private Header headerProject;
    private JButton initGame,rules,yesButton,noButton;

    private JPanel principalPanel,buttonPanel,textPanel,counterPanel;
    private JTextField playerUsername;

    private Escucha escucha;
    private int counter=0;
    private JLabel textTimer;
    List<String> wordsToMemorize;
    List<String> theOtherWords;
    Timer timer;



    public mainMenu(){
        initGUI();
        setIconImage(new ImageIcon(getClass().getResource("/resources/Imagen1.jpg")).getImage());
        //Default JFrame configuration
        this.setLayout(new BorderLayout());
        this.setTitle("I Know That Word");
        this.setSize(1020,720);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        timer = new Timer(1000, escucha);

        textTimer = new JLabel("HOLA");

        yesButton=new JButton("Yes");
        yesButton.setBackground(Color.green);
        yesButton.setOpaque(true);

        noButton=new JButton("No");
        noButton.setBackground(Color.red);
        noButton.setOpaque(true);

        buttonPanel = new JPanel();
        textPanel = new JPanel();
        counterPanel= new JPanel();


        imagePanel backgroundImage = new imagePanel("/resources/GuiFiles/mainMenuBackground.jpg");
        backgroundImage.setLayout(new BorderLayout());

        wordsToMemorize = new ArrayList<>();
        theOtherWords = new ArrayList<>();

        escucha = new Escucha();

        playerUsername = new JTextField(null,20);
        playerUsername.setHorizontalAlignment(JTextField.CENTER);

        principalPanel = new JPanel();

        initGame = new JButton("Iniciar Juego");
        initGame.addActionListener(escucha);

        rules = new JButton("Reglas");
        rules.addActionListener(escucha);

        buttonPanel.add(initGame);
        buttonPanel.add(rules);
        textPanel.add(textTimer);


        //principalPanel.add(playerUsername,BorderLayout.CENTER);
        //principalPanel.add(buttonPanel,BorderLayout.SOUTH);
        //principalPanel.add(textPanel,BorderLayout.NORTH);
        this.add(playerUsername,BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(textPanel,BorderLayout.NORTH);
        pack();
    }

    public void GUIInGame(){

    }

    public void initGame(){


        principalPanel.removeAll();
        principalPanel.revalidate();
        principalPanel.repaint();


        principalPanel.add(textPanel);
        principalPanel.add(counterPanel);
        principalPanel.repaint();
        principalPanel.revalidate();

        filesManager filesManager = new filesManager();
        String database = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\database.txt";
        String file = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\words.txt";
        String nombre = playerUsername.getText();
        filesManager.manageName(database,nombre);
        int level = filesManager.manageName(database,nombre);

        try {
            List<String> randomLines = filesManager.getRandomLines(file, level);

            for (String linea : randomLines) {
                // System.out.println(linea);
            }
            int totalLines=randomLines.size();
            int ammountToMemorize=(randomLines.size()/2);
            System.out.println(ammountToMemorize);
            wordsToMemorize=randomLines.subList(0,ammountToMemorize);
            theOtherWords=randomLines.subList(ammountToMemorize,totalLines);

            System.out.println(wordsToMemorize+"Palabra a memorizar\n");
            System.out.println(theOtherWords+"NO ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       timer.start();

    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            mainMenu miProjectGUI = new mainMenu();
        });
    }

    private class Escucha implements ActionListener {
        private int counter = 0; // Agrega una variable de contador

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == initGame) {
                initGame();
                counter = 0; // Reinicia el contador al iniciar el juego
                timer.start();
            }
            if (e.getSource() == timer) {
                System.out.println("El timer está corriendo? " + String.valueOf(timer.isRunning()));
                if (counter < wordsToMemorize.size()) {
                    String palabra = wordsToMemorize.get(counter);
                    textTimer.setText(palabra);
                    counter++; // Incrementa el contador para obtener la siguiente palabra en la siguiente ejecución
                } else {
                    timer.stop();
                }
            }
        }
    }
 }