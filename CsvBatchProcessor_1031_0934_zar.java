// 代码生成时间: 2025-10-31 09:34:43
package com.example.batchprocessor;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import play.libs.Files;
import play.libs.akka.Akka;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files as JavaFiles;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class CsvBatchProcessor extends Controller {

    private final ActorSystem system = Akka.system();
    private final Materializer materializer = ActorMaterializer.create(system);
    private final Executor executor = system.dispatcher();

    public CompletionStage<Result> uploadAndProcess(Http.MultipartFormData<File> multipartFormData) {
        return processCsvFiles(multipartFormData.getFiles()).run(materializer, executor);
    }

    private Source<CsvFileResult, ?> processCsvFiles(List<Http.MultipartFormData.FilePart<File>> files) {
        return Source.from(files)
                .mapAsync(parallelism -> 1, this::processFile)
                .map(CsvFileResult::new);
    }

    private Consumer<CsvFileResult> processFile(Http.MultipartFormData.FilePart<File> filePart) {
        return result -> {
            try {
                Path tempFile = Files.temporaryFile(filePart.getFilename(), "csv");
                filePart.getFile().copyTo(tempFile, true);

                try (CSVParser parser = CSVParser.parse(tempFile, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
                    for (CSVRecord record : parser) {
                        // Process each record, for example:
                        // saveRecordToDatabase(record);
                        // or perform other business logic
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }

                result.success(tempFile.getFileName().toString());
            } catch (Exception e) {
                result.failure(e.getMessage());
            }
        };
    }

    public static class CsvFileResult {
        private final String success;
        private final String failure;

        private CsvFileResult(String success) {
            this.success = success;
            this.failure = null;
        }

        private CsvFileResult(String failure) {
            this.success = null;
            this.failure = failure;
        }
    }
}
