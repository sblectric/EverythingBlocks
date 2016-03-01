package com.EverythingBlocks.crafting;

import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

import com.EverythingBlocks.api.EverythingBlocksAPI;
import com.EverythingBlocks.ref.RefStrings;

public class EBCraftingManager {

	/** Add all valid block recipes */
	public static void addRecipes() {

		// Everything Block Recipes
		GameRegistry.addRecipe(new EverythingBlockCraftingRecipes());
		GameRegistry.addRecipe(new EverythingBlockDecraftingRecipes());
		GameRegistry.addRecipe(new EverythingSlabCraftingRecipes());
		GameRegistry.addRecipe(new EverythingStairCraftingRecipes());
		GameRegistry.addRecipe(new EverythingStairDecraftingRecipes());
		RecipeSorter.register(RefStrings.MODID + ":EBCrafting", EverythingBlockCraftingRecipes.class, Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register(RefStrings.MODID + ":EBDecrafting", EverythingBlockDecraftingRecipes.class, Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register(RefStrings.MODID + ":EBSlabCrafting", EverythingSlabCraftingRecipes.class, Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register(RefStrings.MODID + ":EBStairCrafting", EverythingStairCraftingRecipes.class, Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register(RefStrings.MODID + ":EBStairDecrafting", EverythingStairDecraftingRecipes.class, Category.SHAPELESS, "after:minecraft:shapeless");
		
		// direct the API to these recipes
		EverythingBlocksAPI.craftingRecipes = new EverythingBlockCraftingRecipes();
		EverythingBlocksAPI.decraftingRecipes = new EverythingBlockDecraftingRecipes();
		EverythingBlocksAPI.craftingRecipesSlabs = new EverythingSlabCraftingRecipes();
		EverythingBlocksAPI.craftingRecipesStairs = new EverythingStairCraftingRecipes();
		EverythingBlocksAPI.decraftingRecipesStairs = new EverythingStairDecraftingRecipes();
		
	}

	/** Find recipes excluding one or more recipe types */
	public static ItemStack findMatchingRecipeExcluding(InventoryCrafting inv, World world, Class... excluding) {
		int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;
		int j;
		List recipes = CraftingManager.getInstance().getRecipeList();

		for (j = 0; j < inv.getSizeInventory(); ++j)
		{
			ItemStack itemstack2 = inv.getStackInSlot(j);

			if (itemstack2 != null)
			{
				if (i == 0)
				{
					itemstack = itemstack2;
				}

				if (i == 1)
				{
					itemstack1 = itemstack2;
				}

				++i;
			}
		}

		if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
		{
			Item item = itemstack.getItem();
			int j1 = item.getMaxDamage() - itemstack.getItemDamage(); // getItemDamageForDisplay
			int k = item.getMaxDamage() - itemstack1.getItemDamage();
			int l = j1 + k + item.getMaxDamage() * 5 / 100;
			int i1 = item.getMaxDamage() - l;

			if (i1 < 0)
			{
				i1 = 0;
			}

			return new ItemStack(itemstack.getItem(), 1, i1);
		}
		else
		{
			for (j = 0; j < recipes.size(); ++j)
			{
				IRecipe irecipe = (IRecipe)recipes.get(j);

				// 1.1.1 Botania fix (try catch)
				try {
					if((!Arrays.asList(excluding).contains(irecipe.getClass())) && irecipe.matches(inv, world))
					{
						return irecipe.getCraftingResult(inv);
					}
				}
				catch (Exception e) { }
			}

			return null;
		}
	}

}
