
# Programming 2 Lab — Exception Handling with Familiar OOP Designs

## Lecture connection
This lab is based on the Exception Handling lecture sections on the default handler, `try-catch`, specific vs general catches, meaningful handlers using `e.getMessage()` / `e.getClass()`, `throws`, and user-defined exceptions. The warm-up also reuses the familiar `Animal` polymorphism idea from the polymorphism lecture. citeturn824109view0turn824109view1

## Time
- Warm-up: 10 minutes
- Main lab: 30 minutes
- Extension 1: 10 minutes
- Extension 2: 10 minutes

## Learning goals
By the end of this lab, you should be able to:
- explain what Java’s default handler does when no user-defined handler exists,
- write specific `catch` blocks before a general `catch`,
- use `e.getMessage()` to create a meaningful handler,
- use `throws` to move responsibility to the caller,
- create and use your own checked exception class. citeturn824109view0

---

# Part 0 — Warm-up from polymorphism lecture (10 minutes)

## Scenario
You already know that one `Animal` reference can point to a `Dog` or a `Cat`. In this warm-up, an unsafe cast causes a runtime exception.

## Starter code
```java
public static void showDogTrick(Animal animal) {
    Dog dog = (Dog) animal;   // may crash here
    dog.fetch();
}
```

## Tasks
1. Run the code with both a `Dog` and a `Cat`.
2. Observe the exception type.
3. Add a `try-catch` handler for `ClassCastException`.
4. Then rewrite the method in a safer way using `instanceof`.

## Questions
1. Which line throws the exception?
2. What would Java do if there were no `try-catch` block?
3. Why is `instanceof` usually better than catching this exception after the crash?

---

# Main Lab — Animal Shelter Exception Handling (30 minutes)

## Scenario
A small animal shelter keeps track of animals, adoption fees, and feeding rules.

You will work with these classes:
- `Animal`
- `Dog`
- `Cat`
- `ShelterService`
- `DuplicateAnimalNameException`
- `InvalidFoodAmountException`
- `Main`

The shelter code has three kinds of exception situations:
1. a runtime problem such as dividing by zero,
2. a problem that should be passed to the caller using `throws`,
3. a user-defined exception for shelter rules.

---

## Task 1 — Specific and general handlers
Complete `calculateAverageFee` in `ShelterService`.

### Goal
Use:
- one specific `catch` for `ArithmeticException`,
- one specific `catch` for `NullPointerException`,
- one general `catch (Exception e)` at the end.

### Starter method
```java
public static double calculateAverageFee(int totalFee, int animalCount, Animal featuredAnimal) {
    double result = 0;

    try {
        result = totalFee / animalCount;
        System.out.println("Featured animal: " + featuredAnimal.getName());
    }
    // TODO: add catches here

    return result;
}
```

### Rules
- If `animalCount` is 0, return `-1`.
- If `featuredAnimal` is `null`, return `-2`.
- For any other exception, print the class name and message, then return `-3`.

---

## Task 2 — Meaningful handler
In `Main`, when `calculateAverageFee(...)` causes an exception, your handler should use exception information.

### Required output style
- print `e.getClass()`
- print `e.getMessage()`

This follows the lecture’s idea of a meaningful handler instead of just assigning a random number. citeturn824109view0

---

## Task 3 — Using `throws`
Complete `feedAnimal` in `ShelterService`.

### Starter method
```java
public static void feedAnimal(Animal animal, int foodAmount)
        throws InvalidFoodAmountException {
    // TODO
}
```

### Rules
- If `foodAmount <= 0`, throw `InvalidFoodAmountException`.
- Otherwise print: `Feeding <animal name> with <foodAmount> units of food.`

### Question
Why is `throws` useful here instead of writing the same `try-catch` logic in many methods?

---

## Task 4 — User-defined exception
Complete `addAnimal` in `ShelterService`.

### Starter method
```java
public static void addAnimal(ArrayList<Animal> animals, Animal newAnimal)
        throws DuplicateAnimalNameException {
    // TODO
}
```

### Rule
If another animal in the list already has the same name (case-insensitive), throw `DuplicateAnimalNameException` with a clear message.

### Hint
Use:
```java
throw new DuplicateAnimalNameException("Animal name already exists: " + newAnimal.getName());
```

---

# Extension 1 — Vehicle Example (10 minutes)

## Scenario
A garage service app should not accept negative mileage values.

## Task
Complete:
```java
public static void recordMileage(Vehicle vehicle, int newMileage)
        throws InvalidMileageException {
    // TODO
}
```

### Rule
- If `newMileage < vehicle.getMileage()`, throw `InvalidMileageException`.
- Otherwise update the mileage.

---

# Extension 2 — Bank Example (10 minutes)

## Scenario
A bank account should not allow withdrawals beyond the balance.

## Task
Complete:
```java
public void withdraw(double amount) throws InsufficientFundsException {
    // TODO
}
```

### Rule
- If `amount > balance`, throw `InsufficientFundsException`.
- Otherwise subtract the amount.

---

# Reflection questions
1. According to the lecture, what does the default handler do?
2. Why must the general `catch (Exception e)` come after specific catches?
3. What is the difference between `try-catch` and `throws`?
4. Why do custom exceptions usually call `super(message)`?
5. Which problems in this lab are checked exceptions, and which are unchecked?

---

# Files in the starter project
- `Animal.java`
- `Dog.java`
- `Cat.java`
- `Vehicle.java`
- `BankAccount.java`
- `ShelterService.java`
- `DuplicateAnimalNameException.java`
- `InvalidFoodAmountException.java`
- `InvalidMileageException.java`
- `InsufficientFundsException.java`
- `Main.java`
- JUnit test files in `src/test/java`
