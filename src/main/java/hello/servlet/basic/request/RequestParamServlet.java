package hello.servlet.basic.request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1. 파라미터 전송 기능 https://localhost:8079/request-param?username=hello&age=20
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("[전체 파ㅣ라미터 조히] - start");
    request.getParameterNames().asIterator().forEachRemaining(
        paramName -> System.out.println(paramName + " : " + request.getParameter(paramName)));
    System.out.println("[전체 파라미터 조히] - end");
    System.out.println();

    System.out.println("[단일 파라미터 조회} - start");
    System.out.println("username : " + request.getParameter("username"));
    System.out.println("age : " + request.getParameter("age"));
    System.out.println("[단일 파라미터 조회} - end");
    System.out.println();

    // 중복 사용 지양
    // 중복시 getParameterValues()의 첫 번쨰 값 return
    String[] usernames = request.getParameterValues("username");
    for (String name : usernames) {
      System.out.println("username : " + name);
    }
    response.getWriter().write("OK");
  }
}