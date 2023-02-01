package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();

        String search = request.getParameter("search");

        List<String> companies;
        companies = getCompanies();

        if (search == null || search.isEmpty()) {
            for (String company : companies) {
                out.println(company);
            }
        } else {
            List<String> companiesSearch = companies.stream()
                    .filter(company -> company.contains(search))
                    .collect(Collectors.toList());
            if (companiesSearch.isEmpty()) {
                out.println("Companies not found");
            } else {
                for (String company : companiesSearch) {
                    out.println(company);
                }
            }
        }
        // END
    }
}
