[![Build Status](https://travis-ci.org/felixreimann/opt4j.svg?branch=master)](https://travis-ci.org/felixreimann/opt4j)
[![Coverage Status](https://coveralls.io/repos/github/felixreimann/opt4j/badge.svg?branch=master)](https://coveralls.io/github/felixreimann/opt4j?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.opt4j/opt4j-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.opt4j/opt4j-core)
[![Javadocs](https://javadoc.io/badge/org.opt4j/opt4j-core.svg)](https://javadoc.io/doc/org.opt4j/opt4j-core)

#  Opt4J - Modular Java framework for meta-heuristic optimization 

Opt4J is an open source Java-based framework for evolutionary computation.
It contains a set of (multi-objective) optimization algorithms such as evolutionary algorithms (including SPEA2 and NSGA2), differential evolution, particle swarm optimization, and simulated annealing.
The benchmarks that are included comprise ZDT, DTLZ, WFG, and the knapsack problem.

The goal of Opt4J is to simplify the evolutionary optimization of user-defined problems as well as the implementation of arbitrary meta-heuristic optimization algorithms.
For this purpose, Opt4J relies on a module-based implementation and offers a graphical user interface for the configuration as well as a visualization of the optimization process.

## Usage
Opt4J can be used as standalone Swing-based application or as a library.

### Linux/Unix
Run:

	./start.sh

### Windows
Run:

	./start.bat

## Developer
Opt4J uses Gradle. Run

	./gradlew tasks

to show all available tasks.

Use

	./gradlew eclipse

to create the required .project and .classpath files to import the project in Eclipse.

Use

	./gradlew run

to execute the application.

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

Opt4J was formerly hosted at https://sourceforge.net/projects/opt4j/

## Credits

Brought to you by
* Martin Lukasiewycz
* Felix Reimann
* Michael Glaß

This project uses
* [https://github.com/google/guice](google/guice)
* [https://gitlab.ow2.org/sat4j/sat4j/](sat4j) (in subproject opt4j-satdecoding)

## License

Opt4J uses [MIT License](./LICENSE).
