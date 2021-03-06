package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControlControllerV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private final Map<String, ControllerV4> ControllerV4Map = new HashMap<>();

    public FrontControllerServletV4() {
        ControllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        ControllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        ControllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        ControllerV4 controller = ControllerV4Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        MyView myView = viewResolver(viewName);

        myView.render(model, request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + "/7.jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
            .forEachRemaining(
                paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

}
