# AppDeveloperTests
AppDeveloperTests is a sample Android application written in Kotlin that showcases modern Android development practices. It leverages various libraries and tools to demonstrate fetching and displaying a list of users from the [Random User Generator API](https://randomuser.me/) using Jetpack Compose.

## Project Structure
The project follows the clean architecture structure, providing a clear separation of concerns:

- App: The top-level module responsible for the application's UI layer. It contains the Jetpack Compose UI components, view models, and UI-related logic.

  - Framework: This module contains implementations of the data and presentation layers using Android-specific frameworks, such as Room for local storage, Retrofit for network requests.
  - UI: The UI components, screens, and navigation logic reside in this module.

- Domain: The domain layer holds the business logic. It is independent of any framework and contains only pure Kotlin code.

- Data: The data layer is responsible for interacting with external data sources, such as network and local databases. It contains repository implementations and data models.

- Usecases: The use cases layer defines the high-level application operations that combine business rules and data manipulation logic. It acts as a bridge between the domain and data layers.

## Technologies Used
- **Kotlin** - The programming language used for Android development.
- **KSP (Kotlin Symbol Processing)** - A symbol processing extension for Kotlin.
- **Jetpack Compose** - Modern Android UI toolkit.
- **Material 3** - Design system for Compose applications.
- **Paging 3** - A library for efficiently loading large data sets from a data source.
- **Retrofit** - A type-safe HTTP client for Android.
- **Arrow** - Library for Typed Functional Programming in Kotlin.
- **Dagger Hilt** - Dependency injection library for Android.
- **Navigation Compose** - Navigation library for Jetpack Compose.
- **Splashscreen** - Splash screen API for a smoother app launch experience.
- **Coil** - Image loading library for Android.
- **Room** - A SQLite object mapping library.
- **JUnit 4** - Testing framework for Java and Kotlin.
- **MockK** - Mocking library for Kotlin.
- **Turbine** - A testing library for kotlinx.coroutines.
- **Kluent** - Fluent Assertion-Library for Kotlin.
- **MockWebServer** - Web server for testing HTTP clients.
- **Detekt** - Static code analysis tool for Kotlin.
- **KtLint** - Kotlin linter and formatter.
- **Gradle Version Catalogs** - Used for managing and sharing dependency versions in Gradle.

## Getting Started
1 - **Clone the repository:**
```
git clone https://github.com/JuJoDevs/AppDeveloperTests.git
```
2 - **Open the project in Android Studio.**

3 - **Build and run the application on an Android emulator or physical device.**

## Usage
The app will fetch a list of random users upon launch and display them using Jetpack Compose UI.
Tap on a user to view more details.

## Configuration
The project's dependencies and versions can be found in the gradle/libs.versions.toml file.

## Problems encountered during the development
Paging 3, while a powerful library for handling large data sets, presented difficulties in setting up effective unit tests, but I finally found a solution.

## License
This project is licensed under the MIT License.
