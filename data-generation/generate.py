#!/usr/bin/python3
import requests
import csv
import sys
import io

HOME_TEAM = "Team 1"
AWAY_TEAM = "Team 2"


def download_data(url):
    response = requests.get(url)
    buff = io.StringIO(response.text)
    return csv.DictReader(buff)


def is_winner(score):
    if (int(score[0]) > int(score[1])):
        return HOME_TEAM
    elif (int(score[0]) < int(score[1])):
        return AWAY_TEAM
    else:
        return "DRAW"


def determine_points(winner, team):
    if (winner == team):
        return 3
    elif (winner == "DRAW"):
        return 1
    else:
        return 0


def determine_goal_diff(data):
    return (data['goalsFor'] - data['goalsAgainst'])


def write_csv(path, headers, data, file_name):
    with open(f'{path}/{file_name}.csv', 'w') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=headers)
        writer.writeheader()
        writer.writerows(data)


def generate_standings(match_data):
    teams = []
    matches = []
    for data in match_data:
        score = data["FT"]
        score = score.split("-")
        matches.append({
            "homeTeam": data[HOME_TEAM],
            "awayTeam": data[AWAY_TEAM],
            "homeScore": score[0],
            "awayScore": score[1]
        })
        team = list(filter(lambda x: x["teamName"] == data[HOME_TEAM], teams))
        if len(team) > 0:
            team[0]['played'] += 1
            team[0]['points'] += determine_points(is_winner(score), HOME_TEAM)
            team[0]['won'] += 1 if is_winner(score) == HOME_TEAM else 0
            team[0]['drawn'] += 1 if is_winner(score) == "DRAW" else 0
            team[0]['lost'] += 1 if is_winner(score) == AWAY_TEAM else 0
            team[0]['goalsFor'] += int(score[0])
            team[0]['goalsAgainst'] += int(score[1])
            team[0]['goalDifference'] = determine_goal_diff(team[0])
        else:
            teams.append({
                "teamName": data[HOME_TEAM].strip(),
                "played": 1,
                "points": determine_points(is_winner(score), HOME_TEAM),
                "won": 1 if is_winner(score) == HOME_TEAM else 0,
                "drawn": 1 if is_winner(score) == "DRAW" else 0,
                "lost": 1 if is_winner(score) == AWAY_TEAM else 0,
                "goalsFor": int(score[0]),
                "goalsAgainst": int(score[1]),
                "goalDifference": int(score[0]) - int(score[1]),

            })

        team = list(filter(lambda x: x["teamName"] == data[AWAY_TEAM], teams))
        if len(team) > 0:
            team[0]['played'] += 1
            team[0]['points'] += determine_points(is_winner(score), AWAY_TEAM)
            team[0]['won'] += 1 if is_winner(score) == AWAY_TEAM else 0
            team[0]['drawn'] += 1 if is_winner(score) == "DRAW" else 0
            team[0]['lost'] += 1 if is_winner(score) == HOME_TEAM else 0
            team[0]['goalsFor'] += int(score[1])
            team[0]['goalsAgainst'] += int(score[0])
            team[0]['goalDifference'] = determine_goal_diff(team[0])
        else:
            teams.append({
                "teamName": data[AWAY_TEAM].strip(),
                "played": 1,
                "points": determine_points(is_winner(score), AWAY_TEAM),
                "won": 1 if is_winner(score) == AWAY_TEAM else 0,
                "drawn": 1 if is_winner(score) == "DRAW" else 0,
                "lost": 1 if is_winner(score) == HOME_TEAM else 0,
                "goalsFor": int(score[1]),
                "goalsAgainst": int(score[0]),
                "goalDifference": int(score[0]) - int(score[1]),

            })
    return [sorted(teams, key=lambda x: (-x["points"], -x["goalDifference"], x["teamName"])), matches]


def write_standings_to_csv(standings, path):
    headers = standings[0].keys()
    write_csv(path, headers, standings, "standings")


def write_match_data_to_csv(matches, path):
    headers = matches[0].keys()
    write_csv(path, headers, matches, "match_data")


if __name__ == "__main__":
    data = download_data(sys.argv[1])
    data = generate_standings(data)
    write_standings_to_csv(data[0], sys.argv[2])
    write_match_data_to_csv(data[1], sys.argv[2])
