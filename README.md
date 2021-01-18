# OOP-Grand-Devoir2

# Quiz Maker : Ex1 Grand Devoir 2

Grupa : 1220bF

### Authors : 
* Andrei Cristiana
* Nae Sebastian-Ion

### A quiz maker made for OOP Course 

#####Functionalities
* Enter - save the question
* Esc - quit the program
* Generate_html - creates the HTML
* Open - open a quiz
* Quiz - creates a file with the question of the 

Class | Description
-------- | -----------
Question | Creates a object of type Question where you can store all the information about it
Quiz | Creates an object of type Quiz which stores an ArrayList of Questions with their respective answers
HTML_gen | Creates an HTML Template to be used create a HTML file with answers


Functions | Description
-------- | -----------
Top_UI | Create the top part of the app adding the items needed
Left_UI | Create the left part , added a vbox with items
Right_UI | Create the right part, using a dynamic grid for the answers
Bottom_UI | Create the bottom part, buttons on the bottom half
clear_scene | Clear all the values in the scene
UI | Set the UI of a scene
saveQuestion | save the question in an array and then reset the Question object
Pop_UI | generate a UI based on the question with the index of the array list

