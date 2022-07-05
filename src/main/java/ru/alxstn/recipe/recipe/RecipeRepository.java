package ru.alxstn.recipe.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    // Using Query Methods
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    // Query format: findBy{}IgnoreCaseOrderBy  ----> returns a list<E>

    @Transactional(readOnly = true)
    // Category Search -> Full match meaning the strings are equal when case is ignored.
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    @Transactional(readOnly = true)
    // Name Search -> partial match meaning you want to find a string "Containing" the given name.
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

    @Transactional(readOnly = true)
    List<Recipe> findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(String name, String category);
}
