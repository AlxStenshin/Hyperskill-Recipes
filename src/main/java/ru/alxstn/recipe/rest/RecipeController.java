package ru.alxstn.recipe.rest;

import ru.alxstn.recipe.recipe.Recipe;
import ru.alxstn.recipe.recipe.RecipeJsonViews;
import ru.alxstn.recipe.recipe.RecipeService;
import ru.alxstn.recipe.user.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @JsonView(RecipeJsonViews.InfoView.class)
    public Recipe getRecipe(@Min(1L) @PathVariable long id) {
        return recipeService.get(id);
    }

    @GetMapping("/search")
    @JsonView(RecipeJsonViews.InfoView.class)
    public ResponseEntity<List<Recipe>> search(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String category) {
        if ((name == null) && (category == null))
            return ResponseEntity.badRequest().build();

        List<Recipe> result;
        if (category == null) {
            result = recipeService.searchByName(name);
        }
        else if (name == null) {
            result = recipeService.searchByCategory(category);
        }
        else result = recipeService.searchByNameAndCategory(name, category);

        return ResponseEntity.ok(result);
    }

    @JsonView(RecipeJsonViews.IdOnlyView.class)
    @PostMapping(value = "/new")
    public Recipe newRecipe(@Valid @JsonView(RecipeJsonViews.OnCreateView.class)
                            @RequestBody Recipe recipe,
                            @AuthenticationPrincipal UserDetails details) {
        User authUser = new User();
        authUser.setEmail(details.getUsername());
        authUser.setPassword(details.getPassword());

        recipe.setUser(authUser);
        return recipeService.add(recipe);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@recipeOwnerDetector.isOwner(#id, principal)")
    public void updateRecipe(@PathVariable long id,
                             @Valid @RequestBody Recipe recipe) {
        recipeService.update(id, recipe);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@recipeOwnerDetector.isOwner(#id, principal)")
    public void deleteRecipe(@Min(1L) @PathVariable long id) {
        recipeService.delete(id);
    }

}
