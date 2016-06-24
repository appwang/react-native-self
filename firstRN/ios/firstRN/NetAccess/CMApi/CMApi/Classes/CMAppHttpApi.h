//
//  CMAppHttpApi.h
//  cheshang
//
//  Created by zrh on 16/3/25.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CMApiUtils.h"
#import "CMResponse.h"


@interface CMAppHttpApi : NSObject


+ (instancetype)shareIntance;

+ (NSDictionary *)checkParams:(NSDictionary *)params;

#pragma mark -

/**
 *  警报
 *
 *  @param code    类别代码
 *  @param message 警报信息
 */
+ (void)toReportErrorWithCode:(NSString *)code message:(NSDictionary *)message;



@end

@interface CMAppHttpApi (Fapis)


#pragma mark - Normal extend chemao
/**
 *  FAPIS 接口参数说明
 *
 *  @param nameClass   class name
 *  @param namePackage package name
 *  @param keyPackage  key of package 可以为nil
 *  @param parameters  参数
 *  @param view
 *  @param completion (NSError *error, CMResponse *response);
 */

/**
 *  私有api 需要包签名
 */
+ (void)fapisPrivateApiWithClass:(NSString *)className
                         package:(NSString *)package
                      keyPackage:(NSString *)keyPackage
                         appType:(CMAppType)appType
                          params:(NSDictionary *)params
                  showHUDAddedTo:(UIView *)view
                      completion:(CMFapisCompletion)completion;
/**
 *  私有api 需要包签名(keyPackage由appType决定)
 */
+ (void)fapisPrivateApiWithClass:(NSString *)className
                         package:(NSString *)package
                         appType:(CMAppType)appType
                          params:(NSDictionary *)params
                  showHUDAddedTo:(UIView *)view
                      completion:(CMFapisCompletion)completion;
/**
 *  公共api，仅对数据签名
 */
+ (void)fapisPublicApiWithClass:(NSString *)className
                        package:(NSString *)package
                        appType:(CMAppType)appType
                         params:(NSDictionary *)params
                 showHUDAddedTo:(UIView *)view
                     completion:(CMFapisCompletion)completion;

@end



@interface CMAppHttpApi (Deprecated)

/**
 *  以下方法已经全部废弃，全部转用CMApis（服务端FAPIS）接口规范
 *
 *  @废弃人 zhairuhui(zhairh@chemao.com.cn)
 */
#pragma mark - Deprecated START__________
+ (void)toGetSubUrl:(NSString *)suburl params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");
+ (void)requestGetUrl:(NSString *)url params:(NSDictionary *)params showHUDAddedTo:(UIView *)view completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");


+ (void)toGetCachedSubUrl:(NSString *)suburl params:(NSDictionary *)params completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");

// 发车专用
+ (void)requestPostUrl:(NSString *)url params:(id)params completion:(CMCompletion)completion CM_Deprecated("user \"Fapis\"");

+ (BOOL)isStatusOk:(NSDictionary *)responseObject CM_Deprecated("老接口");

#pragma mark Deprecated END__________

@end