package wusc.edu.web.boss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.common.utils.StringUtils;

import wusc.edu.facade.user.entity.User;
import wusc.edu.facade.user.enums.UserStatusEnum;
import wusc.edu.facade.user.enums.UserTypeEnum;
import wusc.edu.facade.user.service.UserService;
import wusc.edu.web.common.constant.SessionConstant;

/**
 * 
 * @描述: 用户登录  .
 * @作者: WuShuicheng .
 * @创建时间: 2015-1-25,下午7:50:22 .
 * @版本号: V1.0 .
 */
@Scope("prototype")
public class PmsLoginAction {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsLoginAction.class);
	@Autowired
	private UserService service;


	/**
	 * 取出当前登录用户对象
	 */
	public User getLoginedUser(HttpServletRequest req) {
		User user = (User) req.getAttribute(SessionConstant.USER_SESSION_KEY);
		
		return user;
	}

	/**
	 * 登录验证Action
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public Map<String, Object> userLogin(HttpServletRequest request,
			@RequestParam("userNo")  String userNo,
			@RequestParam("userPwd") String pwd) {
		Map<String,Object> data = new HashMap<>();
		String userNoMsg ; 
		try {
			
			// 明文用户名
			if (StringUtils.isBlank(userNo)) {
				data.put("userNoMsg", "用户名不能为空");
				return data ;
			}
			User user = service.findUserByUserNo(userNo);
			if (user == null) {
				log.warn("== no such user");
				data.put("userNoMsg", "用户名或密码不正确");
				return data ;
			}
			
			if (user.getStatus().intValue() == UserStatusEnum.INACTIVE.getValue()) {
				log.warn("== 帐号【" + userNo + "】已被冻结");
				data.put("userNoMsg", "该帐号已被冻结");
				return data ;
			}
			if (StringUtils.isBlank(pwd)) {
				data.put("userPwdMsg", "密码不能为空");
				return data ;
			}
			// 加密明文密码
			// 验证密码
			if (user.getUserPwd().equals(DigestUtils.sha1Hex(pwd))) {
				// 用户信息，包括登录信息和权限
				request.put(SessionConstant.USER_SESSION_KEY, user);

				// 将主帐号ID放入Session 
				if (UserTypeEnum.MAIN_USER.getValue().equals(user.getUserType())) {
					this.getSession().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, user.getId());
				} else if (UserTypeEnum.SUB_USER.getValue().equals(user.getUserType())) {
					this.getSessionMap().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, user.getMainUserId());
				} else {
					// 其它类型用户的主帐号ID默认为0
					this.getSessionMap().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, 0L);
				}

				data.put("userNo", userNo);
				data.put("lastLoginTime", user.getLastLoginTime());

				try {
					// 更新登录数据
					user.setLastLoginTime(new Date());
					user.setPwdErrorCount(0); // 错误次数设为0
					service.update(user);

				} catch (Exception e) {
					data.put("errorMsg", e.getMessage());
					return data ;
				}

				// 判断用户是否重置了密码，如果重置，弹出强制修改密码页面； TODO
				data.put("isChangePwd", user.getIsChangedPwd());

				return data;

			} else {
				// 密码错误
				log.warn("== wrongPassword");
				// 错误次数加1
				Integer pwdErrorCount = user.getPwdErrorCount();
				if (pwdErrorCount == null) {
					pwdErrorCount = 0;
				}
				user.setPwdErrorCount(pwdErrorCount + 1);
				user.setPwdErrorTime(new Date()); // 设为当前时间
				String msg = "";
				if (user.getPwdErrorCount().intValue() >= SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT) {
					// 超5次就冻结帐号
					user.setStatus(UserStatusEnum.INACTIVE.getValue());
					msg += "<br/>密码已连续输错【" + SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT + "】次，帐号已被冻结";
				} else {
					msg += "<br/>密码错误，再输错【" + (SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT - user.getPwdErrorCount().intValue()) + "】次将冻结帐号";
				}
				service.update(user);
				data.put("userPwdMsg", msg);
				return data ;
			}

		} catch (RuntimeException e) {
			log.error("login exception:", e);
			data.put("errorMsg", "登录出错");
			return data ;
		} catch (Exception e) {
			log.error("login exception:", e);
			data.put("errorMsg", "登录出错");
			return data ;
		}
	}

	/**
	 * 跳转到退出确认页面.
	 * 
	 * @return LogOutConfirm.
	 */
	public String logoutConfirm() {
		log.info("== logoutConfirm");
		return "logoutConfirm";
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		this.getSessionMap().clear();
		log.info("== logout");
		return "logout";
	}

	/**
	 * 跳转到登录超时确认页面.
	 * 
	 * @return LogOutConfirm.
	 * @throws Exception
	 */
	public String timeoutConfirm() throws Exception {
		this.getSessionMap().clear();
		return "timeoutConfirm";
	}

}
