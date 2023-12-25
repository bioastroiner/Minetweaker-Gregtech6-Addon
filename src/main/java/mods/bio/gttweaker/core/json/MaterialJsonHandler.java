package mods.bio.gttweaker.core.json;

import com.google.gson.GsonBuilder;
import gregapi.oredict.OreDictMaterial;

public class MaterialJsonHandler {
	public static void init(){
		String source = "";
		MaterialJson json = new GsonBuilder().setPrettyPrinting().create().fromJson(source,MaterialJson.class);
		OreDictMaterial.createMaterial(json.id.intValue(),json.name,json.name);
	}
}

class MaterialJson {
	Number id;
	String name;
	Object color;
	Object colorFluid;
	Object colorSolid;
	Object colorPlasma;
	Object colorGas;
	Object mod;
	Object formula;
	Object oreMultiplier;
	Object oreMultiplierProcessing;
	Object burn;
	Object tool;
	Object quality;
	Object duribility;
	Object speed;
	Object heatDamage;
	Object hidden;
	Object gramPerCubicCentimeter;
	Object melting;
	Object boiling;
	Object plasma;
	Object priorityPrefix;
	Object liquidU;
	Object gasU;
	Object plasmaU;
	Number neutrons;
	Number protons;
	Number electrons;
	public MaterialJson(){
		// process
		StringBuilder builder;


//		if(color != null){
//			if (color.getClass().isArray()){
//				Object[] colors = (Object[])color;
//				for (int i = 0; i < ((Object[]) color).length; i++) {
//					if(colors[i] instanceof Number)
//				}
//			}
//			if(color instanceof String){
//				// 0xFFFFFF
//				Integer.decode(color)
//			}
//		}

		if(liquidU instanceof String){
			Long.parseLong(((String) liquidU).toUpperCase().replace(" ","").replace("U",""));
		}

	}
}