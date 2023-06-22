package zad1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        Path dir = Paths.get(dirName);
        Path resultFile = Paths.get(resultFileName);
        try {
            Files.deleteIfExists(resultFile);
            Files.createFile(resultFile);
            BufferedWriter writer = Files.newBufferedWriter(resultFile, StandardCharsets.UTF_8);
            FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".txt")) {
                        byte[] bytes = Files.readAllBytes(file);
                        String content = new String(bytes, Charset.forName("Cp1250"));
                        writer.write(content);
                        writer.newLine();
                    }
                    return FileVisitResult.CONTINUE;
                }
            };
            Files.walkFileTree(dir, fileVisitor);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
