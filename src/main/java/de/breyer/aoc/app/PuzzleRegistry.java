package de.breyer.aoc.app;

import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;

public class PuzzleRegistry {

    private static final PuzzleRegistry INSTANCE = new PuzzleRegistry();

    public static PuzzleRegistry getInstance() {
        return INSTANCE;
    }

    private final HashMap<String, AbstractAocPuzzle> registry;

    private PuzzleRegistry() {
        registry = new HashMap<>();
        findPuzzles();
    }

    private void findPuzzles() {
        Reflections reflections = new Reflections("de.breyer.aoc");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(AocPuzzle.class);
        types.forEach(this::registerPuzzle);
    }

    private void registerPuzzle(Class<?> type) {
        try {
            String name = type.getAnnotation(AocPuzzle.class).value();
            AbstractAocPuzzle puzzle = (AbstractAocPuzzle) type.getDeclaredConstructor().newInstance();
            registry.put(name, puzzle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AbstractAocPuzzle getPuzzle(String name) {
        return registry.get(name);
    }

    public void printRegistry() {
        System.out.println();
        System.out.println("In der Registry sind " + registry.size() + " Puzzle registriert");
        registry.forEach((key, value) -> System.out.println(key));
        System.out.println();
    }
}
