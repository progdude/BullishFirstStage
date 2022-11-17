## # Bullish First Stage Application



This is the repo for my Bullish First Stage Application. Most of the code is pretty straightforward, just wanted to mention a couple of key points:

* I've created CRUD endpoints for each of the necessary models we have. They can be found in the `controllers` folder.
  * CustomerController: To handle all operations regarding customers
  * ProductController: To handle all operations regarding Products
  * ProductDealControler: To allow the admin to add or remove deals on products
* Typically, in a project like this, I would like to create some sort of token based authentication, where we can use the JWT to authenticate who the user is and what access they are allowed. That seems out of the scope however, so I implemented a new simple approach:
  * We have two roles: `ADMIN` and `CUSTOMER` , which can be found in the enum `Role`
  * Each of these roles have a specified list of operations they're allowed to do
  * Typically we would put the token in the `bearer` header and then have the framework help with the auth. Here, I ask the user to include the role in the header, as a simplistic way to emulate some IAM
* Deals for products can have rather complex logic and it doesn't seem like a good idea to send that logic over an API endpoint
  * I have a `ProductDeal` interface that all deals can use. We should code the deal logic into the code and we will have a feature flag associated with each deal
  * This feature flag is a boolean value and will be stored in our persistent DB. It the value of a feature flag is set to `True`, only then are we allowed to apply the deal to the cart
* Using an actual persistent storage is out of the scope for this project, so in my `DatabaseClient` file, you'll see that I just use several HashMaps/Lists to store everythign in memory and "emulate" our DB
  * Using the `@Repository` feature of Spring to avoid recreating for all it's users
* The tests are located here: `src/test`
  * They are all basic JUnit tests



 You can run the service from `BullishFirstStageApplication`. 

To build first run `./mvnw install `. To run the application run: `./mvnw spring-boot:run `

To run the tests, you should be able to run: `./mvnw clean test` or just run them via your favorite IDE (IntelliJ, Eclipse, etc)

​	



### Potential Improvements

A list of some, but no where near all, improvements:

* Better code documentation for all the endpoints
* Implement an accurate authentication flow with IAM principles
* More unit tests to cover all possible scenarios
* Use an actual persistend DB to store our results
* Include logic for logging and recording necessary metrics
* Handle concurrency issues to make sure this system can scale better
* And a lot more. Done as an assessment, so the scope was limited.

