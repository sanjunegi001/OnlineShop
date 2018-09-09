/*
 * 
 */
package com.auth.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auth.dao.UserLoginDAO;
import com.auth.domain.Loginuser;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginController.
 */
@Controller
public class LoginController {

	@Autowired
	private UserLoginDAO userLogindao;

	/**
	 * Home.
	 *
	 * @param loginuser
	 *            the loginuser
	 * @return the model and view
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView Home(@ModelAttribute("loginuser") Loginuser loginuser) {

		return new ModelAndView("Login");
	}

	/**
	 * Process login.
	 *
	 * @param loginuser
	 *            the loginuser
	 * @param model
	 *            the model
	 * @param session
	 *            the session
	 * @return the model and view
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "/processLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processLogin(@ModelAttribute("loginuser") Loginuser loginuser, Model model, HttpSession session) throws Exception {

		String captcha = (String) session.getAttribute("CAPTCHA");

		Date startTime = null;
		Calendar c1 = Calendar.getInstance();
		startTime = c1.getTime();

		Date connectionStartTime = null;
		String logMsg = "\n-";
		BufferedWriter out = null;
		BufferedWriter out1 = null;
		FileWriter fstream = null;
		FileWriter fstream1 = null;
		Calendar c = Calendar.getInstance();
		long nonce = c.getTimeInMillis();

		String username = loginuser.getUsername().trim();
		String password = loginuser.getPassword().trim();

		ClassLoader classLoader = getClass().getClassLoader();

		try {

			if (captcha == null || (captcha != null && !captcha.equals(loginuser.getCaptcha()))) {
				loginuser.setCaptcha("");
				model.addAttribute("exerror", "0");
				model.addAttribute("error", "Captcha does not match");
				return new ModelAndView("Login");
			}

			else if (loginuser.getUsername().trim() == "" && loginuser.getPassword().trim() == "") {
				model.addAttribute("exerror", "0");
				model.addAttribute("error", "Username and Password Are Required Field! ");
				return new ModelAndView("Login");
			} else if (loginuser.getUsername().trim() == "") {
				model.addAttribute("exerror", "0");
				model.addAttribute("error", "Username Is Required!");
				return new ModelAndView("Login");
			} else if (loginuser.getPassword().trim() == "") {
				model.addAttribute("exerror", "0");
				model.addAttribute("error", "Password Is Required!");
				return new ModelAndView("Login");
			}

			else {

				boolean isValidUser = userLogindao.isValidUser(loginuser.getUsername().trim(), loginuser.getPassword().trim(), session);

				if (isValidUser == true) {
					return new ModelAndView("redirect:/home.html");

				}

				else

				{

					model.addAttribute("exerror", "0");
					model.addAttribute("error", "Invalid Username And Password");
					return new ModelAndView("Login");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Login");

	}

}
