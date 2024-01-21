# Darwin Simulation
The Darwin Simulation project is a Java-based application that simulates the evolution of creatures inspired by the principles of Charles Darwin's theory of natural selection. With user data validation and tests implemented in JUnit.
Created by:
- [Radosław Rolka](https://github.com/radoslawrolka)
- [Weronika Wojtas](https://github.com/WerWojtas)

## Menu
### Simulation parameters
Menu provides configuring parameters in a simulation environment. The menu is organized into sections for:
- map settings,
- plant settings, 
- animal settings
<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/menu.png" width=800> 

### Config & Logs
Notably, the configuration section allows users to save and load settings, choose whether to save logs to CSV, set log names, and specify a move delay for the simulation. 

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/menu2.png" width=800>

### Validation 
If required field is empty or given data is invalid then user is informed about that with AlertBox and specified error message.

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/menu3.png" width=800>

## Simulation
### Game
simulation interface with visualizations for animal and plant counts, a grid for the simulation map, and statistics on current simulation state. It includes controls for pausing/resuming, starting, and toggling the display of preferred settings and the best animal. 
Legend:
- Blue animal - >75% of max energy
- Orange animal - >25% of max energy 
- Red animal - >0% of max energy 
- Black animal - 0% energy

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/game.png" width=800>

### Following Animal
Example view of running simulation with followed animal:

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/game2.png" width=800> 

### Prefferd Fields
Highlighted Preffered fields for plant growth:

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/game3.png" width=800> 

### Best Animal
White animal is higlighted as 'Best Animal':

<img src="https://github.com/radoslawrolka/PO_2023_ROLKA_WOJTAS/blob/master/img/game4.png" width=800> 

## Tests
The tests for the system aim to ensure the correctness, reliability, and robustness of the implemented functionalities. Test cases cover a variety of scenarios, including normal use cases, edge cases, and potential error conditions. The testing strategy involves unit tests and integration tests.

----------------------------------------------------------------------------------------------
