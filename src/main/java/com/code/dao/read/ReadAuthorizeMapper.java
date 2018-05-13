
package com.code.dao.read;
import java.util.*;
import com.code.config.mybatis.ReadMapper;
import com.code.domain.Authorize;
import java.util.List;

/**
 * <p> Mapper Class</p>
 *
 * @author majian
 * 
 */
public interface ReadAuthorizeMapper extends ReadMapper<Authorize>{

    List<String> getAuthorizeStringList(int Type);

}
