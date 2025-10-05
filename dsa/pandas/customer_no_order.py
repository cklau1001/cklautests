import pandas as pd

def find_customers_no_order(customers: pd.DataFrame, orders: pd.DataFrame) -> pd.DataFrame:
    """

    183
    https://leetcode.com/problems/customers-who-never-order/description/

    :param customers:
    :param orders:
    :return:  customers who order nothing
    """

    customer_orders: pd.DataFrame = pd.merge(customers, orders, left_on="id", right_on="customerId", how="left")
    customer_orders: pd.DataFrame = customer_orders[customer_orders["customerId"].isnull()]

    # create a series and pass to DataFrame
    no_order_customers = pd.DataFrame({'Customers': customer_orders['name']})

    print(no_order_customers)

    return no_order_customers

def find_customers_no_order_2(customers: pd.DataFrame, orders: pd.DataFrame) -> pd.DataFrame:

    customer_orders = pd.merge(customers, orders, left_on="id", right_on="customerId", how="left")
    mask = customer_orders["customerId"].isnull()
    no_order_customers = customer_orders[mask]
    print(type(no_order_customers['name']))
    no_order_customers = no_order_customers[['name']].rename(columns={'name': 'Customers'})
    # print(no_order_customers)

# main

if __name__ == "__main__":
    customer_data = [
        {"id": 1, "name": "Joe"},
        {"id": 2, "name": "Henry"},
        {"id": 3, "name": "Sam"},
        {"id": 4, "name": "Max"},
    ]

    order_data = [
        {"id": 1, "customerId": 3},
        {"id": 2, "customerId": 1},
    ]

    customers = pd.DataFrame(customer_data)
    orders = pd.DataFrame(order_data)

    find_customers_no_order_2(customers, orders)
