# Solitaire Compose

![Static Badge](https://img.shields.io/badge/1.9.20-purple?style=for-the-badge&logo=kotlin)

A simple solitaire game developed using Compose.


## Targets

| Target                                                                   | Platform | Status              |
|--------------------------------------------------------------------------|----------|---------------------|
| Android                                                                  | Android  | In-development 🔨   |
| Desktop                                                                  | JVM      | Awaiting Deployment |
| [IntelliJ Plugin](https://plugins.jetbrains.com/plugin/22697-solitaire)  | JVM      | Released            |
| [Kotlin/JS Website](https://bkmbigo.github.io/solitaire-compose/js)      | JS       | Deployed            |
| [Kotlin/Wasm Website](https://bkmbigo.github.io/solitaire-compose/wasm/) | Wasm     | Deployed            |
| Chrome Extension                                                         | --       | In-development 🔨   |

> **Note**  
> A firefox extension will be added in future
 
Currently, the chrome extension is written in javascript.

> The project has a VS Code extension. Although the extension works, there are significant performance issues that affect the target. Therefore, the target will not be deployed.

#### IntelliJ Plugin
You can download the IntelliJ plugin from [Jetbrains Marketplace](https://plugins.jetbrains.com/plugin/22697-solitaire) or by simply opening the plugins settings on your IDE and searching for Solitaire. After installing, You can open the plugin by clicking on the Menu Item `Solitaire` found in the Tools menu.

## Documentation

There are several documentation covering [the anatomy of a game](docs/anatomy_of_the_game.md) and others to come later

## Libraries

The project can only use a limited number of Kotlin libraries due to having a Wasm target. Libraries used are:

- Compose Multiplatform
- Voyager (Custom implementation on Wasm target).


## Contribution

Feel free to fork and create a pull request. Ensure you merge into the `development` branch 
