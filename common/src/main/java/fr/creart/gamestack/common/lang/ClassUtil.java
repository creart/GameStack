package fr.creart.gamestack.common.lang;

import fr.creart.gamestack.common.log.CommonLogger;

/**
 * Some utils for classes
 *
 * @author Creart
 */
public class ClassUtil {

    private ClassUtil()
    {

    }

    /**
     * Loads the given class
     *
     * @param path path to the class
     */
    public static void loadClass(String path)
    {
        try {
            Class.forName(path);
        } catch (Exception e) {
            CommonLogger.error("Could not find class for path " + path);
        }
    }

}
