package web;

import com.google.gson.Gson;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import java.util.List;
import java.util.Random;

public class Server extends Jooby {

    public Server() {

        install(new GsonModule());

        mount(new StaticAssetModule());

        // Sample list of jokes (this can be loaded from a file later)
        List<String> jokes = List.of(
            "Yo mama’s so fat, when she skips a meal, the stock market drops.",
            "Yo mama’s so poor, she can’t even afford to listen to free podcasts.",
            "Yo mama’s so hairy, Bigfoot takes pictures of her.",
            "Yo mama’s so dumb, she thinks a quarterback is a refund."
        );

        // Endpoint to serve random jokes
        get("/random-joke", ctx -> {
            Random random = new Random();
            String randomJoke = jokes.get(random.nextInt(jokes.size()));
            
            // Convert JokeResponse to JSON using Gson
            Gson gson = require(Gson.class);
            String jsonResponse = gson.toJson(new JokeResponse(randomJoke));

            // Send the JSON response
            ctx.setResponseType("application/json");
            return ctx.send(jsonResponse);
        });
    }

    public static void main(String[] args) {
        System.out.println("\nStarting Server.");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8087))
                .start();
    }

    // Response class to format the JSON
    static class JokeResponse {
        public String joke;

        public JokeResponse(String joke) {
            this.joke = joke;
        }
    }
}
