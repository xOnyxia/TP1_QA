import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * AnalyticsWriter object use to read data from a non binary tree and write to CSV files.
 * The output is formed by two different CSV files.
 * First CSV file is for packages analytics.
 * Second CSV file is for classes analytics.
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
 * */
public class AnalyticsWriter {

    private String src_path;
    private String output_path;
    private PackageNode dataTreeRoot;

    private String packagesPath;
    private String classesPath;

    private File packagesFile;
    private File classesFile;

    private FileWriter packagesFileWriter;
    private FileWriter classesFileWriter;

    private BufferedWriter packagesBufferedWriter;
    private BufferedWriter classesBufferedWriter;

    /**
     * Class to write data from a non-binary tree structure to CSV files.
     * First level of the tree is not considered as a package since it's the source directory.
     *
     * @param src_path
     * @param output_path
     * @param dataTreeRoot
     */
    public AnalyticsWriter(String src_path, String output_path, PackageNode dataTreeRoot) {

        this.src_path = src_path;
        this.output_path = output_path;
        this.dataTreeRoot = dataTreeRoot;

        this.packagesPath = this.output_path + "paquets.csv";
        this.classesPath = this.output_path + "classes.csv";

        this.packagesFile = new File(this.packagesPath);
        this.classesFile = new File(this.classesPath);

    }

    /**
     * Constructor.
     *
     */
    public void writeAnalyticsFromRoot() {

        try {
            // create FileWriter objects
            this.packagesFileWriter = new FileWriter(this.packagesFile);
            this.classesFileWriter = new FileWriter(this.classesFile);

            // create BufferedWriter objects
            this.packagesBufferedWriter = new BufferedWriter(this.packagesFileWriter);
            this.classesBufferedWriter = new BufferedWriter(this.classesFileWriter);

            // adding headers to CSV files
            String packagesHeader = "chemin,paquet,paquet_LOC,paquet_CLOC,paquet_DC,WCP,paquet_BC";
            String classesHeader = "chemin,class,classe_LOC,classe_CLOC,classe_DC,WMC,classes_BC";

            this.packagesBufferedWriter.write(packagesHeader);
            this.packagesBufferedWriter.newLine();

            this.classesBufferedWriter.write(classesHeader);
            this.classesBufferedWriter.newLine();

            // loop over class nodes in src_path
            for (ClassNode classNode: this.dataTreeRoot.getChildClasses()) {
                writeClassAnalytics(classNode);
            }

            // loop over package nodes in src_path
            for (PackageNode packageNode: this.dataTreeRoot.getChildPackages()) {
                writePackageAnalytics(packageNode);
            }

            // close writer connections
            this.packagesBufferedWriter.flush();
            this.packagesBufferedWriter.close();

            this.classesBufferedWriter.flush();
            this.classesBufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to write current package node data to class file.
     * CSV Header is "chemin,paquet,paquet_LOC,paquet_CLOC,paquet_DC,WCP,paquet_BC";
     *
     * @param currentNode
     * @throws IOException
     */
    private void writePackageAnalytics(PackageNode currentNode) throws IOException {

        if (currentNode.getWcp() != 0) {
            this.packagesBufferedWriter.write(Utils.getRelativePath(currentNode.getParentNode().getPath(),
                    this.src_path) + ",");
            this.packagesBufferedWriter.write(Utils.getPackageNameFromPath(currentNode.getPath(), this.src_path) + ",");
            this.packagesBufferedWriter.write(String.valueOf(currentNode.getLoc()) + ",");
            this.packagesBufferedWriter.write(String.valueOf(currentNode.getCloc()) + ",");
            this.packagesBufferedWriter.write(String.valueOf(currentNode.getDc()) + ",");
            this.packagesBufferedWriter.write(String.valueOf(currentNode.getWcp()) + ",");
            this.packagesBufferedWriter.write(String.valueOf(currentNode.getBc()));
            this.packagesBufferedWriter.newLine();
        }

        // loop over class nodes, child of current node
        for (ClassNode classNode: currentNode.getChildClasses()) {
            writeClassAnalytics(classNode);
        }

        // loop over package nodes, child of current node
        for (PackageNode packageNode: currentNode.getChildPackages()) {
            writePackageAnalytics(packageNode);
        }

    }

    /**
     * Method to write current class node data to class file.
     * CSV Header is "chemin,class,classe_LOC,classe_CLOC,classe_DC,WMC,classes_BC";
     *
     * @param currentNode
     * @throws IOException
     */
    private void writeClassAnalytics(ClassNode currentNode) throws IOException {

        this.classesBufferedWriter.write(Utils.getRelativePath(currentNode.getParentNode().getPath(),
                                         this.src_path) + ",");
        this.classesBufferedWriter.write(Utils.getClassNameFromPath(currentNode.getPath()) + ",");
        this.classesBufferedWriter.write(String.valueOf(currentNode.getLoc()) + ",");
        this.classesBufferedWriter.write(String.valueOf(currentNode.getCloc()) + ",");
        this.classesBufferedWriter.write(String.valueOf(currentNode.getDc()) + ",");
        this.classesBufferedWriter.write(String.valueOf(currentNode.getWmc()) + ",");
        this.classesBufferedWriter.write(String.valueOf(currentNode.getBc()));
        this.classesBufferedWriter.newLine();

    }

}
