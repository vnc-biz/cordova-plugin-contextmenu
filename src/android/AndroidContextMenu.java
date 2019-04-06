package com.vnc.biz;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.widget.Toast;
import android.os.Handler;
import android.view.Window;
import android.view.ActionMode.Callback;
import android.view.ActionMode;
import android.view.WindowManager.LayoutParams;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.KeyEvent;
import android.app.Activity;

import android.webkit.ValueCallback;

public class AndroidContextMenu extends CordovaPlugin {
    private Window window;
    private Activity activity;

    private static Window.Callback windowCallback;

    private CallbackContext callback = null;
    private boolean dismissMenu;

    protected Window.Callback getWindowCallback() {
        if (windowCallback == null) {
            windowCallback = window.getCallback();
        }

        return windowCallback;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        activity = cordova.getActivity();
        window = activity.getWindow();

        if ("setCallback".equals(action)) {
            callback = callbackContext;
            initCallbacks();
            return true;
        }

        if ("setDismissMenu".equals(action)) {
            dismissMenu = args.getBoolean(0);
            return true;
        }

        return false;
    }

    private void initCallbacks() {
        final Window.Callback windowCallback = getWindowCallback();
        window.setCallback(new Window.Callback() {
            @Override
            public ActionMode onWindowStartingActionMode(Callback callback) {
                return windowCallback.onWindowStartingActionMode(callback);
            }

            @Override
            public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
                return windowCallback.onWindowStartingActionMode(callback, type);
            }

            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                windowCallback.onWindowFocusChanged(hasFocus);
            }

            @Override
            public void onWindowAttributesChanged(LayoutParams attrs) {
                windowCallback.onWindowAttributesChanged(attrs);
            }

            @Override
            public boolean onSearchRequested() {
                return windowCallback.onSearchRequested();
            }

            @Override
            public boolean onSearchRequested(SearchEvent searchEvent) {
                return windowCallback.onSearchRequested(searchEvent);
            }

            @Override
            public boolean onPreparePanel(int featureId, View view, Menu menu) {
                return windowCallback.onPreparePanel(featureId, view, menu);
            }

            @Override
            public void onPanelClosed(int featureId, Menu menu) {
                windowCallback.onPanelClosed(featureId, menu);
            }

            @Override
            public boolean onMenuOpened(int featureId, Menu menu) {
                return windowCallback.onMenuOpened(featureId, menu);
            }

            @Override
            public boolean onMenuItemSelected(int featureId, MenuItem item) {
                return windowCallback.onMenuItemSelected(featureId, item);
            }

            @Override
            public void onDetachedFromWindow() {
                System.exit(0);
                windowCallback.onDetachedFromWindow();
            }

            @Override
            public View onCreatePanelView(int featureId) {
                return windowCallback.onCreatePanelView(featureId);
            }

            @Override
            public boolean onCreatePanelMenu(int featureId, Menu menu) {
                return windowCallback.onCreatePanelMenu(featureId, menu);
            }

            @Override
            public void onContentChanged() {
                windowCallback.onContentChanged();
            }

            @Override
            public void onAttachedToWindow() {
                windowCallback.onAttachedToWindow();
            }

            @Override
            public void onActionModeStarted(ActionMode mode) {
              if (dismissMenu) {
                  if (mode != null) {
                      mode.finish();
                  }
                  if (webView != null) {
                      webView.getEngine().evaluateJavascript("window.getSelection().toString()", new ValueCallback<String>() {
                          @Override
                          public void onReceiveValue(String value) {
                              if (value != null) {
                                  if (callback != null) {
                                      try {
                                          JSONObject parameter = new JSONObject();
                                          parameter.put("selectedText", value);
                                          PluginResult result = new PluginResult(PluginResult.Status.OK, parameter);
                                          result.setKeepCallback(true);
                                          callback.sendPluginResult(result);
                                      } catch (JSONException e) {
                                          callback.error(e.getMessage());
                                      }
                                  }
                              }
                          }
                      });
                  }
              }
              windowCallback.onActionModeStarted(mode);
            }

            @Override
            public void onActionModeFinished(ActionMode mode) {
                windowCallback.onActionModeFinished(mode);
            }

            @Override
            public boolean dispatchTrackballEvent(MotionEvent event) {
                return windowCallback.dispatchTrackballEvent(event);
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent event) {
                return windowCallback.dispatchTouchEvent(event);
            }

            @Override
            public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
                return windowCallback.dispatchPopulateAccessibilityEvent(event);
            }

            @Override
            public boolean dispatchKeyShortcutEvent(KeyEvent event) {
                return windowCallback.dispatchKeyShortcutEvent(event);
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                return windowCallback.dispatchKeyEvent(event);
            }

            @Override
            public boolean dispatchGenericMotionEvent(MotionEvent event) {
                return windowCallback.dispatchGenericMotionEvent(event);
            }
        });
    }
}
