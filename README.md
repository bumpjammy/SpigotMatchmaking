# SpigotMatchmaking

This API allows developers to create and manage queues within their Spigot plugin. Queues are used to match players with each other, supporting multiple gamemodes and elo ratings

## Features:
- The Queue class provides a template for creating and managing queues.
- Players can be added to a queue with or without an associated ELO rating.
- The minimum ELO difference of players can be specified
- The maximum number of players in a queue can be set.
- A match is found when the ELO difference between two players is within the specified range, or if no ELO difference is specified.
- When a match is found, the abstract `onMatchFound` method is called, allowing the developer to specify what should happen when a match is found.

## Possible improvements:
- Currently, the `checkForMatches` method is called every 5 seconds. This could be configurable by the developer to allow for more or less frequent checking.
- Adding the ability to prioritize matching players based on factors such as how long they have been waiting in the queue.
- Allowing developers to specify other criteria for matching players, such as game mode preferences.
