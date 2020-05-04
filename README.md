### Getting started

**Requirements**: 
- JDK 8
- Maven

To build application you should in project root
```
mvn package
```

To run application you should run in project root
```
java -jar ./target/ticks-0.0.1-SNAPSHOT.jar 
```

It is possible to configure application using:

| Name            | Default value | Meaning |
|-----------------|---------------|---------|
| window        |60000               |the size of the time window used for statistics collection (in ms)          |
| accuracy      | 1000              | how often statistics can be changed (in ms). In case of 1000 - only value of retrieved timestamp till seconds will be used |
| scale                |  2             |the scale for rounding during average calculation         |

example of run with parameters:
```
java -jar ./target/ticks-0.0.1-SNAPSHOT.jar --window=60000 --scale=2 --accuracy=1000
```

### Short explanation 

The main idea was to calculate statistics in advance after every tick.

### Assumptions I made while developing

- High availability is better than consistency
- ROUND_HALF_DOWN mode should be used for rounding of double numbers
- timestamp in tick cannot be from the future
- possible instrument values are not changing often

### What I'd like to improve if I had more time

- Tests quality. Unfortunately I could not find the good framework to test different concurrent situations during unit tests.
Writing cases by myself could take too big effort. Also there is no negative scenarious because I assumed that input data is always correct :) 

- Improve consistency and make it customizable.

- Add validation for the configuration values

- Make statistics for an instrument to not store all instruments which were ever processed even if there are no updates for them

### Did I like challenge?

Definitely