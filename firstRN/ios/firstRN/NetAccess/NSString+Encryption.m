//
//  NSString+Encryption.m
//  todo
//
//  Created by zrh on 14-3-31.
//  Copyright (c) 2014年 com.benhe. All rights reserved.
//

#import "NSString+Encryption.h"
#import <CommonCrypto/CommonDigest.h>
#import "CMAppUtils.h"

@implementation NSString (Encryption)

- (NSString *) md5HexDigest{
    const char *original_str = [self UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(original_str, (CC_LONG)strlen(original_str), result);
    NSMutableString *hash = [NSMutableString string];
    for (int i = 0; i < 16; i++)
        [hash appendFormat:@"%02x", result[i]];
    return [hash lowercaseString];
}

- (NSString *)psdEncode{
    NSString *strRand = [CMAppUtils getRandomChars:3];
    NSString *strPsd64 = [CMAppUtils base64EncodedStringFromString:self];
    return [strRand stringByAppendingString:strPsd64];
}

- (NSString *)CMURLEncode {
    NSString *newString = CFBridgingRelease(CFURLCreateStringByAddingPercentEscapes(kCFAllocatorDefault, (CFStringRef)self,NULL,  CFSTR(":/?#[]@!$ &'()*+,;=\"<>%{}|\\^~`"), CFStringConvertNSStringEncodingToEncoding(NSUTF8StringEncoding)));
    if (newString) {
        return newString;
    }
    return self;
}

@end
