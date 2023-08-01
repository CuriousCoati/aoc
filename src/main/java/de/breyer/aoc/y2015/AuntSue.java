package de.breyer.aoc.y2015;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AuntSue {

    private int number;
    private Integer children;
    private Integer cats;
    private Integer samoyeds;
    private Integer pomeranians;
    private Integer akitas;
    private Integer vizslas;
    private Integer goldfish;
    private Integer trees;
    private Integer cars;
    private Integer perfumes;

    public boolean match(AuntSue auntSue) {
        return (auntSue.children == null || Objects.equals(children, auntSue.children))
                && (auntSue.cats == null || Objects.equals(cats, auntSue.cats))
                && (auntSue.samoyeds == null || Objects.equals(samoyeds, auntSue.samoyeds))
                && (auntSue.pomeranians == null || Objects.equals(pomeranians, auntSue.pomeranians))
                && (auntSue.akitas == null || Objects.equals(akitas, auntSue.akitas))
                && (auntSue.vizslas == null || Objects.equals(vizslas, auntSue.vizslas))
                && (auntSue.goldfish == null || Objects.equals(goldfish, auntSue.goldfish))
                && (auntSue.trees == null || Objects.equals(trees, auntSue.trees))
                && (auntSue.cars == null || Objects.equals(cars, auntSue.cars))
                && (auntSue.perfumes == null || Objects.equals(perfumes, auntSue.perfumes));
    }

    public boolean matchTwo(AuntSue auntSue) {
        return (auntSue.children == null || Objects.equals(children, auntSue.children))
                && (auntSue.cats == null || auntSue.cats > cats)
                && (auntSue.samoyeds == null || Objects.equals(samoyeds, auntSue.samoyeds))
                && (auntSue.pomeranians == null || auntSue.pomeranians < pomeranians)
                && (auntSue.akitas == null || Objects.equals(akitas, auntSue.akitas))
                && (auntSue.vizslas == null || Objects.equals(vizslas, auntSue.vizslas))
                && (auntSue.goldfish == null || auntSue.goldfish < goldfish)
                && (auntSue.trees == null || auntSue.trees > trees)
                && (auntSue.cars == null || Objects.equals(cars, auntSue.cars))
                && (auntSue.perfumes == null || Objects.equals(perfumes, auntSue.perfumes));
    }
}
