package io.github.ehdnd.servlet.web.frontcontroller.v4;

import io.github.ehdnd.servlet.web.frontcontroller.ModelView;
import io.github.ehdnd.servlet.web.frontcontroller.MyView;
import io.github.ehdnd.servlet.web.frontcontroller.v4.ControllerV4;
import io.github.ehdnd.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import io.github.ehdnd.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import io.github.ehdnd.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jspecify.annotations.NonNull;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

  private Map<String, ControllerV4> controllerMap = new HashMap<>();

  public FrontControllerServletV4() {
    controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
    controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
    controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String requestURI = req.getRequestURI();

    ControllerV4 controller = controllerMap.get(requestURI);
    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    Map<String, String> paramMap = createParamMap(req);
    // 별도의 Model 객체 (ModelView) -> model 제작해 전달 
    Map<String, Object> model = new HashMap<>();
    
    String viewName = controller.process(paramMap, model);
    MyView view = viewResolver(viewName);

    view.render(model, req, resp);
  }

  private static MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }

  // 큰 로직들 중 세세한 로직이라 따로 메서드를 뽑아냈다.
  private static Map<String, String> createParamMap(HttpServletRequest req) {
    Map<String, String> paramMap = new HashMap<>();
    req.getParameterNames().asIterator()
        .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
    return paramMap;
  }
}
