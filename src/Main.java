import java.io.File;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        String src_path = args[0];
        String output_path = args[1];

        // Initialize data structure, root is a dummy PackageNode.
        PackageNode root = new PackageNode(src_path);

        // Call to static method Parsing.parsePackage()
        Parsing.parsePackage(root, src_path);

        // TODO : les methodes de Parsing devraient etre private
        //  et appelee par parseClass ou parsePackage (static methods)

        // Read data structure and write CSV files
        // Ignore first level PackageNode, which is the root provided as src_path
        // Strip src_path from all path to get relative path
        // TODO



        // write your code here
        //tests unitaires pour tester parsing
        //TODO: cleanup at the end
        /*Parsing parstest = new Parsing();

        File myObj = new File("src/TreeNode.java");

        parstest.classeLOC(myObj);
        parstest.classeCLOC(myObj);
        parstest.classeDC();
        float nbMeth = parstest.classMethods(myObj);
        System.out.println("Nombres de lignes de code: " + parstest.classe_LOC);
        System.out.println("Nombres de lignes de commentaires: " + parstest.classe_CLOC);
        System.out.println("Densite de comments classe: " + parstest.classe_DC);
        System.out.println("Nombre de methodes: " + parstest.classMethods(myObj));*/
    }
}
