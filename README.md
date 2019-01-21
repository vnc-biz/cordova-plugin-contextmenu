# Overview

Cordova plugin that hides standard copy/past menu and gives users an event to handle it and show custom copy/past menu

# Connect plugin 

```
<plugin name="cordova-plugin-contextmenu" spec="https://github.com/vnc-biz/cordova-plugin-contextmenu"/>
```  

# Usage example

Add the following code inside `onDeviceReady`:

```
var androidContextMenu = new AndroidContextMenu();
androidContextMenu.setCallback(function(text) {
  console.log(text.selectedText);
});
```
