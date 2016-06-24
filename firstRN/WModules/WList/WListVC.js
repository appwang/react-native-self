/**
 * Created by cousin on 16/6/7.
 */

'use strict';
import React,{
    Component
} from 'react';
import {
    AppRegistry,
    View,
    Image,
    StyleSheet,
    Text,
    TouchableOpacity,
    RefreshControl,
    ListView,
    NativeModules
} from 'react-native';

import WViewStyle from './../WStyle/WViewStyle'
import WListAnimation from './WListAnimation'

const WListStyle = StyleSheet.create({
    listNavigatorView: {
        flexDirection: 'row',
        flex: 0,
        backgroundColor: '#c50000',
        height: 64,
        justifyContent: 'space-between'
    },
    listNavigatorLeftView: {
        marginLeft: 10,
        marginBottom: 12,
        alignSelf: 'flex-end',
        flexDirection: 'row'
    },
    listNavigatorLeftImageView: {
        resizeMode: 'contain'
    },
    listNavigatorCenterTitleView: {
        alignSelf: 'flex-end'
    },
    listNavigatorRightView: {
        marginRight: 10,
        marginBottom: 12,
        alignSelf: 'flex-end',
        width: 10
    },
    listNavigatorLeftTitleSubView: {
        marginTop: 2,
        marginLeft: 3
    },
    indicatorWrapper: {
        height: 45,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#FFFFFF'
    },
    listRowCellBackView: {
        margin: 5,
        flexDirection: 'row'
    },
    listRowView: {
        backgroundColor: '#FFFFFF'
    },
    renderSeparatorView: {
        height: 1,
        backgroundColor: '#d4d5d6'
    },
    listRowRightView: {
        justifyContent: 'space-between',
        marginLeft: 5,
        marginRight: 5,
        flex: 1
    },
    listRowRightTitleHeadSubView: {
        fontSize: 14
    },
    listRowRightTitleMiddleView: {
        flexDirection: 'row'
    },
    listRowRightTitleMiddleSubView: {
        fontSize: 12,
        color: '#999999'
    },
    listRowRightTitleMiddleSpaceSubView: {
        marginLeft: 5
    },
    listRowRightBottomSubView: {
        justifyContent: 'space-between',
        flexDirection:'row'
    },
    listRowRightBottomLeftSubView: {
        fontSize: 20,
        color: '#c50000'
    },
    listRowRightBottomMiddleSubView:{

    },
    listRowRightBottomRightSubView: {
        flexDirection: 'row',
        justifyContent: 'flex-end',
        alignItems: 'center'
    },
    listRowRightDownSubView: {
        resizeMode: 'contain',
        marginLeft: 2
    },
    listRowLeftImageSubView: {
        height: 80,
        width:100
    }
});

class WListVC extends Component {
    constructor(props) {
        super(props);
        this.refreshPage = 1;
        this.dataArray= [];
        this.state = {
            dataSource: new ListView.DataSource({rowHasChanged:(r1,r2) => r1 !== r2}),
            loadMore: false,
            isRefreshing: false
        }
    }
    componentWillMount() {

    }
    popToHomeView() {
        const { navigator } = this.props;
        navigator.pop();
    }
    componentDidMount() {
        this.setState({
            isRefreshing: true
        });
        this.requestToCar(this.refreshPage.toString());
    }
    requestToCar(page) {
        let WNetAccess = NativeModules.WNetAccess;
        WNetAccess.requestWithSearchCarWithPage(page,(error,res) => {
            if(error) {
                Alert.alert('提示',error,[{text: '知道了'}]);
                this.setState({
                    loadMore: false,
                    isRefreshing: false
                });
            }else {
                for (let [,elem] of res['data'].entries()) {
                    this.dataArray.push(elem);
                }
                this.setState({
                    dataSource:this.state.dataSource.cloneWithRows(this.dataArray),
                    loadMore: false,
                    isRefreshing: false
                });
            }
        });
    }

    loadToMoreCar() {
        if (this.state.loadMore || this.state.isRefreshing) {
            return;
        }
        this.setState({
           loadMore: true
        });
        this.refreshPage++;
        this.requestToCar(this.refreshPage.toString());
    }

    pullToRequestCar() {
        if (this.state.isRefreshing) {
            return;
        }
        this.setState({
            isRefreshing: true
        });
        this.refreshPage= 0;
        this.dataArray.splice(0,this.dataArray.length);
        this.requestToCar(this.refreshPage.toString());
    }

