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
    1. Download and extract all the files. And open them in Idea IDE(supporting LibGDX library).
    2. Let all the files be loaded.
    3. Open the gradle tab from the left taskbar.
    4. Click on 'Tasks' then 'Application' then 'run' to run the program.

## Structure
The project contains the following Java files:

- `ArmoredPig.java`
- `BasicPig.java`
- `BlackBird.java`
- `BlueBird.java`
- `CollisionHandler.java`
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
1. The Program starts on the main screen.
2. It has a button to toggle music.(On by default every time you run.)
3. It has a start game button which takes you to the Level select page.
4. The Level select page has 3 levels(1-3) all unlocked for now(the level might change in the final app)
5. It also has a load button for loading previously  saved games.(Not functional right now )
6. Every level has birds on the left and pigs and structures on the right.
7. Save button to save the current state of the game and resume later.(not functional right now.)
8. pause  button on the top left
9. pause button has 3 options
    1. Resume
    2. Music Toggle
    3. Return to Level Select Screen
10. Just to showcase the Win and lose screens. Buttons to show the screens have been instantiated in each level for now.
11. Upon losing, you can either retry or return to the main menu.
12. Upon winning apart from retrying and returning to the main menu, You can also directly move to the next level.(If it exists)

## Assumptions
1. All levels are unlocked.
2. No special features of birds except for their weight and damage have been added.
3. Soad save are not working properly .

## Sources
1. [Angry Birds Fandom](https://angrybirds.fandom.com/wiki/Angry_Birds_Wiki)
2. [Google Images](https://images.google.com)
3. [YouTube playlist for reference](https://www.youtube.com/watch?v=YCGmXVCvogY&list=PLrnO5Pu2zAHKAIjRtTLAXtZKMSA6JWnmf)



