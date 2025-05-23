# Business App Starter with Spring Boot

Business App is a starter for Vaadin with Spring Boot integration. 

[Live demo](https://labs.vaadin.com/business/)

The starter gives you a productivity boost and a head-start. You get an app shell with a typical hierarchical left-hand menu. The shell, the views and the components are all responsive and touch friendly, which makes them great for desktop and mobile use. The views are built with Java, which enhances Java developers' productivity by allowing them to do all in one language.

The app comes with multiple list views to edit master-detail data. Views can be divided horizontally or vertically to open up the details, and the details can also be split into multiple tabs for extra space. The details can also be opened fullscreen to maximize the use of space. Additionally there is an opt-in option for opening multiple application views in tabs within the app, for quick comparison or navigation between data. You enable this feature by setting MainLayout.navigationTabs to true.

You can read the detailed documentation in [Vaadin Docs](https://vaadin.com/docs/business-app/overview.html)

## Running the Project in Development Mode

```
mvn spring-boot:run
```

Wait for the application to start

Open http://localhost:8083/ to view the application.

## Running the Project in Production Mode

```
mvn spring-boot:run -Pproduction
```

The default mode when the application is built or started is 'development'. The 'production' mode is turned on by using the `production` profile when building or starting the app.

Note that if you switch between running in production mode and development mode, you need to do
```
mvn clean
```
before running in the other mode.

## Database

The application uses an in-memory H2 database by default. You can access the H2 console at http://localhost:8080/h2-console when the application is running.

Connection details:
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password
