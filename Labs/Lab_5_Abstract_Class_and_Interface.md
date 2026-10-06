# Programming 2 — Lab 8
## Abstract Classes and Interfaces

**Estimated time:** 50–65 minutes  
**Topics:** Abstract class, abstract method, concrete subclass, interface, `implements`, multiple interfaces, default method, polymorphic reference

### Lab goal

You will build a small **smart-device system** in which devices share common information but do not all behave the same way.

The lab deliberately uses both an **abstract class** and **interfaces**. Do not replace the design with one large concrete class.

---

# Part 1 — Build the Abstract Base Class

Create an abstract class:

```java
public abstract class SmartDevice
```

It must contain:

```text
brand
model
```

Use appropriate access control.

Add:

```java
public SmartDevice(String brand, String model)
public String getBrand()
public String getModel()
public abstract void performMainAction()
```

Also add a normal method:

```java
public void showBasicInfo()
```

It should print the brand and model.

### Basic condition

Every `SmartDevice` must have a brand and model.

### Tricky condition

`performMainAction()` must be **abstract**. Do not provide a generic implementation such as:

```java
System.out.println("Device is working");
```

The point is that each specific device decides what its main action means.

---

# Part 2 — Create Two Different Devices

Create:

```java
public class SmartPhone extends SmartDevice
```

and:

```java
public class SmartSpeaker extends SmartDevice
```

Each class must implement `performMainAction()` differently.

Example:

```text
SmartPhone is making a phone call.
SmartSpeaker is playing music.
```

Add one additional method to each class that makes sense for that device.

For example:

```java
makeCall()
```

and:

```java
playMusic()
```

### Basic condition

Both subclasses must compile and must implement the abstract method.

### Tricky condition

Do not copy the same `performMainAction()` implementation into both classes. The abstract method exists specifically because the behavior is different.

---

# Part 3 — Add an Interface

Create:

```java
public interface Chargeable
```

with:

```java
void charge();
```

Make `SmartPhone` implement `Chargeable`.

Its `charge()` method should print:

```text
SmartPhone is charging.
```

### Basic condition

`SmartPhone` must use:

```java
implements Chargeable
```

### Tricky condition

Do not put `charge()` into `SmartDevice`.

Not every smart device must be chargeable.

---

# Part 4 — Add a Second Interface

Create:

```java
public interface Connectable
```

with:

```java
void connectToWiFi();
```

Make both `SmartPhone` and `SmartSpeaker` implement it.

Use different messages, for example:

```text
SmartPhone connected to WiFi.
SmartSpeaker connected to WiFi.
```

### Basic condition

Both classes must implement the interface method.

### Tricky condition

A class can implement more than one interface. Do not try to write:

```java
extends Chargeable
```

Interfaces are connected with `implements`.

---

# Part 5 — Use Polymorphic References

In `main`, create:

```java
SmartDevice d1 = new SmartPhone("Apple", "iPhone");
SmartDevice d2 = new SmartSpeaker("Google", "Nest");
```

Call:

```java
d1.showBasicInfo();
d1.performMainAction();

d2.showBasicInfo();
d2.performMainAction();
```

### Basic condition

The program must compile even though `SmartDevice` is abstract.

### Tricky condition

You cannot do:

```java
SmartDevice d = new SmartDevice(...);
```

An abstract class cannot be instantiated.

---

# Part 6 — Advanced Understanding Challenge

Create:

```java
public static void testCharging(Chargeable device)
```

Inside the method, call:

```java
device.charge();
```

Call it using a `SmartPhone`.

Then answer this question in a comment:

```text
Why can testCharging() accept a SmartPhone even though
the parameter type is Chargeable?
```

### Extra challenge

Try passing a `SmartSpeaker` to `testCharging()`.

Explain why Java accepts the `SmartPhone` but rejects the `SmartSpeaker`.

---

# Final Checklist

- [ ] `SmartDevice` is abstract.
- [ ] `performMainAction()` is abstract.
- [ ] `SmartPhone` and `SmartSpeaker` extend `SmartDevice`.
- [ ] `Chargeable` is an interface.
- [ ] `Connectable` is an interface.
- [ ] `SmartPhone` implements both interfaces.
- [ ] `SmartSpeaker` implements `Connectable`.
- [ ] A `SmartDevice` reference can refer to a concrete subclass.
- [ ] You tested the interface reference in `testCharging()`.
- [ ] You can explain why an abstract class cannot be instantiated.
