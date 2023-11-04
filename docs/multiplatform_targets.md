## Kotlin/Wasm

Wasm is the new and still experimental kotlin target. Find out more in the [official website](https://kotl.in/wasm)
and [samples](https://github.com/Kotlin/kotlin-wasm-examples/)

## Configure your project for Compose and Kotlin/Wasm

#### Build Configuration

Add the Wasm target in the module's build.gradle.kts file

###### build.gradle.kts

```kotlin
plugins {
    id("org.jetbrains.kotlin") version "1.9.20"
    id("org.jetbrains.compose") version "1.5.10-dev-wasm02"
    ...
}

kotlin {
    ...
    wasmJs {
        moduleName = "game"  // Used in load.mjs file
        browser {

            /** Instructs webpack to open the website in Google Chrome */
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    open = mapOf(
                        "app" to mapOf(
                            "name" to "google-chrome",
                        )
                    )
                )
            }


        }
        binaries.executable()
    }
    sourceSets {
        ...
        val wasmJsMain by getting {
            dependencies {
                implementation(compose.material3)  // Place compose dependencies
            }
        }
        ...
    }
}

compose {
    experimental {
        web.application {}
    }
    kotlinCompilerPlugin.set("1.5.3")
}
```

Add the following line to your gradle.properties

###### gradle.properties

```
org.jetbrains.compose.experimental.jscanvas.enabled=true
```

#### Resources

Sync the project and head over to `src/wasmJsMain/resources` and prepare the following files. It is advisable to prepare
a loading screen as it may take a while to load the application.

###### index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    ...

    <script type="application/javascript" src="skiko.js"></script>
    <script src="wasmApp.js"></script>

    ...

</head>
<body>
...

<canvas id="ComposeTarget"></canvas>

...
</body>
```

###### load.mjs

This file is responsible for preparing the wasm environment. It also checks whether the environment supports wasm-gc

```javascript
import {instantiate} from './game.uninstantiated.mjs';  // "game" was obtained from build.gradle.kts

await wasmSetup;

let te = null;
try {
    await instantiate({skia: Module['asm']});
} catch (e) {
    te = e;
}

if (te == null) {

    // Wasm-GC has been initialized successfully

} else {

    // Problem instantiating Wasm-GC. Probably the user's browser does not support Wasm-GC

}
```

#### Webpack Configuration

Lastly, head to the module root and create a folder `webpack.config.d` (the folder should be in the same directory as
the `src` folder) and place the following files.

###### boilerplate.js

```javascript
config.entry = {
    main: [require('path').resolve(__dirname, "kotlin/load.mjs")]
};

config.resolve ?? (config.resolve = {});
config.resolve.alias ?? (config.resolve.alias = {});
config.resolve.alias.skia = false;

```

###### cleanupSourcemap.js

```javascript
// Replace paths unavailable during compilation with `null`, so they will not be shown in devtools
;
(() => {
    const fs = require("fs");
    const path = require("path");

    const outDir = __dirname + "/kotlin/"
    const projecName = path.basename(__dirname);
    const mapFile = outDir + projecName + ".map"

    const sourcemap = JSON.parse(fs.readFileSync(mapFile))
    const sources = sourcemap["sources"]
    srcLoop: for (let i in sources) {
        const srcFilePath = sources[i];
        if (srcFilePath == null) continue;

        const srcFileCandidates = [
            outDir + srcFilePath,
            outDir + srcFilePath.substring("../".length),
            outDir + "../" + srcFilePath,
        ];

        for (let srcFile of srcFileCandidates) {
            if (fs.existsSync(srcFile)) continue srcLoop;
        }

        sources[i] = null;
    }

    fs.writeFileSync(mapFile, JSON.stringify(sourcemap));
})();
```

#### Kotlin

Head over to `src/wasmJsMain/kotlin` and place the following file

###### main.wasm.kt

```kotlin
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Solitaire") { // "Solitaire" - The title of the webpage

        // Place your compose code here

        Text(
            text = "Greetings Wasm!!"
        )
    }
}
```

### Development Run

Find a task `wasmJsBrowserDevelopmentRun` in your module and run it. 
