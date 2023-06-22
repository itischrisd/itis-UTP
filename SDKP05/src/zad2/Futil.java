package zad2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        try {
            Path resultPath = Paths.get(resultFileName);
            Charset outputCharset = StandardCharsets.UTF_8;

            Stream<Path> pathStream = Files.walk(Paths.get(dirName));
            Stream<String> fileContentStream = pathStream
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .flatMap(Futil::readFile)
                .map(String::trim);

            try (Writer writer = new OutputStreamWriter(Files.newOutputStream(resultPath), outputCharset)) {
                fileContentStream.forEach(line -> {
                    try {
                        writer.write(line);
                        writer.write(System.lineSeparator());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stream<String> readFile(Path path) {
        try {
            return Files.lines(path, Charset.forName("Cp1250"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
