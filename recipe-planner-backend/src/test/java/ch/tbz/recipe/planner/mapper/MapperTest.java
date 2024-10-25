package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

public class MapperTest {

    private final RecipeEntityMapper recipeMapper = Mappers.getMapper(RecipeEntityMapper.class);
    private final IngredientEntityMapper ingredientMapper = Mappers.getMapper(IngredientEntityMapper.class);

    @Test
    void recipeEntityToDomain() {
        RecipeEntity entity = new RecipeEntity(UUID.randomUUID(), "Test Recipe", "Description", "image_url", List.of());
        Recipe domain = recipeMapper.entityToDomain(entity);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(domain.getId()).isEqualTo(entity.getId());
        softly.assertThat(domain.getName()).isEqualTo(entity.getName());
        softly.assertThat(domain.getDescription()).isEqualTo(entity.getDescription());
        softly.assertThat(domain.getIngredients()).isEqualTo(entity.getIngredients());
        softly.assertAll();
    }

    @Test
    void recipeDomainToEntity() {
        Recipe domain = new Recipe(UUID.randomUUID(), "Test Recipe", "Description", "image_url", List.of());
        RecipeEntity entity = recipeMapper.domainToEntity(domain);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(entity.getId()).isEqualTo(domain.getId());
        softly.assertThat(entity.getName()).isEqualTo(domain.getName());
        softly.assertThat(entity.getDescription()).isEqualTo(domain.getDescription());
        softly.assertThat(entity.getIngredients()).isEqualTo(domain.getIngredients());
        softly.assertAll();
    }

    @Test
    void ingredientDomainToEntity() {
        Ingredient domain = new Ingredient(UUID.randomUUID(), "Tomato", "The big ones", Unit.PIECE, 5);
        IngredientEntity entity = ingredientMapper.domainToEntity(domain);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(entity.getId()).isEqualTo(domain.getId());
        softly.assertThat(entity.getName()).isEqualTo(domain.getName());
        softly.assertThat(entity.getUnit()).isEqualTo(domain.getUnit());
        softly.assertThat(entity.getAmount()).isEqualTo(domain.getAmount());
        softly.assertAll();
    }
}