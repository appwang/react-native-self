/**
 * Created by cousin on 16/6/12.
 */
'use strict';
import React,{
    Component,
    PropTypes
} from 'react';
import {
    AppRegistry,
    View,
    ListView,
    Image,
    Text,
    StyleSheet,
    TouchableOpacity,
    Platform,
    Navigator
} from 'react-native';

import WViewStyle from '../WStyle/WViewStyle'
import WScreen from './../WStyle/WScreen'
import WListVC from './../WList/WListVC'

const IMAGE_URLS_HEAD = [
    require('./../../WImage/homeItemCarMine.png'),
    require('./../../WImage/homeItemCarUnion.png'),
    require('./../../WImage/homeItemCarAll.png'),
    require('./../../WImage/homeItemReport.png')
];

const TITLE_HEAD = [
    '本店车辆',
    '联盟车辆',
    '合伙人车辆',
    '报表'
];

const SELLER = [
    require('./../../WImage/homeItemClueAll.png'),
    require('./../../WImage/homeItemOrderAll.png'),
    require('./../../WImage/homeItemClueBuyAdd.png'),
    require('./../../WImage/homeItemQASale.png'),
    require('./../../WImage/homeItemInsurance.png'),
    require('./../../WImage/homeItemSubscription.png')
];

const SELLERCONTENT = [
    '全部买车线索',
    '全部订单',
    '新增买车线索',
    '质保销售',
    '保险销售',
    '求购信息'
];

const APPRAISERSSHI = [
    require('./../../WImage/homeItemSellClueAll.png'),
    require('./../../WImage/homeItemCarAdd.png'),
    require('./../../WImage/wDetection.png')
];

const APPRAISERSSHICONTENT = [
    '全部收车线索',
    '发布车辆',
    '质保检测'
];

const FINANCE = [
    require('./../../WImage/homeItemMoney.png')
];

const FINANCECONTENT = [
    '资金流水'
];

const COMMONTOOL = [
    require('./../../WImage/homeItemCalcLoan.png'),
    require('./../../WImage/homeItemHelpPos.png'),
    require('./../../WImage/homeItemHelp9.png')
];

const COMMONTOOLCONTENT = [
    '贷款计算器',
    'pos机指南',
    '九宫格教程'
];

const ROWCELL = [
    {0:'销售',1:SELLER,2:SELLERCONTENT},
    {0:'评估师',1:APPRAISERSSHI,2:APPRAISERSSHICONTENT},
    {0:'财务',1:FINANCE,2:FINANCECONTENT},
    {0:'常用工具',1:COMMONTOOL,2:COMMONTOOLCONTENT}
];

const WHomeStyle = StyleSheet.create({
    listBackView: {
        flex : 1
    },
    listHeadView: {
        flex: 1,
        backgroundColor: '#FFFFFF',
        flexDirection: 'row',
        flexWrap: 'wrap'
    },
    listHeadSubView: {
        justifyContent: 'center',
        alignItems: 'center',
        width: WScreen.getScreenSizeWidth() / 3.0,
        height: WScreen.getScreenSizeWidth() / 3.0,
        backgroundColor: '#FFFFFF',
        borderBottomWidth: 0.5,
        borderBottomColor: '#ebebf1',
        borderRightWidth: 0.5,
        borderRightColor: '#ebebf1',
        flex: 0
    },
    listHeadBottomView: {
        height: 10,
        width: WScreen.getScreenSizeWidth(),
        backgroundColor: '#ebebf1'
    },
    listHeadSubViewImage: {
        resizeMode: 'contain'
    },
    listHeadSubViewTitle: {
        fontSize: 15,
        marginTop: 20
    },
    listRowBackView: {
        flex: 1,
        backgroundColor: '#ebebf1'
    },
    listRowHeadView: {
        backgroundColor: '#FFFFFF',
        height: 40,
        flexDirection: 'row',
        borderBottomWidth: 1,
        borderBottomColor: '#ebebf1'
    },
    listRowHeadSubViewImage: {
        margin: 10
    },
    listRowHeadSubViewTitle: {
        paddingTop: 10,
        paddingBottom: 10,
        color: '#b90000'
    },
    listRowContentBackView: {
        backgroundColor: '#FFFFFF',
        width: WScreen.getScreenSizeWidth(),
        height: WScreen.getScreenSizeWidth() / 3.0 * 2,
        flexDirection: 'row',
        flexWrap: 'wrap'
    },
    listRowContentReduceBackView: {
        height: WScreen.getScreenSizeWidth() / 3.0
    },
    listRowContentBottomView: {
        flex: 0,
        width: WScreen.getScreenSizeWidth(),
        height: 10
    }
});

