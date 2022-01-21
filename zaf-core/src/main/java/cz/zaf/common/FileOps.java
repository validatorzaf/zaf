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
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult visitFile(
                      Path file, BasicFileAttributes attrs) 
                      throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }        
    }
}
