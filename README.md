# OOP-Grand-Devoir2

# Virtual Library : Ex2 Grand Devoir 2

Grupa : 1220bF

### Authors

-Andrei Cristiana
-Nae Sebastian-Ion

### A Virtual Library made for OOP Course

### Functionalities
- Books - list of existing books in the library
- Filter - list of available/borrowed books
- Search - find entries in the database based on title, author or publishing house
- Main book list - select a book from an author and see al the details of the book
- Delete - delete the book from the database
- Change status - change the book status (borrowed/available)
- Add a new book - add a new book to the library


| Class | Description |
| ----------- | ----------- |
| Livre | Creates an object of Livre type and that has all the methods correspondant to the possible operations that cand be made in the program |


| Functions | Description |
| ----------- | ----------- |
| addNewLivre | Adds a new book to the database |
| setBookStatusImprunte |Sets the status of a specified book to 'borrowed' |
| setBookStatusDispomible |Sets the status of a specified book to 'available' |
| deleteBook | Deletes a specified book form the database |
| findBook | Returns results after certain criteria after searching for them in the databse |
| addNewLivre | Adds a new book to the database |
| showBorrowedBooks | Returns a list of borrowed books |
| showAvailableBooks | Returns a list of available books |
| getBooksFromAuthor | Returns a list of books fof a certain author |
| getAuthors | Returns a list of authors |
| getDetails | Returns the details of a certain book |













| Functions | Description |
| ----------- | ----------- |
| bookDetailsLayout | Returns a grid that contains all the details of a book |
| loadFilterPane | Returns a grid that contains the list of available/borrowed books  |
| loadQuestionDeletePane | Returns a VBox containing the confirmation/information of deleting a certain book |
| loadSelectedBookPane | Returnsa grid containing the details of the selected book from the main book list |
| loadChangeStatusGrid | Returns a grid that contains elements and options for changing the status of a book(borrowed/available) |
| loadAllBooksAccordion |Returns an accordion that contains the main book list |
| loadAddBookPane | Returns a grid that contains a form for adding a new book in the database |
| loadMainPane | Returns a BorderPane that contains all the other UI panes and elements |