    renderListFootView() {
        return (
            this.state.loadMore ? (<View style={WListStyle.indicatorWrapper}>
                <WListAnimation timingLength={50} duration={500} bodyColor={'#aaaaaa'} />
            </View>) : null
        );
    }
    
    renderListRow(RowData,sectionID,rowID) {
        let symbolList = [];
        if (Number.parseInt(RowData['car']['car_certification']) === 1) {
            symbolList.push(<Image source={require('./../../WImage/iconCarRz1.png')} style={WListStyle.listRowRightDownSubView} key={'carRZ'} />);
        }
        if (Number.parseInt(RowData['car']['car_source_user_type']) === 4) {
            symbolList.push(<Image source={require('./../../WImage/iconCarPerson1.png')} style={WListStyle.listRowRightDownSubView} key={'carPerson'} />);
        }
        if (!Number.parseInt(RowData['car']['factory_qa_range_type'])) {
            symbolList.push(<Image source={require('./../../WImage/iconCarZb.png')} style={WListStyle.listRowRightDownSubView} key={'carZB'} />)
        }
        let timeList = [];
        timeList.push(
            <Text style={WListStyle.listRowRightTitleMiddleSubView} key={'年'}>
                {RowData['car']['model_year'] + '年'}
            </Text>
        );
        timeList.push(
            <Text style={[WListStyle.listRowRightTitleMiddleSubView,WListStyle.listRowRightTitleMiddleSpaceSubView]} key={'公里'}>
                {(Number.parseFloat(RowData['car']['km_num'])/10000).toString().substring(0,4) + '万公里'}
            </Text>
        );
        timeList.push(
            <Text style={[WListStyle.listRowRightTitleMiddleSubView,WListStyle.listRowRightTitleMiddleSpaceSubView]} key={'省份'}>
                {RowData['car']['reg_area_cname']}
            </Text>
        );
        return (
            <View style={WListStyle.listRowCellBackView}>
                <Image style={[WListStyle.listRowRightDownSubView,WListStyle.listRowLeftImageSubView]} source={{uri: RowData['car']['logo']}} />
                <View style={WListStyle.listRowRightView}>
                    <Text style={WListStyle.listRowRightTitleHeadSubView} numberOfLines={2}>
                        {RowData['car']['name']}
                    </Text>
                    <View style={WListStyle.listRowRightTitleMiddleView}>
                        {timeList}
                    </View>
                    <View style={WListStyle.listRowRightBottomSubView}>
                        <Text style={WListStyle.listRowRightBottomLeftSubView}>
                            {(Number.parseFloat(RowData['car']['seller_price'])/10000).toString() + '万'}
                        </Text>
                        <View style={WListStyle.listRowRightBottomMiddleSubView}/>
                        <View style={WListStyle.listRowRightBottomRightSubView}>
                            {symbolList}
                        </View>
                    </View>
                </View>
            </View>
        );
    }
    
    render() {
        return (
            <View style={WViewStyle.BaseViewBackground}>
                <View style={WListStyle.listNavigatorView}>
                    <TouchableOpacity onPress={this.popToHomeView.bind(this)} style={WListStyle.listNavigatorLeftView}>
                        <Image source={require('./../../WImage/iconBack.png')} style={WListStyle.listNavigatorLeftImageViewView} />
                        <Text style={[WViewStyle.TitleTextFont,WListStyle.listNavigatorLeftTitleSubView]}>
                            返回
                        </Text>
                    </TouchableOpacity>
                    <Text style={[WViewStyle.NavigationBarTitleView,WViewStyle.TitleTextFont,WListStyle.listNavigatorCenterTitleView]}>
                        合伙人车源
                    </Text>
                    <View style={WListStyle.listNavigatorRightView} />
                </View>
                <ListView
                    style={WListStyle.listRowView}
                    dataSource={this.state.dataSource}
                    renderRow={this.renderListRow.bind(this)}
                    onEndReached={this.loadToMoreCar.bind(this)}
                    renderFooter={this.renderListFootView.bind(this)}
                    onEndReachedThreshold = {-80}
                    scrollRenderAheadDistance={300}
                    initialListSize={10}
                    renderSeparator={(rowID,sectionID) => <View key={`${sectionID}-${rowID}`} style={WListStyle.renderSeparatorView} />}
                    refreshControl={
                        <RefreshControl
                           refreshing={this.state.isRefreshing}
                           onRefresh={this.pullToRequestCar.bind(this)}
                           tintColor="#aaaaaa"
                           title="拼命加载中..."
                           progressBackgroundColor="#aaaaaa"
                         />
                    }/>
            </View>
        );
    }
}

export default WListVC