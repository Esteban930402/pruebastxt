package resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mainMenu extends JFrame  {
    private Timer timer;
    private Timer wordTimer;
    private String name, database,file; //String name se usara para almacenar el nombre de usuario ingresado,
                                        //database almacenara la direccion del txt que sera utilizado para la base de dados
                                        //file, se almacenara la direccion del txt donde estan las palabras que se utilizaran en el programa
    private int level;
    private JButton initGame,rules,yesButton;
    private Image backgroundTest,backgroundTest2;

    private JPanel principalPanel,buttonPanel,textPanel,counterPanel,backgroundPanel,inGameBackgroundPanel;
    private JTextField playerUsername;
    private Escucha escucha;
    private int comparer=0;
    private JLabel textTimer;
    private List<String> wordsToMemorize;
    private List<String> theOtherWords;
    private List<String> selectdWords;

    public mainMenu(){
        initGUI();
        setIconImage(new ImageIcon(getClass().getResource("/resources/Imagen1.jpg")).getImage());
        //Default JFrame configuration
        this.setTitle("I Know That Word");
        this.setSize(1020,720);
        this.getContentPane();
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        backgroundPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if(backgroundTest!=null){
                    //int x = (getWidth()-backgroundTest.getWidth(null))/2;
                    //int y = (getHeight()-backgroundTest.getHeight(null))/2;
                    g.drawImage(backgroundTest,0,0,getWidth(),getHeight(),null);
                }

            }
        };
        backgroundPanel.setPreferredSize(new Dimension(1400,1080));

        //Creacion de hilo

        Thread hilo= new Thread(){
            @Override
            public void run(){
                try {
                    backgroundTest= ImageIO.read(getClass().getResource("/resources/12.jpg"));
                    backgroundPanel.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        hilo.start();



        file= new String();
        database = new String();
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

        textTimer = new JLabel("I KNOW THAT WORD!!!");
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

        /*noButton=new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wordTimer.isRunning()){
                    System.out.println("Se presiono  No");
                }
            }
        });
        noButton.setBackground(Color.red);
        noButton.setOpaque(true);*/

        this.buttonPanel = new JPanel();
        this.textPanel = new JPanel();
        this.counterPanel= new JPanel();

        this.counterPanel.add(yesButton);

        imagePanel backgroundImage = new imagePanel("/resources/GuiFiles/mainMenuBackground.jpg");
        backgroundImage.setLayout(new BorderLayout());

        this.wordsToMemorize = new ArrayList<>();
        this.theOtherWords = new ArrayList<>();

        this.escucha = new Escucha();

        this.playerUsername = new JTextField(null,20);
        this.playerUsername.setHorizontalAlignment(JTextField.CENTER);

        this.principalPanel = new JPanel();

        this.initGame = new JButton("Iniciar Juego");
        this.initGame.addActionListener(escucha);

        this.rules = new JButton("Reglas");
        this.rules.addActionListener(escucha);

        this.buttonPanel.add(playerUsername);
        this.buttonPanel.add(initGame);
        this.buttonPanel.add(rules);

        this.backgroundPanel.setLayout(new BorderLayout());
        this.principalPanel.add(buttonPanel,BorderLayout.SOUTH);


        this.backgroundPanel.add(principalPanel,BorderLayout.SOUTH);
        getContentPane().add(backgroundPanel);
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
        wordTimer = new Timer(500, new ActionListener() {
            private int counter =0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==yesButton){
                    yesButton.setEnabled(false);
                }
                yesButton.setEnabled(true);
                if (counter<palabrasMezcladas.size()){
                    yesButton.setEnabled(true);
                    String Word = palabrasMezcladas.get(counter);
                    textTimer.setText(Word);
                    counter++;
                }else {
                    wordTimer.stop();
                    yesButton.setEnabled(false);
                    int palabrasAmemorizar= wordsToMemorize.size();
                    int porcentaje = (comparer*100)/palabrasAmemorizar;
                    System.out.println("Porcentaje de coincidencia: " + porcentaje + "%");
                    int porcentajeParaAvanzar=0;
                    switch (level){
                        case 1:
                            porcentajeParaAvanzar=70;
                            break;
                        case 2:
                            porcentajeParaAvanzar=70;
                            break;
                        case 3:
                            porcentajeParaAvanzar=75;
                            break;
                        case 4:
                            porcentajeParaAvanzar=80;
                            break;
                        case 5:
                            porcentajeParaAvanzar=80;
                            break;
                        case 6:
                            porcentajeParaAvanzar=85;
                            break;
                        case 7:
                            porcentajeParaAvanzar=90;
                            break;
                        case 8:
                            porcentajeParaAvanzar=90;
                            break;
                        case 9:
                            porcentajeParaAvanzar=95;
                            break;
                        case 10:
                            porcentajeParaAvanzar=100;
                            break;
                    }
                    System.out.println(porcentajeParaAvanzar);
                    if (porcentaje>=porcentajeParaAvanzar){
                        filesManager filesManager = new filesManager();
                        int currentLevel=filesManager.manageName(database,name);
                        int newLevel= currentLevel+1;
                        filesManager.updateLevel(database,name,newLevel);
                        JOptionPane.showMessageDialog(null,"Congratulations, press ok if you are ready for the next level");
                        initGame();
                    }
                }
            }
        });
        wordTimer.start();
    }

    public void initGame(){
        inGameBackgroundPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if(backgroundTest2!=null){
                    //int x = (getWidth()-backgroundTest.getWidth(null))/2;
                    //int y = (getHeight()-backgroundTest.getHeight(null))/2;
                    g.drawImage(backgroundTest2,0,0,getWidth(),getHeight(),null);
                }

            }
        };
        inGameBackgroundPanel.setPreferredSize(new Dimension(1400,1080));

        //Creacion de hilo

        Thread hilo2= new Thread(){
            @Override
            public void run(){
                try {
                    backgroundTest2= ImageIO.read(getClass().getResource("/resources/mainMenuBackground.jpg"));
                    inGameBackgroundPanel.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        hilo2.start();

        selectdWords = new ArrayList<>();
        getContentPane().removeAll();
        getContentPane().revalidate();
        getContentPane().repaint();
        principalPanel.removeAll();
        principalPanel.repaint();
        principalPanel.revalidate();
        //principalPanel.setLayout(new BorderLayout());
        //textPanel.add(textTimer);
        textTimer.setBounds(420,320,100,30);
        textTimer.setFont(textTimer.getFont().deriveFont(Font.ITALIC,60));
        yesButton.setBounds(500,320,500,500);
        inGameBackgroundPanel.add(yesButton);
        //principalPanel.add(textPanel,BorderLayout.NORTH);
        //principalPanel.add(yesButton,BorderLayout.SOUTH);
        inGameBackgroundPanel.add(textTimer);


        getContentPane().add(inGameBackgroundPanel);
        getContentPane().repaint();
        getContentPane().revalidate();
        //backgroundPanel.removeAll();
        //backgroundPanel.repaint();
        //backgroundPanel.revalidate();
        //principalPanel.removeAll();
        //principalPanel.revalidate();
        //principalPanel.repaint();
        //principalPanel.add(counterPanel);
        yesButton.setEnabled(false);
        principalPanel.repaint();
        principalPanel.revalidate();
        filesManager filesManager = new filesManager();
        database = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\database.txt";
        file = "C:\\Users\\Owner\\IdeaProjects\\pruebastxt\\src\\resources\\words.txt";
        name = playerUsername.getText();
        filesManager.manageName(database,name);
        level = filesManager.manageName(database,name);
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
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == initGame) {
                initGame();
            }
        }
    }
 }