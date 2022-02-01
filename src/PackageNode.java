import java.util.HashSet;
import java.util.Set;

/**
 * TODO : description
 *
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
 * */
public class PackageNode {

    private PackageNode parentNode;
    private Set<PackageNode> childPackages;
    private Set<ClassNode> childClasses;
    private String path;

    // measured metrics
    private int loc;        // number of lines
    private int cloc;       // number of lines which include comments

    // calculated metrics
    private int wcp;        // Weighted Classes per Package
    private float dc;       // density of comments : dc = cloc / loc
    private float bc;       // degree of comments : bc = dc / wcp

    /**
     * Class constructor.
     * Use for tree's root.
     */
    PackageNode (String path) {
        this.path = path;
        this.parentNode = null;
        this.childPackages = new HashSet<>();
        this.childClasses = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wcp = 0;
    }

    /**
     * Class constructor specifying parentNode and path.
     * Use for child packages node.
     *
     * @param parentNode
     */
    PackageNode (PackageNode parentNode, String path) {
        this.path = path;
        this.parentNode = parentNode;
        this.childPackages = new HashSet<>();
        this.childClasses = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wcp = 0;
    }

    /**
     * Add measured number of lines per package to loc metric.
     * dc metric is also updated.
     * This method will be called from a {@link ClassNode} inside child classes set.
     *
     * @param value
     */
    public void addLoc(int value) {
        this.loc += value;
        this.updateDc();
    }

    /**
     * Add measured number of lines which include comments to cloc metric.
     * dc metric is also updated.
     * This method will be called from a {@link ClassNode} inside child classes set.
     *
     * @param value
     */
    public void addCloc(int value) {
        this.cloc += value;
        this.updateDc();
    }

    /**
     * Add measured Weighted Methods per Class to wcp metric.
     * Added value is propagated up the tree by recursively updating parent's node.
     * TP1 Statement :
     * """
     *     WCP : «Weighted Classes per Package», pour chaque paquet.
     *     C’est la somme des WMC de toutes les classes d’un paquet
     *     et les WCP de ses sous-paquets.
     * """
     * bc metric is also updated.
     *
     * @param value
     */
    public void addWcp(int value) {
        this.wcp += value;
        this.updateBc();
        if (this.parentNode != null) {
            this.parentNode.addWcp(this.wcp);
        }
    }

    /**
     * Replace dc metric value by recalculated value if loc value is not equal to zero.
     * bc metric is also updated.
     */
    public void updateDc() {
        if (this.loc != 0) {
            this.dc = (float) this.cloc / (float) this.loc;
            this.updateBc();
        }
    }

    /**
     * Replace bc metric value by recalculated value if wcp value is not equal to zero.
     */
    public void updateBc() {
        if (this.wcp != 0) {
            this.bc = this.dc / (float) this.wcp;
        }
    }

    /**
     * Add a {@link PackageNode} as child node to the childNodes set.
     * Method overloading is use to classify child nodes.
     *
     * @param childPackage
     */
    public void addChildNode(PackageNode childPackage) {
        this.childPackages.add(childPackage);
    }

    /**
     * Add a {@link ClassNode} as child node to the childNodes set.
     * Method overloading is use to classify child nodes.
     *
     * @param childClass
     */
    public void addChildNode(ClassNode childClass) {
        this.childClasses.add(childClass);
    }

    /**
     * Setter method.
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter method.
     *
     * @return      Parent node
     */
    public PackageNode getParentNode() {
        return parentNode;
    }

    /**
     * Getter method.
     *
     * @return      Set with child PackageNode
     */
    public Set<PackageNode> getChildPackages() {
        return childPackages;
    }

    /**
     * Getter method.
     *
     * @return      Set with child ClassNode
     */
    public Set<ClassNode> getChildClasses() {
        return childClasses;
    }

    /**
     * Getter method.
     *
     * @return      Element path
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter method.
     *
     * @return      number of lines per class or package
     */
    public int getLoc() {
        return loc;
    }

    /**
     * Getter method.
     *
     * @return      number of lines which include comments
     */
    public int getCloc() {
        return cloc;
    }

    /**
     * Getter method.
     *
     * @return      number of methods per class or package
     */
    public int getWcp() {
        return wcp;
    }

    /**
     * Getter method.
     *
     * @return      density of comments per class or package : dc = cloc / loc
     */
    public float getDc() {
        return dc;
    }

    /**
     * Getter method.
     *
     * @return      degree of comments for class or package : bc = dc / wcp
     */
    public float getBc() {
        return bc;
    }
}
