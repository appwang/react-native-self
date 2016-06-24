/**
 * Created by cousin on 16/6/6.
 */
'use strict';
import React, {
    AppRegistry,
    StyleSheet,
    View,
    Text,
    Platform
} from 'react-native';

import WScreen from './WScreen'

const WViewStyle = StyleSheet.create({
    BaseViewBackground: {
        flex: 1,
        backgroundColor: '#ebebf1',
    },
    NavigationBarView: {
        flex: 0,
        backgroundColor: '#c50000',
        justifyContent: 'flex-end',
        //flexDirection: 'row',
        height: Platform.OS === 'ios' ? 64 : 44
    },
    NavigationBarTitleView: {
        flex: 0,
        //alignSelf: 'center',
        marginBottom: 12,
        alignSelf: 'center'
    },
    TitleTextFont: {
        fontSize: 17,
        fontWeight: 'bold',
        color: '#FFFFFF'
    }
});

export default WViewStyle