import java.util.HashSet;
import java.util.Set;

public class TreeNode {

    private TreeNode parentNode;
    private Set<TreeNode> childNodes;
    private String path;    // TODO : determine the kind of path we want here (partial or complete)
    private String name;

    // mesured metrics :
    private int loc;        // number of lines per class or package
    private int cloc;       // number of lines which include comments
    private int wmc;        // weighted methods per class = number of methods per class

    // calculated metrics :
    private float dc;       // density of comments per class or package : dc = cloc / loc
    private float bc;       // degree of comments for class or package : bc = dc / (wmc or wcp)
    private int wcp;        // sum of wmc of class and subpackage for a package

    // Constructor to use for tree's root
    TreeNode () {
        this.parentNode = null;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wmc = 0;
    }

    // Constructor to use for child node
    TreeNode (TreeNode parentNode) {
        this.parentNode = parentNode;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.nmethods = 0;
    }


    // Methods

    // Getters and setters

    public void updateDc() {
        float value = (float)this.cloc / (float)this.loc;
        setDc(value);
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public void addChildNode(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public void setCloc(int cloc) {
        this.cloc = cloc;
    }

    public void setWmc(int wmc) {
        this.wmc = wmc;
    }

    public void setDc(float dc) {
        this.dc = dc;
    }

    public void setBc(float bc) {
        this.bc = bc;
    }

    public void setWcp(int wcp) {
        this.wcp = wcp;
    }
}

/*

Dans une node il y aura :
- Des attributs pour chacun des métriques : mesurés et calculés
- Un attribut path ou path relatif (à déterminer ce qu'on a besoin)
- Une liste de node-enfants (comme ce n'est pas un arbre binaire)
- Un lien vers la node parent
- et des méthodes setters

Les setters sur les attributs à cumuler s'appliqueront en remontant l'arbre.

class in Java:

    // Attributes

    // Constructor

    // Methods

    // Getters and setters

 */


