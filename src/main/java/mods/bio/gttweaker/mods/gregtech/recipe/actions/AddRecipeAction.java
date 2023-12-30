package mods.bio.gttweaker.mods.gregtech.recipe.actions;

import gregapi.recipes.Recipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.api.mods.gregtech.recipe.IRecipe;
import mods.bio.gttweaker.core.RecipeHelper;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipeMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AddRecipeAction implements IUndoableAction {

	private final Recipe mRecipe;
	private final Recipe.RecipeMap mRecipeMap;
	private boolean success_do = false;
	// unused boolean
	private boolean success_udo = false;

	public AddRecipeAction(Recipe mRecipe, Recipe.RecipeMap mRecipeMap) {
		this.mRecipe = mRecipe;
		this.mRecipeMap = mRecipeMap;
	}

	public static boolean addRecipe(Recipe aRecipe, Recipe.RecipeMap aRecipeMap) {
		AddRecipeAction action = new AddRecipeAction(aRecipe, aRecipeMap);
		MineTweakerAPI.apply(action);
		return action.success_do;
	}

	@Override
	public void apply() {
		// due to collision checking internally,
		// we do not need to check for recipe's existence here
		success_do = mRecipeMap.addRecipe(mRecipe) != null;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void undo() {
		// Only remove if do was success
		// this is for case when the recipe that was added
		// was already existing
		if (success_do) RecipeHelper.removeGTRecipe(mRecipe, mRecipeMap);
	}

	@Override
	public String describe() {
		return String.format("Adding Recipe %s to RecipeMap: %s", CTRecipe.format(mRecipe), CTRecipeMap.format(mRecipeMap));
	}

	@Override
	public String describeUndo() {
		return String.format("Reverting Recipe %s from RecipeMap: %s", CTRecipe.format(mRecipe), CTRecipeMap.format(mRecipeMap));
	}

	@Override
	public Object getOverrideKey() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AddRecipeAction that = (AddRecipeAction) o;

		return new EqualsBuilder().append(mRecipe, that.mRecipe).append(mRecipeMap, that.mRecipeMap).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(mRecipe).append(mRecipeMap).toHashCode();
	}
}
