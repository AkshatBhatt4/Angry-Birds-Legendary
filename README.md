# Angry Birds Legendary
A libGDX project generated with gdx-liftoff.

This project was generated with a template including simple application launchers and an ApplicationAdapter extension.

To launch the game you have to first bulid the project using gradle and then launch it by running Lwjgl3Launcher.

This is an Angry Birds Game created by Akshat Bhatt(2023059) and Nitesh Yaduv(2023356) using Java and libraryGDX.
[GitHub Private Repository](https://github.com/AkshatBhatt4/Angry-Birds-Legendary)

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.

## Steps to Setup and run the project:
    1. Download and extract all the files. And open them in Idea IDE.
    2. Let all the files be loaded.
    3. Open the gradle tab from the left taskbar.
    4. Click on 'Tasks' then 'Application' then 'run' to reun the program.

## Structure
The project contains the following Java files:

- `ArmoredPig.java`
- `BasicPig.java`
- `BlackBird.java`
- `BlueBird.java`
- `GameCharacter.java`
- `GlassStructure.java`
- `KingPig.java`
- `Level.java`
- `Level1.java`
- `Level2.java`
- `Level3.java`
- `LevelSelectScreen.java`
- `LooseScreen.java`
- `Main.java`
- `RedBird.java`
- `StoneStructure.java`
- `Structure.java`
- `TextureManager.java`
- `VictoryScreen.java`
- `WoodStructure.java`

## Steps to test/use the Program:
1. You need to select whether you want to enter the interface or quit it.
2. You need to select your User type.
3. At every single step you will have an option to go back to either the previous menu or logout to the main menu.
4. You will have to Log in/Signup (Admin username-admin password-admin@123) You can also use pre-fed profiles of student/professor.
5. If you choose to signup (only for student and professor) you will have to log in again using those credentials.
6. At log in you will be authenticated and then brought to the menu of the respected user.
    - For Administrator: the menu allows him to:
        - Manage Course Catalog
        - Manage Student Records
        - Assign Professors to Courses
        - Handle Complaints
        - View Profile and
        - edit profile.
    - For Professor: the menu allows him to:
        - Manage Courses
        - View Enrolled Students
        - View Profile and
        - edit profile.
    - For Student: the menu allows him to:
        - View Available Courses
        - Register for Courses
        - View Schedule
        - Track Academic Progress
        - Drop Courses
        - Submit Complaint
        - View Complaint Status
        - View Profile and
        - edit profile.
7. A student can write complaints to admin and view status. Admin can also view the complaints sorted by status and after reading have the option to change the status of the complaint.
8. The program will save the data as long as the user stays in the interface and the data changes will be also reflected and after exiting the interface the program will terminate and so wil the data .
9. Even though error handling has been implemented in most of the inputs it is suggested that the user stays careful of the input.

## Sources
1. [Angry Birds Fandom](https://angrybirds.fandom.com/wiki/Angry_Birds_Wiki)
2. [Google Images](https://images.google.com)
3. [YouTube playlist for reference](https://www.youtube.com/watch?v=YCGmXVCvogY&list=PLrnO5Pu2zAHKAIjRtTLAXtZKMSA6JWnmf)
