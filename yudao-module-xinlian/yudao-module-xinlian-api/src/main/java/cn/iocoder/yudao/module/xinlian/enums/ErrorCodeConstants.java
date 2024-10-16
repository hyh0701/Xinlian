package cn.iocoder.yudao.module.xinlian.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode NEWS_CATEGORY_NOT_EXISTS = new ErrorCode(6001, "新闻目录不存在");
    ErrorCode NEWS_CATEGORY_EXITS_CHILDREN = new ErrorCode(6002, "存在存在子新闻目录，无法删除");
    ErrorCode NEWS_CATEGORY_PARENT_NOT_EXITS = new ErrorCode(6003,"父级新闻目录不存在");
    ErrorCode NEWS_CATEGORY_PARENT_ERROR = new ErrorCode(6004, "不能设置自己为父新闻目录");
    ErrorCode NEWS_CATEGORY_CATE_NAME_DUPLICATE = new ErrorCode(6005, "已经存在该分类名字的新闻目录");
    ErrorCode NEWS_CATEGORY_PARENT_IS_CHILD = new ErrorCode(6006, "不能设置自己的子NewsCategory为父NewsCategory");

    ErrorCode NEWS_NOT_EXISTS = new ErrorCode(6101, "新闻不存在");

    ErrorCode JOB_NOT_EXISTS = new ErrorCode(6201, "职务管理不存在");

    ErrorCode ACTIVITY_NOT_EXISTS = new ErrorCode(6301, "活动记录不存在");

    ErrorCode ORG_NOT_EXISTS = new ErrorCode(6401, "组织架构管理不存在");
    ErrorCode ORG_EXITS_CHILDREN = new ErrorCode(6402, "存在存在子组织架构管理，无法删除");
    ErrorCode ORG_PARENT_NOT_EXITS = new ErrorCode(6403,"父级组织架构管理不存在");
    ErrorCode ORG_PARENT_ERROR = new ErrorCode(6404, "不能设置自己为父组织架构管理");
    ErrorCode ORG_NAME_DUPLICATE = new ErrorCode(6405, "已经存在该组织名字的组织架构管理");
    ErrorCode ORG_PARENT_IS_CHILD = new ErrorCode(6406, "不能设置自己的子XinlianOrg为父XinlianOrg");

    ErrorCode ACTIVITY_APPOINT_NOT_EXISTS = new ErrorCode(6501, "活动报名不存在");
    ErrorCode ACTIVITY_APPOINT_INVALID = new ErrorCode(6502, "活动已停止报名");
    ErrorCode ACTIVITY_APPOINT_LIMIT_MEMBER = new ErrorCode(6503, "活动仅允许会员报名");
    ErrorCode ACTIVITY_APPOINT_LIMIT_MAX = new ErrorCode(6503, "活动报名人数已满");
    ErrorCode ACTIVITY_APPOINT_HAS_EXIST = new ErrorCode(6503, "用户已经报名，无需重复提交");

    ErrorCode MEMBER_NOT_EXISTS = new ErrorCode(6601, "新联会员不存在");
    ErrorCode MEMBER_Mobile_HAS_EXISTS = new ErrorCode(6602, "新联会员手机号码已存在");
    ErrorCode Failed_Create_System_User = new ErrorCode(6603, "创建系统用户失败");
    ErrorCode MEMBER_NOT_EXISTS_CONTACT = new ErrorCode(6604, "新联联系人配置错误");
    ErrorCode MEMBER_SEARCH_NOT_EXISTS = new ErrorCode(6605, "未搜索到会员");
    ErrorCode MEMBER_MUST_BE_PASSWORD = new ErrorCode(6606, "需输入用户密码");

    ErrorCode APPOINT_NOT_EXISTS = new ErrorCode(6701, "绩效计分不存在");
    ErrorCode APPOINT_NO_PERMISSION = new ErrorCode(6702, "无权限创建绩效计分");
    ErrorCode APPOINT_HAS_EXIST_USER = new ErrorCode(6702, "用户已创建该季度考核计分");

    ErrorCode ADVICE_NOT_EXISTS = new ErrorCode(6801, "咨询记录不存在");
    ErrorCode ADVICE_USER_NOT_EXISTS = new ErrorCode(6801, "该用户不存在咨询记录");
    ErrorCode COMPANY_NOT_EXISTS = new ErrorCode(6901, "企业会员不存在");

}
