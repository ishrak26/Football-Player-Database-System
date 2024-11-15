# Football Player Database System ⚽  
*A JavaFX Application with Server-Based Networking*

## Overview  
This project was developed as a term project for **Level-1/Term-II** under the course **CSE 108: Object-Oriented Programming Language Sessional**. It is a **JavaFX application** that connects a football club to a local server using **Java Networking**. 

### Key Features  
- **Club Registration and Login**: A club account is required for accessing features.  
- **Player Management**: View, search, and filter a list of club players based on user-defined criteria.  
- **Transfer Market**:  
  - Clubs can connect to other clubs for player transfers.  
  - The transfer market updates automatically based on user input.  
  - Transfers can be initiated directly from the application interface.

### Challenges and Future Improvements  
The project introduces server-based networking, enabling clubs to connect and trade players. However, there are some limitations and areas for future enhancements:
1. **Single Login Restriction**: Currently, one club can log in from a single program instance at a time. Future updates may enable simultaneous logins from multiple instances.  
2. **Budget-Oriented Transfers**: Adding a fixed budget system for more realistic transfer management.  
3. **Scalability**: The current implementation uses a small dataset (25 players) read from a text file. Search algorithms are linear for simplicity but can be optimized for larger datasets.

### Technical Details  
- **Platform**: JavaFX  
- **Backend**: Java Networking (server-based architecture)  
- **Database**: Player data is read from a `.txt` file.

### Demo  
Check out the YouTube demo of this project:  
[YouTube Demo](https://youtu.be/KRCxGvgQvro?si=Ux9-tG7kxH4VBnu6)
