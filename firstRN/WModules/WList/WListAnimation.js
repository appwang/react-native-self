/**
 * Created by cousin on 16/6/14.
 */

'use strict';
import React,{
    Component,
    PropTypes
} from 'react';

import {
    Animated,
    View,
    AppRegistry
} from 'react-native';


export default class WListAnimation extends Component {
    static defaultProps = {
        timingLength: 50,
        duration: 500,
        bodyColor: 'white',
        radius: 5
    };
    static propTypes = {
        timingLength:React.PropTypes.number,
        duration:React.PropTypes.number,
        bodyColor:React.PropTypes.string,
        radius:React.PropTypes.number
    };
    constructor(props) {
        super(props);
        this.state = {
            animatedValue: new Animated.Value(0)
        }
    }
    
    componentDidMount() {
        this.createAnimation(this);
    }
    
    createAnimation(that) {
        Animated.timing(
            this.state.animatedValue,
            {
                toValue: 1,
                duration: 500
            }).start(() => {
            Animated.timing(this.state.animatedValue,{
                toValue: 0,
                duration: 500
            }).start(() => {
                that.createAnimation(that)
            })
        })
    }

    render () {
        return (
            <Animated.View
                style={{
            width: this.props.radius * 2,
            height: this.props.radius * 2,
            borderRadius: this.props.radius,
            backgroundColor: this.props.bodyColor,
            transform: [{
              translateX: this.state.animatedValue.interpolate({
                inputRange: [0, 1],
                outputRange: [-this.props.timingLength / 2, this.props.timingLength / 2]
              })}
            ]
          }}/>
        )
    }
}