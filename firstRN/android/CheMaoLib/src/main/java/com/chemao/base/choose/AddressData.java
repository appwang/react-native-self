package com.chemao.base.choose;
/**
 * @File : AddressData.java
 * @ClassName : AddressData
 * @Description : 本地设置省市信息
 * @Author : tanhuohui
 * @date : 2015-01-06
 *
 */
public class AddressData {
		/**省份信息*/
		public static final String PROVINCES[] = {"北京","天津","上海","重庆","河北","河南","黑龙江","吉林","辽宁","山东","内蒙古","江苏","安徽","山西","陕西","甘肃","浙江","江西","湖北","湖南","贵州","四川","云南","新疆","宁夏","青海","西藏","广西","广东","福建","海南","台湾","香港","澳门"};
		/**省份编号数组*/
		public static final int P_ID[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34};
		/**城市信息*/
		public static final String CITIES[][] = {
	        {"北京"},
	        {"天津"},
	        {"上海"},
	        {"重庆"},
	        {"保定","馆陶", "定州", "承德" , "沧州", "秦皇岛", "廊坊", "衡水", "赵县", "邯郸","张北", "石家庄","张家口", "正定","唐山", "邢台"},
	        {"安阳", "济源", "焦作","鹤壁", "长葛", "明港", "信阳", "漯河", "禹州", "洛阳", "新乡", "开封", "许昌", "鄢陵", "南阳", "郑州", "周口", "商丘", "三门峡", "平顶山", "驻马店", "濮阳"},
	        {"大庆", "黑河", "鹤岗", "哈尔滨", "大兴安岭", "齐齐哈尔", "牡丹江", "鸡西", "佳木斯", "七台河", "伊春", "绥化", "双鸭山"},
	        {"白城", "辽源", "吉林", "长春", "白山", "延边", "通化", "四平", "松原"},
	        {"鞍山","丹东", "大连","朝阳", "本溪", "锦州", "葫芦岛", "阜新", "营口", "抚顺", "辽阳", "铁岭", "瓦房店", "盘锦", "沈阳"},
	        {"滨州", "济南", "菏泽", "东营", "德州","聊城", "淄博", "临沂","枣庄", "垦利", "威海", "济宁", "烟台", "章丘", "莱芜", "诸城", "泰安", "潍坊", "青岛","日照"},
	        {"阿拉善盟", "鄂尔多斯", "赤峰", "巴彦淖尔盟", "包头", "通辽", "海拉尔", "呼伦贝尔", "呼和浩特", "乌海", "兴安盟", "乌兰察布", "锡林郭勒盟"},
	        {"常州", "南京", "连云港", "淮安", "大丰", "宿迁", "沭阳", "苏州", "镇江", "南通", "泰州", "扬州","盐城", "无锡", "徐州"},
	        {"安庆", "池州", "巢湖", "毫州", "蚌埠", "淮南", "铜陵", "合肥", "桐城", "阜阳", "马鞍山", "滁州", "宿州", "芜湖", "淮北", "宣城", "霍邱", "六安", "黄山", "和县"},
	        {"长治", "吕梁", "晋中","晋城", "大同", "朔州", "清徐", "临猗", "临汾", "太原", "阳泉", "沂州", "运城"},
	        {"安康", "铜川", "商洛", "汉中", "宝鸡", "延安", "咸阳", "西安", "渭南", "榆林"},
	        {"白银", "嘉峪关", "金昌", "甘南", "定西", "陇南", "临夏", "兰州","酒泉", "平凉", "武威", "张掖", "庆阳", "天水"},
	        {"杭州", "丽水", "金华", "嘉兴", "湖州", "绍兴", "瑞安", "衢州", "宁波","台州", "乐清", "舟山", "温州", "义乌"},
	        {"抚州","景德镇", "吉安", "九江", "宜春", "赣州", "永新", "新余", "上饶", "萍乡", "南昌","鹰潭"},
	        {"恩施", "荆州", "黄冈", "黄石", "鄂州", "神农架", "宜昌", "十堰", "潜江", "仙桃", "荆门", "咸宁", "随州", "襄阳", "孝感", "天门", "武汉"},
	        {"长沙", "怀化", "衡阳", "郴州", "常德", "湘西", "湘潭", "邵阳", "娄底","岳阳", "株洲", "张家界", "永州", "益阳"},
	        {"安顺", "黔东南", "六盘水", "贵阳", "毕节", "遵义", "铜仁", "黔南西", "黔南"},
	        {"阿坝", "达州", "德阳", "成都", "巴中", "乐山", "遂宁", "甘孜", "宜宾", "广元", "南充", "广安", "攀枝花", "雅安", "泸州", "自贡", "资阳", "眉山", "内江", "凉山", "绵阳"},
	        {"保山", "德宏", "迪庆", "大理", "楚雄", "临沧", "丽江","昆明", "玉溪", "红河", "邵通", "怒江", "文山", "西双版纳","普洱", "曲靖"},
	        {"阿克苏", "昌吉","博尔塔拉", "巴音郭楞", "阿拉尔", "库尔勒", "伊犁", "克拉玛依", "阿勒泰", "和田", "乌鲁木齐", "哈密", "五家渠", "塔城", "喀什", "吐鲁番", "图木舒克", "克孜勒苏", "石河子"},
	        {"固原", "中卫", "银川", "吴忠", "石嘴山"},
	        {"果洛", "海北","海东", "海西", "黄南", "玉树", "西宁", "海南"},
	        {"阿里", "那曲", "林芝", "拉萨", "昌都", "山南", "日喀则"},
	        {"百色", "桂林", "防城港", "崇左", "北海", "柳州", "贺州", "河池", "贵港", "来宾", "梧州", "玉林", "南宁", "钦州"},
	        {"潮州", "惠州", "广州", "佛山", "东莞", "茂名", "台山", "揭阳", "阳江", "江门", "韶关", "河源", "汕尾", "阳春", "梅州", "云浮", "珠海", "深圳", "肇庆", "汕头", "清远", "中山", "顺德", "湛江"},
	        {"福州", "莆田", "南平", "宁德", "龙岩", "厦门", "武夷山", "三明", "泉州", "漳州"},
	        {"海口", "五指山", "三亚", "三沙"},
	        {"台湾"},
	        {"香港"},
	        {"澳门"},
	    };
		/**城市编号数组*/
		public static final int[][] C_ID = {
	        {36},
	        {37},
	        {38},
	        {39},
	        {40, 44, 43, 42, 41,48, 47, 46,54, 45, 55, 49, 52, 53, 50, 51},
	        {56, 60, 59, 58, 57, 64, 72, 63, 73, 62, 70, 61, 71, 74, 65, 75,76, 68,69, 66, 77, 67},
	        {78, 82,81, 80, 79, 86, 85, 84, 83, 87, 90, 88, 89},
	        {91, 95, 94,93, 92, 99, 98, 97, 96},
	        {100, 104, 103, 102, 101, 108, 107, 106, 114, 105, 109, 112, 113, 110,111},
	        {115, 119, 118, 117, 116, 123, 131, 122,132, 121, 129, 120, 130, 133, 124, 134,127, 128, 125,126},
	        {135, 139, 138, 137,136, 143, 142, 141, 140, 144,147, 145, 146},
	        {148, 152, 151, 150, 149, 156, 155, 154, 162, 153, 157, 160, 161, 158, 159},
	        {163,167, 166, 165, 164, 171, 179, 170, 180, 169, 177, 168, 178,181, 172, 182, 175, 176, 173,174},
	        {183,187, 186, 185,184, 191, 190, 189, 188,192, 195, 193, 194},
	        {196, 200, 199, 198, 197, 204, 203, 202, 201, 205 },
	        {206, 210, 209, 208, 207, 214, 213, 212, 211, 215, 218,219, 216, 217},
	        {220, 224, 223, 222, 221, 228, 227,226, 225, 229,232, 233, 230, 231},
	        {234, 238, 237, 236, 244, 235, 245, 242, 241, 240,239, 243},
	        {246, 250, 249, 248, 247, 254, 262, 253, 252, 260, 251, 261,255, 258, 259, 256, 257},
	        {263, 267, 266, 265, 264,271, 270, 269, 268, 272, 275,276, 273, 274},
	        {277, 281, 280, 279, 278, 285,284, 283, 282},
	        {286, 290, 289, 288, 287, 294, 302, 293, 303, 292, 300, 291, 301, 304, 295, 305, 306, 298, 299, 296, 297},
	        {307,311, 310, 309, 308, 315, 314, 313,321, 312, 322, 316, 319, 320,317, 318},
	        {323, 327, 326, 325, 324, 331, 339, 330, 416,329, 337, 328, 338, 417, 332, 335, 336, 333, 334},
	        {340,344, 343, 342, 341},
	        {345,349, 348, 347, 346, 352, 351,350},
	        {353, 357, 356, 355,354, 359, 358},
	        {360, 364, 363, 362, 361, 368, 367, 366, 365, 369, 372, 373,370, 371},
	        {374, 378, 377, 376, 375,382, 390, 381, 391,380, 388, 379, 389, 392, 383, 393, 394, 386, 397,387, 384, 395,385, 396},
	        {398, 402, 401,400, 399, 406, 405, 404, 403, 407},
	        {408, 411, 410, 409},
	        {412},
	        {413},
	        {414},
	    };
		
}