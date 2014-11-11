/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.minws.sso.auth;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.minws.sso.blog.Blog;

public class AuthController extends Controller {
	
	@Before(LoginInterceptor.class)
	public void index() {
		setAttr("blogList", Blog.me.findAll());
		render("index.jsp");
	}
	
	public void login() {
		render("login.jsp");
	}
}