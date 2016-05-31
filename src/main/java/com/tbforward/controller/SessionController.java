package com.tbforward.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/session")
public class SessionController {
    
    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

//    @Autowired
//    private UserService userService;
//    
//    @RequestMapping(method=RequestMethod.POST)
//    public @ResponseBody LoginResponseBO login(@RequestBody LoginBO user, 
//            HttpSession session, HttpServletRequest request) {
//        String userName = user.getUsername();
//        String password = user.getPassword();
//        String ip = request.getRemoteAddr();
//        
//                        
//        session.setAttribute(SessionConstants.SESSION_USER_OBJECT, sUser);
//
//        
//        return response;
//    }
//    
//    @RequestMapping(method=RequestMethod.GET)
//    public @ResponseBody SessionUserBO get(SessionUser currentUser) {
//    }
//    
//    @RequestMapping(method=RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public void logout(SessionUser currentUser, HttpSession session, HttpServletRequest request) {
//        session.invalidate();
//    }
}
