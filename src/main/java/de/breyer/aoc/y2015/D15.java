package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_15")
public class D15 extends AbstractAocPuzzle {

    private Map<Ingredient, Integer> bestRecipe;

    @Override
    protected void partOne() {
        var ingredients = parseInput();
        bestRecipe = null;
        findBestRecipe(new int[ingredients.size()], 100, ingredients, 0, -1);
        printBestRecipe();
    }

    private List<Ingredient> parseInput() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String line : lines) {
            var split = line.split(" ");
            ingredients.add(new Ingredient(split[0].replace(":", ""), readInt(split[2]), readInt(split[4]), readInt(split[6]), readInt(split[8]),
                    readInt(split[10])));
        }
        return ingredients;
    }

    private int readInt(String s) {
        return Integer.parseInt(s.replace(",", ""));
    }

    private void findBestRecipe(int[] currentPermutation, int remainingPercentage, List<Ingredient> ingredients, int index, int calorieTarget) {
        if (index == ingredients.size() - 1) {
            currentPermutation[index] = remainingPercentage;

            Map<Ingredient, Integer> recipe = new HashMap<>();
            for (int i = 0; i < ingredients.size(); i++) {
                recipe.put(ingredients.get(i), currentPermutation[i]);
            }

            var sumCalories = recipe.entrySet().stream().map(entry -> entry.getKey().calories() * entry.getValue()).reduce(0, Integer::sum);

            if (null == bestRecipe) {
                bestRecipe = recipe;
            } else if (calorieTarget == -1 || sumCalories == calorieTarget) {
                if (scoreRecipe(recipe) > scoreRecipe(bestRecipe)) {
                    bestRecipe = recipe;
                }
            }

        } else {
            for (int percentage = 0; percentage <= remainingPercentage; percentage++) {
                currentPermutation[index] = percentage;
                findBestRecipe(currentPermutation, remainingPercentage - percentage, ingredients, index + 1, calorieTarget);
            }
        }
    }

    private long scoreRecipe(Map<Ingredient, Integer> recipe) {
        long capacitySum = 0;
        long durabilitySum = 0;
        long flavorSum = 0;
        long textureSum = 0;

        for (Map.Entry<Ingredient, Integer> entry : recipe.entrySet()) {
            capacitySum += (long) entry.getKey().capacity() * entry.getValue();
            durabilitySum += (long) entry.getKey().durability() * entry.getValue();
            flavorSum += (long) entry.getKey().flavor() * entry.getValue();
            textureSum += (long) entry.getKey().texture() * entry.getValue();
        }
        return Math.max(capacitySum, 0) * Math.max(durabilitySum, 0) * Math.max(flavorSum, 0) * Math.max(textureSum, 0);
    }

    private void printBestRecipe() {
        System.out.println("Best recipe(" + scoreRecipe(bestRecipe) + "):");
        bestRecipe.forEach((key, value) -> System.out.println(key.name() + ": " + value));
    }

    @Override
    protected void partTwo() {
        var ingredients = parseInput();
        bestRecipe = null;
        findBestRecipe(new int[ingredients.size()], 100, ingredients, 0, 500);
        printBestRecipe();
    }

}
