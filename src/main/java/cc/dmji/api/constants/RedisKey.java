package cc.dmji.api.constants;

/**
 * Created by echisan on 2018/5/21
 */
public class RedisKey {

    /** 验证邮箱时存放uuid */
    public static final String VERIFY_EMAIL_KEY = "verify_email_uid_";

    /** 发送邮箱认证请求限制 */
    public static final String RE_VERIFY_EMAIL_LIMIT_ = "re_verify_email_limit_";

    /** 存放token */
    public static final String LOGIN_TOKEN_KEY = "login_token";

    /** 存放LockUser */
    public static final String LOGIN_LOCK_USER_KEY = "login_lock_user";

    /** IP提交频繁 */
    public static final String POST_FREQUENT_IP_KEY = "POST_FREQUENT_IP_";

    /** 弹幕缓存 */
    public static final String DANMAKU_KEY = "danmaku_id_";

    /** 用户日志记录 */
    public static final String USER_LOG_RECORD_KEY = "user_log_record";

    /** 在线注册用户 */
    public static final String ONLINE_AUTH_USER_KEY = "online_auth_user";

    /** 在线游客 */
    public static final String ONLINE_ANON_USER_KEY = "online_anon_user";

    /** 一天共访问次数 */
    public static final String VISIT_COUNT_KEY = "visit_count_key";

    /** 今日最多同时在线注册用户数 */
    public static final String MAX_ONLINE_AUTH_USER_KEY = "max_online_auth_user";

    /** 今日最多同时在线游客数 */
    public static final String MAX_ONLINE_ANON_USER_KEY = "max_online_anon_user";

    /** 今日共同在线人数峰值 */
    public static final String MAX_ONLINE_TOTAL_USER_KEY = "max_online_total_user";

}
