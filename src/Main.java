/**
 * Main java class for this application.
 *
 * @author      Megane Dandurand
 * @author      Julien Thibeault
 */
public class Main {

    public static void main(String[] args) {

        String srcPath = args[0];
        String outputPath = args[1];

        srcPath = Utils.pathFormatting(srcPath);
        outputPath = Utils.pathFormatting(outputPath);

        System.out.println("srcPath : " + srcPath);
        System.out.println("outputPath : " + outputPath);

        // Initialize data structure, root is a dummy PackageNode.
        PackageNode root = new PackageNode(srcPath);

        // Call to static method Parsing.parsePackage()
        Parsing.parsePackage(root, srcPath);

        // Output to CSV files
        AnalyticsWriter analyticsWriter = new AnalyticsWriter(srcPath, outputPath, root);

        analyticsWriter.writeAnalyticsFromRoot();
    }
}
