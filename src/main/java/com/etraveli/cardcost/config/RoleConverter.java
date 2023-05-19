package com.etraveli.cardcost.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.etraveli.cardcost.enums.RoleTypeEnum;

@Component
public class RoleConverter implements Converter<String, RoleTypeEnum> {
  @Override
  public RoleTypeEnum convert(String source) {
    return RoleTypeEnum.valueOf(source.toUpperCase());
  }
}
