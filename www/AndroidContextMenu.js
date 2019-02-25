var exec = cordova.require('cordova/exec');

var AndroidContextMenu = function() {
  console.log('AndroidContextMenu instanced');
};

AndroidContextMenu.prototype.setCallback = function(onChanged) {
  exec(onChanged, function(err) {
    console.log(err);
  }, 'AndroidContextMenu', 'setCallback', []);
};


AndroidContextMenu.prototype.setDismissMenu = function(dismiss) {
  var errorCallback = function(obj) {
      onError(obj);
  };

  var successCallback = function(obj) {
      onSuccess(obj);
  };

  exec(successCallback, errorCallback, 'AndroidContextMenu', 'setDismissMenu', [dismiss]);
};

if (typeof module != 'undefined' && module.exports) {
  module.exports = AndroidContextMenu;
}
