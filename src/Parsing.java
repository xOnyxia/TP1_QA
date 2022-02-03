import java.io.*;
import java.util.Scanner;

/**
 * Parsing object use to collect data from a source containing java Files and/or java packages.
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
 * */
public class Parsing {

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
                return lowercaseName.endsWith(".java");
            }
        });

        if (javaFiles != null && javaFiles.length != 0) {

            // If there is at least 1 class
            PackageNode currentPackage = new PackageNode(parentPackage, currentPath);
            parentPackage.addChildNode(currentPackage);
            try {

                //  Parse package classes
                for (String javaFile : javaFiles) {
                    String filePath = currentPath + javaFile;
                    parseClass(parentPackage, filePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //  Recursion on packages
            if (directories != null) {
                for (String directory : directories) {
                    String newPath = currentPath + directory + "/";
                    parsePackage(currentPackage, newPath);
                }
            }

        } else if (directories != null && directories.length != 0) {

            // If there is at least 1 directory but no class
            for (String directory : directories) {
                String newPath = currentPath + directory + "/";
                parsePackage(parentPackage, newPath);
            }
        } else {
            // empty directory
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
        currentClass.addLoc(parseClassLoc(fileToParse));
        currentClass.addCloc(parseClassCloc(fileToParse));
        currentClass.addWmc(calculateClassComplexity(fileToParse));
    }

    /**
     * Static method to parse a class and get the amount of lines of code.
     *
     * @param file File to be parsed.
     * @return Amount of lines of code (code only or code + comment).
     */
    private static int parseClassLoc(File file){

        //constants
        final String SINGLE_COMMENT = "//";
        final String JAVADOC_COMMENT = "/**";
        final String MULTIPLE_LINES_COMMENT = "/*";
        final String END_COMMENT = "*/";

        int classLoc = 0;

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                //si pas vide
                if (!line.trim().isEmpty()) {

                    //si commence pas par un commentaire
                    if (!((line.trim().length() >= 2) &&
                            (line.trim().substring(0, 2).equals(SINGLE_COMMENT)))) {

                        //si la ligne commence avec un commentaire multilignes ou de javadoc
                        if (((line.trim().length() >= 2) && (line.trim().substring(0, 2).equals(MULTIPLE_LINES_COMMENT)))
                                || ((line.trim().length() >= 3) && (line.trim().substring(0, 3).equals(JAVADOC_COMMENT)))) {

                            //tant qu'on trouve pas la ligne avec la fin de commentaire (openedComments=0), next line
                            int openedComments = 1;
                            while (myReader.hasNextLine() && openedComments != 0) {
                                line = myReader.nextLine();

                                //si on trouve un autre commentaire, on le rajoute dans openedComments
                                if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                    ++openedComments;
                                }

                                //si on trouve une fin de commentaire, on l'enleve de openedComments
                                if (line.contains(END_COMMENT)) {
                                    --openedComments;
                                }
                            }
                        }

                        //soit une ligne de code avec commentaire ou juste un ligne de code
                        else {

                            //ligne de code avec commentaire
                            if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                ++classLoc;

                                //tant qu'on trouve pas la ligne avec la fin de commentaire (openedComments=0), next line
                                int openedComments = 1;
                                while (myReader.hasNextLine() && openedComments != 0) {
                                    line = myReader.nextLine();

                                    //si on trouve un autre commentaire, on le rajoute dans openedComments
                                    if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                        ++openedComments;
                                    }

                                    //si on trouve une fin de commentaire, on l'enleve de openedComments
                                    if (line.contains(END_COMMENT)) {
                                        --openedComments;
                                    }
                                }
                            }

                            //ligne avec juste du code
                            else {
                                ++classLoc;
                            }
                        }
                    }
                }

                //else: ligne vide ou ligne commencant par un commentaire, alors do nothing.
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return classLoc;
    }

    /**
     * Static method to parse a class and get the amount of lines containing comments.
     *
     * @param file File to be parsed.
     * @return Amount of lines containing comments (comment only or code + comment).
     */
    private static int parseClassCloc(File file){

        //constants
        final String SINGLE_COMMENT = "//";
        final String JAVADOC_COMMENT = "/**";
        final String MULTIPLE_LINES_COMMENT = "/*";
        final String END_COMMENT = "*/";

        int classCloc = 0;

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                //si la ligne n'est pas vide
                if (!line.trim().isEmpty()) {

                    //si commence par un comment //
                    if (line.trim().length() >= 2 && line.trim().substring(0, 2).equals(SINGLE_COMMENT)) {
                        ++classCloc;
                    } else {

                        //si il y a un commentaire multilignes ou de javadoc
                        if ((line.trim().length() >= 2 && line.trim().substring(0, 2).equals(MULTIPLE_LINES_COMMENT))
                                || ((line.trim().length() >= 3) && (line.trim().substring(0, 3).equals(JAVADOC_COMMENT)))) {

                            //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                            int openedComments = 1;
                            while (myReader.hasNextLine() && openedComments != 0) {
                                ++classCloc;
                                line = myReader.nextLine();

                                //si on trouve un autre commentaire, on le rajoute dans openedComments
                                if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                    ++openedComments;
                                }

                                //si on trouve une fin de commentaire, on l'enleve de openedComments
                                if (line.contains(END_COMMENT)) {
                                    --openedComments;
                                }
                            }
                            ++classCloc;
                        }

                        //soit une ligne de code avec commentaire ou juste une ligne de code
                        else {

                            //ligne de code avec commentaire
                            if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                ++classCloc;

                                //tant qu'on trouve pas la ligne avec la fin de commentaire, next line
                                int openedComments = 1;
                                while (myReader.hasNextLine() && openedComments != 0) {
                                    ++classCloc;
                                    line = myReader.nextLine();

                                    //si on trouve un autre commentaire, on le rajoute dans openedComments
                                    if (line.contains(MULTIPLE_LINES_COMMENT) || line.contains(JAVADOC_COMMENT)) {
                                        ++openedComments;
                                    }

                                    //si on trouve une fin de commentaire, on l'enleve de openedComments
                                    if (line.contains(END_COMMENT)) {
                                        --openedComments;
                                    }
                                }
                                ++classCloc;
                            }

                            //ligne de code avec commentaire //
                            else {
                                if (line.contains(SINGLE_COMMENT)) {
                                    ++classCloc;
                                }

                                //sinon juste du code et fait rien
                            }
                        }
                    }
                }

                //sinon c'est une ligne vide
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return classCloc;
    }

    /**
     * Static method to calculate the weighted methods per class (WMC).
     *
     * @param file File to be parsed.
     * @return Class complexity (WMC).
     */
    private static int calculateClassComplexity(File file) throws FileNotFoundException {

        //constants
        final String IF_CONDITION = "if";
        final String ELSE_CONDITION = "else";
        final String WHILE_CONDITION = "while";

        int complexity = 1;

        //compter nombre de if, else et while
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {

            //si pas vide
            String line = myReader.nextLine();
            if (line.contains(IF_CONDITION) || line.contains(ELSE_CONDITION)) {
                ++complexity;
            }

            if (line.contains(WHILE_CONDITION)) {
                ++complexity;
            }
        }
        return complexity;
    }
}
