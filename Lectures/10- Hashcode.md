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
### 3.3 `hashCode()` Calculation
Build a hash value for an object

Combine existing hash with a double field
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
### 3.5 Conceptual confusion: step-by-step behavior inside HashSet

In a Library book example think of same Canadian author who has multiple books, or same book multiple editions, or same book multiple publishers will be added? Because they will have different hash code? Its is not clear if the hash code is only based on authors name in the book objects in the library. To clarify this confusion lets consider step-by-step behavior inside HashSet.

#### 3.5.1 The CORE RULE

From Java contract:

    - If two objects are equal (equals = true) → they MUST have same hashCode
    
    - If two objects are not equal → they can have same OR different hashCode

BIG misunderstanding 
```
“Different hashCode = always added”
“Same hashCode = always duplicate”

❌ BOTH are wrong.
```
How HashSet actually works  

When you do:

`set.add(book);`

Java does:
```
Step 1 → use hashCode → find bucket
Step 2 → use equals → check duplicate inside bucket
```
hashCode = fast grouping

equals = final decision

Even if hashCodes are same, equals decides uniqueness

#### 3.5.2 Addressing the confusion — Library Example

Let’s define a Book class.

###### Case A — hashCode based ONLY on author (BAD DESIGN)
```
@Override
public int hashCode() {
    return author.hashCode();
}

@Override
public boolean equals(Object obj) {
    Book other = (Book) obj;
    return this.author.equals(other.author);
}
```
Now let's test:
```
Book b1 = new Book("Java Basics", "Alice", 2020);
Book b2 = new Book("Advanced Java", "Alice", 2023);

HashSet<Book> set = new HashSet<>();
set.add(b1);
set.add(b2);
```
What happens?
```
hashCode → SAME (same author)
equals → TRUE (same author)
```
RESULT:

Only 1 book stored ❌ WRONG

Meaning

“Same author = same book” // conceptually wrong

Because:

“Different books should be added”

But `equals()` says they are the same.

KEY PRINCIPLE
```
HashCode does NOT define uniqueness
equals() defines uniqueness
```
###### Case B — Better Design (Real Library)
```
@Override
public boolean equals(Object obj) {
    Book other = (Book) obj;
    return this.title.equals(other.title)
        && this.author.equals(other.author)
        && this.year == other.year;
}

@Override
public int hashCode() {
    return Objects.hash(title, author, year);
}
Let's test again

Book b1 = new Book("Java Basics", "Alice", 2020);

Book b2 = new Book("Advanced Java", "Alice", 2023);

set.add(b1);

set.add(b2);
```
RESULT

Both books stored

Because:
```
equals = FALSE
hashCode = different (likely)
```
#### 3.5.3 Three scenarios explained

1️. Same author, different books
```
Book("Java 1", "Alice")
Book("Java 2", "Alice")
```
If equals uses:

only author → ❌ duplicate

author + title → ✅ different

2️. Same book, different editions
```
Book("Java Basics", "Alice", 1st edition)
Book("Java Basics", "Alice", 2nd edition)
```
If equals includes:

edition → treated as DIFFERENT

ignores edition → treated as SAME

Depends on your design

3️. Same book, different publishers
```
Book("Java Basics", "Alice", "Pearson")
Book("Java Basics", "Alice", "O'Reilly")
```
Again:
```
`equals` includes publisher?	
```
Result

Yes	separate books

No	duplicate

HashSet does NOT decide duplicates using hashCode. It uses equals()

Demonstration  
```
Book b1 = new Book("Java", "Alice", 2020);
Book b2 = new Book("Java", "Alice", 2020);

// even if hashCodes are different (bad design)
System.out.println(b1.equals(b2)); // true

set.add(b1);
set.add(b2);
```
Only ONE stored

#### 3.5.4 What if hashCodes are SAME but equals is FALSE?
```
@Override
public int hashCode() {
    return 1; // worst possible hash
}
```
All books go into same bucket.

But:
```
equals() → different
```
RESULT:

All books still stored.

Because equals separates them.

#### 3.5.5 HashSet Animation — How Objects Are Stored

We simulate:

HashSet<Book> set = new HashSet<>();

STEP 0 — Empty HashSet

Think of HashSet as buckets (like an array of lists):
```
Index (bucket)
0   →  [ ]
1   →  [ ]
2   →  [ ]
3   →  [ ]
4   →  [ ]
```
STEP 1 — Add First Book
```
Book b1 = new Book("Java Basics", "Alice", 2020);

set.add(b1);
```
What Java does:

    - Compute `hashCode`

    - Map to bucket index

    - `hashCode(b1) → 12345 → index = 2`

Result
```
0   →  [ ]
1   →  [ ]
2   →  [ b1 ]
3   →  [ ]
4   →  [ ]
```
STEP 2 — Add Different Book (Different hash)
```
Book b2 = new Book("Advanced Java", "Alice", 2023);
set.add(b2);
hashCode(b2) → 67890 → index = 4
```
Result
```
0   →  [ ]
1   →  [ ]
2   →  [ b1 ]
3   →  [ ]
4   →  [ b2 ]
```
No collision → directly added

