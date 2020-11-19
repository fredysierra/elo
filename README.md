#ELO Ranking Application

Ranking Players using [Elo](https://en.wikipedia.org/wiki/Elo_rating_system) algorithm.


## Configuration 

Given two files:

1. Players file: Each line has an ID number and a name for that player. Name and location fo the file is defined in  
[application.properties](src/main/resources/application.properties) with the property `players.filename`. Default value is [./players.txt](./players.txt)

2. Matches file: Each line contains the ID of the two players of a match and the first one is the winner of that match. Name and location fo the file is defined in  
[application.properties](src/main/resources/application.properties) with the property `matches.filename` Default value is [./matches.txt](./matches.txt)

## How to run the application

Required:

1. Java 11
2. Maven 

### Build the application

`mvn clean install`

### Run application using maven

Example:

`mvn spring-boot:run -Dspring-boot.run.arguments="SHOW_DETAILS 13"`

Use `-Dspring-boot.run.arguments` to send different options.

### Run jar application

After running `mvn clean install` jar application `elo-0.0.1-SNAPSHOT.jar` could be found on [./target](.target) directory.

Example:

`java -jar target/elo-0.0.1-SNAPSHOT.jar  SHOW_DETAILS 13`

Where `SHOW_DETAILS` and `13` are application options.

## Application options

* `LIST_PLAYERS`: Shows a list of players and their ranking, number of wins and losses. By default, the list is sorted by `RANKING`. However other sort options could be `ID`, `NAME`, `RANKING`, `WINS`, `LOSSES`. it is possible as well to change the sorting directions using the options `ASC`, `DES`. By default, the list is sorted by using `ASC`.

Example:
`java -jar target/elo-0.0.1-SNAPSHOT.jar  LIST_PLAYERS WINS DES`

* `SHOW_DETAILS`: Show the player details like ranking, number of wins, losses. Additionally, it shows whom the player won or lost and the number of times. For this option is mandatory a second parameter with the `playerId`.

Example:
`java -jar target/elo-0.0.1-SNAPSHOT.jar  SHOW_DETAILS 13`

* `GENERATE_MATCH`: Shows a list with Player ID pairs with suggested matches. Matches are arranged based on player ranking. Matches with similar player rankings will be fun and challenging for the players. A match is generated per player in the list. As the player list might not be even in terms of the number of players, a player might need to play twice.

### Runtime configuration options.

Location of the source files `players.filename` and `matches.filename` can be changed when running the application.

Example:
`java -jar target/elo-0.0.1-SNAPSHOT.jar SHOW_DETAILS 13 --players.filename=./new_players_file.txt`

## For Developers.

1. Console entry point is [io.fysus.elo.CommandRunner](src/main/java/io/fysus/elo/CommandRunner.java) class.

2. Based on the chosen option one of the [io.fysus.elo.controller.Controller](src/main/java/io/fysus/elo/controller/Controller.java) is executed.

3. Controllers classes rely on Services implementations in the package [io.fysus.elo.service](src/main/java/io/fysus/elo/service/) to obtain the information and that information is formatted and reported using one of the implementations of [io.fysus.elo.formatter.OutputFormatter](src/main/java/io/fysus/elo/formatter/OutputFormatter.java)
 
4. Service Implementation in the package [io.fysus.elo.service](src/main/java/io/fysus/elo/service/) rely on [io.fysus.elo.core.MatchAnalyzer](src/main/java/io/fysus/elo/core/MatchAnalyzer.java) to obtain match and player statistics.


## LICENSE

See [License](LICENSE.md)

## Questions?

Twitter: [@fysus](https://twitter.com/fysus)
Email: [fredysierra@gmail.com](mailto:fredysierra@gmail.com?subject=[Github Elo])
Linkedin: https://www.linkedin.com/in/fredysierra/

## Copyright

Copyright (c) 2020 Fredy Sierra




