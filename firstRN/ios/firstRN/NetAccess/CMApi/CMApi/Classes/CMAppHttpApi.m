//
//  CMAppHttpApi.m
//  cheshang
//
//  Created by zrh on 16/3/25.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import "CMAppHttpApi.h"
#import "CMHttpApi.h"


// 测试
static NSString *const keyPackageChemaoT = @"fc96f865f95f381ae265185c370b4177";
static NSString *const keyPackageCheshangT = @"3bd679036267a732750921fe1904e003";
static NSString *const keyPackageTradeT = @"d654c277ed1f325243dcec6e655364a6";


//// 线上
static NSString *const keyPackageChemao = @"f9d670fab96694f6998f1ca7505acc0d";
static NSString *const keyPackageCheshang = @"c1932ae2300209ecc6ac23eacc9d7b1b";
static NSString *const keyPackageTrade = @"f5f07baf6ce02d8aba77276710257e2a";


@interface CMAppHttpApi ()

@property (strong, nonatomic, readonly) NSString *keyPackageChemao;
@property (strong, nonatomic, readonly) NSString *keyPackageCheshang;
@property (strong, nonatomic, readonly) NSString *keyPackageTrade;

@end


@implementation CMAppHttpApi


+ (instancetype)shareIntance{
    static CMAppHttpApi *_shareIntance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _shareIntance = [[CMAppHttpApi alloc] init];
        // 初始化key
        [_shareIntance toInitKeyPackage];
    });
    return _shareIntance;
}

- (void)toInitKeyPackage{
    CMEnvironment type = [CMApiUtils apiEnviroment];
    if (type & CMEnvironmentIsTest) {
        _keyPackageChemao = keyPackageChemaoT;
        _keyPackageCheshang = keyPackageCheshangT;
        _keyPackageTrade = keyPackageTradeT;
    }
    else{
        _keyPackageChemao = keyPackageChemao;
        _keyPackageCheshang = keyPackageCheshang;
        _keyPackageTrade = keyPackageTrade;
    }
}



+ (NSString *)keyPackageChemao{
    return [CMAppHttpApi shareIntance].keyPackageChemao;
}

+ (NSString *)keyPackageCheshang{
    return [CMAppHttpApi shareIntance].keyPackageCheshang;
}

+ (NSString *)keyPackageTrade{
    return [CMAppHttpApi shareIntance].keyPackageTrade;
}

+ (NSDictionary *)checkParams:(NSDictionary *)params{
    return [CMApiUtils checkParams:params];
}

/**
 *  私有api 需要包签名
 */
+ (void)fapisPrivateApiWithClass:(NSString *)className
                         package:(NSString *)package
                      keyPackage:(NSString *)keyPackage
                         appType:(CMAppType)appType
                          params:(NSDictionary *)params
                  showHUDAddedTo:(UIView *)view
                      completion:(CMFapisCompletion)completion{
    NSAssert(className.length, @"class is nil");
    NSAssert(package.length, @"package is nil");
    NSDictionary *dict = [self checkParams:params];
    [CMHttpApi fapisWithClass:className package:package keyPackage:keyPackage appType:appType parameters:dict showHUDAddedTo:view completion:completion];

}

+ (void)fapisPrivateApiWithClass:(NSString *)className
                         package:(NSString *)package
                         appType:(CMAppType)appType
                          params:(NSDictionary *)params
                  showHUDAddedTo:(UIView *)view
                      completion:(CMFapisCompletion)completion{
    NSString *keyPackage = nil;
    switch (appType) {
        case CMAppTypeChemao: {
            keyPackage = [self keyPackageChemao];
            break;
        }
        case CMAppTypeAdviser: {
        }
        case CMAppTypeCheshang: {
            keyPackage = [self keyPackageCheshang];
            break;
        }
        case CMAppTypeTrade:{
            keyPackage = [self keyPackageTrade];
            break;
        }
        default: break;
    }
    [self fapisPrivateApiWithClass:className package:package keyPackage:keyPackage appType:appType params:params showHUDAddedTo:view completion:completion];
}

/**
 *  公共api，仅对数据签名
 */
+ (void)fapisPublicApiWithClass:(NSString *)className
                        package:(NSString *)package
                        appType:(CMAppType)appType
                         params:(NSDictionary *)params
                 showHUDAddedTo:(UIView *)view
                     completion:(CMFapisCompletion)completion{
    [self fapisPrivateApiWithClass:className package:package keyPackage:nil appType:appType params:params showHUDAddedTo:view completion:completion];
}


/**
 *  警报
 *
 *  @param code    类别代码
 *  @param message 警报信息
 */

