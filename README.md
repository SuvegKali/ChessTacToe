<img width="552" height="307" alt="Screenshot 2026-03-11 at 12 10 52 PM" src="https://github.com/user-attachments/assets/c4299f9e-892f-4476-bba3-d276c4341078" />
<img width="552" height="660" alt="Screenshot 2026-03-11 at 12 11 32 PM" src="https://github.com/user-attachments/assets/ced12255-63ac-4235-a7d9-0a8f551878f7" />

# ♟️ Game v1.0: Rules & Mechanics

### **Overview**
A strategic hybrid of Chess and Tic-Tac-Toe played on a **4x4 grid**.

### **Objective**
The goal is to get all **three** of your pieces (Rook, Knight, and Pawn) into a **straight line**—horizontally, vertically, or diagonally.

---

### **🎮 How to Play**

#### **1. The Opening Phase**
* **Starting:** White begins by placing one piece anywhere on the board, followed by Black.
* **Minimum Requirement:** Each player **must** place at least **two** pieces on the board before they can begin moving or capturing.

#### **2. Player Choices**
Once two pieces are on the board, on your turn, you may choose **one** of the following actions:
1. **Place:** Put your third piece on any empty square.
2. **Move:** Move an existing piece according to standard Chess rules.
3. **Capture:** Remove an opponent’s piece from the board.

---

### **⚔️ Capture & Respawn Rules**
Capturing is a tactical reset rather than an elimination:

* **The Respawn:** When your piece is captured, you can immediately replace it anywhere on the board.
    * *Restriction:* You cannot place the respawned piece on a square that **immediately completes** a straight line to win the game.
* **The Cooldown:** After capturing a piece, you must wait **one full turn** before you are allowed to capture again.

> **Winning the Game:** The first player to align their 3 pieces in a row wins!

---

# 🛠️ Technology Stack and Tools Used

## Technologies Used

* Java
* Java Swing (GUI Development)
* Object-Oriented Programming (OOP)

## Tools Used

* IntelliJ IDEA
* Git & GitHub
* PlantUML

---

# ✨ Features and Functionalities Implemented

## Core Features

* Interactive 4x4 game board
* Turn-based multiplayer gameplay
* Chess-inspired movement system
* Piece placement and movement validation
* Win detection system
* Capture and respawn mechanics
* Cooldown system after captures
* Dynamic game status updates

## GUI Features

* Graphical user interface using Java Swing
* Highlighting selected pieces
* Real-time board updates
* Side panel for lobby pieces
* Error dialogs for invalid actions

## Additional Functionalities

* Game-over handling
* Player turn switching
* Board refresh system
* UML-based project design
