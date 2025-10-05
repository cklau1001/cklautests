import pandas as pd
from datetime import datetime

class RisingTemperature:

    def rising_temperature(self, weather: pd.DataFrame) -> pd.DataFrame:

        weather = weather.sort_values(by = 'recordDate')
        # diff() return the difference between two immediate rows
        rising_temperature = weather[(weather['temperature'].diff() > 0) & (weather['recordDate'].diff().dt.days == 1)]
        # rising_temperature1 = rising_temperature.loc[:,'id']  # return series
        # rising_temperature1 = pd.DataFrame({'id': rising_temperature1})

        print(rising_temperature[['id']])

        return rising_temperature[['id']]





# main

if __name__ == "__main__":

    weather_data = {
        "id": [1, 2, 3, 4,5],
        "recordDate": [
            datetime.strptime("2015-01-01", "%Y-%m-%d"),
            datetime.strptime("2015-01-02", "%Y-%m-%d"),
            datetime.strptime("2015-01-03", "%Y-%m-%d"),
            datetime.strptime("2015-01-04", "%Y-%m-%d"),
            datetime.strptime("2015-01-05", "%Y-%m-%d"),
        ],
        "temperature": [10, 25, 20, 30, 40]
    }
    rt = RisingTemperature()
    weather_frame = pd.DataFrame(weather_data)

    rt.rising_temperature(weather_frame)

