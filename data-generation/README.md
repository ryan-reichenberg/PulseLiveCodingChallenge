# Football Data Generation Utility

**NOTE: This utility requires Python 3.x**

## Description
This utility will generate correct standings from match data and will also process the match data and normalise it into 
a form the Java code can process.

This utility expects csv data to be in football.csv format. 
More can be  seen here: https://github.com/footballcsv/spec/tree/master/docs


For example, standings.csv
```
teamName,played,points,won,drawn,lost,goalsFor,goalsAgainst,goalDifference
Manchester United FC,38,83,25,8,5,74,34,40
Arsenal FC,38,78,23,9,6,85,42,43
Newcastle United FC,38,69,21,6,11,63,48,15
Chelsea FC,38,67,19,10,9,68,38,30
Liverpool FC,38,64,18,10,10,61,41,20
Blackburn Rovers FC,38,60,16,12,10,52,43,9
Everton FC,38,59,17,8,13,48,49,-1
Southampton FC,38,52,13,13,12,43,46,-3
Manchester City FC,38,51,15,6,17,47,54,-7
Tottenham Hotspur FC,38,50,14,8,16,51,62,-11
Middlesbrough FC,38,49,13,10,15,48,44,4
Charlton Athletic FC,38,49,14,7,17,45,56,-11
Birmingham City FC,38,48,13,9,16,41,49,-8
Fulham FC,38,48,13,9,16,41,50,-9
Leeds United FC,38,47,14,5,19,58,57,1
Aston Villa FC,38,45,12,9,17,42,47,-5
Bolton Wanderers FC,38,44,10,14,14,41,51,-10
West Ham United FC,38,42,10,12,16,42,59,-17
West Bromwich Albion FC,38,26,6,8,24,29,65,-36
Sunderland AFC,38,19,4,7,27,21,65,-44
```
Match_data.csv
```
Leeds United FC,Manchester City FC,3,0
Fulham FC,Bolton Wanderers FC,4,1
Southampton FC,Middlesbrough FC,0,0
Everton FC,Tottenham Hotspur FC,2,2
Blackburn Rovers FC,Sunderland AFC,0,0
Charlton Athletic FC,Chelsea FC,2,3
... (truncated)
```

## Running
NOTE: First two steps are optional

Create the virtual python environment
```
python3 -m env  ./.venv
```
Set up the virtual python environment
```
source ./.venv/bin/active
```
Install dependencies
```
pip install -r requirements.txt
```
Run
```
python generate.py <url> <output dir>
<url> - Url to csv football data. E.g. https://raw.githubusercontent.com/footballcsv/deutschland/master/2000s/2005-06/de.1.csv
<output directory> - Where to save the generate files to. E.g. ./data/bundesliga
```


## Improvements
- This code is due for a refactoring - it's pretty quick and dirty at the moment
- The code isn't smart enough to make directories at the moment, it requires directories to already
exist
For example,
```
mkdir data/bundesliga
```