var exec = cordova.require('cordova/exec');

var AndroidContextMenu = function() {
  console.log('AndroidContextMenu instanced');
};

AndroidContextMenu.prototype.setCallback = function(onChanged) {
  exec(onChanged, function(err) {
    console.log(err);
  }, 'AndroidContextMenu', 'setCallback', []);
};

if (typeof module != 'undefined' && module.exports) {
  module.exports = AndroidContextMenu;
}
