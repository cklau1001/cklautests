import pandas as pd
from datetime import datetime

class TripUtil:

    def get_cancellation_rate_per_day(self, trips: pd.DataFrame, users: pd.DataFrame) -> pd.DataFrame:
        """
        262
        https://leetcode.com/problems/trips-and-users/description/

        An example how to query two frames with common columns without a merge()

        :param trips:  input trip per day
        :param users:  status of both clients and drivers
        :return:
            cancellation rate from 2013-10-01 to 2013-10-03
        """

        # Get a list of unbanned clients and drivers
        unbanned_users = users[users.banned == "No"]
        # print(unbanned_users)

        #
        # Filter trips from 2013-10-01 to 2013-10-03
        # if datetime cannot be supported by between(), use string comparison instead
        #
        trips = trips[trips.request_at.between(
            datetime.strptime("2013-10-01", "%Y-%m-%d"),
            datetime.strptime("2013-10-03", "%Y-%m-%d"))]

        # add a cancelled column in original frame
        # This is to avoid SettingWithCopyWarning due to attempt to modify the view (unbanned_trips) later
        trips["cancelled"] = trips.status != "completed"

        # filter trips from 2013-10-01 to 2013-10-03

        unbanned_trips = trips[
            (trips.client_id.isin(unbanned_users.users_id)) &
            (trips.driver_id.isin(unbanned_users.users_id))
        ]

        # mean() can work on boolean column and round the mean() to two decimal places
        cancelled_ratio_frame = unbanned_trips.groupby("request_at").agg(
            {"cancelled": "mean"}
        ).round(2).reset_index()

        cancelled_ratio_frame = cancelled_ratio_frame.rename(columns={
            "request_at": "Day",
            "cancelled": "Cancellation Rate"
        })
        print(cancelled_ratio_frame)
        return cancelled_ratio_frame

# main

if __name__ == "__main__":

    status = ["completed", "cancelled_by_driver", "cancelled_by_client"]
    ban_status = ["No", "Yes"]
    user_type = ["client", "driver"]

    trip_data = [
        {"id": 1, "client_id": 1, "driver_id": 10, "city_id": 1, "status": status[0], "request_at": datetime.strptime("2013-10-01", "%Y-%m-%d") },
        {"id": 2, "client_id": 2, "driver_id": 11, "city_id": 1, "status": status[1], "request_at": datetime.strptime("2013-10-01", "%Y-%m-%d") },
        {"id": 3, "client_id": 3, "driver_id": 12, "city_id": 6, "status": status[0], "request_at": datetime.strptime("2013-10-01", "%Y-%m-%d") },
        {"id": 4, "client_id": 4, "driver_id": 13, "city_id": 6, "status": status[2], "request_at": datetime.strptime("2013-10-01", "%Y-%m-%d") },
        {"id": 5, "client_id": 1, "driver_id": 10, "city_id": 1, "status": status[0], "request_at": datetime.strptime("2013-10-02", "%Y-%m-%d") },
        {"id": 6, "client_id": 2, "driver_id": 11, "city_id": 6, "status": status[0], "request_at": datetime.strptime("2013-10-02", "%Y-%m-%d") },
        {"id": 7, "client_id": 3, "driver_id": 12, "city_id": 6, "status": status[0], "request_at": datetime.strptime("2013-10-02", "%Y-%m-%d") },
        {"id": 8, "client_id": 2, "driver_id": 12, "city_id": 12, "status": status[0], "request_at": datetime.strptime("2013-10-03", "%Y-%m-%d") },
        {"id": 9, "client_id": 3, "driver_id": 10, "city_id": 12, "status": status[0], "request_at": datetime.strptime("2013-10-03", "%Y-%m-%d") },
        {"id": 10, "client_id": 4, "driver_id": 13, "city_id": 12, "status": status[1], "request_at": datetime.strptime("2013-10-03", "%Y-%m-%d") },
    ]

    user_data = [
        {"users_id": 1, "banned": ban_status[0], "role": user_type[0]},
        {"users_id": 2, "banned": ban_status[1], "role": user_type[0]},
        {"users_id": 3, "banned": ban_status[0], "role": user_type[0]},
        {"users_id": 4, "banned": ban_status[0], "role": user_type[0]},
        {"users_id": 10, "banned": ban_status[0], "role": user_type[1]},
        {"users_id": 11, "banned": ban_status[0], "role": user_type[1]},
        {"users_id": 12, "banned": ban_status[0], "role": user_type[1]},
        {"users_id": 13, "banned": ban_status[0], "role": user_type[1]},
    ]

    trip_frame = pd.DataFrame(trip_data)
    user_frame = pd.DataFrame(user_data)

    tu = TripUtil()
    tu.get_cancellation_rate_per_day(trip_frame, user_frame)