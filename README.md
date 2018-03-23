# Flight Simulator App written in Java
This Java Swing App simulates a number of flights, showing real time movement and detecting crashes.

## User Interface
The user interface consists of 3 main parts. 
- On the top part of the window, a text box is shown, which shows the time passed since the start of simulation, the number of flights, the number of successful landings and crashes.
- The menu bar lets the user start and stop the current simulation and load new maps and aircrafts from the input/ directory
- On the right part of the window, helpful messages appear regarding the current events that happen during the simulation 
- On the main part of the window, a map is presented which covers 1200x600 nautical miles. The map is split into different blocks that cover 20x20 nautical miles. For each block the color represents the height of the land. 

On top of the map there are numerous airports whose locations are imported from the menu bar. In the `icons` directory there are images for airports and planes of different kinds. While there are images for each orientation, to show the plane in a diagonal orientation I am using an AffineTransform before drawing the plane image on the map

## Path calculation
For each aircraft at the start of the simulation the shortest path to its destination is calculated using Dijkstra's algorithm. The path also takes into account that each airport has a certain orientation. Each aircraft can move from its current cell to any of the 8 adjacent cells. The path is then saved in a separate data structure.

## Movement
Each aircraft is assigned a timer which is scheduled in regular intervals according to the current speed. While the aircraft is close to the starting airport or the landing airport it must have a speed lower than the speed limit. As a result, the timer is cancelled and rescheduled when approaching or leaving a speed limited zone. Furthermore, the aircraft must start at the height of the starting airport and it must land at the height of the ending airport. The aircraft category determines the elevation speed.

## Crashes
An aircraft might crash while in flight. That might happen due to lack of fuel, crashing with another aircraft, having a smaller height than the current block height.While I haven't done so yet, there is a way to avoid height related crashes using the extra height variable in the Position class. The plane then would have a path that would avoid blocks that might cause a crash. The checks related to each aircraft crashing on its own are executed while the position update happens while another thread is checking for crashes where two or more planes are in the same position. 

## Next Steps
- For now the map area has a fixed size. In the future I would like to implement a scrolling mechanism that allows the user to zoom in and out of the map.
- I would like to extend the path finding algorithm to take avoid crashes as much as possible.
- Instead of having 20x20nm blocks I plan on having the aircrafts move on blocks having a much smaller size to make the movement much more fluid and the crash detection more accurate.