+ (void)toReportErrorWithAppType:(CMAppType)appType Code:(NSString *)code message:(NSDictionary *)message{
    NSAssert(code.length, @"code is nil");
    NSMutableDictionary *dict = [message mutableCopy];
    if (!dict) {
        dict = [NSMutableDictionary dictionary];
    }
    
    NSString *sys = [NSString stringWithFormat:@"%@_%@_%@", [[UIDevice currentDevice] systemName], [[UIDevice currentDevice] systemVersion], [CMApiUtils modelReadable]];
    [dict setObject:sys forKey:@"sys"];
    NSString *strVersion = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
    NSError *error = nil;
    NSData *data = [NSJSONSerialization dataWithJSONObject:[CMAppHttpApi checkParams:dict] options:0 error:&error];
    NSString *strMessage = nil;
    if (data) {
        strMessage = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    }
    else{
        strMessage = @"";
    }
    NSString *url = [CMApiUtils appendingApiBaseUrl:@"api/index.php?funcNo=1112"];
    NSString *appName = nil;
    switch (appType) {
        case CMAppTypeChemao: {
            appName = @"cm";
            break;
        }
        case CMAppTypeCheshang: {
            appName = @"cs";
            break;
        }
        case CMAppTypeAdviser: {
            appName = @"ca";
            break;
        }
        case CMAppTypeRz: {
            appName = @"cr";
            break;
        }
        default: {
            appName = @"";
        }
    }
    if (!appName) {
        appName = @"";
    }
    NSDictionary *dictParams = @{@"code":code, @"message":strMessage, @"app_name":appName, @"app_version":strVersion};
    [CMHttpApi httpGetWithUrl:url params:dictParams showHUDAddedTo:nil completion:^(NSError *error, NSDictionary *responseJson) {}];
}
+ (void)toReportErrorWithCode:(NSString *)code message:(NSDictionary *)message{
    // 判断App类型
    CMAppType appType = 0;
    NSString *appIdentifier = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleIdentifier"];
    if ([appIdentifier rangeOfString:@"com.chemao.CheMao"].location != NSNotFound || [appIdentifier rangeOfString:@"com.hzchemao.CheMao"].location != NSNotFound) {
        appType = CMAppTypeChemao;
    }
    else if ([appIdentifier rangeOfString:@"com.chemao.cheshang"].location != NSNotFound || [appIdentifier rangeOfString:@"com.hzchemao.cheshang"].location != NSNotFound) {
        appType = CMAppTypeCheshang;
    }
    else if ([appIdentifier rangeOfString:@"com.chemao.advisor"].location != NSNotFound || [appIdentifier rangeOfString:@"com.hzchemao.advisor"].location != NSNotFound) {
        appType = CMAppTypeAdviser;
    }
    else if ([appIdentifier rangeOfString:@"com.chemao.rz"].location != NSNotFound || [appIdentifier rangeOfString:@"com.hzchemao.rz"].location != NSNotFound) {
        appType = CMAppTypeRz;
    }
    [self toReportErrorWithAppType:appType Code:code message:message];
}

/**
 *  以下方法已经全部废弃，全部转用CMApis（服务端FAPIS）接口规范
 *
 *  @废弃人 zhairuhui(zhairh@chemao.com.cn)
 */
#pragma mark - Deprecated START__________

+ (void)toGetSubUrl:(NSString *)suburl params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion{
    NSString *url = [CMApiUtils appendingApiBaseUrl:suburl];
    params = [self checkParams:params];
    NSStringEncoding gbkEncoding =CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    [CMHttpApi toGetUrl:url params:params encoding:gbkEncoding cached:NO showHUDAddedTo:view completion:completion];
}
+ (void)requestGetUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\""){
    NSStringEncoding encoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    url = [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    /**********   用户类型 start  **********/
    params = [self checkParams:params];
    /********** 用户类型 end  **********/
    
    [CMHttpApi toGetUrl:url params:params encoding:encoding cached:NO showHUDAddedTo:nil completion:completion];
}


+ (void)toGetCachedSubUrl:(NSString *)suburl params:(NSDictionary *)params completion:(CMCompletion)completion{
    NSString *url = [CMApiUtils appendingApiBaseUrl:suburl];
    params = [self checkParams:params];
    NSStringEncoding gbkEncoding =CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    [CMHttpApi toGetUrl:url params:params encoding:gbkEncoding cached:YES showHUDAddedTo:nil completion:completion];
}
+ (void)requestPostUrl:(NSString *)url params:(id)params completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\""){
    NSStringEncoding encoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    
    /**********   用户类型 start  **********/
    NSDictionary *dict = [self checkParams:nil];
    NSString *userIdentity = dict[@"identity"];
    if (!userIdentity.length) {
        userIdentity = @"";
    }
    if ([params isKindOfClass:[NSString class]]) {
        NSData *jsonData = [params dataUsingEncoding:NSUTF8StringEncoding];
        NSMutableDictionary *object = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:nil];
        
        NSMutableString *infoString = [object[@"info"] mutableCopy];
        [infoString appendFormat:@"&identity=%@", userIdentity];
        
        NSMutableDictionary *jsonDictionary = [object mutableCopy];
        if (!jsonDictionary) {
            jsonDictionary = [NSMutableDictionary dictionary];
        }
        [jsonDictionary setValue:infoString forKey:@"info"];
        
        NSData *data = [NSJSONSerialization dataWithJSONObject:jsonDictionary options:0 error:nil];
        params = [[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding] mutableCopy];
    }
    else if ([params isKindOfClass:[NSDictionary class]] || !params) {
        if (!params[@"params"]) {
            NSMutableDictionary *carInfo = [params mutableCopy];
            [carInfo setValue:userIdentity forKey:@"params"];
            if (!carInfo) {
                carInfo = [NSMutableDictionary dictionary];
            }
            params = [carInfo copy];
        }
    }
    
    /********** 用户类型 end  **********/
    NSString *strParams = nil;
    if (![params isKindOfClass:[NSString class]]) {
        NSError *errorJson = nil;
        NSData *dataParams = [NSJSONSerialization dataWithJSONObject:params options:NSJSONWritingPrettyPrinted error:&errorJson];
        NSAssert(errorJson == nil, @"params jsonError");
        strParams = [[NSString alloc] initWithData:dataParams encoding:NSUTF8StringEncoding];
    } else
        strParams = params;
    NSData *dataPost = [strParams dataUsingEncoding:encoding];
    [CMHttpApi toPostUrl:url data:dataPost encoding:encoding completion:completion];
}

+ (BOOL)isStatusOk:(NSDictionary *)responseObject {
    if ([responseObject[@"status"] integerValue] == 1) {
        return YES;
    }
    return NO;
}

@end

