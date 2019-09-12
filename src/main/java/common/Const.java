package common;

public class Const {

    /**
     * 数据库增加、删除、更新操作的返回值
     * -1 为自定义错误
     * 受影响行数 row
     * row 为 0 则数据库操作失败
     * row 为 1 则数据库操作成功
     */
    public static final Integer NULL = 0;
    public static final Integer ERROR = -1;

    public static final String TOKEN = "token";
    public static final String USER = "user";

    /**
     * 正则
     */
    public static final String REGEXP_PHONE
            = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

}
