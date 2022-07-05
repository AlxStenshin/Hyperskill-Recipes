package ru.alxstn.recipe.recipe;

import ru.alxstn.recipe.user.User;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipes")
@JsonView(RecipeJsonViews.InfoView.class)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(RecipeJsonViews.IdOnlyView.class)
    private long id;

    @NotBlank
    @JsonView(RecipeJsonViews.OnCreateView.class)
    private String name;

    @NotBlank
    @JsonView(RecipeJsonViews.OnCreateView.class)
    private String category;

    @NotBlank
    @JsonView(RecipeJsonViews.OnCreateView.class)
    private String description;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotEmpty
    @ElementCollection
    @Column(name = "ingredient")
    @JsonView(RecipeJsonViews.OnCreateView.class)
    private List<String> ingredients;

    @NotEmpty
    @ElementCollection
    @Column(name = "direction")
    @JsonView(RecipeJsonViews.OnCreateView.class)
    private List<String> directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(RecipeJsonViews.InternalView.class)
    private User user;
}