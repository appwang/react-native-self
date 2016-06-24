//
//  CMHttpApi.m
//  cheshang
//
//  Created by zrh on 16/3/25.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import "CMHttpApi.h"
#import "AFNetworking.h"
#import "MBProgressHUD.h"

#ifndef CMSTRTipLoading
#define CMSTRTipLoading    @"正在努力加载中..."
#endif

#ifndef CMSTRLoading
#define CMSTRLoading @"加载中..."
#endif

// 测试
static NSString *const keyPublicDefaultT = @"c8b697c75a0050c4d8513bf1b73be184";
static NSString *const keyPublicChemaoT = @"2d44d8e2fc0b9517b5152ede2cf042b1";

// 线上
static NSString *const keyPublicDefault = @"f9d670fab96694f6998f1ca7505acc0d";
static NSString *const keyPublicChemao = @"f48a897be771827d05ba0b722165a09d";


@interface CMHttpApi ()

@property (strong, nonatomic, readonly) NSString *keyPublicDefault;
@property (strong, nonatomic, readonly) NSString *keyPublicChemao;

@property (strong, nonatomic, readonly) NSString *urlFapis;

@property (strong, nonatomic) AFHTTPSessionManager *sessionManager;


@end

@implementation CMHttpApi

#pragma mark - ____________________
#pragma mark  Get

+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion {
    [self httpGetWithUrl:url params:params encoding:NSUTF8StringEncoding cachePolicy:CMCachePolicyDefault showHUDAddedTo:view completion:completion];
}

+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params cachePolicy:(CMCachePolicy)cachePolicy showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion {
    [self httpGetWithUrl:url params:params encoding:NSUTF8StringEncoding cachePolicy:cachePolicy showHUDAddedTo:view completion:completion];
}

+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding cachePolicy:(CMCachePolicy)cachePolicy showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;
{
    [[self shareIntance] requestWithHTTPMethod:@"GET" Url:url params:params encoding:encoding showHUDAddedTo:view completion:completion];
}

#pragma mark Post

+ (void)httpPostWithUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion {
    [self httpPostWithUrl:url params:params encoding:NSUTF8StringEncoding showHUDAddedTo:view completion:completion];
}

+ (void)httpPostWithUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion {
    [[self shareIntance] requestWithHTTPMethod:@"POST" Url:url params:params encoding:NSUTF8StringEncoding showHUDAddedTo:view completion:completion];
}

#pragma mark Fapis

+ (void)fapisWithClass:(NSString *)nameClass
               package:(NSString *)namePackage
            keyPackage:(NSString *)keyPackage
               appType:(CMAppType)appType
            parameters:(NSDictionary *)parameters
        showHUDAddedTo:(UIView *)view
            completion:(CMFapisCompletion)completion{
    NSString *keyPublic = nil;
    CMHttpApi *api = [self shareIntance];
    switch (appType) {
        case CMAppTypeChemao: {
            keyPublic = api.keyPublicChemao;
            break;
        }
        default:{
            keyPublic = api.keyPublicDefault;
            break;
        }
    }
    [api fapisBaseWithClass:nameClass package:namePackage keyPackage:keyPackage keyPublic:keyPublic parameters:parameters showHUDAddedTo:view completion:completion];
}

#pragma mark - __________Base___________

+ (instancetype)shareIntance{
    static CMHttpApi *api = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        api = [[CMHttpApi alloc] init];
        [api toInitConfig];
        api.sessionManager = [AFHTTPSessionManager manager];
        api.sessionManager.responseSerializer = [AFHTTPResponseSerializer serializer];
        api.sessionManager.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeNone];
    });
    return api;
}

- (void)toInitConfig{
    CMEnvironment type = [CMApiUtils apiEnviroment];
    if (type & CMEnvironmentIsTest) {
        _keyPublicDefault = keyPublicDefaultT;
        _keyPublicChemao = keyPublicChemaoT;
    }
    else{
        _keyPublicDefault = keyPublicDefault;
        _keyPublicChemao = keyPublicChemao;
    }
    _urlFapis = [CMApiUtils apiFapiUrl];
}

