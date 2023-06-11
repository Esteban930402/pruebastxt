package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mainMenu extends JFrame  {
    private Timer timer;
    private Timer wordTimer;
    private String name;
    private boolean trueOrFalse;
    private JButton initGame,rules,yesButton,noButton;

    private JPanel principalPanel,buttonPanel,textPanel,counterPanel;
    private JTextField playerUsername;

    private Escucha escucha;
    private int comparer=0;
    private JLabel textTimer;
    private List<String> wordsToMemorize;
    private List<String> theOtherWords;
    private List<String> selectdWords;
    private List<String> noWords;


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
        name = new String();

        timer = new Timer(500, new ActionListener() {
            private int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter < wordsToMemorize.size()) {
                    String palabra = wordsToMemorize.get(counter);
                    textTimer.setText(palabra);
                    counter++;
                } else {
                    timer.stop();
                    showWordsAndValidate();
                }
            }
        });


        textTimer = new JLabel("HOLA");

        yesButton=new JButton("Yes");
        yesButton.setBackground(Color.green);
        yesButton.setOpaque(true);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordTimer.isRunning()){
                    String validar = textTimer.getText();
                    if (wordsToMemorize.contains(validar)){
                            comparer++;
                            System.out.println(comparer);
                    }
                    selectdWords.add(validar);
                    System.out.println(selectdWords);
                }
            }
        });

        noButton=new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordTimer.isRunning()){
                    System.out.println("Se presiono  No");
                }
            }
        });
        noButton.setBackground(Color.red);
        noButton.setOpaque(true);

        buttonPanel = new JPanel();
        textPanel = new JPanel();
        counterPanel= new JPanel();

        counterPanel.add(yesButton);
        counterPanel.add(noButton);


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

        principalPanel.setLayout(new BorderLayout());
        principalPanel.add(buttonPanel,BorderLayout.SOUTH);
        principalPanel.add(playerUsername,BorderLayout.CENTER);
        principalPanel.add(textPanel,BorderLayout.NORTH);

        this.add(principalPanel);
        pack();
    }

    public void showWordsAndValidate() {
        List<String> palabrasMezcladas = new ArrayList<>();
        palabrasMezcladas.addAll(wordsToMemorize);
        palabrasMezcladas.addAll(theOtherWords);
        System.out.println(palabrasMezcladas);
        Collections.shuffle(palabrasMezcladas);
        System.out.println(palabrasMezcladas);


        JOptionPane.showMessageDialog(null, "Select Yes if the word was in the ones shown above, if it was not found press No, you have 5 seconds to do it");
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        wordTimer = new Timer(2000, new ActionListener() {
            private int counter =0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==yesButton){
                    yesButton.setEnabled(false);
                }
                yesButton.setEnabled(true);
                noButton.setEnabled(true);
                if (counter<palabrasMezcladas.size()){
                    yesButton.setEnabled(true);
                    String Word = palabrasMezcladas.get(counter);
                    textTimer.setText(Word);
                    counter++;
                }else {
                    wordTimer.stop();
                    yesButton.setEnabled(false);
                    noButton.setEnabled(false);
                    int palabrasAmemorizar= wordsToMemorize.size();
                    int porcentaje = (comparer*100)/palabrasAmemorizar;
                    System.out.println("Porcentaje de coincidencia: " + porcentaje + "%");


                }
            }
        });
        wordTimer.start();

    }

    public void initGame(){

        selectdWords = new ArrayList<>();

        principalPanel.removeAll();
        principalPanel.revalidate();
        principalPanel.repaint();


        principalPanel.add(textPanel,BorderLayout.CENTER);
        principalPanel.add(counterPanel,BorderLayout.SOUTH);
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        principalPanel.repaint();
        principalPanel.revalidate();

        filesManager filesManager = new filesManager();
        String database = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\database.txt";
        String file = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\words.txt";
        name = playerUsername.getText();
        filesManager.manageName(database,name);
        int level = filesManager.manageName(database,name);

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
            }
        }
    }
 }