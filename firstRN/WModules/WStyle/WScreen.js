/**
 * Created by cousin on 16/6/8.
 */

import React, {
    AppRegistry,
    Platform
} from 'react-native';

export default class WScreen {
    static getScreenSizeHeight(): number {
        return (Platform.OS === 'ios') ? require('Dimensions').get('window').height : require('Dimensions').get('window').height - 20;
    }

    static getScreenSizeWidth(): number {
        return require('Dimensions').get('window').width;
    }
}

