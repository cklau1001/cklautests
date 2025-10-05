import pandas as pd

#
# https://leetcode.com/problems/combine-two-tables/description/
#
def combine_two_tables(person: pd.DataFrame, address: pd.DataFrame) -> pd.DataFrame:

    result = pd.merge(person, address, on='personId', how='left')
    result = result[['firstName', 'lastName', 'city', 'state']]
    # result = result.loc[:, ["firstName", 'lastName', 'city', 'state']] # slower
    return result

# main
if __name__ == "__main__":

    persons = [
        {"personId": 1, "lastName": "Wang", "firstName": "Allen"},
        {"personId": 2, "lastName": "Alice", "firstName": "Bob"},
               ]

    addresses = [
        {"addressId": 1, "personId": 2, "city": "New York City", "state": "New York"},
        {"addressId": 2, "personId": 3, "city": "Leetcode", "state": "New York"},
    ]
    person_frame = pd.DataFrame(persons)
    # print(person_frame)
    address_frame = pd.DataFrame(addresses)
    # print(address_frame)

    merge_frame = combine_two_tables(person_frame, address_frame)
    print(merge_frame)