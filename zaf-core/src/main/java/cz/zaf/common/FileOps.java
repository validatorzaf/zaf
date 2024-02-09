package cz.zaf.common;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileOps {
    
    static public void recursiveDelete(Path pathForDelete) throws IOException {
        if (Files.isRegularFile(pathForDelete)) {
            Files.delete(pathForDelete);
        } else {
            if (Files.isDirectory(pathForDelete)) {
                // recursive dir delete
                Files.walkFileTree(pathForDelete, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(
                      Path dir, IOException exc) throws IOException {
                        delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult visitFile(
                      Path file, BasicFileAttributes attrs) 
                      throws IOException {
                        delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }        
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("win"));
    }

    /**
     * Delete file or folder
     * 
     * Method has special check to delete ReadOnly files on Windows
     * 
     * @param path
     * @throws IOException
     */
    static public void delete(Path path) throws IOException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            boolean retry = false;
            if (isWindows()) {
                // try to reset readolny attribute
                Object val = Files.getAttribute(path, "dos:readonly");
                if(val!=null && val instanceof Boolean) {
                    Boolean readOnly = (Boolean)val;
                    if (readOnly) {
                        Files.setAttribute(path, "dos:readonly", false);
                        retry = true;
                    }
                    
                }
            }
            if (retry) {
                Files.delete(path);
                return;
            }
            // rethrow orig exception
            throw e;
        }

    }

}
