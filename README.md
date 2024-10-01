# Ampersand_test


During the development of the user management app, several key assumptions were made:

Assumptions:

	•	API Responses: We assumed that the external API would return user details in a predictable JSON structure, containing fields such as user ID, name, position, and photo. 
	•	Proper error handling is in place to manage potential API errors, including scenarios where the API might be unreachable or return unexpected data formats. In such cases, the app gracefully displays error messages to the user instead of crashing.
	•	Device Types: The app is designed to run primarily on Android phones and tablets. The layout and navigation have been optimized for portable screens, although additional adaptations for other form factors like Android TVs have not been included.
	•	Navigation: It is assumed that users will access user details by selecting an item from the list, with a back button facilitating return navigation to the previous screen.

Libraries/Tools Used:

	•	Retrofit & OkHttp: For making API calls to fetch user data.
	•	Gson: For converting JSON responses into Kotlin data models.
	•	Jetpack Compose: For building the user interface declaratively.
	•	MVVM Architecture: Employed to separate concerns and facilitate better testability and maintainability of the codebase.
	•	ViewModel: Used to manage UI-related data in a lifecycle-conscious way.
	•	Material3: Utilized for modern UI components, including the TopAppBar and Scaffold.

Instructions to Set Up:

	1.	Ensure that you have Android Studio installed.
	2.	Clone the repository from the remote URL.
	3.	Open the project in Android Studio and sync the project to install all dependencies.
	4.	Use an emulator or connect an Android device to run the app via the Run button in Android Studio.

Throughout the development process, best practices were adhered to ensuring a robust, maintainable, and user-friendly application.
