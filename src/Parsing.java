import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Parsing {

    final String singleComment = "//";
    final String javaDocComment = "/**";
    final String multiLineComment = "/*";
    final String commentEnder = "*/";

    // TODO : ajouter constante pour String if et while.
    // TODO : méthode pour mesurer WMC (complexité cyclomatique de McCabe)

    // classe_LOC : nombre de lignes de code d’une classe
    // paquet_LOC : nombre de lignes de code d’un paquet (java package) -- la somme des LOC de ses classes
    // classe_CLOC : nombre de lignes de code d’une classe qui contiennent des commentaires
    // paquet_CLOC : nombre de lignes de code d’un paquet qui contiennent des commentaires
    //classe_DC : densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
    //paquet_DC : densité de commentaires pour un paquet : paquet_DC = paquet_CLOC / paquet_LOC

    /**
     * Static method to parse a package recursively and gather analytics inside a tree structure.
     *
     * @param parentPackage     Parent package node from data structure to gather analytics.
     * @param currentPath       Current explored path during recursion.
     */
    public static void parsePackage(PackageNode parentPackage, String currentPath) {

        //Create File object
        File directoryPath = new File(currentPath);

        // List of directories
        String[] directories = directoryPath.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });

        // List of java files
        String[] javaFiles = directoryPath.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".txt");
            }
        });

        if (javaFiles != null && javaFiles.length != 0) {
            // If there is at least 1 class

            PackageNode currentPackage = new PackageNode(parentPackage, currentPath);

            //  Parse package classes
            for (String javaFile: javaFiles) {
                String filePath = currentPath + javaFile;
                parseClass(currentPackage, filePath);
            }

            //  Recursion on packages
            for (String directory: directories) {
                String newPath = currentPath + directory + "/";
                parsePackage(currentPackage, newPath);
            }

        } else if (directories != null && directories.length != 0) {
            // If there is at least 1 directory but no class

            for (String directory: directories) {
                String newPath = currentPath + directory + "/";
                parsePackage(parentPackage, newPath);
            }

        } else {
            // empty folder
            ;
        }
    }

    /**
     * Private method to parse a single file.
     *
     * @param parentPackage     Parent package node from data structure to gather analytics.
     * @param filePath          File to parse.
     */
    private static void parseClass(PackageNode parentPackage, String filePath) {
        ClassNode currentClass = new ClassNode(parentPackage, filePath);

        parentPackage.addChildNode(currentClass);

        // TODO : apply methods and add value to loc, cloc and dc

        // update ClassNode with measured values
        currentClass.addLoc();
        currentClass.addCloc();
        currentClass.addWmc();

    }


    // FIXME : methods under this line should be private

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
                    if (line.trim().length()>=2 && line.trim().substring(0, 2).equals(singleComment)) {
                        //do nada
                    }
                    else {
                        if ((line.trim().length()>=2 && line.trim().substring(0, 2).equals(multiLineComment)) || (line.trim().length()>=3 && line.trim().substring(0, 3).equals(javaDocComment))) {
                            int count = 1;
                            //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                            while(myReader.hasNextLine() && count!=0){
                                line = myReader.nextLine();
                                if(line.contains(multiLineComment) || line.contains(javaDocComment)){
                                    ++count;
                                }
                                if(line.contains(commentEnder)){
                                    --count;
                                }
                            }
                        }
                        //soit une ligne de code avec commentaire ou juste code
                        else {
                            //ligne de code avec commentaire
                            if (line.contains(multiLineComment) || line.contains(javaDocComment)) {
                                ++classe_LOC;
                                int count = 1;
                                while(myReader.hasNextLine() && count!=0){
                                    line = myReader.nextLine();
                                    if(line.contains(multiLineComment) || line.contains(javaDocComment)){
                                        ++count;
                                    }
                                    if(line.contains(commentEnder)){
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
                    if (line.trim().length()>=2 && line.trim().substring(0, 2).equals(singleComment)) {
                        ++classe_CLOC;
                    } else {
                        if ((line.trim().length()>=2 && line.trim().substring(0, 2).equals(multiLineComment)) || (line.trim().length()>=3 && line.trim().substring(0, 3).equals(javaDocComment))) {
                            //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                            int count = 1;
                            while(myReader.hasNextLine() && count!=0) {
                                ++classe_CLOC;
                                line = myReader.nextLine();
                                if (line.contains(multiLineComment) || line.contains(javaDocComment)) {
                                    ++count;
                                }
                                if (line.contains(commentEnder)) {
                                    --count;
                                }
                            }
                            ++classe_CLOC;
                        }
                        //soit une ligne de code avec commentaire ou juste code
                        else {
                            //ligne de code avec commentaire
                            if (line.contains(multiLineComment) || line.contains(javaDocComment)) {
                                ++classe_CLOC;
                                int count = 1;
                                while(myReader.hasNextLine() && count!=0){
                                    ++classe_CLOC;
                                    line = myReader.nextLine();
                                    if(line.contains(multiLineComment) || line.contains(javaDocComment)){
                                        ++count;
                                    }
                                    if(line.contains(commentEnder)){
                                        --count;
                                    }
                                }
                                ++classe_CLOC;
                            }
                            else {
                                //avec //
                                if(line.contains(singleComment)){
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

    float paquet_LOC = 0;
    float paquet_CLOC = 0;
    float paquet_DC = 0;

    public void paquetLOC(){
        //TODO: pour chaque classe dans le paquet, faire classe_LOC et sum
        //FIXME : géré par la récursion dans MAIN : méthode non nécessaire en raison de la structure de donnée
    }

    public void paquetCLOC(){
        //TODO: pour chaque classe dans le paquet, faire classe_CLOC et sum
        //FIXME : géré par la récursion dans MAIN : méthode non nécessaire en raison de la structure de donnée
    }

    public void paquetDC(){
        // FIXME : pas besoin, géré par la structure de donnée
        if(paquet_LOC!=0) {
            paquet_DC = paquet_CLOC/paquet_LOC;
        }
        else {
            System.err.println("Division par 0");
        }
    }

    public float classMethods(File file) throws ClassNotFoundException {
        String className = file.getName();
        className = className.substring(0, className.lastIndexOf(".java"));
        Class fileClass = Class.forName(className);
        Method[] methods = fileClass.getMethods();
        //returns the amount of methods in the file class so that we can calculate WMC
        return methods.length;
    }

    public void packageMethods(){
        //TODO: calculer WMC en loopant sur chaque classe du paquet et les additionner. return cette valeur
        // FIXME : pas besoin,géré par la structure de donnée
    }
}
