import java.io.File;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        // arg1 = src_path
        // arg2 = output_path (where to save CSV files)

        // Attributes required in main class
        // Comme on pourrait avoir des classes ou des dossier a la source on a besoin de :
        Set<PackageNode> rootPackages;
        Set<ClassNode> rootClasses;
        // on devra itérer sur ces deux Set à la fin pour produire les CSV.
        //      - Lire toutes les classes
        //      - Lire toutes les packages récursivement incluant les classes

        /*
         * Pseudo code à formater
         *
         * Code récursif :
         * Descendre chaque branche de l'arbre en notant le chemin parcouru
         * et en passant une référence vers le plus récent PackageNode créé (parentNode).
         * S'arreter à chaque dossier qui contient au moins un fichier java,
         * c'est ce qui définit un package.
         *
         * Mesurer les métriques et les associée à la nouvelle node créé.
         * (on peut modifier les constructeurs selon l'ordre des étapes)
         *
         * Pour chaque dossier trouver, relancer l'analyse récursif
         *
         * */

        // TODO : code pour l'analyse récursive

        // TODO : code pour la lecture récursive des données recueillies : pour écrire vers CSV

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
