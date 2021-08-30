package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static analyzer.PatternSearch.PatternSearch.KMPSearchFirst;

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
    private final String pattern;
    private final String description;

    FileAnalyzerCallable(File file, String pattern, String description) {
        this.file = file;
        this.pattern = pattern;
        this.description = description;
    }

    @Override
    public FileAnalyzerCallableResult call() {
        String result;
        try {
            byte[] allBytes = Files.readAllBytes(file.toPath());
            byte[] patternBytes = pattern.getBytes();
            if (KMPSearchFirst(allBytes, patternBytes) != -1) {
                result = description;
            } else {
                result = "Unknown file type";
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
        if (args.length < 3) {
            System.out.println("java Main --naive huge_doc.pdf \"%PDF-\" \"PDF document\"");
            return;
        }
        String directory = args[0];
        String pattern = args[1];
        String description = args[2];

        File[] files = Path.of(directory).toFile().listFiles();
        System.err.println("files: " + Arrays.toString(files));

        if (files == null) {
            return;
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<FileAnalyzerCallableResult>> futures = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                futures.add(executor.submit(new FileAnalyzerCallable(file, pattern, description)));
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