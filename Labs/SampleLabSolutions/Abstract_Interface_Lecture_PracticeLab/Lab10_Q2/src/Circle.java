public class Circle implements GeometricObject {

    protected double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle[radius=" + radius + "]";
    }
}

/*Why radius is protected in the following Circle class? What is the design choice in making it protected instead of private?
First: What does protected mean?

In Java:
private → only accessible inside the same class
protected → accessible:

inside the class
inside subclasses (even in other packages)

Why would radius be protected instead of private?

It’s a design choice based on inheritance.
Example Context
class Circle {
    protected double radius;
}

Now suppose we create a subclass:

class ColoredCircle extends Circle {

    public double getDiameter() {
        return 2 * radius;   //  allowed because radius is protected
    }
}
If radius were private
class Circle {
    private double radius;
}

Now this breaks:

class ColoredCircle extends Circle {

    public double getDiameter() {
        return 2 * radius;   //  ERROR
    }
}

Because:

private members are NOT accessible in subclasses
Better Design (Industry Style)
class Circle {
    private double radius;

    protected double getRadius() {
        return radius;
    }
}

Now subclasses use:

return 2 * getRadius();

✔ keeps encapsulation
✔ still supports inheritance
protected is often used in teaching to simplify inheritance,
but in real design, we prefer private + controlled access.
When SHOULD you use protected?

Use it when:

You EXPECT subclasses

Subclasses NEED direct access

You are designing a framework/base class

Example:

abstract class Shape {
    protected double size;
}
radius is protected so that subclasses of Circle can directly access it.
If it were private, subclasses would not be able to use it without getters.
This is a design trade-off between ease of inheritance and encapsulation.
*/
