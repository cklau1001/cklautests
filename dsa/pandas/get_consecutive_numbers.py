import pandas as pd

#
# https://leetcode.com/problems/consecutive-numbers/description/
#
def consecutive_numbers(logs: pd.DataFrame) -> pd.DataFrame:

    return consecutive_number_by_shift(logs)

def consecutive_number_by_shift(logs: pd.DataFrame) -> pd.DataFrame:

    logs["previous"] = logs['num'].shift(1)
    logs["next"] = logs['num'].shift(-1)

    # con_logs = (logs[(logs["num"] == logs["previous"])
    #                & (logs["num"] == logs["next"])]
    #            .drop_duplicates(subset='num', keep='first'))

    con_logs = logs[(logs["num"] == logs["previous"]) & (logs["num"] == logs["next"])]

    # print(con_logs)
    consecu_logs = pd.DataFrame({'ConsecutiveNums': con_logs['num'].unique()})
    print(consecu_logs.reset_index(drop=True))

    return consecu_logs.reset_index(drop=True)

# main

if __name__ == "__main__":

    log_data = {"id": [1,2,3,4,5,6,7,8,9], "num": [1,1,1,1,2,1,2,2,2]}
    log_frame = pd.DataFrame(log_data)
    conlog_frame = consecutive_number_by_shift(log_frame)

    # How to convert a dataframe to a dictionary
    conlog_dict = conlog_frame.to_dict(orient='records')

    print("===== dict ======")
    print(conlog_dict)