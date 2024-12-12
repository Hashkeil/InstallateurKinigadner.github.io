package at.DiplomProjekt.Installateur.security;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;


@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        model.addAttribute("status", getErrorStatus(request));
        model.addAttribute("error", getErrorMessage(servletWebRequest));

        return "error";
    }

    private HttpStatus getErrorStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return statusCode != null ? HttpStatus.valueOf(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getErrorMessage(ServletWebRequest servletWebRequest) {
        Throwable error = errorAttributes.getError(servletWebRequest);
        return error != null ? error.getMessage() : "Unknown error";
    }


}
