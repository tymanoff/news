package plus.irbis.news.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import plus.irbis.news.config.NewsServiceProperties;
import plus.irbis.news.web.utils.ErrorUtil;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class AuthenticationProductTokenFilter implements Filter {

    private static final String SERVICE_URL = "/api/v1/news";
    private static final String PRODUCT_TOKEN = "Product-Token";
    private final ObjectMapper objectMapper;
    private final NewsServiceProperties newsServiceProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();

        if (!(StringUtils.startsWith(pathInfo, SERVICE_URL) || StringUtils.startsWith(servletPath, SERVICE_URL))
                || !HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String productTokenHeader = request.getHeader(PRODUCT_TOKEN);

        if (!StringUtils.equals(productTokenHeader, newsServiceProperties.getProductToken())) {
            ErrorUtil.sendError(response, objectMapper, HttpServletResponse.SC_FORBIDDEN, "Product token " +
                    "is invalid");
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
