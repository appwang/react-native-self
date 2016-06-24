//
//  AppUtils.m
//  zlydoc+iphone
//
//  Created by Ryan on 14+5+23.
//  Copyright (c) 2014年 zlycare. All rights reserved.
//

#import "CMAppUtils.h"
#import <CommonCrypto/CommonDigest.h>

@implementation CMAppUtils

/********************* System Utils **********************/

+ (NSString *)base64EncodedStringFromString:(NSString *)string {
    NSData *data = [NSData
        dataWithBytes:[string UTF8String]
               length:[string lengthOfBytesUsingEncoding:NSUTF8StringEncoding]];
    NSUInteger length = [data length];
    NSMutableData *mutableData =
        [NSMutableData dataWithLength:((length + 2) / 3) * 4];

    uint8_t *input = (uint8_t *) [data bytes];
    uint8_t *output = (uint8_t *) [mutableData mutableBytes];

    for (NSUInteger i = 0; i < length; i += 3) {
        NSUInteger value = 0;
        for (NSUInteger j = i; j < (i + 3); j++) {
            value <<= 8;
            if (j < length) {
                value |= (0xFF & input[j]);
            }
        }

        static uint8_t const kAFBase64EncodingTable[] =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

        NSUInteger idx = (i / 3) * 4;
        output[idx + 0] = kAFBase64EncodingTable[(value >> 18) & 0x3F];
        output[idx + 1] = kAFBase64EncodingTable[(value >> 12) & 0x3F];
        output[idx + 2] = (i + 1) < length
                              ? kAFBase64EncodingTable[(value >> 6) & 0x3F]
                              : '=';
        output[idx + 3] = (i + 2) < length
                              ? kAFBase64EncodingTable[(value >> 0) & 0x3F]
                              : '=';
    }

    return [[NSString alloc] initWithData:mutableData
                                 encoding:NSASCIIStringEncoding];
}

+ (NSString *)sha1:(NSString *)input {
    NSData *data = [input dataUsingEncoding:NSUTF8StringEncoding];

    uint8_t digest[CC_SHA1_DIGEST_LENGTH];
    CC_SHA1(data.bytes, (int) data.length, digest);

    NSMutableString *output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];

    for (int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++) {
        [output appendFormat:@"%02x", digest[i]];
    }

    return output;
}


+ (NSString *)getRandomChars:(NSInteger)count {
  const NSString *kchartables = @"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  unichar chars1[count];
  for (NSInteger i = 0; i < count; i++) {
    NSInteger x = arc4random() % 62;
    unichar letter = [kchartables characterAtIndex:x];
    chars1[i] = letter;
  }
  NSString *randomChars = [NSString stringWithCharacters:chars1 length:count];
  return randomChars;
}



@end
