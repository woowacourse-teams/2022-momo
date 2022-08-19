package com.woowacourse.momo.global.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String PREFIX = "momo_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        Identifier prefixedTableName = new Identifier(PREFIX + name.getText().toLowerCase(), name.isQuoted());
        return super.toPhysicalTableName(prefixedTableName, context);
    }
}
