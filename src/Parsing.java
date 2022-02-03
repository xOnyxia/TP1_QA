import java.io.*;
import java.util.Scanner;

public class Parsing {

    /**
     * Static method to parse a package recursively and gather analytics inside a tree structure.
     *
     * @param parentPackage     Parent package node from data structure to gather analytics.
     * @param currentPath       Current explored path during recursion.
     */
    public static void parsePackage(PackageNode parentPackage, String currentPath) throws FileNotFoundException {

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
            if (directories != null) {
                for (String directory: directories) {
                    String newPath = currentPath + directory + "/";
                    parsePackage(currentPackage, newPath);
                }
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
    private static void parseClass(PackageNode parentPackage, String filePath) throws FileNotFoundException {
        ClassNode currentClass = new ClassNode(parentPackage, filePath);

        parentPackage.addChildNode(currentClass);

        File fileToParse = new File(filePath);

        // update ClassNode with measured values
        currentClass.addLoc(classeLOC(fileToParse));
        currentClass.addCloc(classeCLOC(fileToParse));
        currentClass.addWmc(classComplexity(fileToParse));

    }

    /**
     * Static method to parse a class and get the amount of lines of code.
     *
     * @param file File to be parsed.
     * @return Amount of lines of code (code only or code + comment).
     */
    private static int classeLOC(File file){
        //constants
        final String singleComment = "//";
        final String javaDocComment = "/**";
        final String multiLineComment = "/*";
        final String commentEnder = "*/";

        int classe_LOC = 0;

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
        return classe_LOC;
    }

    /**
     * Static method to parse a class and get the amount of lines containing comments.
     *
     * @param file File to be parsed.
     * @return Amount of lines containing comments (comment only or code + comment).
     */
    private static int classeCLOC(File file){
        //constants
        final String singleComment = "//";
        final String javaDocComment = "/**";
        final String multiLineComment = "/*";
        final String commentEnder = "*/";

        int classe_CLOC = 0;

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

        return classe_CLOC;
    }
    
    /**
     * Static method to calculate the weighted methods per class (WMC).
     *
     * @param file File to be parsed.
     * @return Class complexity (WMC).
     */
    private static int classComplexity(File file) throws FileNotFoundException {
        //constants
        final String ifCondition = "if";
        final String elseCondition = "else";
        final String whileCondition = "while";

        int complexite = 1;

        //compter nombre de if, else et while
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            //si pas vide
            String line = myReader.nextLine();

            if(line.contains(ifCondition) || line.contains(elseCondition)){
                ++complexite;
            }

            if(line.contains(whileCondition)){
                ++complexite;
            }
        }

        return complexite;
    }
}
