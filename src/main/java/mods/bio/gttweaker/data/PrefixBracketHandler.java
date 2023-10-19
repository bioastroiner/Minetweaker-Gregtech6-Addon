package mods.bio.gttweaker.data;

import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.runtime.GlobalRegistry;
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
public class PrefixBracketHandler implements IBracketHandler {

	public static IPrefix getPrefix(String name) {
		return new MCPrefix(name);
	}

	@Override
	public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
		return tokens.size() > 2 && tokens.get(0).getValue().equals("prefix") && tokens.get(1).getValue().equals(":") ? this.find(environment, tokens, 2, tokens.size()) : null;
	}

	private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();

		for (int i = startIndex; i < endIndex; ++i) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}

		return new PrefixReferenceSymbol(environment, valueBuilder.toString());
	}

	private class PrefixReferenceSymbol implements IZenSymbol {
		private final IEnvironmentGlobal environment;
		private final String name;

		public PrefixReferenceSymbol(IEnvironmentGlobal environment, String name) {
			this.environment = environment;
			this.name = name;
		}

		public IPartialExpression instance(ZenPosition position) {
			IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(),
					PrefixBracketHandler.class,
					"getPrefix",
					String.class);
			return new ExpressionCallStatic(position, this.environment, method, new ExpressionString(position, this.name));
		}
	}
}

