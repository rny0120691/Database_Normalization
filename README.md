# Database_Normalization

This project is an implementation of functional dependencies and normalization to 3NF.

To run this project you can either:

   Run the MainClass.Java:
	1.Input the schema from the console you want in the form of ABCDE and press Enter
	2.Input the Functional dependencies from the console seperated by comma ex. AB->C, CD->E and press Enter

	ps.I have added a schema and functional dependencies as a comment 
           at the start of the program in case you want to paste them to the console easily.
OR
  
   Run the MainDemo.java, which is a demo that has an example and requires no input.
   
The output looks like this:

-------------------The Functional Dependencies are------------------

[{[C, D]-->[A]}, {[C, E]-->[H]}, {[B, G, H]-->[A, B]}, {[C]-->[D]}, {[E, G]-->[A]}, {[H]-->[B]}, {[B, E]-->[C, D]}, {[C, E]-->[B]}]

<----------------Decomposing Right Hand Side----------------------->

[{[C, D]-->[A]}, {[C, E]-->[H]}, {[B, G, H]-->[A]}, {[B, G, H]-->[B]}, {[C]-->[D]}, {[E, G]-->[A]}, {[H]-->[B]}, {[B, E]-->[C]}, {[B, E]-->[D]}, {[C, E]-->[B]}]

<----------------Removing Extraneous Left Attribute---------------->

[{[C]-->[A]}, {[C, E]-->[H]}, {[G, H]-->[A]}, {[H]-->[B]}, {[C]-->[D]}, {[E, G]-->[A]}, {[B, E]-->[C]}, {[B, E]-->[D]}, {[C, E]-->[B]}]

<----------Eliminating Redundant Functional Dependencies----------->

[{[C]-->[A]}, {[C, E]-->[H]}, {[G, H]-->[A]}, {[H]-->[B]}, {[C]-->[D]}, {[E, G]-->[A]}, {[B, E]-->[C]}]

<----------------------------Apply Union--------------------------->

[{[C]-->[A, D]}, {[C, E]-->[H]}, {[G, H]-->[A]}, {[H]-->[B]}, {[E, G]-->[A]}, {[B, E]-->[C]}]
A not part of the key
B can be part of the key
C can be part of the key
D can be part of the key
E must be part of the key
F must be part of the key
G must be part of the key
H can be part of the key

-------------------------Candidate Keys are-------------------------

[[B, E, F, G], [C, E, F, G], [E, F, G, H]]

--------------------Converting into relations-----------------------

Relation 1[A, C, D], Relation 2[C, E, H], Relation 3[A, G, H], Relation 4[B, H], Relation 5[A, E, G], Relation 6[B, C, E], Relation 7[B, E, F, G], Relation 8[C, E, F, G], Relation 9[E, F, G, H]
