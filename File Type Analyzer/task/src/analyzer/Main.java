package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static analyzer.PatternSearch.PatternSearch.KMPSearchFirst;

class Pattern {
    int priority;
    String pattern;
    String description;

    public Pattern(int priority, String pattern, String description) {
        this.priority = priority;
        this.pattern = pattern;
        this.description = description;
    }

    public Pattern(String[] array) {
        this(
                Integer.parseInt(array[0]),
                array[1].substring(1, array[1].length() - 1),
                array[2].substring(1, array[2].length() - 1));
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "priority=" + priority +
                ", pattern='" + pattern + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

class FileAnalyzerCallableResult {
    String filename;
    String description;

    public FileAnalyzerCallableResult(String filename, String description) {
        this.filename = filename;
        this.description = description;
    }
}

class FileAnalyzerCallable implements Callable<FileAnalyzerCallableResult> {
    private final File file;
    private final List<Pattern> patterns;

    FileAnalyzerCallable(File file, List<Pattern> patterns) {
        this.file = file;
        this.patterns = patterns;
    }

    @Override
    public FileAnalyzerCallableResult call() {
        String result = "No info";
        try {
            byte[] allBytes = Files.readAllBytes(file.toPath());
            for (Pattern pattern : patterns) {
                byte[] patternBytes = pattern.pattern.getBytes();
                if (KMPSearchFirst(allBytes, patternBytes) != -1) {
                    result = pattern.description;
                    break;
                } else {
                    result = "Unknown file type";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error reading file " + file.getName();
        }
        return new FileAnalyzerCallableResult(file.getName(), result);
    }
}

public class Main {
    public static void main(String[] args) {
        System.err.println("Arguments: " + Arrays.toString(args));
        if (args.length < 2) {
            System.out.println("java Main --naive huge_doc.pdf \"%PDF-\" \"PDF document\"");
            return;
        }
        String directory = args[0];
        String patternsDb = args[1];

        File[] files = Path.of(directory).toFile().listFiles();
        System.err.println("files: " + Arrays.toString(files));

        List<Pattern> patterns = null;
        try (Stream<String> filesStream = Files.lines(Paths.get(patternsDb))) {
            patterns = filesStream
                    .map(it -> new Pattern(it.split(";")))
                    .sorted(Comparator.comparing(Pattern::getPriority).reversed())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.err.println(patterns);

        if (files == null || patterns == null) {
            return;
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<FileAnalyzerCallableResult>> futures = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                futures.add(executor.submit(new FileAnalyzerCallable(file, patterns)));
            }
        }
        try {
            for (Future<FileAnalyzerCallableResult> future : futures) {
                FileAnalyzerCallableResult res = future.get();
                System.out.println(res.filename + ": " + res.description);
            }
        } catch (Exception e) {
            System.err.println("Exception");
        } finally {
            executor.shutdown();
        }
    }
}