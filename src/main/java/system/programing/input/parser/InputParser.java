package system.programing.input.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InputParser {

    private static final String API_URL = "https://lindat.mff.cuni.cz/services/udpipe/api/process";
    private static final String MODEL = "ukrainian-iu-ud-2.12-230717";
    private final String input;

    public InputParser(String input) {
        this.input = input.replaceAll("=", "дорівнює").replaceAll("–", "дорівнює")
                .replaceAll("-", "дорівнює").replaceAll("∠", "кут ");
    }

    public List<List<Lemma>> parse() throws IOException, InterruptedException {
        List<List<Lemma>> list = new ArrayList<>();
        for (String str : input.split("\\.")) {
            for (String str1 : str.split(",")) {
                String encodedModel = URLEncoder.encode(MODEL, StandardCharsets.UTF_8);
                String encodedData = URLEncoder.encode(str1, StandardCharsets.UTF_8);

                StringBuilder paramsBuilder = new StringBuilder();
                paramsBuilder.append("model=").append(encodedModel);
                paramsBuilder.append("&tokenizer");
                paramsBuilder.append("&tagger");
                paramsBuilder.append("&parser");
                paramsBuilder.append("&data=").append(encodedData);

                String params = paramsBuilder.toString();

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(params))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    list.add(Lemma.getLemmasFromJSON(response.body()));
                } else {
                    throw new RuntimeException("Статус код: " + response.statusCode() + "Тіло відповіді: " + response.body());
                }
            }
        }
        return list;
    }
}
