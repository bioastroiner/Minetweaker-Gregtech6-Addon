## [Recipe API](src/main/java/mods/bio/gttweaker/recipe)
```zs
// YAG overhelms the functionality of recipes
// over any previous gt6 tweaker addons

// To get started you might wanna see how many 
// types of recipe maps exist in your gt6 instance.
// for that refer back to the COMMANDS section of this doc
// alternatively you can retrive names in script
// for looping porpuses

// retrives a list of all recipe map instances
var maps = mods.gregtech.recipe.RecipeMaps
    .recipemaps;
    
foreach rm in maps {
    print(rm.name);
}

// once you chose the correct name of the recipemap
// you want to add recipe to it

// call a new recipemap refrence
var map = <recipemap:centrifuge>;

// or use the long actual name (this is for compat reasons and yes it will be checked before the short one)
map = <recipemap:gt.recipes.centrifuge>;

// or you can use this method call
map = 
    mods.gregtech.recipe.RecipeMaps
    .getRecipeMap("centrifuge");
```
*Adding Recipes to RecipeMaps*
```zs
var map = <recipemap:press>;
// There is only 1 way of adding recipes to a recipe map:
//      1. Invoking a Factory/Builder
// Unlike MTUtils we no longer pass parameters to long method
// and instead use factories like civilized coders :3

// Factory:
//      1. Create a Factory
        map.factory
//      2. Now Pass parameters to the factory
        .EUt(16) // in EU
        .duration(100) // in tick
        .input() // takes an IItemStack
        .inputs() // takes an IItemStack[]
        .output() // takes an IItemStack
        .outputs() // takes an IItemStack[]
        .inputFluid() // takes ILiquidStack
        .inputFluids() // takes ILiquidStack[]
        .outputFluid() // takes ILiquidStack
        .outputFluids() // takes ILiquidStack[]
//      3. Finally Build and Register the recipe
        .buildAndRegister();
// example:
// remember we can use .unify on IOreDict now to retrive the unified gt item directly
// this will never result in a nullpointer unless the ore is empty (mistyped the ore name)
   
        <recipemap:press>
            .EUt(364)
            .duration(10)
            .inputs([<ore:plateGold>.unify,<ore:plateOsmium>.unify])
            .output(<ore:plateAdamantium>.unify)
            .buildAndRegister();
            
//  however you might ask why we need to call unify everytime we pass the paramters?
// why dosent it do an auto evaluation?
// so... well it actually can! if we dont pass an entry it will automatically evaluate
// since it can never accept real IOreDict as input (and def not output)
// if you wish to use ores as all and not unified you can use a loop like so

// loops, this is unfortunate and because of GT6's own internal design philosophy...
// it just dosen't accept multi items (ores)
for item in <ore:circuitBasic>.items {
        <recipemap:press>
            .EUt(364)
            .duration(10)
            .inputs([item,<ore:plateOsmium>])
            .output(<ore:plateAdamantium>)
            .buildAndRegister();
}
            
```