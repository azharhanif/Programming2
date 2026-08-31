
# Programming 2 Lab — Teacher Solution

## Lecture connection
This solution follows the lecture’s treatment of the default handler, specific vs general catches, meaningful handlers, `throws`, and user-defined exceptions. The warm-up begins with the familiar `Animal` hierarchy from the polymorphism lecture. citeturn824109view0turn824109view1

---

# Part 0 — Warm-up solution

## Unsafe version with `try-catch`
```java
public static void showDogTrickWithCatch(Animal animal) {
    try {
        Dog dog = (Dog) animal;
        dog.fetch();
    } catch (ClassCastException e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
    }
}
```

## Safer version with `instanceof`
```java
public static void showDogTrickSafely(Animal animal) {
    if (animal instanceof Dog dog) {
        dog.fetch();
    } else {
        System.out.println(animal.getName() + " is not a dog.");
    }
}
```

### Teaching notes
- Without a user-defined handler, Java’s default handler prints the exception information, prints the stack trace, and stops execution. citeturn824109view0
- `ClassCastException` is unchecked, so Java does not force a handler at compile time. citeturn824109view0

---

# Task 1 — Specific and general handlers

```java
public static double calculateAverageFee(int totalFee, int animalCount, Animal featuredAnimal) {
    double result = 0;

    try {
        result = totalFee / animalCount;
        System.out.println("Featured animal: " + featuredAnimal.getName());
    } catch (ArithmeticException e) {
        System.out.println("Arithmetic problem: " + e.getMessage());
        result = -1;
    } catch (NullPointerException e) {
        System.out.println("Null problem: " + e.getMessage());
        result = -2;
    } catch (Exception e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        result = -3;
    }

    return result;
}
```

### Teaching notes
The lecture explicitly says the general catch must come after the specific catches, because Java checks catches one by one. If `catch (Exception e)` is first, the later specific catches will never be reached. citeturn824109view0

---

# Task 2 — Meaningful handler in `Main`

```java
try {
    double avg = ShelterService.calculateAverageFee(100, 0, new Dog("Rocky"));
    System.out.println("Average fee: " + avg);
} catch (Exception e) {
    System.out.println(e.getClass());
    System.out.println(e.getMessage());
}
```

### Teaching note
In this particular design, `calculateAverageFee` already handles its own exceptions, so the outer `catch` may not run. This is still useful to discuss layered handling.

---

# Task 3 — `throws`

```java
public static void feedAnimal(Animal animal, int foodAmount)
        throws InvalidFoodAmountException {

    if (foodAmount <= 0) {
        throw new InvalidFoodAmountException(
                "Food amount must be greater than 0. Given: " + foodAmount);
    }

    System.out.println("Feeding " + animal.getName()
            + " with " + foodAmount + " units of food.");
}
```

### Teaching notes
The lecture explains that `throws` does not really solve the problem in that method. It announces that if the exception happens here, the caller must provide the solution. This avoids repeating the same handler in many methods. citeturn824109view0

---

# Task 4 — User-defined exception

```java
public static void addAnimal(ArrayList<Animal> animals, Animal newAnimal)
        throws DuplicateAnimalNameException {

    for (Animal animal : animals) {
        if (animal.getName().equalsIgnoreCase(newAnimal.getName())) {
            throw new DuplicateAnimalNameException(
                    "Animal name already exists: " + newAnimal.getName());
        }
    }

    animals.add(newAnimal);
}
```

### Custom exception example
```java
public class DuplicateAnimalNameException extends Exception {
    public DuplicateAnimalNameException(String message) {
        super(message);
    }
}
```

### Teaching notes
`super(message)` passes the message to the parent `Exception` class so that `getMessage()` works correctly.

---

# Extension 1 — Vehicle solution

```java
public static void recordMileage(Vehicle vehicle, int newMileage)
        throws InvalidMileageException {

    if (newMileage < vehicle.getMileage()) {
        throw new InvalidMileageException(
                "New mileage cannot be less than current mileage.");
    }

    vehicle.setMileage(newMileage);
}
```

---

# Extension 2 — Bank solution

```java
public void withdraw(double amount) throws InsufficientFundsException {
    if (amount > balance) {
        throw new InsufficientFundsException(
                "Withdrawal denied. Balance = " + balance + ", amount = " + amount);
    }

    balance -= amount;
}
```

---

# Expected ideas students should mention
- Default handler: prints exception information, prints stack trace, stops execution. citeturn824109view0
- Specific catches should come before general catches. citeturn824109view0
- `throws` pushes responsibility to the caller. citeturn824109view0
- Checked exceptions must be declared or handled; unchecked exceptions do not require that at compile time. citeturn824109view0

---

# Quick marking guide
- Warm-up `ClassCastException` handling: 2
- `calculateAverageFee`: 4
- `feedAnimal`: 2
- `addAnimal`: 3
- Vehicle extension: 2
- Bank extension: 2
- Reflection / explanation: 3

Total: 18 marks
