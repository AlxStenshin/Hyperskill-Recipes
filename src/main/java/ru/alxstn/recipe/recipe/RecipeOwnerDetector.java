package ru.alxstn.recipe.recipe;

import ru.alxstn.recipe.security.RecipeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "recipeOwnerDetector")
public class RecipeOwnerDetector {
    private final RecipeService recipeService;

    @Autowired
    public RecipeOwnerDetector(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public boolean isOwner(long id, RecipeUserDetails userDetails) {
        Recipe toModify = recipeService.get(id);
        return toModify.getUser().getEmail().equals(userDetails.getUsername());
    }
}
