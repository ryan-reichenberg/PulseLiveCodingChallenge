# PulseLive Coding Challenge

NOTE: This application requires Java 11+ to run.

# Description
This application is a CLI application that will print a formatted league table to standard output. For example:
```
  Position                           Team     Played        Won      Drawn       Lost         GF         GA         GD     Points
         1                     Arsenal FC         38         26         12          0         73         26         47         90
         2                     Chelsea FC         38         24          7          7         67         30         37         79
         3           Manchester United FC         38         23          6          9         64         35         29         75
         4                   Liverpool FC         38         16         12         10         55         37         18         60
         5            Newcastle United FC         38         13         17          8         52         40         12         56
         6                 Aston Villa FC         38         15         11         12         48         44          4         56
         7           Charlton Athletic FC         38         14         11         13         51         51          0         53
         8            Bolton Wanderers FC         38         14         11         13         48         56         -8         53
         9                      Fulham FC         38         14         10         14         52         46          6         52
        10             Birmingham City FC         38         12         14         12         43         48         -5         50
        11               Middlesbrough FC         38         13          9         16         44         52         -8         48
        12                 Southampton FC         38         12         11         15         44         45         -1         47
        13                  Portsmouth FC         38         12          9         17         47         54         -7         45
        14           Tottenham Hotspur FC         38         13          6         19         47         57        -10         45
        15            Blackburn Rovers FC         38         12          8         18         51         59         -8         44
        16             Manchester City FC         38          9         14         15         55         54          1         41
        17                     Everton FC         38          9         12         17         45         57        -12         39
        18              Leicester City FC         38          6         15         17         48         65        -17         33
        19                Leeds United FC         38          8          9         21         40         79        -39         33
        20     Wolverhampton Wanderers FC         38          7         12         19         38         77        -39         33

```

NOTE: This application only handles csv files. Included is a utility to download match data, 
transform it into  a form this application can read in  and generate a corresponding
league table in csv format.


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
  - Spring-Kafka
  - Spring-batch?
  - Swagger
  - Actuator
- Logging
- Lombok
- Database
  - JPA
  - Hibernate
- Docker