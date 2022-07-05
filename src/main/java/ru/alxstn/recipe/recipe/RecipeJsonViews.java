package ru.alxstn.recipe.recipe;

public class RecipeJsonViews {
    public interface IdOnlyView { }
    public interface OnCreateView  { }
    public interface InfoView extends OnCreateView { }
    public interface InternalView { }
}