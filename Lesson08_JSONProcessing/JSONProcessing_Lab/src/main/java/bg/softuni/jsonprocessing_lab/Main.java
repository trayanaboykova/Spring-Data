package bg.softuni.jsonprocessing_lab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder().create();

        
    }
}
