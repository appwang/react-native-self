//
//  CMApiUtils.m
//  cheshang
//
//  Created by zrh on 16/5/11.
//  Copyright © 2016年 HangZhou CheMao Network techonology Co.,Ltd. All rights reserved.
//

#import "CMApiUtils.h"
#import <CommonCrypto/CommonDigest.h>
#import <sys/utsname.h>

NSString *const CMHttpApiToLogoutNotification = @"CMHttpApiToLogoutNotification";
NSString *const kCMErrorUserInfoKeyInfo = @"info";

/**
 *  api接口地址（区分线上、预上线、测试、开发）
 */
static NSString *const CMApiBaseUrlRelease    = @"http://www.chemao.com.cn";
static NSString *const CMApiBaseUrlPreRelease = @"http://pre.chemao.com.cn";
static NSString *const CMApiBaseUrlTest       = @"http://test.365eche.com:8181";
static NSString *const CMApiBaseUrlDev        = @"http://dev.365eche.com:8181";


static NSString *const CMApiFapiUrlRelease    = @"http://fapis.chemao.com.cn";
static NSString *const CMApiFapiUrlPreRelease = @"http://prefapis.chemao.com.cn";
static NSString *const CMApiFapiUrlTest       = @"http://testfapis.365eche.com:8181";
static NSString *const CMApiFapiUrlDev        = @"http://testfapis.365eche.com:8181";


static NSString *const CMApiFDDSUrlRelease    = @"http://dss.chemao.com.cn";
static NSString *const CMApiFDDSUrlPreRelease = @"http://dss.chemao.com.cn";
static NSString *const CMApiFDDSUrlTest       = @"http://testdss.365eche.com:8181";
static NSString *const CMApiFDDSUrlDev        = @"http://testdss.365eche.com:8181";


@implementation CMApiUtils

+ (instancetype)shareIntance{
    static CMApiUtils *_shareIntance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _shareIntance = [[CMApiUtils alloc] init];
    });
    return _shareIntance;
}

- (void)setDelegate:(id<CMApiUtilsDelegate>)delegate{
    NSAssert([delegate respondsToSelector:@selector(cmApiLogout:)], @"cmApiLogout: is miss");
    _delegate = delegate;
}

static CMEnvironment _apiEnvironment = CMEnvironmentRelease;

+ (void)setApiEnvironment:(CMEnvironment)type{
    _apiEnvironment = type;
}
+ (CMEnvironment)apiEnviroment{
    return _apiEnvironment;
}

+ (NSDictionary *)checkParams:(NSDictionary *)params{
    CMApiUtils *api = [CMApiUtils shareIntance];
    if (api.delegate && [api.delegate respondsToSelector:@selector(cmApiCheckParams:)]) {
        return [api.delegate cmApiCheckParams:params];
    }
    return params;
}


/**
 *  接口地址拼接
 *
 *  @param subUrl 子链接
 *
 *  @return NSString of url
 */
+ (NSString *)appendingApiBaseUrl:(NSString *)subUrl{
    NSString *str1 = [subUrl substringToIndex:1];
    if ([str1 isEqualToString:@"/"]) {
        return [[self apiBaseUrl] stringByAppendingString:subUrl];
    }else {
        return [[self apiBaseUrl] stringByAppendingFormat:@"/%@", subUrl];
    }
}
+ (NSString *)appendingApiFapiUrl:(NSString *)subUrl{
    NSString *str1 = [subUrl substringToIndex:1];
    if ([str1 isEqualToString:@"/"]) {
        return [[self apiFapiUrl] stringByAppendingString:subUrl];
    }else {
        return [[self apiFapiUrl] stringByAppendingFormat:@"/%@", subUrl];
    }
}
+ (NSString *)appendingApiFDDSUrl:(NSString *)subUrl{
    NSString *str1 = [subUrl substringToIndex:1];
    if ([str1 isEqualToString:@"/"]) {
        return [[self apiFDDSUrl] stringByAppendingString:subUrl];
    }else {
        return [[self apiFDDSUrl] stringByAppendingFormat:@"/%@", subUrl];
    }
}

