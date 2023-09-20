// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
const vscode = require('vscode');

// This method is called when your extension is activated
// Your extension is activated the very first time the command is executed

/**
 * @param {vscode.ExtensionContext} context
 */
function activate(context) {

	// Use the console to output diagnostic information (console.log) and errors (console.error)
	// This line of code will only be executed once when your extension is activated
	console.log('Congratulations, your extension "Solitaire" is now active!');

	// The command has been defined in the package.json file
	// Now provide the implementation of the command with  registerCommand
	// The commandId parameter must match the command field in package.json
	let disposable = vscode.commands.registerCommand('solitaire.start', function () {
		// The code you place here will be executed every time your command is executed

		// Display a message box to the user
		vscode.window.showInformationMessage('Hello World from Solitaire!');

		const panel = vscode.window.createWebviewPanel(
			"solitaire-compose",
			"Solitaire Compose",
			vscode.ViewColumn.Active,
			{
				enableScripts: true
			}
		);

		let skikoOnDiskPath = vscode.Uri.joinPath(context.extensionUri, 'skiko.js');
		let kotlinOnDiskPath = vscode.Uri.joinPath(context.extensionUri, 'visualstudiocode.js');
		let loadingOnDiskPath = vscode.Uri.joinPath(context.extensionUri, 'loading.css');
		let animationOnDiskPath = vscode.Uri.joinPath(context.extensionUri, 'animation.css');

		let skikoSrc = panel.webview.asWebviewUri(skikoOnDiskPath);
		let kotlinCodeSrc = panel.webview.asWebviewUri(kotlinOnDiskPath);
		let commonSrc = panel.webview.asWebviewUri(context.extensionUri);
		let loadingSrc = panel.webview.asWebviewUri(loadingOnDiskPath);
		let animationSrc = panel.webview.asWebviewUri(animationOnDiskPath);

		// Set its HTML content
		panel.webview.html = getWebviewContent(
			skikoSrc,
			kotlinCodeSrc,
			loadingSrc,
			animationSrc
		);
	});

	context.subscriptions.push(disposable);
}

// This method is called when your extension is deactivated
function deactivate() {}

function getWebviewContent(
	skikoSrc,
	kotlinCodeSrc,
	loadingCssSrc,
	animationCssSrc
) {
	return `<!DOCTYPE html>
	<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Solitaire</title>
		<script src="${skikoSrc}"></script>
	
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Andika">
		<link id="loadingStyleSheet" rel="stylesheet" href="${loadingCssSrc}"/>
		<link rel="stylesheet" href="${animationCssSrc}"/>
	
	</head>
	<body>
	<canvas id="ComposeTarget"></canvas>
	<script src="${kotlinCodeSrc}"></script>
	
	<div class="loading">
		<h2>Solitaire</h2>
		<div class="instruction">
			<p class="welcome">Welcome to Solitaire VS Code Extension. The game will begin shortly</p>
			<div class = "animation">
				<div class="ring"></div>
			</div>
		</div>
		<h6>Developed using <a href="https://kotl.in/js">Kotlin/JS</a> and <a
				href="https://github.com/JetBrains/compose-multiplatform">Compose for Web</a></h6>
	</div>
	
	</body>
	</html>`;
}

module.exports = {
	activate,
	deactivate
}
