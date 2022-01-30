import java.util.HashSet;
import java.util.Set;

/**
 * TODO : description
 *
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
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


    /**
     * Class constructor. Use for tree's root.
     */
    TreeNode () {
        this.parentNode = null;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wmcwcp = 0;
    }

    /**
     * Class constructor specifying parentNode. Use for child node.
     *
     * @param parentNode
     */
    TreeNode (TreeNode parentNode) {
        this.parentNode = parentNode;
        this.childNodes = new HashSet<>();
        this.loc = 0;
        this.cloc = 0;
        this.wmcwcp = 0;
    }

    /**
     * Add mesured number of lines per class or package to loc metric.
     * Added value is propagated up the tree by recursively updating parent's node.
     * dc metric is also updated.
     *
     * @param value
     */
    public void addLoc(int value) {
        this.loc += value;
        this.updateDc();
        if (this.parentNode != null) {
            this.parentNode.addLoc(this.loc);
        }
    }

    /**
     * Add mesured number of lines which include comments to cloc metric.
     * Added value is propagated up the tree by recursively updating parent's node.
     * dc metric is also updated.
     *
     * @param value
     */
    public void addCloc(int value) {
        this.cloc += value;
        this.updateDc();
        if (this.parentNode != null) {
            this.parentNode.addCloc(this.cloc);
        }
    }

    /**
     * Add mesured number of methods to wmcwcp metric.
     * Added value is propagated up the tree by recursively updating parent's node.
     * bc metric is also updated.
     *
     * @param value
     */
    public void addWmcwcp(int value) {
        this.wmcwcp += value;
        this.updateBc();
        if (this.parentNode != null) {
            this.parentNode.addWmcwcp(this.wmcwcp);
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
     * Replace bc metric value by recalculated value if wmcwcp value is not equal to zero.
     */
    public void updateBc() {
        if (this.wmcwcp != 0) {
            this.bc = this.dc / (float) this.wmcwcp;
        }
    }

    /**
     * Add a {@link TreeNode} as child node to the childNodes set.
     *
     * @param childNode
     */
    public void addChildNode(TreeNode childNode) {
        this.childNodes.add(childNode);
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
    public TreeNode getParentNode() {
        return parentNode;
    }

    /**
     * Getter method.
     *
     * @return      Set with child nodes
     */
    public Set<TreeNode> getChildNodes() {
        return childNodes;
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
    public int getWmcwcp() {
        return wmcwcp;
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
     * @return      degree of comments for class or package : bc = dc / wmcwcp
     */
    public float getBc() {
        return bc;
    }
}
