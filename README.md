## Task: calculate the approximate fuel cost of the business trips.

A GUI based, stand-alone application that takes 3 parameters from the user; 

1. the distance in miles, 

2. cars fuel efficiency in miles per gallon,

3. the type of the fuel (98-Octane and Diesel), the costs of the fuels are in per liter. The application should provide an ability to pick which fuel is used in the algorithm. 

The cost of fuel per litter will be read from a file CSV (comma-separated values).The parameters used to calculate the cost of fuel as well as the final cost should be saved in a file using CSV format (coma-separated values) on the server side.

The data should be transferred between client and server using a dedicated class object. The class will contain all the parameters required by the algorithm. 

The calculation and writing to a file should take place on server side, and the results should be transferred to a client and displayed on client side. 

**This design of application is thin client fat server.**

Implementation Notes:
- The layout of the application done by using FXML along with the Scene Builder.
- Java8.
- Apache NetBeans 11.
