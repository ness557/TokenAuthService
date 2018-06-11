package simple.microservices.tokenauthservice.service;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.*;
import simple.microservices.tokenauthservice.model.UserInfo;
import simple.microservices.tokenauthservice.repository.UserInfoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoCrudRepository repository;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public UserInfoServiceImpl(UserInfoCrudRepository userInfoCrudRepository) {
        this.repository = userInfoCrudRepository;
    }

    @Override
    public Status SaveUserInfo(UserInfo userInfo) {
        logger.info("Trying to save user_info " + userInfo);

        if (ObjectUtils.allNotNull(repository.save(userInfo))) {
            logger.info("User_info saved");
            return Status.OK;
        }
        logger.info("User_info NOT saved");
        return Status.ERROR;
    }

    @Override
    public Status removeUserInfo(UserInfo userInfo) {
        logger.info("Trying to delete user_info " + userInfo);

        repository.delete(userInfo);
        return Status.OK;
    }

    @Override
    public Status removeUserInfo(int id) {
        logger.info("Trying to delete user_info by id = " + id);

        repository.deleteById(id);
        return Status.OK;
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        logger.info("Trying to find user_info by id = " + id);
        UserInfo info = repository.getById(id);

        if (ObjectUtils.allNotNull(info))
            logger.info("User_info found: " + info);
        else
            logger.info("User_info NOT found");

        return info;
    }

    @Override
    public List<UserInfo> getUserInfoList() {
        logger.info("Trying to get user_info list");

        List<UserInfo> infos = repository.findAll();

        if (infos.isEmpty())
            logger.info("List is empty");
        else
            logger.info("Got list");

        return infos;
    }
}
