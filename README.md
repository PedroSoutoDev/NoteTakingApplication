# Note Taking Fullstack
Fall 2019 class group project. Creating a note taking web application using React for frontend and Spark for backend.

## BACKEND

### ResponseDto
By using a Data Transfer Object (DTO), communication between the web application and the backend server is cheaper and faster. By converting it back and forth between a JSON, this object can be easily sent via HTTP calls. It also allows the web application to display its content easily. Finally, having a standardized response allows the front end to easily parse the information in order to display it.

### NoteDto
Class used to store notes that are downloaded from MongoDB. As notes are downloaded from the database, they are converted into *DTO objects* that are easy to work with. This makes it possible to search, sort, and print notes from the database in an easy way.

### MongoDao
By using a data access object (DAO), the server has an interface by which to access notes from the database. This means that the logic of data retrieval is abstracted away from the server. If the data source were to be changed (to a text file or a new database system for instance), only the DAO would have to be changed. This allows the program to be modular.  
  
  
## React for front end
The frontend of this note taking app is a simple interface that allows users to both add and remove notes from the database. Furthermore, everytime they do so, the list of notes on the database is updated and displayed at the bottom of the page. React is a good choice for this since it allows heavy dynamic content to be refreshed often.  
Below is a picture of the frontend:  

![App frontend using React.](https://raw.githubusercontent.com/PedroSoutoSFSU/NoteTakingFullStack/master/Frontend.png)
