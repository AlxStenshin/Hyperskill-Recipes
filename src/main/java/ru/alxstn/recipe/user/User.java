package ru.alxstn.recipe.user;

import ru.alxstn.recipe.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    private static final String emailRegex = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}";

    @Id
    @Email(regexp = emailRegex)
    @NotBlank
    @JsonView(UserJsonViews.RegistrationView.class)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    @JsonView(UserJsonViews.RegistrationView.class)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;

}
