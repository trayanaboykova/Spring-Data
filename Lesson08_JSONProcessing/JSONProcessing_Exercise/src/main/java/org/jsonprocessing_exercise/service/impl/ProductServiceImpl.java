package org.jsonprocessing_exercise.service.impl;

import com.google.gson.Gson;
import org.jsonprocessing_exercise.data.entities.Category;
import org.jsonprocessing_exercise.data.entities.Product;
import org.jsonprocessing_exercise.data.entities.User;
import org.jsonprocessing_exercise.data.repositories.CategoryRepository;
import org.jsonprocessing_exercise.data.repositories.ProductRepository;
import org.jsonprocessing_exercise.data.repositories.UserRepository;
import org.jsonprocessing_exercise.service.ProductService;
import org.jsonprocessing_exercise.service.dtos.ProductSeedDto;
import org.jsonprocessing_exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl implements ProductService {
    // constants
    private static final String FILE_PATH = "src/main/resources/json/products.json";

    // beans
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        if (this.productRepository.count() == 0) {
            ProductSeedDto[] productSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), ProductSeedDto[].class);

            for (ProductSeedDto productSeedDto : productSeedDtos) {
                if (!this.validationUtil.isValid(productSeedDto)) {
                    this.validationUtil.getViolations(productSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Product product = this.modelMapper.map(productSeedDto, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategories());

                this.productRepository.saveAndFlush(product);
            }
        }
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int randomCount = ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 0; i < randomCount ; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(randomId).get());
        }

        return categories;
    }

    private User getRandomUser(boolean isBuyer) {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return isBuyer && randomId % 4 == 0 ? null : this.userRepository.findById(randomId).get();
    }
}
