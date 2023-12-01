package mods.bio.gttweaker.recipe.bracket;

import gregapi.recipes.Recipe;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import minetweaker.runtime.GlobalRegistry;
import mods.bio.gttweaker.recipe.CTRecipeMap;
import mods.bio.gttweaker.recipe.CTRecipeMaps;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

@BracketHandler(priority = 99)
public class CTRecipeMapBracketHandler implements IBracketHandler {

	public static CTRecipeMap getRM(String name){
		if(Recipe.RecipeMap.RECIPE_MAPS.containsKey(name.toLowerCase()))
			return CTRecipeMaps.getRecipeMap(name.toLowerCase());
		MineTweakerAPI.logError("ERROR AT PROCESSING BRACKET HANDLER: " + "<recipemap:" +name + ">");
		MineTweakerAPI.logError("No Such Recipe Map for: " + name +". Returning NULL");
		return null;
	}

	@Override
	public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
		return tokens.size() > 2 && tokens.get(0).getValue()
				.equals("recipemap")
				&& tokens.get(1).getValue().equals(":") ? this.find(environment, tokens, 2, tokens.size()) : null;
	}

	private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();

		for (int i = startIndex; i < endIndex; ++i) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}

		return new CTRecipeMapBracketHandler.RecipeMapReferenceSymbol(environment, valueBuilder.toString());
	}

	private class RecipeMapReferenceSymbol implements IZenSymbol {
		private final IEnvironmentGlobal environment;
		private final String name;

		public RecipeMapReferenceSymbol(IEnvironmentGlobal environment, String name) {
			this.environment = environment;
			this.name = name;
		}

		public IPartialExpression instance(ZenPosition position) {
			IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(),
					CTRecipeMapBracketHandler.class,
					"getRM",
					String.class);
			return new ExpressionCallStatic(position, this.environment, method, new ExpressionString(position, this.name));
		}
	}

}
