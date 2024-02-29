package in.freelance.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String tenantName = req.getHeader("Tenant_Id");
        TenantContext.setCurrentTenant(tenantName);

        try{
            chain.doFilter(request, response);
        }
        finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
