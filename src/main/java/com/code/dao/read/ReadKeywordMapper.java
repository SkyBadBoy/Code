
package com.code.dao.read;
import java.util.*;
import com.code.config.mybatis.ReadMapper;
import com.code.domain.Keyword;
import java.util.List;

/**
 * <p> Mapper Class</p>
 *
 * @author majian
 * 
 */
public interface ReadKeywordMapper extends ReadMapper<Keyword>{

    Set<String> getKeywordSet();

}
