//
//  CMApiUtils.h
//  cheshang
//
//  Created by zrh on 16/5/11.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
@class CMResponse;

// 如果没有定义测试宏，则定义一份
#ifndef CM_Deprecated
#define CM_Deprecated(tip) __attribute__((deprecated(tip)))
#endif

#ifndef CMDebugHttp
#define CMDebugHttp 0
#endif

#ifndef CMSTRNetworkBad
#define CMSTRNetworkBad @"网络不给力啊"
#endif

#ifndef CMSTRNetworkNo
#define CMSTRNetworkNo @"当前无网络唉"
#endif

// 车猫环境
typedef NS_ENUM(NSInteger, CMEnvironment) {
    CMEnvironmentRelease = 0,   // 线上环境
    CMEnvironmentPreRelease,    // 预上线环境
    CMEnvironmentTest,          // 测试环境
    CMEnvironmentDevelopment,   // 开发环境
    CMEnvironmentCustom = -1,   // 自定义环境（警告：开发人员调试专用，不能乱用）
};

NS_ENUM(NSInteger){
    CMEnvironmentIsTest = ~1,       // & 测试模式判断
    CMEnvironmentIsSdkTest = ~0,        // & SDK测试模式判断
};


typedef NS_ENUM(NSInteger, CMAppType) {
    CMAppTypeDefault = 0,
    CMAppTypeChemao,
    CMAppTypeCheshang,
    CMAppTypeAdviser,
    CMAppTypeRz,
    CMAppTypeTrade = 10,
};

typedef NS_ENUM(NSInteger, CMCachePolicy) {
    CMCachePolicyDefault = 0,           // 无缓存机制
    CMCachePolicyCacheFirst = 1,        // 缓存优先
    CMCachePolicyCacheAndRequest = 2,   // 缓存先返回，再请求更新
};


UIKIT_EXTERN NSString *const kCMErrorUserInfoKeyInfo;

typedef void(^CMCompletion)(NSError *error, NSDictionary *responseJson);
typedef void(^CMFapisCompletion)(NSError *error, CMResponse *response);

typedef void(^CMRequsetCompletion)(NSError *error, id response);

typedef void(^SuccessBlock)(NSDictionary* responseObject) CM_Deprecated("unused");
typedef void(^FailureBlock)(NSError *error) CM_Deprecated("unused");

@protocol CMApiUtilsDelegate <NSObject>

@required
- (NSDictionary *)cmApiCheckParams:(NSDictionary *)params;
- (void)cmApiLogout:(CMResponse *)reaponse;

@end


/**
 *  CMAPI扩展
 */
@interface CMApiUtils : NSObject

@property (weak, nonatomic) id<CMApiUtilsDelegate> delegate;

+ (instancetype)shareIntance;

#pragma mark - api

+ (void)setApiEnvironment:(CMEnvironment)type;
+ (CMEnvironment)apiEnviroment;

+ (NSDictionary *)checkParams:(NSDictionary *)params;


/**
 *  接口地址拼接
 *
 *  @param subUrl 子链接
 *
 *  @return NSString of url
 */
+ (NSString *)appendingApiBaseUrl:(NSString *)subUrl;
+ (NSString *)appendingApiFapiUrl:(NSString *)subUrl;
+ (NSString *)appendingApiFDDSUrl:(NSString *)subUrl;

+ (NSString *)apiBaseUrl;     // 旧版接口地址
+ (NSString *)apiFapiUrl;     // Fapis接口地址
+ (NSString *)apiFDDSUrl;     // FDDS接口地址


#pragma mark - other

//组装checksum
+ (NSString *)getChecksumWithClass:(NSString *)nameClass
                           package:(NSString *)namePackage
                        keyPackage:(NSString *)keyPackage
                              time:(NSString *)time;

+ (NSString *)getChecksum:(NSDictionary *)parm time:(NSString *)time;

//获取当前时间的秒数
+ (NSString *)getUtcTmestemp;

//获取User-Agent信息
+ (NSString *)getUserAgentInfo;

//随机数（比如随机3位或8位）
+ (NSString *)getRandomChars:(NSInteger)count;

//sha1加密
+ (NSString *)sha1:(NSString *)str;

//获取MD5加密后字符串
+ (NSString *)md5FromString:(NSString *)str;

// 设备型号 iPhone8,1
+ (NSString *)models;
// 设备型号 iPhone 6s
+ (NSString *)modelReadable;


@end

CG_INLINE NSString *CMErrorString(NSError *error) {
    NSString *string = nil;
    if ([error.domain isEqualToString:NSURLErrorDomain]) {
        switch (error.code) {
            case NSURLErrorUnknown: return CMSTRNetworkBad;
            case NSURLErrorNotConnectedToInternet:  return CMSTRNetworkNo;  // 无法连接到网络
            case NSURLErrorZeroByteResource:        return @"没有返回数据";   // 没有数据
#if defined(DEBUG)||defined(_DEBUG)
#if defined(CMSdkEnvironment)&& (CMSdkEnvironment>1)
            case NSURLErrorCannotDecodeRawData:        return @"数据错误，无法解析（是否开启debug模式）";   // 不能解码原始数据
            case NSURLErrorCannotDecodeContentData:        return @"数据格式不对（是否是非字典数据）";   // 不能解码内容数据
            case NSURLErrorCannotParseResponse:        return @"响应消息错误，无法解析";   // 无法解析响应
            case NSURLErrorDataLengthExceedsMaximum:        return @"数据包长度过长";   // 数据长度过大
            case NSURLErrorDataNotAllowed:  return @"数据错误，无法提交";        // 错误数据不允许
#endif
#endif
            default:{
                @try {
                    string = error.userInfo[kCMErrorUserInfoKeyInfo];
                }
                @catch (NSException *exception) {
                }
                break;
            }
        }
    }
    if (!string) {
        string = CMSTRNetworkBad;
    }
    return string;
}

