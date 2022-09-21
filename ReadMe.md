# Object Oriented Programming Project: Inventory Manager
This was a project for an Object Oriented Programming course. The objective was to create a simple inventory management system in two phases. The first phase as a console application with all the implemented functionality, and the second phase was to implement a GUI on top of the phase one application.

## Documentation is in progress as I lost the most up to date readme file.

### Compiling and Running
Compiling the code is just like any other java program: 
1. navigate into the src directory
2. call 'javac main/Main.java' to compile
3. call 'java main.Main' to run
```
C:\...\OOP\test>cd src

C:\...\OOP\test\src>javac main/Main.java
Note: .\transactions\Transaction.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.

C:\...\OOP\test\src>java main.Main


Inventory Management System Menu
--------------------------------
p: Add Product
s: Add Store
i: Perform Incoming Transaction
o: Perform Outgoing Transaction
b: Perform Transactions From File
r: Generate Reports
x: Exit Program

Input an action:
```
The above snippet shows an example of compiling and running the code. The base menu is included in the example to show that it does work.

### General Info
The provided class diagram is what I used, but made changes where necessary to implement required functionality. One big change was the inclusion of the util and input files. This felt necessary as there were many times when I was taking the same kind of input, so pulling that algorithm out into a method was useful. Also splitting out the input from the Main file helped to keep it from getting to be massively large in size.

In general, the file structure of the program is divided by functionality, main is in the ```main``` package and input as well as other utilities are in the ```main/util``` package. Anything to do with reporting is in the ```reports``` package. The ```stocks``` package contains files that relate to the product and store classes. And finally ```transactions``` contains files centered around the transaction portion of the program.

Using the system is fairly self explanatory. There are no command line arguments for running the code. The i/o is text-based on the command line, when the program runs you will receive a menu and and a prompt. Which looks like this:
```
Inventory Management System Menu
--------------------------------
p: Add Product
s: Add Store
i: Perform Incoming Transaction
o: Perform Outgoing Transaction
b: Perform Transactions From File
r: Generate Reports
x: Exit Program

Input an action: 
```

### Input
The program will prompt for any required input when you pick an action from this menu. Generally any restrictions on the input are specified when the prompt is displayed, but if the input provided is invalid, the program should alert the user of this and re-issue the prompt for input.

### Data
When inputting information about stores or products, the program always wants the ID of the product or store. Not the name. 

### Files
Input files should be placed in the working directory of the program.

While the program can technically output transactions to a file, this functionality is not active as I was unsure exactly which transactions should be outputted. The method for outputting transactions can be found in the ```src/transactions/TransactionManager.java``` file.

# Demo

## Adding a Product
```
Input an action: p
Enter product name: aaa
Enter product amount: 11
```

## Adding a Store
```
Input an action: s
Enter store name: asdf
Enter store address: asdf
```

## Incoming Transaction
```
Input an action: i
List of available products for the transaction: 
0: aaa
1: b
Select a product: 0
Enter product amount: 1
Would you like to add another product? (y/n): y
0: aaa
1: b
Select a product: 1
Enter product amount: 3
Would you like to add another product? (y/n): n
```

## Outgoing Transaction
```
Input an action: o
List of available stores for the transaction: 
0: asdf
Select a product: 0
List of available products for the transaction: 
0: aaa
1: b
Select a product: 0
Enter product amount: 2
Would you like to add another product? (y/n): n
```

## Transactions By File
```
Input an action: b
Enter the name of the transaction file: batchTransactions.txt
```

## Reports
Reports have a sub-menu as there are a lot and I didn't want to overlap hotkeys with other functionality
```
Inventory Management Report Submenu
--------------------------------
a: All Products Report
v: Available Products Report
s: Store Products Report(all stores or one)
t: All Transactions Report (all or monthly)
p: Product Transactions Report
h: High Volume Products Report (incoming or outgoing)
x: Return to Main Menu
```

### All Products Report
```
Input an action: a

All Products Report:
Products appear in the format: name(id): amount
aaa(0): 15
b(1): 25
```

### Available Products Report
```
Input an action: v

Available Products Report:
Products appear in the format: name(id): amount
aaa(0): 15
b(1): 25
```

### Store Products Report
```
Input an action: s

Enter a store ID to report on (enter 'a' for all, or a valid number): a
Store Products Report For All Stores:
Products appear in the format name(id): amount
aaa(0): 2
```

### All Transactions Report
```
Input an action: t

Incoming or outgoing transactions? (i for incoming, o for outgoing): i
Enter the number representation of a month, 1=January, 12=December (enter 'a' for all, or a valid number): a
All Transactions Report:
Products appear in the format: name(id): amount
ID: 0 Products: aaa(0): 4
ID: 1 Products: b(1): 3, aaa(0): 1
ID: 3 Products: aaa(0): 1
```

### Product Transactions Report
```
Input an action: p

Incoming or outgoing transactions? (i for incoming, o for outgoing): i
Select a product: 0
Transactions Report for aaa:
Products appear in the format: name(id): amount
ID: 0 Products: aaa(0): 15
ID: 1 Products: b(1): 25, aaa(0): 15
ID: 3 Products: aaa(0): 15
```

### High Volume Report
```
Input an action: h

Incoming or outgoing transactions? (i for incoming, o for outgoing): i
High Volume  Incoming Items:
Products appear in the format: name(id): amount
aaa(0): 6
b(1): 3
```
