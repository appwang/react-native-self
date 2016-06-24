//
//  AppUtils.h
//  zlydoc+iphone
//
//  Created by Ryan on 14+5+23.
//  Copyright (c) 2014年 zlycare. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CMAppUtils : NSObject

/********************** System Utils ***********************/


//获取MD5加密后字符串
+ (NSString *)md5FromString:(NSString *)str;

+ (NSString *)base64EncodedStringFromString:(NSString*)str;

//随机数（比如随机3位或8位）
+ (NSString *)getRandomChars:(NSInteger)count;

@end