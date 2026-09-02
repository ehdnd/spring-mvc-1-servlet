package io.github.ehdnd.servlet.web.frontcontroller.v5;

import io.github.ehdnd.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

  boolean supports(Object handler);

  ModelView handle(HttpServletRequest req, HttpServletResponse resp, Object handler)
      throws ServletException, IOException;

  // Q: 왜 ModelView 를 반환하도록 했는가? V4 처럼 이름만 주고 생성하도록 하지 않은 이유는?
  // A: 
}
