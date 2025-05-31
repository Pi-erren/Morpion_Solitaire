**Please see docs/Development_Report.pdf where we explain our work**  
  
* Authors: Doubabi Mustapha, Neveu Pierre
* Grade: 19/20
* Course: Object programming
* University: PSL Paris Dauphine
  
# Morpion Solitaire - Java project: University of Paris Dauphine

Our implementation of Morpion Solitaire game, see https://fr.wikipedia.org/wiki/Morpion_solitaire

## Nested Monte Carlo Search
We implemented the NMCS algorithm by Tristan Cazenave: https://www.lamsade.dauphine.fr/~cazenave/papers/nested.pdf.  
  
The paper results on Morpion solitaire:  
  <img width="895" alt="Capture d’écran 2025-05-31 à 14 53 31" src="https://github.com/user-attachments/assets/393a92cb-f044-4861-a720-618178cf38b4" />
  
Our results with limited computation ressources:  
<img width="925" alt="Capture d’écran 2025-05-31 à 14 55 26" src="https://github.com/user-attachments/assets/83e86c76-9c09-44d3-82bd-53b6434496b5" />
  
## Launch

The executable is "Morpion Solitaire.jar".
1) Go to root directory
```
java -jar "Morpion Solitaire.jar"
```
OR double click

## Documentation
In the the "docs" folder you can find:

1) The report of the development of the game: "Development_Report.pdf"
2) A user guide for the application: "User_Guide.pdf"
3) A class diagram: "class_diagram.png"

## Data
The folder data contains:

1) algo_scores.csv: a csv file containing the scores for the algorithms, see Development_Report.pdf for more
2) scoreboard.txt: a text file containing the player scores.

This folder must be in the same folder than the executable to guarantee its access.
