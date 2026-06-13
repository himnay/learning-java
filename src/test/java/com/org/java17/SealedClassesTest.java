// FILE 7: src/test/java/com/org/java17/SealedClassesTest.java
package com.org.java17;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sealed Classes (Java 17)")
class SealedClassesTest {

    // ---------------------------------------------------------------------------
    // Sealed type hierarchy
    // ---------------------------------------------------------------------------

    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {}

    record Rectangle(double width, double height) implements Shape {}

    record Triangle(double base, double height) implements Shape {}

    // A sealed abstract class example
    sealed abstract static class Animal permits Dog, Cat {}

    static final class Dog extends Animal {
        String speak() { return "Woof"; }
    }

    static final class Cat extends Animal {
        String speak() { return "Meow"; }
    }

    // ---------------------------------------------------------------------------
    // Area computation using pattern-matching switch (exhaustive)
    // ---------------------------------------------------------------------------

    static double area(Shape shape) {
        return switch (shape) {
            case Circle c       -> Math.PI * c.radius() * c.radius();
            case Rectangle r    -> r.width() * r.height();
            case Triangle t     -> 0.5 * t.base() * t.height();
        };
    }

    @Test
    @DisplayName("area(Circle) computes PI * r^2")
    void circleArea() {
        assertEquals(Math.PI * 4, area(new Circle(2.0)), 1e-9);
    }

    @Test
    @DisplayName("area(Rectangle) computes width * height")
    void rectangleArea() {
        assertEquals(20.0, area(new Rectangle(4.0, 5.0)), 1e-9);
    }

    @Test
    @DisplayName("area(Triangle) computes 0.5 * base * height")
    void triangleArea() {
        assertEquals(6.0, area(new Triangle(4.0, 3.0)), 1e-9);
    }

    // ---------------------------------------------------------------------------
    // Sealed interface: only permitted subtypes are allowed
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Sealed interface permits field contains exactly the declared subtypes")
    void permittedSubclasses() {
        Class<?>[] permitted = Shape.class.getPermittedSubclasses();
        assertNotNull(permitted);
        assertEquals(3, permitted.length);
        // Check names
        boolean hasCircle    = false;
        boolean hasRectangle = false;
        boolean hasTriangle  = false;
        for (Class<?> c : permitted) {
            if (c == Circle.class)    hasCircle    = true;
            if (c == Rectangle.class) hasRectangle = true;
            if (c == Triangle.class)  hasTriangle  = true;
        }
        assertTrue(hasCircle,    "Circle must be permitted");
        assertTrue(hasRectangle, "Rectangle must be permitted");
        assertTrue(hasTriangle,  "Triangle must be permitted");
    }

    @Test
    @DisplayName("Sealed class isSealed() returns true")
    void shapeInterfaceIsSealed() {
        assertTrue(Shape.class.isSealed());
    }

    @Test
    @DisplayName("Permitted record implementing sealed interface is not sealed")
    void circleIsNotSealed() {
        // A record is final, not sealed
        assertFalse(Circle.class.isSealed());
    }

    // ---------------------------------------------------------------------------
    // Pattern-matching switch with guarded patterns
    // ---------------------------------------------------------------------------

    static String classify(Shape shape) {
        return switch (shape) {
            case Circle c when c.radius() > 10   -> "large circle";
            case Circle c                         -> "small circle";
            case Rectangle r when r.width() == r.height() -> "square";
            case Rectangle r                      -> "rectangle";
            case Triangle t                       -> "triangle";
        };
    }

    @Test
    @DisplayName("Guarded pattern selects large circle when radius > 10")
    void guardedPatternLargeCircle() {
        assertEquals("large circle", classify(new Circle(15)));
    }

    @Test
    @DisplayName("Guarded pattern falls through to plain circle when radius <= 10")
    void guardedPatternSmallCircle() {
        assertEquals("small circle", classify(new Circle(5)));
    }

    @Test
    @DisplayName("Guarded pattern identifies square when width equals height")
    void guardedPatternSquare() {
        assertEquals("square", classify(new Rectangle(3, 3)));
    }

    @Test
    @DisplayName("Non-square rectangle is classified as 'rectangle'")
    void guardedPatternRectangle() {
        assertEquals("rectangle", classify(new Rectangle(4, 5)));
    }

    // ---------------------------------------------------------------------------
    // Sealed abstract class
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("Sealed abstract class permits only declared subclasses")
    void animalPermittedSubclasses() {
        Class<?>[] permitted = Animal.class.getPermittedSubclasses();
        assertNotNull(permitted);
        assertEquals(2, permitted.length);
    }

    @Test
    @DisplayName("Permitted final subclasses of sealed class behave polymorphically")
    void animalPolymorphism() {
        Animal dog = new Dog();
        Animal cat = new Cat();
        String result = switch (dog) {
            case Dog d -> d.speak();
            case Cat c -> c.speak();
        };
        assertEquals("Woof", result);

        String catResult = switch (cat) {
            case Dog d -> d.speak();
            case Cat c -> c.speak();
        };
        assertEquals("Meow", catResult);
    }

    // ---------------------------------------------------------------------------
    // instanceof with sealed types
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("instanceof works correctly with sealed type hierarchy")
    void instanceofWithSealedType() {
        Shape shape = new Circle(3.0);
        assertTrue(shape instanceof Shape);
        assertTrue(shape instanceof Circle);
        assertFalse(shape instanceof Rectangle);
        assertFalse(shape instanceof Triangle);
    }

    @Test
    @DisplayName("Pattern matching instanceof binds variable for sealed subtypes")
    void patternMatchingInstanceofSealed() {
        Shape shape = new Rectangle(6.0, 4.0);
        if (shape instanceof Rectangle r) {
            assertEquals(24.0, r.width() * r.height(), 1e-9);
        } else {
            fail("Expected a Rectangle");
        }
    }
}
