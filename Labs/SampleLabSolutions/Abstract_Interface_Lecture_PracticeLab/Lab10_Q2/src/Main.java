//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Circle c = new Circle(5);
        System.out.println(c);
        System.out.println("Area: " + c.getArea());
        System.out.println("Perimeter: " + c.getPerimeter());

        ResizableCircle rc = new ResizableCircle(10);
        System.out.println(rc);
        rc.resize(50);
        System.out.println("After resize: " + rc);
    }
}