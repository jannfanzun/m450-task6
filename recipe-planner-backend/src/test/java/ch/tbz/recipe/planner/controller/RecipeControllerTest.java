package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void getRecipes_returnsListOfRecipes() throws Exception {
        List<Recipe> recipes = List.of(new Recipe(UUID.randomUUID(), "Test Recipe", "Description", "image_url", List.of()));
        when(recipeService.getRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'Test Recipe'}]"));
    }

    @Test
    void getRecipe_returnsRecipeById() throws Exception {
        UUID recipeId = UUID.randomUUID();
        Recipe recipe = new Recipe(recipeId, "Test Recipe", "Description", "image_url", List.of());
        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        mockMvc.perform(get("/api/recipes/recipe/" + recipeId))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Test Recipe'}"));
    }

    @Test
    void addRecipe_createsNewRecipe() throws Exception {
        Recipe recipe = new Recipe(UUID.randomUUID(), "New Recipe", "New Description", "new_image_url", List.of());
        when(recipeService.addRecipe(recipe)).thenReturn(recipe);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + recipe.getId() + "\",\"name\":\"New Recipe\",\"description\":\"New Description\",\"imageUrl\":\"new_image_url\",\"ingredients\":[]}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + recipe.getId() + "\",\"name\":\"New Recipe\",\"description\":\"New Description\",\"imageUrl\":\"new_image_url\",\"ingredients\":[]}"));
    }
}