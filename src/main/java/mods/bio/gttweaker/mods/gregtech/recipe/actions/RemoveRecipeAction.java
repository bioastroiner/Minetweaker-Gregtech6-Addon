package mods.bio.gttweaker.mods.gregtech.recipe.actions;

import gregapi.recipes.Recipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import mods.bio.gttweaker.core.RecipeHelper;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipe;
import mods.bio.gttweaker.mods.gregtech.recipe.CTRecipeMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static mods.bio.gttweaker.core.RecipeHelper.removeGTRecipe;


public class RemoveRecipeAction implements IUndoableAction {
	private final Recipe mRecipe;
	private final Recipe.RecipeMap mRecipeMap;
	private boolean success_do = false;
	// unused boolean
	private boolean success_udo = false;

	public RemoveRecipeAction(Recipe mRecipe, Recipe.RecipeMap mRecipeMap) {
		this.mRecipe = mRecipe;
		this.mRecipeMap = mRecipeMap;
	}

	public static boolean removeRecipeAction(Recipe aRecipe, Recipe.RecipeMap aRecipeMap) {
		//aRecipeMap.mRecipeList.stream().filter(r->r.equals(aRecipe)).findFirst().ifPresent(recipe -> );
		RemoveRecipeAction action = new RemoveRecipeAction(aRecipe, aRecipeMap);
		MineTweakerAPI.apply(action);
		return action.success_do;
	}

	private boolean canRemove() {
		return mRecipeMap.mRecipeList.contains(mRecipe);
	}

	@Override
	public void apply() {
		if (canRemove()) success_do = removeGTRecipe(mRecipe, mRecipeMap);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void undo() {
		// we need to turn of collisions check or it can't reAdd the recipe, it has to do with how gt findRecipeInternal function works...
		success_udo = RecipeHelper.addGTRecipe(mRecipe, mRecipeMap, false);
		if (!success_udo)
			MineTweakerAPI.logWarning("Removed Recipe, Revert was not successful!: " + CTRecipe.format(mRecipe));
	}

	@Override
	public String describe() {
		return String.format("Removing Recipe %s from RecipeMap: %s", CTRecipe.format(mRecipe), CTRecipeMap.format(mRecipeMap));
	}

	@Override
	public String describeUndo() {
		return String.format("Returning Recipe %s to RecipeMap: %s", CTRecipe.format(mRecipe), CTRecipeMap.format(mRecipeMap));
	}

	@Override
	public Object getOverrideKey() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		RemoveRecipeAction action = (RemoveRecipeAction) o;

		return new EqualsBuilder().append(mRecipe, action.mRecipe).append(mRecipeMap, action.mRecipeMap).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(mRecipe).append(mRecipeMap).toHashCode();
	}
}