// 旧版接口地址
+ (NSString *)apiBaseUrl {
    NSString *strBaseUrl = nil;
    CMEnvironment type = _apiEnvironment;
    switch (type) {
        case CMEnvironmentRelease: {
            strBaseUrl = CMApiBaseUrlRelease;
            break;
        }
        case CMEnvironmentPreRelease: {
            strBaseUrl = CMApiBaseUrlPreRelease;
            break;
        }
        case CMEnvironmentTest: {
            strBaseUrl = CMApiBaseUrlTest;
            break;
        }
        case CMEnvironmentDevelopment: {
            strBaseUrl = CMApiBaseUrlDev;
            break;
        }
        case CMEnvironmentCustom: {
            strBaseUrl = CMApiBaseUrlPreRelease;
            break;
        }
        default: {
            strBaseUrl = CMApiBaseUrlRelease;
            break;
        }
    }
    return strBaseUrl;
}
// Fapis接口地址
+ (NSString *)apiFapiUrl{
    NSString *strFapiUrl = nil;
    CMEnvironment type = _apiEnvironment;
    switch (type) {
        case CMEnvironmentRelease: {
            strFapiUrl = CMApiFapiUrlRelease;
            break;
        }
        case CMEnvironmentPreRelease: {
            strFapiUrl = CMApiFapiUrlPreRelease;
            break;
        }
        case CMEnvironmentTest: {
            strFapiUrl = CMApiFapiUrlTest;
            break;
        }
        case CMEnvironmentDevelopment: {
            strFapiUrl = CMApiFapiUrlDev;
            break;
        }
        case CMEnvironmentCustom: {
//            strFapiUrl = [CMUserDefaults apiCustomUrl];
//            if (strFapiUrl.length) {
//                break;
//            }
        }
        default: {
            strFapiUrl = CMApiBaseUrlRelease;
            break;
        }
    }
    return strFapiUrl;
}
// FDDS接口地址
+ (NSString *)apiFDDSUrl{
    NSString *strBaseUrl = nil;
    CMEnvironment type = _apiEnvironment;
    switch (type) {
        case CMEnvironmentRelease: {
            strBaseUrl = CMApiFDDSUrlRelease;
            break;
        }
        case CMEnvironmentPreRelease: {
            strBaseUrl = CMApiFDDSUrlPreRelease;
            break;
        }
        case CMEnvironmentTest: {
            strBaseUrl = CMApiFDDSUrlTest;
            break;
        }
        case CMEnvironmentDevelopment: {
            strBaseUrl = CMApiFDDSUrlDev;
            break;
        }
        default: {
            strBaseUrl = CMApiFDDSUrlRelease;
            break;
        }
    }
    return strBaseUrl;
}

+ (NSString *)getChecksumWithClass:(NSString *)nameClass
                           package:(NSString *)namePackage
                        keyPackage:(NSString *)keyPackage
                              time:(NSString *)time{
    NSString *utf = [NSString stringWithFormat:@"%@%@%@%@", time, namePackage, nameClass, keyPackage];
    NSString *md5utf = [self md5FromString:utf];
    return md5utf;
    
}

+ (NSString *)getChecksum:(NSDictionary *)parm time:(NSString *)time {
    NSString *package = @"";
    NSString *class = @"";
    NSString *package_key = @"";
    if (parm[@"package"]) {
        package = parm[@"package"];
        
        NSLog(@"-----%@-----",package);
        
    }
    if (parm[@"class"]) {
        class = parm[@"class"];
        
        NSLog(@"-----%@-----",class);
        
    }
    if (parm[@"package_key"]) {
        package_key = parm[@"package_key"];
        
        NSLog(@"-----%@-----",package_key);
        
    }
    
    NSString *utf = [NSString stringWithFormat:@"%@%@%@%@", time, package, class, package_key];
    NSString *md5utf = [self md5FromString:utf];
    return md5utf;
}

+ (NSString *)getUtcTmestemp {
    long long date = (long long) [[NSDate date] timeIntervalSince1970];
    NSString *utc_tmestemp = [NSString stringWithFormat:@"%lld", date];
    return utc_tmestemp;
}

+ (NSString *)getUserAgentInfo {
    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];
    NSString *versionNum = infoDict[@"CFBundleShortVersionString"];
    NSString *appName = infoDict[@"CFBundleName"];
    NSString *platformName = infoDict[@"DTPlatformName"];
    NSString *platformVersion = infoDict[@"DTPlatformVersion"];
    NSString *bundleVersion = infoDict[@"CFBundleVersion"];
    NSString *userAgentInfo = [NSString stringWithFormat:@"%@/%@(%@;ios %@;bundleVersion %@)", appName, versionNum, platformName, platformVersion, bundleVersion];
    return userAgentInfo;
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

