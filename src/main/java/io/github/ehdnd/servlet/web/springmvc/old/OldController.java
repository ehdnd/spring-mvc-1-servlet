package io.github.ehdnd.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") // 스프링빈 이름을 url 패턴으로 맞추면 호출된다.
public class OldController implements Controller {

  // url 이름이랑 똑같이 생긴 스프링 빈을 찾아주는 HandlerMapping (자동등록)
  // 핸들러 어댑터도 찾고 실행해야한다.

  @Override
  public ModelAndView handleRequest(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    System.out.println("OldController.handleRequest");
    return new ModelAndView("new-form");
  }
}
