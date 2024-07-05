package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.Patent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatentMapper extends BaseMapper<Patent> {
}