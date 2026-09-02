package io.github.ehdnd.servlet.web.frontcontroller.v3;

import io.github.ehdnd.servlet.web.frontcontroller.ModelView;
import io.github.ehdnd.servlet.web.frontcontroller.MyView;
import io.github.ehdnd.servlet.web.frontcontroller.v3.ControllerV3;
import io.github.ehdnd.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import io.github.ehdnd.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import io.github.ehdnd.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jspecify.annotations.NonNull;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

  private Map<String, ControllerV3> controllerMap = new HashMap<>();

  public FrontControllerServletV3() {
    controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
    controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
    controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String requestURI = req.getRequestURI();

    ControllerV3 controller = controllerMap.get(requestURI);
    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    // req -> paramMap 도입 - 종속성 제거
    Map<String, String> paramMap = createParamMap(req);
    // req -> 별도의 Model 객체 (ModelView)
    ModelView modelView = controller.process(paramMap);

    String viewName = modelView.getViewName();
    MyView view = viewResolver(viewName);

    view.render(modelView.getModel(), req, resp);
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
