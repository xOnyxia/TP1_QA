public class Utils {

    /**
     * Static method to get the name of the java class from the path
     *
     * @param path
     * @return class name
     */
    public static String getClassNameFromPath(String path) {

        String[] splitPath = path.split("/");

        return splitPath[splitPath.length - 1];
    }

    /**
     * Static method to get the relative path of the source path
     *
     * @param path
     * @param srcPath
     * @return relative path
     */
    public static String getRelativePath(String path, String srcPath) {

        String relativePath = path.replace(srcPath, "");

        return relativePath.length() != 0 ? relativePath : "~ root ~";
    }

    /**
     * Static method to get the name of the package from the path name
     *
     * @param path
     * @param srcPath
     * @return package name
     */
    public static String getPackageNameFromPath(String path, String srcPath) {

        String relativePath = path.replace(srcPath, "");
        relativePath = String.join(".", relativePath.split("/"));

        return relativePath.length() != 0 ? relativePath : "~ root ~";
    }

    /**
     * Static method to add a "/" at the end of the given output path to fix the formatting
     *
     * @param outputPath path name to be parsed.
     * @return corrected path name.
     */
    public static String outputPathFormatting(String outputPath){

        if(outputPath.charAt(outputPath.length()-1) != '/'){
            outputPath.concat("/");
        }
        return outputPath;
    }
}
