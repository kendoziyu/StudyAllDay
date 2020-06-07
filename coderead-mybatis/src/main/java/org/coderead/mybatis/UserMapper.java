package org.coderead.mybatis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.mapping.StatementType;
import org.coderead.mybatis.bean.User;
import org.coderead.mybatis.cache.DiskCache;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface UserMapper {

    @Select({"select * from users where id=#{userId}"})
    User selectByid(Integer id);

    @Select({"select * from users where id=#{1}"})
    @Options
    User selectByid3(Integer id);

    @Select({"select * from users where id={key}"})
    User selectByid4(Map map);

    @Select({"select * from users where name=#{name} or age=#{user.age}"})
    @Options
    User selectByNameOrAge(@Param("name") String name, @Param("user") User user);

    @Select({" select * from users where name='${name}'"})
    @Options(statementType = StatementType.PREPARED)
    List<User> selectByName(User user);

    List<User> selectByUser(User user);

    @Insert("INSERT INTO `users`( `name`, `age`, `sex`, `email`, `phone_number`) VALUES ( #{name}, #{age}, #{sex}, #{email}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);

    int editUser(User user);

    @Update("update  users set name=#{param1} where id=#{id}")
    @Options(flushCache = Options.FlushCachePolicy.FALSE)
    int setName(Integer id, String name);

    @Delete("delete from users where id=#{id}")
    int deleteUser(Integer id);
}
