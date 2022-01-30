import java.util.HashSet;
import java.util.Set;

/**
 * TODO javadoc
 * */
public class TreeNode {

    private TreeNode parentNode;
    private Set<TreeNode> childNodes;
    private String path;    // TODO : determine the kind of path we want here (partial or complete, vs file and dir name)

    // mesured metrics
    private int loc;        // number of lines per class or package
    private int cloc;       // number of lines which include comments

    // mesured (class) or calculated (package) metric
    private int wmcwcp;     // number of methods per class or package

    // calculated metrics
    private float dc;       // density of comments per class or package : dc = cloc / loc
    private float bc;       // degree of comments for class or package : bc = dc / wmcwcp

    // Constructor to use for tree's root
    TreeNode () {
        this.parentNode = null;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wmcwcp = 0;
    }

    // Constructor to use for child node
    TreeNode (TreeNode parentNode) {
        this.parentNode = parentNode;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wmcwcp = 0;
    }

    // Setters & methods to update metrics recursively

    public void addLoc(int value) {
        this.loc += value;
        this.updateDc();
        if (this.parentNode != null) {
            this.parentNode.addLoc(this.loc);
        }
    }

    public void addCloc(int value) {
        this.cloc += value;
        this.updateDc();
        if (this.parentNode != null) {
            this.parentNode.addCloc(this.cloc);
        }
    }

    public void addWmcwcp(int value) {
        this.wmcwcp += value;
        this.updateBc();
        if (this.parentNode != null) {
            this.parentNode.addWmcwcp(this.wmcwcp);
        }
    }

    public void updateDc() {
        if (this.loc != 0) {
            this.dc = (float) this.cloc / (float) this.loc;
            this.updateBc();
        }
    }

    public void updateBc() {
        if (this.wmcwcp != 0) {
            this.bc = this.dc / (float) this.wmcwcp;
        }
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

    // Getters

    public TreeNode getParentNode() {
        return parentNode;
    }

    public Set<TreeNode> getChildNodes() {
        return childNodes;
    }

    public String getPath() {
        return path;
    }

    public int getLoc() {
        return loc;
    }

    public int getCloc() {
        return cloc;
    }

    public int getWmcwcp() {
        return wmcwcp;
    }

    public float getDc() {
        return dc;
    }

    public float getBc() {
        return bc;
    }
}

