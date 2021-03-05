package com.github.spring.components.lightweight.test.sample.translated;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.translate.v3.CreateGlossaryMetadata;
import com.google.cloud.translate.v3.CreateGlossaryRequest;
import com.google.cloud.translate.v3.GcsSource;
import com.google.cloud.translate.v3.Glossary;
import com.google.cloud.translate.v3.GlossaryInputConfig;
import com.google.cloud.translate.v3.GlossaryName;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslationServiceClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class GoogleGlossary {

    public static void createGlossary() throws InterruptedException, ExecutionException, IOException {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "YOUR-PROJECT-ID";
        String glossaryId = "your-glossary-display-name";
        List<String> languageCodes = new ArrayList<>();
        languageCodes.add("en");
        String inputUri = "gs://your-gcs-bucket/path/to/input/file.txt";
        createGlossary(projectId, glossaryId, languageCodes, inputUri);
    }

    // Create a equivalent term sets glossary
    // https://cloud.google.com/translate/docs/advanced/glossary#format-glossary
    public static void createGlossary(
            String projectId, String glossaryId, List<String> languageCodes, String inputUri)
            throws IOException, ExecutionException, InterruptedException {

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            String location = "us-central1";
            LocationName parent = LocationName.of(projectId, location);
            GlossaryName glossaryName = GlossaryName.of(projectId, location, glossaryId);

            // Supported Languages: https://cloud.google.com/translate/docs/languages
            Glossary.LanguageCodesSet languageCodesSet =
                    Glossary.LanguageCodesSet.newBuilder().addAllLanguageCodes(languageCodes).build();

            // Configure the source of the file from a GCS bucket
            GcsSource gcsSource = GcsSource.newBuilder().setInputUri(inputUri).build();
            GlossaryInputConfig inputConfig =
                    GlossaryInputConfig.newBuilder().setGcsSource(gcsSource).build();

            Glossary glossary =
                    Glossary.newBuilder()
                            .setName(glossaryName.toString())
                            .setLanguageCodesSet(languageCodesSet)
                            .setInputConfig(inputConfig)
                            .build();

            CreateGlossaryRequest request =
                    CreateGlossaryRequest.newBuilder()
                            .setParent(parent.toString())
                            .setGlossary(glossary)
                            .build();

            // Start an asynchronous request
            OperationFuture<Glossary, CreateGlossaryMetadata> future =
                    client.createGlossaryAsync(request);

            System.out.println("Waiting for operation to complete...");
            Glossary response = future.get();
            System.out.println("Created Glossary.");
            System.out.printf("Glossary name: %s\n", response.getName());
            System.out.printf("Entry count: %s\n", response.getEntryCount());
            System.out.printf("Input URI: %s\n", response.getInputConfig().getGcsSource().getInputUri());
        }
    }
}