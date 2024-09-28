# Ampersand_test
During the development of the user management app, a few key assumptions were made:

	1.	Assumptions:
	•	API Responses: Since no external API was integrated, we used hardcoded user data within the app. If this were a live application, we would assume that the API would return user details in a predictable structure (e.g., user ID, name, position, and photo).
	•	Device Types: We assumed that the app would run primarily on Android phones and tablets. As such, the layout and navigation were optimized for smaller screens, but we did not explicitly design for other form factors like foldables or Android TVs.
	•	Error Handling: For simplicity, we did not implement extensive error handling or data validation (e.g., checking for null values or network errors when fetching data from an API).
	•	Navigation: It is assumed that users would access user details by selecting an item from the list, and a back button would allow them to return to the previous screen.
	2.	Libraries/Tools Used:
	•	Jetpack Compose: For building the UI declaratively.
	•	Navigation Component: Used for navigating between the user list and user details screens.
	•	Material3: Used for modern UI elements, including the TopAppBar and Scaffold.
	3.	Instructions to Set Up:
	•	Ensure that you have Android Studio installed.
	•	Clone the repository from the remote URL.
	•	Open the project in Android Studio and sync the project to install all dependencies.
	•	Use an emulator or connect an Android device to run the app via the Run button in Android Studio.
 
