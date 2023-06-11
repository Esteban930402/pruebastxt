package resources;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class filesManager {
    public List<String> getRandomLines(String explorerID, int level) throws IOException{
        List<String> lineas= new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(explorerID))){
            String linea;
            while ((linea=br.readLine())!=null){
                lineas.add(linea);

            }
        }
        List<String> randomLines = new ArrayList<>();
        Random random = new Random();
        int totalLines= lineas.size();
        int wordsAmmount=0;
        switch (level){
            case 1:
                wordsAmmount=20;
                break;
            case 2:
                wordsAmmount=40;
                break;
            case 3:
                wordsAmmount=50;
                break;
            case 4:
                wordsAmmount=60;
                break;
            case 5:
                wordsAmmount=70;
                break;
            case 6:
                wordsAmmount=80;
                break;
            case 7:
                wordsAmmount=100;
                break;
            case 8:
                wordsAmmount=120;
                break;
            case 9:
                wordsAmmount=140;
                break;
            case 10:
                wordsAmmount=200;
                break;
        }


        for (int i=0;i<wordsAmmount;i++){
            int randomI = random.nextInt(totalLines);
            randomLines.add(lineas.get(randomI));
        }
        return randomLines;
    }
    public int manageName(String explorerID, String name){
        int ronda = 0;
        try {
            File file = new File(explorerID);
            Scanner scanner = new Scanner(file);

            boolean search=false;
            int getLevel = 0;
            int firstLevel =1;

            while(scanner.hasNextLine()){
                String linea2 = scanner.nextLine();
                String[] partes = linea2.split(":");

                if(partes.length==2 && partes[0].equals(name)){
                    search=true;
                    getLevel=Integer.parseInt(partes[1]);
                    break;
                }
            }
            scanner.close();
            if (search){
                ronda= getLevel;
                System.out.println("El nombre"+name+"Se encontro en el nivel: "+getLevel);
            } else {
                FileWriter fileWriter = new FileWriter(explorerID,true);
                fileWriter.write(name+":"+firstLevel);
                fileWriter.write(System.lineSeparator());
                fileWriter.close();
                System.out.println("El nombre : "+name+"Fue agregado con el nivel incial");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ronda;
    }
    public void updateLevel(String explorerID,String name, int newlevel){
        try {
            File file = new File(explorerID);
            List<String> lines = new ArrayList<>();

            try (Scanner scanner = new Scanner(file)){
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");

                    if (parts.length ==2 && parts[0].equals(name)){
                        line = parts[0]+":"+ newlevel;
                    }
                    lines.add(line);
                }
            }

            try (FileWriter fileWriter = new FileWriter(file)){
                for (String line : lines){
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
