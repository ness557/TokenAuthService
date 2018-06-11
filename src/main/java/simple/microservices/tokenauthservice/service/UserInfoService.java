package simple.microservices.tokenauthservice.service;

import simple.microservices.tokenauthservice.model.UserInfo;

import java.util.List;

public interface UserInfoService {

    Status SaveUserInfo(UserInfo userInfo);
    Status removeUserInfo(UserInfo userInfo);
    Status removeUserInfo(int id);
    UserInfo getUserInfoById(int id);
    List<UserInfo> getUserInfoList();
}
