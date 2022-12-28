## Table of contents

-   [Project Summary](#project-summary)
-   [Requirements](#requirements)
-   [How to Run Project](#how-to-run-project)
-   [Assessment and Results](#assessment-and-results)

## Project Summary
A simple 2D game with two levels written in Java for project 2B for the University of Melbourne subject SWEN20003 (Object Oriented Software Development) completed in semester 2 2022. It uses the Unimelb BAGEL library. 

##### Taken from the specifications for Project 2 
"The game features two levels : Level 0 is in the lab and Level 1 is in the Over Under. In Level 0, the player will be able to control Fae who has to move around the walls and avoid any sinkholes that are in the lab. If the player falls into a sinkhole, the player will lose health points. To finish the level, the player has to get to the gate, located in the bottom right of the window. If the player’s health reduces to 0, the game ends." 

"When the player finishes Level 0, Level 1 starts - Fae is now in the Over Under. To win the level and the game, the player must fight and defeat Navec. However, the player has to deal with more sinkholes and demons too. The player can cause damage to the demons and Navec when the player presses a certain key. Likewise, the demons and Navec can cause damage to the player. The player will also have to move around trees (they don’t cause any damage like the walls in Level 0)."

"Level 1 will also give the player the opportunity to change the timescale so that the difficulty of the game can be changed. Pressing a certain key will increase/decrease the speed of the demons and Navec. Like in Level 0, the game will end if the player’s health reduces to 0 or less." 

#### Game Synopsis 
##### Taken from the specifications for Project 2 
"A dark evil has arrived in your hometown. A group of government scientists have opened a gate in their laboratory to another dimension called the Over Under. The Over Under is ruled by Navec (an evil creature of immense power) and his henchmen are called demons. The scientists thought they could control these creatures but alas they failed and are being held captive in the Over Under. Navec has created sinkholes that will destroy the lab and is planning on eventually destroying your world.

The player’s name is Fae, the daughter of one of the scientists. In order to save your father and your town, you need to avoid the sinkholes, find the gate in the lab and defeat Navec & his demons in the Over Under..."

#### Game Controls
ESC: quit game 
Spacebar: start game
UP, DOWN, LEFT, RIGHT Arrow keys: move player character (Fae) in respective directions 
A: attack 
W: switch to level 1 (second level) of game
K: slow down enemies (only available in second level)
L: speed up enemies (only available in second level)

## Requirements
SDK Amazon Corretto version 17 (corretto-17) 

#### Packages Required 
None

## How to Run Project 
#### In IntelliJ IDE
- Open project in IDE
- Open src/main directory 
- Run ShadowDimension.java file 

#### Using Terminal (MACOS)/Command Prompt (Windows)

## Assessment and Results 
Final Mark: 14/14 (100%) 

##### Final Mark Breakdown
Implementation: 10/10 
    – Correct implementation of start screen and entity creation: 0.5 marks
    – Correct implementation of the player’s attack behaviour (including images and states): 2 marks
    – Correct implementation of demon & Navec’s behaviour (including images, states and health points rendering): 2 marks
    – Correct implementation of fire behaviour (for both demons & Navec): 2 marks
    – Correct implementation of stationary items’ behaviour: 1 mark
    – Correct implementation of timescale controls: 1 mark
    – Correct implementation of level transition: 0.5 marks
    – Correct implementation of bounds, win and end of game logic: 1 mark

Coding Style: 4/4
    – Delegation: breaking the code down into appropriate classes: 0.5 marks
    – Use of methods: avoiding repeated code and overly complex methods: 0.5 marks 
    – Cohesion: classes are complete units that contain all their data: 0.5 marks
    – Coupling: interactions between classes are not overly complex: 0.5 marks
    – General code style: visibility modifiers, magic numbers, commenting etc.: 1 mark 
    – Use of Javadoc documentation: 1 mark