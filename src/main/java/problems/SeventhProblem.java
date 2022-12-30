package problems;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;

public class SeventhProblem {

    sealed interface LineParser permits Command, Directory, File {}

    public static LineParser parse(String line, FileWalker tree) {
        if (line.startsWith("$")) return Command.from(line);
        if (line.startsWith("dir")) return Directory.from(line, tree.actualFolder());
        return File.from(line);
    }

    record File(String name, Long size) implements LineParser {
        public static LineParser from(String line) {
            var split = line.split(" ");
            return new File(
                    split[1],
                    Long.valueOf(split[0])
            );
        }
    }
    static final class Directory implements LineParser {
        private static final String GO_BACK = "..";
        private final String name;
        private final Directory father;
        private final Map<String, Directory> directories;
        private final List<File> files;

        Directory(String name, Directory father, Map<String, Directory> directories, List<File> files) {
            this.name = name;
            this.father = father;
            this.directories = directories;
            this.files = files;
        }

        public Directory(String name, Directory father) {
            this(
                    name,
                    father,
                    new HashMap<>(),
                    new ArrayList<>()
            );
        }

        public static Directory from(String line, Directory actual) {
            var split = line.split(" ");
            return new Directory(split[1], actual);
        }

        public Stream<Directory> subfolders(){
            if(directories.isEmpty()) return Stream.empty();

            return Stream.concat(
                    directories.values().stream(),
                    directories
                    .values()
                    .stream()
                    .flatMap(Directory::subfolders)
            );
        }

        public Optional<Directory> navigate(String file) {
            if (GO_BACK.equals(file)) return Optional.ofNullable(father);

            return Optional.ofNullable(directories.get(file));
        }

        public Long size(){
             var totalFileSize = files.stream().mapToLong(File::size).sum();
             var totalFolderSize = directories.values().stream().mapToLong(Directory::size).sum();

             return totalFileSize + totalFolderSize;
        }

        public Directory findRoot(){
            var root = father;

            while(root.father() != null){
                root = root.father();
            }

            return root;
        }

        public void put(Directory directory){
            directories.put(directory.name(), directory);
        }

        public String name() {
            return name;
        }
        public Directory father() {
            return father;
        }

        public void add(File file) {
            files.add(file);
        }

        @Override
        public String toString() {
            return "Directory[name=%s, directories=\n\t%s\n, files=%s]".formatted(name, directories, files);
        }
    }

    record Command(String command, String arg) implements LineParser {
        public boolean isChangeDirectory() {
            return "cd".equals(command);
        }

        public static Command from(String name) {
            var split = name.substring(2).split(" ");

            if(split.length > 1) return new Command(split[0], split[1]);

            return new Command(split[0], null);
        }
    }
    static final class FileWalker {
        private final String ROOT_DIRECTORY_NAME = "/";

        private final Long AVAILABLE_SPACE = 70_000_000L;

        private final Long UPDATED_SIZE = 30_000_000L;

        private Directory root;
        private Directory folder;
        public FileWalker(Directory root) {
            this.root = root;
            this.folder = root;
        }
        public void changeDirectory(Command command){
            if(ROOT_DIRECTORY_NAME.equals(command.arg())){
                this.folder = root;
                return;
            }

            this.folder = folder.navigate(command.arg()).orElseThrow(() -> {
                System.out.println("Houve um problema: ");
                System.out.println("folder => %s".formatted(folder.name()));
                System.out.println(root.subfolders().map(Directory::name).collect(Collectors.joining(" ")));
                return new RuntimeException();
            });
        }

        public void createDirectory(Directory directory){
            folder.put(directory);
        }

        public void createFile(File file){
            folder.add(file);
        }

        public Directory actualFolder() {
            return folder;
        }

        public Long filterFolderAtLeastSize(Long limit){
            return root.subfolders()
                    .mapToLong(Directory::size)
                    .filter(size -> size < limit)
                    .sum();
        }

        public Directory findBestDirectoryToRemove(){
            final var space = AVAILABLE_SPACE - root.size();

            // solução gulosa, idealmente deveriamos checar se a pasta já ajuda
            // caso sim => verificamos as subpastas
            // caso não => eliminamos essa possibilidade
            // recursão simples :)
            return root
                    .subfolders()
                    .filter( directory -> {
                        final var spaceIfRemoved = space + directory.size();

                        return spaceIfRemoved > UPDATED_SIZE;
                    })
                    .min(comparingLong(Directory::size))
                    .get();
        }
    }

    public static FileWalker solve(Stream<String> lines) {
        var tree = new FileWalker(new Directory("/", null));

        lines.forEach(line -> {
            switch (parse(line, tree)) {
                case Command command && command.isChangeDirectory() -> tree.changeDirectory(command);
                case Directory directory -> tree.createDirectory(directory);
                case File file -> tree.createFile(file);
                default -> {}
            }
        });

        return tree;
    }

    public static void main(String[] args) {
        var lines = Stream.of(
                "$ cd /",
                "$ ls",
                "dir a",
                "14848514 b.txt",
                "8504156 c.dat",
                "dir d",
                "$ cd a",
                "$ ls",
                "dir e",
                "29116 f",
                "2557 g",
                "62596 h.lst",
                "$ cd e",
                "$ ls",
                "584 i",
                "$ cd ..",
                "$ cd ..",
                "$ cd d",
                "$ ls",
                "4060174 j",
                "8033020 d.log",
                "5626152 d.ext",
                "7214296 k"
        );
        solve(lines);
    }
}
