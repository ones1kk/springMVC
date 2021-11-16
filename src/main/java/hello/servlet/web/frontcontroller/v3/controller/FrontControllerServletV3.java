package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

  private final Map<String, ControllerV3> controllerV3Map = new HashMap<>();

  public FrontControllerServletV3() {
    controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
    controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
    controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("FrontControllerServletV3.service");

    String requestURI = request.getRequestURI();
    ControllerV3 controller = controllerV3Map.get(requestURI);
    if (controller == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    // paramMap
    // method 추출 단축키 : option + command + M
    Map<String, String> paramMap = createParamMap(request);

    ModelView modelView = controller.process(paramMap);

    String viewName = modelView.getViewName();
    MyView myView = viewResolver(viewName);

    myView.render(modelView.getModel(), request, response);

  }

  private MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }

  private Map<String, String> createParamMap(HttpServletRequest request) {
    Map<String, String> paramMap = new HashMap<>();
    request.getParameterNames().asIterator()
        .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
    return paramMap;
  }

}
