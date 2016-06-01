package com.denis.tech.talk.functionall;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalReader {
    public static void main(String[] args) {
        //Print first line per existing file file
        Stream<String> files = Stream.of("file1.txt", "file2.txt", "file3.txt");

//        firstTry(files);
//
//        secondTryMonadTry(files);
//
//        moreFunctionalProgramming(files);

//        evenMoreFunctional(files);

//        results(secondTryMonadTry(files));
        results(evenMoreFunctional(files));
    }

    private static Stream<Try<String>> evenMoreFunctional(Stream<String> files) {
        return files
                .map(file -> Try.of(() -> new FileReader(file)))
                .map(t -> t.map(BufferedReader::new))
                .map(t -> t.map(BufferedReader::readLine, BufferedReader::close));
    }

    private static Stream<Try<String>> moreFunctionalProgramming(Stream<String> files) {
        return files
                .map(file -> Try.of(() -> new FileReader(file)))
                .map(t -> t.isSuccess() ? Try.success(new BufferedReader(t.get())) : Try.<BufferedReader>failure(t.reasonOfFail()))
                .map(t -> {
                    if (t.isSuccess()) {
                        return Try.of(() -> t.get().readLine());
                    } else {
                        return Try.<String>failure(t.reasonOfFail());
                    }
                })
                ;
    }

    private static void results(Stream<Try<String>> lines) {
        Map<Boolean, List<Try<String>>> splited = lines.collect(Collectors.partitioningBy(Try::isSuccess));

        splited.get(Boolean.TRUE).stream().map(Try::get).forEach(System.out::println);
        splited.get(Boolean.FALSE).stream().map(Try::reasonOfFail).forEach(System.out::println);
    }

    private static Stream<Try<String>> secondTryMonadTry(Stream<String> files) {
        return files
                .map(file -> {
                    try {
                        return Try.success(new FileReader(file));
                    } catch (FileNotFoundException e) {
                        return Try.<FileReader>failure(e);
                    }
                })
                .map(t -> t.isSuccess() ? Try.success(new BufferedReader(t.get())) : Try.<BufferedReader>failure(t.reasonOfFail()))
                .map(t -> {
                    if (t.isSuccess()) {
                        try {
                            return Try.success(t.get().readLine());
                        } catch (IOException e) {
                            return Try.<String>failure(e);
                        }
                    } else {
                        return Try.<String>failure(t.reasonOfFail());
                    }
                });
    }

    private static Stream<String> firstTry(Stream<String> files) {
        return files
                .map(file -> {
                    try {
                        return Optional.of(new FileReader(file));
                    } catch (FileNotFoundException e) {
                        return Optional.<FileReader>empty();
                    }
                })
                .filter(Optional::isPresent).map(Optional::get)
                .map(BufferedReader::new)
                .map(reader -> {
                    try {
                        return Optional.of(reader.readLine());
                    } catch (IOException e) {
                        return Optional.<String>empty();
                    }
                })
                .filter(Optional::isPresent).map(Optional::get);
    }
}