- (BOOL)isNetworkOkWithError:(NSError **)error{
    AFNetworkReachabilityManager *reachManage = [AFNetworkReachabilityManager sharedManager];
    AFNetworkReachabilityStatus currState = [reachManage networkReachabilityStatus];
    if (currState == AFNetworkReachabilityStatusNotReachable) {
        *error = [NSError errorWithDomain:NSURLErrorDomain code:NSURLErrorNotConnectedToInternet userInfo:nil];
        return NO;
    }
    return YES;
}

#pragma mark - Base request

/**
 *  标准接口(不缓存)
 *
 *  @param request          NSURLRequest
 *  @param uploadProgress   return NSProgress *uploadProgress
 *  @param downloadProgress return NSProgress *uploadProgress
 *  @param encoding         NSStringEncoding
 *  @param completion       CMCompletion
 *
 *  @return NSURLSessionDataTask
 */
- (NSURLSessionDataTask *)dataTaskWithURLRequest:(NSURLRequest *)request
                                  uploadProgress:(void (^)(NSProgress *uploadProgress)) uploadProgress
                                downloadProgress:(void (^)(NSProgress *downloadProgress)) downloadProgress
                                        encoding:(NSStringEncoding)encoding
                                      completion:(CMCompletion)completion{
    NSAssert(request, @"UrlRequest is nil");
    
    NSDate *dateRequestStart = [NSDate date];
    NSLog(@"%@", dateRequestStart);
    
    __block NSURLSessionDataTask *dataTask = nil;
    dataTask = [self.sessionManager dataTaskWithRequest:request uploadProgress:uploadProgress downloadProgress:downloadProgress completionHandler:^(NSURLResponse * __unused response, id responseObject, NSError *error) {
        if (error) {
#if defined(DEBUG)||defined(_DEBUG)
            NSLog(@"🆘🆘🆘🆘🆘🆘\nrequest:\t%@\nheader:\t%@\nbody:\t%@\n\nresponse:%@\n\nerror:\n%@\n",request.URL.absoluteString,request.allHTTPHeaderFields, [[NSString alloc] initWithData:[request HTTPBody] encoding:NSUTF8StringEncoding],response.description, error);
#endif
            if (completion) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    completion(error, nil);
                });
            }
            return ;
        }
        
        NSData *responseData = responseObject;
        NSString *responseString = [[NSString alloc] initWithData:responseData encoding:encoding];
        if (encoding != NSUTF8StringEncoding) {
            responseData = [responseString dataUsingEncoding:NSUTF8StringEncoding];
        }
        
        NSObject *jsonObject = nil;
        NSError *errorParse = nil;
        jsonObject = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingAllowFragments error:&errorParse];
        
#if defined(DEBUG)||defined(_DEBUG)
        NSLog(@"✅✅✅✅✅✅\nrequest:\t%@\ntimeStart:%@\ttimeEnd:%@\ttime cost:%f\nheader:\t%@\nbody:\t%@\n\nresponse:\n%@\n\n",request.URL.absoluteString, dateRequestStart, [NSDate date], [dateRequestStart timeIntervalSinceNow], request.allHTTPHeaderFields, [[NSString alloc] initWithData:[request HTTPBody] encoding:NSUTF8StringEncoding], responseString);
#endif
        if (errorParse) {
            if (completion) {
                NSError *error = [NSError errorWithDomain:NSURLErrorDomain code:NSURLErrorCannotDecodeRawData userInfo:@{kCMErrorUserInfoKeyInfo:responseString?responseString:@""}];
                completion(error, nil);
            }
            return;
        }
        if (!jsonObject || ![jsonObject isKindOfClass:[NSDictionary class]]) {
            if (completion) {
                NSError *error = [NSError errorWithDomain:NSURLErrorDomain code:NSURLErrorCannotDecodeContentData userInfo:@{kCMErrorUserInfoKeyInfo:responseString?responseString:@""}];
                completion(error, nil);
            }
            return;
        }
        
        if (completion) {
            completion(nil, (NSDictionary *)jsonObject);
        }
    }];
    [dataTask resume];
    return dataTask;
}

#pragma mark Http

