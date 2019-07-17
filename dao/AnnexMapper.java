package com.yonyou.iuap.annex.dao;

import com.yonyou.iuap.annex.entity.Annex;
import com.yonyou.iuap.annex.entity.AnnexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnnexMapper {
    long countByExample(AnnexExample example);

    int deleteByExample(AnnexExample example);

    int deleteByPrimaryKey(String id);

    int insert(Annex record);

    int insertSelective(Annex record);

    List<Annex> selectByExample(AnnexExample example);

    Annex selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Annex record, @Param("example") AnnexExample example);

    int updateByExample(@Param("record") Annex record, @Param("example") AnnexExample example);

    int updateByPrimaryKeySelective(Annex record);

    int updateByPrimaryKey(Annex record);
}