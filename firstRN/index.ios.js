/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
'use strict';
import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Navigator,
  View,
  StatusBar
} from 'react-native';

import WLoginVC from './WModules/WLogin/WLoginVC'
import WViewStyle from './WModules/WStyle/WViewStyle'
//import WListVC from './WModules/WList/WListVC'


class firstRN extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    let defaultName = 'WLoginVC';
    let defaultComponent = WLoginVC;
    return (
      <View style={WViewStyle.BaseViewBackground}>
        <Navigator
            initialRoute={{name: defaultName, component: defaultComponent}}
            configureScene={(route) => {
          return Navigator.SceneConfigs.PushFromRight;
        }}
            renderScene={(route, navigator) => {
          let Component = route.component;
          return <Component {...route.params} navigator={navigator} />
        }}
        />
      </View>
    );
  }
}

AppRegistry.registerComponent('firstRN', () => firstRN);