/**
 *  标准接口
 *
 *  @param method      HTTPMethod，默认GET
 *  @param url         url
 *  @param params      参数
 *  @param encoding    编码格式
 *  @param completion  完成回调
 */
- (NSURLSessionDataTask *)requestWithHTTPMethod:(NSString *)method
                                            Url:(NSString *)url
                                         params:(NSDictionary *)params
                                       encoding:(NSStringEncoding)encoding
                                 showHUDAddedTo:(UIView *)view
                                     completion:(CMCompletion)completion {
    NSAssert(url.length, @"CMHttpRequest URLString is null");
    url = [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];

    NSError *error = nil;
    if (![self isNetworkOkWithError:&error]) {
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(error, nil);
            });
        }
        return nil;
    }
    __weak MBProgressHUD *hud = nil;
    if (completion && view) {
        hud = [MBProgressHUD showHUDAddedTo:view animated:true];
        hud.dimBackground = true;
        hud.labelText = CMSTRLoading;
    }
    
    
    NSDate *dateRequestStart = [NSDate date];
    NSLog(@"%@", dateRequestStart);
    
    if (!method.length) {
        method = @"GET";
    }
    
    NSError *serializationError = nil;
    NSMutableURLRequest *request = nil;
    
    if ([method isEqualToString:@"POST"]) {
        AFJSONRequestSerializer *serializer = [AFJSONRequestSerializer serializer];
        serializer.stringEncoding = encoding;
        request = [serializer requestWithMethod:method URLString:url parameters:params error:&serializationError];
    }
    else {
        AFHTTPRequestSerializer *serializer = [AFHTTPRequestSerializer serializer];
        serializer.stringEncoding = encoding;
        request = [serializer requestWithMethod:method URLString:url parameters:params error:&serializationError];
    }
    
    if (serializationError) {
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(serializationError, nil);
            });
        }
        return nil;
    }
    
    return [self dataTaskWithURLRequest:request uploadProgress:nil downloadProgress:nil encoding:encoding completion:^(NSError *error, NSDictionary *responseJson) {
        if (view) {
            [hud hide:NO];
        }
        if (completion){
            completion(error, responseJson);
        }
    }];
}
- (NSURLSessionDataTask *)requestWithPostUrl:(NSString *)url
                                        data:(NSData *)data
                                    encoding:(NSStringEncoding)encoding
                                  completion:(CMCompletion)completion {
    NSAssert(url.length, @"CMHttpRequest URLString is null");
    url = [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSError *error = nil;
    if (![self isNetworkOkWithError:&error]) {
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(error, nil);
            });
        }
        return nil;
    }
    
    NSDate *dateRequestStart = [NSDate date];
    NSLog(@"%@", dateRequestStart);
    
    NSError *serializationError = nil;
    NSMutableURLRequest *request = nil;
    
    AFJSONRequestSerializer *serializer = [AFJSONRequestSerializer serializer];
    serializer.stringEncoding = encoding;
    request = [serializer requestWithMethod:@"POST" URLString:url parameters:nil error:&serializationError];
    [request setHTTPBody:data];
    request.timeoutInterval = 60;
    
    if (serializationError) {
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(serializationError, nil);
            });
        }
        return nil;
    }
    
    return [self dataTaskWithURLRequest:request uploadProgress:nil downloadProgress:nil encoding:encoding completion:completion];
}

#pragma mark  fapis
/**
 *  FAPIS 底层接口
 *
 *  @param url          url
 *  @param nameClass    className
 *  @param namePackage  packageName
 *  @param keyPackage   权限key 可以不传
 *  @param keyPublic    公钥
 *  @param parameters   参数
 *  @param view         是否锁屏
 *  @param completion   完成回调
 */