+ (NSString *)md5FromString:(NSString *)str {
    const char *cStr = [str UTF8String];
    unsigned char result[16];
    CC_MD5(cStr, (int) strlen(cStr), result); // This is the md5 call
    return [NSString
            stringWithFormat:
            @"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
            result[0], result[1], result[2], result[3], result[4], result[5],
            result[6], result[7], result[8], result[9], result[10], result[11],
            result[12], result[13], result[14], result[15]];
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

// 设备型号 iPhone8,1
+ (NSString *)models{
    static NSString *deviceString = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        struct utsname systemInfo;
        uname(&systemInfo);
        deviceString = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
#if defined(DEBUG) || defined(_DEBUG)
        NSLog(@"systemInfo.machine:%@",deviceString);
#endif
        
    });
    return deviceString;
}
// 设备型号 iPhone 6s
+ (NSString *)modelReadable{
    NSDictionary *dictDeviceNamesByCode = @{@"i386"      :@"iPhone Simulator",
                                            @"x86_64"    :@"iPhone Simulator",
                                            @"iPhone1,1" :@"iPhone 2G",
                                            @"iPhone1,2" :@"iPhone 3G",
                                            @"iPhone2,1" :@"iPhone 3GS",
                                            @"iPhone3,1" :@"iPhone 4(GSM)",
                                            @"iPhone3,2" :@"iPhone 4(GSM Rev A)",
                                            @"iPhone3,3" :@"iPhone 4(CDMA)",
                                            @"iPhone4,1" :@"iPhone 4S",
                                            @"iPhone5,1" :@"iPhone 5(GSM)",
                                            @"iPhone5,2" :@"iPhone 5(GSM+CDMA)",
                                            @"iPhone5,3" :@"iPhone 5c(GSM)",
                                            @"iPhone5,4" :@"iPhone 5c(Global)",
                                            @"iPhone6,1" :@"iphone 5s(GSM)",
                                            @"iPhone6,2" :@"iphone 5s(Global)",
                                            @"iPhone7,1" :@"iPhone 6 Plus",
                                            @"iPhone7,2" :@"iPhone 6",
                                            @"iPhone8,1" :@"iPhone 6s",
                                            @"iPhone8,2" :@"iPhone 6s Plus",
                                            @"iPhone8,4" :@"iPhone SE",
                                            
                                            @"iPod1,1"   :@"iPod Touch 1",   // (Original)
                                            @"iPod2,1"   :@"iPod Touch 2",   // (Second Generation)
                                            @"iPod3,1"   :@"iPod Touch 3",   // (Third Generation)
                                            @"iPod4,1"   :@"iPod Touch 4",   // (Fourth Generation)
                                            @"iPod5,1"   :@"iPod Touch 5",
                                            @"iPod6,1"   :@"iPod Touch",
                                            @"iPod7,1"   :@"iPod Touch",      //(6th Generation)
                                            
                                            @"iPad1,1"   :@"iPad",
                                            @"iPad2,1"   :@"iPad 2(WiFi)",
                                            @"iPad2,2"   :@"iPad 2(GSM)",
                                            @"iPad2,3"   :@"iPad 2(CDMA)",
                                            @"iPad2,4"   :@"iPad 2(WiFi + New Chip)",
                                            @"iPad2,5"   :@"iPad mini (WiFi)",
                                            @"iPad2,6"   :@"iPad mini (GSM)",
                                            @"iPad2,7"   :@"ipad mini (GSM+CDMA)",
                                            @"iPad3,1"   :@"iPad 3(WiFi)",
                                            @"iPad3,2"   :@"iPad 3(GSM+CDMA)",
                                            @"iPad3,3"   :@"iPad 3(GSM)",
                                            @"iPad3,4"   :@"iPad 4(WiFi)",
                                            @"iPad3,5"   :@"iPad 4(GSM)",
                                            @"iPad3,6"   :@"iPad 4(GSM+CDMA)",
                                            @"iPad4,1"   :@"iPad Air",      // 5th Generation iPad (iPad Air) - Wifi
                                            @"iPad4,2"   :@"iPad Air",      // 5th Generation iPad (iPad Air) - Cellular
                                            @"iPad4,3"   :@"iPad Air",      // 5th Generation iPad (iPad Air) - Cellular
                                            @"iPad4,4"   :@"iPad Mini 2",     // (2nd Generation iPad Mini - Wifi)
                                            @"iPad4,5"   :@"iPad Mini 2",     // (2nd Generation iPad Mini - Cellular)
                                            @"iPad4,6"   :@"iPad Mini 2",     // (2nd Generation iPad Mini )
                                            @"iPad4,7"   :@"iPad Mini 3",   // (3rd Generation iPad Mini - Wifi (model A1599))
                                            @"iPad4,8"   :@"iPad Mini 3",   // (3rd Generation iPad Mini -  (model ))
                                            @"iPad4,9"   :@"iPad Mini 3",   // (3rd Generation iPad Mini - Wifi (model ))
                                            };
    NSString *code = [self models];
    NSString* deviceName = [dictDeviceNamesByCode objectForKey:code];
    if (!deviceName) {
        deviceName = [NSString stringWithFormat:@"Unknow Device: %@",[self models]];
    }
    return deviceName;
}

#pragma mark - Private method



@end
