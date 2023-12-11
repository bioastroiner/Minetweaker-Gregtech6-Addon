## Bracket Handlers
###### [Material Bracket Handler]()
    // TODO: 
###### [Prefix Bracket Handler]()
    // TODO: 
###### [RecipeMap Bracket Handler](src/main/java/mods/bio/gttweaker/recipe/bracket/CTRecipeMapBracketHandler.java)
This bracket handler will return an instance of `mods.gregtech.recipe.RecipeMap`

To Actually work with RecipeMap in gt6 you should know what they are actually called.
RecipeMaps in GT6 are named in `gt.recipe.NAME` or `gt.fuelmap.NAME` and so on.
You can use the ingame commands such as `/mt gt recipemaps` command to get a verbous list of all registered gt6 RecipeMaps.

Alternatively for the sake of scripting ease, the handler has smart resolving abilities such as it can automaticaly resolve that `gt.recipe` or `gt.fuel` is redundent so in case the name was not exact it checks only the last part of the recipe map's name.
for example RecipeMap object for `<recipemap:gt.recipe.centrifuge>` can be written as `<recipemap:centrifuge>`, It will automatically resolve the closest recipe map it can find it is advised to not do this however in case of a possible conflict (which means whatever recipe map that gets prioritized will be loaded first).
```zs
// to create the handler
// <recipemap:{NAME}>

// these are the same objects!!
var b = (<recipemap:centrifuge> == <recipemap:gt.recipe.centrifuge>);
print(b); // b = true

var recipe = something.somethingrecipeFactory.build() 
        as mods.gregtech.recipe.Recipe;

<recipemap:centrifuge>.add(recipe); // register recipe to a recipe map independently
<recipemap:centrifuge>.factory....buildAndRegister(); // or directly call a factory and register it at once!

// useful information about recipemaps:
// you need to care about these info when
// creating a new recipe otherwise it will result in an error
// you can get this info from /mt gt recipmaps command too
var map = <recipemap:centrifuge>;
map.minInputs ...
map.maxInputs...
map...minOutputs...
map.....minFluidOutputs...
... and so on
```