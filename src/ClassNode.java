/**
 * TODO : description
 *
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
 * */
public class ClassNode {

    private PackageNode parentNode;
    private String path;

    // mesured metrics
    private int loc;        // number of lines
    private int cloc;       // number of lines which include comments
    private int wmc;        // Weighted Methods per Class

    // calculated metrics
    private float dc;       // density of comments : dc = cloc / loc
    private float bc;       // degree of comments  : bc = dc / wmc

    /**
     * Class constructor specifying parentNode. Use for child packages node.
     *
     * @param parentNode
     */
    ClassNode (PackageNode parentNode, String path) {
        this.path = path;
        this.parentNode = parentNode;
        this.loc = 0;
        this.cloc = 0;
        this.wmc = 0;
    }

    /**
     * Add mesured number of lines per class to loc metric.
     * Added value is also added to parent's node, package node.
     * dc metric is also updated.
     *
     * @param value
     */
    public void addLoc(int value) {
        this.loc += value;
        this.updateDc();
        this.parentNode.addLoc(this.loc);
    }

    /**
     * Add mesured number of lines which include comments to cloc metric.
     * Added value is also added to parent's node, package node.
     * dc metric is also updated.
     *
     * @param value
     */
    public void addCloc(int value) {
        this.cloc += value;
        this.updateDc();
        this.parentNode.addCloc(this.cloc);
    }

    /**
     * Add mesured Weighted Methods per Class to wmc metric.
     * Added value is propagated up the tree by recursively updating parent's node, packages.
     * bc metric is also updated.
     *
     * @param value
     */
    public void addWmc(int value) {
        this.wmc += value;
        this.updateBc();
        this.parentNode.addWcp(this.wmc);
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
     * Replace bc metric value by recalculated value if wmc value is not equal to zero.
     */
    public void updateBc() {
        if (this.wmc != 0) {
            this.bc = this.dc / (float) this.wmc;
        }
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
    public int getWmc() {
        return wmc;
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
     * @return      degree of comments for class or package : bc = dc / wmc
     */
    public float getBc() {
        return bc;
    }
}
