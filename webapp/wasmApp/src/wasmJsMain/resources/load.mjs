import { instantiate } from './game.uninstantiated.mjs';

await wasmSetup;

let te = null;
try {
    await instantiate({ skia: Module['asm'] });
} catch (e) {
    te = e;
}

if (te == null) {
    document.getElementById("loading").style.display="none";
    document.getElementById("loading").style.visibility="hidden";
} else {
    throw te;
}
