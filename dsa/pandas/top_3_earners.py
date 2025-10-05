import pandas as pd

class TopThreeEarners:

    def get_top_3_earners(self, employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:
        """
            Return top 3 earners from each department
            :arg
                employee: employee frame
                department: department frame
            :return
                dataFrame of top 3 earners from each department

            Related SQL
            -------------
            SELECT Department, Employee, Salary
            FROM (
                SELECT
                    e.departmentId,
                    e.name as Employee,
                    e.salary as Salary,
                    DENSE_RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) as rank,
                    d.name as Department
                FROM employee e
                INNER JOIN department d on e.departmentId = d.id
            ) ranked_employees
            WHERE rank <= 3;



        """

        # Create a column - rank that host the ranking info using dense method
        employee['rank'] = employee.groupby('departmentId')['salary'].rank(method='dense', ascending=False)

        # Get departmentId, name and salary columns by getting first 3 ranks
        top_earners = employee[employee['rank'] <= 3][['departmentId', 'name', 'salary' ]]

        top_earners = top_earners.sort_values(['departmentId', 'salary'], ascending=[True, False]).reset_index(drop=True)
        print(top_earners)
        # Only select department name, employee name and salary columns after merge
        top_name_earners = pd.merge(top_earners, department, left_on="departmentId", right_on="id", how="left")[["name_y", "name_x", "salary" ]]
        top_name_earners = top_name_earners.rename(columns={"name_y": "Department",
                                                            "name_x": "Employee",
                                                            "salary": "Salary"})


        #print(top_name_earners)
        return top_name_earners

    def get_highest_salary_each_department(self, employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:
        """
           185
           https://leetcode.com/problems/department-top-three-salaries/description/

           two approaches to get the results

        :param employee:
        :param department:
        :return:
            final result in DataFrame
        """
        return self.get_highest_salary_each_department_by_merge(employee, department)
        # return self.get_highest_salary_each_department_by_agg(employee, department)

    def get_highest_salary_each_department_by_merge(self, employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:

        # Approach 1: Merge two queries

        # set the column of max() as hight_salary
        highest_salary = employee.groupby('departmentId')['salary'].max().reset_index(name="highest_salary")
        # set the column of size() as total_employees
        employee_count = employee.groupby('departmentId').size().reset_index(name="Total employees")

        highest_employee_count_frame = pd.merge(highest_salary, employee_count, on='departmentId')
        # print(highest_salary)

        highest_employee_count_merge_frame = pd.merge(highest_employee_count_frame, department,
                                                      left_on='departmentId', right_on='id')[['name', 'highest_salary', 'Total employees']]

        highest_employee_count_merge_frame = highest_employee_count_merge_frame.rename(columns={'name': 'Department'})
        print(highest_employee_count_merge_frame)

        return highest_employee_count_frame

    def get_highest_salary_each_department_by_agg(self, employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:
        '''
            Get highest salary and employee count per department by agg() so that the query can be performed in one shot
        :arg
            employee: employee frame
            department: department frame
        :return
            result dataframe

        '''
        highest_employee_count_frame = employee.groupby('departmentId').agg(
            highest_salary = ('salary', 'max'),
            Total_employees = ('id', 'count')
        ).reset_index()

        highest_employee_count_merge_frame = pd.merge(highest_employee_count_frame, department,
                                                      left_on='departmentId', right_on='id')[['name', 'highest_salary', 'Total_employee']]

        highest_employee_count_merge_frame = highest_employee_count_merge_frame.rename(columns={
                                                'Total_employee': 'Total Employees',
                                                'name': 'Department'})
        print(highest_employee_count_merge_frame)

        return highest_employee_count_frame

if __name__ == "__main__":

    employee_data = [
        {"id": 1, "name": "Joe", "salary": 85000, "departmentId": 1},
        {"id": 2, "name": "Henry", "salary": 80000, "departmentId": 2},
        {"id": 3, "name": "Sam", "salary": 60000, "departmentId": 2},
        {"id": 4, "name": "Max", "salary": 90000, "departmentId": 1},
        {"id": 5, "name": "Janet", "salary": 69000, "departmentId": 1},
        {"id": 6, "name": "Randy", "salary": 85000, "departmentId": 1},
        {"id": 7, "name": "Will", "salary": 70000, "departmentId": 1},
    ]

    department_data = [
        {"id": 1, "name": "IT"},
        {"id": 2, "name": "Sales"},
    ]
    employee_frame = pd.DataFrame(employee_data)
    department_frame = pd.DataFrame(department_data)

    top_earners = TopThreeEarners()
    # top_earners.get_top_3_earners(employee_frame, department_frame)

    top_earners.get_highest_salary_each_department(employee_frame, department_frame)
