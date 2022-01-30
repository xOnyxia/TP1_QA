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

        int classe_LOC = 0;
        int classe_CLOC = 0;
        long classe_DC = 0;

        public void classeLOC(File file){
            try {
                Scanner myReader = new Scanner(file);

                while (myReader.hasNextLine()) {
                    //si pas vide
                    String line = myReader.nextLine();
                    if (!line.trim().isEmpty()) {
                        //si commence par un comment
                        if (line.trim().length()>=2 && line.trim().substring(0, 2).equals("//")) {
                            //myReader.nextLine();
                            //do nada
                        } else {
                            if ((line.trim().length()>=2 && line.trim().substring(0, 2).equals("/*")) || (line.trim().length()>=3 && line.trim().substring(0, 3).equals("/**"))) {
                                //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                                while (myReader.hasNextLine() && !line.contains("*/")) {
                                   line = myReader.nextLine();
                                }
                                //aller a la ligne apres celle de fin de commentaire
                               // myReader.nextLine();
                            }
                            //soit une ligne de code avec commentaire ou juste code
                            else {
                                //ligne de code avec commentaire
                                if (line.contains("/*") || line.contains("/**")) {
                                    ++classe_LOC;
                                    while (myReader.hasNextLine() && !line.contains("*/")) {
                                        line = myReader.nextLine();
                                    }
                                  //  myReader.nextLine();

                                }
                                //juste du code
                                else {
                                    ++classe_LOC;
                                }
                            }
                        }
                    }
                    //ligne vide
                    else {
                        //do nada
                    }
                }
                myReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        public void classeCLOC(){
           // Scanner myReader = new Scanner(myObj);

        }

        public void classeDC(){
            if(classe_LOC!=0) {
                classe_DC = classe_CLOC / classe_LOC;
            }
            else {
                System.err.println("Division par 0");
            }
        }
    }
