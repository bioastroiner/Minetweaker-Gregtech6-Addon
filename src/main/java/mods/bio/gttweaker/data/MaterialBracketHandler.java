package mods.bio.gttweaker.data;

import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.runtime.GlobalRegistry;
import org.apache.commons.lang3.math.NumberUtils;
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

@BracketHandler(priority = 100)
public class MaterialBracketHandler implements IBracketHandler {

	public static IMaterial getMaterial(String name) {
		if(NumberUtils.isNumber(name)) return new MCMaterial((short)Integer.parseInt(name));
		return new MCMaterial(name);
	}

	@Override
	public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
		return tokens.size() > 2 && tokens.get(0).getValue().equals("material") && tokens.get(1).getValue().equals(":") ? this.find(environment, tokens, 2, tokens.size()) : null;
	}

	private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();

		for (int i = startIndex; i < endIndex; ++i) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}

		return new MaterialReferenceSymbol(environment, valueBuilder.toString());
	}

	private class MaterialReferenceSymbol implements IZenSymbol {
		private final IEnvironmentGlobal environment;
		private final String name;

		public MaterialReferenceSymbol(IEnvironmentGlobal environment, String name) {
			this.environment = environment;
			this.name = name;
		}

		public IPartialExpression instance(ZenPosition position) {
			IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(),
					MaterialBracketHandler.class,
					"getMaterial",
					String.class);
			return new ExpressionCallStatic(position, this.environment, method, new ExpressionString(position, this.name));
		}
	}
}

