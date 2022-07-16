# PulseLive Coding Challenge

# Description
This application is a CLI application that will print a formatted league table to standard output. For example:

TODO

NOTE: This application only handles csv files. Included is a utility to download match data and generate a corresponding
league table in csv format.

# Design decisions

# Running
```
./gradlew run --args="<file path>"
<file path> - This isa file path to match data. See test/resources/data/match_data.csv as an example
```
## Testing
```
./gradlew test
```


# Known Issues
- No validations around the data

# Assumptions
- Average absolute score difference in the LeagueTableEntry as the int type implies a whole number but the average would be a double. 
As a result I treated this as the normal goal difference.
- The data coming in (e.g. Match data) will always be correct, so no validations are provided. 
In a real world example, it is  likely this data is coming from a messaging source (e.g. Kafka) and an entirely different service.
As such, it makes sense validation of the data (e.g. messages) are a responsibility of that service.

# Improvements
- SpringBoot
- Lombok