- (NSURLSessionDataTask *)fapisBaseWithClass:(NSString *)nameClass
                                     package:(NSString *)namePackage
                                  keyPackage:(NSString *)keyPackage
                                   keyPublic:(NSString *)keyPublic
                                  parameters:(NSDictionary *)parameters
                              showHUDAddedTo:(UIView *)view
                                  completion:(CMFapisCompletion)completion{
    NSAssert(nameClass && nameClass.length, @"class 不能为空");
    NSAssert(namePackage && namePackage.length, @"package 不能为空");
    NSAssert(keyPublic && keyPublic.length, @"Public key is nil");
    
    NSError *error = nil;
    if (![self isNetworkOkWithError:&error]) {
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(error, nil);
            });
        }
        return nil;
    }
    
    NSDate *dateRequestStart = [NSDate date];
    NSLog(@"%@", dateRequestStart);
    __weak MBProgressHUD *hud = nil;
    if (view != nil) {
        hud = [MBProgressHUD showHUDAddedTo:view animated:true];
        hud.dimBackground = true;
        hud.labelText = CMSTRLoading;
    }
    
    NSMutableDictionary *mutableParameters = [parameters mutableCopy];
    if (!mutableParameters) {
        mutableParameters = [NSMutableDictionary dictionary];
    }
    NSString *utcTmestemp = [CMApiUtils getUtcTmestemp];
    
    // 包签名（存在）
    if (keyPackage.length) {
        NSString *checksum = [CMApiUtils getChecksumWithClass:nameClass package:namePackage keyPackage:keyPackage time:utcTmestemp];
        [mutableParameters setValue:checksum forKey:@"checksum"];
    }
    // package_key 必须remove 防止误传
    [mutableParameters removeObjectForKey:@"package_key"];
    [mutableParameters setObject:nameClass forKey:@"class"];
    [mutableParameters setObject:namePackage forKey:@"package"];
    
    NSDictionary *dictParams = [mutableParameters copy];
    // 数据签名
    NSString *random = [CMApiUtils getRandomChars:8];
    NSString *userAgentInfo = [CMApiUtils getUserAgentInfo];
    NSString *jsonbody = [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:dictParams options:0 error:nil] encoding:NSUTF8StringEncoding];
    NSString *sha1 = [CMApiUtils sha1:[NSString stringWithFormat:@"%@%@%@%@", jsonbody, utcTmestemp, random, keyPublic]];
    
    NSURLRequest *request = nil;
    AFJSONRequestSerializer *serializer = [AFJSONRequestSerializer serializer];
    // httpHeader init
    [serializer setValue:@"application/json;charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    [serializer setValue:sha1 forHTTPHeaderField:@"Signature"];
    [serializer setValue:utcTmestemp forHTTPHeaderField:@"UTC-Timestemp"];
    [serializer setValue:random forHTTPHeaderField:@"Random"];
    [serializer setValue:userAgentInfo forHTTPHeaderField:@"User-Agent"];
    request = [serializer requestWithMethod:@"POST" URLString:self.urlFapis parameters:dictParams error:nil];
    
    return [self dataTaskWithURLRequest:request uploadProgress:nil downloadProgress:nil encoding:NSUTF8StringEncoding completion:^(NSError *error, NSDictionary *responseJson) {
        if (view) {
            [hud hide:NO];
        }
        if (error){
            if (completion) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    completion(error, nil);
                });
            }
            return ;
        }
        CMResponse *baseRes = [CMResponse modelWithJsonDict:responseJson];
        if (baseRes.code == CMResponseCodeTokenError) {
            // 退出登录
            if ([CMApiUtils shareIntance].delegate){
                [[CMApiUtils shareIntance].delegate cmApiLogout:baseRes];
                return;
            }
        }
        if (completion) {
            dispatch_async(dispatch_get_main_queue(), ^{
                completion(nil, baseRes);
            });
        }
    }];
}


/**
 *  以下方法已经全部废弃，全部转用CMApis（服务端FAPIS）接口规范
 *
 *  @废弃人 zhairuhui(zhairh@chemao.com.cn)
 */
#pragma mark - Deprecated START__________

// 标准接口
+ (void)toGetUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding cached:(BOOL)isCached showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion{
    [[self shareIntance] requestWithHTTPMethod:@"GET" Url:url params:params encoding:encoding showHUDAddedTo:view completion:completion];
}
+ (void)toPostUrl:(NSString *)url data:(NSData *)data encoding:(NSStringEncoding)encoding completion:(CMCompletion)completion{
    [[self shareIntance] requestWithPostUrl:url data:data encoding:encoding completion:completion];
}
#pragma mark  Deprecated END__________
#pragma mark -

@end



