# Coursework1
Smart Home Simulator (coursework Programming 1, University of Southampton)

## Coursework Aims
-Understand how to construct simple classes and implement methods.
-Are able to take simple pseudo-code descriptions and construct working Java code from them.
-Can write a working program to perform a complex task.
-Have an understanding of object oriented programming.
-Can correctly use polymorphism and I/O.
-Can write code that it is understandable and conforms to good coding practice

## Specification
The aim of this coursework is to construct a simple simulation of the energy consumption- and energy
generation- of a smart house. In the house are a number of appliances which consume/generate electricity
or consume water when they operate, meters that record the amount of water and electricity
consumed/generated and a battery which stores excess electricity if the house happens to generate more
than it needs. 

### How to run the Smart Home Simulator

Once you have downloaded all java classes in one folder, compile the House.java class from terminal:

```
javac House.java
```
Run the program with these arguments:

```
java House CONFIGURATION_FILE n
```
Where CONFIGURATION_FILE is the file txt (it is provided in the repository) and "n" it is the number of hours
which the house will run the simulation 

Or just the configuration file:

```
java House CONFIGURATION_FILE
```
The simulation will run for 168 hours (1 week)

### Output example

```
javac House.java
java House house.txt 2
```
```

Day: 1
Time: 1:00
+--------------+--------------+--------------+
|   Utility    |    Meter     |    Cost(GPB) |
+--------------+--------------+--------------+
| Water        | 0.0          | 0.00         |
+--------------+--------------+--------------+
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+
| Utility name |    Meter     |   Cost(GPB)  | Units drawn from the mains  |  Units drawn from the battery |Units in the battery|
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+
| Electricity  | 4.6144753    | 0.06         | 4.6144753                   | 0.0                           | 0.0/500.0          |
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+

Day: 1
Time: 2:00
+--------------+--------------+--------------+
|   Utility    |    Meter     |    Cost(GPB) |
+--------------+--------------+--------------+
| Water        | 0.0          | 0.00         |
+--------------+--------------+--------------+
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+
| Utility name |    Meter     |   Cost(GPB)  | Units drawn from the mains  |  Units drawn from the battery |Units in the battery|
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+
| Electricity  | 10.39253     | 0.14         | 10.39253                    | 0.0                           | 0.0/500.0          |
+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+

The total cost is: 0.20GPB

```


