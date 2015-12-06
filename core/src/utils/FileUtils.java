package utils;

import com.badlogic.gdx.Gdx;

public class FileUtils {
    private FileUtils() {}
    
    public static String loadShaderFile(String filename) {
        String filePath = "shaders/" + filename;
        return readTextFile(filePath);
    }
    
    public static String readTextFile(String filename) {
        return Gdx.files.internal(filename).readString();
    }

}
