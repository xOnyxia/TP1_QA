public class Utils {

    public static String getClassNameFromPath(String path) {

        String[] splitPath = path.split("/");

        return splitPath[splitPath.length - 1];

    }

    public static String getRelativePath(String path, String srcPath) {

        String relativePath = path.replace(srcPath, "");

        return relativePath.length() != 0 ? relativePath : "~ root ~";

    }

    public static String getPackageNameFromPath(String path, String srcPath) {

        String relativePath = path.replace(srcPath, "");

        relativePath = String.join(".", relativePath.split("/"));

        return relativePath.length() != 0 ? relativePath : "~ root ~";

    }

}
