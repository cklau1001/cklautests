import pandas as pd
from datetime import datetime

class GameAnalysis:

    def get_first_logon_date(self, activity: pd.DataFrame) -> pd.DataFrame:
        """
        Get the first logon date of each player using group-by and then aggregate
        :param activity:
        :return:
        """
        first_logon_per_player = activity.groupby("player_id").aggregate({'event_date': 'min'})
        # activity_by_logon = activity.groupby("player_id")['device_id'].sum()

        print(first_logon_per_player.reset_index())
        return first_logon_per_player.rename(columns={'event_date': 'first_login'}).reset_index()

    def get_first_logon_date_v11(self, activity: pd.DataFrame) -> pd.DataFrame:

        first_logon_per_player = activity.groupby('player_id')['event_date'].min().reset_index()
        first_logon_per_player = first_logon_per_player.rename(columns={'event_date': 'first_login'})
        print(first_logon_per_player)

        return first_logon_per_player

    def get_first_logon_date_v2(self, activity: pd.DataFrame) -> pd.DataFrame:
        result=activity.sort_values(by=['player_id','event_date'],ascending=True)
        print(result)

        result=result.drop_duplicates(subset=['player_id'],keep='first')

        result = result.rename(columns={'event_date': 'first_login'})
        return result[['player_id', 'first_login']]

    def get_fraction_player_logon_two_consecutive_days(self, activity: pd.DataFrame) -> pd.DataFrame:

        num_players = activity['player_id'].nunique()
        # print(f"num_players={num_players}")

        player_logon_date = activity.groupby("player_id")["event_date"].min() + pd.Timedelta(days=1)
        # print(player_logon_date)

        consecutive_player = activity[activity['event_date'] == activity.groupby("player_id")["event_date"].transform('min') + pd.Timedelta(days=1)]

        # print(consecutive_player.shape)

        fraction_frame = pd.DataFrame({'fraction': [round(consecutive_player.shape[0] / num_players, 2)]})
        print(fraction_frame)

        return fraction_frame

# main

if __name__ == "__main__":

    game_data = {
        "player_id": [1, 1, 2, 3, 3],
        "device_id": [2, 2, 3, 1 ,4],
        "event_date": [
            datetime.strptime("2016-03-01", "%Y-%m-%d"),
            datetime.strptime("2016-03-02", "%Y-%m-%d"),
            datetime.strptime("2016-06-25", "%Y-%m-%d"),
            datetime.strptime("2016-03-02", "%Y-%m-%d"),
            datetime.strptime("2016-07-03", "%Y-%m-%d")
        ],
        "games_played": [5, 6, 1, 0, 5]
    }

    game_frame = pd.DataFrame(game_data)

    ga = GameAnalysis()
    # ga.get_first_logon_date_v11(game_frame)
    ga.get_fraction_player_logon_two_consecutive_days(game_frame)
