//
//  NSString+Encryption.h
//  todo
//
//  Created by zrh on 14-3-31.
//  Copyright (c) 2014年 com.benhe. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (Encryption)
- (NSString *)md5HexDigest;
@end

@interface NSString (CMEncryption)

- (NSString *)psdEncode;

@end


@interface NSString (CMURLEncode)

- (NSString *)CMURLEncode;

@end