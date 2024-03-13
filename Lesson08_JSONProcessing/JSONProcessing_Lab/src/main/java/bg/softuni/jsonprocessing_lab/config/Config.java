package bg.softuni.jsonprocessing_lab.config;

import bg.softuni.jsonprocessing_lab.services.AddressService;
import bg.softuni.jsonprocessing_lab.services.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson createGSON() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean("withNulls")
    @Primary
    public Gson createGSONWithNulls() {
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    @Bean("withoutNulls")
    public Gson createGSONWithoutNulls() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public PersonService personService(
            AddressService addressService,
            @Value("${yourproject.yourkey.config1}") String config1) {
        System.out.println(config1);
        return new PersonService(addressService);
    }
}
