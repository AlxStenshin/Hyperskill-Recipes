package ru.alxstn.recipe.recipe;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe get(Long id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    public Recipe add(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Recipe newValue) {
        Recipe toUpdate = get(id);
        toUpdate.setName(newValue.getName());
        toUpdate.setDescription(newValue.getDescription());
        toUpdate.setCategory(newValue.getCategory());
        toUpdate.setIngredients(newValue.getIngredients());
        toUpdate.setDirections(newValue.getDirections());
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByNameAndCategory(String name, String category) {
        return recipeRepository.findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(name, category);
    }
}
