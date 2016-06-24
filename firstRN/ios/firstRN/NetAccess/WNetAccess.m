//
//  WNetAccess.m
//  firstRN
//
//  Created by cousin on 16/6/12.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "WNetAccess.h"
#import "CMAppHttpApi.h"
#import "RCTBridge.h"
#import "NSString+Encryption.h"

@implementation WNetAccess

RCT_EXPORT_MODULE();
RCT_EXPORT_METHOD(loginWithUserName:(NSString *)userName andPassword:(NSString *)password andCallBack:(RCTResponseSenderBlock)callBack)
{
  NSUserDefaults *userDefault = [NSUserDefaults standardUserDefaults];
  if ([userDefault objectForKey:@"user"]) {
    [userDefault removeObjectForKey:@"user"];
    [userDefault synchronize];
  }
  NSString *phoneStr = userName;
  NSString *passwordStr = password.psdEncode;
  NSDictionary *param = @{@"chemao_username":phoneStr, @"chemao_password":passwordStr};
  [CMAppHttpApi fapisPrivateApiWithClass:@"LOGIN" package:@"app_client.cheshang.base_manage" appType:CMAppTypeCheshang params:param showHUDAddedTo:nil completion:^(NSError *error, CMResponse *response) {
    if (response.status == CMResponseStatusSuccess) {
      [userDefault setObject:response.result forKey:@"user"];
      [userDefault synchronize];
      callBack(@[(id)kCFNull,response.result]);
    }else {
      if (error) {
        callBack(@[error.localizedDescription,(id)kCFNull]);
      }else {
        callBack(@[response.errorInfo,(id)kCFNull]);
      }
    }
  }];
}

RCT_EXPORT_METHOD(requestWithSearchCarWithPage:(NSString *)page andCallBack:(RCTResponseSenderBlock)callBack)
{
  NSUserDefaults *userDefault = [NSUserDefaults standardUserDefaults];
  NSDictionary *dic;
  if ([userDefault objectForKey:@"user"]) {
    dic = [userDefault objectForKey:@"user"];
  }
  NSMutableDictionary *params = [NSMutableDictionary dictionaryWithCapacity:0];
  [params setObject:dic[@"user"][@"uid"] forKey:@"uid"];
  [params setObject:@"220" forKey:@"section"];
  [params setObject:@"杭州" forKey:@"section_name"];
  [params setObject:@"" forKey:@"identity"];
  [params setObject:dic[@"token"] forKey:@"token"];
  [params setObject:page forKey:@"page"];
  [CMAppHttpApi fapisPrivateApiWithClass:@"CAR_SEARCH" package:@"app_client.cheshang.car_manage" appType:CMAppTypeCheshang params:params showHUDAddedTo:nil completion:^(NSError *error, CMResponse *response) {
    if (response.status == CMResponseStatusSuccess) {
      callBack(@[(id)kCFNull,response.result]);
    }else {
      if (error) {
        callBack(@[error.localizedDescription,(id)kCFNull]);
      }else {
        callBack(@[response.errorInfo,(id)kCFNull]);
      }
    }
  }];
}


@end
