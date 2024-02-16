> **GETTING STARTED:** You must start from some combination of the CSV Sprint code that you and your partner ended up with. Please move your code directly into this repository so that the `pom.xml`, `/src` folder, etc, are all at this base directory.

> **IMPORTANT NOTE**: In order to run the server, run `mvn package` in your terminal then `./run` (using Git Bash for Windows users). This will be the same as the first Sprint. Take notice when transferring this run sprint to your Sprint 2 implementation that the path of your Server class matches the path specified in the run script. Currently, it is set to execute Server at `edu/brown/cs/student/main/server/Server`. Running through terminal will save a lot of computer resources (IntelliJ is pretty intensive!) in future sprints.

# Project Details

Project Name: Sprint 2 Server

Team Members: Lina Halim and Malin Leven

Estimated time to finish project: 14 hours

Repo Link: https://github.com/cs0320-s24/server-lhalim-mleven

# Design Choices

Caching - Contains all classes needed for caching and search with cache (User Story 3)
- Cache
- cachedSearch
- cachedServer
- CachingMain
- EvictionPolicy
- VolatileTTLCache
- VolatileTTLEvictionPolicy

CensusHelpers - Contains all classes needed to loadcsv, viewcsv, and broadband (User Story 1)
- Census
- CensusAPIUtilities
- CensusHandler

SearchHelpers - Contains classes used in to search for a target
- Search
- SearchHandler

server - Class with the main method that starts Spark
- Server

Classes from Sprint 1: CSV
- EarningsDisparity
- CreateEarningsDisparity
- CreateStringList
- CreatorFromRow

# Errors/Bugs

# Tests

We have various tests to test the caching, the server, and the different functionalities we have.

# How to

In Intellij terminal, input mvn package, then input ./run. The locak host will appear at port 8989: http://localhost:8989
To get to the different endpoints, enter the desired endpoint.

