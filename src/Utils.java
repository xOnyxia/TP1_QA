import java.util.Arrays;
import java.util.Collections;

public class Utils {

    public static String getClassNameFromPath(String path) {

        String[] splitPath = path.split("/");

        return splitPath[splitPath.length - 1];

    }

    public static String getRelativePath(String path, String srcPath) {

        return path.replace(srcPath, "");

    }

    public static String getPackageNameFromPath(String path, String srcPath) {

        String relativePath = path.replace(srcPath, "");

        return String.join(".", relativePath.split("/"));

    }

}
