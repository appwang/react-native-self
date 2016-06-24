/**
 * Created by cousin on 16/6/7.
 */

'use strict';
import React, {
    Component,
    PropTypes
} from 'react';
import {
    AppRegistry,
    View,
    Image,
    Text,
    StyleSheet,
    TextInput,
    PanResponder,
    NativeModules,
    Alert,
    ProgressBarAndroid,
    ActivityIndicatorIOS,
    Platform,
    StatusBar
} from 'react-native';

import WViewStyle from '../WStyle/WViewStyle'
import WScreen from './../WStyle/WScreen'
import WHomeVC from './../WHome/WHomeVC'

const WLoginStyle = StyleSheet.create({
    imageStyleView: {
        flex: 1,
        width: WScreen.getScreenSizeWidth()
    },
    loginBackgroundView: {
        top: 100,
        flex: 1
    },
    textFieldView: {
        flex: 0,
        flexDirection: 'row',
        backgroundColor: '#578392',
        marginLeft: 20,
        marginRight: 20,
        height: 50,
        borderRadius: 5,
        opacity: 0.6
    },
    textFieldLeft: {
        flex: 0,
        marginLeft: 10,
        marginTop: 15,
        width: 20,
        height: 20
    },
    textFieldLine: {
        marginTop: 10,
        height: 30,
        width: 0.5,
        backgroundColor: '#FFFFFF'
    },
    textFieldInputView: {
        flex : 1,
        height: 40,
        marginTop: 5,
        marginLeft: 8,
        color: '#FFFFFF',
        fontSize: 15
    },
    textFieldSecondView: {
        marginTop: 15
    },
    textFieldLeftConstraint: {
        resizeMode: 'contain'
    },
    buttonView: {
        marginTop : 40,
        marginLeft: 20,
        marginRight: 20,
        backgroundColor: '#c50000',
        height: 50,
        borderRadius: 5,
        opacity: 0.8
    },
    buttonTitleView: {
        color: '#FFFFFF',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center',
        letterSpacing: 5,
        marginLeft: 5,
        marginRight: 5,
        paddingTop: 15,
        flex: 1,
        textAlignVertical: 'center'
    },
    loginLoading: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    loginLoadingText: {
        marginTop: 10
    }
});


class WLoginVC extends Component {
    static defaultProps = {
        navTitle: "车商登录",
        loadingText: '登录中...'
    };
    static propTypes = {
        navTitle:React.PropTypes.string.isRequired,
        loadingText:React.PropTypes.string.isRequired
    };
    constructor(props) {
        super(props);
        this.state= {
            accountMobile: '',
            userPassword: '',
            loginErr: 0,
            isLogin: false
        };
        this.myPanResponder={}
    }
    componentWillMount() {
        this.myPanResponder = PanResponder.create({
            onStartShouldSetPanResponder:(evt,gestureState) => true,
            onStartShouldSetPanResponderCapture:(evt,gestureState) => false,
            onMoveShouldSetPanResponder:(evt,gestureState) => true,
            onMoveShouldSetPanResponderCapture:(evt,gestureState) => true,
            onPanResponderTerminationRequest:(evt,gestureState) =>true,

            onPanResponderGrant:(evt,gestureState) => {

            },
            onPanResponderMove:(evt,gestureState) => {

            },
            onPanResponderRelease:(evt,gestureState) => {
                this.hideKeyBoardFromCom();
            },
            onPanResponderTerminate:(evt,gestureState) => {

            }
        });
    }
    
    hideKeyBoardFromCom() {
        this.refs.accountTF.blur();
        this.refs.passwordTF.blur();
    }

    loginToHomeView() {
        if (this.state.accountMobile.length < 11) {
            Alert.alert('提示','请输入11位手机号',[{text:'知道了'}]);
            return;
        }
        this.setState({
            isLogin:true
        });
        let WNetAccess = NativeModules.WNetAccess;
        WNetAccess.loginWithUserName(this.state.accountMobile,this.state.userPassword,(error,res) => {
            this.setState({
                isLogin:false
            });
            if(error) {
                Alert.alert('提示',error,[{text: '知道了'}]);
            }else {
                const { navigator } = this.props;
                if(navigator) {
                    navigator.push({
                        name: 'WHomeVC',
                        component: WHomeVC,
                        params: {
                            navName: res['dealer']['name']
                        }
                    });
                }
            }
        });
    }

    renderToLogin() {
        if (this.state.isLogin) {
            return (
                <View style={WLoginStyle.loginLoading}>
                    {(Platform.OS === 'ios') ? <ActivityIndicatorIOS size="small" animating={true} color="white" /> : <ProgressBarAndroid styleAttr="SmallInverse" color='#3e9ce9' />}
                </View>
            );
        }else {
            return(
                <Text style={WLoginStyle.buttonTitleView} onPress={this.loginToHomeView.bind(this)}>
                    {'登录'}
                </Text>
            );
        }
    }

    render() {
        return (
            <View style={WViewStyle.BaseViewBackground}>
                <StatusBar animated={true} barStyle="light-content" backgroundColor="#c50000" />
                <View style={WViewStyle.NavigationBarView}>
                    <Text style={[WViewStyle.NavigationBarTitleView,WViewStyle.TitleTextFont]} allowFontScaling={true}>
                        {this.props.navTitle}
                    </Text>
                </View>
                <Image style={WLoginStyle.imageStyleView} source={require('./../../WImage/bgLogin.png')} {...this.myPanResponder.panHandlers}>
                    <View style={WLoginStyle.loginBackgroundView}>
                        <View style={WLoginStyle.textFieldView}>
                            <Image style={WLoginStyle.textFieldLeft} source={require('./../../WImage/iconUserId.png')} />
                            <View style={[WLoginStyle.textFieldLeft,WLoginStyle.textFieldLine]} />
                            <TextInput
                                style={WLoginStyle.textFieldInputView}
                                autoFocus={false}
                                keyboardType={'numeric'}
                                maxLength={11}
                                placeholder={'请输入账号'}
                                placeholderTextColor={'#cdcdcd'}
                                clearButtonMode={'while-editing'}
                                onChangeText={(text) => {this.state.accountMobile= text}}
                                ref="accountTF"
                                underlineColorAndroid={'transparent'}
                                textAlignVertical={'center'}
                            />
                        </View>
                        <View style={[WLoginStyle.textFieldView,WLoginStyle.textFieldSecondView]}>
                            <Image style={[WLoginStyle.textFieldLeft,WLoginStyle.textFieldLeftConstraint]} source={require('./../../WImage/iconPassword.png')} />
                            <View style={[WLoginStyle.textFieldLeft,WLoginStyle.textFieldLine]} />
                            <TextInput
                                style={WLoginStyle.textFieldInputView}
                                autoFocus={false}
                                keyboardType={'default'}
                                placeholder={'请输入密码'}
                                placeholderTextColor={'#cdcdcd'}
                                clearButtonMode={'while-editing'}
                                secureTextEntry={true}
                                ref="passwordTF"
                                onChangeText={(text) => {this.state.userPassword= text}}
                                underlineColorAndroid={'transparent'}
                                textAlignVertical={'center'}
                            />
                        </View>
                        <View style={WLoginStyle.buttonView}>
                            {this.renderToLogin()}
                        </View>
                    </View>
                </Image>
            </View>
        );
    }
}



// WLoginVC.defaultProps = {
//     navTitle: '车商登录'
// };
//
// WLoginVC.propTypes = {
//     navTitle:React.propTypes.string.isRequired
// };

export default WLoginVC