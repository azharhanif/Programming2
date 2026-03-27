# HashCode

## 1. Set

Let's first take a look at one example: there is a big library with thousands of books, and the library wants to know how many authors are Canadian. What we can do is to go through each book, and find out the nationality of the author, if she/he is Canadian, then increase the counter by one. However, an author may write more than one book, so this solution may count an author more than one time.

A better solution is to create a collection that contains all Canadian authors that you have see so far. So every time you see a Canadian author, first check if it exists in that collection already, if so, you should not count it again, only if the collection does not contain that author, you can increase the counter by one and add the author to the collection. For this solution, you need to write a if statement to manually check if you should count a Canadian author or not.

There is a perfect data structure can do this for you. `Set`, which is also a collection, allows you to add different elements to it. However, Set do not have repeated elements, that is to say, if you have an empty set, and you want to add 1 to it, then 1 will be successfully added to it. After that, if you want to add 1 to the same set again, then the 1 will not be added since there is already a 1 inside. You can understand it as every time if you want to add a new element to a set, there is a hidden for loop that go through the set, and takes each element out and uses `equals()` compare it to the element you want to add, if the two are the same, then the set will not re-add the element again.

## 2. HashCode

If we have a big set, like right now we have a set of Canadian authors, and since we have thousands of books in the library, the Canadian authors can also be a lot. Assume we have 1000 of them added to the set, and right now we find a new book that is written by a Canadian author, Java will go through the set, and try to compare the new author with each element, and there are two situations: 1. if any element in the set is the same as the new author, Java will not add the new author to the set; 2. if no element in the set is the same as the new author, then Java will add the new author to the set.

And to compare two authors Java can use the `equals()` method. The problem is that there are 1000 of authors in the set already, that is to say, in the worst case, we have to call `equals()` 1000 times to know if we can add that new author to the set. Calling a method takes resources, you have to pass parameters, do calculations (usually there are many if statements, and a casting statement in `equals()`), and then return the result. do it for 1000 times just for a simple adding operation does not sound very efficient.

So there comes the `hashcode`. The idea of hashcode is very simple. Comparing two objects by using `equals()` is very time consuming, but computer can always compare two integers fast, like 3 and 5 are not equal. So if we can use an integer number to represent an object, we can compare integers instead of comparing two objects, which will save us a lot of time.

`hashcode()` is a method that calculates the hashcode for an object based on its data member. There are many different algorithms to calculate the hashcode, but all of them can be understood as a mathematic function `f(x)`, passing `x`(the object) to it, it will return you `y` (an integer). If two objects (`x`) are the same, then the calculated hashcode(`y`) should always be the same. But to be noticed, even though the chance is very low, if two objects (`x`) are not the same, the hashcde(`y`) may still be the same, in the other word, the hashcode is not unique.

So now what Java will do when we want to add a new element to the set, Java will first call the `hashCode()` method to calculate the hashcode for the new object, and then compare with the hashcode with each element in the set, if the two hashcode are not the same, Java knows that the two objects are not the same, and if the two hashcode are the same, Java will call the `equals()` to double check if the two objects are the same or not (this will happen very rarely).

## 3. How to Write HashCode()

### 3.1 Superclass

For a superclass, you can generate the `hashCode()` directly, and choose the data member that you want to have.

```java
@Override
public int hashCode() {
    int hash = 5;
    hash += 53 * hash + Objects.hashCode(this.id);
    hash += 53 * hash + Objects.hashCode(this.title);
    hash += 53 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
    return hash;
}
```
#### 3.1.1 General pattern behind hash formula:
```
hash = PRIME * hash + fieldHash; // This is called a rolling hash / polynomial hash

a) Why multiply first?

Because:

`hash = 31 * hash + newValue`

means:

- previous hash is shifted (scaled)
- new value is added with influence/weights
- Order matters
- Fields don’t “collapse” into each other (see example bellow)

b) Why PRIME numbers (7, 31, 41, 53)?

Yes — they are prime numbers, but not random.

Reason 1 — Reduce collisions

Prime numbers help distribute hash values more evenly.

If you use bad numbers: `hash = 2 * hash + value;`

    - patterns repeat easily → collisions increase

Reason 2 — Mathematical mixing

Primes avoid common factors:

    - If numbers share factors → patterns overlap
    - If prime → better spread

Reason 3 — Historical + practical choice

Some primes are conventionally used in Java:

Prime	    Usage
7	        initial seed
31	        most common multiplier
53, 37	    sometimes used for variation

c) Why specifically 31 is VERY popular?

Reason A — Efficient computation

    - `31 * x == (x << 5) - x`
      // Compiler can optimize this (bit shift)

Reason B — Good distribution

    - Empirically tested → low collisions

Reason C — Used in Java itself

    - Example:

    `String.hashCode()` uses:

    - `hash = 31 * hash + char`

This is why 31 most common

d) What about 7?
    - `int hash = 7;`
     // This is just a starting seed

e) Why not 0?
If:

    - `int hash = 0;`

then:

    - `hash = 31 * 0 + value → value`

first field dominates too much

f) Why 7?
    - small prime
     - non-zero
     - avoids trivial patterns

Could also be 17, 23, etc.

g) What about 41, 53?

These are:

    - just different primes
    - sometimes used to add variation

But important:

    - You DO NOT need multiple primes

Most correct implementations use:

    `hash = 31 * hash + field;`

repeated for all fields.

h) Best practice:

    int hash = 7;
    hash = 31 * hash + field1;
    hash = 31 * hash + field2;
    hash = 31 * hash + field3;

simple, consistent, standard

i) Why multiplication matters (simple demo)

Without multiplication

    hash = a + b
    (1,2) → 3  
    (2,1) → 3  ❌ collision

With multiplication

    hash = 31 * a + b
    (1,2) → 33  
    (2,1) → 63  ✅ different
```

