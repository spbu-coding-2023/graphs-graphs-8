# Graph Visualizer
## About
Application for graph analysis using embedded algorithms

## Quick start
### Pre-requisites
- Kotlin 1.9.23
- Gradle 8.5
- JDK 21
#### Start
Clone repository

```
git clone git@github.com:odiumuniverse/GraphVisualizer.git //SSH
```
```
git clone https://github.com/odiumuniverse/GraphVisualizer.git //HTTPS
```
Go to the application folder
```
cd GraphVisualizer
```
Now, run it by
```
./gradlew run
```
### Import and Exports
You can import and export graphs using SQLite and Neo4j
- When using Neo4j you have to enter your data (we do not use your data)
- SQLite is a local database, you don't need to do anything, just select it in the application settings

## Features
#### You can use basic algorithms
- Searching for clusters
- Betweenness centrality
- Graph layout
#### There are also other algorithms
- Finding bridges
- Finding cycles
- Finding the shortest path using Ford-Bellman and Dijkstra algorithms
- Finding strong connectivity components
- Constructing a minimal island tree

## Licence
The app is distributed under [Unlicence](https://unlicense.org/), meaning we are putting this project into the public domain
## Contributing
 We do not support contributing, so please write to the authors with your suggestions
## Authors
- [Aleksey Dmitrievtsev](https://github.com/admitrievtsev)
- [Gleb Nasretdinov](https://github.com/Ycyken)
- [Azamat Ishbaev](https://github.com/odiumuniverse)

