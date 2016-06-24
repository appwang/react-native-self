//
//  CMResponse.m
//  cheshang
//
//  Created by zrh on 16/5/11.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import "CMResponse.h"
#import "CMApiUtils.h"

@implementation CMResponse


#pragma mark - Public method

- (instancetype)initWithJsonDict:(NSDictionary *)jsonDict{
    self = [super init];
    if(self){
        @try {//服务端数据为空时经常类型变,比如字典变成数组
            [self updateWithJsonDict:jsonDict];
        }
        @catch (NSException *exception) {
            
            NSLog(@"updateWithJsonDict error");
            
        }
        @finally {
            
        }
    }
    return self;
}

- (void)updateWithJsonDict:(NSDictionary *)jsonDict{
    _jsonDict = jsonDict;
    _msg = jsonDict[@"status"][@"msg"];
    _code = [jsonDict[@"status"][@"code"] integerValue];
    _runTime = jsonDict[@"status"][@"runtime"];
    _result = jsonDict[@"result"];
    if (_code == 0) {
        _status = CMResponseStatusSuccess;
    }
    else {
        _status = CMResponseStatusFailed;
    }
}

+ (instancetype)modelWithJsonDict:(NSDictionary *)jsonDict{
    return [[self alloc] initWithJsonDict:jsonDict];
}

#pragma mark - @property

- (NSString *)errorInfo{
    if (_msg && _msg.length) {
        return _msg;
    }
    return CMSTRNetworkBad;
}

@end