### 3.2 Subclass

For a subclass, you need to call the superclass `hashCode()` to generate a hashcode based on the superclass data members and then add it with the hashcode based on the subclass data members.

```java
@Override
    public int hashCode() {
        int hash = 3;
        hash += 41 * hash + super.hashCode();		// calling the super class hashcode()
        hash += 41 * hash + (int) (Double.doubleToLongBits(this.publicationFrequency)
                ^ (Double.doubleToLongBits(this.publicationFrequency) >>> 32));
        return hash;
    }
```
### 3.3 Purpose
- Build a hash value for an object
- Combine existing hash with a double field
#### 3.3.1 Why Double.doubleToLongBits(...)?
A double is 64 bits, but hashCode() returns an int (32 bits).

So we convert:
```
double → long (64 bits)
long bits = Double.doubleToLongBits(publicationFrequency);
```
This gives the exact binary representation of the double.

The Important Part — >>> 32 (32-bit shift)

`bits >>> 32` What this does:

- Takes the upper 32 bits of the 64-bit number
- Moves them to the lower 32-bit position

#### 3.3.2 Q1 `double` is already 64 bits… why convert to `long`? 
Visual Explanation:
```
double = 64 bits  
long   = 64 bits
```
So the conversion is NOT about size. The real reason: bit-level access

- A double is a floating-point number (IEEE-754 format): `sign | exponent | mantissa`

Java does NOT let you directly:

- shift a double
- XOR a double
- access its raw bit pattern

So this is illegal:
```
double x = 3.14;
x >>> 32;     // ❌ not allowed
```
What Double.doubleToLongBits() does?

It says: “Give me the exact 64-bit binary representation of this double as a long.”

`long bits = Double.doubleToLongBits(3.14);`

Now you can do:
```
bits >>> 32   // ✅ allowed
bits ^ ...    // ✅ allowed
```
#### 3.3.3 Q2 Does shifting by 32 lose the lower 32 bits?

Yes — BUT that’s intentional and temporary. Because:

```
long bits = Double.doubleToLongBits(x);

This is:
`bits = [ HIGH 32 bits ][ LOW 32 bits ]`
 
Binary Animation: bits ^ (bits >>> 32)

We start with a 64-bit value (from doubleToLongBits):

STEP 0 — Original 64-bit value
bits = [ HIGH 32 bits ] [ LOW 32 bits ]

Example:
[ 10110011 01010101 11110000 00001111 ]
[ 11001100 00110011 10101010 01010101 ]

Think of it as:
bits = HIGH | LOW

STEP 1 — Shift right by 32
`bits >>> 32`

Result:

shifted =

[ 00000000 00000000 00000000 00000000 ]
[ 10110011 01010101 11110000 00001111 ]

What happened?

- HIGH half moved down
- LOW half is gone (in this version)

STEP 2 — XOR both values
`bits ^ (bits >>> 32)`

Now align them:

Original bits:
[ HIGH ][ LOW ]

Shifted bits:
[ 0000 ][ HIGH ]

XOR operation

Top 32 bits:
HIGH ^ 0000 = HIGH

Bottom 32 bits:
LOW ^ HIGH

RESULT
`[ HIGH ][ LOW ^ HIGH ]`

BOTH halves are now mixed into one value.

STEP 3 — Cast to int
`(int)(bits ^ (bits >>> 32))`

Why cast to int?

After XOR, we still have a long.

`(int)(...)`

→ keeps only the lower 32 bits (which now contain mixed data).Java keeps only the lower 32 bits:

FINAL HASH PART =
[ LOW ^ HIGH ]

Why multiply by 41? `41 * hash`

This is a hash mixing strategy.

- 41 is a prime number
- helps spread values better
- reduces collisions
  
Small Issue in the above Code `hash += 41 * hash + ...`

This is unusual. 

Standard pattern is: `hash = 41 * hash + ...`

It still works, but it's not standard practice.

Standard Version  

long bits = Double.doubleToLongBits(this.publicationFrequency);
hash = 41 * hash + (int)(bits ^ (bits >>> 32));
```
### 3.4 More examples

```java
public class User {
    private long id;
    private String name;
    private String email;
    
    @Override
    public boolean equals(Object object) {
        if (this == object) 
            return true;
        if (object == null)
            return false;
        if (this.getClass() != object.getClass())
            return false;
        
        User user = (User) object;
        
        return id == user.id && (name.equals(user.name)) && (email.equals(user.email));
    }
    
    @Override
    public int hashCode(){
        // return 1
        // return (int) id * name.hashCode() * email.hashCode();
        
        int hash = 7;
        
        hash += 31 * hash + (int) (id ^ (id >>> 32));
        hash += 31 * hash + (name == null ? 0 : name.hashCode());
        // Java is calling the hashCode() method of the String class, not the hashCode() of User.
        hash += 31 * hash + (email == null ? 0 : email.hashCode());
        
         // hash += 53 * hash + (int) (Double.doubleToLongBits(id) ^ Double.doubleToLongBits(id) >>> 32); ❌                  not needed
        
        return hash;
    }
}

```
