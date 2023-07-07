**Ecommerce API Test**
This Java code performs API testing for an e-commerce application. It includes various API requests such as login, adding a product, creating an order, and deleting a product/order. The code uses the RestAssured library for making HTTP requests and handling responses.
**Prerequisites**
To run this code, you need to have the following dependencies set up:
•	RestAssured library
•	TestNG framework
•	Java SDK
**Getting Started**
To get started with this code, follow the instructions below:
1.	Clone the repository or download the code files.
2.	Open the code in your preferred Java development environment (e.g., Eclipse, IntelliJ).
3.	Make sure you have the required dependencies mentioned above set up.
4.	Update the code with appropriate values for the API endpoints, user credentials, and file paths as needed.
5.	Compile and run the EcommerceAPITest class.
**Code Structure**
The code is structured as follows:
1.	Importing necessary libraries and classes.
2.	Setting up logging for request and response.
3.	Performing the login request to obtain the authentication token and user ID.
4.	Adding a product using the obtained token and user ID.
5.	Creating an order with the added product.
6.	Deleting the added product and order.
7.	Asserting the response messages for successful deletion.
8.	The code includes error handling and logging of request/response details.
**Logging**
The code logs the request and response details to a file named logging.txt. This can be useful for debugging and analysing the API interactions.
