import pandas as pd
import numpy
from numpy.ma.core import empty_like


def second_highest_salary(employee: pd.DataFrame) -> pd.DataFrame:

    # sorted_salaries = employee.sort_values(by='salary', ascending=False).drop_duplicates()
    sorted_salaries = employee['salary'].sort_values(ascending=False).drop_duplicates()

    print(sorted_salaries)

    second_highest: numpy.int64 = None if len(sorted_salaries) <= 1 else sorted_salaries.iloc[1]

    # value = second_highest
    #int_value = second_highest.item()
    #print(f"value={value}, type={type(value)}")
    #print(f"int_value={int_value}, type={type(int_value)}")

    # how to create a dataframe
    return pd.DataFrame([{"SecondHighestSalary": second_highest}])

def nth_highest_salary(employee: pd.DataFrame, N: int) -> pd.DataFrame:

    if N < 1:
        return pd.DataFrame({f"getNthHighestSalary({N})": [None]})
    # sort it
    sorted_salaries = employee['salary'].sort_values(ascending=False).drop_duplicates()
    nth_hightest = None if len(sorted_salaries) < N else sorted_salaries.iloc[N-1]

    return pd.DataFrame({f"getNthHighestSalary({N})": [nth_hightest]})

# main

if __name__ == "__main__":

    employees = [
        {"id": 1, "salary": 100},
        {"id": 2, "salary": 200},
        {"id": 3, "salary": 300},
    ]

    employees_frame = pd.DataFrame(employees)
    second_frame = second_highest_salary(employees_frame)
    nth_frame = nth_highest_salary(employees_frame, -1)

    print("==== result ===")
    print(nth_frame)

    # A and B are columns
    # [...] are the rows
    #   A  B
    #   1  4
    #   2  5
    #   3  6
    #
    print("==== aframe ====")
    a_frame = pd.DataFrame({'A': [1, 2, 3], 'B': [4, 5, 6]})
    print(a_frame.shift(1))

    # dataframe
    print("===== bframe ===")
    b_frame = pd.DataFrame({'current': [1,1,1,2,3,4,5]})
    b_frame['previous'] = b_frame['current'].shift(1)
    b_frame['next'] = b_frame['current'].shift(-1)
    be = b_frame[(b_frame['current'] == b_frame['previous']) & (b_frame['current'] == b_frame['next'])]
    print(b_frame)
    print(be)


