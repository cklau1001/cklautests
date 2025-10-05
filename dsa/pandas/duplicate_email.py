import pandas as pd


def duplicate_emails(person: pd.DataFrame) -> pd.DataFrame:

    dup_emails = person[person.duplicated(['email']) == True]

    # print(dup_emails)

    result = dup_emails[['email']].drop_duplicates(keep='first')
    result.reset_index(drop=True).rename(columns={'email': 'Email'}, inplace=True)

    print(result)
    return result

def duplicate_emails_groupby(person: pd.DataFrame) -> pd.DataFrame:

    dup_emails: [] = person.groupby('email').filter(lambda x: len(x) > 1)['email'].unique()

    dup_emails = pd.DataFrame({"Email": dup_emails})
    print(dup_emails)
    return dup_emails

def dupliate_emails_valuecount(person: pd.DataFrame) -> pd.DataFrame:

    # return type of value_counts = pandas series
    # it contains two properties
    #   - index which is the unique value ( ie. email in this case )
    #   - values which is the count of each values
    #
    dup_emails = person['email'].value_counts()
    dup_emails_1 = dup_emails[dup_emails > 1].index.tolist()
    print(dup_emails_1)

    return pd.DataFrame({'Email': dup_emails_1})

def delete_duplicate_emails(person: pd.DataFrame) -> None:
    """
    196
    https://leetcode.com/problems/delete-duplicate-emails/description/

    :param person:
    :return:
    """

    person.sort_values(by=['id'], ascending=True, inplace=True)
    # print(person)
    person.drop_duplicates(['email'], keep="first", inplace=True)

    print(person)
# main

if __name__ == "__main__":

    email_list = [
        {"id": 2, "email": "a@b.com"},
        {"id": 3, "email": "c@d.com"},
        {"id": 1, "email": "a@b.com"},
        {"id": 4, "email": "a@b.com"},
        {"id": 5, "email": "c@d.com"},
    ]

    person_frame = pd.DataFrame(email_list)

    # To get the row with id == 1
    # print(person_frame[person_frame["id"] == 1])

    # mark the id column with True on id == 1 and False on the rest
    print((person_frame["id"] == 1).__dict__)
    # To get the row with id == 1 by returning the row with True
    # print(person_frame[person_frame["id"] == 1])


    # To get the rows with email = a@b.com
    # print(person_frame[person_frame["email"] == "a@b.com"])
    # print(person_frame.iloc[0:1])
    # duplicate_emails(person_frame)
    # duplicate_emails_groupby(person_frame)
    # dupliate_emails_valuecount(person_frame)
    delete_duplicate_emails(person_frame)