# Documentation

------------------
#### Basics
* [ ] [Commands](Commands.md)
* [ ] [Adding/Removing Recipes](Recipes.md)
* [ ] [Adding Materials](Materials.md)
* [ ] [GT Bracket Handlers](Brackets.md)
* [ ] []()
* [ ]
* [ ]
---
Zen API Structure

* `minetweaker.mods.gregtech`
  * `minetweaker.mods.gregtech.oredict`
    * `minetweaker.mods.gregtech.oredict.Material`
    * `minetweaker.mods.gregtech.oredict.Prefix`
    * `minetweaker.mods.gregtech.oredict.Unifier`
  * `minetweaker.mods.gregtech.recipe`
    * `minetweaker.mods.gregtech.recipe.Recipe`
    * `minetweaker.mods.gregtech.recipe.RecipeFactory`
    * `minetweaker.mods.gregtech.recipe.RecipeMap`
    * `minetweaker.mods.gregtech.recipe.RecipeMaps`


---
junks

## Extentions
###### [Extentions to IOreDict](src/main/java/mods/bio/gttweaker/oredict/CTIOreDictExpansion.java)
```zs
// extended functionality added to ore entries
// minetweaker.oredict.IOreDictEntry;

<ore:plateSteel>.material // returns a gt associated material (possibly null)
// it's an instance of mods.gregtech.oredict.Material

<ore:plateGold>.unify // returns a gt unified item, it could be null if its not unified or ore is not valid
// it's an instance of IItemStack
```

## GT OreDict Data Manipulation
I never knew how to call this! Greg calls them OreDictMaterialData and they are associated with a lot of items even automatically!
for example how dose greg figure out that a golden plate gives 2 Units of Gold when macerated? The Answer is **OreDictMaterialData**!.

But what OreDictMaterialData is anyway? it's basically a fancy way of saying things have certain Material and how much of that Material it contains! additionally it also tracks of things called "byproducts" which are also Material and Amounts Bundles that Greg tracks.
For example as said before a Golden Pressure Plate has 2 Units of Gold as its main Material with amount "2U" and no Byproducts!
But consider an item such as a Shovel, it contains 1U of Iron and a byproduct with Material of Wood with amounts of 0.5U (stick).
Also some items can have NULL main material so look for those...

These traits have been **A BIG PAIN IN THE BUTT** in the past for anybody who tried to add recipes with MT into gt.
Why? well as i mentioned this data are set automatically... and they are set **BEFORE** MineTweaker scripts run.
So let's say you remove the recipe for a golden pressure plate to use less gold like 0.5U. You remove its recipe using MineTweaker but gt dosent recognize that as legit on reload! so it still thinks the pressure plate contains 2 U of gold and it gets dupped...

Fortunently in This mod we forced Greg to run his checks even on reload each time and generate the datas more often (for example during `/mt reload`).
So there is nothing you need to do this is an automatic fix.
However we provide you more fucntional and robust functionalities to tweak this even further for your likings!

All you have to do is to use the `mods.gregtech.oredict.Unifier` Utility class which contains custom functions that go trough all the OreDictMaterialDatas and set/remove/update them for your likings.
note that the Unifier class dosent only manipulate the oredict data hense the name Unifier it is also planned to do more work on the Unifier side of things...

```zs
import mods.gregtech.oredict.Unifier;
import mods.gregtech.oredict.Material;

// removes a given material from an item stack
// Unifier. remove(IItemStack aStack, Material aMaterial)
Unifier.remove(<ore:ingotGold>,<material:gold>);

```

```zs
Unifier.add(
            IItemStack, // itemstack to add to
            Material, // <material:x>
            Amount // float amount (How much of U)
);
```
removes any associations from a given item
```zs
Unifier.clear(
            IItemStack
)
```