STEP 3 — Collision (Same hashCode)
```
Book b3 = new Book("Python", "Bob", 2022);
```
Suppose:

`hashCode(b3) → index = 2`

Now bucket 2 already has b1

Java does:
```
Compare: b3.equals(b1) ?
```
Case A — equals = FALSE

`b3 ≠ b1`

Add it
```
0   →  [ ]
1   →  [ ]
2   →  [ b1, b3 ]
3   →  [ ]
4   →  [ b2 ]
```
Case B — equals = TRUE
```
b3.equals(b1) → true
```
DO NOT ADD
```
0   →  [ ]
1   →  [ ]
2   →  [ b1 ]
3   →  [ ]
4   →  [ b2 ]
```
    - Same hashCode ≠ duplicate
    
    - equals() decides duplicate

STEP 4 — Worst Case (All hashCodes same)
```
@Override
public int hashCode() {
    return 1;
}
```
Everything goes to same bucket
```
0   →  [ ]
1   →  [ b1, b2, b3, b4, b5 ]
2   →  [ ]
3   →  [ ]
4   →  [ ]
```
What happens internally?

Java checks:

b2.equals(b1)?
b3.equals(b1)?
b3.equals(b2)?
...

Still works — just slower

#### 3.5.6 FINAL ANIMATION SUMMARY
ADD OBJECT:
```
Step 1: hashCode() → pick bucket
Step 2: equals() → check duplicates inside bucket
Step 3: add if not duplicate
```
### 3.6 Purpose of hashCode() with HashSet example
Goal: Show how `hashCode() + equals()`
    - actually control behavior inside a Set
    - Example: Using User with HashSet

We’ll use `User` class (with `equals()` and `hashCode()`).

Step 1 — Create Users
```
User u1 = new User(1, "Alice", "a@email.com");
User u2 = new User(1, "Alice", "a@email.com"); // same data
User u3 = new User(2, "Bob", "b@email.com");   // different
```
Step 2 — Add to HashSet
```
import java.util.HashSet;

HashSet<User> set = new HashSet<>();

set.add(u1);
set.add(u2);
set.add(u3);

System.out.println("Set size: " + set.size());
```
#### 3.6.1 What happens internally?
Step-by-step:

1️. Add u1
    - compute hashCode(u1)
    - go to bucket
    - add
`Bucket[2] → [u1]`

2. Add u2 (same data)
    - compute hashCode(u2) → SAME bucket
    - compare using equals(u1, u2) → TRUE
    - NOT added

3️. Add u3
    - different hash → different bucket
    - added
    
4. Final Result
    - Set size: 2
      
5. Key Insight
    -  `HashSet` removes duplicates based on `equals()`
    -  `hashCode()` only helps find where to check
      
#### 3.6.2 ❌ Now showing WRONG case (very important)
Remove hashCode()

```
@Override
public int hashCode() {
    return super.hashCode(); // default (bad here)
}
```
1. Run same test:
```
set.add(u1);
set.add(u2);
```
2. Result
Set size: 3  ❌ WRONG

3. Why?

    - Different memory → different hashCode
    - goes to different buckets
    - equals() NEVER called
      
#### 3.6.3 Visual Bucket Example

With correct hashCode
```
Bucket 1 → [u1, u2 → equals → duplicate → ignored]
Bucket 3 → [u3]
With wrong hashCode
Bucket 1 → [u1]
Bucket 4 → [u2]  ❌ treated as different
Bucket 3 → [u3]
```
Demo
```
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        HashSet<User> set = new HashSet<>();

        User u1 = new User(1, "Alice", "a@email.com");
        User u2 = new User(1, "Alice", "a@email.com");
        User u3 = new User(2, "Bob", "b@email.com");

        set.add(u1);
        set.add(u2);
        set.add(u3);

        System.out.println("Set size: " + set.size());

        for (User u : set) {
            System.out.println(u);
        }
    }
}
```

```
Case	                        Result
Correct equals + hashCode	    duplicates removed
Only equals correct	            duplicates remain ❌
Only hashCode correct	        still broken ❌
```
If equals() is overridden, hashCode() MUST also be overridden
#### 3.6.4 Why super.hashCode() is bad design?
1.    `super.hashCode()` uses memory address, not object’s data.
```
@Override
public int hashCode() {
    return super.hashCode(); // default (bad here)
}
```
2.    So two objects with same data:
```
new User(1, "Alice", "a@email.com")
new User(1, "Alice", "a@email.com")
```
    - will have different hash codes
    - and HashSet will treat them as different

