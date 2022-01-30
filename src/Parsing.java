import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parsing {

    // classe_LOC : nombre de lignes de code d’une classe
    // paquet_LOC : nombre de lignes de code d’un paquet (java package) -- la somme des LOC de ses classes
    // classe_CLOC : nombre de lignes de code d’une classe qui contiennent des commentaires
    // paquet_CLOC : nombre de lignes de code d’un paquet qui contiennent des commentaires
    //paquet_DC : densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
    //paquet_DC : densité de commentaires pour un paquet : paquet_DC = paquet_CLOC / paquet_LOC

        float classe_LOC = 0;
        float classe_CLOC = 0;
        float classe_DC = 0;

        public void classeLOC(File file){
            try {
                Scanner myReader = new Scanner(file);

                while (myReader.hasNextLine()) {
                    //si pas vide
                    String line = myReader.nextLine();
                    if (!line.trim().isEmpty()) {
                        //si commence par un comment
                        if (line.trim().length()>=2 && line.trim().substring(0, 2).equals("//")) {
                            //do nada
                        }
                        else {
                            if ((line.trim().length()>=2 && line.trim().substring(0, 2).equals("/*")) || (line.trim().length()>=3 && line.trim().substring(0, 3).equals("/**"))) {
                                int count = 1;
                                //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                                while(myReader.hasNextLine() && count!=0){
                                    line = myReader.nextLine();
                                    if(line.contains("/*") || line.contains("/**")){
                                        ++count;
                                    }
                                    if(line.contains("*/")){
                                        --count;
                                    }
                                }
                            }
                            //soit une ligne de code avec commentaire ou juste code
                            else {
                                //ligne de code avec commentaire
                                if (line.contains("/*") || line.contains("/**")) {
                                    ++classe_LOC;
                                    int count = 1;
                                    while(myReader.hasNextLine() && count!=0){
                                        line = myReader.nextLine();
                                        if(line.contains("/*") || line.contains("/**")){
                                            ++count;
                                        }
                                        if(line.contains("*/")){
                                            --count;
                                        }
                                    }
                                }
                                //juste du code
                                else {
                                    ++classe_LOC;
                                }
                            }
                        }
                    }
                    //ligne vide
                }
                myReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        public void classeCLOC(File file){
            try {
                Scanner myReader = new Scanner(file);

                while (myReader.hasNextLine()) {
                    //si pas vide
                    String line = myReader.nextLine();
                    if (!line.trim().isEmpty()) {
                        //si commence par un comment
                        if (line.trim().length()>=2 && line.trim().substring(0, 2).equals("//")) {
                            ++classe_CLOC;
                        } else {
                            if ((line.trim().length()>=2 && line.trim().substring(0, 2).equals("/*")) || (line.trim().length()>=3 && line.trim().substring(0, 3).equals("/**"))) {
                                //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                                int count = 1;
                                while(myReader.hasNextLine() && count!=0) {
                                    ++classe_CLOC;
                                    line = myReader.nextLine();
                                    if (line.contains("/*") || line.contains("/**")) {
                                        ++count;
                                    }
                                    if (line.contains("*/")) {
                                        --count;
                                    }
                                }
                                ++classe_CLOC;
                            }
                            //soit une ligne de code avec commentaire ou juste code
                            else {
                                //ligne de code avec commentaire
                                if (line.contains("/*") || line.contains("/**")) {
                                    ++classe_CLOC;
                                    int count = 1;
                                    while(myReader.hasNextLine() && count!=0){
                                        ++classe_CLOC;
                                        line = myReader.nextLine();
                                        if(line.contains("/*") || line.contains("/**")){
                                            ++count;
                                        }
                                        if(line.contains("*/")){
                                            --count;
                                        }
                                    }
                                    ++classe_CLOC;
                                }
                                else {
                                    //avec //
                                    if(line.contains("//")){
                                        ++classe_CLOC;
                                    }
                                    //juste du code
                                }
                            }
                        }
                    }
                    //ligne vide
                }
                myReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        public void classeDC(){
            if(classe_LOC!=0) {
                classe_DC = classe_CLOC/classe_LOC;
            }
            else {
                System.err.println("Division par 0");
            }
        }
    }
