package com.sky.framework.common.id.concurrent;

import com.sky.framework.common.dto.base.BaseParamDTO;
import com.sky.framework.common.dto.base.BaseResultDTO;
import org.junit.Test;

public class ObjectTest {

    @Test
    public void objectEqualsTest() {
        BaseParamDTO o1 = new BaseParamDTO();
        Base1 o2 = new Base1();
        Base2 o3 = new Base2();
    }

    public class Base1 extends BaseResultDTO {

    }

    public class Base2 extends BaseParamDTO {

    }
}