3.     The Core Rule:
```
If `equals()` returns true → `hashCode()` MUST be same
```
Here `equals()` says:
```
id, name, email → define equality
```
But `super.hashCode()` says:
```
object identity (memory) → define hash
```
These are inconsistent, for example
```
Step 1 — Two equal objects
User u1 = new User(1, "Alice", "a@email.com");
User u2 = new User(1, "Alice", "a@email.com");

Step 2 — equals()
System.out.println(u1.equals(u2)); // true

They are logically equal

Step 3 — hashCode (with super)
System.out.println(u1.hashCode()); // e.g. 123456
System.out.println(u2.hashCode()); // e.g. 987654

Different values ❌

What happens in HashSet

    HashSet<User> set = new HashSet<>();
    set.add(u1);
    set.add(u2);

Internal behavior

    Add u1
    hash → bucket 2 → store u1
    Add u2
    hash → bucket 4 → store u2

Different bucket → equals() NOT even checked

Result

Set size = 2 ❌ (duplicate not removed)

Why equals() is NOT even used

Because of HashSet logic:

    - Use hashCode → find bucket
    - Only compare equals() inside SAME bucket

Different hash → different bucket → equals skipped
This is the real problem
```
Bad `hashCode` breaks `HashSet` logic

#### 3.6.5 Correct behavior (when hashCode is proper)
```
@Override
public int hashCode() {
    return Objects.hash(id, name, email);
}
```
Now:

`u1.hashCode() == u2.hashCode()`

HashSet behavior

Same bucket → compare equals → duplicate → NOT added

Result
Set size = 1 ✔️

Analogy:

hashCode() → which room
equals() → same person inside room?

With bad hashCode

Alice (u1) → Room 2  
Alice (u2) → Room 5  

→ never compared → duplicate slips in ❌

With good hashCode

Alice (u1) → Room 2  
Alice (u2) → Room 2  

→ compared → duplicate removed ✔️

`super.hashCode()` is bad here because it ignores object’s data and uses memory identity, breaking the rule that equal objects must have the same hash code.
```
If you override equals(), you MUST override hashCode()
AND both must use the SAME fields
```

### 3.7 Trick exam question to predict HashSet output 
#### 3.7.1 Trick Question 1 — “Looks identical”
Code
```
import java.util.*;

class User {
    int id;

    User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return this.id == u.id;
    }

    // ❌ NO hashCode override
}

public class Main {
    public static void main(String[] args) {
        HashSet<User> set = new HashSet<>();

        set.add(new User(1));
        set.add(new User(1));

        System.out.println(set.size());
    }
}
```
    - Common Student Answer: 1
    - Correct Answer: 2
    - Why:
    ```
        equals() → true
        BUT hashCode() → different (memory-based)
        → different buckets → equals NOT checked
    ```
    - Lesson
    
    `equals() alone is NOT enough`
        
#### 3.7.2 Trick Question 2 — “Bad hash but still works”
Code
```
class User {
    int id;

    User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return this.id == u.id;
    }

    @Override
    public int hashCode() {
        return 1; // worst possible
    }
}
HashSet<User> set = new HashSet<>();
set.add(new User(1));
set.add(new User(1));

System.out.println(set.size());
```

    - Common Student Answer: 2
    - (because hash is bad)
    - Correct Answer:1
    - Why
```
All objects go to SAME bucket
equals() is checked → duplicate removed
```
    - Lesson
    
    Bad hashCode affects performance, not correctness

#### 3.7.3 Trick Question 3 — “equals always false”
Code
```
class User {
    int id;

    User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return false; // ❌
    }

    @Override
    public int hashCode() {
        return id;
    }
}
HashSet<User> set = new HashSet<>();
set.add(new User(1));
set.add(new User(1));

System.out.println(set.size());
```
    - Correct Answer: 2
    - Why
```
equals() NEVER returns true
→ nothing is ever considered duplicate
```
    - Lesson
    
    equals() defines uniqueness — ALWAYS
#### 3.7.4 Trick Question 4 — “Same object reference”
Code
```
User u = new User(1);

HashSet<User> set = new HashSet<>();
set.add(u);
set.add(u);

System.out.println(set.size());
```
    - Correct Answer: 1
    - Why
```
Same reference → same object
HashSet detects duplicate immediately
```
    - Lesson
    
    Same reference = always duplicate
#### 3.7.5 Trick Question 5 — “Different objects, same hash, equals false”
Code
```
class User {
    int id;

    User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return this.id == u.id;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
HashSet<User> set = new HashSet<>();

set.add(new User(1));
set.add(new User(2));

System.out.println(set.size());
```
    - Correct Answer: 2
    - Why
```
Same bucket
equals() → false
→ both stored
```
    - Lesson

    Same hashCode does NOT mean duplicate

#### 3.7.6 Trick Question 6 — “equals uses more fields than hashCode”
Code
```
class User {
    int id;
    String name;

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return this.id == u.id && this.name.equals(u.name);
    }

    @Override
    public int hashCode() {
        return id; // ❌ missing name
    }
}
HashSet<User> set = new HashSet<>();

set.add(new User(1, "Alice"));
set.add(new User(1, "Bob"));

System.out.println(set.size());
```
    - Common Answer: 1
    - Correct Answer: 2
    - Why
```
hashCode → same bucket
equals → FALSE (name different)
→ both stored
```
    - Lesson
        
    `hashCode` can be less strict than `equals` (but never more strict)


