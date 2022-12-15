package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public record Solver(String folder) {
    public <T> T forProblem(String filename, Function<Stream<String>, T> executer){
        var filenameWithExtension = "%s.txt".formatted(filename);
        try(var lines = Files.lines(Path.of(".", folder, filenameWithExtension).toAbsolutePath())) {
            return executer.apply(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void inspect(String filename, Consumer<Stream<String>> consumer){
        var filenameWithExtension = "%s.txt".formatted(filename);
        try(var lines = Files.lines(Path.of(".", folder, filenameWithExtension).toAbsolutePath())) {
            consumer.accept(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
