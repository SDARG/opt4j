[![SDARG Build & Test](https://github.com/SDARG/opt4j/actions/workflows/gradle.yml/badge.svg)](https://github.com/SDARG/opt4j/actions/workflows/gradle.yml)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/324a853969fa45d1871d6e764e24fe37)](https://app.codacy.com/app/felixreimann/opt4j?utm_source=github.com&utm_medium=referral&utm_content=felixreimann/opt4j&utm_campaign=badger)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/1079b342eee541c3b843d01c293c880b)](https://www.codacy.com/app/felixreimann/opt4j?utm_source=github.com&utm_medium=referral&utm_content=felixreimann/opt4j&utm_campaign=Badge_Coverage)
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
* [google/guice](https://github.com/google/guice)
* [sat4j](https://gitlab.ow2.org/sat4j/sat4j/) (in subproject opt4j-satdecoding)

## License

Opt4J uses [MIT License](./LICENSE).