export default class WHomeVC extends Component {
    static defaultProps = {


    };
    static propTypes = {

    };
    constructor(props) {
        super(props);
        this.state = {
            navName : this.props.navName,
            dataSource: new ListView.DataSource({rowHasChanged:(r1,r2) => r1 !== r2}).cloneWithRows(ROWCELL),
            isFreshing: false
        }
    }
    componentWillMount() {
        
    }
    componentDidMount() {

    }

    renderHeadView() {
        let listsHead = [];
        for(let [index,elem] of IMAGE_URLS_HEAD.entries()) {
            let  titleName = TITLE_HEAD[index];
            console.log(titleName);
            listsHead.push(
                <TouchableOpacity onPress={this.onPress.bind(this)} key={index}>
                    <View style={WHomeStyle.listHeadSubView}>
                        <Image style={WHomeStyle.listHeadSubViewImage} source={elem}/>
                        <Text style={WHomeStyle.listHeadSubViewTitle}>
                            {titleName}
                        </Text>
                    </View>
                </TouchableOpacity>
            );
        }
        return (
            <View style={WHomeStyle.listHeadView}>
                {listsHead}
                <View style={WHomeStyle.listHeadBottomView} />
            </View>
        );
    }

    onPress() {
        const { navigator } = this.props;
        navigator.push({
            name: 'WListVC',
            component: WListVC
        });
    }

    renderRowCS(rowData,sectionID,RowID) {
        let lists = [];
        for(let [index,elem] of rowData[1].entries()) {
            console.log(elem);
            lists.push(
                <View style={WHomeStyle.listHeadSubView} key={index}>
                    <Image style={WHomeStyle.listHeadSubViewImage} source={elem} />
                    <Text style={WHomeStyle.listHeadSubViewTitle}>
                        {rowData[2][index]}
                    </Text>
                </View>
            );
        }
        if(lists.length > 3) {
            return (
                <View style={WHomeStyle.listBackView}>
                    <View style={WHomeStyle.listRowHeadView}>
                        <Image style={WHomeStyle.listRowHeadSubViewImage} source={require('./../../WImage/redHeader.png')} />
                        <Text style={WHomeStyle.listRowHeadSubViewTitle}>
                            {rowData[0]}
                        </Text>
                    </View>
                    <View style={WHomeStyle.listRowContentBackView}>
                        {lists}
                    </View>
                    <View style={WHomeStyle.listRowContentBottomView} />
                </View>
            );
        }else {
            return (
                <View style={WHomeStyle.listBackView}>
                    <View style={WHomeStyle.listRowHeadView}>
                        <Image style={WHomeStyle.listRowHeadSubViewImage} source={require('./../../WImage/redHeader.png')} />
                        <Text style={WHomeStyle.listRowHeadSubViewTitle}>
                            {rowData[0]}
                        </Text>
                    </View>
                    <View style={[WHomeStyle.listRowContentBackView,WHomeStyle.listRowContentReduceBackView]}>
                        {lists}
                    </View>
                    <View style={WHomeStyle.listRowContentBottomView} />
                </View>
            );
        }
    }
    // 在此处，使用整个加载试图在根布局上进行替换时，会造成ListView无法重新对顶部和底部的控件进行偏移,要给最顶层View增加style
    render() {
        return (
            <View style={WViewStyle.BaseViewBackground}>
                <View style={WViewStyle.NavigationBarView}>
                    <Text style={[WViewStyle.NavigationBarTitleView,WViewStyle.TitleTextFont]} allowFontScaling={true}>
                        {this.state.navName}
                    </Text>
                </View>
                <ListView
                    style={WHomeStyle.listBackView}
                    dataSource= {this.state.dataSource}
                    renderRow= {this.renderRowCS.bind(this)}
                    renderHeader={this.renderHeadView.bind(this)}
                />
            </View>
        );
    }
}