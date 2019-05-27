/**
 * Copyright (C), 2013-2018, XXX有限公司
 * FileName: Author
 * Author:  T_DDong
 * Date:     2018/10/25 15:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.gtmap.gtcc.account.config;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author T_DDong
 * @create 2018/10/25 15:39

 * @since 1.0.0
 */
public enum Author {

    LOGIN("LOGIN");

    /**
     * 枚举内容
     */
    private String desc;

    public String getDesc() {
        return desc;
    }

    Author(String desc) {
        this.desc = desc;
    }
}