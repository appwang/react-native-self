//
//  CMResponse.h
//  cheshang
//
//  Created by zrh on 16/5/11.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger,CMResponseStatus) {
    CMResponseStatusFailed = 0,
    CMResponseStatusSuccess,
};

typedef NS_ENUM(NSInteger, CMResponseCode) {
    CMResponseCodeTokenMiss = 950,
    CMResponseCodeTokenError = 951,     // Token not registered or expired.(token不存在或已过期)
    CMResponseCodeDateEmpty = 999,
};

/**
 *  FAPIS 专用
 */
@interface CMResponse : NSObject{
@protected
    NSDictionary *_jsonDict;
}

@property (nonatomic, strong) NSDictionary *jsonDict;   //保存对应的dict

@property (nonatomic,assign) NSInteger code;
@property (nonatomic,copy) NSString *msg;       // 显示信息
@property (nonatomic,copy) NSString *runTime;

@property (nonatomic,copy) NSDictionary *result;

@property (nonatomic,assign,readonly) CMResponseStatus status;
@property (nonatomic,strong,readonly) NSString *errorInfo;  //api返回错误信息


/**
 *  以json字典初始化.initWithJsonDict或updateWithJsonDict必须实现,才能满足modelWithJsonDict
 *
 *  @param jsonDict
 *
 *  @return
 */
- (instancetype)initWithJsonDict:(NSDictionary *)jsonDict;
- (void)updateWithJsonDict:(NSDictionary *)jsonDict;        // 必须实现

+ (instancetype)modelWithJsonDict:(NSDictionary *)jsonDict;



@end
