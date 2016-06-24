//
//  CMHttpApi.h
//  cheshang
//
//  Created by zrh on 16/3/25.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CMApiUtils.h"
#import "CMResponse.h"

@interface CMHttpApi : NSObject

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
                                      completion:(CMCompletion)completion;

@end

#pragma mark - __________HTTP__________

@interface CMHttpApi (Http)

#pragma mark  Get

// NSUTF8StringEncoding
+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;

+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params cachePolicy:(CMCachePolicy)cachePolicy showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;

+ (void)httpGetWithUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding cachePolicy:(CMCachePolicy)cachePolicy showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;


#pragma mark Post

// NSUTF8StringEncoding
+ (void)httpPostWithUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;

+ (void)httpPostWithUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion;

@end

#pragma mark - __________FAPIS__________

@interface CMHttpApi (Fapis)

/**
*  FAPI(version 2.0.0)
*
*  @param nameClass   class name
*  @param namePackage package name
*  @param keyPackage  key of package
*  @param parameters  参数
*  @param view        是否锁屏
*  @param completion  回调
*/
+ (void)fapisWithClass:(NSString *)nameClass
               package:(NSString *)namePackage
            keyPackage:(NSString *)keyPackage
               appType:(CMAppType)appType
            parameters:(NSDictionary *)parameters
        showHUDAddedTo:(UIView *)view
            completion:(CMFapisCompletion)completion;

@end


@interface CMHttpApi (Deprecated)
/**
 *  以下方法已经全部废弃，全部转用CMApis（服务端FAPIS）接口规范
 *
 *  @废弃人 zhairuhui(zhairh@chemao.com.cn)
 */
#pragma mark - Deprecated
// 标准接口
#pragma mark - Get
+ (void)toGetUrl:(NSString *)url params:(NSDictionary *)params encoding:(NSStringEncoding)encoding cached:(BOOL)isCached showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");
+ (void)toPostUrl:(NSString *)url data:(NSData *)data encoding:(NSStringEncoding)encoding completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");

#pragma mark Deprecated END__________

@end



